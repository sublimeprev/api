package com.sublimeprev.api.bases;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PageRes<T> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8014546240759030993L;
	private List<T> content;
	private long totalElements;
	private int totalPages;

	public PageRes(List<T> content, long totalElements, int totalPages) {
		this.content = content;
		this.totalElements = totalElements;
		this.totalPages = totalPages;
	}
}