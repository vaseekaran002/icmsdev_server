package com.perksoft.icms.service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perksoft.icms.models.Group;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.request.GroupRequest;
import com.perksoft.icms.payload.response.GroupResponse;
import com.perksoft.icms.repository.GroupRepository;

@Service
public class GroupService {

	@Autowired
	private GroupRepository groupRepository;
	

	public GroupResponse addGroup(GroupRequest groupRequest) {
		Group group = new Group();
		group.setName(groupRequest.getName());
		group.setStatus(groupRequest.getStatus());
		group.setDescription(groupRequest.getDescription());
		group.setTenantId(groupRequest.getTenantid());
		group.setGroupImage(groupRequest.getGroupImage());
		group.setCreatedBy(groupRequest.getCreatedBy());
		groupRepository.save(group);
		return fetchGroupById(group.getId());
	}

	public void deleteById(Long id) {
		groupRepository.deleteById(id);
	}

	public List<GroupResponse> fetchGroupsByTenantId(String tenantId) {
		List<Group> groups = groupRepository.findAllByStatusAndTenantId("active", UUID.fromString(tenantId));
		List<GroupResponse> groupResponse = convertToGroupResponse(groups);
		return groupResponse;
	}
	
	public List<GroupResponse> fetchGroupByCreatedBy(String createdBy){
		List<Group> groups = groupRepository.findAllByCreatedBy(UUID.fromString(createdBy));
		List<GroupResponse> groupResponse = convertToGroupResponse(groups);
		return groupResponse;
	}

	public GroupResponse fetchGroupById(Long groupId) {
		
//		File file = new File("D:\\photos\\college photos\\sem 1\\IMG_20191129_132254.jpg");
//		byte[] picInBytes = new byte[(int) file.length()];
		Optional<Group> optionalGroup= groupRepository.findById(groupId);
		Group group = optionalGroup.get();
		GroupResponse groupResponse = new GroupResponse();
		groupResponse.setId(group.getId());
		groupResponse.setName(group.getName());
		groupResponse.setStatus(group.getStatus());
		groupResponse.setDescription(group.getDescription());
		groupResponse.setUsers(group.getUsers());
        groupResponse.setGroupImage(group.getGroupImage());
        groupResponse.setCreatedBy(group.getCreatedBy());
		return groupResponse;
	}
	
	public boolean isUserPresent(Group group,UUID userId) {
		 Iterator<User> iter = group.getUsers().iterator();
		 while (iter.hasNext()) {
	            if(iter.next().getId() == userId) {
	            	return true;
	            }
	            
	        }
		 return false;
	}

	public List<GroupResponse> convertToGroupResponse(List<Group> groups) {
		List<GroupResponse> groupResponses = new ArrayList<>();

		groupResponses = groups.stream().map(g -> {
			GroupResponse groupResponse = new GroupResponse();
			groupResponse.setId(g.getId());
			groupResponse.setName(g.getName());
			groupResponse.setStatus(g.getStatus());
			groupResponse.setDescription(g.getDescription());
			groupResponse.setUsers(g.getUsers());
			groupResponse.setGroupImage(g.getGroupImage());
			return groupResponse;
		}).collect(Collectors.toList());
		return groupResponses;
	}

	public void deleteGroup(Long id) {
		Optional<Group> tempGroup = groupRepository.findById(id);
		tempGroup.get().setStatus("inactive");
		groupRepository.save(tempGroup.get());
	}

}
