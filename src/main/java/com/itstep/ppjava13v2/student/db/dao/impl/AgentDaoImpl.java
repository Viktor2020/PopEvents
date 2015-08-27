package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.AgentDao;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Agent;
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
public class AgentDaoImpl implements AgentDao {

	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	@Autowired
	private DataSource connectionManager;

	public AgentDaoImpl(DataSource connectionManager) {
		this.connectionManager = connectionManager;
	}

	public AgentDaoImpl() {
	}

	@Override
	public List<Agent> findAll() throws DaoException {
		List<Agent> agentList = new ArrayList<>();
		String sql = "SELECT" +
				"  agentId," +
				"  agentFirstName," +
				"  agentLastName," +
				"  agentStreetAddress," +
				"  agentCity," +
				"  agentState," +
				"  agentPhoneNumber," +
				"  agentDateHired," +
				"  agentSalary," +
				"  agentCommissionRate" +
				"  FROM agents;";
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
				agentList.add(getAgent(resultSet));
			}
		} catch (SQLException e) {
			log.error("Error agent ", e);
			throw new DaoException("Error agent", e);
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
		return agentList;
	}

	@Override
	public List<Agent> findAllByEngagementId(long engagementId) throws DaoException {
		List<Agent> agentList = new ArrayList<>();
		String sql = "SELECT" +
				"  agentId," +
				"  agentFirstName," +
				"  agentLastName," +
				"  agentStreetAddress," +
				"  agentCity," +
				"  agentState," +
				"  agentPhoneNumber," +
				"  agentDateHired," +
				"  agentSalary," +
				"  agentCommissionRate" +
				"  FROM agents " +
				"  WHERE agentEngagementId = ?;";
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
				agentList.add(getAgent(resultSet));
			}
		} catch (SQLException e) {
			log.error("Error agent ", e);
			throw new DaoException("Error agent", e);
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
		return agentList;
	}


	@Override
	public Agent findById(long id) throws DaoException {
		Agent agent = null;
		String sql = "SELECT" +
				"  agentId," +
				"  agentFirstName," +
				"  agentLastName," +
				"  agentStreetAddress," +
				"  agentCity," +
				"  agentState," +
				"  agentPhoneNumber," +
				"  agentDateHired," +
				"  agentSalary," +
				"  agentCommissionRate" +
				"  FROM agents " +
				"  WHERE agentId = ?;";
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
				agent = getAgent(resultSet);
			} else {
				log.trace("not find agent by id = {}", id);
			}
		} catch (SQLException e) {
			log.error("Error get all agent ", e);
			throw new DaoException("Error agent", e);
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
		return agent;
	}

	@Override
	public List<Agent> findByAgentFirstName(String agentFirstName) throws DaoException {
		List<Agent> agentList = new ArrayList<>();
		String sql = "SELECT" +
				"  agentId," +
				"  agentFirstName," +
				"  agentLastName," +
				"  agentStreetAddress," +
				"  agentCity," +
				"  agentState," +
				"  agentPhoneNumber," +
				"  agentDateHired," +
				"  agentSalary," +
				"  agentCommissionRate" +
				"  FROM agents " +
				"  WHERE agentFirstName = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setString(1, agentFirstName);
			log.trace("get statement {}", statement);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				agentList.add(getAgent(resultSet));
			}
		} catch (SQLException e) {
			log.error("Error agent ", e);
			throw new DaoException("Error agent", e);
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
		return agentList;
	}

	@Override
	public void update(Agent agent) throws DaoException {
		if (agent.getAgentId() <= 0) {
			save(agent);
			return;
		}
		String sql = "UPDATE agents SET " +
				"  agentFirstName = ?," +
				"  agentLastName = ?," +
				"  agentStreetAddress = ?," +
				"  agentCity = ?," +
				"  agentState = ?," +
				"  agentPhoneNumber = ?," +
				"  agentDateHired = ?," +
				"  agentSalary = ?," +
				"  agentCommissionRate = ? " +
				"  WHERE agentId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);

			statement.setString(1, agent.getAgentFirstName());
			statement.setString(2, agent.getAgentLastName());
			statement.setString(3, agent.getAgentStreetAddress());
			statement.setString(4, agent.getAgentCity());
			statement.setString(5, agent.getAgentState());
			statement.setString(6, agent.getAgentPhoneNumber());
			statement.setDate(7, new Date(agent.getAgentDateHired().getTime()));
			statement.setLong(8, agent.getAgentSalary());
			statement.setLong(9, agent.getAgentCommissionRate());
			statement.setLong(10, agent.getAgentId());

			log.trace("get statement {}", statement);
			statement.executeUpdate();

		} catch (SQLException e) {
			log.error("Error agent ", e);
			throw new DaoException("Error agent", e);
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
	public void remove(Agent agent) throws DaoException {
		remove(agent.getAgentId());
	}

	@Override
	public void remove(long id) throws DaoException {
		String sql = "DELETE FROM agents WHERE agentId = ?";
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
			log.error("Error agent ", e);
			throw new DaoException("Error agent", e);
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
	public Agent save(Agent agent) throws DaoException {
		if (agent.getAgentId() > 0) {
			update(agent);
			return agent;
		}
		String sql = "INSERT INTO agents( " +
				"  agentFirstName," +
				"  agentLastName," +
				"  agentStreetAddress," +
				"  agentCity," +
				"  agentState," +
				"  agentPhoneNumber," +
				"  agentDateHired," +
				"  agentSalary," +
				"  agentCommissionRate) " +
				"  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, agent.getAgentFirstName());
			statement.setString(2, agent.getAgentLastName());
			statement.setString(3, agent.getAgentStreetAddress());
			statement.setString(4, agent.getAgentCity());
			statement.setString(5, agent.getAgentState());
			statement.setString(6, agent.getAgentPhoneNumber());
			statement.setDate(7, new Date(agent.getAgentDateHired().getTime()));
			statement.setLong(8, agent.getAgentSalary());
			statement.setLong(9, agent.getAgentCommissionRate());

			log.trace("get statement {}", statement);
			statement.executeUpdate();


			// get id agent
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				log.trace("try get id last added agent");
				if (generatedKeys.next()) {
					agent.setAgentId(generatedKeys.getLong(1));
				} else {
					log.warn("Creating agent {} failed, no ID obtained", agent);
					throw new SQLException("Creating agent failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			log.error("Error agent ", e);
			throw new DaoException("Error agent", e);
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
		return agent;
	}

	private Agent getAgent(ResultSet resultSet) throws SQLException {
		Agent agent;
		if (resultSet != null) {
			agent = new Agent(resultSet.getLong("agentId"),
					resultSet.getString("agentFirstName"),
					resultSet.getString("agentLastName"),
					resultSet.getString("agentStreetAddress"),
					resultSet.getString("agentCity"),
					resultSet.getString("agentState"),
					resultSet.getString("agentPhoneNumber"),
					resultSet.getDate("agentDateHired"),
					resultSet.getLong("agentSalary"),
					resultSet.getLong("agentCommissionRate"));
		} else {
			throw new SQLException("Agent resultSet is null");
		}
		return agent;
	}
}
