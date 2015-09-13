package com.itstep.ppjava13v2.student.db.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Клиент
 */
public class Customer {
	private long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String customerStreetAddress;
	private String customerCity;
	private String customerState;
	private String customerPhoneNumber;

	private Set<MusicalStyle> customerMusicalStyleList;

	public Customer() {
		customerMusicalStyleList = new HashSet<>();
	}

	public Customer(String customerFirstName, String customerLastName, String customerStreetAddress,
	                String customerCity, String customerState, String customerPhoneNumber) {
		this();
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.customerStreetAddress = customerStreetAddress;
		this.customerCity = customerCity;
		this.customerState = customerState;
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public Customer(long customerId, String customerFirstName, String customerLastName,
	                String customerStreetAddress, String customerCity, String customerState, String customerPhoneNumber) {
		this();
		this.customerId = customerId;
		this.customerFirstName = customerFirstName;
		this.customerLastName = customerLastName;
		this.customerStreetAddress = customerStreetAddress;
		this.customerCity = customerCity;
		this.customerState = customerState;
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(long customerId) {
		this.customerId = customerId;
	}

	public String getCustomerFirstName() {
		return customerFirstName;
	}

	public void setCustomerFirstName(String customerFirstName) {
		this.customerFirstName = customerFirstName;
	}

	public String getCustomerLastName() {
		return customerLastName;
	}

	public void setCustomerLastName(String customerLastName) {
		this.customerLastName = customerLastName;
	}

	public String getCustomerStreetAddress() {
		return customerStreetAddress;
	}

	public void setCustomerStreetAddress(String customerStreetAddress) {
		this.customerStreetAddress = customerStreetAddress;
	}

	public String getCustomerCity() {
		return customerCity;
	}

	public void setCustomerCity(String customerCity) {
		this.customerCity = customerCity;
	}

	public String getCustomerState() {
		return customerState;
	}

	public void setCustomerState(String customerState) {
		this.customerState = customerState;
	}

	public String getCustomerPhoneNumber() {
		return customerPhoneNumber;
	}

	public void setCustomerPhoneNumber(String customerPhoneNumber) {
		this.customerPhoneNumber = customerPhoneNumber;
	}

	public Set<MusicalStyle> getCustomerMusicalStyleList() {
		return customerMusicalStyleList;
	}

	public void setCustomerMusicalStyleList(Set<MusicalStyle> customerMusicalStyleList) {
		this.customerMusicalStyleList = customerMusicalStyleList;
	}

	public boolean addMusicalStyle(MusicalStyle musicalStyle) {
		return customerMusicalStyleList.add(musicalStyle);
	}

	public boolean removeMusicalStyle(MusicalStyle musicalStyle) {
		return customerMusicalStyleList.remove(musicalStyle);
	}

	@Override
	public String toString() {
		return "Customer{" +
				"customerPhoneNumber='" + customerPhoneNumber + '\'' +
				", customerState='" + customerState + '\'' +
				", customerCity='" + customerCity + '\'' +
				", customerStreetAddress='" + customerStreetAddress + '\'' +
				", customerLastName='" + customerLastName + '\'' +
				", customerFirstName='" + customerFirstName + '\'' +
				", customerId=" + customerId +
				'}';
	}
}
