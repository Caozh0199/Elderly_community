package com.example.service;

import com.example.entity.Compara;
import com.example.mapper.BaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class BaseService {

    @Autowired
    private BaseMapper baseMapper;

    public Map<String,String> ParaConfig(String paraName){
        Map<String,String> map = new HashMap<>();
        List<Compara> comparas = baseMapper.ParaConfig(paraName);
        if(comparas.size() > 0 ){
            for (Compara compara : comparas) {
                map.put("PARANAME", compara.getParaName());
                map.put("PARACODE1", compara.getParaCode1());
                map.put("PARACODE2", compara.getParaCode2());
                map.put("PARACODE3", compara.getParaCode3());
            }
            return map;
        }else {
            map.put("PARACODE1", "true");
            return map;
        }
    }
}
