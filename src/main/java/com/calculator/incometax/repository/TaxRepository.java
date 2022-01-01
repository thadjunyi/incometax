package com.calculator.incometax.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.calculator.incometax.model.Tax;

public interface TaxRepository extends JpaRepository<Tax, Long> {

	@Query("SELECT t FROM Tax t WHERE t.from = ?1 AND t.to = ?2 ")
	Tax findRateByFromAndTo(String from, String To);
}