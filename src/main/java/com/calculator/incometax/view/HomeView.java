package com.calculator.incometax.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.notification.Notification.Position;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="dashboard", layout = MenuBar.class)
//@RouteAlias(value="", layout = MenuBar.class)
@PageTitle("Dashboard")
public class HomeView extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5386437084703158053L;

	public HomeView() {
		createText();
		createNotification();
	}
	
	public void createText() {
		H1 text = new H1("Hello");
		text.addClassNames("text-l", "m-m");
		
		setDefaultHorizontalComponentAlignment(Alignment.CENTER);
		
		add(text);
	}
	
	public void createNotification() {
		Notification notification = new Notification();
		notification.setText("Welcome!");
		notification.setDuration(5000);
		notification.setPosition(Position.TOP_CENTER);
		notification.setOpened(true);
		
		add(notification);
	}
}