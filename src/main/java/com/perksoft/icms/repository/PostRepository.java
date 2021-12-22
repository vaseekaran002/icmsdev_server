package com.perksoft.icms.repository;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import com.perksoft.icms.models.Post;

@Repository
public interface PostRepository extends PagingAndSortingRepository<Post, Long>{
	
	Page<Post> findAllByTenantId(Pageable pageable, UUID tenantId);
	
	Page<Post> findAllByPageId(Pageable pageable,UUID pageId);
	
	Page<Post> findAllByGroupId(Pageable pageable,Long groupId);
	
}
