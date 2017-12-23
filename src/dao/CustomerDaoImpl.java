package dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.HibernateTemplate;

import entity.Customer;
import entity.PageBean;

public class CustomerDaoImpl implements CustomerDao{
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public void add(Customer customer) {
		
		hibernateTemplate.save(customer);
	}
	public List<Customer> findAll() {
		return (List<Customer>) hibernateTemplate.find("from Customer");
	}
	public Customer findOne(int cid) {
		
		return hibernateTemplate.get(Customer.class, cid);

	}
	public void delete(Customer c) {
		hibernateTemplate.delete(c);
		
	}
	public void update(Customer customer) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(customer);
	}
	
	public int findCount() {
		List<Object> list=(List<Object>) hibernateTemplate.find("select count(*) from Customer");
		if(list!=null&&list.size()!=0)
		{
			Object obj=list.get(0);
			Long lobj=(Long)obj;
			int count=lobj.intValue();
			return count;
		}
		else return 0;
		
	}
	public List<Customer> showPage(int begin, int pageSize) {
		DetachedCriteria criteria=DetachedCriteria.forClass(Customer.class);
		@SuppressWarnings("unchecked")
		List<Customer> list=(List<Customer>) hibernateTemplate.findByCriteria(criteria, begin, pageSize);
		return list;
	}
	public List<Customer> findCondition(Customer customer) {
		@SuppressWarnings("unchecked")
		List<Customer> list = (List<Customer>) hibernateTemplate.find("from Customer where muMac like ?", "%"+customer.getMuMac()+"%");
		return list;
	}

}
