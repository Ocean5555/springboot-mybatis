package com.ocean.mapper;

import com.ocean.entity.Emp;

import java.util.List;
import java.util.Map;

/**
 * Created by 99512 on 2018/12/4 17:00.
 */
public interface EmpMapper {
    public Emp findById(Integer empno);

    //mybatis使用Map包含多个参数
    public List<Map> findEmps(Map map);
    public List<Map> findAll(Map map);
}
