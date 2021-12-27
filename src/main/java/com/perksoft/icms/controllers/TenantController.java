package com.perksoft.icms.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EnumType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.exception.IcmsCustomException;
import com.perksoft.icms.models.ERole;
import com.perksoft.icms.models.MetaData;
import com.perksoft.icms.models.Role;
import com.perksoft.icms.models.Tenant;
import com.perksoft.icms.payload.request.MetadataRequest;
import com.perksoft.icms.payload.request.TenantRequest;
import com.perksoft.icms.repository.MetaDataRepository;
import com.perksoft.icms.repository.RoleRepository;
import com.perksoft.icms.repository.TenantRepository;
import com.perksoft.icms.service.TenantService;
import com.perksoft.icms.util.CommonUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TenantController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private TenantService tenantService;
 
	@Autowired
	private TenantRepository tenantRepository;
	
	@Autowired
	private MetaDataRepository metadataRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@ApiOperation(value = "Used to fetch all active tenants", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates posts"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/tenant")
	public ResponseEntity<String> findAllActiveTenants() {
		LOGGER.info("Started fetching active tenants");
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					tenantService.findAllActiveTenants("active"));	
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while fetching active tenants {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while fetching active tenants {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of  fetching active tenants and response {}", responseEntity);
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
	public ResponseEntity<String> addTenant(@RequestBody TenantRequest tenantRequest) throws Exception {
		LOGGER.info("Started Creating/Updating tenants");
		ResponseEntity<String> responseEntity = null;
		try {
			Optional<Tenant> optionalTenant = tenantService.findByTenantname(tenantRequest.getName());
			if (!optionalTenant.isPresent()) {
				Tenant tenant = tenantService.addTenant(tenantRequest);
				responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
						tenant);	
			} else {
				LOGGER.info("Error occurred while Creating/Updating tenant {}", "Tenant already exist");
				responseEntity = commonUtil.generateEntityResponse("Tenant already exist", Constants.FAILURE, Constants.FAILURE);
			}
		}catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while Creating/Updating tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while Creating/Updating tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of  Creating/Updating tenant and response {}", responseEntity);
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
	public ResponseEntity<String> updateTenant(@RequestBody TenantRequest tenantRequest) throws Exception {
		LOGGER.info("Started Creating/Updating tenants");
		ResponseEntity<String> responseEntity = null;
		try {
			Optional<Tenant> optionalTenant = tenantRepository.findById(tenantRequest.getId());
			if (optionalTenant.isPresent()) {
				optionalTenant.get().setName(tenantRequest.getName());
				optionalTenant.get().setDescription(tenantRequest.getDescription());
				optionalTenant.get().setLogo(tenantRequest.getLogo());
				optionalTenant.get().setRoles(tenantRequest.getRoles());
				responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
						tenantRepository.save(optionalTenant.get()));	
				
			} else {
				LOGGER.info("Error occurred while Creating/Updating tenant {}", "Tenant already exist");
				responseEntity = commonUtil.generateEntityResponse("Tenant already exist", Constants.FAILURE, Constants.FAILURE);
			}
		}catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while Creating/Updating tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while Creating/Updating tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of  Creating/Updating tenant and response {}", responseEntity);
		return responseEntity;
	}
	
	
	@ApiOperation(value = "Used to map metadata for tenant", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates posts"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/tenant/{tenantid}/metadatamapping")
	public ResponseEntity<String> metadataMapping(@RequestBody List<MetadataRequest> metadataRequests,@PathVariable("tenantid") UUID tenantId) {
		LOGGER.info("Started mapping metadata for tenant {}",tenantId);
		ResponseEntity<String> responseEntity = null;
		try {
			Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
			Set<MetaData> metadatas = new HashSet<MetaData>();
			for (MetadataRequest meta : metadataRequests) {
				MetaData newMeta = new MetaData();
				newMeta.setId(meta.getId());
				newMeta.setComponentName(meta.getComponentName());
				newMeta.setComponentOrder(meta.getComponentOrder());
				newMeta.setDisplayName(meta.getDisplayName());
				newMeta.setStatus(meta.getStatus());
				Set<Role> roles = new HashSet<>();
				for(Role role : meta.getRoles()) {
					Role existingRole = roleRepository.findByName(role.getName()).get();
					roles.add(existingRole);
				}
				newMeta.setRoles(roles);
				metadataRepository.save(newMeta);
				metadatas.add(newMeta);
			}
			optionalTenant.get().setMetaData(metadatas);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					tenantRepository.save(optionalTenant.get()));	
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while mapping metadata for tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while mapping metadata for tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of  mapping metadata for tenant and response {}", responseEntity);
		return responseEntity;
		
	}
	
	@ApiOperation(value = "Used to fetch metadata for tenant", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates posts"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("{tenantid}/metadata")
	public ResponseEntity<String> getTenantMetadata(@PathVariable("tenantid") String tenantId) throws Exception {
		LOGGER.info("Started fetching metadata for tenant {}",tenantId);
		ResponseEntity<String> responseEntity = null;
		try {
			Optional<Tenant> optionalTenant = tenantRepository.findById(UUID.fromString(tenantId));
			if (optionalTenant.isPresent()) {
				responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
						optionalTenant.get().getMetaData());	
			} else {
				LOGGER.info("Error occurred while fetching metadata for tenant {}", "no metadata found");
				responseEntity = commonUtil.generateEntityResponse("no metadata found", Constants.FAILURE, Constants.FAILURE);
			}
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while fetching  metadata for tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while fetching metadata for tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of fetching metadata for tenant and response {}", responseEntity);
		return responseEntity;
	}
}
