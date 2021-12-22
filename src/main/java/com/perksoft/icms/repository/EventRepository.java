package com.perksoft.icms.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perksoft.icms.models.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	public List<Event> findAllByTenantId(UUID tenantId);
	
}
