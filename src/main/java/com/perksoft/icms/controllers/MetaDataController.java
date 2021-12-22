package com.perksoft.icms.controllers;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.models.MetaData;
import com.perksoft.icms.models.Tenant;
import com.perksoft.icms.repository.MetaDataRepository;
import com.perksoft.icms.repository.TenantRepository;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/metadata")
public class MetaDataController {
	
	@Autowired
	private MetaDataRepository metaDataRepository;
	
	@Autowired
	private TenantRepository tenantRepository;
	
	@GetMapping("/list")
	public List<MetaData> getAllMetaData(){
		return metaDataRepository.findAll();
	}
	
	@GetMapping("/list/{tenantid}/{roleId}")
	public Set<MetaData> getMetadata(@PathVariable("tenantid") String tenantId,@PathVariable("roleId") String roleId) {
		Optional<Tenant> optionalTenant = tenantRepository.findById(UUID.fromString(tenantId));
		Set<MetaData> metadataList =  optionalTenant.get().getMetaData();
		List<MetaData> roleMetadata = metaDataRepository.findByRole(roleId);
		metadataList.addAll(roleMetadata);
		return metadataList;
	}
}
