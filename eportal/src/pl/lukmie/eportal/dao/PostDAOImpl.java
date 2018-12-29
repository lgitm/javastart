package pl.lukmie.eportal.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import pl.lukmie.eportal.model.Post;
import pl.lukmie.eportal.model.User;
import pl.lukmie.eportal.util.ConnectionProvider;

public class PostDAOImpl implements PostDAO {
	
	private static final String CREATE_POST = "INSERT INTO post(name, description, url, user_id, date, up_vote, down_vote) VALUES(:name, :description, :url, :user_id, :date, :up_vote, :down_vote);"; 
	private static final String READ_ALL_POST = "SELECT user.user_id, username, email, is_active, password, post_id, name, description, url, date, up_vote, down_vote FROM post LEFT JOIN user ON post.user_id = user.user_id;";
	private static final String READ_POST = "SELECT user.user_id, username, email, is_active, password, post_id, name, description, url, date, up_vote, down_vote FROM post LEFT JOIN user ON post.user_id = user.user_id WHERE post_id = :post_id;";
	private static final String UPDATE_POST = "UPDATE post SET name = :name, description = :description, url = :url, user_id = :user_id, date = :date, up_vote = :up_vote, down_vote = :down_vote WHERE post_id = :post_id;";
	
	private NamedParameterJdbcTemplate template;
	
	public PostDAOImpl(){
		template = new NamedParameterJdbcTemplate(ConnectionProvider.getDataSource());
	}
	
	@Override
	public Post create(Post post) {
		Post resultPost = new Post(post);
		KeyHolder holder = new GeneratedKeyHolder();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("name", post.getName());
		paramMap.put("description", post.getDescription());
		paramMap.put("url", post.getUrl());
		paramMap.put("user_id", post.getUser().getId());
		paramMap.put("date", post.getTimestamp());
		paramMap.put("up_vote", post.getUpVote());
		paramMap.put("down_vote", post.getDownVote());
		SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
		int update = template.update(CREATE_POST, paramSource, holder);
		if(update > 0) {
			resultPost.setId(holder.getKey().longValue());
		}
		return resultPost;
	}

	@Override
	public Post read(Long primaryKey) {
		SqlParameterSource paramSource = new MapSqlParameterSource("post_id", primaryKey);
		Post post = template.queryForObject(READ_POST, paramSource, new PostRowMapper());
		return post;
	}

	@Override
	public boolean update(Post post) {
		boolean result = false;
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("post_id", post.getId());
		paramMap.put("name", post.getName());
		paramMap.put("description", post.getDescription());
		paramMap.put("url", post.getUrl());
		paramMap.put("user_id", post.getUser().getId());
		paramMap.put("date", post.getTimestamp());
		paramMap.put("up_vote", post.getUpVote());
		paramMap.put("down_vote", post.getDownVote());
		SqlParameterSource paramSource = new MapSqlParameterSource(paramMap);
		int update = template.update(UPDATE_POST, paramSource);
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
	public List<Post> getAll() {
		List<Post> posts = template.query(READ_ALL_POST, new PostRowMapper());
		return posts;
	}
	
	
	private class PostRowMapper implements RowMapper<Post>{

		@Override
		public Post mapRow(ResultSet resultSet, int row) throws SQLException {
			Post post = new Post();
			post.setId(resultSet.getLong("post_id"));
			post.setName(resultSet.getString("name"));
			post.setDescription(resultSet.getString("description"));
			post.setUrl(resultSet.getString("url"));
			post.setUpVote(resultSet.getInt("up_vote"));
			post.setDownVote(resultSet.getInt("down_vote"));
			User user = new User();
			user.setId(resultSet.getLong("user_id"));
			user.setUsername(resultSet.getString("username"));
			user.setEmail(resultSet.getString("email"));
			user.setPassword(resultSet.getString("password"));
			post.setUser(user);
			return post;			
		}
		
	}

}
