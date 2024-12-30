package mysite.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import mysite.repository.BoardRepository;
import mysite.vo.BoardVo;

@Service
public class BoardService {
	private BoardRepository boardRepository;
	public final static int pageSize = 5;

	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}

	public void addContents(BoardVo vo) {
		boardRepository.insertNew(vo);
	}

	public BoardVo getContents(Long id) { // boardId
		boardRepository.updateHitById(id);
		return boardRepository.findById(id);
	}

	public BoardVo getContents(Long id, Long userId) { // boardId, userId
		return null;
	}

	public void updateContents(BoardVo vo) {
		boardRepository.updateByBoardId(vo);
	}

	public void deleteContents(Long id, Long userId) { // boardId, userId
		boardRepository.deleteByBoardId(id);
	}

	public Map<String, Object> getContentsList(int pageNo, String keyword) {
		Map<String, Object> map = new HashMap<>();
		List<BoardVo> list = null;
		if (keyword.isBlank()) {
			list = boardRepository.findAll(pageNo, pageSize);
		} else {
			list = boardRepository.findAllByKeyword(pageNo, pageSize, keyword);
		}
		map.put("list", list);

		// view의 pagination를 위한 데이터 값 계산
		int totalCount = boardRepository.findBoardCount();
		int beginPage = 1;
		int endPage = boardRepository.findEndPage(pageSize);

		int prevPage = pageNo - 2;
		if (pageNo + 2 >= endPage)
			prevPage = endPage - 4;
		prevPage = Math.max(prevPage, beginPage);

		int nextPage = pageNo + 2;
		if (nextPage < endPage && nextPage <= 5)
			nextPage = 5;
		nextPage = Math.min(nextPage, endPage);

		map.put("totalCount", totalCount);
		map.put("pageSize", pageSize);
		map.put("pageNo", pageNo);
		map.put("beginPage", beginPage);
		map.put("endPage", endPage);
		map.put("prevPage", prevPage);
		map.put("nextPage", nextPage);
		map.put("keyword", keyword);

		return map;
	}
}
