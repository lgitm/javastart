package pl.lukmie.eportal.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import pl.lukmie.eportal.model.Post;
import pl.lukmie.eportal.model.User;
import pl.lukmie.eportal.model.Vote;
import pl.lukmie.eportal.model.VoteType;
import pl.lukmie.eportal.service.PostService;
import pl.lukmie.eportal.service.VoteService;

@WebServlet("/vote")
public class VoteController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		User loggedUser = (User) request.getSession().getAttribute("user");
		if(loggedUser != null) {
			VoteType voteType = VoteType.valueOf(request.getParameter("vote"));
			long userId  = loggedUser.getId();
			long postId = Long.parseLong(request.getParameter("post_id"));
			updateVote(userId, postId, voteType);
		}
		response.sendRedirect(request.getContextPath() + "/");
	}
	
	private void updateVote(long userId, long postId, VoteType voteType) {
		VoteService voteService = new VoteService();
		Vote existingVote = voteService.getVoteByPostUserId(postId, userId);
		Vote updatedVote = voteService.addOrUpdateVote(postId, userId, voteType);
		if(existingVote != updatedVote || !updatedVote.equals(existingVote)) {
			updatePost(postId, existingVote, updatedVote);
		}
	}

	private void updatePost(long postId, Vote oldVote, Vote newVote) {
		PostService postService = new PostService();
		Post postById = postService.getPostById(postId);
		Post updatedPost = null;
		if(oldVote == null && newVote != null) {
			updatedPost = addPostVote(postById, newVote.getVoteType());
		} else if(oldVote != null && newVote != null) {
			updatedPost = removePostVote(postById, oldVote.getVoteType());
			updatedPost = addPostVote(updatedPost, newVote.getVoteType());
		}
		postService.updatePost(updatedPost);
	}
	
	private Post addPostVote(Post post, VoteType voteType) {
		Post postCopy = new Post(post);
		if(voteType == VoteType.VOTE_UP) {
			postCopy.setUpVote(postCopy.getUpVote() + 1);
		} else if(voteType == VoteType.VOTE_DOWN) {
			postCopy.setDownVote(postCopy.getDownVote() + 1);
		}
		return postCopy;
	}
	
	private Post removePostVote(Post post, VoteType voteType) {
		Post postCopy = new Post(post);
		if(voteType == VoteType.VOTE_UP) {
			postCopy.setUpVote(postCopy.getUpVote() - 1);
		} else if(voteType == VoteType.VOTE_DOWN) {
			postCopy.setDownVote(postCopy.getDownVote() - 1);
		}
		return postCopy;
	}

}
