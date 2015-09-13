package com.itstep.ppjava13v2.student.db.domain;

import java.util.*;

/**
 * контракт
 */
public class Engagement {
	private long engagementId;
	private Date engagementStartDate;
	private Date engagementEndDate;
	private long engagementPrice;

	private Set<Customer> engagementCustomerList;       // клиенты
	private Set<Agent> engagementAgentList;             // агенты
	private Set<Entertainer> engagementEntertainerList; // артисты

	public Engagement() {
		engagementEntertainerList = new HashSet<>();
		engagementAgentList = new HashSet<>();
		engagementCustomerList = new HashSet<>();
	}

	public Engagement(Date engagementStartDate, Date engagementEndDate, long engagementPrice) {
		this();
		this.engagementStartDate = engagementStartDate;
		this.engagementEndDate = engagementEndDate;
		this.engagementPrice = engagementPrice;
	}

	public Engagement(long engagementId, Date engagementStartDate, Date engagementEndDate, long engagementPrice) {
		this();
		this.engagementId = engagementId;
		this.engagementStartDate = engagementStartDate;
		this.engagementEndDate = engagementEndDate;
		this.engagementPrice = engagementPrice;
	}

	public long getEngagementId() {
		return engagementId;
	}

	public void setEngagementId(long engagementId) {
		this.engagementId = engagementId;
	}

	public Date getEngagementStartDate() {
		return engagementStartDate;
	}

	public void setEngagementStartDate(Date engagementStartDate) {
		this.engagementStartDate = engagementStartDate;
	}

	public Date getEngagementEndDate() {
		return engagementEndDate;
	}

	public void setEngagementEndDate(Date engagementEndDate) {
		this.engagementEndDate = engagementEndDate;
	}

	public long getEngagementPrice() {
		return engagementPrice;
	}

	public void setEngagementPrice(long engagementPrice) {
		this.engagementPrice = engagementPrice;
	}

	public Set<Customer> getEngagementCustomerList() {
		return engagementCustomerList;
	}

	public void setEngagementCustomerList(Set<Customer> engagementCustomerList) {
		this.engagementCustomerList = engagementCustomerList;
	}

	public Set<Agent> getEngagementAgentList() {
		return engagementAgentList;
	}

	public void setEngagementAgentList(Set<Agent> engagementAgentList) {
		this.engagementAgentList = engagementAgentList;
	}

	public Set<Entertainer> getEngagementEntertainerList() {
		return engagementEntertainerList;
	}

	public void setEngagementEntertainerList(Set<Entertainer> engagementEntertainerList) {
		this.engagementEntertainerList = engagementEntertainerList;
	}

	public boolean addCustomer(Customer customer) {
		return engagementCustomerList.add(customer);
	}

	public boolean removeCustomer(Customer customer) {
		return engagementCustomerList.remove(customer);
	}

	public boolean addAgent(Agent agent) {
		return engagementAgentList.add(agent);
	}

	public boolean removeAgent(Agent agent) {
		return engagementAgentList.remove(agent);
	}

	public boolean addEntertainer(Entertainer entertainer) {
		return engagementEntertainerList.add(entertainer);
	}

	public boolean removeEntertainer(Entertainer entertainer) {
		return engagementEntertainerList.remove(entertainer);
	}

	@Override
	public String toString() {
		return "Contract{" +
				"engagementPrice=" + engagementPrice +
				", engagementEndDate=" + engagementEndDate +
				", engagementStartDate=" + engagementStartDate +
				", engagementId=" + engagementId +
				'}';
	}
}
