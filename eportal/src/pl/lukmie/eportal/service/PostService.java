package pl.lukmie.eportal.service;


import java.sql.Timestamp;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import pl.lukmie.eportal.dao.DAOFactory;
import pl.lukmie.eportal.dao.PostDAO;
import pl.lukmie.eportal.model.Post;
import pl.lukmie.eportal.model.User;

public class PostService {
	
	public void addPost(String name, String description, String url, User user) {
		Post post = createPostObject(name, description, url, user);
		DAOFactory factory = DAOFactory.getDAOFactory();
		PostDAO postDAO = factory.getPostDAO();
		postDAO.create(post);
	}
	
	private Post createPostObject(String name, String description, String url, User user) {
		Post post = new Post();
		post.setName(name);
		post.setDescription(description);
		post.setUrl(url);
		User copyUser = new User(user);
		post.setUser(copyUser);
		post.setTimestamp(new Timestamp(new Date().getTime()));
		return post;
	}
	
	public Post getPostById(long postId) {
		DAOFactory factory = DAOFactory.getDAOFactory();
		PostDAO postDAO = factory.getPostDAO();
		Post post = postDAO.read(postId);
		return post;
	}
	
	public boolean updatePost(Post post) {
		DAOFactory factory = DAOFactory.getDAOFactory();
		PostDAO postDAO = factory.getPostDAO();
		boolean result = postDAO.update(post);
		return result;
	}
	
	public List<Post> getAllPosts(){
		return getAllPosts(null);
	}
	
	public List<Post> getAllPosts(Comparator<Post> comparator){
		DAOFactory factory = DAOFactory.getDAOFactory();
		PostDAO postDAO = factory.getPostDAO();
		List<Post> posts = postDAO.getAll();
		if(comparator != null && posts != null) {
			posts.sort(comparator);
		}
		return posts;
	}
}
