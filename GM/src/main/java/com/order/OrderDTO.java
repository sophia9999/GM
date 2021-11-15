package com.order;

public class OrderDTO {
	
	private int oNum;		// 주문번호
	private String userId;	// 회원아이디
	private String userName;// 회원이름
	private String order_date;// 주문날짜
	private int total_price;	// 총주문금액
	private int dPrice;		// 배송비
	private String phoneNum;	//연락처
	
	
	private String odNum;	// 주문상세번호
	private int cQty;	// 수량
	private int cdNum; // 의류디테일 번호
	
	private int ccNum;	// 의류색상번호
	private int sizes;	//사이즈
	
	private int cNum; 	//의류번호
	private String clothName;	// 의류명
	private int price; // 가격
	private int discount;//할인가
	
	private String deNum;	// 운송장번호
	private String state;	// 배송상태
	private String sendDate; // 배송보낸날짜
	private String arriveDate;	// 배송 도착예정일

	
	private String daddress_detail; // 상세주소
	private String dCode;		// 우편번호
	private String daddress;	//기본주소
	private String recipient;	//수취인
	private String recPhoneNum;	//수취인 전화번호
	private String request;
	
	private int fileNum;
	private String filename;
	
	public String getDeNum() {
		return deNum;
	}
	public void setDeNum(String deNum) {
		this.deNum = deNum;
	}
	public String getSendDate() {
		return sendDate;
	}
	public void setSendDate(String sendDate) {
		this.sendDate = sendDate;
	}
	public int getoNum() {
		return oNum;
	}
	public void setoNum(int oNum) {
		this.oNum = oNum;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getOrder_date() {
		return order_date;
	}
	public void setOrder_date(String order_date) {
		this.order_date = order_date;
	}
	public int getTotal_price() {
		return total_price;
	}
	public void setTotal_price(int total_price) {
		this.total_price = total_price;
	}
	public int getdPrice() {
		return dPrice;
	}
	public void setdPrice(int dPrice) {
		this.dPrice = dPrice;
	}
	public String getPhoneNum() {
		return phoneNum;
	}
	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	public String getOdNum() {
		return odNum;
	}
	public void setOdNum(String odNum) {
		this.odNum = odNum;
	}
	public int getcQty() {
		return cQty;
	}
	public void setcQty(int cQty) {
		this.cQty = cQty;
	}
	public int getCdNum() {
		return cdNum;
	}
	public void setCdNum(int cdNum) {
		this.cdNum = cdNum;
	}
	public int getCcNum() {
		return ccNum;
	}
	public void setCcNum(int ccNum) {
		this.ccNum = ccNum;
	}
	public int getSizes() {
		return sizes;
	}
	public void setSizes(int sizes) {
		this.sizes = sizes;
	}
	public int getcNum() {
		return cNum;
	}
	public void setcNum(int cNum) {
		this.cNum = cNum;
	}
	public String getClothName() {
		return clothName;
	}
	public void setClothName(String clothName) {
		this.clothName = clothName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getArriveDate() {
		return arriveDate;
	}
	public void setArriveDate(String arriveDate) {
		this.arriveDate = arriveDate;
	}
	public String getDaddress_detail() {
		return daddress_detail;
	}
	public void setDaddress_detail(String daddress_detail) {
		this.daddress_detail = daddress_detail;
	}
	public String getdCode() {
		return dCode;
	}
	public void setdCode(String dCode) {
		this.dCode = dCode;
	}
	public String getDaddress() {
		return daddress;
	}
	public void setDaddress(String daddress) {
		this.daddress = daddress;
	}
	public String getRecipient() {
		return recipient;
	}
	public void setRecipient(String recipient) {
		this.recipient = recipient;
	}
	public String getRecPhoneNum() {
		return recPhoneNum;
	}
	public void setRecPhoneNum(String recPhoneNum) {
		this.recPhoneNum = recPhoneNum;
	}
	public int getFileNum() {
		return fileNum;
	}
	public void setFileNum(int fileNum) {
		this.fileNum = fileNum;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	

}
