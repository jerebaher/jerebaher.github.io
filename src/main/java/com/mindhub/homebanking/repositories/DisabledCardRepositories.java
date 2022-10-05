package com.mindhub.homebanking.repositories;

import com.mindhub.homebanking.models.DisabledCard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DisabledCardRepositories extends JpaRepository<DisabledCard, Long> {
}
