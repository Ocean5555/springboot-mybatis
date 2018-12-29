package com.ocean.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.ocean.entity.Emp;
import com.ocean.mapper.EmpMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 99512 on 2018/12/4 17:52.
 */
@Service
public class EmpService {
    @Autowired
    private EmpMapper empMapper;

    public Emp findById(Integer empno){
        return empMapper.findById(empno);
    }

    public List<Map> findEmps(String dname,Float sal){
        Map map = new HashMap();
        map.put("pdname" , dname);
        map.put("psal" , sal);
        return empMapper.findEmps(map);
    }

    public Page<Map> findAll(Integer page, Integer rows, String department,String work,String  keyword){
        PageHelper.startPage(page,rows);

        if("-1".equals(department)){
            department=null;
        }
        if("-1".equals(work)){
            work=null;
        }
        if("".equals(keyword)){
            keyword=null;
        }
        if(keyword!=null){
            keyword = "%" + keyword.trim() + "%";
        }
        Map map = new HashMap();
        map.put("department",department);
        map.put("work",work);
        map.put("keyword",keyword);
        Page<Map> list = (Page)empMapper.findAll(map);
        return list;
    }
}
