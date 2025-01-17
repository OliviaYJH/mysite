package mysite.controller;

import java.util.Map;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import mysite.security.Auth;
import mysite.service.BoardService;
import mysite.vo.BoardVo;
import mysite.vo.UserVo;

@Controller
@RequestMapping("/board")
public class BoardController {
	private BoardService boardService;

	public BoardController(BoardService boardService) {
		this.boardService = boardService;
	}

	@RequestMapping("/{pageNo}")
	public String list(Model model, @PathVariable("pageNo") String no,
			@RequestParam(value = "kwd", required = true, defaultValue = "") String kwd) {
		if (no == null)
			no = "1";
		int pageNo = Integer.parseInt(no);

		Map<String, Object> map = boardService.getContentsList(pageNo, kwd);
		model.addAttribute("list", map.get("list"));
		model.addAttribute("totalCount", map.get("totalCount"));
		model.addAttribute("pageSize", map.get("pageSize"));
		model.addAttribute("beginPage", map.get("beginPage"));
		model.addAttribute("endPage", map.get("endPage"));
		model.addAttribute("prevPage", map.get("prevPage"));
		model.addAttribute("nextPage", map.get("nextPage"));
		model.addAttribute("kwd", map.get("kwd"));

		return "board/list";
	}

	// 게시글 작성하기
	@RequestMapping(value = "/write", method = RequestMethod.GET)
	public String write() {
		return "board/write";
	}

	@RequestMapping(value = "/write", method = RequestMethod.POST)
	public String write(Authentication authentication, BoardVo vo) {
		UserVo authUser = (UserVo) authentication.getPrincipal();
		
		vo.setUserId(authUser.getId());
		boardService.addContents(vo);
		return "redirect:/board/1";
	}

	// 게시글 상세보기
	@RequestMapping("/view/{id}")
	public String view(Model model, @PathVariable("id") Long id) {
		BoardVo vo = boardService.getContents(id);
		model.addAttribute("vo", vo);
		return "board/view";
	}

	// 게시글 삭제
	@RequestMapping("/delete/{id}")
	public String delete(Authentication authentication, @PathVariable("id") Long id) {
		UserVo authUser = (UserVo) authentication.getPrincipal();
				
		boardService.deleteContents(id, authUser.getId());
		return "redirect:/board/1";
	}

	// 게시글 수정
	@Auth
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.GET)
	public String modify(@PathVariable("id") Long id, Model model) {
		BoardVo vo = boardService.getContents(id);
		model.addAttribute("vo", vo);
		return "board/modify";
	}

	@RequestMapping(value = "/modify/{id}", method = RequestMethod.POST)
	public String modify(@PathVariable("id") Long id, BoardVo vo) {
		boardService.updateContents(vo);
		return "redirect:/board/view/" + id;
	}

	// 답글 작성
	@RequestMapping(value = "/reply/{boardId}", method = RequestMethod.GET)
	public String reply(@PathVariable("boardId") Long boardId) {
		return "board/reply";
	}

	@RequestMapping(value = "/reply/{boardId}", method = RequestMethod.POST)
	public String reply(Authentication authentication, BoardVo vo, @PathVariable("boardId") Long boardId) {
		UserVo authUser = (UserVo) authentication.getPrincipal();
				
		vo.setUserId(authUser.getId());
		vo.setId(boardId);
		boardService.addContents(vo);
		return "redirect:/board/1";
	}

}
