package org.turing.api.controller;

import org.turing.api.domain.Role;
import org.turing.api.domain.User;
import org.turing.api.dto.UserListDto;
import org.turing.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


@Controller
@RequestMapping("/users")
public class UserController {


	@Autowired
	private UserService service;
	
	@RequestMapping
	public String getUsersPage() {
		
		UserListDto userListDto = new UserListDto();
		userListDto.setUsers(service.readAll());

		return "users";
	}
	
	@RequestMapping(value="/records")
	public @ResponseBody UserListDto getUsers() {
		
		UserListDto userListDto = new UserListDto();
		userListDto.setUsers(service.readAll());
		return userListDto;
	}
	
	@RequestMapping(value="/get")
	public @ResponseBody User get(@RequestBody User user) {
		return service.read(user);
	}

	@RequestMapping(value="/create", method=RequestMethod.POST)
	public @ResponseBody User create(
			@RequestParam String username,
			@RequestParam String password,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam Integer role) {

		Role newRole = new Role();
		newRole.setRole(role);
		
		User newUser = new User();
		newUser.setUsername(username);
		newUser.setPassword(password);
		newUser.setFirstName(firstName);
		newUser.setLastName(lastName);
		newUser.setRole(newRole);
		
		return service.create(newUser);
	}
	
	@RequestMapping(value="/update", method=RequestMethod.POST)
	public @ResponseBody User update(
			@RequestParam String username,
			@RequestParam String firstName,
			@RequestParam String lastName,
			@RequestParam Integer role) {

		Role existingRole = new Role();
		existingRole.setRole(role);
		
		User existingUser = new User();
		existingUser.setUsername(username);
		existingUser.setFirstName(firstName);
		existingUser.setLastName(lastName);
		existingUser.setRole(existingRole);
		
		return service.update(existingUser);
	}
	
	@RequestMapping(value="/delete", method=RequestMethod.POST)
	public @ResponseBody Boolean delete(
			@RequestParam String username) {

		User existingUser = new User();
		existingUser.setUsername(username);
		
		return service.delete(existingUser);
	}

}
