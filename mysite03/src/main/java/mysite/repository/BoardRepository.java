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

	public void updateBygNoAndoNo(int gNo, int oNo) {
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("update board set o_no = o_no + 1 where g_no = ? and o_no >= ?;");) {
			pstmt.setInt(1, gNo);
			pstmt.setInt(2, oNo + 1);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}
	}

	public int deleteByBoardId(Long boardId) {
		int count = 0;
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("delete from board where id = ?;");) {
			pstmt.setLong(1, boardId);

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return count;
	}

	public int findEndPage(int pageSize) {
		int result = 0;

		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select ceil(count(*)/?) from board;")) {
			pstmt.setInt(1, pageSize);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public int findEndPageByKeyword(String keyword, int pageSize) {
		int result = 0;

		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select ceil(count(*)/?) from board where title like ?;")) {
			pstmt.setInt(1, pageSize);
			pstmt.setString(2, "%" + keyword + "%");

			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public int findBoardCount() {
		int result = 0;

		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select count(*) from board;")) {
			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}

	public int findBoardCountByKeyword(String keyword) {
		int result = 0;

		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select count(*) from board where title like ?;")) {
			pstmt.setString(1, "%" + keyword + "%");

			ResultSet rs = pstmt.executeQuery();
			if (rs.next())
				result = rs.getInt(1);
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return result;
	}
}
