package com.ecommerce.demo.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.demo.exceptions.AlreadyExistsException;
import com.ecommerce.demo.model.User;
import com.ecommerce.demo.repository.UserRepository;
import com.ecommerce.demo.request.CreateUserRequest;

@Service
public class UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	public User createUser(CreateUserRequest request) {
        return  Optional.of(request)
                .filter(user -> !userRepository.existsByEmail(request.getEmail()))
                .map(req -> {
                    User user = new User();
                    user.setEmail(request.getEmail());
                    user.setPassword(request.getPassword());
                    user.setFirstName(request.getFirstName());
                    user.setLastName(request.getLastName());
                    return  userRepository.save(user);
                }) .orElseThrow(() -> new AlreadyExistsException("Oops!" +request.getEmail() +" already exists!"));
    }
}
