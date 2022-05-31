package com.pluralsight.conferencedemo.controllers;

import com.pluralsight.conferencedemo.models.Speaker;
import com.pluralsight.conferencedemo.repositories.SpeakerRespository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/speakers")
public class SpeakersController {

    @Autowired
    private SpeakerRespository speakerRespository;

    @GetMapping
    public List<Speaker> list() {
        return speakerRespository.findAll();
    }

    @GetMapping
    @RequestMapping("{id}")
    public Speaker get(@PathVariable Long id) {
        return speakerRespository.getOne(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Speaker create(@RequestBody Speaker speaker) {
        return speakerRespository.saveAndFlush(speaker);
    }

    @RequestMapping(name = "{id}", method = RequestMethod.DELETE)
    public void delete (@PathVariable Long id) {
        // Also need to check for children records before deleting
        speakerRespository.deleteById(id);
    }

    @RequestMapping(name = "{id}", method = RequestMethod.PUT)
    public Speaker update(@PathVariable Long id, @RequestBody Speaker speaker) {
        // bec this is PUT, we expect all attrs to be passed in, A PATCH would only need what
        // TODO: Add validation that all attrs are passed in otherwise return a 400 bad payload
        Speaker existingSpeaker = speakerRespository.getOne(id);
        BeanUtils.copyProperties(speaker, existingSpeaker, "speaker_id");
        return speakerRespository.saveAndFlush(existingSpeaker);
    }

}
