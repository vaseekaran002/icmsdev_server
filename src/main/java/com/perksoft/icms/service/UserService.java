package com.perksoft.icms.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.perksoft.icms.models.User;
import com.perksoft.icms.payload.response.PageResponse;
import com.perksoft.icms.payload.response.UserResponse;
import com.perksoft.icms.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PageService pageService;

	public List<User> getAllUsers(String tenantId) {
		return userRepository.findAllByTenantId(UUID.fromString(tenantId));
	}

	public User saveUser(User user) {
		return userRepository.save(user);
	}

	public List<PageResponse> getFavouritePages(String userId) {
		List<PageResponse> pageResponses = new ArrayList<>();
		Optional<User> optionalExistingUser = userRepository.findById(UUID.fromString(userId));

		if (optionalExistingUser.isPresent()) {
			User user = optionalExistingUser.get();
			pageResponses = pageService.converToResponse(user.getFollowingPages());
		}
		return pageResponses;
	}

	public User getUserByIdAndTenantId(String userId, String tenantId) {
		Optional<User> optionalUser = userRepository.findByIdAndTenantId(UUID.fromString(userId),
				UUID.fromString(tenantId));
		User user = null;

		if (optionalUser.isPresent()) {
			user = optionalUser.get();
		}
		return user;
	}

	public UserResponse convertToUserResponse(User user) {
		UserResponse userResponse = null;

		if (user != null) {
			userResponse = new UserResponse();
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
		}
		return userResponse;
	}

}
