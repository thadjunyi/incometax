package com.calculator.incometax.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Donation {
	
	@Id
	@GeneratedValue
	private Long id;

	private String rate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	protected Donation() {
		super();
	}

	public Donation(String rate) {
		super();
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "Donation [id=" + getId() + ", rate=" + rate + "]";
	}
}
