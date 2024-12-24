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
		String no = request.getParameter("pageNo");
		if(no == null) no = "1";
		int pageNo = Integer.parseInt(no);

		// 파라미터
		int pageSize = 3;

		List<BoardVo> list = new BoardDao().findAll(pageNo, pageSize);
		request.setAttribute("list", list);

		// page 정보
		int totalCount = new BoardDao().findBoardCount();
		int beginPage = 1;
		int endPage = new BoardDao().findEndPage(pageSize);
		
		int prevPage = pageNo - 2; // 현재 화면에서 확인할 수 있는 가장 작은 페이지 번호
		prevPage = Math.max(prevPage, beginPage);
		
		int nextPage = pageNo + 2;
		if(pageNo + 2 < endPage) {
			nextPage = 5;
		}
		nextPage = Math.min(nextPage, endPage);
		
		request.setAttribute("totalCount", totalCount);
		request.setAttribute("pageSize", pageSize);
		request.setAttribute("pageNo", pageNo);
		request.setAttribute("beginPage", beginPage);
		request.setAttribute("endPage", endPage);
		request.setAttribute("prevPage", prevPage);
		request.setAttribute("nextPage", nextPage);
		// 현재 페이지의 마지막 번호 
		
		System.out.println("pageNo: " + pageNo + ", beginPage: " + beginPage + ", endPage: " + endPage + ", prevPage: " + prevPage + ", nextPage: " + nextPage);
		
		request.getRequestDispatcher("/WEB-INF/views/board/list.jsp").forward(request, response);
	}

}