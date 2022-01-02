package com.calculator.incometax.view;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.Route;

//@Route(value="blank", layout = MenuBar.class)
public class BlankView extends VerticalLayout implements BeforeEnterObserver {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1653748720690269720L;

	@Override
    public void beforeEnter(BeforeEnterEvent event) {
//        event.rerouteTo(DashboardView.class);
    }
}