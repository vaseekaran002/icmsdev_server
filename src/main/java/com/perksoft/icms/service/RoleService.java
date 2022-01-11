package com.perksoft.icms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perksoft.icms.models.ERole;
import com.perksoft.icms.models.Role;
import com.perksoft.icms.payload.request.RoleRequest;
import com.perksoft.icms.payload.response.RoleResponse;
import com.perksoft.icms.repository.RoleRepository;

@Service
public class RoleService {
	
	@Autowired
	private RoleRepository roleRepository;
	

	public Role updateRole(RoleRequest roleRequest) {
		Role role = new Role();
		role.setId(roleRequest.getRoleId());
		role.setName(ERole.valueOf(roleRequest.getName()));
		role.setRoleDescription(roleRequest.getDescription());
		role.setStatus(roleRequest.getStatus());
		role.setTenantId(UUID.fromString(roleRequest.getTenantId()));
		return roleRepository.save(role);
	}
	
	public List<RoleResponse> getAllRoles(String tenantId){
		List<Role> roles = roleRepository.findAllByTenantId(UUID.fromString(tenantId));
		return convertToRoleResponse(roles);
	}
	
	public List<RoleResponse> convertToRoleResponse(List<Role> roles){
		List<RoleResponse> roleResponses = new ArrayList<RoleResponse>();
		roleResponses = roles.stream().map(role -> {
			RoleResponse roleResponse = new RoleResponse();
			roleResponse.setRoleId(role.getId());
			roleResponse.setName(role.getName().toString());
			roleResponse.setDescription(role.getRoleDescription());
			roleResponse.setStatus(role.getStatus());
			roleResponse.setTenantId(role.getTenantId().toString());
			return roleResponse;
		}).collect(Collectors.toList());
		return roleResponses;
	}
	

}
