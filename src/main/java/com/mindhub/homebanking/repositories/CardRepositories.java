package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface CardRepositories extends JpaRepository<Card, Long> {
    Card findByNumberCard(String number);
}
