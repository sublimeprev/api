package com.api.sublimeprev.service;

import com.api.sublimeprev.model.Mother;
import com.api.sublimeprev.repository.MotherRepository;
import org.hibernate.service.spi.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class MotherService {
    @Autowired
    private MotherRepository repository;

    public Mother saveMother(Mother mother){
        return this.repository.save(mother);
    }

    public Mother update(Mother newMother){
        LocalDateTime today = LocalDateTime.now();
        Mother mother = Mother.builder()
                .name(newMother.getName())
                .cpf(newMother.getCpf())
                .rg(newMother.getRg())
                .pis(newMother.getPis())
                .email(newMother.getEmail())
                .maritalStatusEnum(newMother.getMaritalStatusEnum())
                .schoolingEnum(newMother.getSchoolingEnum())
                .motherName(newMother.getMotherName())
                .fatherName(newMother.getFatherName())
                .birthday(newMother.getBirthday())
                .address(newMother.getAddress())
                .lastUpdated(today)
                .build();
        return mother;
    }

    public void deleteLogical(Long idMother){
        Mother mother = this.findById(idMother);
        mother.setDeleted(true);
    }

    public Mother findById(Long idMother){
        return this.repository.findById(idMother).orElseThrow(() -> new ServiceException("Mother not found"));
    }

    public List<Mother> findAllMother(){
        return this.repository.findAll();
    }

    public void send(){
        
    }

//    public List<Mother> findAllMotherIsDeletedFalse(){
//        return this.repository.findAllIsDeletedFalse();
//    }
}
