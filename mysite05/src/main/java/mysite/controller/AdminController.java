package mysite.controller;

import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.ServletContext;
import mysite.service.FileUploadService;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

@Controller
@RequestMapping("/admin")
public class AdminController {
	private final FileUploadService fileUploadService;
	private final SiteService siteService;
	private final ServletContext servletContext;
	private final ApplicationContext applicationContext;

	public AdminController(
			SiteService siteService, 
			FileUploadService fileUploadService,
			ServletContext servletContext,
			 ApplicationContext applicationContext) {
		this.siteService = siteService;
		this.fileUploadService = fileUploadService;
		this.servletContext = servletContext;
		this.applicationContext = applicationContext;
	}

	@RequestMapping({ "", "/main" })
	public String main(Model model) {
		SiteVo vo = siteService.getSite();
		model.addAttribute("siteVo", vo);
		return "admin/main";
	}

	@RequestMapping("/update")
	public String update(@RequestParam("title") String title, @RequestParam("welcome") String welcome,
			@RequestParam("description") String description, @RequestParam("file") MultipartFile file) {
		SiteVo vo = new SiteVo();
		vo.setTitle(title);
		vo.setWelcome(welcome);
		vo.setDescription(description);

		if (!file.isEmpty()) {
			String url = fileUploadService.restore(file);
			vo.setProfile(url);

			siteService.updateSite(vo);
			
			// update servlet context bean 
			servletContext.setAttribute("siteVo", vo);
			
			// update application context bean 
			SiteVo site = applicationContext.getBean(SiteVo.class);
			BeanUtils.copyProperties(vo, site);
		}

		return "redirect:/admin";
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
