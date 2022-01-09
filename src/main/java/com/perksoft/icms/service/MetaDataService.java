package com.perksoft.icms.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perksoft.icms.models.MetaData;
import com.perksoft.icms.repository.MetaDataRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MetaDataService {

	@Autowired
	private MetaDataRepository metaDataRepository;

	public List<MetaData> getAllMetaData() {
		return metaDataRepository.findAll();
	}

}
