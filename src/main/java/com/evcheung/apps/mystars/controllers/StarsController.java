package com.evcheung.apps.mystars.controllers;

import com.evcheung.apps.mystars.entities.Star;
import com.evcheung.apps.mystars.entities.Tag;
import com.evcheung.apps.mystars.repositories.StarRepository;
import com.evcheung.apps.mystars.requests.StarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/starred")
public class StarsController {
    @Autowired
    StarRepository starRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity index(){
        Page<Star> stars = starRepository.findAll(new PageRequest(0, 100));

        List<StarDto> collect = stars.getContent().stream().map(x -> new StarDto(x)).collect(Collectors.toList());
        return ResponseEntity.ok(collect);
    }

    @RequestMapping(path = "{owner}/{repo}", method = RequestMethod.PUT)
    public ResponseEntity update(@PathVariable("owner") String owner,
                                 @PathVariable("repo") String repo,
                                 @RequestBody StarRequest request){

        Star star = starRepository.findFirstByOwnerAndRepositoryName(owner, repo);

        if (star == null) {
            star = new Star();
        }
        star.setOwner(owner);
        star.setRepositoryName(repo);
        List<Tag> tags = request.getTags().stream().map(title -> {
            Tag tag = new Tag();
            tag.setTitle(title);
            return tag;
        }).collect(Collectors.toList());
        star.setTags(tags);

        Star savedStar = starRepository.save(star);

        return ResponseEntity.ok(new StarDto(savedStar));
    }
}