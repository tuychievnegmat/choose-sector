package com.example.choosesector.choosesector.repository;

import com.example.choosesector.choosesector.entity.SectorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SectorRepository extends CrudRepository<SectorEntity, Long> {
    List<SectorEntity> findByGroup(String group);
    SectorEntity findBySector(String sector);
}
