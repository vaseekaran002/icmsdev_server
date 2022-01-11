package com.perksoft.icms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perksoft.icms.models.JwtBlacklist;

@Repository
public interface JwtTokenRepository extends JpaRepository<JwtBlacklist, Long> {
	
	public JwtBlacklist findByToken(String token);
}	
