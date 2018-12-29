package pl.lukmie.eportal.controller;

import java.io.IOException;
import java.util.Comparator;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.lukmie.eportal.model.Post;
import pl.lukmie.eportal.service.PostService;

@WebServlet("")
public class HomeController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		savePostsInRequest(request);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}
	
	private void savePostsInRequest(HttpServletRequest request) {
		PostService postService = new PostService();
		List<Post> allPosts = postService.getAllPosts(new Comparator<Post>() {

			@Override
			public int compare(Post p1, Post p2) {
				int p1Vote = p1.getUpVote() - p1.getDownVote();
				int p2Vote = p2.getUpVote() - p2.getDownVote();
				if(p1Vote < p2Vote) {
					return 1;
				} else if(p1Vote > p2Vote) {
					return -1;
				}
				return 0;
			}		
		});
		request.setAttribute("posts", allPosts);
	}
}
