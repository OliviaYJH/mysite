package mysite.controller.action.user;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import mysite.controller.ActionServlet.Action;
import mysite.dao.UserDao;
import mysite.vo.UserVo;

public class UpdateAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		// Access Control
		if (session == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}

		UserVo authUser = (UserVo) session.getAttribute("authUser");
		if (authUser == null) {
			response.sendRedirect(request.getContextPath());
			return;
		}
		
		UserVo vo = new UserVo();
		vo.setId(authUser.getId());
		vo.setName(request.getParameter("name"));
		vo.setEmail(request.getParameter("email"));
		vo.setGender(request.getParameter("gender"));
		vo.setPassword(request.getParameter("password"));
		
		new UserDao().update(vo);
		authUser.setName(vo.getName());
		
		response.sendRedirect(request.getContextPath()+"/user");
	}

}
