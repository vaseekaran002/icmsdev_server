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

import com.perksoft.icms.payload.request.CommentRequest;
import com.perksoft.icms.payload.response.CommentResponse;
import com.perksoft.icms.payload.response.MessageResponse;
import com.perksoft.icms.service.CommentService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/comment")
@Api(value = "Comments service")
public class CommentController {

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
	public ResponseEntity<?> updateComment(@PathVariable("tenantid") String tenantId,
			@Valid @RequestBody CommentRequest commentRequest) {
		commentRequest.setTenantId(UUID.fromString(tenantId));
		commentService.updateComments(commentRequest);
		return ResponseEntity.ok(new MessageResponse("Comment updated successfully!"));
	}

	@ApiOperation(value = "Used to retrieve all comments for post", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates commnets"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{postid}/list")
	public ResponseEntity<?> getAllCommentsByPost(@PathVariable("tenantid") String tenantId,
			@PathVariable("postid") Long postId, @RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "10") int size) {
		List<CommentResponse> commentResponses = commentService.getAllCommentsByPostId(postId,
				UUID.fromString(tenantId), from, size);
		return ResponseEntity.ok(commentResponses);
	}

}
