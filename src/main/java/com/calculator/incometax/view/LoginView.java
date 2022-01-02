package com.calculator.incometax.view;

import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.login.LoginI18n;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="")
@PageTitle("Login")
public class LoginView extends VerticalLayout implements BeforeEnterObserver {

    /**
	 * 
	 */
	private static final long serialVersionUID = 3854637105490243881L;
	
	private final LoginForm loginForm = new LoginForm(); 

	public LoginView() {
		addClassName("login-view");
		setSizeFull(); 
		setAlignItems(Alignment.CENTER);
		setJustifyContentMode(JustifyContentMode.CENTER);
		
        getStyle()
            .set("background-color", "var(--lumo-contrast-5pct)");
//            .set("display", "flex")
//            .set("justify-content", "center")
//            .set("padding", "var(--lumo-space-l)")
//            .set("height", "100%");
        
        LoginI18n i18n = LoginI18n.createDefault();

		i18n.setAdditionalInformation("Please contact admin if you're experiencing issues logging into your account");
        
		loginForm.setI18n(i18n);
		loginForm.setAction("login");
		loginForm.setForgotPasswordButtonVisible(false);
		
        add(loginForm);
    }
	
	@Override
	public void beforeEnter(BeforeEnterEvent beforeEnterEvent) {
		// inform the user about an authentication error
		if(beforeEnterEvent.getLocation()  
        .getQueryParameters()
        .getParameters()
        .containsKey("error")) {
			loginForm.setError(true);
        }
	}
}