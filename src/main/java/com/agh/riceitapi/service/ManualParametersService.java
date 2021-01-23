package com.agh.riceitapi.service;


import com.agh.riceitapi.dto.ManualParametersDTO;
import com.agh.riceitapi.exception.ManualParametersNotFoundException;
import com.agh.riceitapi.model.ManualParameters;
import com.agh.riceitapi.repository.ManualParametersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ManualParametersService {

    @Autowired
    private ManualParametersRepository manualParametersRepository;


    public ManualParametersDTO getManualParameters(Long userId){
        ManualParameters mp = manualParametersRepository.findByUserId(userId).orElseThrow(
                () -> new ManualParametersNotFoundException("There is no manual parameters set for user with id: " + userId));

        return new ManualParametersDTO(
                mp.getManKcal(),
                mp.getManProtein(),
                mp.getManFat(),
                mp.getManCarbohydrate());
    }

    public void updateManualParameters(Long userId, ManualParametersDTO dto){
        ManualParameters mp = manualParametersRepository.findByUserId(userId).orElseThrow(
                () -> new ManualParametersNotFoundException("There is no manual parameters set for user with id: " + userId));

        mp.setManKcal(dto.getKcalMan());
        mp.setManProtein(dto.getProteinMan());
        mp.setManFat(dto.getFatMan());
        mp.setManCarbohydrate(dto.getCarbohydrateMan());

        manualParametersRepository.save(mp);
    }



}
