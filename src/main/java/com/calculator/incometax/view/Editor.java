package com.calculator.incometax.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.calculator.incometax.model.Tax;
import com.calculator.incometax.repository.TaxRepository;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyNotifier;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

@SpringComponent
@UIScope
public class Editor extends VerticalLayout implements KeyNotifier {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8887368010269254193L;

	private final TaxRepository repository;

	/**
	 * The currently edited customer
	 */
	private Tax tax;

	/* Fields to edit properties in Tax entity */
	TextField from = new TextField("From");
	TextField to = new TextField("To");
	TextField rate = new TextField("Rate");

	/* Action buttons */
	// TODO why more code?
	Button save = new Button("Save", VaadinIcon.CHECK.create());
	Button cancel = new Button("Cancel");
	Button delete = new Button("Delete", VaadinIcon.TRASH.create());
	HorizontalLayout actions = new HorizontalLayout(save, cancel, delete);

	Binder<Tax> binder = new Binder<>(Tax.class);
	private ChangeHandler changeHandler;

	@Autowired
	public Editor(TaxRepository repository) {
		this.repository = repository;

		add(from, to, rate, actions);

		// bind using naming convention
		binder.bindInstanceFields(this);

		// Configure and style components
		setSpacing(true);

		save.getElement().getThemeList().add("primary");
		delete.getElement().getThemeList().add("error");

		addKeyPressListener(Key.ENTER, e -> save());

		// wire action buttons to save, delete and reset
		save.addClickListener(e -> save());
		delete.addClickListener(e -> delete());
		cancel.addClickListener(e -> editTax(tax));
		setVisible(false);
	}

	void delete() {
		repository.delete(tax);
		changeHandler.onChange();
	}

	void save() {
		repository.save(tax);
		changeHandler.onChange();
	}

	public interface ChangeHandler {
		void onChange();
	}

	public final void editTax(Tax t) {
		if (t == null) {
			setVisible(false);
			return;
		}
		final boolean persisted = t.getId() != null;
		if (persisted) {
			// Find fresh entity for editing
			tax = repository.findById(t.getId()).get();
		}
		else {
			tax = t;
		}
		cancel.setVisible(persisted);

		// Bind tax properties to similarly named fields
		// Could also use annotation or "manual binding" or programmatically
		// moving values from fields to entities before saving
		binder.setBean(tax);

		setVisible(true);

		// Focus rate initially
		rate.focus();
	}

	public void setChangeHandler(ChangeHandler h) {
		// ChangeHandler is notified when either save or delete
		// is clicked
		changeHandler = h;
	}

}