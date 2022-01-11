package com.perksoft.icms.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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
import com.perksoft.icms.models.ERole;
import com.perksoft.icms.models.MetaData;
import com.perksoft.icms.models.Role;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.request.LoginRequest;
import com.perksoft.icms.payload.request.SignupRequest;
import com.perksoft.icms.payload.response.JwtResponse;
import com.perksoft.icms.repository.RoleRepository;
import com.perksoft.icms.repository.UserRepository;
import com.perksoft.icms.security.jwt.JwtTokenService;
import com.perksoft.icms.security.services.UserDetailsImpl;
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
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

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
	@PostMapping("/signin")
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
			Set<MetaData> finalmetadata = new HashSet<>();

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

		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			log.info("Error occurred while signing up user {}", "Username is already taken!");
			responseEntity = commonUtil.generateEntityResponse("Username is already taken!", Constants.FAILURE,
					Constants.FAILURE);
		}
		// need to add email validation method
		if (userRepository.existsByUsername(signUpRequest.getEmail())) {
			log.info("Error occurred while signing up user {}", "Email is already in use!");
			responseEntity = commonUtil.generateEntityResponse("Email is already in use!", Constants.FAILURE,
					Constants.FAILURE);
		}

		try {
			// Create new user's account
			User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
					encoder.encode(signUpRequest.getPassword()));

			Set<String> strRoles = signUpRequest.getRole();
			Set<Role> roles = new HashSet<>();

			if (strRoles == null) {
				Role userRole = roleRepository.findByName(ERole.USER)
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			} else {
				strRoles.forEach(role -> {
					switch (role) {
					case "superadmin":
						Role adminRole = roleRepository.findByName(ERole.SUPER_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);

						break;
					case "tenantadmin":
						Role modRole = roleRepository.findByName(ERole.TENANT_ADMIN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);

						break;
					case "ROLE_MUSICIAN":
						Role musRole = roleRepository.findByName(ERole.MUSICIAN)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(musRole);
						break;
					default:
						Role userRole = roleRepository.findByName(ERole.USER)
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
					}
				});
			}

			user.setRoles(roles);
			user.setTenantId(UUID.fromString(tenantId));
			userRepository.save(user);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS, user);
		} catch (IcmsCustomException e) {
			log.info("Error occurred while signing up user {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while signing up user {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of signing up user and response {}", responseEntity);
		return responseEntity;
	}
}
