package com.perksoft.icms.controllers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.exception.IcmsCustomException;
import com.perksoft.icms.models.Page;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.request.PageFollowerRequest;
import com.perksoft.icms.payload.request.PageRequest;
import com.perksoft.icms.payload.response.PageResponse;
import com.perksoft.icms.repository.PageRepository;
import com.perksoft.icms.service.PageService;
import com.perksoft.icms.util.CommonUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/pages")
public class PageController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	public PageService pageService;

	@Autowired
	public PageRepository pageRepository;
	
	@ApiOperation(value = "Used to create/update page", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates group"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/update")
	public ResponseEntity<String> updatePage(@PathVariable("tenantid") String tenantId,
			@RequestBody PageRequest pageRequest) {
		LOGGER.info("Started Creating/Updating page for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;
		try {
			pageRequest.setTenantId(UUID.fromString(tenantId));
			PageResponse pageResponse = pageService.updatePage(pageRequest);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					pageResponse);
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while Creating/Updating page {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while Creating/Updating page {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of Creating/Updating page and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "Used to fetch Page by Id", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates group"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{pageId}")
	public ResponseEntity<String> getPageById(@PathVariable("pageId") String pageId) {
		LOGGER.info("Started fetching Page by Id  {}", pageId);
		ResponseEntity<String> responseEntity = null;
		try {
			PageResponse pageResponse = pageService.getPageById(pageId);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					pageResponse);
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while fetching page by Id {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while fetching page by Id {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of fetching page by Id and response {}", responseEntity);
		return responseEntity;
	}
	
	@ApiOperation(value = "Used to fetch Page by tenant Id", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates group"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/list")
	public ResponseEntity<String> getPagesByTenantId(@PathVariable("tenantid") String tenantId) {
		LOGGER.info("Started fetching page for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;
		try {
			List<PageResponse> pageResponses = pageService.getAllPageByTenantId(tenantId);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					pageResponses);
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while fetching page by tenant Id {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while fetching page by tenant Id {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of fetching page by Tenant id and response {}", responseEntity);
		return responseEntity;
	}
	
	@ApiOperation(value = "Used to fetch Page by user Id", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates group"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{userId}/list")
	public ResponseEntity<String> getPagesByUserId(@PathVariable(name = "userId") String userId) {
		LOGGER.info("Started fetching page by user Id {}", userId);
		ResponseEntity<String> responseEntity = null;
		try {
			List<PageResponse> pageResponses = pageService.getAllPageByUserId(userId);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					pageResponses);
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while fetching page  by user Id {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while fetching page  by user Id {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of fetching page by user Id and response {}", responseEntity);
		return responseEntity;
	}
	
	@ApiOperation(value = "Used to create follow request", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates group"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/{pageId}/followers/update")
	public ResponseEntity<?> followRequest(@PathVariable("pageId") String pageId,@RequestBody PageFollowerRequest pageFollowerRequest) {
		LOGGER.info("Started creating follow request for page {}", pageId);
		ResponseEntity<String> responseEntity = null;
		try {
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					pageService.followerRequest(pageId, pageFollowerRequest));
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while creating follow request {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while creating follow request {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of creating follow request for page and response {}", responseEntity);
		return responseEntity;
	}
	
	@ApiOperation(value = "Used to fetch top followers", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates group"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{pageId}/followers")
	public ResponseEntity<String> getTopFollwers(@PathVariable("pageId") String pageId) {
		LOGGER.info("Started fetching top users for page {}", pageId);
		ResponseEntity<String> responseEntity = null;
		try {
			List<User> topUsers = new ArrayList<User>();
			Optional<Page> existingPage = pageRepository.findById(UUID.fromString(pageId));
			Iterator<User> iter = existingPage.get().getFollowers().iterator();
			int i = 0;

			while (i < 10 && iter.hasNext()) {
				topUsers.add(iter.next());
				i++;
			}
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					topUsers);
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while fetching top users for page {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while fetching top users for page {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of creating ffetching top users for page and response {}", responseEntity);
		return responseEntity;
	}
	
	

}
