package com.calculator.incometax.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class SRS {
	
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

	protected SRS() {
		super();
	}

	public SRS(String rate) {
		super();
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "SRS [id=" + getId() + ", rate=" + rate + "]";
	}
}
