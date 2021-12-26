package com.perksoft.icms.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.exception.IcmsCustomException;
import com.perksoft.icms.models.MetaData;
import com.perksoft.icms.models.Tenant;
import com.perksoft.icms.repository.MetaDataRepository;
import com.perksoft.icms.repository.TenantRepository;
import com.perksoft.icms.util.CommonUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/metadata")
public class MetaDataController {
	
	
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private MetaDataRepository metaDataRepository;
	
	@Autowired
	private TenantRepository tenantRepository;
	
	@ApiOperation(value = "retrieves all metadatas", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates posts"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/list")
	public ResponseEntity<String> getAllMetaData(){
		LOGGER.info("Started fetching all metadatas");
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					metaDataRepository.findAll());
		}
		 catch (IcmsCustomException e) {
				LOGGER.info("Error occurred while fetching all metadatas {}", e.getMessage());
				responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
			} catch (Exception e) {
				LOGGER.info("Error occurred while fetching all metadatas {}", e.getMessage());
				responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
						Constants.EXCEPTION);
			}
			LOGGER.info("End of fetching all metadatas and response {}", responseEntity);
			return responseEntity;
	}
	
//	@ApiOperation(value = "retrieves  metadatas", response = List.class)
//	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates posts"),
//			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
//			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
//			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
//			@ApiResponse(code = 409, message = "Business validaiton error occured"),
//			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
//	@GetMapping("/list/{tenantid}/{roleId}")
//	public Set<MetaData> getMetadata(@PathVariable("tenantid") String tenantId,@PathVariable("roleId") String roleId) {
//		Optional<Tenant> optionalTenant = tenantRepository.findById(UUID.fromString(tenantId));
//		Set<MetaData> metadataList =  optionalTenant.get().getMetaData();
//		List<MetaData> roleMetadata = metaDataRepository.findByRole(roleId);
//		metadataList.addAll(roleMetadata);
//		return metadataList;
//	}
}
