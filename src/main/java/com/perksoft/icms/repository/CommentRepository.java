package com.perksoft.icms.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.perksoft.icms.models.Comment;
import com.perksoft.icms.models.Post;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment, Long> {

	Page<Comment> findAllCommentsByPost(Post post, Pageable pageable);

}
