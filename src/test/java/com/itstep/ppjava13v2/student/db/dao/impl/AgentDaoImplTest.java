package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.AgentDao;
import com.itstep.ppjava13v2.student.db.domain.Agent;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AgentDaoImplTest {

	static DataSource connectionManager = R.connectionManager;

	@BeforeClass
	public static void setUpClass() throws Exception {
		R.createDB();
	}

	@AfterClass
	public static void tearDownClass() throws Exception {
		R.deleteDB();
	}

	@Before
	public void setUp() throws Exception {
		R.clearTables();
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

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertAgent); // add agent to db

			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			agentDao.remove(1);

			assertEquals(0, R.countRecordsInTable("Agents"));
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testAgent() throws Exception {

		Agent agent = new Agent("FName", "LName", "KM", "DP", "DN", "9379992", new Date(), 1, 1);

		AgentDao agentDao = new AgentDaoImpl(connectionManager);
		agentDao.save(agent);
		agentDao.remove(agent);

		assertEquals(0, R.countRecordsInTable("Agents"));

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