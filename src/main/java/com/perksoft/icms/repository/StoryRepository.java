package com.perksoft.icms.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perksoft.icms.models.Story;

@Repository
public interface StoryRepository extends JpaRepository<Story, Long> {

	public List<Story> findAllByPageId(UUID pageId);
}
