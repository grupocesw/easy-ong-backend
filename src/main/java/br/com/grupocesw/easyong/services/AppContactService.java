package br.com.grupocesw.easyong.services;

import br.com.grupocesw.easyong.entities.AppContact;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public interface AppContactService {

	AppContact create(AppContact request);

	AppContact retrieve(Long id);

	void delete(Long id);

	void existsOrThrowsException(Long id);
	
	Page<AppContact> findAll(Pageable pageable);

}
