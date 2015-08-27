package com.itstep.ppjava13v2.student.db.dao.impl;

import com.itstep.ppjava13v2.student.db.dao.MusicalStyleDao;
import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.MusicalStyle;
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
public class MusicalStyleDaoImpl implements MusicalStyleDao {

	private static final Logger log = LoggerFactory.getLogger(new Throwable().getStackTrace()[0].getClassName());

	@Autowired
	private DataSource connectionManager;

	public MusicalStyleDaoImpl(DataSource connectionManager) {
		this.connectionManager = connectionManager;
	}

	public MusicalStyleDaoImpl() {
	}


	@Override
	public List<MusicalStyle> findAll() throws DaoException {
		List<MusicalStyle> musicalStyleList = new ArrayList<>();
		String sql = "SELECT musicalStyleId, musicalStyleName FROM musicalStyles;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			log.trace("Get statement {}", statement);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				musicalStyleList.add(getMusicalStyle(resultSet));
			}
		} catch (SQLException e) {
			log.error("Error musicalStyle ", e);
			throw new DaoException("Error musicalStyle", e);
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
		return musicalStyleList;
	}

	@Override
	public List<MusicalStyle> findAllByEntertainerId(long entertainerId) throws DaoException {
		List<MusicalStyle> musicalStyleList = new ArrayList<>();
		String sql = "SELECT musicalstyles.musicalStyleId, musicalStyleName FROM musicalStyles, entertainer_styles WHERE entertainer_styles.musicalStyleId = musicalstyles.musicalStyleId AND entertainer_styles.entertainerId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setLong(1, entertainerId);
			log.trace("Get statement {}", statement);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				musicalStyleList.add(getMusicalStyle(resultSet));
			}
		} catch (SQLException e) {
			log.error("Error musicalStyle ", e);
			throw new DaoException("Error musicalStyle", e);
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
		return musicalStyleList;
	}

	@Override
	public List<MusicalStyle> findAllByCustomerId(long customerId) throws DaoException {
		List<MusicalStyle> musicalStyleList = new ArrayList<>();
		String sql = "SELECT musicalstyles.musicalStyleId, musicalStyleName FROM musicalStyles, customer_styles WHERE customer_styles.musicalStyleId = musicalstyles.musicalStyleId AND customer_styles.customerId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setLong(1, customerId);
			log.trace("Get statement {}", statement);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				musicalStyleList.add(getMusicalStyle(resultSet));
			}
		} catch (SQLException e) {
			log.error("Error musicalStyle ", e);
			throw new DaoException("Error musicalStyle", e);
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
		return musicalStyleList;
	}

	private MusicalStyle getMusicalStyle(ResultSet resultSet) throws SQLException {
		MusicalStyle musicalStyle;
		if (resultSet != null) {
			musicalStyle = new MusicalStyle(
					resultSet.getLong("musicalStyleId")
					, resultSet.getString("musicalStyleName")
			);
		} else {
			throw new SQLException("MusicalStyle resultSet is null");
		}
		return musicalStyle;
	}

	@Override
	public MusicalStyle findById(long id) throws DaoException {
		MusicalStyle musicalStyle = null;
		String sql = "SELECT musicalStyleId, musicalStyleName FROM MusicalStyles WHERE musicalStyleId = ?;";
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
				musicalStyle = getMusicalStyle(resultSet);
			} else {
				log.trace("not find musicalStyle by id = {}", id);
			}
		} catch (SQLException e) {
			log.error("Error get all musicalStyle ", e);
			throw new DaoException("Error musicalStyle", e);
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
		return musicalStyle;
	}

	@Override
	public void update(MusicalStyle musicalStyle) throws DaoException {
		if (musicalStyle.getMusicalStyleId() <= 0) {
			save(musicalStyle);
			return;
		}
		String sql = "UPDATE musicalStyles SET  musicalStyleName = ? WHERE musicalStyleId = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);

			statement.setString(1, musicalStyle.getMusicalStyleName());
			statement.setLong(2, musicalStyle.getMusicalStyleId());

			log.trace("get statement {}", statement);
			statement.executeUpdate();

		} catch (SQLException e) {
			log.error("Error musicalStyle ", e);
			throw new DaoException("Error musicalStyle", e);
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
	public void remove(MusicalStyle musicalStyle) throws DaoException {
		remove(musicalStyle.getMusicalStyleId());
	}

	@Override
	public void remove(long id) throws DaoException {
		String sql = "DELETE FROM MusicalStyles WHERE musicalStyleId = ?";
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
			log.error("Error musicalStyle ", e);
			throw new DaoException("Error musicalStyle", e);
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
	public MusicalStyle save(MusicalStyle musicalStyle) throws DaoException {
		if (musicalStyle.getMusicalStyleId() > 0) {
			update(musicalStyle);
			return musicalStyle;
		}
		String sql = "INSERT INTO MusicalStyles (musicalStyleName) VALUES (?);";
		Connection connection = null;
		PreparedStatement statement = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, musicalStyle.getMusicalStyleName());
			log.trace("get statement {}", statement);
			statement.executeUpdate();

			// get id musicalStyle
			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				log.trace("try get id last added musicalStyle");
				if (generatedKeys.next()) {
					musicalStyle.setMusicalStyleId(generatedKeys.getLong(1));
				} else {
					log.warn("Creating musicalStyle {} failed, no ID obtained", musicalStyle);
					throw new SQLException("Creating musicalStyle failed, no ID obtained.");
				}
			}
		} catch (SQLException e) {
			log.error("Error musicalStyle ", e);
			throw new DaoException("Error musicalStyle", e);
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

		return musicalStyle;
	}

	@Override
	public List<MusicalStyle> findByMusicalStyleName(String musicalStyleName) throws DaoException {
		List<MusicalStyle> musicalStyleList = new ArrayList<>();
		String sql = "SELECT musicalstyles.musicalStyleId, musicalStyleName FROM musicalStyles WHERE musicalStyleName = ?;";
		Connection connection = null;
		PreparedStatement statement = null;
		ResultSet resultSet = null;
		try {
			log.trace("Try get connection");
			connection = connectionManager.getConnection();
			log.trace("Got connection {}", connection);
			statement = connection.prepareStatement(sql);
			statement.setString(1, musicalStyleName);
			log.trace("Get statement {}", statement);
			resultSet = statement.executeQuery();
			while (resultSet.next()) {
				musicalStyleList.add(getMusicalStyle(resultSet));
			}
		} catch (SQLException e) {
			log.error("Error musicalStyle ", e);
			throw new DaoException("Error musicalStyle", e);
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
		return musicalStyleList;
	}
}
