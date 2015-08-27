package com.itstep.ppjava13v2.student.db.domain;

import java.util.Date;

/**
 * Агент
 */
public class Agent {
	private long agentId;
	private String agentFirstName;
	private String agentLastName;
	private String agentStreetAddress;
	private String agentCity;
	private String agentState;
	private String agentPhoneNumber;
	private Date agentDateHired;
	private long agentSalary;
	private long agentCommissionRate;

	public Agent() {

	}

	public Agent(String agentFirstName, String agentLastName, String agentStreetAddress, String agentCity,
	             String agentState, String agentPhoneNumber, Date agentDateHired,
	             long agentSalary, long agentCommissionRate) {
		this();
		this.agentFirstName = agentFirstName;
		this.agentLastName = agentLastName;
		this.agentStreetAddress = agentStreetAddress;
		this.agentCity = agentCity;
		this.agentState = agentState;
		this.agentPhoneNumber = agentPhoneNumber;
		this.agentDateHired = agentDateHired;
		this.agentSalary = agentSalary;
		this.agentCommissionRate = agentCommissionRate;
	}

	public Agent(long agentId, String agentFirstName, String agentLastName, String agentStreetAddress,
	             String agentCity, String agentState, String agentPhoneNumber, Date agentDateHired,
	             long agentSalary, long agentCommissionRate) {
		this();
		this.agentId = agentId;
		this.agentFirstName = agentFirstName;
		this.agentLastName = agentLastName;
		this.agentStreetAddress = agentStreetAddress;
		this.agentCity = agentCity;
		this.agentState = agentState;
		this.agentPhoneNumber = agentPhoneNumber;
		this.agentDateHired = agentDateHired;
		this.agentSalary = agentSalary;
		this.agentCommissionRate = agentCommissionRate;
	}

	public long getAgentId() {
		return agentId;
	}

	public void setAgentId(long agentId) {
		this.agentId = agentId;
	}

	public String getAgentFirstName() {
		return agentFirstName;
	}

	public void setAgentFirstName(String agentFirstName) {
		this.agentFirstName = agentFirstName;
	}

	public String getAgentLastName() {
		return agentLastName;
	}

	public void setAgentLastName(String agentLastName) {
		this.agentLastName = agentLastName;
	}

	public String getAgentStreetAddress() {
		return agentStreetAddress;
	}

	public void setAgentStreetAddress(String agentStreetAddress) {
		this.agentStreetAddress = agentStreetAddress;
	}

	public String getAgentCity() {
		return agentCity;
	}

	public void setAgentCity(String agentCity) {
		this.agentCity = agentCity;
	}

	public String getAgentState() {
		return agentState;
	}

	public void setAgentState(String agentState) {
		this.agentState = agentState;
	}

	public String getAgentPhoneNumber() {
		return agentPhoneNumber;
	}

	public void setAgentPhoneNumber(String agentPhoneNumber) {
		this.agentPhoneNumber = agentPhoneNumber;
	}

	public Date getAgentDateHired() {
		return agentDateHired;
	}

	public void setAgentDateHired(Date agentDateHired) {
		this.agentDateHired = agentDateHired;
	}

	public long getAgentSalary() {
		return agentSalary;
	}

	public void setAgentSalary(long agentSalary) {
		this.agentSalary = agentSalary;
	}

	public long getAgentCommissionRate() {
		return agentCommissionRate;
	}

	public void setAgentCommissionRate(long agentCommissionRate) {
		this.agentCommissionRate = agentCommissionRate;
	}

	@Override
	public String toString() {
		return "Agent{" +
				"agentId=" + agentId +
				", agentFirstName='" + agentFirstName + '\'' +
				", agentLastName='" + agentLastName + '\'' +
				", agentStreetAddress='" + agentStreetAddress + '\'' +
				", agentCity='" + agentCity + '\'' +
				", agentState='" + agentState + '\'' +
				", agentPhoneNumber='" + agentPhoneNumber + '\'' +
				", agentDateHired=" + agentDateHired +
				", agentSalary=" + agentSalary +
				", agentCommissionRate=" + agentCommissionRate +
				'}';
	}
}
