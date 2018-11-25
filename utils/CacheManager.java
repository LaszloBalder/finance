package nl.yasmijn.utils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class CacheManager<T> {
	private int cacheSeconds;
	private ArrayList<T> list = new ArrayList<T>();

	public CacheManager(int cacheSeconds) {
		this.cacheSeconds = cacheSeconds;
	}

	public void add(String name, T data) {
		list.add(data);
	}

	public T getData(String name) {
		return list.get(0);
	}

	public static void main(String[] args) {

	}
}

class CacheData<T> {
	private Date validThrough;
	private String name;
	private Object data;

	public CacheData(String name, T data) {
		this.name = name;
		this.data = data;
		this.validThrough = Calendar.getInstance().getTime();
	}
}