package mysite.interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.LocaleResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.service.SiteService;
import mysite.vo.SiteVo;

public class SiteInterceptor implements HandlerInterceptor {
	private LocaleResolver localeResolver;
	private SiteService siteService;
	
	public SiteInterceptor(SiteService siteService, LocaleResolver localeResolver) {
		this.siteService = siteService;
		this.localeResolver = localeResolver;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// locale 
		String lang = localeResolver.resolveLocale(request).getLanguage();
		request.setAttribute("lang", lang);
		
		SiteVo vo = siteService.getSite();
		
		request.getSession().setAttribute("title", vo.getTitle());
		return true;
	}
	
}

