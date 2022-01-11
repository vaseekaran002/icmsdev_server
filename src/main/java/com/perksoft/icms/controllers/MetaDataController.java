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
import com.perksoft.icms.models.MetaData;
import com.perksoft.icms.payload.request.MetadataRequest;
import com.perksoft.icms.service.MetaDataService;
import com.perksoft.icms.util.CommonUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/metadata")
public class MetaDataController {

	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private MetaDataService metaDataService;
	
	@ApiOperation(value = "used to create/update metadata", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated metadata"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/update")
	public ResponseEntity<String> updateMetadata(@RequestBody MetadataRequest metadataRequest,@PathVariable("tenantid") String tenantId){
		log.info("Started creating/updating  metadatas");
		ResponseEntity<String> responseEntity = null;
		try {
			metadataRequest.setTenantid(tenantId);
			MetaData metadata = metaDataService.updateMetadata(metadataRequest);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,metadata);
		}
		catch (IcmsCustomException e) {
			log.info("Error occurred while creating/updating metadatas {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while creating/updating metadatas {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of creating/updating metadatas and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "retrieves all metadata", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates posts"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/list")
	public ResponseEntity<String> getAllMetaData() {
		log.info("Started fetching all metadatas");
		ResponseEntity<String> responseEntity = null;

		try {
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					metaDataService.getAllMetaData());
		} catch (IcmsCustomException e) {
			log.info("Error occurred while fetching all metadatas {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while fetching all metadatas {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of fetching all metadatas and response {}", responseEntity);
		return responseEntity;
	}
}
