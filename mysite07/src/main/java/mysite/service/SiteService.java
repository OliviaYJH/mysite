package mysite.service;

import org.springframework.stereotype.Service;

import mysite.repository.SiteRepository;
import mysite.vo.SiteVo;

@Service
public class SiteService {
	private SiteRepository siteRepository;
	
	public SiteService(SiteRepository siteRepository) {
		this.siteRepository = siteRepository;
	}
	
	public SiteVo getSite() {
		return siteRepository.getSite();
	}
	
	public int updateSite(SiteVo siteVo) {
		return siteRepository.updateSite(siteVo);
	}
}
