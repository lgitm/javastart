package pl.lukmie.eportal.model;

import java.sql.Timestamp;

public class Vote {
	private long id;
	private long postId;
	private long userId;
	private Timestamp date;
	private VoteType voteType;
	
	public Vote() {
	}

	public Vote(Vote vote) {
		this.id = vote.id;
		this.postId = vote.postId;
		this.userId = vote.userId;
		this.date = new Timestamp(vote.date.getTime());
		this.voteType = vote.voteType;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getPostId() {
		return postId;
	}

	public void setPostId(long postId) {
		this.postId = postId;
	}

	public long getUserId() {
		return userId;
	}

	public void setUserId(long userId) {
		this.userId = userId;
	}

	public Timestamp getDate() {
		return date;
	}

	public void setDate(Timestamp date) {
		this.date = date;
	}

	public VoteType getVoteType() {
		return voteType;
	}

	public void setVoteType(VoteType voteType) {
		this.voteType = voteType;
	}

	@Override
	public String toString() {
		return "Vote [id=" + id + ", postId=" + postId + ", userId=" + userId + ", date=" + date + ", voteType=" + voteType + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + (int) (postId ^ (postId >>> 32));
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (userId ^ (userId >>> 32));
		result = prime * result + ((voteType == null) ? 0 : voteType.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Vote other = (Vote) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (postId != other.postId)
			return false;
		if (id != other.id)
			return false;
		if (userId != other.userId)
			return false;
		if (other.voteType != null)
			return false;
		return true;
	}
	
	
	
	
}
