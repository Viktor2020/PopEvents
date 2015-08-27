package com.itstep.ppjava13v2.student.db.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**

 */
public interface ConnectionManager {
	Connection getConnection() throws SQLException;

	Connection getConnection(String url, String user, String password) throws SQLException;
}
