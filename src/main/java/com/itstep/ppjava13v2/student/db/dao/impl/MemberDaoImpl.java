package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.MemberDao;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Member;
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
public class MemberDaoImpl implements MemberDao {
	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	@Autowired
	private DataSource connectionManager;

	public MemberDaoImpl(DataSource connectionManager) {
		this.connectionManager = connectionManager;
	}

	public MemberDaoImpl() {
	}

	@Override
	public List<Member> findAll() throws DaoException {
		List<Member> memberList = new ArrayList<>();
		String sql = "SELECT memberId, memberFirstName ,memberLastName, memberPhoneNumber , memberGender  FROM members;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			log.trace("Get statement {}", statement);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				memberList.add(getMember(resultSet));
			}
		} catch (SQLException e) {
			log.error("Error member ", e);
			throw new DaoException("Error member", e);
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
		return memberList;
	}

	@Override
	public List<Member> findAllByEntertainerId(long entertainerId) throws DaoException {
		List<Member> memberList = new ArrayList<>();
		String sql = "SELECT members.memberId, memberFirstName ,memberLastName, memberPhoneNumber , memberGender  FROM members , entertainer_members WHERE members.memberId = entertainer_members.memberId AND entertainer_members.entertainerId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setLong(1, entertainerId);
			log.trace("Get statement {}", statement);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				memberList.add(getMember(resultSet));
			}
		} catch (SQLException e) {
			log.error("Error member ", e);
			throw new DaoException("Error member", e);
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
		return memberList;
	}

	private Member getMember(ResultSet resultSet) throws SQLException {
		Member member;
		if (resultSet != null) {
			member = new Member(
					resultSet.getLong("memberId")
					, resultSet.getString("memberFirstName")
					, resultSet.getString("memberLastName")
					, resultSet.getString("memberPhoneNumber")
					, resultSet.getString("memberGender")
			);
		} else {
			throw new SQLException("Member resultSet is null");
		}
		return member;
	}

	@Override
	public Member findById(long id) throws DaoException {
		Member member = null;
		String sql = "SELECT memberId, memberFirstName ,memberLastName, memberPhoneNumber, memberGender FROM members WHERE memberId = ?;";
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
				member = getMember(resultSet);
			} else {
				log.trace("not find member by id = {}", id);
			}
		} catch (SQLException e) {
			log.error("Error get all member ", e);
			throw new DaoException("Error member", e);
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
		return member;
	}

	@Override
	public void update(Member member) throws DaoException {
		if (member.getMemberId() <= 0) {
			save(member);
			return;
		}
		String sql = "UPDATE members SET " +
				"  memberFirstName = ?," +
				"  memberLastName = ?," +
				"  memberPhoneNumber = ?," +
				"  memberGender = ? " +
				"  WHERE memberId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);

			statement.setString(1, member.getMemberFirstName());
			statement.setString(2, member.getMemberLastName());
			statement.setString(3, member.getMemberPhoneNumber());
			statement.setString(4, member.getMemberGender());
			statement.setLong(5, member.getMemberId());

			log.trace("get statement {}", statement);
			statement.executeUpdate();

		} catch (SQLException e) {
			log.error("Error member ", e);
			throw new DaoException("Error member", e);
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
	public void remove(Member member) throws DaoException {
		remove(member.getMemberId());
	}

	@Override
	public void remove(long id) throws DaoException {
		String sql = "DELETE FROM Members WHERE memberId = ?";
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
			log.error("Error member ", e);
			throw new DaoException("Error member", e);
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
	public Member save(Member member) throws DaoException {
		if (member.getMemberId() > 0) {
			update(member);
			return member;
		}
		String sql = "INSERT INTO Members (memberFirstName, memberLastName, memberPhoneNumber, memberGender ) " +
				"VALUES (?,?,?,? );";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, member.getMemberFirstName());
			statement.setString(2, member.getMemberLastName());
			statement.setString(3, member.getMemberPhoneNumber());
			statement.setString(4, member.getMemberGender());
			log.trace("get statement {}", statement);
			statement.executeUpdate();

			// get id member
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				log.trace("try get id last added member");
				if (generatedKeys.next()) {
					member.setMemberId(generatedKeys.getLong(1));
				} else {
					log.warn("Creating member {} failed, no ID obtained", member);
					throw new SQLException("Creating member failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			log.error("Error member ", e);
			throw new DaoException("Error member", e);
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
		return member;
	}

	@Override
	public List<Member> findByMemberFirstName(String memberFirstName) throws DaoException {
		List<Member> memberList = new ArrayList<>();
		String sql = "SELECT members.memberId, memberFirstName ,memberLastName, memberPhoneNumber , memberGender  FROM members  WHERE members.memberFirstName= ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setString(1, memberFirstName);
			log.trace("Get statement {}", statement);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				memberList.add(getMember(resultSet));
			}
		} catch (SQLException e) {
			log.error("Error member ", e);
			throw new DaoException("Error member", e);
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
		return memberList;
	}
}
