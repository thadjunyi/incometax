package com.calculator.incometax.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Tax {
	
	@Id
	@GeneratedValue
	private Long id;

	@Column(name="fromValue")
	private String from;

	@Column(name="toValue")
	private String to;

	private String rate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	protected Tax() {
		super();
	}

	public Tax(String from, String to, String rate) {
		super();
		this.from = from;
		this.to = to;
		this.rate = rate;
	}

	@Override
	public String toString() {
		return "Tax [id=" + getId() + ", from=" + from + ", to=" + to + ", rate=" + rate + "]";
	}
}