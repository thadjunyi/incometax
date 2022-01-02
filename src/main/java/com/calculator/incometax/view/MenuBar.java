package com.calculator.incometax.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.calculator.incometax.security.SecurityService;
import com.calculator.incometax.utils.ComponentUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.applayout.DrawerToggle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.ParentLayout;
import com.vaadin.flow.router.RouterLink;

@ParentLayout(MainView.class)
public class MenuBar extends AppLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5374780662715594766L;
	
	@Autowired
	SecurityService securityService;
	
	public MenuBar() {
		createHeader();
        createDrawer();
        
	    setPrimarySection(Section.DRAWER);
    }
	
	private void createHeader() {
		H1 title = new H1("Income Tax Calculator");
		title.addClassNames("text-l", "m-m");
		title.getStyle()
		.set("font-size", "var(--lumo-font-size-l)")
		.set("margin", "0");

		Button logoutButton = ComponentUtils.createButton("Log out", null, e -> securityService.logout());
		logoutButton.setWidth("100px");
		
        HorizontalLayout header = new HorizontalLayout(
          new DrawerToggle(),
          title,
          logoutButton
        );
        
        header.expand(title);

        header.setDefaultVerticalComponentAlignment(FlexComponent.Alignment.CENTER); 
        header.setWidth("100%");
        header.addClassNames("py-0", "px-m");

        addToNavbar(header);
    }
	
	private void createDrawer() {
		Tabs tabs = new Tabs();
		
		tabs.add(
		      createTab(VaadinIcon.DASHBOARD, "Dashboard", DashboardView.class),
		      createTab(VaadinIcon.CALC, "Calculator", CalculatorView.class),
		      createTab(VaadinIcon.LINK, "Reference Link", LinkView.class)
	    );
	    tabs.setOrientation(Tabs.Orientation.VERTICAL);
	
	    addToDrawer(tabs);
	}
	
	private Tab createTab(VaadinIcon viewIcon, String viewName, Class<? extends Component> route) {
	    Icon icon = viewIcon.create();
	    icon.getStyle()
	      .set("box-sizing", "border-box")
	      .set("margin-inline-end", "var(--lumo-space-m)")
	      .set("margin-inline-start", "var(--lumo-space-xs)")
	      .set("padding", "var(--lumo-space-xs)");

	    RouterLink link = new RouterLink();
	    link.add(icon, new Span(viewName));
	    link.setRoute(route);
	    link.setTabIndex(-1);

	    return new Tab(link);
	  }
}
