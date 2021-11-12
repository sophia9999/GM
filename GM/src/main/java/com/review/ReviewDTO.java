package com.review;

public class ReviewDTO {
	private int rNum;
	private int listNum;
	private String userId;
	private String subject;
	private String content;
	private String r_reg_date;

	private String clothName;
	private int odNum;
	private int oNum;
	private String color;
	private String sizes;
	
	private String allClothesName;
	private String cdNum;
	
	public int getrNum() {
		return rNum;
	}
	public void setrNum(int rNum) {
		this.rNum = rNum;
	}
	public int getListNum() {
		return listNum;
	}
	public void setListNum(int listNum) {
		this.listNum = listNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getSubject() {
		return subject;
	}
	public void setSubject(String subject) {
		this.subject = subject;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getR_reg_date() {
		return r_reg_date;
	}
	public void setR_reg_date(String r_reg_date) {
		this.r_reg_date = r_reg_date;
	}
	public String getClothName() {
		return clothName;
	}
	public void setClothName(String clothName) {
		this.clothName = clothName;
	}
	public int getOdNum() {
		return odNum;
	}
	public void setOdNum(int odNum) {
		this.odNum = odNum;
	}
	public int getoNum() {
		return oNum;
	}
	public void setoNum(int oNum) {
		this.oNum = oNum;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getSizes() {
		return sizes;
	}
	public void setSizes(String sizes) {
		this.sizes = sizes;
	}
	public String getAllClothesName() {
		return allClothesName;
	}
	public void setAllClothesName(String allClothesName) {
		this.allClothesName = allClothesName;
	}
	public String getCdNum() {
		return cdNum;
	}
	public void setCdNum(String cdNum) {
		this.cdNum = cdNum;
	}
}