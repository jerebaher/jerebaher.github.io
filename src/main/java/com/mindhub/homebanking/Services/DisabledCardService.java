package com.mindhub.homebanking.Services;

import com.mindhub.homebanking.models.DisabledCard;

import java.util.List;
import java.util.Set;

public interface DisabledCardService {
    List<DisabledCard> findAllDisabledCards();

    DisabledCard findDisabledCardById(Long id);

    void saveDisabledCard(DisabledCard disabledCard);

    void deleteDisabledCard(DisabledCard disabledCard);
}
