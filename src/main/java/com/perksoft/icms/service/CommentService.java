package com.perksoft.icms.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.perksoft.icms.models.Comment;
import com.perksoft.icms.models.Post;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.request.CommentRequest;
import com.perksoft.icms.payload.response.CommentResponse;
import com.perksoft.icms.repository.CommentRepository;
import com.perksoft.icms.repository.UserRepository;

@Service
public class CommentService {

	@Autowired
	private CommentRepository commentRepository;

	@Autowired
	private PostService postService;

	@Autowired
	private UserRepository userRepository;

	public void updateComments(CommentRequest commentRequest) {
		Post post = postService.findPostById(commentRequest.getPostId());
		User user = userRepository.findById(commentRequest.getUserId()).get();
		Comment comment = new Comment();
		comment.setId(commentRequest.getCommentId());
		comment.setComments(commentRequest.getComments());
		comment.setPost(post);
		comment.setStatus(commentRequest.getStatus());
		comment.setTenantId(commentRequest.getTenantId());
		comment.setTotalDisLikes(commentRequest.getTotalDisLikes());
		comment.setTotalLikes(commentRequest.getTotalLikes());
		comment.setUser(user);
        comment.setPageId(commentRequest.getPageId());
		if (Objects.isNull(commentRequest.getPostId())) {
			comment.setCreatedTime(LocalDateTime.now());
			comment.setUpdatedTime(LocalDateTime.now());
		} else {
			comment.setCreatedTime(comment.getCreatedTime());
			comment.setUpdatedTime(LocalDateTime.now());
		}
		commentRepository.save(comment);
	}

	public List<CommentResponse> getAllCommentsByPostId(Long postId, UUID tenantId, int from, int size) {
		Post post = postService.findPostById(postId);
		Page<Comment> pageableComment = commentRepository.findAllCommentsByPost(post,
				PageRequest.of(from, size, Sort.by(Sort.Direction.DESC, "updatedTime")));
		List<CommentResponse> commentResponses = new ArrayList<>();

		if (pageableComment != null) {
			commentResponses = convertToCommentResponse(pageableComment.toList());
		}
		return commentResponses;
	}

	private List<CommentResponse> convertToCommentResponse(List<Comment> comments) {
		List<CommentResponse> commentResponses = new ArrayList<>();

		commentResponses = comments.stream().map(c -> {
			CommentResponse commentResponse = new CommentResponse();
			commentResponse.setId(c.getId());
			commentResponse.setComments(c.getComments());
			commentResponse.setStatus(c.getStatus());
			commentResponse.setTotalDisLikes(c.getTotalDisLikes());
			commentResponse.setTotalLikes(c.getTotalLikes());
			commentResponse.setCreatedTime(c.getCreatedTime());
			commentResponse.setUpdatedTime(c.getUpdatedTime());
			commentResponse.setUser(c.getUser());
			commentResponse.setPageId(c.getPageId());
			return commentResponse;
		}).collect(Collectors.toList());
		return commentResponses;
	}

}
