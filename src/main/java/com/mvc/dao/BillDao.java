package com.mvc.dao;

import java.util.List;

import com.mvc.entities.Bill;

public interface BillDao {

	Bill get(int id);
	
	List<Bill> list();
	
	Integer save(Bill bill);
	
	void update(Bill bill);
}
