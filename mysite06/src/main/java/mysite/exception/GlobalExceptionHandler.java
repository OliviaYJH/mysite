package mysite.exception;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mysite.dto.JsonResult;

@ControllerAdvice
public class GlobalExceptionHandler {
	private static final Log log = LogFactory.getLog(GlobalExceptionHandler.class);

	@ExceptionHandler(Exception.class)
	public void handler(HttpServletRequest request, HttpServletResponse response, Model model, Exception e)
			throws Exception {

		// 1. logging
		StringWriter errors = new StringWriter();
		e.printStackTrace(new PrintWriter(errors));
		log.error(errors.toString());

		// 2. 요청 구분
		// json 요청: request header의 accept: application/json (o) 반드시 있음
		// html 요청: request header의 accept: application/json (x) 반드시 없음
		String accept = request.getHeader("accept");

		// 3. JSON 응답
		if (accept.matches(".*application/json.*")) {
			JsonResult jsonResult = JsonResult
					.fail((e instanceof NoHandlerFoundException) ? "Unknown API URL" : errors.toString());
			// 본문 내용
			String jsonString = new ObjectMapper().writeValueAsString(jsonResult);

			response.setStatus(HttpServletResponse.SC_OK);
			response.setContentType("application/json; charset=utf-8");
			OutputStream os = response.getOutputStream();
			os.write(jsonString.getBytes("utf-8"));
			os.close();

			return;
		}

		// 4. HTML 응답: 사과 페이지(종료)
		if (e instanceof NoHandlerFoundException || e instanceof NoResourceFoundException) {
			request.
			getRequestDispatcher("/error/404").forward(request, response);
		} else {
			request.setAttribute("errors", errors.toString());
			request.getRequestDispatcher("/error/500").forward(request, response);
		}
	}
}
