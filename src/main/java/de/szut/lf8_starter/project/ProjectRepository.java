package de.szut.lf8_starter.project;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<ProjectEntity, Integer> {
    List<ProjectEntity> findByEmployeeIdsContains(Integer employeeId);
    @Query("SELECT p FROM ProjectEntity p WHERE :employeeId MEMBER OF p.employeeIds")
    List<ProjectEntity> findProjectsByEmployeeId(Integer employeeId);
}
