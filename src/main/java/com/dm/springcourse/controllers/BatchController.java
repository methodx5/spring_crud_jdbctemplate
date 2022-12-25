package com.dm.springcourse.controllers;


import com.dm.springcourse.dao.PersonDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@RequestMapping("/batchorno")
public class BatchController {

    private final PersonDAO personDAO;

    @Autowired
    public BatchController(PersonDAO personDAO) {
        this.personDAO = personDAO;
    }


    @GetMapping()
    public String batchUpdate (){
        return "batch/index";
    }

    @GetMapping("/nobatch")
    public String withBatch(){
        personDAO.multiUpdate();
        return "redirect:/people";
    }

    @GetMapping("/batch")
    public String batch(){
        personDAO.batchUpdate();
        return "redirect:/people";
    }
}
