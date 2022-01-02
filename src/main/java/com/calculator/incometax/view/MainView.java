package com.calculator.incometax.view;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.RoutePrefix;
import com.vaadin.flow.router.RouterLayout;

//@RoutePrefix("v1/incometax/vaadin")
public class MainView extends Div implements RouterLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2108242592934004748L;

	public MainView() {
		getStyle()
        	.set("height", "100%");
	}
}
