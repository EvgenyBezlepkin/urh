package com.example.demo.controller;

import com.example.demo.domain.*;
import com.example.demo.repository.IntermediateResultRepository;
import com.example.demo.repository.ResultRepository;
import com.example.demo.repository.ServiceTemporalRepository;
import com.example.demo.repository.TestRepository;
import com.example.demo.service.TestIntermediateResult;
import com.example.demo.service.TestResult;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.time.LocalDateTime;

@Controller
public class Main {


    JavaMailSender emailSender;
    private final IntermediateResultRepository intermediateResult;
    private final ServiceTemporalRepository serviceTemporalRepository;
    private final TestIntermediateResult interRes;
    private final TestResult testRes;
    private final ResultRepository resultRepository;
    private final TestRepository testRepository;


    public Main(@Qualifier("getJavaMailSender") JavaMailSender emailSender, IntermediateResultRepository intermediateResult, ResultRepository resultRepository, ServiceTemporalRepository serviceTemporalRepository, TestIntermediateResult interRes, TestResult testRes, TestRepository testRepository) {
        this.resultRepository = resultRepository;
        this.emailSender = emailSender;
        this.intermediateResult = intermediateResult;
        this.serviceTemporalRepository = serviceTemporalRepository;
        this.interRes = interRes;
        this.testRes = testRes;
        this.testRepository = testRepository;
    }

    //C:\Java projects\demo\target\classes\sendMessageRu
    @GetMapping("/")
    public String showIndex() throws IOException, URISyntaxException {
        return "index";
    }


    @GetMapping("/test")
    public String startTest(Model model) {
        model.addAttribute("test", new Test());
        return "test";
    }


    @RequestMapping("/result")
    public String resTest(Model model, @ModelAttribute Test item, @RequestHeader("Accept-Language") String langHeader) {

        String lang = langHeader.substring(0, 2);

        IntermediateResult interRes = countTest(item);
        String res = countResult(interRes, lang);
        res = res.replaceAll("%. ", "%<br>");

        Result result = new Result(item.getName(), item.getEmail(), res, item.getAge(), LocalDateTime.now());

        Result resultSave = resultRepository.save(result);

        Long id = resultSave.getId();

        item.setId(id);
        testRepository.save(item);

        interRes.setId(id);
        intermediateResult.save(interRes);

        model.addAttribute("result", result);
        return "result";
    }


    @GetMapping("/about")
    public String getAboutPage() {
        return "about";
    }


    @GetMapping("/our_services")
    public String getServicesPage(Model model) {
        model.addAttribute("serviceTemporal", new ServiceTemporal());
        model.addAttribute("count", resultRepository.count());
        return "our_servicesTemporal";
    }


    @PostMapping("/our_services")
    public String saveServicesPage(@ModelAttribute ServiceTemporal st) {
        serviceTemporalRepository.save(st);
        return "redirect:/our_services";
    }


    @GetMapping("/contacts")
    public String getContactsPage(Model model) {
        model.addAttribute("rewiev", new Rewiev());
        return "contacts";
    }

    @GetMapping("/our_services_2")
    public String getServ(Model model) {
        return "our_services";
    }


    private IntermediateResult countTest(Test item) {
        interRes.setItem(item);
        return interRes.compute();
    }


    private String countResult(IntermediateResult interRes, String lang) {
        testRes.setInterRes(interRes);
        return testRes.getResult(lang);
    }


}
