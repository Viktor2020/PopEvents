package com.itstep.ppjava13v2.student.db.domain;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * партнер
 */
public class Member {
	private long memberId;
	private String memberFirstName;
	private String memberLastName;
	private String memberPhoneNumber;
	private String memberGender;

	private Set<Entertainer> memberEntertainerList;

	public Member() {
		memberEntertainerList = new HashSet<>();
	}

	public Member(String memberFirstName, String memberLastName, String memberPhoneNumber, String memberGender) {
		this();
		this.memberFirstName = memberFirstName;
		this.memberLastName = memberLastName;
		this.memberPhoneNumber = memberPhoneNumber;
		this.memberGender = memberGender;
	}

	public Member(long memberId, String memberFirstName, String memberLastName,
	              String memberPhoneNumber, String memberGender) {
		this();
		this.memberId = memberId;
		this.memberFirstName = memberFirstName;
		this.memberLastName = memberLastName;
		this.memberPhoneNumber = memberPhoneNumber;
		this.memberGender = memberGender;
	}

	public long getMemberId() {
		return memberId;
	}

	public void setMemberId(long memberId) {
		this.memberId = memberId;
	}

	public String getMemberFirstName() {
		return memberFirstName;
	}

	public void setMemberFirstName(String memberFirstName) {
		this.memberFirstName = memberFirstName;
	}

	public String getMemberLastName() {
		return memberLastName;
	}

	public void setMemberLastName(String memberLastName) {
		this.memberLastName = memberLastName;
	}

	public String getMemberPhoneNumber() {
		return memberPhoneNumber;
	}

	public void setMemberPhoneNumber(String memberPhoneNumber) {
		this.memberPhoneNumber = memberPhoneNumber;
	}

	public String getMemberGender() {
		return memberGender;
	}

	public void setMemberGender(String memberGender) {
		this.memberGender = memberGender;
	}

	public Set<Entertainer> getMemberEntertainerList() {
		return memberEntertainerList;
	}

	public void setMemberEntertainerList(Set<Entertainer> memberEntertainerList) {
		this.memberEntertainerList = memberEntertainerList;
	}

	public boolean addEntertainer(Entertainer entertainer) {
		return memberEntertainerList.add(entertainer);
	}

	public boolean removeEntertainer(Entertainer entertainer) {
		return memberEntertainerList.remove(entertainer);
	}

	@Override
	public String toString() {
		return "Member{" +
				"memberGender='" + memberGender + '\'' +
				", memberPhoneNumber='" + memberPhoneNumber + '\'' +
				", memberLastName='" + memberLastName + '\'' +
				", memberFirstName='" + memberFirstName + '\'' +
				", memberId=" + memberId +
				'}';
	}
}
