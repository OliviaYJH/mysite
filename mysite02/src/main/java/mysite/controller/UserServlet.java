package mysite.controller;

import java.io.IOException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.dao.UserDao;
import mysite.vo.UserVo;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		String action = request.getParameter("a");

		// /user?a=joinform(GET)
		if ("joinform".equals(action)) {
			request.getRequestDispatcher("/WEB-INF/views/user/joinform.jsp").forward(request, response);
		} else if ("join".equals(action)) { // /user?a=join(POST)
			// insert 일어나고 redirect 시키기
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			UserVo vo = new UserVo();
			vo.setName(name);
			vo.setEmail(email);
			vo.setPassword(password);
			vo.setGender(gender);
			
			System.out.println("vo: " + vo);
			new UserDao().insert(vo);
			
			response.sendRedirect(request.getContextPath() + "/user?a=joinsuccess");
		} else if ("joinsuccess".equals(action)) { // /user?a=joinsuccess(GET)
			request.getRequestDispatcher("/WEB-INF/views/user/joinsuccess.jsp").forward(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
