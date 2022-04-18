package com.example.choosesector.choosesector.repository;

import com.example.choosesector.choosesector.entity.PersonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
   PersonEntity findByUsername(String username);
}
