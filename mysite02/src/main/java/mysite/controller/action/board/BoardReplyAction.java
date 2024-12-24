package mysite.controller.action.board;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class BoardReplyAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Long boardId = Long.parseLong(request.getParameter("boardId"));
		Long userId = Long.parseLong(request.getParameter("userId"));
		String title = request.getParameter("title");
		String content = request.getParameter("content");
		
		BoardVo vo = new BoardDao().findById(boardId); // 원글 찾기 
		
		new BoardDao().updateBygNoAndoNo(vo.getgNo(), vo.getoNo());
		vo.setTitle(title);
		vo.setContents(content);
		vo.setUserId(userId);
		new BoardDao().insertReply(vo);

		response.sendRedirect(request.getContextPath() + "/board");
	}

}
