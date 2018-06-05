package com.seunghoo.na.domain;



public class Board {

	private int bno;
	private String title;
	private String content;
	//날짜를 가져올 때는 String으로 가져오는 것이좋습니다. 
	//년 월 일 순으로 데이터를 가져옵니다.
	private String regdate;
	private int readcnt;
	private String email;
	private String ip;
	private String nickname;
	
	private int replycnt;
	
	public int getReplycnt() {
		return replycnt;
	}

	public void setReplycnt(int replycnt) {
		this.replycnt = replycnt;
	}

	//날짜와 시간을 표현할 변수 
	//오늘 작성한 글은 시간을 어제 이전에 작성된 글은 날짜를 출력 
	private String dispDate ;

	public int getBno() {
		return bno;
	}

	public void setBno(int bno) {
		this.bno = bno;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getRegdate() {
		return regdate;
	}

	public void setRegdate(String regdate) {
		this.regdate = regdate;
	}

	public int getReadcnt() {
		return readcnt;
	}

	public void setReadcnt(int readcnt) {
		this.readcnt = readcnt;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public String getDispDate() {
		return dispDate;
	}

	public void setDispDate(String dispDate) {
		this.dispDate = dispDate;
	}

	@Override
	public String toString() {
		return "Board [bno=" + bno + ", title=" + title + ", content=" + content + ", regdate=" + regdate + ", readcnt="
				+ readcnt + ", email=" + email + ", ip=" + ip + ", nickname=" + nickname + ", dispDate=" + dispDate
				+ "]";
	}
	
}
