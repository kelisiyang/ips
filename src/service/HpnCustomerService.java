package service;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import dao.CustomerDao;
import dao.HpnCustomerDao;
import entity.Customer;
import entity.HpnCustomer;
import entity.PageBean;
@Transactional
public class HpnCustomerService {
	private HpnCustomerDao hpncustomerDao;

	public HpnCustomerDao getHpncustomerDao() {
		return hpncustomerDao;
	}

	public void setHpncustomerDao(HpnCustomerDao hpncustomerDao) {
		this.hpncustomerDao = hpncustomerDao;
	}

	public void add(HpnCustomer hpncustomer) {
		hpncustomerDao.add(hpncustomer);
		
	}

	public List<HpnCustomer> findAll() {
		return hpncustomerDao.findAll();
		
	}

	public HpnCustomer findOne(int cid) {
		// TODO Auto-generated method stub
		return hpncustomerDao.findOne(cid);
	}

	public void delete(HpnCustomer c) {
		hpncustomerDao.delete(c);
		
	}

	public void update(HpnCustomer hpncustomer) {
		// TODO Auto-generated method stub
		hpncustomerDao.update(hpncustomer);
	}


	/*public PageBean listPage(Integer currentPage) {
		// TODO Auto-generated method stub
		PageBean pageBean=new PageBean();
		pageBean.setCurrentPage(currentPage);
		int totalCount=hpncustomerDao.findCount();
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
		List<HpnCustomer> list=hpncustomerDao.showPage(begin,pageSize);
		pageBean.setList(list);
		return pageBean;
	}*/

	public List<HpnCustomer> findCondition(HpnCustomer hpncustomer) {
		// TODO Auto-generated method stub
		return hpncustomerDao.findCondition(hpncustomer);
	}

	//根据hpncustomer和起始终止时间查询
	public List<HpnCustomer> findCondition2(HpnCustomer hpncustomer,String starttime,String endtime) throws ParseException {
		// TODO Auto-generated method stub
		return hpncustomerDao.findCondition2(hpncustomer,starttime,endtime);
	}
    //根据起始时间和终止时间查看热点图
	public List<HpnCustomer> findCondition3(String starttime, String endtime) throws ParseException {
		// TODO Auto-generated method stub
		return hpncustomerDao.findCondition3(starttime,endtime);
	}

	
}
