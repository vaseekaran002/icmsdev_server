package com.perksoft.icms.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.exception.IcmsCustomException;
import com.perksoft.icms.models.Tenant;
import com.perksoft.icms.payload.request.TenantRequest;
import com.perksoft.icms.repository.TenantRepository;
import com.perksoft.icms.service.TenantService;
import com.perksoft.icms.util.CommonUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TenantController {

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private TenantService tenantService;

	@Autowired
	private TenantRepository tenantRepository;

	@ApiOperation(value = "Used to fetch all active tenants", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates posts"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/tenant/list")
	public ResponseEntity<String> findAllActiveTenants() {
		log.info("Started fetching active tenants");
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					tenantService.findAllActiveTenants("active"));
		} catch (IcmsCustomException e) {
			log.info("Error occurred while fetching active tenants {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while fetching active tenants {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of  fetching active tenants and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "Used to creat/update tenants", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates posts"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/tenant")
	public ResponseEntity<String> addTenant(@RequestBody TenantRequest tenantRequest) {
		log.info("Started Creating/Updating tenants");
		ResponseEntity<String> responseEntity = null;

		try {
			Optional<Tenant> optionalTenant = tenantService.findByTenantname(tenantRequest.getName());

			if (!optionalTenant.isPresent()) {
				Tenant tenant = tenantService.addTenant(tenantRequest);
				responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
						tenant);
			} else {
				log.info("Error occurred while Creating/Updating tenant Tenant already exist {}", "");
				responseEntity = commonUtil.generateEntityResponse("Tenant already exist", Constants.FAILURE,
						Constants.FAILURE);
			}
		} catch (IcmsCustomException e) {
			log.info("Error occurred while Creating/Updating tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while Creating/Updating tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of  Creating/Updating tenant and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "Used to update tenants", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates posts"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/tenant/update")
	public ResponseEntity<String> updateTenant(@RequestBody TenantRequest tenantRequest) {
		log.info("Started Creating/Updating tenants");
		ResponseEntity<String> responseEntity = null;

		try {
			Tenant tenant = tenantService.findByTenantId(tenantRequest.getId());

			if (tenant != null) {
				tenant.setName(tenantRequest.getName());
				tenant.setDescription(tenantRequest.getDescription());
				tenant.setLogo(tenantRequest.getLogo());
				responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
						tenantRepository.save(tenant));
			} else {
				log.info("Error occurred while Creating/Updating tenant {}", "Tenant already exist");
				responseEntity = commonUtil.generateEntityResponse("Tenant already exist", Constants.FAILURE,
						Constants.FAILURE);
			}
		} catch (IcmsCustomException e) {
			log.info("Error occurred while Creating/Updating tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while Creating/Updating tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of  Creating/Updating tenant and response {}", responseEntity);
		return responseEntity;
	}

}
