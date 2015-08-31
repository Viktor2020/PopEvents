package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.*;
import com.itstep.ppjava13v2.student.db.domain.Agent;
import com.itstep.ppjava13v2.student.db.domain.Customer;
import com.itstep.ppjava13v2.student.db.domain.Engagement;
import com.itstep.ppjava13v2.student.db.domain.Entertainer;
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

public class EngagementDaoImplTest {

	static DataSource connectionManager = R.connectionManager;
	private String sqlSelectById = "SELECT *" +
			"      FROM  Engagements " +
			"      WHERE   Engagements.engagementId = 1;";

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

		int sizeLine = 3;
		for (int i = 0; i < sizeLine; i++) {
			Connection connection = null;
			Connection connection2 = null;
			Connection connection3 = null;
			Connection connection4 = null;
			try {
				connection = connectionManager.getConnection();
				connection.createStatement().execute(R.sqlInsertEngagement);
				connection2 = connectionManager.getConnection();
				connection2.createStatement().execute(R.sqlInsertAgent);
				connection3 = connectionManager.getConnection();
				connection3.createStatement().execute(R.sqlInsertCustomer);
				connection4 = connectionManager.getConnection();
				connection4.createStatement().execute(R.sqlInsertEntertainer);
			} finally {
				if (connection != null) {
					connection.close();
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
			}
		}

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		MemberDao memberDao = new MemberDaoImpl(connectionManager);
		CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
		AgentDao agentDao = new AgentDaoImpl(connectionManager);
		EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
		EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);
		assertEquals(sizeLine, engagementDao.findAll().size());
		assertEquals(sizeLine, engagementDao.findAll().get(0).getEngagementAgentList().size());
		assertEquals(sizeLine, engagementDao.findAll().get(0).getEngagementCustomerList().size());
		assertEquals(sizeLine, engagementDao.findAll().get(0).getEngagementEntertainerList().size());
	}

	@Test
	public void testFindById() throws Exception {
		Connection connection = null;
		Connection connection2 = null;
		Connection connection3 = null;
		Connection connection4 = null;
		Connection connection5 = null;
		try {
			connection = connectionManager.getConnection();

			connection2 = connectionManager.getConnection();
			connection3 = connectionManager.getConnection();
			connection4 = connectionManager.getConnection();
			connection5 = connectionManager.getConnection();

			connection.createStatement().execute(R.sqlInsertEngagement);
			connection3.createStatement().execute(R.sqlInsertAgent);
			connection4.createStatement().execute(R.sqlInsertCustomer);
			connection5.createStatement().execute(R.sqlInsertEntertainer);

			ResultSet resultSet = connection2.createStatement().executeQuery(sqlSelectById);
			resultSet.next();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);
			Engagement engagement = engagementDao.findById(1);

			testEngagement(engagement, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
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
		}
	}

	private void testEngagement(Engagement engagement, ResultSet resultSet) throws Exception {
		assertEquals(engagement.getEngagementId(), resultSet.getLong("engagementId"));
		assertEquals(engagement.getEngagementStartDate().getTime(), resultSet.getDate("engagementStartDate").getTime(), 1e+12);
		assertEquals(engagement.getEngagementEndDate().getTime(), resultSet.getDate("engagementEndDate").getTime(), 1e+12);
		assertEquals(engagement.getEngagementPrice(), resultSet.getLong("engagementPrice"));

	}

	@Test
	public void testUpdateCustomerInEngagement() throws Exception {
		Connection connection2 = null;
		Connection connection3 = null;
		Connection connection4 = null;
		Connection connection5 = null;
		try {
			connection2 = connectionManager.getConnection();
			connection3 = connectionManager.getConnection();
			connection4 = connectionManager.getConnection();
			connection5 = connectionManager.getConnection();

			connection2.createStatement().execute(R.sqlInsertEngagement);
			connection3.createStatement().execute(R.sqlInsertAgent);
			connection4.createStatement().execute(R.sqlInsertCustomer);
			connection5.createStatement().execute(R.sqlInsertEntertainer);

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);
			Engagement engagement = engagementDao.findById(1);

			engagement.getEngagementCustomerList().add(new Customer("FName", "LName", "KM", "DP", "DN", "9379992"));
			engagementDao.save(engagement);

			engagement = engagementDao.findById(1);
			boolean flagUpdateCustomerInEngagement = false;
			for (Customer customer : engagement.getEngagementCustomerList()) {
				System.out.println(customer.getCustomerFirstName());
				if (customer.getCustomerFirstName().equals("FName")) {
					flagUpdateCustomerInEngagement = true;
				}
			}
			assertEquals(true, flagUpdateCustomerInEngagement);

		} finally {
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
		}
	}

	@Test
	public void testUpdate() throws Exception {

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertEngagement); // add engagement to db
			assertEquals(1, R.countRecordsInTable("engagements"));
			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);
			Engagement engagement = engagementDao.findById(1);
			engagement.setEngagementPrice(5000);

			engagementDao.update(engagement); // update engagement to db

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery(sqlSelectById); // get engagement to db
			resultSet.next();

			testEngagement(engagement, resultSet); // change this engagement and engagement to db
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
			connection.createStatement().execute(R.sqlInsertEngagement); // add engagement to db


			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);
			engagementDao.remove(1);

			assertEquals(0, R.countRecordsInTable("engagements"));
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testEngagement() throws Exception {
		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		MemberDao memberDao = new MemberDaoImpl(connectionManager);
		CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
		AgentDao agentDao = new AgentDaoImpl(connectionManager);
		EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
		EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);

		Engagement engagement = new Engagement(new Date(), new Date(), 1000);
		Agent agent = new Agent("FName", "LName", "KM", "DP", "DN", "9379992", new Date(), 1, 1);
		Customer customer = new Customer("FName", "LName", "KM", "DP", "DN", "9379992");
		Entertainer entertainer = new Entertainer("SName", "KM", "DP", "DN", "9379992", "http://google.com", "ent@gmail.com", new Date());

		engagement.addEntertainer(entertainer);
		engagement.addCustomer(customer);
		engagement.addAgent(agent);
		engagementDao.save(engagement);

		assertEquals(1, R.countRecordsInTable("entertainers"));
		assertEquals(1, R.countRecordsInTable("customers"));
		assertEquals(1, R.countRecordsInTable("agents"));

		engagement.addEntertainer(new Entertainer("SName", "KM", "DP", "DN", "9379992", "http://google.com", "ent@gmail.com", new Date()));
		engagement.addCustomer(new Customer("FName", "LName", "KM", "DP", "DN", "9379992"));
		engagement.addAgent(new Agent("FName", "LName", "KM", "DP", "DN", "9379992", new Date(), 1, 1));
		engagementDao.update(engagement);

		assertEquals(2, R.countRecordsInTable("entertainers"));
		assertEquals(2, R.countRecordsInTable("customers"));
		assertEquals(2, R.countRecordsInTable("agents"));

		engagementDao.remove(engagement);

		assertEquals(0, R.countRecordsInTable("engagements"));
	}

	@Test
	public void testSave() throws Exception {

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		MemberDao memberDao = new MemberDaoImpl(connectionManager);
		CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
		AgentDao agentDao = new AgentDaoImpl(connectionManager);
		EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
		EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);

		Engagement engagement = new Engagement(new Date(), new Date(), 1000);

		engagementDao.save(engagement);

		Connection connection = null;
		try {
			connection = connectionManager.getConnection();
			ResultSet resultSet = connection.createStatement()
					.executeQuery("SELECT * FROM engagements WHERE engagementId = 1;");

			resultSet.next();

			testEngagement(engagement, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testFindByEngagementPrice() throws Exception {
		String sql = "SELECT * FROM Engagements WHERE engagementPrice = 1000;";
		Connection connectionInsertAgent = null;
		Connection connectionFindByFirstName = null;
		try {
			connectionInsertAgent = connectionManager.getConnection();
			connectionInsertAgent.createStatement().execute(R.sqlInsertEngagement);

			connectionFindByFirstName = connectionManager.getConnection();
			ResultSet resultSet = connectionFindByFirstName.createStatement().executeQuery(sql);
			resultSet.next();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			CustomerDao customerDao = new CustomerDaoImpl(connectionManager, musicalStyleDao);
			AgentDao agentDao = new AgentDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			EngagementDao engagementDao = new EngagementDaoImpl(connectionManager, agentDao, customerDao, entertainerDao);

			List<Engagement> engagement = engagementDao.findByEngagementPrice(1000);
			assertEquals(1, engagement.size());
			testEngagement(engagement.get(0), resultSet);
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