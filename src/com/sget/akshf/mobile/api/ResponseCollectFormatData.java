package com.sget.akshf.mobile.api;

import java.util.List;

public class ResponseCollectFormatData<T> extends ResponseFactory{

	private List<T> data ;

	public List<T> getData() {
		return data;
	}

	public void setData(List<T> data) {
		this.data = data;
	}
	
}
