package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class BoardViewAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long id = Long.parseLong(request.getParameter("id"));
		BoardVo vo = new BoardDao().findById(id);
		new BoardDao().updatesHitById(id);
	
		request.setAttribute("vo", vo);
		request.getRequestDispatcher("/WEB-INF/views/board/view.jsp").forward(request, response);
	}

}
