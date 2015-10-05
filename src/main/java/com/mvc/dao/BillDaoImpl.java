package com.mvc.dao;

import java.util.List;

import org.hibernate.Query;
import org.springframework.orm.hibernate4.support.HibernateDaoSupport;

import com.mvc.aop.LoggerAspect.Log;
import com.mvc.entities.Bill;

public class BillDaoImpl extends HibernateDaoSupport implements BillDao {

	@Log
	@Override
	public Bill get(final int id) {
		Bill bill = getHibernateTemplate().execute((session) -> {
			Query query = session.createQuery("select bill from Bill as bill left join fetch bill.person where bill.id = :id");
			query.setParameter("id", id);
			Bill result = (Bill) query.uniqueResult();
			return result;
		});
		return bill;
	}

	@Log
	@Override
	@SuppressWarnings("unchecked")
	public List<Bill> list() {
		List<Bill> bills = getHibernateTemplate().execute((session) -> {
			Query query = session.createQuery("select bill from Bill as bill left join fetch bill.person");
			List<Bill> result = query.list();
			return result;
		});
		return bills;
	}

	@Log
	@Override
	public Integer save(Bill bill) {
		return (Integer) getHibernateTemplate().save(bill);
	}

	@Log
	@Override
	public void update(Bill bill) {
		getHibernateTemplate().update(bill);
	}

}
