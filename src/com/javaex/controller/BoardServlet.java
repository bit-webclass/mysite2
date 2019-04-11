package com.javaex.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.BoardDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.BoardVo;
import com.javaex.vo.UserVo;

@WebServlet("/board")
public class BoardServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String actionName = request.getParameter("action");
		
		if("writeform".equals(actionName)) {
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			
			if(authUser == null) {
				WebUtil.forward(request, response, "/WEB-INF/views/users/loginform.jsp"	);
			} else {
				WebUtil.forward(request, response, "/WEB-INF/views/board/writeform.jsp"	);
			}	
		}else if("write".equals(actionName)) {
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo) session.getAttribute("authUser");
			BoardVo vo = new BoardVo();
			BoardDao dao = new BoardDao();
			
			vo.setTitle(title);
			vo.setContent(content);
			vo.setUserNo(authUser.getNo());
			
			dao.write(vo);
			
			WebUtil.redirect(request, response, "/mysite2/board");
			
		}else if("read".equals(actionName)) {
			System.out.println("View 접속");
			
			int no = Integer.parseInt(request.getParameter("no"));
			BoardDao dao = new BoardDao();
			BoardVo vo = dao.select(no);
			
			System.out.println(vo.toString());
			
			dao.upCount(no);
					
			request.setAttribute("boardVo", vo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/read.jsp");
		}else if("modifyform".equals(actionName)){
			
			//WebUtil.checkLogin(request, response, "/mysite/board?a=list");
			System.out.println("modifyfrom 접근");
			
			BoardDao dao = new BoardDao();
			int no = Integer.parseInt(request.getParameter("no"));
			BoardVo boardVo = dao.select(no);
			
			request.setAttribute("boardVo", boardVo);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/modifyform.jsp");
		}else if("modify".equals(actionName)) {
			System.out.println("modify 접근");
			
			BoardDao dao = new BoardDao();
			
			String title = request.getParameter("title");
			String content = request.getParameter("content");
			int no = Integer.parseInt(request.getParameter("no"));
			
			BoardVo vo = new BoardVo(no, title, content);
			
			dao.modify(vo);
			
			WebUtil.redirect(request, response, "/mysite2/board?action=read&no=" +no );
		}else if("delete".equals(actionName)) {
			System.out.println("delete 접근");
			
			BoardDao dao = new BoardDao();
			int no = Integer.parseInt(request.getParameter("no"));
			dao.delete(no);
			
			WebUtil.redirect(request, response, "/mysite2/board");				
		}else{
			System.out.println("list 접속");
			BoardDao dao = new BoardDao();
			
			List<BoardVo> boardList = dao.getList();
			request.setAttribute("boardList", boardList);
			
			WebUtil.forward(request, response, "/WEB-INF/views/board/list.jsp"	);	
		}
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
