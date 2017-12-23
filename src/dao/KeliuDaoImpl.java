package dao;

import java.util.List;

import org.springframework.orm.hibernate5.HibernateTemplate;

import entity.Keliu;

public class KeliuDaoImpl implements KeliuDao {
	private HibernateTemplate hibernateTemplate;

	public HibernateTemplate getHibernateTemplate() {
		return hibernateTemplate;
	}

	public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
		this.hibernateTemplate = hibernateTemplate;
	}
	@Override
	public void add(Keliu keliu) {
		
		hibernateTemplate.save(keliu);
		
	}
	@Override
	public List<Keliu> findAll() {
		// TODO Auto-generated method stub
		List<Keliu> list=(List<Keliu>) hibernateTemplate.find("from Keliu");
		return list;
	}
	public Keliu findOne(int kid) {
		// TODO Auto-generated method stub
		return hibernateTemplate.get(Keliu.class, kid);
	}
	@Override
	public void delete(Keliu keliu) {
		// TODO Auto-generated method stub
		hibernateTemplate.delete(keliu);
	}
	public void update(Keliu keliu2) {
		// TODO Auto-generated method stub
		hibernateTemplate.update(keliu2);
	}
}
