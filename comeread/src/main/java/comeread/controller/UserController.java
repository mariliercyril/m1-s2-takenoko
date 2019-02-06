package comeread.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import comeread.model.User;
import comeread.service.ComereadService;

@RestController
@RequestMapping("/users")
public class UserController {

	@Autowired
	@Qualifier("UserService")
	private ComereadService userService;

	@RequestMapping()
	public Collection<User> getAllUsers(){
		return userService.getAllUsers();
	}
	
	@RequestMapping(value="/{uid}", method=RequestMethod.GET)
	public User getUserByUid(@PathVariable("uid") int uid){
		return userService.getUserByUid(uid);
	}
	
	@RequestMapping(value="/{uid}", method=RequestMethod.DELETE)
	public void removeUserByUid(@PathVariable("uid") int uid){
		userService.removeUserByUid(uid);
	}
	
	@RequestMapping(method=RequestMethod.PUT, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void updateUser(@RequestBody User user){
		userService.updateUser(user);
	}
	
	@RequestMapping(method=RequestMethod.POST, consumes=MediaType.APPLICATION_JSON_VALUE)
	public void insertUser(@RequestBody User user){
		userService.insertUser(user);
	}
}
