package com.evcheung.apps.mystars.controllers;

import com.evcheung.apps.mystars.entities.Star;
import com.evcheung.apps.mystars.entities.Tag;
import com.evcheung.apps.mystars.repositories.StarRepository;
import com.evcheung.apps.mystars.requests.GitHubImportRequest;
import com.evcheung.apps.mystars.requests.StarRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
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

        Star savedStar = saveStar(owner, repo, request.getTags());

        return ResponseEntity.ok(new StarDto(savedStar));
    }

    private Star saveStar(String owner, String repo, List<String> tags) {
        Star star = starRepository.findFirstByOwnerAndRepositoryName(owner, repo);

        if (star == null) {
            star = new Star();
        }
        star.setOwner(owner);
        star.setRepositoryName(repo);
        star.setTags(tags.stream().map(title -> {
            Tag tag = new Tag();
            tag.setTitle(title);
            return tag;
        }).collect(Collectors.toList()));

        return starRepository.save(star);
    }

    @RequestMapping(path = "import", method = RequestMethod.PUT)
    public ResponseEntity importFromGitHub(
                                 @RequestBody GitHubImportRequest request){
        RestTemplate restTemplate = new RestTemplate();

        String url = "https://api.github.com/users/" + request.getUsername() + "/starred";
        GitHubStarred[] starred = restTemplate.getForEntity(url, GitHubStarred[].class).getBody();

        for (int i = 0; i <  starred.length; i++) {
            GitHubStarred x = starred[i];
            saveStar(x.getName(), x.getOwner().login, new ArrayList<>());
        }

        return ResponseEntity.ok().build();
    }
}