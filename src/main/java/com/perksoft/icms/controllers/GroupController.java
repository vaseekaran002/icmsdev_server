package com.perksoft.icms.controllers;

import java.util.List;
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

import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.exception.IcmsCustomException;
import com.perksoft.icms.models.Group;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.request.GroupRequest;
import com.perksoft.icms.payload.response.GroupResponse;
import com.perksoft.icms.payload.response.UserResponse;
import com.perksoft.icms.service.GroupService;
import com.perksoft.icms.service.UserService;
import com.perksoft.icms.util.CommonUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantId}/group")
public class GroupController {

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private UserService userService;

	@Autowired
	private GroupService groupService;

	@ApiOperation(value = "Used to create/update group", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates group"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/create")
	public ResponseEntity<String> createGroup(@PathVariable("tenantId") String tenantId,
			@Valid @RequestBody GroupRequest groupRequest) {
		log.info("Started Creating/Updating Group for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;

		try {
			groupRequest.setTenantid(UUID.fromString(tenantId));
			GroupResponse groupResponse = groupService.addGroup(groupRequest);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					groupResponse);
		} catch (IcmsCustomException e) {
			log.info("Error occurred while Creating/Updating Group {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while Creating/Updating Group {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of Creating/Updating Group and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "retrieves all groups of given tenant id", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves group list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/list")
	public ResponseEntity<String> getAllGroups(@PathVariable("tenantId") String tenantId) {
		log.info("Started fetching Group for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;

		try {
			List<GroupResponse> groupResponse = groupService.fetchGroupsByTenantId(tenantId);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					groupResponse);
		} catch (IcmsCustomException e) {
			log.info("Error occurred while fetching Group {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while fetching Group {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of fetching Group and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "Inactivate the group", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Inactivated group successfully"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("{groupId}/delete")
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
	@PostMapping("{groupId}/member/update")
	public ResponseEntity<String> addMembers(@PathVariable("tenantId") String tenantId,
			@PathVariable("groupId") Long groupId, @RequestParam("userlist") List<String> userlist) {
		log.info("Started adding members to Group for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;

		try {
			Group group = groupService.getGroupById(groupId);
			group.setTenantId(UUID.fromString(tenantId));

			for (String userId : userlist) {
				User user = userService.getUserByIdAndTenantId(userId, tenantId);

				if (user != null) {
					group.getUsers().add(user);
				}
			}
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					groupService.saveGroup(group));
		} catch (IcmsCustomException e) {
			log.info("Error occurred while adding members to Group {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while adding members to Group {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of adding members to Group and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "retrieves all users of given group", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves user list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{groupid}/member/list")
	public ResponseEntity<String> getAllGroupUsers(@PathVariable("groupid") Long groupId) {
		log.info("Started fetching group members for Group {}", groupId);
		ResponseEntity<String> responseEntity = null;

		try {
			GroupResponse groupResponse = groupService.fetchGroupById(groupId);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					groupResponse);
		} catch (IcmsCustomException e) {
			log.info("Error occurred while fetching group members {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while fetching group members {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of fetching group members and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "retrieves a group by id", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves group"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{groupId}")
	public ResponseEntity<String> getGroupById(@PathVariable("groupId") Long groupId) {
		log.info("Started fetching  Group {}", groupId);
		ResponseEntity<String> responseEntity = null;

		try {
			GroupResponse groupResponse = groupService.fetchGroupById(groupId);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					groupResponse);
		} catch (IcmsCustomException e) {
			log.info("Error occurred while fetching group  {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while fetching group  {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of fetching group  and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "retrieves all groups created by user", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves group list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("{userId}/list")
	public ResponseEntity<String> getGroupsByCreatedBy(@PathVariable("userId") String createdBy) {
		log.info("Started fetching  Groups created by User {}", createdBy);
		ResponseEntity<String> responseEntity = null;

		try {
			List<GroupResponse> groupResponse = groupService.fetchGroupByCreatedBy(createdBy);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					groupResponse);
		} catch (IcmsCustomException e) {
			log.info("Error occurred while fetching  Groups created by User  {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while fetching  Groups created by User  {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of fetching fetching  Groups created by User  and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "retrieves all groups of given user", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves group list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{userId}/groups")
	public ResponseEntity<String> getUserGroupsByMembers(@PathVariable("tenantId") String tenantId,
			@PathVariable("userId") String userId) {
		log.info("Started fetching  Groups for User {}", userId);
		ResponseEntity<String> responseEntity = null;

		try {
			User user = userService.getUserByIdAndTenantId(userId, tenantId);
			UserResponse userResponse = userService.convertToUserResponse(user);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					userResponse);
		} catch (IcmsCustomException e) {
			log.info("Error occurred while fetching  Groups for User  {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while fetching  Groups for User  {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of fetching fetching  Groups for User  and response {}", responseEntity);
		return responseEntity;
	}

}
