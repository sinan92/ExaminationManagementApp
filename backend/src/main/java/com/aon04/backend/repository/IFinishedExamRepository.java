package com.aon04.backend.repository;

import com.aon04.backend.models.FinishedExam;
import org.springframework.data.repository.CrudRepository;

public interface IFinishedExamRepository extends CrudRepository<FinishedExam, Integer> {
}