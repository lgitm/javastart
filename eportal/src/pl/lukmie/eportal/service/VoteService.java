package pl.lukmie.eportal.service;

import java.sql.Timestamp;
import java.util.Date;

import pl.lukmie.eportal.dao.DAOFactory;
import pl.lukmie.eportal.dao.VoteDAO;
import pl.lukmie.eportal.model.Vote;
import pl.lukmie.eportal.model.VoteType;

public class VoteService {
	public Vote addVote(long postId, long userId, VoteType voteType) {
		Vote vote = new Vote();
		vote.setPostId(postId);
		vote.setUserId(userId);
		vote.setDate(new Timestamp(new Date().getTime()));
		vote.setVoteType(voteType);
		DAOFactory factory = DAOFactory.getDAOFactory();
		VoteDAO voteDAO = factory.getVoteDAO();
		vote = voteDAO.create(vote);
		return vote;
	}
	
	public Vote updateVote(long postId, long userId, VoteType voteType) {
		DAOFactory factory = DAOFactory.getDAOFactory();
		VoteDAO voteDAO = factory.getVoteDAO();
		Vote voteToUpdate = voteDAO.getVoteByUserIdPostId(userId, postId);
		if(voteToUpdate != null) {
			voteToUpdate.setVoteType(voteType);
			voteDAO.update(voteToUpdate);
		}
		return voteToUpdate;
	}
	
	public Vote addOrUpdateVote(long postId, long userId, VoteType voteType) {
		DAOFactory factory = DAOFactory.getDAOFactory();
		VoteDAO voteDAO = factory.getVoteDAO();
		Vote vote = voteDAO.getVoteByUserIdPostId(userId, postId);
		Vote resultVote = null;
		if(vote == null) {
			resultVote = addVote(postId, userId, voteType);
		} else {
			resultVote = updateVote(postId, userId, voteType);
		}
		return resultVote;
	}
	
	public Vote getVoteByPostUserId(long postId, long userId) {
		DAOFactory factory = DAOFactory.getDAOFactory();
		VoteDAO voteDAO = factory.getVoteDAO();
		Vote vote = voteDAO.getVoteByUserIdPostId(userId, postId);
		return vote;
	}
}
