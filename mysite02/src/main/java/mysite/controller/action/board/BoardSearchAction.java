package mysite.controller.action.board;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.controller.ActionServlet.Action;
import mysite.dao.BoardDao;
import mysite.vo.BoardVo;

public class BoardSearchAction implements Action {

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String no = request.getParameter("pageNo");
		if (no == null)
			no = "1";
		int pageNo = Integer.parseInt(no);
		String keyword = request.getParameter("kwd");

		List<BoardVo> list = new BoardDao().findAllByKeyword(pageNo, BoardListAction.pageSize, keyword);
		request.setAttribute("list", list);

		int totalCount = new BoardDao().findBoardCountByKeyword(keyword);
		int beginPage = 1;
		int endPage = new BoardDao().findEndPageByKeyword(keyword, BoardListAction.pageSize);

		int prevPage = pageNo - 2;
		if (pageNo + 2 >= endPage)
			prevPage = endPage - 4;
		prevPage = Math.max(prevPage, beginPage);

		int nextPage = pageNo + 2;
		if (nextPage < endPage && nextPage <= 5)
			nextPage = 5;
		nextPage = Math.min(nextPage, endPage);

		request.setAttribute("totalCount", totalCount);
		request.setAttribute("pageSize", BoardListAction.pageSize);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("prevPage", prevPage);
		request.setAttribute("nextPage", nextPage);
		request.setAttribute("keyword", keyword);

		request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
	}

}
