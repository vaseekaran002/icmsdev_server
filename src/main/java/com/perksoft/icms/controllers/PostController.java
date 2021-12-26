package com.perksoft.icms.controllers;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import com.perksoft.icms.models.Post;
import com.perksoft.icms.payload.request.PostRequest;
import com.perksoft.icms.payload.response.MessageResponse;
import com.perksoft.icms.payload.response.PostResponse;
import com.perksoft.icms.repository.PostRepository;
import com.perksoft.icms.service.PostService;
import com.perksoft.icms.util.CommonUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/{tenantid}/post")
@Api(value = "Posts service")
public class PostController {

	private static final Logger LOGGER=LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private CommonUtil commonUtil;
	
	@Autowired
	private PostService postService;

	@Autowired
	private PostRepository postRepository;
	
	@ApiOperation(value = "Used to update post", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully updates posts"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@PostMapping("/update")
	public ResponseEntity<String> updatePost(@PathVariable("tenantid") String tenantId,
			@Valid @RequestBody PostRequest postRequest) {
		LOGGER.info("Started Creating/Updating post for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;
		try {
			postRequest.setTenantId(UUID.fromString(tenantId));
			Post post = postService.updatePost(postRequest);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					post);
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while Creating/Updating post {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while Creating/Updating post {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of Creating/Updating post and response {}", responseEntity);
		return responseEntity;
	}

	@ApiOperation(value = "retrieves all post of given tenant it", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves post list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/list")
	public ResponseEntity<?> getAllPost(@PathVariable("tenantid") String tenantId,
			@RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "10") int size) {
		LOGGER.info("Started fetching all posts for tenant {}", tenantId);
		ResponseEntity<String> responseEntity = null;
		try {
			List<PostResponse> postResponses = postService.getAllPostByTenantId(tenantId, from, size);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					postResponses);
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while  fetching all posts for tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while  fetching all posts for tenant {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of  fetching all posts and response {}", responseEntity);
		return responseEntity;
	}
	
//	@GetMapping("/{postId}/media")
//	public ResponseEntity<?> getpostMedia(@PathVariable("postId") Long postId){
//		Optional<Post> existingPost = postRepository.findById(postId);
//		InputStream postMedia = new ByteArrayInputStream(existingPost.get().getPostDescription());
//		InputStreamResource inputStreamResource = new InputStreamResource(postMedia);
//	    HttpHeaders headers = new HttpHeaders();
//	    headers.setContentLength(existingPost.get().getPostDescription().length);
//	    return new ResponseEntity(inputStreamResource, headers, HttpStatus.OK);
//	}

	@ApiOperation(value = "retrieves all post of given page Id", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves post list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/{pageId}/list")
	public ResponseEntity<?> getAllPostByPageId(@PathVariable("pageId") String pageId,
			@RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "10") int size){
		LOGGER.info("Started fetching all posts for page id {}", pageId);
		ResponseEntity<String> responseEntity = null;
		try {
			List<PostResponse> postResponses = postService.getAllPostByPageId(pageId, from, size);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					postResponses);	
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while  fetching all posts for page {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while  fetching all posts for page {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of  fetching all posts for page and response {}", responseEntity);
		return responseEntity;
	}
	
	@ApiOperation(value = "retrieves all post of given group Id", response = List.class)
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successfully retrieves post list"),
			@ApiResponse(code = 401, message = "You are not authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
			@ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
			@ApiResponse(code = 409, message = "Business validaiton error occured"),
			@ApiResponse(code = 500, message = "Execepion occured while executing api service") })
	@GetMapping("/group/{groupId}/list")
	public ResponseEntity<?> getAllPostByGroupId(@PathVariable("groupId") Long groupId,
			@RequestParam(defaultValue = "0") int from, @RequestParam(defaultValue = "10") int size){
		LOGGER.info("Started fetching all posts for group id {}", groupId);
		ResponseEntity<String> responseEntity = null;
		try {
			List<PostResponse> postResponses = postService.getAllPostByGroupId(groupId, from, size);
			responseEntity = commonUtil.generateEntityResponse(Constants.SUCCESS_MESSAGE, Constants.SUCCESS,
					postResponses);	
		}
		catch(IcmsCustomException e) {
			LOGGER.info("Error occurred while  fetching all posts for group {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.FAILURE, Constants.FAILURE);
		} catch (Exception e) {
			LOGGER.info("Error occurred while  fetching all posts for group {}", e.getMessage());
			responseEntity = commonUtil.generateEntityResponse(e.getMessage(), Constants.EXCEPTION,
					Constants.EXCEPTION);
		}
		LOGGER.info("End of  fetching all posts for group and response {}", responseEntity);
		return responseEntity;
	}
	
}
