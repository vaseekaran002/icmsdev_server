package com.perksoft.icms.controllers;

import java.util.List;
import java.util.UUID;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.contants.Constants;
import com.perksoft.icms.exception.IcmsCustomException;
import com.perksoft.icms.payload.request.CommentRequest;
import com.perksoft.icms.payload.response.CommentResponse;
import com.perksoft.icms.payload.response.MessageResponse;
import com.perksoft.icms.service.CommentService;
import com.perksoft.icms.util.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/comment")
@Api(value = "Comments service")
public class CommentController {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private CommonUtil commonUtil;

	@Autowired
	private CommentService commentService;

	@ApiOperation(value = "Used to update comments for post", response = ResponseEntity.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates commnets"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/update")
	public ResponseEntity<String> updateComment(@PathVariable("tenantid") String tenantId,
			@Valid @RequestBody CommentRequest commentRequest) {
		LOGGER.info("Started `creating comment for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;
		try{
			commentRequest.setTenantId(UUID.fromString(tenantId));
			commentService.updateComments(commentRequest);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					new MessageResponse("Comment updated successfully!"));
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while creating comment {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while creating comment {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of creating comment and response {}", responseEntity);
		return responseEntity;
		
	}

	@ApiOperation(value = "Used to retrieve all comments for post", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates commnets"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{postid}/list")
	public ResponseEntity<String> getAllCommentsByPost(@PathVariable("tenantid") String tenantId,
			@PathVariable("postid") Long postId, @RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "10") int size) {
		LOGGER.info("Started getting comments for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;
		try {
			List<CommentResponse> commentResponses = commentService.getAllCommentsByPostId(postId,
					UUID.fromString(tenantId), from, size);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					commentResponses);
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while fetching comment {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while fetching comment {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of fetching comment and response {}", responseEntity);
		return responseEntity;
	}

}
