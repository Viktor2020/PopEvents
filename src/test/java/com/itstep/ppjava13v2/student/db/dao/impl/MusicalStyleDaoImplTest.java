package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.MusicalStyleDao;
import com.itstep.ppjava13v2.student.db.domain.MusicalStyle;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MusicalStyleDaoImplTest {


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
				connection.createStatement().execute(R.sqlInsertMusicalStyle);
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		assertEquals(sizeLine, musicalStyleDao.findAll().size());

	}

	@Test
	public void testFindById() throws Exception {
		String sqlFindMusicalStyleById = "SELECT * FROM musicalStyles WHERE musicalStyleId = 1;";

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertMusicalStyle);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindMusicalStyleById);
			resultSet.next();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MusicalStyle musicalStyle = musicalStyleDao.findById(1);

			testMusicalStyle(musicalStyle, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	private void testMusicalStyle(MusicalStyle musicalStyle, ResultSet resultSet) throws Exception {
		assertEquals(musicalStyle.getMusicalStyleId(), resultSet.getLong("musicalStyleId"));
		assertEquals(musicalStyle.getMusicalStyleName(), resultSet.getString("musicalStyleName"));
	}

	@Test
	public void testUpdate() throws Exception {
		String sqlFindMusicalStyleById = "SELECT * FROM musicalStyles WHERE musicalStyleId = 1;";

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertMusicalStyle); // add musicalStyle to db

			connection2 = connectionManager.getConnection();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			MusicalStyle musicalStyle = musicalStyleDao.findById(1);
			musicalStyle.setMusicalStyleName("newMusicalStyleName");

			musicalStyleDao.update(musicalStyle); // update musicalStyle to db

			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindMusicalStyleById); // get musicalStyle to db
			resultSet.next();

			testMusicalStyle(musicalStyle, resultSet); // change this musicalStyle and musicalStyle to db
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
			connection.createStatement().execute(R.sqlInsertMusicalStyle); // add musicalStyle to db


			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			musicalStyleDao.remove(1);

			assertEquals(0, R.countRecordsInTable("musicalStyles"));
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testMusicalStyle() throws Exception {

		MusicalStyle musicalStyle = new MusicalStyle("Name");

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
		musicalStyleDao.save(musicalStyle);
		musicalStyleDao.remove(musicalStyle);

		assertEquals(0, R.countRecordsInTable("musicalStyles"));
	}

	@Test
	public void testSave() throws Exception {

		MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);

		MusicalStyle musicalStyle = new MusicalStyle("Name");

		musicalStyleDao.save(musicalStyle);

		Connection connection = null;
		try {
			connection = connectionManager.getConnection();
			ResultSet resultSet = connection.createStatement()
					.executeQuery("SELECT * FROM musicalStyles WHERE musicalStyleName='Name';");

			resultSet.next();

			testMusicalStyle(musicalStyle, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testFindByMusicalStyleName() throws Exception {
		String sqlFindMusicalStyleById = "SELECT * FROM musicalStyles WHERE musicalStyleName = 'musicalStyleName';";

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertMusicalStyle);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindMusicalStyleById);
			resultSet.next();

			MusicalStyleDao musicalStyleDao = new MusicalStyleDaoImpl(connectionManager);
			List<MusicalStyle> musicalStyle = musicalStyleDao.findByMusicalStyleName("musicalStyleName");

			testMusicalStyle(musicalStyle.get(0), resultSet);
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