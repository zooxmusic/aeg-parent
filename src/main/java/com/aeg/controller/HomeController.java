package com.aeg.controller;

import java.util.ArrayList;
import java.util.List;

import com.aeg.ims.model.Employee;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class HomeController {

    private List<Employee> employees=new ArrayList<Employee>();

    @RequestMapping(value="/")
    public String loadHomePage(Model model){

        model.addAttribute("employees", employees);

        return "index";
    }


    @RequestMapping(value="/addEmployee",method=RequestMethod.POST)
    public String addEmployee(@ModelAttribute("employee")Employee employee, Model model){

        employees.add(employee);

        return "redirect:/";
    }
}