package mysite.controller;

import java.util.Map;

import jakarta.servlet.annotation.WebServlet;
import mysite.controller.action.board.BoardDeleteAction;
import mysite.controller.action.board.BoardListAction;
import mysite.controller.action.board.BoardModifyAction;
import mysite.controller.action.board.BoardModifyFormAction;
import mysite.controller.action.board.BoardReplyAction;
import mysite.controller.action.board.BoardReplyFormAction;
import mysite.controller.action.board.BoardSearchAction;
import mysite.controller.action.board.BoardViewAction;
import mysite.controller.action.board.BoardWriteAction;
import mysite.controller.action.board.BoardWriteFormAction;

@WebServlet("/board")
public class BoardServlet extends ActionServlet {
	private static final long serialVersionUID = 1L;

	private Map<String, Action> mapAction = Map.of(
			"view", new BoardViewAction(),
			"modifyform", new BoardModifyFormAction(),
			"modify", new BoardModifyAction(),
			"delete", new BoardDeleteAction(),
			"writeform", new BoardWriteFormAction(),
			"write", new BoardWriteAction(),
			"replyform", new BoardReplyFormAction(),
			"reply", new BoardReplyAction(),
			"search", new BoardSearchAction()
	);

	@Override
	protected Action getAction(String actionName) {
		return mapAction.getOrDefault(actionName, new BoardListAction());
	}

}
