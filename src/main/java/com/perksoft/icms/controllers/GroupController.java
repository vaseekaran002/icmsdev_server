package com.perksoft.icms.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.validation.Valid;

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

import com.perksoft.icms.models.Group;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.request.GroupRequest;
import com.perksoft.icms.payload.response.GroupResponse;
import com.perksoft.icms.payload.response.MessageResponse;
import com.perksoft.icms.payload.response.UserResponse;
import com.perksoft.icms.repository.GroupRepository;
import com.perksoft.icms.repository.UserRepository;
import com.perksoft.icms.service.GroupService;
import com.perksoft.icms.service.UserService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/group")
public class GroupController {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserService userService;

	@Autowired
	private GroupRepository groupRepository;

	@Autowired
	private GroupService groupService;

	@ApiOperation(value = "Used to create/update group", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates group"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/update")
	public ResponseEntity<?> createGroup(@PathVariable("tenantid") String tenantId,
			@Valid @RequestBody GroupRequest groupRequest) {
		groupRequest.setTenantid(UUID.fromString(tenantId));
		GroupResponse groupResponse = groupService.addGroup(groupRequest);
		return ResponseEntity.ok(groupResponse);
	}

	@ApiOperation(value = "retrieves all groups of given tenant id", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves group list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/list")
	public ResponseEntity<?> getAllGroups(@PathVariable("tenantid") String tenantId) {
		List<GroupResponse> groupResponse = groupService.fetchGroupsByTenantId(tenantId);
		return ResponseEntity.ok(groupResponse);
	}

	public void deleteGroup(Long id) {
		groupService.deleteGroup(id);
	}

	@ApiOperation(value = "adding members to group", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully members added to group"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/member/update")
	public ResponseEntity<MessageResponse> addMembers(@PathVariable("tenantid") String tenantId,
			@RequestBody Group group, @RequestParam("userlist") List<String> userlist) {
		Optional<Group> existingGroup = groupRepository.findById(group.getId());
		existingGroup.get().setTenantId(UUID.fromString(tenantId));

		for (String i : userlist) {
			Optional<User> existingUser = userRepository.findById(UUID.fromString(i));
			if(groupService.isUserPresent(existingGroup.get(), existingUser.get().getId()) == false) {
				existingGroup.get().getUsers().add(existingUser.get());
			}
			else {
				return ResponseEntity.ok(new MessageResponse("Member already exist!!"));
			}
		}
		groupRepository.save(existingGroup.get());
		return ResponseEntity.ok(new MessageResponse("Members added successfully!"));
	}

	@ApiOperation(value = "retrieves all users of given group", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves user list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{groupid}/member/list")
	public ResponseEntity<?> getAllGroupUsers(@PathVariable("groupid") Long groupId) {
		GroupResponse groupResponse = groupService.fetchGroupById(groupId);
		return ResponseEntity.ok(groupResponse.getUsers());
	}
	
	@ApiOperation(value = "retrieves a group", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves user list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{groupid}")
	public ResponseEntity<?> getGroupById(@PathVariable("groupid") Long groupId){
		GroupResponse groupResponse = groupService.fetchGroupById(groupId);
		return ResponseEntity.ok(groupResponse);
	}
	
	@GetMapping("{userId}/list")
	public ResponseEntity<?> getGroupsByCreatedBy(@PathVariable("userId") String createdBy){
		List<GroupResponse> groupResponse = groupService.fetchGroupByCreatedBy(createdBy);
		return ResponseEntity.ok(groupResponse);
	}
	
	@ApiOperation(value = "retrieves all groups  of given user", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves group list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{userid}/added")
	public ResponseEntity<?> getUserGroupsByMembers(@PathVariable("tenantid") String tenantId,
			@PathVariable("userid") String userId) {
		UserResponse userResponse = userService.getUserById(UUID.fromString(userId), UUID.fromString(tenantId));
		return ResponseEntity.ok(userResponse.getGroups());
	}
	
}
