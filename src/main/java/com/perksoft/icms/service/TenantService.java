package com.perksoft.icms.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.perksoft.icms.models.ERole;
import com.perksoft.icms.models.Role;
import com.perksoft.icms.models.Tenant;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.request.TenantRequest;
import com.perksoft.icms.repository.RoleRepository;
import com.perksoft.icms.repository.TenantRepository;
import com.perksoft.icms.repository.UserRepository;

@Service
public class TenantService {

	@Autowired
	private TenantRepository tenantRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

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
		
		Set<Role> roles = new HashSet<>();
        for(Role role : tenantRequest.getRoles()) {
        	Role newRole = new Role();
			newRole.setId(role.getId());
			newRole.setName(role.getName());
			newRole.setRoleDescription(role.getRoleDescription());
			roleRepository.save(newRole);
			roles.add(newRole);
        }
        tenant.setRoles(roles);
		
//		Role modRole = roleRepository.findByName(ERole.TENANT_ADMIN)
//				.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
//		roles.add(modRole);

		User user = new User();
		user.setTenantId(tenant.getId());
		user.setEmail(StringUtils.trimWhitespace(tenantRequest.getName()) + "@"+tenantRequest.getName()+".com");
		user.setUsername(StringUtils.trimWhitespace(tenantRequest.getName()));
		user.setPassword(encoder.encode(StringUtils.trimWhitespace(tenantRequest.getName())));
		user.setRoles(roles);
	     userRepository.save(user);
	     return tenantRepository.save(tenant);
	}

	public Optional<Tenant> findByTenantname(String name) {
		return tenantRepository.findByName(name);
	}

	public List<Tenant> findAllActiveTenants(String status) {
		List<Tenant> tenants = tenantRepository.findAllByStatus(status);
		return tenants;
	}

	public void updateTenant(Tenant tenant) {
		tenantRepository.save(tenant);
	}

	public void deleteById(UUID id) {
		tenantRepository.deleteById(id);
	}

}
