package com.aon04.backend.repository;

import com.aon04.backend.models.Exam;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IExamRepository extends CrudRepository<Exam, Integer> {
}
