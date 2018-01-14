package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.springframework.orm.hibernate5.HibernateTemplate;

import entity.Customer;
import entity.HpnCustomer;
import entity.PageBean;

public class HpnCustomerDaoImpl implements HpnCustomerDao{
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}

	@Override
	public void add(HpnCustomer hpncustomer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public HpnCustomer findOne(int cid) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(HpnCustomer c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(HpnCustomer hpncustomer) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<HpnCustomer> findCondition(HpnCustomer hpncustomer) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HpnCustomer> findCondition2(HpnCustomer hpncustomer, String starttime, String endtime)
			throws ParseException {
		@SuppressWarnings("unchecked")
		//将String转换成Date类型 
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date startdate = sdf.parse(starttime);
		Date enddate=sdf.parse(endtime);
		Object[] values={"app",hpncustomer.getMACCode(),startdate,enddate};
		String hql="from HpnCustomer where operater=? and MACCode=? and updateDatetime>? and updateDatetime<? order by updateDatetime";
		List<HpnCustomer> list = (List<HpnCustomer>) hibernateTemplate.find(hql,values);
		return list;
	}

	@Override
	public List<HpnCustomer> findAll() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int findCount() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public List<HpnCustomer> showPage(int begin, int pageSize) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<HpnCustomer> findCondition3(String starttime, String endtime) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}
}
