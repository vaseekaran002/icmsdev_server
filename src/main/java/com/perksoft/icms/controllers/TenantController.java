package com.perksoft.icms.controllers;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import javax.persistence.EnumType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.perksoft.icms.models.ERole;
import com.perksoft.icms.models.MetaData;
import com.perksoft.icms.models.Role;
import com.perksoft.icms.models.Tenant;
import com.perksoft.icms.payload.request.MetadataRequest;
import com.perksoft.icms.payload.request.TenantRequest;
import com.perksoft.icms.repository.MetaDataRepository;
import com.perksoft.icms.repository.RoleRepository;
import com.perksoft.icms.repository.TenantRepository;
import com.perksoft.icms.service.TenantService;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class TenantController {

	@Autowired
	private TenantService tenantService;
 
	@Autowired
	private TenantRepository tenantRepository;
	
	@Autowired
	private MetaDataRepository metadataRepository;
	
	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping("/tenant")
	public List<Tenant> findAllActiveTenants() {
		return tenantService.findAllActiveTenants("active");
	}

	@PostMapping("/tenant")
	public ResponseEntity<?> addTenant(@RequestBody TenantRequest tenantRequest) throws Exception {
		Optional<Tenant> optionalTenant = tenantService.findByTenantname(tenantRequest.getName());

		if (!optionalTenant.isPresent()) {
			Tenant tenant = tenantService.addTenant(tenantRequest);
			return ResponseEntity.ok(tenant);
		} else {
			throw new Exception("Tenant already exist");
		}
	}
	
	@PostMapping("/tenant/update")
	public void updateTenant(@RequestBody TenantRequest tenantRequest) throws Exception {
		System.out.println(tenantRequest.getId());
		Optional<Tenant> optionalTenant = tenantRepository.findById(tenantRequest.getId());
		if (optionalTenant.isPresent()) {
			optionalTenant.get().setName(tenantRequest.getName());
			optionalTenant.get().setDescription(tenantRequest.getDescription());
			optionalTenant.get().setLogo(tenantRequest.getLogo());
			optionalTenant.get().setRoles(tenantRequest.getRoles());
			tenantRepository.save(optionalTenant.get());
		} else {
			throw new Exception("Tenant already exist");
		}
	}
	
	
	
	@PostMapping("/tenant/{tenantid}/metadatamapping")
	public Tenant metadataMapping(@RequestBody List<MetadataRequest> metadataRequests,@PathVariable("tenantid") UUID tenantId) {
		Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
		Set<MetaData> metadatas = new HashSet<MetaData>();
		for (MetadataRequest meta : metadataRequests) {
			MetaData newMeta = new MetaData();
			newMeta.setId(meta.getId());
			newMeta.setComponentName(meta.getComponentName());
			newMeta.setComponentOrder(meta.getComponentOrder());
			newMeta.setDisplayName(meta.getDisplayName());
			newMeta.setStatus(meta.getStatus());
			Set<Role> roles = new HashSet<>();
			for(Role role : meta.getRoles()) {
				Role existingRole = roleRepository.findByName(role.getName()).get();
				roles.add(existingRole);
			}
			newMeta.setRoles(roles);
			metadataRepository.save(newMeta);
			metadatas.add(newMeta);
		}
		optionalTenant.get().setMetaData(metadatas);
		return tenantRepository.save(optionalTenant.get());
		
	}
	
	@GetMapping("{tenantid}/metadata")
	public Set<MetaData> getTenantMetadata(@PathVariable("tenantid") String tenantId) throws Exception {
		Optional<Tenant> optionalTenant = tenantRepository.findById(UUID.fromString(tenantId));
		if (optionalTenant.isPresent()) {
			return optionalTenant.get().getMetaData();
		} else {
			throw new Exception("no metadata found");
		}
	}
}
