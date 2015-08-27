package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.AgentDao;
import com.itstep.ppjava13v2.student.db.dao.CustomerDao;
import com.itstep.ppjava13v2.student.db.dao.EngagementDao;
import com.itstep.ppjava13v2.student.db.dao.EntertainerDao;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Agent;
import com.itstep.ppjava13v2.student.db.domain.Customer;
import com.itstep.ppjava13v2.student.db.domain.Engagement;
import com.itstep.ppjava13v2.student.db.domain.Entertainer;
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
public class EngagementDaoImpl implements EngagementDao {
	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	@Autowired
	private DataSource connectionManager;

	@Autowired
	private AgentDao agentDao;

	@Autowired
	private CustomerDao engagementDao;

	@Autowired
	private EntertainerDao entertainerDao;

	public EngagementDaoImpl(DataSource connectionManager, AgentDao agentDao, CustomerDao engagementDao,
	                         EntertainerDao entertainerDao) {
		this.connectionManager = connectionManager;
		this.agentDao = agentDao;
		this.engagementDao = engagementDao;
		this.entertainerDao = entertainerDao;
	}

	public EngagementDaoImpl() {}

	@Override
	public List<Engagement> findAll() throws DaoException {
		List<Engagement> engagementList = new ArrayList<>();
		String sql = "SELECT * FROM Engagements ;";
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
				Engagement engagement = getEngagement(resultSet);
				engagement.setEngagementAgentList(agentDao.findAllByEngagementId(engagement.getEngagementId()));
				engagement.setEngagementCustomerList(engagementDao.findAllByEntertainerId(engagement.getEngagementId()));
				engagement.setEngagementEntertainerList(entertainerDao.findAllByEngagementId(engagement.getEngagementId()));
				engagementList.add(engagement);
			}

		} catch (SQLException e) {
			log.error("Error engagement ", e);
			throw new DaoException("Error engagement", e);
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
		return engagementList;
	}

	@Override
	public Engagement findById(long id) throws DaoException {
		Engagement engagement = null;
		String sql = "SELECT * FROM Engagements " +
				" WHERE  Engagements.engagementId = ?;";
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
				engagement = getEngagement(resultSet);
				engagement.setEngagementAgentList(agentDao.findAllByEngagementId(engagement.getEngagementId()));
				engagement.setEngagementCustomerList(engagementDao.findAllByEntertainerId(engagement.getEngagementId()));
				engagement.setEngagementEntertainerList(entertainerDao.findAllByEngagementId(engagement.getEngagementId()));
			}

		} catch (SQLException e) {
			log.error("Error get all engagement ", e);
			throw new DaoException("Error engagement", e);
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
		return engagement;
	}

	@Override
	public void update(Engagement engagement) throws DaoException {
		if (engagement.getEngagementId() > 0) {
			String sql = "UPDATE Engagements SET " +
					" engagementStartDate = ?" +
					"  , engagementEndDate = ?" +
					"  , engagementPrice = ?" +
					" WHERE engagementId = ?;";
			Connection connection = null;
			PreparedStatement statement = null;
			try {
				log.trace("Try get connection");
				connection = connectionManager.getConnection();
				log.trace("Got connection {}", connection);
				statement = connection.prepareStatement(sql);

				statement.setDate(1, new Date(engagement.getEngagementStartDate().getTime()));
				statement.setDate(2, new Date(engagement.getEngagementEndDate().getTime()));
				statement.setLong(3, engagement.getEngagementPrice());
				statement.setLong(4, engagement.getEngagementId());

				log.trace("get statement {}", statement);
				statement.executeUpdate();
				changeCommunication(engagement);
			} catch (SQLException e) {
				log.error("Error engagement ", e);
				throw new DaoException("Error engagement", e);
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
			save(engagement);
		}
	}

	@Override
	public void remove(Engagement engagement) throws DaoException {
		remove(engagement.getEngagementId());
	}

	@Override
	public void remove(long id) throws DaoException {
		String sql = "DELETE FROM engagements WHERE engagementId = ?";
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
			log.error("Error engagement ", e);
			throw new DaoException("Error engagement", e);
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
	public Engagement save(Engagement engagement) throws DaoException {
		if (engagement.getEngagementId() > 0) {
			update(engagement);
		} else {

			String sql = "INSERT INTO engagements( " +
					" engagementStartDate" +
					", engagementEndDate" +
					", engagementPrice " +
					") " +
					"  VALUES (?, ?, ?);";
			Connection connection = null;
			PreparedStatement statement = null;
			try {
				log.trace("Try get connection");
				connection = connectionManager.getConnection();
				log.trace("Got connection {}", connection);
				statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

				statement.setDate(1, new Date(engagement.getEngagementStartDate().getTime()));
				statement.setDate(2, new Date(engagement.getEngagementEndDate().getTime()));
				statement.setLong(3, engagement.getEngagementPrice());

				log.trace("get statement {}", statement);
				statement.executeUpdate();

				// get id engagement
				try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
					log.trace("try get id last added engagement");
					if (generatedKeys.next()) {
						engagement.setEngagementId(generatedKeys.getLong(1));
					} else {
						log.warn("Creating engagement {} failed, no ID obtained", engagement);
						throw new SQLException("Creating engagement failed, no ID obtained.");
					}
				}

				//
				changeCommunication(engagement);
			} catch (SQLException e) {
				log.error("Error engagement ", e);
				throw new DaoException("Error engagement", e);
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
		return engagement;

	}

	@Override
	public List<Engagement> findByEngagementPrice(long price) throws DaoException {
		List<Engagement> engagementList = new ArrayList<>();
		String sql = "SELECT * FROM Engagements WHERE engagementPrice = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setLong(1,price);
			log.trace("get statement {}", statement);
			resultSet = statement.executeQuery();

			while (resultSet.next()) {
				Engagement engagement = getEngagement(resultSet);
				engagement.setEngagementAgentList(agentDao.findAllByEngagementId(engagement.getEngagementId()));
				engagement.setEngagementCustomerList(engagementDao.findAllByEntertainerId(engagement.getEngagementId()));
				engagement.setEngagementEntertainerList(entertainerDao.findAllByEngagementId(engagement.getEngagementId()));
				engagementList.add(engagement);
			}

		} catch (SQLException e) {
			log.error("Error engagement ", e);
			throw new DaoException("Error engagement", e);
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
		return engagementList;
	}

	private Engagement getEngagement(ResultSet resultSet) throws SQLException {
		Engagement engagement;
		if (resultSet != null) {
			engagement = new Engagement(resultSet.getLong("engagementId"),
					resultSet.getDate("engagementStartDate"),
					resultSet.getDate("engagementEndDate"),
					resultSet.getLong("engagementPrice")
			);
		} else {
			throw new SQLException("Engagement resultSet is null");
		}
		return engagement;
	}

	private void updateAgent(Long engagementId, Agent agent) throws DaoException {
		if (agent.getAgentId() > 0) {
			String sql = "UPDATE Agents SET agentEngagementId = ? WHERE agentId = ?;";

			Connection connection = null;
			PreparedStatement statement = null;
			try {
				log.trace("Try get connection");
				connection = connectionManager.getConnection();
				log.trace("Got connection {}", connection);
				statement = connection.prepareStatement(sql);
				statement.setLong(1, engagementId);
				statement.setLong(2, agent.getAgentId());
				log.trace("get statement {}", statement);
				statement.executeUpdate();
			} catch (SQLException e) {
				log.error("Error engagement.updateAgent ", e);
				throw new DaoException("Error engagement.updateAgent", e);
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
			agentDao.save(agent);
			updateAgent(engagementId, agent);
		}
	}

	private void updateCustomer(Long engagementId, Customer customer) throws DaoException {
		if (customer.getCustomerId() > 0) {
			String sql = "UPDATE Customers SET customerEngagementId = ? WHERE customerId = ?;";

			Connection connection = null;
			PreparedStatement statement = null;
			try {
				log.trace("Try get connection");
				connection = connectionManager.getConnection();
				log.trace("Got connection {}", connection);
				statement = connection.prepareStatement(sql);
				statement.setLong(1, engagementId);
				statement.setLong(2, customer.getCustomerId());
				log.trace("get statement {}", statement);
				statement.executeUpdate();


			} catch (SQLException e) {
				log.error("Error engagement.updateCustomer ", e);
				throw new DaoException("Error engagement.updateCustomer", e);
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
			engagementDao.save(customer);
			updateCustomer(engagementId, customer);
		}
	}

	private void updateEntertainer(Long engagementId, Entertainer entertainer) throws DaoException {
		if (entertainer.getEntertainerId() > 0) {

			String sql = "UPDATE Entertainers SET entertainerEngagementId = ? WHERE entertainerId = ?;";

			Connection connection = null;
			PreparedStatement statement = null;
			try {
				log.trace("Try get connection");
				connection = connectionManager.getConnection();
				log.trace("Got connection {}", connection);
				statement = connection.prepareStatement(sql);
				statement.setLong(1, engagementId);
				statement.setLong(2, entertainer.getEntertainerId());
				log.trace("get statement {}", statement);
				statement.executeUpdate();


			} catch (SQLException e) {
				log.error("Error engagement.updateEntertainer ", e);
				throw new DaoException("Error engagement.updateEntertainer", e);
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
			entertainerDao.save(entertainer);
			updateEntertainer(engagementId, entertainer);
		}
	}

	private void changeCommunication(Engagement engagement) throws DaoException {
		for (Agent agent : engagement.getEngagementAgentList()) {
			updateAgent(engagement.getEngagementId(), agent);
		}
		for (Customer customer : engagement.getEngagementCustomerList()) {
			updateCustomer(engagement.getEngagementId(), customer);
		}
		for (Entertainer entertainer : engagement.getEngagementEntertainerList()) {
			updateEntertainer(engagement.getEngagementId(), entertainer);
		}
	}

}
