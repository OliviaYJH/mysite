package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;

public class BoardReplyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long boardId = Long.parseLong(request.getParameter("boardId"));
		request.setAttribute("boardId", boardId);
	
		request.getRequestDispatcher("/WEB-INF/views/board/reply.jsp").forward(request, response);
	}

}
