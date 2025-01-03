package mysite.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import mysite.security.Auth;
import mysite.service.FileUploadService;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Auth(role = "ADMIN")
@Controller
@RequestMapping("/admin")
public class AdminController {
	private final FileUploadService fileUploadService;
	private SiteService siteService;
	
	public AdminController(SiteService siteService, FileUploadService fileUploadService) {
		this.siteService = siteService;
		this.fileUploadService = fileUploadService;
	}

	@RequestMapping({ "", "/main" })
	public String main(Model model) {
		SiteVo vo = siteService.getSite();
		model.addAttribute("siteVo", vo);
		return "admin/main";
	}
	
	@RequestMapping("/update")
	public String update(
			@RequestParam("title") String title,
			@RequestParam("welcome") String welcome,
			@RequestParam("description") String description,
			@RequestParam("file") MultipartFile file
			) {
		SiteVo vo = new SiteVo();
		vo.setTitle(title);
		vo.setWelcome(welcome);
		vo.setDescription(description);
		
		if(!file.isEmpty()) {
			String url = fileUploadService.restore(file);
			vo.setProfile(url);
			
			siteService.updateSite(vo);
		}
		
		return "redirect:/admin/main";
	}

	@RequestMapping("/guestbook")
	public String guestbook() {
		return "admin/guestbook";
	}

	@RequestMapping("/board")
	public String board() {
		return "admin/board";
	}

	@RequestMapping("/user")
	public String user() {
		return "admin/user";
	}
}
