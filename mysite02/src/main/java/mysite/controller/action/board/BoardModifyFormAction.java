package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class BoardModifyFormAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long boardId = Long.parseLong(request.getParameter("boardId"));
		BoardVo vo = new BoardDao().findById(boardId);

		request.setAttribute("vo", vo);
		request.getRequestDispatcher("/WEB-INF/views/board/modify.jsp").forward(request, response);
	}

}
