package com.ocean.controller;

import ch.qos.logback.core.net.SyslogOutputStream;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.github.pagehelper.Page;
import com.ocean.entity.Emp;
import com.ocean.service.EmpService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 99512 on 2018/12/4 17:54.
 */
@Controller
@RequestMapping("/emp")
public class EmpController {
    @Resource
    private EmpService empService;

    @RequestMapping("/{id}")
    @ResponseBody
    public Emp findById(@PathVariable("id") Integer id){
        return empService.findById(id);
    }

    @RequestMapping("/list")
    @ResponseBody
    public List<Map> findEmps(String dname,Float sal){
        List<Map> emps = empService.findEmps(dname,sal);
        for(Map map : emps){
            System.out.println(map.entrySet());
        }
        return emps;
    }

    @RequestMapping("/manager")
    public String findAll(){
        return "manager";
    }

    @RequestMapping("/manager/list")
    @ResponseBody
    public Map findEmps(Integer page, Integer limit,String department,String work,String  keyword){
        Page<Map> p = empService.findAll(page,limit,department,work,keyword);
        Map result = new HashMap();
        result.put("code",0);
        result.put("msg","");
        result.put("count",p.getTotal());
        result.put("data",p.getResult());
        return result;
    }

    @RequestMapping("delete")
    @ResponseBody
    public Map  deleteById(String empno){
        Map map = new HashMap();
        map.put("code",0);
        map.put("msg","aa---"+empno);
        return map;
    }
}
