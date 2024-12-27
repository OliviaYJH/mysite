package mysite.controller;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.service.BoardService;
import mysite.vo.BoardVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	private BoardService boardService;

	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@RequestMapping("/{pageNo}")
	public String list(Model model, @PathVariable("pageNo") String no,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String keyword) {
		if (no == null)
			no = "1";
		int pageNo = Integer.parseInt(no);
		
		Map<String, Object> map = boardService.getContentsList(pageNo, keyword);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("totalCount", map.get("totalCount"));
		model.addAttribute("pageSize", map.get("pageSize"));
		model.addAttribute("beginPage", map.get("beginPage"));
		model.addAttribute("endPage", map.get("endPage"));
		model.addAttribute("prevPage", map.get("prevPage"));
		model.addAttribute("nextPage", map.get("nextPage"));

		return "board/list";
	}
	
	
}
