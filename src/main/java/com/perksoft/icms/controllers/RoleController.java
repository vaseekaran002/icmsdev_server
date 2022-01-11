package com.perksoft.icms.controllers;

import java.util.List;

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
import com.perksoft.icms.models.Role;
import com.perksoft.icms.payload.request.RoleRequest;
import com.perksoft.icms.payload.response.RoleResponse;
import com.perksoft.icms.service.RoleService;
import com.perksoft.icms.util.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/role")
@Api(value = "Role service")
public class RoleController {
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private RoleService roleService;
	
	@ApiOperation(value = "Used to update role", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates role"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/update")
	public ResponseEntity<String> updateRole(@RequestBody RoleRequest roleRequest,@PathVariable("tenantid") String tenantId){
		log.info("Started Creating/Updating post for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;
		try {
			roleRequest.setTenantId(tenantId);
			Role role = roleService.updateRole(roleRequest);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS, role);
		}
		catch (IcmsCustomException e) {
			log.info("Error occurred while Creating/Updating role {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while Creating/Updating role {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of Creating/Updating role and response {}", responseEntity);
		return responseEntity;
	}
	
	
	@ApiOperation(value = "Used to fetch all role", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetches roles"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/list")
	public ResponseEntity<String> getRolesByTenantId(@PathVariable("tenantid") String tenantId){
		log.info("Started fetching post for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;
		try {
			List<RoleResponse> roles = roleService.getAllRoles(tenantId);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS, roles);
		}
		catch (IcmsCustomException e) {
			log.info("Error occurred while fetching role {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while fetching role {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of fetching role and response {}", responseEntity);
		return responseEntity;
	}

}
