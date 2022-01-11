package com.perksoft.icms.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
import com.perksoft.icms.models.Owner;
import com.perksoft.icms.payload.request.OwnerRequest;
import com.perksoft.icms.payload.response.OwnerResponse;
import com.perksoft.icms.service.OwnerService;
import com.perksoft.icms.util.CommonUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/owners")
public class OwnerController {

	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private OwnerService ownerService;
	
	@ApiOperation(value = "Used to create/update owners", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updated owners"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/update/{pageId}")
	public ResponseEntity<String> updateOwners(@RequestBody List<OwnerRequest> ownerRequests,@PathVariable("pageId") String pageId){
		log.info("Started Creating/Updating owners for  {}", pageId);
		ResponseEntity<String> responseEntity = null;
		try {
			List<Owner> owners = new ArrayList<Owner>();
			for(OwnerRequest owner : ownerRequests) {
				owners.add(ownerService.updateOwners(owner, UUID.fromString(pageId)));
			}
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS, owners);
		}catch (IcmsCustomException e) {
			log.info("Error occurred while Creating/Updating owners {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while Creating/Updating owners {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of Creating/Updating owners and response {}", responseEntity);
		return responseEntity;
	}
	
	@ApiOperation(value = "Used to fetch owners", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully fetched owners"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/list/{pageId}")
	public ResponseEntity<String> getOwnersByPageId(@PathVariable("pageId") String pageId){
		log.info("Started fetching owners for  {}", pageId);
		ResponseEntity<String> responseEntity = null;
		try {
			List<OwnerResponse> owners = ownerService.getOwnersByPageId(pageId);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS, owners);
		}
		catch (IcmsCustomException e) {
			log.info("Error occurred while fetching owners {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			log.info("Error occurred while fetching owners {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		log.info("End of fetching owners and response {}", responseEntity);
		return responseEntity;
	}
	
}
