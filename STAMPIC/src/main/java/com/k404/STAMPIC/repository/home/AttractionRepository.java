package com.k404.STAMPIC.repository.home;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.k404.STAMPIC.domain.home.Attraction;

@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
	
	 public List<Attraction> findAll();
}
