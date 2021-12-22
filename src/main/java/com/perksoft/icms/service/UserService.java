package com.perksoft.icms.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perksoft.icms.models.Page;
import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.response.PageResponse;
import com.perksoft.icms.payload.response.UserResponse;
import com.perksoft.icms.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	public PageService pageService;
	
	public List<PageResponse> getFavouritePages(String userId){
		User existingUser = userRepository.findById(UUID.fromString(userId)).get();
		List<Page> followingPages = new ArrayList<Page>();
		Iterator<Page> iter = existingUser.getFollowingPages().iterator();
		int i = 0;
		while (i < 10 && iter.hasNext()) {
			followingPages.add(iter.next());
			i++;
		}
		List<PageResponse> pageResponses = pageService.converToResponse(followingPages);
		return pageResponses;
	}

	public UserResponse getUserById(UUID userId, UUID tenantId) {
		Optional<User> optionalUser = userRepository.findById(userId);
		User user = optionalUser.get();
		UserResponse userResponse = new UserResponse();
		userResponse.setEmail(user.getEmail());
		userResponse.setGroups(user.getGroups());
		userResponse.setUsername(user.getUsername());
		userResponse.setProfileImage(user.getProfileImage());
		userResponse.setFirstName(user.getFirstName());
		userResponse.setLastName(user.getLastName());
		userResponse.setId(user.getId());
		userResponse.setMobileNumber(user.getMobileNumber());
		userResponse.setPosts(user.getPosts());
		userResponse.setProfileImage(user.getProfileImage());
		return userResponse;
	}

}
