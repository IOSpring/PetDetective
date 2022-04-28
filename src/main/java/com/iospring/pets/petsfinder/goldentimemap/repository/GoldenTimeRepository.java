package com.iospring.pets.petsfinder.goldentimemap.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

@Repository
@RequiredArgsConstructor
public class GoldenTimeRepository {
    private final EntityManager em;

}
