package dao;

import java.util.List;

import entity.Customer;
import entity.PageBean;

public interface CustomerDao {

	public void add(Customer customer);

	public List<Customer> findAll();

	public Customer findOne(int cid);

	public void delete(Customer c);

	public void update(Customer customer);

	public int findCount();

	public List<Customer> showPage(int begin, int pageSize);

	public List<Customer> findCondition(Customer customer);

	
}
