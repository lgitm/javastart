package pl.lukmie.eportal.dao;

import java.util.List;

import pl.lukmie.eportal.model.Post;

public interface PostDAO extends GenericDAO<Post, Long> {
	List<Post> getAll();
}
