package com.itstep.ppjava13v2.student.db.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * Музыкальный стиль
 */
public class MusicalStyle {
	private long musicalStyleId;
	private String musicalStyleName;

	private Set<Customer> musicalStyleCustomerList;
	private Set<Entertainer> musicalStyleEntertainerList;

	public MusicalStyle() {
		musicalStyleCustomerList = new HashSet<>();
		musicalStyleEntertainerList = new HashSet<>();
	}

	public MusicalStyle(String musicalStyleName) {
		this();
		this.musicalStyleName = musicalStyleName;
	}

	public MusicalStyle(long musicalStyleId, String musicalStyleName) {
		this();
		this.musicalStyleId = musicalStyleId;
		this.musicalStyleName = musicalStyleName;
	}

	public long getMusicalStyleId() {
		return musicalStyleId;
	}

	public void setMusicalStyleId(long musicalStyleId) {
		this.musicalStyleId = musicalStyleId;
	}

	public String getMusicalStyleName() {
		return musicalStyleName;
	}

	public void setMusicalStyleName(String musicalStyleName) {
		this.musicalStyleName = musicalStyleName;
	}

	public Set<Customer> getMusicalStyleCustomerList() {
		return musicalStyleCustomerList;
	}

	public void setMusicalStyleCustomerList(Set<Customer> musicalStyleCustomerList) {
		this.musicalStyleCustomerList = musicalStyleCustomerList;
	}

	public Set<Entertainer> getMusicalStyleEntertainerList() {
		return musicalStyleEntertainerList;
	}

	public void setMusicalStyleEntertainerList(Set<Entertainer> musicalStyleEntertainerList) {
		this.musicalStyleEntertainerList = musicalStyleEntertainerList;
	}

	public boolean addCustomer(Customer customer) {
		return musicalStyleCustomerList.add(customer);
	}

	public boolean removeCustomer(Customer customer) {
		return musicalStyleCustomerList.remove(customer);
	}

	public boolean addEntertainer(Entertainer entertainer) {
		return musicalStyleEntertainerList.add(entertainer);
	}

	public boolean removeEntertainer(Entertainer entertainer) {
		return musicalStyleEntertainerList.remove(entertainer);
	}

	@Override
	public String toString() {
		return "MusicalStyle{" +
				"musicalStyleId=" + musicalStyleId +
				", musicalStyleName=" + musicalStyleName +
				'}';
	}
}
