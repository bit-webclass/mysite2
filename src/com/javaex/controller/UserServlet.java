package com.javaex.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.javaex.dao.UserDao;
import com.javaex.util.WebUtil;
import com.javaex.vo.UserVo;

@WebServlet("/user")
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("user");
		request.setCharacterEncoding("UTF-8");
		
		String action = request.getParameter("action");
		
		if("joinform".equals(action)) {
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinform.jsp");
		}else if("join".equals(action)) {
			//파라미터수집
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			//vo만들기
			UserVo userVo = new UserVo(name, email, password, gender);
			System.out.println(userVo.toString());
			
			//insert
			UserDao userDao = new UserDao();
			userDao.insert(userVo);
			
			//화면 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/joinsuccess.jsp");
		}else if("loginform".equals(action)) {
			//화면 포워드
			WebUtil.forward(request, response, "/WEB-INF/views/user/loginform.jsp");
		}else if("login".equals(action)) {
			String email = request.getParameter("email");
			String password = request.getParameter("password");
			
			//getUser
			UserDao userDao = new UserDao();
			UserVo userVo = userDao.getUser(email, password);
			//System.out.println(userVo.toString());
			
			if(userVo == null) { //로그인실패
				WebUtil.redirect(request, response, "/mysite2/user?action=loginform&result=fail");
				
			}else { //로그인성공
				HttpSession session = request.getSession(true); 
				session.setAttribute("authUser", userVo);
				
				WebUtil.redirect(request, response, "/mysite2/main");
			}
			
		}else if("logout".equals(action)) {
			HttpSession session = request.getSession();
			session.removeAttribute("authUser");
			session.invalidate();
			
			WebUtil.redirect(request, response, "/mysite2/main");
			
		}else if("modifyform".equals(action)) {
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			UserDao dao = new UserDao();
			UserVo userVo = dao.getUser(no);
			System.out.println(userVo.toString());
			
			request.setAttribute("userVo", userVo);
			WebUtil.forward(request, response, "/WEB-INF/views/user/modifyform.jsp");
		}else if("modify".equals(action)) {			
			HttpSession session = request.getSession();
			UserVo authUser = (UserVo)session.getAttribute("authUser");
			int no = authUser.getNo();
			
			String name = request.getParameter("name");
			String password = request.getParameter("password");
			String gender = request.getParameter("gender");
			
			UserVo vo = new UserVo();
			vo.setNo(no);
			vo.setName(name);
			vo.setPassword(password);
			vo.setGender(gender);
			System.out.println(vo.toString());
			
			UserDao dao = new UserDao();
			dao.update(vo);
			
			authUser.setName(name);
			
			WebUtil.redirect(request, response, "/mysite2/main");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
