package com.perksoft.icms.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perksoft.icms.models.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, UUID> {

	public List<Tenant> findAllByStatus(String status);

	public Optional<Tenant> findByName(String name);

}
