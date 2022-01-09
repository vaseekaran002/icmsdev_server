package com.perksoft.icms.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.exception.IcmsCustomException;
import com.perksoft.icms.models.Page;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.request.SignupRequest;
import com.perksoft.icms.payload.response.PageResponse;
import com.perksoft.icms.payload.response.UserResponse;
import com.perksoft.icms.service.UserService;
import com.perksoft.icms.util.CommonUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/user")
public class UserController {

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private UserService userService;

	@ApiOperation(value = "retrieves  user by user id", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves group list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/profile/{userid}")
	public ResponseEntity<String> getUserById(@PathVariable("tenantid") String tenantId,
			@PathVariable("userid") String userId) {
		log.info("Started fetching user by user id for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;

		try {
			User user = userService.getUserByIdAndTenantId(userId, tenantId);
			UserResponse userResponse = userService.convertToUserResponse(user);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					userResponse);
		} catch (IcmsCustomException e) {
			log.info("Error occurred while fetching user by user id {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while fetching user by user id {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of  fetching user by user id and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "updates user profile", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates user profile"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/update/profile")
	public ResponseEntity<String> updateProfile(@RequestBody SignupRequest signupRequest,
			@PathVariable("tenantid") String tenantId) {
		log.info("Started updating user by user id for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;

		try {
			User existingUser = userService.getUserByIdAndTenantId(signupRequest.getId(), tenantId);

			if (existingUser != null) {
				existingUser.setFirstName(signupRequest.getFirstName());
				existingUser.setLastName(signupRequest.getLastName());
				existingUser.setMobileNumber(signupRequest.getMobileNumber());
				existingUser.setProfileImage(signupRequest.getProfileImage());
				existingUser.setRoles(signupRequest.getRoles());
				User user = userService.saveUser(existingUser);
				responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS, user);
			} else {
				log.info("Error occurred while updating user by user id {}", "");
				responseEntity = commonUtil.generateEntityResponse("Profile not Updated", Constants.FAILURE,
						Constants.FAILURE);
			}
		} catch (IcmsCustomException e) {
			log.info("Error occurred while updating user by user id {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while updating user by user id {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of updating user by user id and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "retrieves metadata for users", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved metadata"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{userid}/metadata")
	public ResponseEntity<String> getUserMetadata(@PathVariable("tenantid") String tenantId,
			@PathVariable("userid") String userId) {
		log.info("Started fetching metadata for user for tenant {} ", tenantId);
		ResponseEntity<String> responseEntity = null;
		try {
			User user = userService.getUserByIdAndTenantId(userId, tenantId);
			UserResponse userResponse = userService.convertToUserResponse(user);
			userResponse.setMetaData(null);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					userResponse.getMetaData());
		} catch (IcmsCustomException e) {
			log.info("Error occurred while fetching metadata for user {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while fetching metadata for user {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of fetching metadata for user and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "retrieves all users", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved users"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("{loggedinUserId}/list")
	public ResponseEntity<String> getAllUsers(@PathVariable("tenantid") String tenantId,
			@PathVariable("loggedinUserId") String userId) {
		log.info("Started fetching all the users for tenant {} ", tenantId);
		ResponseEntity<String> responseEntity = null;

		try {
			List<User> userList = userService.getAllUsers(tenantId);
			User user = userService.getUserByIdAndTenantId(userId, tenantId);

			if (user != null) {
				userList.remove(user);
			}
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS, userList);
		} catch (IcmsCustomException e) {
			log.info("Error occurred while fetching all the users for tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while fetching all the users for tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of fetching all the users for tenant and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "retrieves all users following pages", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved all user follow pages"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("{userId}/pages")
	public ResponseEntity<String> getAllUserFollowPages(@PathVariable("tenantid") String tenantId,
			@PathVariable("userId") String userId) {
		log.info("Started fetching all the users following pages for tenant {} ", tenantId);
		ResponseEntity<String> responseEntity = null;

		try {
			User existingUser = userService.getUserByIdAndTenantId(userId, tenantId);
			Set<Page> followedPages = existingUser.getFollowingPages();
			List<Page> pageList = new ArrayList<>(followedPages);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS, pageList);
		} catch (IcmsCustomException e) {
			log.info("Error occurred while fetching all the users following pages {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while fetching all the users following pages {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of fetching all the users following pages and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "retrieves users favourite pages", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved all user follow pages"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{userId}/favourite/pages")
	public ResponseEntity<String> getFavouritePages(@PathVariable("userId") String userId) {
		log.info("Started fetching favourite pages for user {} ", userId);
		ResponseEntity<String> responseEntity = null;

		try {
			List<PageResponse> pageResponses = userService.getFavouritePages(userId);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					pageResponses);
		} catch (IcmsCustomException e) {
			log.info("Error occurred while fetching favourite pages for user {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while fetching favourite pages for user {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of fetching favourite pages for user and response {}", responseEntity);
		return responseEntity;
	}

}
