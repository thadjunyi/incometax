package com.calculator.incometax.view;

import com.vaadin.flow.component.details.Details;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="link", layout = MenuBar.class)
@PageTitle("Link")
public class LinkView<T> extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5386437084703158053L;

	public LinkView() {
		createReferenceDetails();
	}
	
	public void createReferenceDetails() {
		Span reliefLink = new Span("https://www.iras.gov.sg/taxes/individual-income-tax/employees/deductions-for-individuals/personal-reliefs-and-tax-rebates/relief-checker");
		Span vaadinLink = new Span("https://vaadin.com/docs/latest/ds/components");

		VerticalLayout content = new VerticalLayout(reliefLink, vaadinLink);
		content.setSpacing(false);
		content.setPadding(false);

		Details details = new Details("Reference Links", content);
		details.setOpened(true);

		add(details);
	}
}