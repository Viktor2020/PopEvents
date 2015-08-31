package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.MemberDao;
import com.itstep.ppjava13v2.student.db.domain.Member;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class MemberDaoImplTest {

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

		try {
			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlInsertMember); // add member to db


			MemberDao memberDao = new MemberDaoImpl(connectionManager);
			memberDao.remove(1);
			assertEquals(0, R.countRecordsInTable("members"));
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	@Test
	public void testMember() throws Exception {

		Member member = new Member("FName", "LName", "KM", "DP");

		MemberDao memberDao = new MemberDaoImpl(connectionManager);
		memberDao.save(member);
		memberDao.remove(member);

		assertEquals(0, R.countRecordsInTable("members"));

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