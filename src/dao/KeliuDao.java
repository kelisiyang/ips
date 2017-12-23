package dao;

import java.util.List;

import entity.Keliu;

public interface KeliuDao {

	void add(Keliu keliu);

	List<Keliu> findAll();

	Keliu findOne(int kid);

	void delete(Keliu keliu);

	void update(Keliu keliu2);

}
