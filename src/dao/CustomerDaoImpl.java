package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    //根据mumac和起始结束时间段查询轨迹
	@Override
	public List<Customer> findCondition2(Customer customer, String starttime, String endtime) throws ParseException {
		@SuppressWarnings("unchecked")
		//将String转换成Date类型 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startdate = sdf.parse(starttime);
		Date enddate=sdf.parse(endtime);
		Object[] values={customer.getMuMac(),startdate,enddate};
		String hql="from Customer where muMac=? and recvtime>? and recvtime<?";
		List<Customer> list = (List<Customer>) hibernateTemplate.find(hql,values);
		return list;
	}
	@Override
	public List<Customer> findCondition3(String starttime, String endtime) throws ParseException {
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startdate = sdf.parse(starttime);
		Date enddate=sdf.parse(endtime);
		Object[] values={startdate,enddate};
		String hql="from Customer where recvtime>? and recvtime<?";
		List<Customer> list = (List<Customer>) hibernateTemplate.find(hql,values);
		return list;
}
}
