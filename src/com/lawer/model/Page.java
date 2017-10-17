package com.lawer.model;

public class Page {
	private int total;//总个数
	private int currentPage;//当前页
	private int pages;//总页数
	private int pernum;//每页个数
	private int start;//起始
	private int end;//结束
	public static int ROWS = 10;//默认每页显示个数

	public Page() {
		super();
	}
	
	public Page(int total, int currentPage) {
		this(total,currentPage,ROWS);
	}

	public Page(int total, int currentPage, int pernum) {
		super();
		this.total = total;
		this.currentPage = currentPage == 0 ? 1 : currentPage;
		this.pernum = pernum;
		this.pages = (int) Math.ceil((total + 0.0) / pernum);
		count();
	}

	private void count() {
		this.start = pernum * (currentPage - 1);
		this.end = pernum * (currentPage);
		if (this.end > total) {
			this.end = total;
		}
	}

	/**
	 * 是否有上一页
	 * @return
	 */
	public boolean hasPreview(){
		if(pages>1&&currentPage>1){
			return true;
		}
		return false;
	}
	/**
	 * 是否有下一页
	 * @return
	 */
	public boolean hasNext(){
		if(pages>1&&currentPage<pages){
			return true;
		}
		return false;
	}
	public int getTotal() {
		return total;
	}

	public void setTotal(int total) {
		this.total = total;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getPages() {
		return pages;
	}

	public void setPages(int pages) {
		this.pages = pages;
	}

	public int getPernum() {
		return pernum;
	}

	public void setPernum(int pernum) {
		this.pernum = pernum;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getEnd() {
		return end;
	}

	public void setEnd(int end) {
		this.end = end;
	}
}
