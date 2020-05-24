package com.backbase.kalahcodingchallenge.repository;

import com.backbase.kalahcodingchallenge.models.dao.GameDAO;
import org.springframework.data.repository.CrudRepository;

public interface GameCRUDRepository extends CrudRepository<GameDAO, Integer> {
}
