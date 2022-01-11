package com.perksoft.icms.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perksoft.icms.models.ERole;
import com.perksoft.icms.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {
  Optional<Role> findByName(ERole name);
  List<Role> findAllByTenantId(UUID tenantId);
}
