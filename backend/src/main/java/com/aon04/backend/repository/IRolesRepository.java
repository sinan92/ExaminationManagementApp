package com.aon04.backend.repository;

import com.aon04.backend.models.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

public interface IRolesRepository extends CrudRepository<Role, Integer> {
}