package com.example.eksamensprojektprojektmanager.repository;

import com.example.eksamensprojektprojektmanager.model.Experience;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class ExperienceRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public void save(Experience experience) {
        entityManager.persist(experience);
    }

    public List<Experience> findByUserId(Long userId) {
        String query = "SELECT e FROM Experience e WHERE e.userId = :userId";
        return entityManager.createQuery(query, Experience.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Transactional
    public void delete(Long experienceId) {
        Experience experience = entityManager.find(Experience.class, experienceId);
        if (experience != null) {
            entityManager.remove(experience);
        }
    }

}