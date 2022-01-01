package com.calculator.incometax.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.calculator.incometax.model.Donation;

public interface DonationRepository extends JpaRepository<Donation, Long> {

}