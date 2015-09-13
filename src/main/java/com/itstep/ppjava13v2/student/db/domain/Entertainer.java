package com.itstep.ppjava13v2.student.db.domain;

import java.util.*;

/**
 * эстрадный артист
 */
public class Entertainer {
	private long entertainerId;
	private String entertainerStageName;
	private String entertainerStreetAddress;
	private String entertainerCity;
	private String entertainerState;
	private String entertainerPhoneNumber;
	private String entertainerWebPage;
	private String entertainerEmailAddress;
	private Date entertainerDateEntered;

	private Set<MusicalStyle> entertainerMusicalStyleList; // муз стили
	private Set<Member> entertainerMemberList;     // партнеры

	public Entertainer() {
		entertainerMusicalStyleList = new HashSet<>();
		entertainerMemberList = new HashSet<>();
	}

	public Entertainer(String entertainerStageName, String entertainerStreetAddress, String entertainerCity,
	                   String entertainerState, String entertainerPhoneNumber, String entertainerWebPage,
	                   String entertainerEmailAddress, Date entertainerDateEntered) {
		this();
		this.entertainerStageName = entertainerStageName;
		this.entertainerStreetAddress = entertainerStreetAddress;
		this.entertainerCity = entertainerCity;
		this.entertainerState = entertainerState;
		this.entertainerPhoneNumber = entertainerPhoneNumber;
		this.entertainerWebPage = entertainerWebPage;
		this.entertainerEmailAddress = entertainerEmailAddress;
		this.entertainerDateEntered = entertainerDateEntered;
	}

	public Entertainer(long entertainerId, String entertainerStageName, String entertainerStreetAddress,
	                   String entertainerCity, String entertainerState, String entertainerPhoneNumber,
	                   String entertainerWebPage, String entertainerEmailAddress, Date entertainerDateEntered) {
		this();
		this.entertainerId = entertainerId;
		this.entertainerStageName = entertainerStageName;
		this.entertainerStreetAddress = entertainerStreetAddress;
		this.entertainerCity = entertainerCity;
		this.entertainerState = entertainerState;
		this.entertainerPhoneNumber = entertainerPhoneNumber;
		this.entertainerWebPage = entertainerWebPage;
		this.entertainerEmailAddress = entertainerEmailAddress;
		this.entertainerDateEntered = entertainerDateEntered;
	}

	public long getEntertainerId() {
		return entertainerId;
	}

	public void setEntertainerId(long entertainerId) {
		this.entertainerId = entertainerId;
	}

	public String getEntertainerStageName() {
		return entertainerStageName;
	}

	public void setEntertainerStageName(String entertainerStageName) {
		this.entertainerStageName = entertainerStageName;
	}

	public String getEntertainerStreetAddress() {
		return entertainerStreetAddress;
	}

	public void setEntertainerStreetAddress(String entertainerStreetAddress) {
		this.entertainerStreetAddress = entertainerStreetAddress;
	}

	public String getEntertainerCity() {
		return entertainerCity;
	}

	public void setEntertainerCity(String entertainerCity) {
		this.entertainerCity = entertainerCity;
	}

	public String getEntertainerState() {
		return entertainerState;
	}

	public void setEntertainerState(String entertainerState) {
		this.entertainerState = entertainerState;
	}

	public String getEntertainerPhoneNumber() {
		return entertainerPhoneNumber;
	}

	public void setEntertainerPhoneNumber(String entertainerPhoneNumber) {
		this.entertainerPhoneNumber = entertainerPhoneNumber;
	}

	public String getEntertainerWebPage() {
		return entertainerWebPage;
	}

	public void setEntertainerWebPage(String entertainerWebPage) {
		this.entertainerWebPage = entertainerWebPage;
	}

	public String getEntertainerEmailAddress() {
		return entertainerEmailAddress;
	}

	public void setEntertainerEmailAddress(String entertainerEmailAddress) {
		this.entertainerEmailAddress = entertainerEmailAddress;
	}

	public Date getEntertainerDateEntered() {
		return entertainerDateEntered;
	}

	public void setEntertainerDateEntered(Date entertainerDateEntered) {
		this.entertainerDateEntered = entertainerDateEntered;
	}

	public Set<MusicalStyle> getEntertainerMusicalStyleList() {
		return entertainerMusicalStyleList;
	}

	public void setEntertainerMusicalStyleList(Set<MusicalStyle> entertainerMusicalStyleList) {
		this.entertainerMusicalStyleList = entertainerMusicalStyleList;
	}

	public Set<Member> getEntertainerMemberList() {
		return entertainerMemberList;
	}

	public void setEntertainerMemberList(Set<Member> entertainerMemberList) {
		this.entertainerMemberList = entertainerMemberList;
	}

	public boolean addMusicalStyle(MusicalStyle musicalStyle) {
		return entertainerMusicalStyleList.add(musicalStyle);
	}

	public boolean removeMusicalStyle(MusicalStyle musicalStyle) {
		return entertainerMusicalStyleList.remove(musicalStyle);
	}

	public boolean addMember(Member member) {
		return entertainerMemberList.add(member);
	}

	public boolean removeMember(Member member) {
		return entertainerMemberList.remove(member);
	}

	@Override
	public String toString() {
		return "Entertainer{" +
				"entertainerDateEntered=" + entertainerDateEntered +
				", entertainerEmailAddress='" + entertainerEmailAddress + '\'' +
				", entertainerWebPage='" + entertainerWebPage + '\'' +
				", entertainerPhoneNumber='" + entertainerPhoneNumber + '\'' +
				", entertainerState='" + entertainerState + '\'' +
				", entertainerCity='" + entertainerCity + '\'' +
				", entertainerStreetAddress='" + entertainerStreetAddress + '\'' +
				", entertainerStageName='" + entertainerStageName + '\'' +
				", entertainerId=" + entertainerId +
				'}';
	}
}
