package com.itstep.ppjava13v2.student.db.dao;

import com.itstep.ppjava13v2.student.db.dao.exeptions.DaoException;
import com.itstep.ppjava13v2.student.db.domain.Agent;

import java.util.Set;

/**

 */
public interface AgentDao {
	Set<Agent> findAll() throws DaoException;

	Set<Agent> findAllByEngagementId(long engagementId) throws DaoException;

	Agent findById(long id) throws DaoException;

	Set<Agent> findByAgentFirstName(String agentFirstName) throws DaoException;

	void update(Agent agent) throws DaoException;

	void remove(Agent agent) throws DaoException;

	void remove(long id) throws DaoException;

	Agent save(Agent agent) throws DaoException;

}
