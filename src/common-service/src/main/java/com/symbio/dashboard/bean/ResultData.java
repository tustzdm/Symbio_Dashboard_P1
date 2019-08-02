package com.symbio.dashboard.bean;

/**
 * Returns the data encapsulation 
 * 
 * @author Shawn.Liu
 * 
 * @param <T>
 */
public class ResultData<T> implements java.io.Serializable {

	private static final long serialVersionUID = 480912760628480815L;

	private static final String OPE_SUCCESS = "0";

	private String ec = OPE_SUCCESS;
	private String em = "";
	private T data;
	private Paging paging;

	public ResultData() {
	}

	public ResultData(String code, String msg) {
		this.ec = code;
		this.em = msg;
	}

	public String getEm() {
		return em;
	}

	public void setEm(String msg) {
		this.em = msg;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public Paging getPaging() {
		return paging;
	}

	public String getEc() {
		return ec;
	}

	public void setEc(String ec) {
		this.ec = ec;
	}

	public void setPaging(Paging paging) {
		this.paging = paging;
	}

	public boolean isSuccess() {
		return OPE_SUCCESS.equals(this.ec);
	}

	public boolean hasError() {
		return !isSuccess();
	}
}
