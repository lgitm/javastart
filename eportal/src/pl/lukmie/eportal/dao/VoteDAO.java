package pl.lukmie.eportal.dao;

import pl.lukmie.eportal.model.Vote;

public interface VoteDAO extends GenericDAO<Vote, Long> {
	public Vote getVoteByUserIdPostId(long userId, long postId);
}
