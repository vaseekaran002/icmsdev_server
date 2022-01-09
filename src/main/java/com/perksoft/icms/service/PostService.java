package com.perksoft.icms.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.perksoft.icms.models.Post;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.request.PostRequest;
import com.perksoft.icms.payload.response.PostResponse;
import com.perksoft.icms.repository.PostRepository;
import com.perksoft.icms.repository.UserRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private UserRepository userRepository;

	public Post updatePost(PostRequest postRequest) {

		Post post = new Post();

		if (postRequest.getCreatedBy() != null) {
			Optional<User> existingUser = userRepository.findById(postRequest.getCreatedBy());
			post.setCreatedBy(existingUser.get());
		}
		// File file = new File("C:\\Users\\Eswar P\\Pictures\\Camera Roll\\Mycam.jpg");
		// byte[] picInBytes = new byte[(int) file.length()];
		post.setId(postRequest.getPostId());
		post.setPostDescription(postRequest.getPostDescription());
		post.setPostTitle(postRequest.getPostTitle());
		post.setPostType(postRequest.getPostType());
		post.setStatus(postRequest.getStatus());
		post.setTotalDisLikes(postRequest.getTotalDisLikes());
		post.setTotalLikes(postRequest.getTotalLikes());
		post.setTenantId(postRequest.getTenantId());
		post.setPageId(postRequest.getPageId());
		post.setGroupId(postRequest.getGroupId());
		if (Objects.isNull(postRequest.getPostId())) {
			post.setCreatedTime(LocalDateTime.now());
			post.setUpdatedTime(LocalDateTime.now());
		} else {
			post.setCreatedTime(post.getCreatedTime());
			post.setUpdatedTime(LocalDateTime.now());
		}
		return postRepository.save(post);

	}

	
	
	public Post findPostById(Long postId) {
		return postRepository.findById(postId).get();
	}
	
	public List<PostResponse> getAllPostByPageId(String pageId,int from, int size){
		Page<Post> pageablePost = postRepository.findAllByPageId(
				PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "updatedTime")), UUID.fromString(pageId));
		List<PostResponse> postResponses = convertToPostResponse(pageablePost.toList());
		return postResponses;
	}
	
	public List<PostResponse> getAllPostByGroupId(Long groupId,int from, int size){
		Page<Post> pageablePost = postRepository.findAllByGroupId(
				PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "updatedTime")), groupId);
		List<PostResponse> postResponses = convertToPostResponse(pageablePost.toList());
		return postResponses;
	}

	public List<PostResponse> getAllPostByTenantId(String tenantId, int from, int size) {
		Page<Post> pageablePost = postRepository.findAllByTenantId(
				PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "updatedTime")), UUID.fromString(tenantId));
		List<PostResponse> postResponses = convertToPostResponse(pageablePost.toList());
		return postResponses;
	}

	private List<PostResponse> convertToPostResponse(List<Post> posts) {
		List<PostResponse> postResponses = new ArrayList<>();

		postResponses = posts.stream().map(p -> {
			PostResponse postResponse = new PostResponse();
			postResponse.setId(p.getId());
			postResponse.setDescription(p.getPostDescription());
			postResponse.setTitle(p.getPostTitle());
			postResponse.setType(p.getPostType());
			postResponse.setStatus(p.getStatus());
			postResponse.setTotalDisLikes(p.getTotalDisLikes());
			postResponse.setTotalLikes(p.getTotalLikes());
			postResponse.setCreatedTime(p.getCreatedTime());
			postResponse.setUpdatedTime(p.getUpdatedTime());
			postResponse.setUser(p.getCreatedBy());
			postResponse.setPageId(p.getPageId());
			postResponse.setGroupId(p.getGroupId());
			return postResponse;
		}).collect(Collectors.toList());
		return postResponses;
	}
}
