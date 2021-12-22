package com.perksoft.icms.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perksoft.icms.models.Page;
import com.perksoft.icms.models.User;

@Repository
public interface PageRepository extends JpaRepository<Page, UUID> {

	public List<Page> findAllByTenantId(UUID tenantId);
	public List<Page> findAllByCreatedBy(User user);
}
