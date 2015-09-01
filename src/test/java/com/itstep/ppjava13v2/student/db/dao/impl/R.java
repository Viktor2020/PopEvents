package com.itstep.ppjava13v2.student.db.dao.impl;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;

import static org.junit.Assert.assertEquals;

/**

 */
public class R {
	public static final String sqlCreateTableAgents = "CREATE TABLE if not exists Agents" +
			"(" +
			"  agentId             INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
			"  agentFirstName      VARCHAR(255)    NOT NULL," +
			"  agentLastName       VARCHAR(255)    NOT NULL," +
			"  agentStreetAddress  VARCHAR(255)    NOT NULL," +
			"  agentCity           VARCHAR(255)    NOT NULL," +
			"  agentState          VARCHAR(255)    NOT NULL," +
			"  agentPhoneNumber    VARCHAR(255)    NOT NULL," +
			"  agentDateHired      DATE            NOT NULL," +
			"  agentSalary         INT             NOT NULL," +
			"  agentCommissionRate INT             NOT NULL," +
			"  agentEngagementId        INT" +
			");";
	public static final String sqlCreateTableCustomers = "CREATE TABLE if not exists  Customers" +
			"(" +
			"  customerId            INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
			"  customerFirstName     VARCHAR(255)    NOT NULL," +
			"  customerLastName      VARCHAR(255)    NOT NULL," +
			"  customerStreetAddress VARCHAR(255)    NOT NULL," +
			"  customerCity          VARCHAR(255)    NOT NULL," +
			"  customerState         VARCHAR(255)    NOT NULL," +
			"  customerPhoneNumber   VARCHAR(255)    NOT NULL," +
			"  customerEngagementId          INT" +
			");";
	public static final String sqlCreateTableEntertainers = "CREATE TABLE if not exists Entertainers" +
			"(" +
			"  entertainerId            INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
			"  entertainerStageName     VARCHAR(255)    NOT NULL," +
			"  entertainerStreetAddress VARCHAR(255)    NOT NULL," +
			"  entertainerCity          VARCHAR(255)    NOT NULL," +
			"  entertainerState         VARCHAR(255)    NOT NULL," +
			"  entertainerPhoneNumber   VARCHAR(255)    NOT NULL," +
			"  entertainerWebPage       VARCHAR(255)    NOT NULL," +
			"  entertainerEmailAddress  VARCHAR(255)    NOT NULL," +
			"  entertainerDateEntered   DATE            NOT NULL," +
			"  entertainerEngagementId             INT" +
			");";
	public static final String sqlCreateTableEngagements = "CREATE TABLE if not exists Engagements" +
			"(" +
			"  engagementId        INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
			"  engagementStartDate DATE            NOT NULL," +
			"  engagementEndDate   DATE            NOT NULL," +
			"  engagementPrice     INT             NOT NULL" +
			");";

	public static final String sqlCreateTableMember = "CREATE TABLE if not exists Members" +
			"(" +
			"   memberId          INT PRIMARY KEY NOT NULL AUTO_INCREMENT" +
			",  memberFirstName   VARCHAR(255)    NOT NULL" +
			",  memberLastName    VARCHAR(255)    NOT NULL" +
			",  memberPhoneNumber VARCHAR(255)    NOT NULL" +
			",  memberGender      VARCHAR(255)    NOT NULL " +
			")";

	public static final String sqlCreateTableMusicalStyles = "CREATE TABLE if not exists MusicalStyles" +
			"(" +
			"  musicalStyleId   INT PRIMARY KEY NOT NULL AUTO_INCREMENT," +
			"  musicalStyleName VARCHAR(255)    NOT NULL" +
			");";

	public static final String sqlCreateTableEntertainer_Members = "CREATE TABLE if not exists Entertainer_Members (" +
			"  entertainerId INT REFERENCES Entertainers (entertainerId) ON UPDATE CASCADE    ON DELETE CASCADE" +
			"  ,  memberId      INT REFERENCES Members (memberId)    ON UPDATE CASCADE " +
			"  ,  CONSTRAINT entertainer_member_pkey PRIMARY KEY (entertainerId, memberId)" +
			");";

	public static final String sqlCreateTableEntertainer_Styles = "CREATE TABLE if not exists Entertainer_Styles (" +
			"  entertainerId  INT REFERENCES Entertainers (entertainerId)    ON UPDATE CASCADE    ON DELETE CASCADE" +
			"  ,  musicalStyleId INT REFERENCES MusicalStyles (musicalStyleId)  ON UPDATE CASCADE " +
			"  ,  CONSTRAINT entertainer_style_pkey PRIMARY KEY (entertainerId, musicalStyleId)" +
			");";

	public static final String sqlCreateTableCustomer_Styles = "CREATE TABLE if not exists Customer_Styles (" +
			"  customerId     INT REFERENCES Customers (customerId)    ON UPDATE CASCADE    ON DELETE CASCADE" +
			"  ,  musicalStyleId INT REFERENCES MusicalStyles (musicalStyleId)  ON UPDATE CASCADE " +
			"  ,  CONSTRAINT customer_style_pkey PRIMARY KEY (customerId, musicalStyleId)" +
			");";

	public static final String sqlInsertCustomer = "INSERT INTO Customers (customerFirstName, customerLastName, customerStreetAddress, customerCity, customerState, customerPhoneNumber,customerEngagementId) VALUES ('k.Pupok1', 'k.Ivan1', 'karlaMarlsa1', 'dnepr', 'DP', '8458494',1);";
	public static final String sqlInsertAgent = "INSERT INTO Agents (agentFirstName, agentLastName, agentStreetAddress, agentCity, agentState, agentPhoneNumber, agentDateHired, agentSalary, agentCommissionRate,agentEngagementId)VALUES ('a.Smith', 'a.John', 'agentStreetAddress', 'agentCity', 'agentState', '8458494', now(), 200, 15,1);";
	public static final String sqlInsertEntertainer = "INSERT INTO Entertainers (entertainerStageName, entertainerStreetAddress, entertainerCity, entertainerState, entertainerPhoneNumber, entertainerWebPage, entertainerEmailAddress, entertainerDateEntered,entertainerEngagementId)" +
			"VALUES ('Chaplin', 'entertainerStreetAddress', 'entertainerCity', 'entertainerState', 'entertainerPhoneNumber1', 'entertainerWebPage', 'entertainerEmailAddress', now(),1);";

	public static final String sqlInsertEngagement = "INSERT INTO engagements (engagementStartDate, engagementEndDate, engagementPrice) VALUES (now(), now(), 1000);";

	public static final String sqlInsertMember = "INSERT INTO members( memberFirstName, memberLastName, memberPhoneNumber, memberGender ) " +
			"VALUES ('memberFirstName', " +
			"'memberLastName', " +
			"'memberPhoneNumber'," +
			"'memberGender'" +
			");";

	public static final String sqlInsertMusicalStyle = "INSERT INTO musicalStyles( musicalStyleName) " +
			"VALUES ('musicalStyleName');";

	public static DataSource connectionManager =  new TestDataSource();

	public static void createDB() throws Exception {
		String sqlCreateTestDb = "CREATE DATABASE IF NOT EXISTS testPopEvent;";
		Connection connection = null;
		try {
			connection = connectionManager.getConnection("root", "qqqq");
			connection.createStatement().execute(sqlCreateTestDb);
			connection.close();

			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlCreateTableAgents);
			connection.close();

			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlCreateTableCustomers);
			connection.close();

			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlCreateTableEngagements);
			connection.close();

			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlCreateTableEntertainers);
			connection.close();

			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlCreateTableMember);
			connection.close();

			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlCreateTableMusicalStyles);
			connection.close();

			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlCreateTableEntertainer_Members);
			connection.close();

			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlCreateTableEntertainer_Styles);
			connection.close();

			connection = connectionManager.getConnection();
			connection.createStatement().execute(R.sqlCreateTableCustomer_Styles);

		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}

	public static void deleteDB() throws Exception {
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

	public static void clearTables() throws Exception {
		String sqlClearTableEngagements = "TRUNCATE TABLE engagements;";// clear table
		Connection connectionClearEngagements = null;
		String sqlClearTableAgents = "TRUNCATE TABLE agents;";// clear table
		Connection connectionClearAgents = null;
		String sqlClearTableCustomers = "TRUNCATE TABLE Customers;";// clear table
		Connection connectionClearCustomers = null;
		String sqlClearTableMusicalStyles = "TRUNCATE TABLE MusicalStyles;";// clear table
		Connection connectionClearMusicalStyles = null;
		String sqlClearTableMembers = "TRUNCATE TABLE Members;";// clear table
		Connection connectionClearMembers = null;
		String sqlClearTableEntertainers = "TRUNCATE TABLE Entertainers;";// clear table
		Connection connectionClearEntertainers = null;
		try {
			connectionClearEngagements = connectionManager.getConnection();
			connectionClearAgents = connectionManager.getConnection();
			connectionClearCustomers = connectionManager.getConnection();
			connectionClearMusicalStyles = connectionManager.getConnection();
			connectionClearMembers = connectionManager.getConnection();
			connectionClearEntertainers = connectionManager.getConnection();

			connectionClearEngagements.createStatement().execute(sqlClearTableEngagements);
			connectionClearAgents.createStatement().execute(sqlClearTableAgents);
			connectionClearCustomers.createStatement().execute(sqlClearTableCustomers);
			connectionClearMusicalStyles.createStatement().execute(sqlClearTableMusicalStyles);
			connectionClearMembers.createStatement().execute(sqlClearTableMembers);
			connectionClearEntertainers.createStatement().execute(sqlClearTableEntertainers);

			assertEquals(0, countRecordsInTable("engagements"));
			assertEquals(0, countRecordsInTable("Agents"));
			assertEquals(0, countRecordsInTable("Customers"));
			assertEquals(0, countRecordsInTable("MusicalStyles"));
			assertEquals(0, countRecordsInTable("Members"));
			assertEquals(0, countRecordsInTable("Entertainers"));
		} finally {
			if (connectionClearEngagements != null) {
				connectionClearEngagements.close();
			}
			if (connectionClearAgents != null) {
				connectionClearAgents.close();
			}
			if (connectionClearCustomers != null) {
				connectionClearCustomers.close();
			}
			if (connectionClearMusicalStyles != null) {
				connectionClearMusicalStyles.close();
			}
			if (connectionClearMembers != null) {
				connectionClearMembers.close();
			}
			if (connectionClearEntertainers != null) {
				connectionClearEntertainers.close();
			}
		}
	}

	public static long countRecordsInTable(String tableName) throws Exception {
		Connection connection = connectionManager.getConnection();
		try {
			ResultSet resultSet = connection.createStatement().executeQuery("SELECT COUNT(*) FROM " + tableName);
			resultSet.next();
			return resultSet.getLong(1);
		} finally {
			if (connection != null) {
				connection.close();
			}
		}
	}
}
