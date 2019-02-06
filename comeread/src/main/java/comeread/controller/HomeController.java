package comeread.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import comeread.model.User;
import comeread.service.ComereadService;

@RestController //THIS CAN'T RETURN JSP PAGES, BUT CAN RETURN A MODELANDVIEW OBJECT OF THE JSP PAGE
@RequestMapping(value={"", "/", "/home"})
public class HomeController {

	@Autowired
	@Qualifier("UserService")
	private ComereadService userService;
	
	@RequestMapping()
	public String home(){
	return ""
				+ "Welcome to ComeRead"
				+ "<br><a href=\"users\">Users</a>"
				+ "<br><a href=\"articles\">Articles</a>";
	}
	
	@RequestMapping(value="home-jsp")
	public ModelAndView homeJsp(){
		ModelAndView mav = new ModelAndView("homepage");
		return mav;
	}
	
//	@RequestMapping(value="home-jsp/", method=RequestMethod.POST)
//	public String insertUser(@ModelAttribute User user){
//		userService.insertUser(user);
//		return "Okay";
//	}
}
