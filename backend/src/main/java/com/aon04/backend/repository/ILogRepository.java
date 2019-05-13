package com.aon04.backend.repository;

import com.aon04.backend.models.Log;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface ILogRepository extends CrudRepository<Log, Integer> {
}
