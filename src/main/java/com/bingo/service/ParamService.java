package com.bingo.service;

import com.bingo.mapper.ParamMapper;
import com.bingo.model.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class ParamService {
    private final ParamMapper dao;

    @Autowired
    public ParamService(ParamMapper dao) {
        this.dao = dao;
    }

    public boolean insert(Param model) {
        //设置id
        model.setId(UUID.randomUUID().toString());
        //设置时间
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String time = format.format(new Date().getTime());//这个就是把时间戳经过处理得到期望格式的时间
        model.setCreateTime(time);

        return dao.insert(model) > 0;
    }

    public Param select(int id) {
        return dao.select(id);
    }

    public Param selectLast() {
        return dao.selectLast();
    }

    public List<Param> selectAll() {
        return dao.selectAll();
    }

    public boolean updateValue(Param model) {
        return dao.updateValue(model) > 0;
    }

    public boolean delete(Integer id) {
        return dao.delete(id) > 0;
    }

    
}
