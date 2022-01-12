package com.perksoft.icms.controllers;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.exception.IcmsCustomException;
import com.perksoft.icms.models.JwtBlacklist;
import com.perksoft.icms.models.MetaData;
import com.perksoft.icms.models.Role;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.request.LoginRequest;
import com.perksoft.icms.payload.request.LogoutRequest;
import com.perksoft.icms.payload.request.SignupRequest;
import com.perksoft.icms.payload.response.JwtResponse;
import com.perksoft.icms.security.services.UserDetailsImpl;
import com.perksoft.icms.service.JwtTokenService;
import com.perksoft.icms.service.RoleService;
import com.perksoft.icms.service.UserService;
import com.perksoft.icms.util.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/auth/{tenantid}")
@Api(value = "Authentication service")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserService userService;

	@Autowired
	private RoleService roleService;

	@Autowired
	private PasswordEncoder encoder;

	@Autowired
	private JwtTokenService jwtTokenService;

	@Autowired
	private CommonUtil commonUtil;

	@ApiOperation(value = "sign user", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves token and user details"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/login")
	public ResponseEntity<String> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
			@PathVariable("tenantid") String tenantId) {
		log.info("Started authenticateUser for tenantId {} and username is {}", tenantId, loginRequest.getUsername());
		ResponseEntity<String> responseEntity = null;

		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = jwtTokenService.generateToken(authentication);

			UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
			List<String> roles = userDetails.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());
			List<Role> userRoles = roleService.getRolesByRoleNamesIn(roles);
			Set<MetaData> finalmetadata = new HashSet<>();

			for (Role role : userRoles) {
				finalmetadata.addAll(role.getMetadata());
			}
			JwtResponse jwtResponse = new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(),
					userDetails.getEmail(), roles, finalmetadata);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					jwtResponse);

		} catch (IcmsCustomException e) {
			log.info("Error occurred while authenticating user {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (BadCredentialsException e) {
			log.info("Error occurred while authenticating user {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while authenticating user {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of authenticateUser and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "Successfully created new user", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully created new user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@PathVariable("tenantid") String tenantId,
			@Valid @RequestBody SignupRequest signUpRequest) {
		log.info("Started signing up user for tenantId {}", tenantId);
		ResponseEntity<String> responseEntity = null;

		if (!userService.existsByUsername(signUpRequest.getEmail())) {

			try {
				User user = new User(signUpRequest.getEmail(), signUpRequest.getEmail(),
						encoder.encode(signUpRequest.getPassword()));
				Set<Role> roles = new HashSet<>();
				List<String> strRoles = signUpRequest.getRoles();
				
				if(strRoles!= null && !strRoles.isEmpty()) {
					
					for (String roleName : signUpRequest.getRoles()) {
						Optional<Role> userRole = roleService.getRoleByName(roleName);

						if (userRole.isPresent()) {
							roles.add(userRole.get());
						}
					}
					user.setRoles(roles);
				}				
				
				
				user.setFirstName(signUpRequest.getFirstName());
				user.setLastName(signUpRequest.getLastName());
				user.setMobileNumber(signUpRequest.getMobileNumber());
				user.setTenantId(UUID.fromString(tenantId));
				userService.saveUser(user);
				responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS, user);
			} catch (IcmsCustomException e) {
				e.printStackTrace();
				log.info("Error occurred while signing up user {}", e.getMessage());
				responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE,
						Constants.FAILURE);
			} catch (Exception e) {
				e.printStackTrace();
				log.info("Error occurred while signing up user {}", e.getMessage());
				responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
						Constants.EXCEPTION);
			}
		} else {
			log.info("Username/email address is already created! {}", "");
			responseEntity = commonUtil.generateEntityResponse("Username/email address is already created!",
					Constants.FAILURE, Constants.FAILURE);
		}
		log.info("End of signing up user and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "logout user", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully logged out user"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping(value = "/logout", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> logout(@RequestBody LogoutRequest logoutRequest,
			@PathVariable("tenantid") String tenantId) {
		log.info("Started logout user for tenantId {}", tenantId);
		ResponseEntity<String> responseEntity = null;

		try {
			JwtBlacklist jwtBlacklist = new JwtBlacklist();
			jwtBlacklist.setToken(logoutRequest.getToken());
			jwtBlacklist.setUsername(logoutRequest.getUsername());
			jwtBlacklist.setLogoutTime(LocalDateTime.now());
			jwtBlacklist = jwtTokenService.saveJwtBlacklist(jwtBlacklist);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS, Constants.SUCCESS, jwtBlacklist);
		} catch (Exception e) {
			log.info("Error occurred while signing up user {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}

		log.info("End of logout user and response {}", responseEntity);
		return responseEntity;
	}
}
