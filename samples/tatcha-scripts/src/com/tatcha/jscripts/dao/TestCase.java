package com.tatcha.jscripts.dao;

public class TestCase {

	private String testNo;
	private String mocNo;
	private String functionality;
	private String status;
	private String remarks;

	public TestCase() {

	}

	public TestCase(String testNo) {
		this.testNo = testNo;
	}

	public TestCase(String testNo, String mocNo, String functionality, String status, String remarks) {

		this.testNo = testNo;
		this.mocNo = mocNo;
		this.functionality = functionality;
		this.status = status;
		this.remarks = remarks;
	}

	public String getTestNo() {
		return testNo;
	}

	public void setTestNo(String testNo) {
		this.testNo = testNo;
	}

	public String getMocNo() {
		return mocNo;
	}

	public void setMocNo(String mocNo) {
		this.mocNo = mocNo;
	}

	public String getFunctionality() {
		return functionality;
	}

	public void setFunctionality(String functionality) {
		this.functionality = functionality;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public String toString() {
		// return super.toString();
		return "Test No:" + testNo + " of MOC " + mocNo + " for Functionality " + functionality + " is having status: "
				+ status + " !! ";
	}
}