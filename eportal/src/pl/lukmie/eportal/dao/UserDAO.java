package pl.lukmie.eportal.dao;

import java.util.List;

import pl.lukmie.eportal.model.User;

public interface UserDAO extends GenericDAO<User, Long> {
	List<User> getAll();
	User getUserByUsername(String username);
}
