package dao;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import entity.Customer;
import entity.HpnCustomer;
import entity.PageBean;

public interface HpnCustomerDao {

	public void add(HpnCustomer hpncustomer);

	public List<HpnCustomer> findAll();

	public HpnCustomer findOne(int cid);

	public void delete(HpnCustomer c);

	public void update(HpnCustomer hpncustomer);

	public int findCount();

	public List<HpnCustomer> showPage(int begin, int pageSize);

	public List<HpnCustomer> findCondition(HpnCustomer hpncustomer);
	//������ѯ ����mac starttime��endtime ��ѯ�����customer
	public List<HpnCustomer> findCondition2(HpnCustomer hpncustomer, String starttime, String endtime)throws ParseException;
	//������ʼʱ�����ֹʱ��鿴�ȵ�ͼ
	public List<HpnCustomer> findCondition3(String starttime, String endtime) throws ParseException;

	
}
