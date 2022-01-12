package com.perksoft.icms.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perksoft.icms.models.MetaData;
import com.perksoft.icms.models.Role;
import com.perksoft.icms.payload.request.MetadataRequest;
import com.perksoft.icms.payload.response.MetadataResponse;
import com.perksoft.icms.repository.MetaDataRepository;

@Service
public class MetaDataService {

	@Autowired
	private MetaDataRepository metaDataRepository;

	@Autowired
	private RoleService roleService;

	public List<MetadataResponse> getAllMetaData() {
		List<MetaData> metadatas = metaDataRepository.findAll();
		return convertToMetadataResponse(metadatas);
	}

	public MetaData updateMetadata(MetadataRequest metadataRequest) {
		MetaData metadata = new MetaData();
		metadata.setId(metadataRequest.getId());
		metadata.setComponentName(metadataRequest.getComponentName());
		metadata.setComponentOrder(metadataRequest.getComponentOrder());
		metadata.setDisplayName(metadataRequest.getDisplayName());
		metadata.setStatus(metadataRequest.getStatus());
		metadata.setTenantId(UUID.fromString(metadataRequest.getTenantid()));
		Set<Role> roles = new HashSet<>();

		for (String role : metadataRequest.getRoles()) {
			Optional<Role> optionalRole = roleService.getRoleByName(role);

			if (optionalRole.isPresent()) {
				roles.add(optionalRole.get());
			}
		}
		metadata.setRoles(roles);
		return metaDataRepository.save(metadata);
	}

	public List<MetadataResponse> convertToMetadataResponse(List<MetaData> metadatas) {

		return metadatas.stream().map(meta -> {
			MetadataResponse metadataResponse = new MetadataResponse();
			metadataResponse.setId(meta.getId());
			metadataResponse.setComponentName(meta.getComponentName());
			metadataResponse.setComponentOrder(meta.getComponentOrder());
			metadataResponse.setDisplayName(meta.getDisplayName());
			metadataResponse.setStatus(meta.getStatus());
			metadataResponse.setRoles(meta.getRoles());
			metadataResponse.setTenantId(meta.getTenantId());
			return metadataResponse;
		}).collect(Collectors.toList());
	}
}
