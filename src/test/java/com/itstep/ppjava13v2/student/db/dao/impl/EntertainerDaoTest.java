package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.EntertainerDao;
import com.itstep.ppjava13v2.student.db.dao.MemberDao;
import com.itstep.ppjava13v2.student.db.dao.MusicalStyleDao;
import com.itstep.ppjava13v2.student.db.domain.Entertainer;
import com.itstep.ppjava13v2.student.db.domain.Member;
import com.itstep.ppjava13v2.student.db.domain.MusicalStyle;
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

public class EntertainerDaoTest {

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
				connection.createStatement().execute(R.sqlInsertEntertainer);
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		MemberDao memberDao = new MemberDaoImpl(connectionManager);
		EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
		assertEquals(sizeLine, entertainerDao.findAll().size());

	}

	@Test
	public void testFindById() throws Exception {
		String sqlFindById = "SELECT * FROM entertainers WHERE entertainers.entertainerId = 1;";


		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertEntertainer);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindById);
			resultSet.next();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			Entertainer entertainer = entertainerDao.findById(1);

			testEntertainer(entertainer, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	private void testEntertainer(Entertainer entertainer, ResultSet resultSet) throws Exception {
		assertEquals(entertainer.getEntertainerId(), resultSet.getLong("entertainerId"));
		assertEquals(entertainer.getEntertainerStageName(), resultSet.getString("entertainerStageName"));
		assertEquals(entertainer.getEntertainerStreetAddress(), resultSet.getString("entertainerStreetAddress"));
		assertEquals(entertainer.getEntertainerCity(), resultSet.getString("entertainerCity"));
		assertEquals(entertainer.getEntertainerState(), resultSet.getString("entertainerState"));
		assertEquals(entertainer.getEntertainerPhoneNumber(), resultSet.getString("entertainerPhoneNumber"));
		assertEquals(entertainer.getEntertainerWebPage(), resultSet.getString("entertainerWebPage"));
		assertEquals(entertainer.getEntertainerEmailAddress(), resultSet.getString("entertainerEmailAddress"));
		assertEquals(entertainer.getEntertainerDateEntered().getTime(), resultSet.getDate("entertainerDateEntered").getTime(), 1e+12);
		assertNotEquals(String.valueOf(new Date()), resultSet.getDate("entertainerDateEntered").getTime(), 1e+12);
	}

	@Test
	public void testUpdate() throws Exception {
		String sqlFindById = "SELECT * FROM entertainers WHERE entertainers.entertainerId = 1;";


		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertEntertainer); // add entertainer to db

			connection2 = connectionManager.getConnection();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			Entertainer entertainer = entertainerDao.findById(1);
			entertainer.setEntertainerStageName("newEntertainerStageName");
			entertainer.setEntertainerStreetAddress("newEntertainerStreetAddress");
			entertainer.setEntertainerCity("newEntertainerCity");
			entertainer.setEntertainerState("newEntertainerState");
			entertainer.setEntertainerPhoneNumber("newEntertainerPhoneNumber");
			entertainer.setEntertainerWebPage("newEntertainerWebPage");
			entertainer.setEntertainerEmailAddress("newEntertainerEmailAddress");
			entertainer.setEntertainerDateEntered(new Date());

			entertainerDao.update(entertainer); // update entertainer to db

			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindById); // get entertainer to db
			resultSet.next();

			testEntertainer(entertainer, resultSet); // change this entertainer and entertainer to db
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
			connection.createStatement().execute(R.sqlInsertEntertainer); // add entertainer to db


			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			entertainerDao.remove(1);

			assertEquals(0, R.countRecordsInTable("entertainers"));
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testEntertainer() throws Exception {

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		MemberDao memberDao = new MemberDaoImpl(connectionManager);
		EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);

		Entertainer entertainer = new Entertainer("SName", "KM", "DP", "DN", "9379992", "http://google.com", "ent@gmail.com", new Date());
		entertainer.addMusicalStyle(new MusicalStyle("rock"));
		entertainer.addMember(new Member("FName", "LName", "KM", "DP"));
		entertainerDao.save(entertainer);

		assertEquals(1, R.countRecordsInTable("MusicalStyles"));
		assertEquals(1, R.countRecordsInTable("Members"));
		entertainerDao.remove(entertainer);

		entertainer.addMusicalStyle(new MusicalStyle("pop"));
		entertainer.addMember(new Member("Name", " me", "KM", "DP"));
		entertainerDao.update(entertainer);

		assertEquals(2, R.countRecordsInTable("MusicalStyles"));
		assertEquals(2, R.countRecordsInTable("Members"));

		assertEquals(1, R.countRecordsInTable("entertainers"));

	}

	@Test
	public void testSave() throws Exception {

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		MemberDao memberDao = new MemberDaoImpl(connectionManager);
		EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);

		Entertainer entertainer = new Entertainer("SName", "KM", "DP", "DN", "9379992", "http://google.com", "ent@gmail.com", new Date());

		entertainerDao.save(entertainer);

		Connection connection = null;
		try {
			connection = connectionManager.getConnection();
			ResultSet resultSet = connection.createStatement()
					.executeQuery("SELECT * FROM entertainers WHERE entertainerStageName='SName';");

			resultSet.next();

			testEntertainer(entertainer, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testFindByEntertainerFirstName() throws Exception {
		String sqlFindById = "SELECT * FROM entertainers WHERE entertainerStageName = 'Chaplin';";

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertEntertainer);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindById);
			resultSet.next();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			EntertainerDao entertainerDao = new EntertainerDaoImpl(connectionManager, musicalStyleDao, memberDao);
			List<Entertainer> entertainer = entertainerDao.findByEntertainerFirstName("Chaplin");

			testEntertainer(entertainer.get(0), resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

}