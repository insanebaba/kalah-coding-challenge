package com.backbase.kalahcodingchallenge.repository;

import com.backbase.kalahcodingchallenge.model.GameModel;
import org.springframework.data.repository.CrudRepository;

public interface GameCRUDRepository extends CrudRepository<GameModel, Integer> {
}
