package com.perksoft.icms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perksoft.icms.models.Group;
import com.perksoft.icms.models.MetaData;

@Repository
public interface MetaDataRepository extends JpaRepository<MetaData, Long> {
	public List<MetaData> findByRole(String role);
}
