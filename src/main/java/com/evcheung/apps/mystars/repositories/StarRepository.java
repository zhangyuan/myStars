package com.evcheung.apps.mystars.repositories;


import com.evcheung.apps.mystars.entities.Star;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

@Component
public interface StarRepository extends CrudRepository<Star, Long> {
    Page<Star> findAll(Pageable pageable);
}
