package com.symbio.dashboard.bean;

/**
 * Page Object
 * 
 * @author Shawn
 * 
 */
public class Paging implements java.io.Serializable {

	private static final long serialVersionUID = 48072538435083189L;

	private int pageSize = 20;
	private int currentPage = 1;
	private int currentRecord = 0;
	private int totalRecords = 0;
	private int totalPages = 0;

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecord(int totalRecords) {
		this.totalRecords = totalRecords;
	}

	public int getTotalPages() {
		totalPages = (totalRecords + pageSize - 1) / pageSize;
		if (this.currentPage > totalPages) {
			this.currentPage = 1;
		}
		return totalPages;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentRecord() {
		currentRecord = (currentPage - 1) * pageSize;
		return currentRecord;
	}
}
