package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class BoardWriteAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long userId = Long.parseLong(request.getParameter("userId"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
	
		BoardVo vo = new BoardVo();
		vo.setUserId(userId);
		vo.setTitle(title);
		vo.setContents(content);
		
		new BoardDao().insertNew(vo);
		response.sendRedirect(request.getContextPath() + "/board");
	}

}
