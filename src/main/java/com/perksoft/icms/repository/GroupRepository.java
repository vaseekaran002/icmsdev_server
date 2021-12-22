package com.perksoft.icms.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.perksoft.icms.models.Group;

public interface GroupRepository extends JpaRepository<Group, Long> {

	public Optional<Group>  findByName(String name);
	
	public List<Group> findAllByStatusAndTenantId(String status, UUID teanantId);
	
	public List<Group> findAllByCreatedBy(UUID createdBy);
}
