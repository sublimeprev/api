package com.api.sublimeprev.service;

import com.api.sublimeprev.model.Consultant;
import com.api.sublimeprev.repository.ConsultantRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ConsultantService {

    @Autowired
    private ConsultantRepository repository;

    public Consultant saveConsultant(Consultant consultant){
        return this.repository.save(consultant);
    }

    public Consultant findById(Long idConsultant){
        return this.repository.findById(idConsultant).orElseThrow(() -> new ServiceException("Consultant not found") );
    }

    public List<Consultant> findAll(){
        return this.repository.findAll();
    }

    public void deleteLogical(Long idConsultant){
        Consultant consultant = this.findById(idConsultant);
        consultant.setDeleted(true);
    }
}
