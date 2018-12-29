package pl.lukmie.eportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import pl.lukmie.eportal.model.Vote;
import pl.lukmie.eportal.model.VoteType;
import pl.lukmie.eportal.util.ConnectionProvider;

public class VoteDAOImpl implements VoteDAO {
	
	private static final String CREATE_VOTE = "INSERT INTO vote(post_id, user_id, date, type) VALUES(:post_id, :user_id, :date, :type);";
	private static final String READ_VOTE = "SELECT vote_id, post_id, user_id, date, type FROM vote WHERE vote_id = :vote_id;";
	private static final String READ_VOTE_BY_POST_USE_IDS = "SELECT vote_id, post_id, user_id, date, type FROM vote WHERE user_id = :user_id AND post_id = :post_id;";
	private static final String UPDATE_VOTE = "UPDATE vote SET date = :date, type = :type WHERE vote_id = :vote_id;";
	
	private NamedParameterJdbcTemplate template;
	
	public VoteDAOImpl() {
		template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
	}
	
	@Override
	public Vote create(Vote vote) {
		Vote copyVote = new Vote(vote);
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("post_id", copyVote.getPostId());
		paramMap.put("user_id", copyVote.getUserId());
		paramMap.put("date", copyVote.getDate());
		paramMap.put("type", copyVote.getVoteType().toString());
		KeyHolder holder = new GeneratedKeyHolder();
		SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
		int update = template.update(CREATE_VOTE, paramSource, holder);
		if(update > 0) {
			copyVote.setId(holder.getKey().longValue());
		}
		return copyVote;
	}

	@Override
	public Vote read(Long primaryKey) {
		SqlParameterSource paramSource = new MapSqlParameterSource("vote_id", primaryKey);
		Vote vote = template.queryForObject(READ_VOTE, paramSource, new VoteRowMapper());
		return vote;
	}

	@Override
	public boolean update(Vote vote) {
		boolean result = false;
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("date", vote.getDate());
		paramMap.put("type", vote.getVoteType().toString());
		paramMap.put("vote_id", vote.getId());
		SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
		int update = template.update(UPDATE_VOTE, paramSource);
		if(update > 0) {
			result = true;
		}
		return result;
	}

	@Override
	public boolean delete(Long key) {
		return false;
	}

	@Override
	public List<Vote> getAll() {
		return null;
	}

	@Override
	public Vote getVoteByUserIdPostId(long userId, long postId) {
		Map<String, Object> paramMap = new HashMap<>();
		paramMap.put("user_id", userId);
		paramMap.put("post_id", postId);
		SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
		Vote vote = null;
		try {
			vote = template.queryForObject(READ_VOTE_BY_POST_USE_IDS, paramSource, new VoteRowMapper());
		} catch (EmptyResultDataAccessException e) {
			
		}
		
		return vote;
	}
	
	private class VoteRowMapper implements RowMapper<Vote> {
		@Override
		public Vote mapRow(ResultSet resultSet, int row) throws SQLException {
			Vote vote = new Vote();
			vote.setId(resultSet.getLong("vote_id"));
			vote.setUserId(resultSet.getLong("user_id"));
			vote.setPostId(resultSet.getLong("post_id"));
			vote.setDate(resultSet.getTimestamp("date"));
			vote.setVoteType(VoteType.valueOf(resultSet.getString("type")));
			return vote;
		}
	}
	
}
