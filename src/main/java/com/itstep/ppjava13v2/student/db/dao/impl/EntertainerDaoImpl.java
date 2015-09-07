package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.EntertainerDao;
import com.itstep.ppjava13v2.student.db.dao.MemberDao;
import com.itstep.ppjava13v2.student.db.dao.MusicalStyleDao;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Entertainer;
import com.itstep.ppjava13v2.student.db.domain.Member;
import com.itstep.ppjava13v2.student.db.domain.MusicalStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
@Transactional
public class EntertainerDaoImpl implements EntertainerDao {
	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	@Autowired
	private DataSource connectionManager;

	@Autowired
	private MusicalStyleDao musicalStyleDao;

	@Autowired
	private MemberDao memberDao;

	public EntertainerDaoImpl(DataSource connectionManager, MusicalStyleDao musicalStyleDao, MemberDao memberDao) {
		this.connectionManager = connectionManager;
		this.musicalStyleDao = musicalStyleDao;
		this.memberDao = memberDao;
	}

	public EntertainerDaoImpl() {
	}


	@Override
	public List<Entertainer> findAll() throws DaoException {
		List<Entertainer> entertainerList = new ArrayList<>();
		String sql = "SELECT" +
				"  entertainerId," +
				"  entertainerStageName," +
				"  entertainerStreetAddress," +
				"  entertainerCity," +
				"  entertainerState," +
				"  entertainerPhoneNumber," +
				"  entertainerWebPage," +
				"  entertainerEmailAddress," +
				"  entertainerDateEntered" +
				"  FROM entertainers;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			log.trace("get statement {}", statement);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Entertainer entertainer = getEntertainer(resultSet);
				entertainer.setEntertainerMemberList(memberDao.findAllByEntertainerId(entertainer.getEntertainerId()));
				entertainer.setEntertainerMusicalStyleList(musicalStyleDao.findAllByEntertainerId(entertainer.getEntertainerId()));
				entertainerList.add(entertainer);
			}
		} catch (SQLException e) {
			log.error("Error entertainer ", e);
			throw new DaoException("Error entertainer", e);
		} finally {
			try {
				if (resultSet != null) {
					log.trace("Close resultSet {}", resultSet);
					resultSet.close();
				}
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
		return entertainerList;
	}

	@Override
	public List<Entertainer> findAllByEngagementId(long engagementId) throws DaoException {
		List<Entertainer> entertainerList = new ArrayList<>();
		String sql = "SELECT" +
				"  entertainerId," +
				"  entertainerStageName," +
				"  entertainerStreetAddress," +
				"  entertainerCity," +
				"  entertainerState," +
				"  entertainerPhoneNumber," +
				"  entertainerWebPage," +
				"  entertainerEmailAddress," +
				"  entertainerDateEntered" +
				"  FROM entertainers " +
				"  WHERE entertainerEngagementId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setLong(1, engagementId);
			log.trace("get statement {}", statement);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Entertainer entertainer = getEntertainer(resultSet);
				entertainer.setEntertainerMemberList(memberDao.findAllByEntertainerId(entertainer.getEntertainerId()));
				entertainer.setEntertainerMusicalStyleList(musicalStyleDao.findAllByEntertainerId(entertainer.getEntertainerId()));
				entertainerList.add(entertainer);
			}
		} catch (SQLException e) {
			log.error("Error entertainer ", e);
			throw new DaoException("Error entertainer", e);
		} finally {
			try {
				if (resultSet != null) {
					log.trace("Close resultSet {}", resultSet);
					resultSet.close();
				}
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
		return entertainerList;
	}


	@Override
	public Entertainer findById(long id) throws DaoException {
		Entertainer entertainer = null;
		String sql = "SELECT" +
				"  entertainerId," +
				"  entertainerStageName," +
				"  entertainerStreetAddress," +
				"  entertainerCity," +
				"  entertainerState," +
				"  entertainerPhoneNumber," +
				"  entertainerWebPage," +
				"  entertainerEmailAddress," +
				"  entertainerDateEntered" +
				"  FROM entertainers " +
				"  WHERE entertainerId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			log.trace("get statement {}", statement);
			resultSet = statement.executeQuery();

			if (resultSet.next()) {
				entertainer = getEntertainer(resultSet);
				entertainer.setEntertainerMemberList(memberDao.findAllByEntertainerId(entertainer.getEntertainerId()));
				entertainer.setEntertainerMusicalStyleList(musicalStyleDao.findAllByEntertainerId(entertainer.getEntertainerId()));

			} else {
				log.trace("not find entertainer by id = {}", id);
			}
		} catch (SQLException e) {
			log.error("Error get all entertainer ", e);
			throw new DaoException("Error entertainer", e);
		} finally {
			try {
				if (resultSet != null) {
					log.trace("Close resultSet {}", resultSet);
					resultSet.close();
				}
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
		return entertainer;
	}

	@Override
	public void update(Entertainer entertainer) throws DaoException {
		if (entertainer.getEntertainerId() <= 0) {
			save(entertainer);
			return;
		}
		String sql = "UPDATE entertainers SET " +
				"  entertainerStageName = ?," +
				"  entertainerStreetAddress = ?," +
				"  entertainerCity = ?," +
				"  entertainerState = ?," +
				"  entertainerPhoneNumber = ?," +
				"  entertainerWebPage = ?," +
				"  entertainerEmailAddress = ?," +
				"  entertainerDateEntered = ? " +
				"  WHERE entertainerId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);

			statement.setString(1, entertainer.getEntertainerStageName());
			statement.setString(2, entertainer.getEntertainerStreetAddress());
			statement.setString(3, entertainer.getEntertainerCity());
			statement.setString(4, entertainer.getEntertainerState());
			statement.setString(5, entertainer.getEntertainerPhoneNumber());
			statement.setString(6, entertainer.getEntertainerWebPage());
			statement.setString(7, entertainer.getEntertainerEmailAddress());
			statement.setDate(8, new Date(entertainer.getEntertainerDateEntered().getTime()));
			statement.setLong(9, entertainer.getEntertainerId());

			log.trace("get statement {}", statement);
			statement.executeUpdate();
			changeCommunication(entertainer);
		} catch (SQLException e) {
			log.error("Error entertainer ", e);
			throw new DaoException("Error entertainer", e);
		} finally {
			try {
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
	}

	@Override
	public void remove(Entertainer entertainer) throws DaoException {
		remove(entertainer.getEntertainerId());
		entertainer.setEntertainerId(0);
	}

	@Override
	public void remove(long id) throws DaoException {
		String sql = "DELETE FROM entertainers WHERE entertainerId = ?";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setLong(1, id);
			log.trace("get statement {}", statement);
			statement.executeUpdate();

		} catch (SQLException e) {
			log.error("Error entertainer ", e);
			throw new DaoException("Error entertainer", e);
		} finally {
			try {
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
	}

	@Override
	public Entertainer save(Entertainer entertainer) throws DaoException {
		if (entertainer.getEntertainerId() > 0) {
			update(entertainer);
			return entertainer;
		}
		String sql = "INSERT INTO entertainers( " +
				"  entertainerStageName," +
				"  entertainerStreetAddress," +
				"  entertainerCity," +
				"  entertainerState," +
				"  entertainerPhoneNumber," +
				"  entertainerWebPage," +
				"  entertainerEmailAddress," +
				"  entertainerDateEntered" +
				") " +
				"  VALUES (?, ?, ?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, entertainer.getEntertainerStageName());
			statement.setString(2, entertainer.getEntertainerStreetAddress());
			statement.setString(3, entertainer.getEntertainerCity());
			statement.setString(4, entertainer.getEntertainerState());
			statement.setString(5, entertainer.getEntertainerPhoneNumber());
			statement.setString(6, entertainer.getEntertainerWebPage());
			statement.setString(7, entertainer.getEntertainerEmailAddress());
			statement.setDate(8, new Date(entertainer.getEntertainerDateEntered().getTime()));

			log.trace("get statement {}", statement);
			statement.executeUpdate();

			// get id entertainer
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				log.trace("try get id last added entertainer");
				if (generatedKeys.next()) {
					entertainer.setEntertainerId(generatedKeys.getLong(1));
				} else {
					log.warn("Creating entertainer {} failed, no ID obtained", entertainer);
					throw new SQLException("Creating entertainer failed, no ID obtained.");
				}
			}
			changeCommunication(entertainer);
		} catch (SQLException e) {
			log.error("Error entertainer ", e);
			throw new DaoException("Error entertainer", e);
		} finally {
			try {
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
		return entertainer;
	}

	private void changeCommunication(Entertainer entertainer) throws DaoException {
			for (MusicalStyle style : entertainer.getEntertainerMusicalStyleList()){
				updateMusical(entertainer.getEntertainerId(),style);
			}
			for (Member member : entertainer.getEntertainerMemberList()){
				updateMember(entertainer.getEntertainerId(), member);
			}

	}

	private void updateMember(long entertainerId, Member member) throws DaoException {
		if (member.getMemberId() > 0) {
			String sql = "INSERT INTO entertainer_members SET entertainerId = ?, memberId = ?;";

			Connection connection = null;
			PreparedStatement statement = null;
			try {
				log.trace("Try get connection");
				connection = connectionManager.getConnection();
				log.trace("Got connection {}", connection);
				statement = connection.prepareStatement(sql);
				statement.setLong(1, entertainerId);
				statement.setLong(2, member.getMemberId());
				log.trace("get statement {}", statement);
				statement.executeUpdate();
			} catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ignored){

			}catch (SQLException e) {
				log.error("Error entertainer.updateMember ", e);
				throw new DaoException("Error entertainer.updateMember", e);
			} finally {
				try {
					if (statement != null) {
						log.trace("Close statement {}", statement);
						statement.close();
					}
					if (connection != null) {
						log.trace("Close connection {}", connection);
						connection.close();
					}
				} catch (SQLException e) {
					log.error("Error close resource.close() ", e);
				}
			}
		} else {
			memberDao.save(member);
			updateMember(entertainerId, member);
		}
	}

	private void updateMusical(long entertainerId, MusicalStyle style) throws DaoException{
		if (style.getMusicalStyleId() > 0) {
			String sql = "INSERT INTO entertainer_styles SET entertainerId = ?, musicalStyleId = ?;";

			Connection connection = null;
			PreparedStatement statement = null;
			try {
				log.trace("Try get connection");
				connection = connectionManager.getConnection();
				log.trace("Got connection {}", connection);
				statement = connection.prepareStatement(sql);
				statement.setLong(1, entertainerId);
				statement.setLong(2, style.getMusicalStyleId());
				log.trace("get statement {}", statement);
				statement.executeUpdate();
			}  catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException ignored){

			}catch(SQLException e) {
				log.error("Error entertainer.updateMusical ", e);
				throw new DaoException("Error entertainer.updateMusical", e);
			} finally {
				try {
					if (statement != null) {
						log.trace("Close statement {}", statement);
						statement.close();
					}
					if (connection != null) {
						log.trace("Close connection {}", connection);
						connection.close();
					}
				} catch (SQLException e) {
					log.error("Error close resource.close() ", e);
				}
			}
		} else {
			musicalStyleDao.save(style);
			updateMusical(entertainerId,style);
		}
	}

	@Override
	public List<Entertainer> findByEntertainerFirstName(String entertainerStageName) throws DaoException {
		List<Entertainer> entertainerList = new ArrayList<>();
		String sql = "SELECT" +
				"  entertainerId," +
				"  entertainerStageName," +
				"  entertainerStreetAddress," +
				"  entertainerCity," +
				"  entertainerState," +
				"  entertainerPhoneNumber," +
				"  entertainerWebPage," +
				"  entertainerEmailAddress," +
				"  entertainerDateEntered" +
				"  FROM entertainers " +
				"  WHERE entertainerStageName = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setString(1, entertainerStageName);
			log.trace("get statement {}", statement);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Entertainer entertainer = getEntertainer(resultSet);
				entertainer.setEntertainerMemberList(memberDao.findAllByEntertainerId(entertainer.getEntertainerId()));
				entertainer.setEntertainerMusicalStyleList(musicalStyleDao.findAllByEntertainerId(entertainer.getEntertainerId()));
				entertainerList.add(entertainer);
			}
		} catch (SQLException e) {
			log.error("Error entertainer ", e);
			throw new DaoException("Error entertainer", e);
		} finally {
			try {
				if (resultSet != null) {
					log.trace("Close resultSet {}", resultSet);
					resultSet.close();
				}
				if (statement != null) {
					log.trace("Close statement {}", statement);
					statement.close();
				}
				if (connection != null) {
					log.trace("Close connection {}", connection);
					connection.close();
				}
			} catch (SQLException e) {
				log.error("Error close resource.close() ", e);
			}
		}
		return entertainerList;
	}

	private Entertainer getEntertainer(ResultSet resultSet) throws SQLException {
		Entertainer entertainer;
		if (resultSet != null) {
			entertainer = new Entertainer(resultSet.getLong("entertainerId"),
					resultSet.getString("entertainerStageName"),
					resultSet.getString("entertainerStreetAddress"),
					resultSet.getString("entertainerCity"),
					resultSet.getString("entertainerState"),
					resultSet.getString("entertainerPhoneNumber"),
					resultSet.getString("entertainerWebPage"),
					resultSet.getString("entertainerEmailAddress"),
					resultSet.getDate("entertainerDateEntered"));
		} else {
			throw new SQLException("Entertainer resultSet is null");
		}
		return entertainer;
	}
}
