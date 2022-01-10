package com.perksoft.icms.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perksoft.icms.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
	
	Optional<User> findByUsername(String username);

	Optional<User> findByIdAndTenantId(UUID userId, UUID tenantId);

	Boolean existsByUsername(String username);

	List<User> findAllByTenantId(UUID tenantId);
	
	
}

