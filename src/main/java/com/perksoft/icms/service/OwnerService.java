package com.perksoft.icms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perksoft.icms.exception.IcmsCustomException;
import com.perksoft.icms.models.Owner;
import com.perksoft.icms.models.Page;
import com.perksoft.icms.payload.request.OwnerRequest;
import com.perksoft.icms.payload.response.OwnerResponse;
import com.perksoft.icms.repository.OwnerRepository;
import com.perksoft.icms.repository.PageRepository;

@Service
public class OwnerService {

	@Autowired
	private OwnerRepository ownerRepository;
	
	@Autowired
	private PageRepository pageRepository;
	
	public Owner updateOwners(OwnerRequest ownerRequest,UUID pageId){
		Optional<Page> existingPage = pageRepository.findById(pageId);
		if(existingPage.isEmpty()) {
			throw new IcmsCustomException("Page not Found");
		}
		Owner owner = new Owner();
		owner.setId(ownerRequest.getOwnerId());
		owner.setAddedBy(ownerRequest.getAddedBy());
		owner.setPageId(existingPage.get().getId());
		owner.setUserId(ownerRequest.getUserId());
		owner.setStatus(ownerRequest.getStatus());
		return ownerRepository.save(owner);
	}
	
	
	public List<OwnerResponse> getOwnersByPageId(String pageId){
		List<Owner> owners =  ownerRepository.findAllByPageId(UUID.fromString(pageId));
		return convertToOwnerResponse(owners);
	}
	
	
	
	public List<OwnerResponse> convertToOwnerResponse(List<Owner> owners){
		List<OwnerResponse> ownerResponses = new ArrayList<OwnerResponse>();
		ownerResponses = owners.stream().map(owner -> {
			OwnerResponse ownerResponse = new OwnerResponse();
			ownerResponse.setOwnerId(owner.getId());
			ownerResponse.setAddedBy(owner.getAddedBy());
			ownerResponse.setPageId(owner.getPageId());
			ownerResponse.setUserId(owner.getUserId());
			ownerResponse.setStatus(owner.getStatus());
			return ownerResponse;
		}).collect(Collectors.toList());
		return ownerResponses;
	}
	
	
	
	
}
