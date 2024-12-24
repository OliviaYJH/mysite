package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;

public class BoardDeleteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long boardId = Long.parseLong(request.getParameter("boardId"));
		
		new BoardDao().deleteByBoardId(boardId);
		response.sendRedirect(request.getContextPath() + "/board");
	}

}
