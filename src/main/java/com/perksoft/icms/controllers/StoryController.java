package com.perksoft.icms.controllers;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.exception.IcmsCustomException;
import com.perksoft.icms.models.Story;
import com.perksoft.icms.payload.request.StoryRequest;
import com.perksoft.icms.payload.response.MessageResponse;
import com.perksoft.icms.payload.response.StoryResponse;
import com.perksoft.icms.repository.StoryRepository;
import com.perksoft.icms.service.StoryService;
import com.perksoft.icms.util.CommonUtil;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/story") 
public class StoryController {
	
    private static final Logger LOGGER=LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	public StoryRepository storyRepository;
	
	@Autowired
	public StoryService storyService;
	
	@ApiOperation(value = "Used to update story", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates posts"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/update")
	public ResponseEntity<String> updateStory(@RequestBody StoryRequest storyRequest){
		LOGGER.info("Started Creating/Updating story");
		ResponseEntity<String> responseEntity = null;
		try {
			Story story =  storyService.createStory(storyRequest);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					story);	
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while Creating/Updating story {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while Creating/Updating story {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of  Creating/Updating story and response {}", responseEntity);
		return responseEntity;
	}
	
	@ApiOperation(value = "Used to fetch story", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates posts"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/list")
	public ResponseEntity<String> getAllStoriesByPageId(@RequestParam(value = "pageId") String pageId){
		LOGGER.info("Started fetching story");
		ResponseEntity<String> responseEntity = null;
		try {
			List<StoryResponse> storyResponses =  storyService.getStroiesByPageId(pageId);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					storyResponses);	
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while fetching story {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while fetching story {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of  fetching story and response {}", responseEntity);
		return responseEntity;
	}
	
}
