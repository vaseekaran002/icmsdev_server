package com.perksoft.icms.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.perksoft.icms.models.Tenant;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.request.TenantRequest;
import com.perksoft.icms.repository.TenantRepository;
import com.perksoft.icms.repository.UserRepository;

@Service
public class TenantService {

	@Autowired
	private TenantRepository tenantRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder encoder;

	public Tenant addTenant(TenantRequest tenantRequest) {
		Tenant tenant = new Tenant();
		tenant.setId(UUID.randomUUID());
		tenant.setName(tenantRequest.getName());
		tenant.setStatus(tenantRequest.getStatus());
		tenant.setDescription(tenantRequest.getDescription());
		tenant.setLogo(tenantRequest.getLogo());
		tenant.setFavIcon(tenantRequest.getFavIcon());

		User user = new User();
		user.setTenantId(tenant.getId());
		user.setEmail(StringUtils.trimWhitespace(tenantRequest.getName()) + "@" + tenantRequest.getName() + ".com");
		user.setUsername(StringUtils.trimWhitespace(tenantRequest.getName()));
		user.setPassword(encoder.encode(StringUtils.trimWhitespace(tenantRequest.getName())));
		// user.setRoles(roles);
		userRepository.save(user);
		return tenantRepository.save(tenant);
	}

	public Optional<Tenant> findByTenantname(String name) {
		return tenantRepository.findByName(name);
	}
	
	public Tenant findByTenantId(UUID tenantId) {
		Tenant tenant = null;
		Optional<Tenant> optionalTenant = tenantRepository.findById(tenantId);
		
		if(optionalTenant.isPresent()) {
			tenant = optionalTenant.get();
		}
		return tenant;
	}
	public List<Tenant> findAllActiveTenants(String status) {
		return tenantRepository.findAllByStatus(status);
	}

	public void updateTenant(Tenant tenant) {
		tenantRepository.save(tenant);
	}

	public void deleteById(UUID id) {
		tenantRepository.deleteById(id);
	}

}
