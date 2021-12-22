package com.perksoft.icms.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.models.MetaData;
import com.perksoft.icms.models.Page;
import com.perksoft.icms.models.Tenant;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.request.SignupRequest;
import com.perksoft.icms.payload.response.MessageResponse;
import com.perksoft.icms.payload.response.PageResponse;
import com.perksoft.icms.payload.response.UserResponse;
import com.perksoft.icms.repository.TenantRepository;
import com.perksoft.icms.repository.UserRepository;
import com.perksoft.icms.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/user")
public class UserController {

	@Autowired
	private UserService userService;

	@Autowired
	private TenantRepository tenantRepository;

	@Autowired
	private UserRepository userRepository;

	

	@ApiOperation(value = "retrieves  user by user id", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves group list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/profile/{userid}")
	public ResponseEntity<?> getUserById(@PathVariable("tenantid") String tenantId,
			@PathVariable("userid") String userId) {
		UserResponse userResponse = userService.getUserById(UUID.fromString(userId), UUID.fromString(tenantId));
		return ResponseEntity.ok(userResponse);
	}

	@ApiOperation(value = "updates user profile", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates user profile"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/update/profile")
	public ResponseEntity<?> updateProfile(@RequestBody SignupRequest signUpRequest,
			@PathVariable("tenantid") String tenantId) {

		Optional<User> existingUser = userRepository.findByIdAndTenantId(signUpRequest.getId(),
				UUID.fromString(tenantId));
		if (existingUser.isPresent()) {
			existingUser.get().setFirstName(signUpRequest.getFirstName());
			existingUser.get().setLastName(signUpRequest.getLastName());
			existingUser.get().setMobileNumber(signUpRequest.getMobileNumber());
			existingUser.get().setProfileImage(signUpRequest.getProfileImage());
			User user = userRepository.save(existingUser.get());
			return ResponseEntity.ok(user);
		} else {
			return ResponseEntity.ok(new MessageResponse("Profile not Updated"));
		}
	}

	@ApiOperation(value = "retrieves metadata for users", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved metadata"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{userid}/metadata")
	public ResponseEntity<?> getUserMetadata(@PathVariable("tenantid") String tenantId,
			@PathVariable("userid") String userId) {
		Optional<Tenant> existingTenant = tenantRepository.findById(UUID.fromString(tenantId));
		Set<MetaData> metadata = existingTenant.get().getMetaData();
		UserResponse userResponse = userService.getUserById(UUID.fromString(userId), UUID.fromString(tenantId));
		userResponse.setMetadatas(metadata);
		return ResponseEntity.ok(userResponse.getMetadatas());
	}

	@ApiOperation(value = "retrieves all users", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved users"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("{loggedinUserId}/list")
	public List<User> getAllUsers(@PathVariable("tenantid") String tenantId,
			@PathVariable("loggedinUserId") String userId) {
		List<User> userList = userRepository.findAllByTenantId(UUID.fromString(tenantId));
		Optional<User> loggedUser = userRepository.findById(UUID.fromString(userId));

		for (User u : userList) {
			
			if (u.getId() == loggedUser.get().getId()) {
				userList.remove(u);
				break;
			}
		}
		return userList;
	}
	
	@ApiOperation(value = "retrieves all users follow up pages", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieved all user follow pages"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("{userId}/pages")
	public List<Page> getAllUserFollowPages(@PathVariable("tenantid") String tenantId,
			@PathVariable("userId") String userId) {
		 	User existingUser = userRepository.findById(UUID.fromString(userId)).get();
		 	Set<Page> followedPages = existingUser.getFollowingPages();
		 	List<Page> pageList = new ArrayList<>(followedPages);
		return pageList;
	}
	
	@GetMapping("/{userId}/favourite/pages")
	public ResponseEntity<?> getFavouritePages(@PathVariable("userId") String userId){
		List<PageResponse> pageResponses = userService.getFavouritePages(userId);
		return ResponseEntity.ok(pageResponses);
	}
	
	


}
