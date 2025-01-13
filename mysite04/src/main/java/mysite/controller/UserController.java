package mysite.controller;

import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.Valid;
import mysite.security.Auth;
import mysite.security.AuthUser;
import mysite.service.UserService;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/user")
public class UserController {
	// @Autowired // 주입 -> 이거 대신에 자바 문법으로 생성자로 주입할 수 있음
	private UserService userService;

	public UserController(UserService userService) {
		this.userService = userService;
	}

	@GetMapping("/join")
	public String join(@ModelAttribute UserVo userVo) {
		return "user/join";
	}

	@PostMapping("/join")
	public String join(@ModelAttribute @Valid UserVo userVo, BindingResult result, Model model) {
		if(result.hasErrors()) {
			Map<String, Object> map = result.getModel();
			model.addAllAttributes(map);
			return "user/join";
		}
		
		userService.join(userVo);
		return "redirect:/user/joinsuccess";
	}

	@RequestMapping("/joinsuccess")
	public String joinSuccess() {
		return "user/joinsuccess";
	}

	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login() {
		return "user/login";
	}

	/*
	 * @Auth
	 * 
	 * @RequestMapping(value = "/update", method = RequestMethod.GET) public String
	 * update(HttpSession session, Model model) { // argument resolver UserVo
	 * authUser = (UserVo) session.getAttribute("authUser"); UserVo userVo =
	 * userService.getUser(authUser.getId());
	 * 
	 * model.addAttribute("vo", userVo); return "user/update"; }
	 */

	@Auth
	@RequestMapping(value = "/update", method = RequestMethod.GET)
	public String update(@AuthUser UserVo authUser, Model model) { // argument resolver
		UserVo userVo = userService.getUser(authUser.getId());

		model.addAttribute("vo", userVo);
		return "user/update";
	}

	/*
	 * @Auth
	 * 
	 * @RequestMapping(value = "/update", method = RequestMethod.POST) public String
	 * update(@AuthUser UserVo authUser, UserVo userVo) { // Access Control
	 * 
	 * userVo.setId(authUser.getId()); userService.update(userVo);
	 * 
	 * authUser.setName(userVo.getName()); return "redirect:/user/update"; }
	 */

	/*
	 * @Auth
	 * 
	 * @RequestMapping(value = "/update", method = RequestMethod.POST) public String
	 * update(HttpSession session, UserVo userVo) { UserVo authUser = (UserVo)
	 * session.getAttribute("authUser"); userVo.setId(authUser.getId());
	 * userService.update(userVo);
	 * 
	 * authUser.setName(userVo.getName()); return "redirect:/user/update"; }
	 */

	@Auth
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public String update(@AuthUser UserVo authUser, UserVo userVo) {
		userVo.setId(authUser.getId());
		userService.update(userVo);

		authUser.setName(userVo.getName());
		return "redirect:/user/update";
	}
}