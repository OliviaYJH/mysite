package mysite.repository;

import org.apache.ibatis.session.SqlSession;
import org.springframework.stereotype.Repository;

import mysite.vo.SiteVo;

@Repository
public class SiteRepository {
	private SqlSession sqlSession;
	
	public SiteRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public SiteVo getSite() {
		return sqlSession.selectOne("site.findSite");
	}
	
	public int updateSite(SiteVo vo) {
		long id = sqlSession.selectOne("site.findMaxId");
		vo.setId(id);
		return sqlSession.update("site.updateSite", vo);
	}
}
