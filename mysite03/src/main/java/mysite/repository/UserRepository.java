package mysite.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.stereotype.Repository;

import mysite.vo.UserVo;

@Repository
public class UserRepository {
	private DataSource dataSource;

	public UserRepository(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public int insert(UserVo vo) {
		int count = 0;

		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("insert into user values(null, ?, ?, ?, ?, now(), 'USER');");) {
			pstmt.setString(1, vo.getName());
			pstmt.setString(2, vo.getEmail());
			pstmt.setString(3, vo.getPassword());
			pstmt.setString(4, vo.getGender());

			count = pstmt.executeUpdate();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
		return count;

	}

	public UserVo findByEmailAndPassword(String email, String password) {
		UserVo userVo = null;

		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select id, name, role from user where email = ? and password = ?");) {
			pstmt.setString(1, email);
			pstmt.setString(2, password);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String role = rs.getString(3);

				userVo = new UserVo();
				userVo.setId(id);
				userVo.setName(name);
				userVo.setRole(role);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return userVo;
	}

	public UserVo findById(Long userId) {
		UserVo userVo = null;

		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn
						.prepareStatement("select id, name, email, password, gender, role from user where id = ?");) {
			pstmt.setLong(1, userId);

			ResultSet rs = pstmt.executeQuery();
			if (rs.next()) {
				Long id = rs.getLong(1);
				String name = rs.getString(2);
				String email = rs.getString(3);
				String password = rs.getString(4);
				String gender = rs.getString(5);
				String role = rs.getString(6);

				userVo = new UserVo();
				userVo.setId(id);
				userVo.setName(name);
				userVo.setEmail(email);
				userVo.setPassword(password);
				userVo.setGender(gender);
				userVo.setRole(role);
			}
			rs.close();
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}

		return userVo;
	}

	public void update(UserVo vo) {
		// password 입력 안되면 이름, 성별만 수정,
		// password 입력 되면 이름, 패스워드, 성별 모두 수정
		try (Connection conn = dataSource.getConnection();
				PreparedStatement pstmt = conn.prepareStatement("update user set name = ?, gender = ? where id = ?;");
				PreparedStatement pstmt2 = conn
						.prepareStatement("update user set name = ?, gender = ?, password = ? where id = ?;");) {

			if (vo.getPassword().isEmpty()) {
				pstmt.setString(1, vo.getName());
				pstmt.setString(2, vo.getGender());
				pstmt.setLong(3, vo.getId());

				pstmt.executeUpdate();
			} else {
				pstmt2.setString(1, vo.getName());
				pstmt2.setString(2, vo.getGender());
				pstmt2.setString(3, vo.getPassword());
				pstmt2.setLong(4, vo.getId());

				pstmt2.executeUpdate();
			}
		} catch (SQLException e) {
			System.out.println("error: " + e);
		}
	}
}
