package mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class BoardListAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String pageNo = request.getParameter("pageNo");
		if (pageNo == null)
			pageNo = "1";

		// 파라미터
		int pageSize = 5;

		List<BoardVo> list = new BoardDao().findAll(pageNo, pageSize);
		request.setAttribute("list", list);

		// page 정보
		// current, end 등 정보 가져오기 
		
		request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
	}

}