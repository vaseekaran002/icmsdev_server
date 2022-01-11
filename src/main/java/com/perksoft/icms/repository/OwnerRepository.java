package com.perksoft.icms.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perksoft.icms.models.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

	List<Owner> findAllByPageId(UUID pageId);
}
