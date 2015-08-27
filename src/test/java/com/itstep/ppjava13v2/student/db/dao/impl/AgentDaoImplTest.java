package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.AgentDao;
import com.itstep.ppjava13v2.student.db.domain.Agent;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AgentDaoImplTest {

	static DataSource connectionManager = new DataSource() {

		@Override
		public <T> T unwrap(Class<T> iface) throws SQLException {
			return null;
		}

		@Override
		public boolean isWrapperFor(Class<?> iface) throws SQLException {
			return false;
		}

		@Override
		public PrintWriter getLogWriter() throws SQLException {
			return null;
		}

		@Override
		public void setLogWriter(PrintWriter out) throws SQLException {

		}

		@Override
		public void setLoginTimeout(int seconds) throws SQLException {

		}

		@Override
		public int getLoginTimeout() throws SQLException {
			return 0;
		}

		@Override
		public Logger getParentLogger() throws SQLFeatureNotSupportedException {
			return null;
		}

		@Override
		public Connection getConnection() throws SQLException {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306/testPopEvent", "root", "qqqq");
		}

		@Override
		public Connection getConnection(String username, String password) throws SQLException {
			return DriverManager.getConnection("jdbc:mysql://localhost:3306", username, password);
		}

	};

	@BeforeClass
	public static void setUpClass() throws Exception {
		String sqlCreateTestDb = "CREATE DATABASE IF NOT EXISTS testPopEvent;";
		Connection connection = null;
		Connection connection1 = null;
		Connection connection2 = null;
		Connection connection3 = null;
		Connection connection4 = null;
		Connection connection5 = null;
		Connection connection6 = null;
		Connection connection7 = null;
		Connection connection8 = null;
		try {
			connection = connectionManager.getConnection("root", "qqqq");
			connection.createStatement().execute(sqlCreateTestDb);
			connection.close();
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlCreateTableAgents);

			connection1 = connectionManager.getConnection();
			connection1.createStatement().execute(R.sqlCreateTableCustomers);

			connection2 = connectionManager.getConnection();
			connection2.createStatement().execute(R.sqlCreateTableEngagements);

			connection3 = connectionManager.getConnection();
			connection3.createStatement().execute(R.sqlCreateTableEntertainers);

			connection4 = connectionManager.getConnection();
			connection4.createStatement().execute(R.sqlCreateTableMember);

			connection5 = connectionManager.getConnection();
			connection5.createStatement().execute(R.sqlCreateTableMusicalStyles);

			connection6 = connectionManager.getConnection();
			connection6.createStatement().execute(R.sqlCreateTableEntertainer_Members);

			connection7 = connectionManager.getConnection();
			connection7.createStatement().execute(R.sqlCreateTableEntertainer_Styles);

			connection8 = connectionManager.getConnection();
			connection8.createStatement().execute(R.sqlCreateTableCustomer_Styles);

		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection1 != null) {
				connection1.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
			if (connection3 != null) {
				connection3.close();
			}
			if (connection4 != null) {
				connection4.close();
			}
			if (connection5 != null) {
				connection5.close();
			}
			if (connection6 != null) {
				connection6.close();
			}
			if (connection7 != null) {
				connection7.close();
			}
			if (connection8 != null) {
				connection8.close();
			}
		}
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		String sqlDropTestDb = "DROP DATABASE IF EXISTS testPopEvent;";
		Connection connection = null;
		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(sqlDropTestDb);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Before
	public void setUp() throws Exception {

		String sqlClearTable = "TRUNCATE TABLE agents;";// clear table agents
		Connection connection = null;
		Connection connection2 = null;
		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(sqlClearTable);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM agents");
			resultSet.next();
			assertEquals(0, resultSet.getLong(1));
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	@Test
	public void testFindAll() throws Exception {

		int sizeLine = 10;
		for (int i = 0; i < sizeLine; i++) {
			Connection connection = null;
			try {
				connection = connectionManager.getConnection();
				connection.createStatement().execute(R.sqlInsertAgent);
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}

		AgentDao agentDao = new AgentDaoImpl(connectionManager);
		assertEquals(sizeLine, agentDao.findAll().size());

	}

	@Test
	public void testFindById() throws Exception {
		String sql = "SELECT * FROM agents WHERE agents.agentId = 1;";

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertAgent);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery(sql);
			resultSet.next();

			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			Agent agent = agentDao.findById(1);

			testAgent(agent, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	private void testAgent(Agent agent, ResultSet resultSet) throws Exception {
		assertEquals(agent.getAgentId(), resultSet.getLong("agentId"));
		assertEquals(agent.getAgentFirstName(), resultSet.getString("agentFirstName"));
		assertEquals(agent.getAgentLastName(), resultSet.getString("agentLastName"));
		assertEquals(agent.getAgentStreetAddress(), resultSet.getString("agentStreetAddress"));
		assertEquals(agent.getAgentCity(), resultSet.getString("agentCity"));
		assertEquals(agent.getAgentState(), resultSet.getString("agentState"));
		assertEquals(agent.getAgentPhoneNumber(), resultSet.getString("agentPhoneNumber"));
		assertEquals(agent.getAgentDateHired().getTime(), resultSet.getDate("agentDateHired").getTime(), 1e+12);
		assertNotEquals(String.valueOf(new Date()), resultSet.getDate("agentDateHired").getTime(), 1e+12);
		assertEquals(agent.getAgentSalary(), resultSet.getLong("agentSalary"));
		assertEquals(agent.getAgentCommissionRate(), resultSet.getLong("agentCommissionRate"));
	}

	@Test
	public void testUpdate() throws Exception {
		String sqlFindAgentById = "SELECT * FROM agents WHERE agents.agentId = 1;";

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertAgent); // add agent to db

			connection2 = connectionManager.getConnection();

			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			Agent agent = agentDao.findById(1);
			agent.setAgentFirstName("newAgentFirstName");
			agent.setAgentLastName("newAgentLastName");
			agent.setAgentStreetAddress("newAgentStreetAddress");
			agent.setAgentCity("newAgentCity");
			agent.setAgentState("newAgentState");
			agent.setAgentPhoneNumber("newAgentPhoneNumber");
			agent.setAgentDateHired(new Date());
			agent.setAgentSalary(11);
			agent.setAgentCommissionRate(11);

			agentDao.update(agent); // update agent to db

			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindAgentById); // get agent to db
			resultSet.next();

			testAgent(agent, resultSet); // change this agent and agent to db
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	@Test
	public void testRemove() throws Exception {

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertAgent); // add agent to db


			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			agentDao.remove(1);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM agents");
			resultSet.next();
			assertEquals(0, resultSet.getLong(1));
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	@Test
	public void testAgent() throws Exception {

		Connection connection2 = null;

		try {
			Agent agent = new Agent("FName", "LName", "KM", "DP", "DN", "9379992", new Date(), 1, 1);

			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			agentDao.save(agent);
			agentDao.remove(agent);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM agents");
			resultSet.next();
			assertEquals(0, resultSet.getLong(1));
		} finally {
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	@Test
	public void testSave() throws Exception {

		AgentDao agentDao = new AgentDaoImpl(connectionManager);

		Agent agent = new Agent("FName", "LName", "KM", "DP", "DN", "9379992", new Date(), 1, 1);

		agentDao.save(agent);

		Connection connection = null;
		try {
			connection = connectionManager.getConnection();
			ResultSet resultSet = connection.createStatement()
					.executeQuery("SELECT * FROM agents WHERE agents.agentFirstName='FName';");

			resultSet.next();

			testAgent(agent, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testToString() throws Exception {
		Date date = new Date();
		Agent agent = new Agent("FName", "LName", "KM", "DP", "DN", "9379992", date, 1, 1);

		assertEquals("Agent" +
				"{agentId=0, " +
				"agentFirstName='FName', " +
				"agentLastName='LName', " +
				"agentStreetAddress='KM', " +
				"agentCity='DP', " +
				"agentState='DN', " +
				"agentPhoneNumber='9379992', " +
				"agentDateHired=" +
				date.toString() +
				", " +
				"agentSalary=1, agentCommissionRate=1}", agent.toString());
	}

	@Test
	public void testFindAllByEngagementId() throws Exception {

	}

	@Test
	public void testFindByAgentFirstName() throws Exception {
		String sql = "SELECT * FROM agents WHERE agents.agentFirstName = 'a.Smith';";
		Connection connectionInsertAgent = null;
		Connection connectionFindByFirstName = null;
		try {
			connectionInsertAgent = connectionManager.getConnection();
			connectionInsertAgent.createStatement().execute(R.sqlInsertAgent);

			connectionFindByFirstName = connectionManager.getConnection();
			ResultSet resultSet = connectionFindByFirstName.createStatement().executeQuery(sql);
			resultSet.next();

			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			List<Agent> agent = agentDao.findByAgentFirstName("a.Smith");
			assertEquals(1, agent.size());
			testAgent(agent.get(0), resultSet);
		} finally {
			if (connectionInsertAgent != null) {
				connectionInsertAgent.close();
			}
			if (connectionFindByFirstName != null) {
				connectionFindByFirstName.close();
			}
		}
	}
}