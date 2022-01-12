package com.perksoft.icms.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.perksoft.icms.models.MetaData;
import com.perksoft.icms.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
	Optional<Role> findByName(String name);
	
	@Query("SELECT r FROM Role r WHERE r.name IN (:roles)")  
	List<Role> findByRolesIn(@Param(value = "roles") List<String> roles);

	@Query("SELECT r.metadata FROM Role r WHERE r.name IN (:roles)")
	List<MetaData> findByMetaDataIn(@Param(value = "roles") List<String> roles);
	
	List<Role> findAllByTenantId(UUID tenantId);
}
