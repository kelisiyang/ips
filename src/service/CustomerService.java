package service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import dao.CustomerDao;
import entity.Customer;
import entity.PageBean;
@Transactional
public class CustomerService {
	private CustomerDao customerDao;

	public CustomerDao getCustomerDao() {
		return customerDao;
	}

	public void setCustomerDao(CustomerDao customerDao) {
		this.customerDao = customerDao;
	}

	public void add(Customer customer) {
		customerDao.add(customer);
		
	}

	public List<Customer> findAll() {
		return customerDao.findAll();
		
	}

	public Customer findOne(int cid) {
		// TODO Auto-generated method stub
		return customerDao.findOne(cid);
	}

	public void delete(Customer c) {
		customerDao.delete(c);
		
	}

	public void update(Customer customer) {
		// TODO Auto-generated method stub
		customerDao.update(customer);
	}


	public PageBean listPage(Integer currentPage) {
		// TODO Auto-generated method stub
		PageBean pageBean=new PageBean();
		pageBean.setCurrentPage(currentPage);
		int totalCount=customerDao.findCount();
		pageBean.setTotalCount(totalCount);
		int pageSize=3;
		int totalPage=0;
		if(totalCount%pageSize==0)
		{
			totalPage=totalCount/pageSize;
		}
		else
		{
			totalPage=totalCount/pageSize+1;
		}
		pageBean.setTotalPage(totalPage);
		int begin=(currentPage-1)*pageSize;
		List<Customer> list=customerDao.showPage(begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}

	public List<Customer> findCondition(Customer customer) {
		// TODO Auto-generated method stub
		return customerDao.findCondition(customer);
	}

	public List<Customer> findCondition2(Customer customer,String starttime,String endtime) throws ParseException {
		// TODO Auto-generated method stub
		return customerDao.findCondition2(customer,starttime,endtime);
	}

	
}
