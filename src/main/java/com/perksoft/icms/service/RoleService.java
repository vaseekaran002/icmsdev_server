package com.perksoft.icms.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perksoft.icms.models.MetaData;
import com.perksoft.icms.models.Role;
import com.perksoft.icms.payload.request.RoleRequest;
import com.perksoft.icms.payload.response.RoleResponse;
import com.perksoft.icms.repository.RoleRepository;

@Service
public class RoleService {

	@Autowired
	private RoleRepository roleRepository;

	public Optional<Role> getRoleByName(String roleName) {
		return roleRepository.findByName(roleName);
	}

	public List<Role> getRolesByRoleNamesIn(List<String> roles) {
		return roleRepository.findByRolesIn(roles);
	}
	
	public List<MetaData> getMetatdataByRoleNames(List<String> roles){
		return roleRepository.findByMetaDataIn(roles);
	}

	public Role updateRole(RoleRequest roleRequest) {
		Role role = new Role();
		role.setId(roleRequest.getId());
		role.setName(roleRequest.getName());
		role.setRoleDescription(roleRequest.getDescription());
		role.setStatus(roleRequest.getStatus());
		role.setTenantId(UUID.fromString(roleRequest.getTenantId()));
		return roleRepository.save(role);
	}

	public List<RoleResponse> getAllRoles(String tenantId) {
		List<Role> roles = roleRepository.findAllByTenantId(UUID.fromString(tenantId));
		return convertToRoleResponse(roles);
	}

	public List<RoleResponse> convertToRoleResponse(List<Role> roles) {

		return roles.stream().map(role -> {
			RoleResponse roleResponse = new RoleResponse();
			roleResponse.setId(role.getId());
			roleResponse.setName(role.getName());
			roleResponse.setDescription(role.getRoleDescription());
			roleResponse.setStatus(role.getStatus());
			roleResponse.setTenantId(role.getTenantId().toString());
			return roleResponse;
		}).collect(Collectors.toList());
	}
}
