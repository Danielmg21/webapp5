package com.lcdd.backend.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.lcdd.backend.dbrepositories.MerchandisingRepository;
import com.lcdd.backend.pojo.Merchandising;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Service
public class MerchandisingService {
	
	@Autowired
	private MerchandisingRepository repository;
	
	public Merchandising findById(long id) {
		return repository.findById(id);
	}

	public List<Merchandising> findAll() {
		return repository.findAll();
	}
	public void save(Merchandising merch) {
		repository.save(merch);
	}

	public void delete(long id) {
		repository.deleteById(id);
	}
	
	public Page<Merchandising> findAllPages(int page, int size) {
		return repository.findAll(PageRequest.of(page,size));
	}
	
	public boolean createMerch(Merchandising merch) {
		if(repository.findById(merch.getId()) != null) {
			//already exist a merch with the same id
			return false;
		}
		if(repository.findByName(merch.getName()) != null){
			//already exist a merch with the same name
			return false;
		}
		save(merch);
		return true;
	}
	

}
