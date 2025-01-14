package mysite.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	@Autowired
	private DataSource dataSource;

	private SqlSession sqlSession;

	public BoardRepository(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}

	public List<BoardVo> findAll(int pageNo, int pageSize) {
		return sqlSession.selectList("board.findAll", Map.of("pageNo", (pageNo - 1) * pageSize, "pageSize", pageSize));
	}

	public List<BoardVo> findAllByKeyword(int pageNo, int pageSize, String keyword) {
		return sqlSession.selectList("board.findAllByKeyword",
				Map.of("keyword", "%" + keyword + "%", "pageNo", (pageNo - 1) * pageSize, "pageSize", pageSize));
	}

	public BoardVo findById(Long id) {
		return sqlSession.selectOne("board.findById", id);
	}

	public int insertNew(BoardVo vo) {
		int max = sqlSession.selectOne("board.selectMaxgNo");
		vo.setgNo(max + 1);
		return sqlSession.insert("board.insertNew", vo);
	}

	public int insertReply(BoardVo vo) {
		return sqlSession.insert("board.insertReply", vo);
	}

	public int updateHitById(Long id) {
		return sqlSession.update("board.updateHitById", id);
	}

	public int updateByBoardId(BoardVo vo) {
		return sqlSession.update("board.updateByBoardId", vo);
	}

	public int updateBygNoAndoNo(int gNo, int oNo) {
		return sqlSession.update("board.updateBygNoAndoNo", Map.of("gNo", gNo, "oNo", oNo + 1));
	}

	public int deleteByBoardId(Long boardId) {
		return sqlSession.delete("board.deleteByBoardId", boardId);
	}

	public int findEndPage(int pageSize) {
		return sqlSession.selectOne("findEndPage", pageSize);
	}

	public int findEndPageByKeyword(String keyword, int pageSize) {
		return sqlSession.selectOne("board.findEndPageByKeyword",
				Map.of("keyword", "%" + keyword + "%", "pageSize", pageSize));
	}

	public int findBoardCount() {
		return sqlSession.selectOne("board.findBoardCount");
	}

	public int findBoardCountByKeyword(String keyword) {
		return sqlSession.selectOne("board.findBoardCountByKeyword",  "%" + keyword + "%");
	}
}
