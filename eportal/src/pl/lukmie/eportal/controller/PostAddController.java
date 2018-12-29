package pl.lukmie.eportal.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.lukmie.eportal.model.User;
import pl.lukmie.eportal.service.PostService;

@WebServlet("/add")
public class PostAddController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		if(request.getUserPrincipal() != null) {
			request.getRequestDispatcher("new.jsp").forward(request, response);
		} else {
			response.sendError(403);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		String name = request.getParameter("inputName");
		String description = request.getParameter("inputDecription");
		String url = request.getParameter("inputUrl");
		User authenticatedUser = (User) request.getSession().getAttribute("user");
		if(request.getUserPrincipal() != null) {
			PostService postService = new PostService();
			postService.addPost(name, description, url, authenticatedUser);
			response.sendRedirect(request.getContextPath() + "/");
		} else {
			response.sendError(403);
		}
	}

}
