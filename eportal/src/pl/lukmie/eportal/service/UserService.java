package pl.lukmie.eportal.service;

import pl.lukmie.eportal.dao.DAOFactory;
import pl.lukmie.eportal.dao.UserDAO;
import pl.lukmie.eportal.model.User;

public class UserService {
	
	public void addUser(String username, String email, String password) {
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(password);
		user.setActive(true);
		DAOFactory factory = DAOFactory.getDAOFactory();
		UserDAO userDAO = factory.getUserDAO();
		userDAO.create(user);
	}
	
	public User getUserById(long userId) {
		DAOFactory factory = DAOFactory.getDAOFactory();
		UserDAO userDAO = factory.getUserDAO();
		User user = userDAO.read(userId);
		return user;
	}
	
	public User getUserByUsername(String username) {
		DAOFactory factory = DAOFactory.getDAOFactory();
		UserDAO userDAO = factory.getUserDAO();
		User user = userDAO.getUserByUsername(username);
		return user;
	}
}
