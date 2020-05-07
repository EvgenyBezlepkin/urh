package com.example.demo.controller;

import com.example.demo.domain.Rewiev;
import com.example.demo.repository.RewievRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ContactsController {

    @Autowired
    private RewievRepository rewievRepository;

    @RequestMapping(value = "contacts",
            method = RequestMethod.POST,
            produces = { MediaType.APPLICATION_JSON_VALUE},
            consumes = {MediaType.APPLICATION_JSON_VALUE})
    public void saveRec(@RequestBody Rewiev rewiev) {
        rewievRepository.save(rewiev);

    }



}
