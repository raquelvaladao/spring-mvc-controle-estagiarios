package com.gft.management.services;


import com.gft.management.models.Daily;
import com.gft.management.repositories.DailyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DailyService {

    @Autowired
    private DailyRepository dailyRepository;

    public Daily saveDaily(Daily daily){
        Daily dailySalvo = dailyRepository.save(daily);
        return daily;
    }

    public Daily getDaily(Long id){
        Daily daily = dailyRepository.findById(id).get();
        return daily;
    }

    public List<Daily> listAllDailys(){
        return dailyRepository.findAll();
    }

    public Daily findDaily(Long id) throws Exception {
        Optional<Daily> daily = dailyRepository.findById(id);

        if(daily.isEmpty()){
            throw new Exception("Daily não encontrada");
        }
        return daily.get();
    }

    public void deleteDaily(Long id) {
        dailyRepository.deleteById(id);
    }

}
