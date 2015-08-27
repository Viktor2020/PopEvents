package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.MemberDao;
import com.itstep.ppjava13v2.student.db.domain.Member;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.*;
import java.util.List;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class MemberDaoImplTest {

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

		String sqlClearTable = "TRUNCATE TABLE members;";// clear table members
		Connection connection = null;
		Connection connection2 = null;
		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(sqlClearTable);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM members");
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
				connection.createStatement().execute(R.sqlInsertMember);
			} finally {
				if (connection != null) {
					connection.close();
				}
			}
		}

		MemberDao memberDao = new MemberDaoImpl(connectionManager);
		assertEquals(sizeLine, memberDao.findAll().size());

	}

	@Test
	public void testFindById() throws Exception {
		String sqlFindMemberById = "SELECT * FROM members WHERE memberId = 1;";

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertMember);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindMemberById);
			resultSet.next();

			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			Member member = memberDao.findById(1);

			testMember(member, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (connection2 != null) {
				connection2.close();
			}
		}
	}

	private void testMember(Member member, ResultSet resultSet) throws Exception {
		assertEquals(member.getMemberId(), resultSet.getLong("memberId"));
		assertEquals(member.getMemberFirstName(), resultSet.getString("memberFirstName"));
		assertEquals(member.getMemberLastName(), resultSet.getString("memberLastName"));
		assertEquals(member.getMemberPhoneNumber(), resultSet.getString("memberPhoneNumber"));
		assertEquals(member.getMemberGender(), resultSet.getString("memberGender"));
	}

	@Test
	public void testUpdate() throws Exception {
		String sqlFindMemberById = "SELECT * FROM members WHERE  memberId = 1;";

		Connection connection = null;
		Connection connection2 = null;

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertMember); // add member to db

			connection2 = connectionManager.getConnection();

			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			Member member = memberDao.findById(1);
			member.setMemberFirstName("newMemberFirstName");
			member.setMemberLastName("newMemberLastName");
			member.setMemberPhoneNumber("newMemberPhoneNumber");
			member.setMemberGender("newMemberGender");

			memberDao.update(member); // update member to db

			ResultSet resultSet = connection2.createStatement().executeQuery(sqlFindMemberById); // get member to db
			resultSet.next();

			testMember(member, resultSet); // change this member and member to db
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
			connection.createStatement().execute(R.sqlInsertMember); // add member to db


			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			memberDao.remove(1);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM members");
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
	public void testMember() throws Exception {

		Connection connection2 = null;

		try {
			Member member = new Member("FName", "LName", "KM", "DP");

			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			memberDao.save(member);
			memberDao.remove(member);

			connection2 = connectionManager.getConnection();
			ResultSet resultSet = connection2.createStatement().executeQuery("SELECT COUNT(*) FROM members");
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

		MemberDao memberDao = new MemberDaoImpl(connectionManager);

		Member member = new Member("FName", "LName", "KM", "DP");

		memberDao.save(member);

		Connection connection = null;
		try {
			connection = connectionManager.getConnection();
			ResultSet resultSet = connection.createStatement()
					.executeQuery("SELECT * FROM members WHERE members.memberFirstName='FName';");

			resultSet.next();

			testMember(member, resultSet);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testFindByMemberFirstName() throws Exception {
		String sql = "SELECT * FROM Members WHERE memberFirstName = 'memberFirstName';";
		Connection connectionInsertMember = null;
		Connection connectionFindByFirstName = null;
		try {
			connectionInsertMember = connectionManager.getConnection();
			connectionInsertMember.createStatement().execute(R.sqlInsertMember);

			connectionFindByFirstName = connectionManager.getConnection();
			ResultSet resultSet = connectionFindByFirstName.createStatement().executeQuery(sql);
			resultSet.next();

			MemberDao agentDao = new MemberDaoImpl(connectionManager);
			List<Member> agent = agentDao.findByMemberFirstName("memberFirstName");
			assertEquals(1, agent.size());
			testMember(agent.get(0), resultSet);
		} finally {
			if (connectionInsertMember != null) {
				connectionInsertMember.close();
			}
			if (connectionFindByFirstName != null) {
				connectionFindByFirstName.close();
			}
		}
	}
}