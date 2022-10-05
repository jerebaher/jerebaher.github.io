package com.mindhub.homebanking.Services.Implements;

import com.mindhub.homebanking.Services.DisabledCardService;
import com.mindhub.homebanking.models.DisabledCard;
import com.mindhub.homebanking.repositories.DisabledCardRepositories;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DisabledCardServicesImpl implements DisabledCardService {

    @Autowired
    DisabledCardRepositories disabledCardRepositories;

    @Override
    public List<DisabledCard> findAllDisabledCards(){
        return disabledCardRepositories.findAll();
    }

    @Override
    public DisabledCard findDisabledCardById(Long id){
        return disabledCardRepositories.findById(id).get();
    }

    @Override
    public void saveDisabledCard(DisabledCard disabledCard){
        disabledCardRepositories.save(disabledCard);
    }

    @Override
    public void deleteDisabledCard(DisabledCard disabledCard){
        disabledCardRepositories.delete(disabledCard);
    }
}
