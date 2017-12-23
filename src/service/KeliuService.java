package service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import dao.KeliuDao;
import entity.Keliu;
@Transactional
public class KeliuService {
	private KeliuDao keliuDao;

	public KeliuDao getKeliuDao() {
		return keliuDao;
	}

	public void setKeliuDao(KeliuDao keliuDao) {
		this.keliuDao = keliuDao;
}

	public void add(Keliu keliu) {
		// TODO Auto-generated method stub
		keliuDao.add(keliu);
	}

	public List<Keliu> findAll() {
		
		return keliuDao.findAll();
	}

	public Keliu findOne(int kid) {
		
		return keliuDao.findOne(kid);
	}

	public void delete(Keliu keliu) {
		// TODO Auto-generated method stub
		keliuDao.delete(keliu);
	}

	public void update(Keliu keliu2) {
		// TODO Auto-generated method stub
		keliuDao.update(keliu2);
	}
}
