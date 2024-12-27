package mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import mysite.vo.BoardVo;

@Repository
public class BoardRepository {
	public List<BoardVo> findAll(int pageNo, int pageSize) {
		List<BoardVo> result = new ArrayList<>();

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select b.id, u.id, b.title, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.depth, u.name"
								+ " from board b, user u where u.id = b.user_id"
								+ " order by b.g_no desc, b.o_no asc limit ?, ?;");) {

			pstmt.setInt(1, (pageNo - 1) * pageSize);
			pstmt.setInt(2, pageSize);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Long boardId = rs.getLong(1);
				Long userId = rs.getLong(2);
				String title = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int depth = rs.getInt(6);
				String name = rs.getString(7);

				BoardVo vo = new BoardVo();
				vo.setId(boardId);
				vo.setUserId(userId);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setDepth(depth);
				vo.setUserName(name);

				result.add(vo);

			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return result;
	}

	public List<BoardVo> findAllByKeyword(int pageNo, int pageSize, String keyword) {
		List<BoardVo> result = new ArrayList<>();

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(
						"select b.id, u.id, b.title, b.hit, date_format(b.reg_date, '%Y-%m-%d %h:%i:%s'), b.depth, u.name"
						+ " from board b, user u where u.id = b.user_id and b.title like ?"
						+ "	order by b.g_no desc, b.o_no asc limit ?, ?;");) {
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, (pageNo - 1) * pageSize);
			pstmt.setInt(3, pageSize);

			ResultSet rs = pstmt.executeQuery();
			while (rs.next()) {
				Long boardId = rs.getLong(1);
				Long userId = rs.getLong(2);
				String title = rs.getString(3);
				int hit = rs.getInt(4);
				String regDate = rs.getString(5);
				int depth = rs.getInt(6);
				String name = rs.getString(7);

				BoardVo vo = new BoardVo();
				vo.setId(boardId);
				vo.setUserId(userId);
				vo.setTitle(title);
				vo.setHit(hit);
				vo.setRegDate(regDate);
				vo.setDepth(depth);
				vo.setUserName(name);

				result.add(vo);

			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error:" + e);
		}

		return result;
	}

	public BoardVo findById(Long id) {
		BoardVo vo = new BoardVo();

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select b.id, b.title, b.contents, u.id, b.g_no, b.o_no, b.depth"
								+ " from board b, user u" + " where b.user_id = u.id and b.id = ?;");) {
			pstmt.setLong(1, id);
			ResultSet rs = pstmt.executeQuery();

			while (rs.next()) {
				Long boardId = rs.getLong(1);
				String title = rs.getString(2);
				String contents = rs.getString(3);
				Long userId = rs.getLong(4);
				int gNo = rs.getInt(5);
				int oNo = rs.getInt(6);
				int depth = rs.getInt(7);

				vo.setId(boardId);
				vo.setTitle(title);
				vo.setContents(contents);
				vo.setUserId(userId);
				vo.setgNo(gNo);
				vo.setoNo(oNo);
				vo.setDepth(depth);
			}

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return vo;
	}

	public int insertNew(BoardVo vo) {
		int count = 0;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("select max(g_no) from board;");
				PreparedStatement pstmt2 = conn
						.prepareStatement("insert board values(null, ?, ?, 0, now(), ?, 1, 0, ?);")) {
			ResultSet rs = pstmt.executeQuery();
			int max = 0;
			while (rs.next())
				max = rs.getInt(1);

			pstmt2.setString(1, vo.getTitle());
			pstmt2.setString(2, vo.getContents());
			pstmt2.setInt(3, max + 1);
			pstmt2.setLong(4, vo.getUserId());

			count = pstmt2.executeUpdate();
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return count;
	}

	public int insertReply(BoardVo vo) {
		int count = 0;

		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("insert board values(null, ?, ?, 0, now(), ?, ?, ?, ?);")) {
			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setInt(3, vo.getgNo());
			pstmt.setInt(4, vo.getoNo() + 1);
			pstmt.setInt(5, vo.getDepth() + 1);
			pstmt.setLong(6, vo.getUserId());

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return count;
	}

	public void updateHitById(Long id) {
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement("update board set hit = hit + 1 where id = ?;");) {

			pstmt.setLong(1, id);
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}
	}

	public void updateByBoardId(BoardVo vo) {
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("update board set title = ?, contents = ? where id = ?;");) {

			pstmt.setString(1, vo.getTitle());
			pstmt.setString(2, vo.getContents());
			pstmt.setLong(3, vo.getId());
			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

	}

	public void updateBygNoAndoNo(int gNo, int oNo) {
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("update board set o_no = o_no + 1 where g_no = ? and o_no >= ?;");) {
			pstmt.setInt(1, gNo);
			pstmt.setInt(2, oNo + 1);
			System.out.println("gNo: " + gNo + ", oNo: " + oNo);

			pstmt.executeUpdate();

		} catch (SQLException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}
	}

	public int deleteByBoardId(Long boardId) {
		int count = 0;
		try (Connection conn = getConnection();
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

		try (Connection conn = getConnection();
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

		try (Connection conn = getConnection();
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

		try (Connection conn = getConnection();
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

		try (Connection conn = getConnection();
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

	private Connection getConnection() throws SQLException {
		Connection conn = null;

		try {
			Class.forName("org.mariadb.jdbc.Driver");

			String url = "jdbc:mariadb://192.168.64.7:3306/webdb";
			conn = DriverManager.getConnection(url, "webdb", "webdb");
		} catch (ClassNotFoundException e) {
			System.out.println("드라이버 로딩 실패: " + e);
		}

		return conn;
	}

}
