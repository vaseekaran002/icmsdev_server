package com.perksoft.icms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.perksoft.icms.models.MetaData;

@Repository
public interface MetaDataRepository extends JpaRepository<MetaData, Long> {
}
