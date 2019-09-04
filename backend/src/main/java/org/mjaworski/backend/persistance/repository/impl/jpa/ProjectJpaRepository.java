package org.mjaworski.backend.persistance.repository.impl.jpa;

import org.mjaworski.backend.persistance.entity.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectJpaRepository extends JpaRepository<Project, Integer> {
    @Query("SELECT p FROM Project p JOIN p.owner o WHERE LOWER(o.username) = LOWER(:username)")
    List<Project> get(@Param("username") String username, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Project p JOIN p.owner o WHERE LOWER(o.username) = LOWER(:username)")
    int getCount(String username);
}
