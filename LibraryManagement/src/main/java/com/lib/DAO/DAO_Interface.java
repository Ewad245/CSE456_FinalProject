package com.lib.DAO;

import java.util.List;


public interface DAO_Interface<T> {
	
	public List<T> selectAll();
	
	public T selectById(int id);
	
	public boolean insert(T t);

	public boolean delete(T t);

	boolean update(T s);
	
	
}
