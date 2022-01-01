package com.calculator.incometax.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.AbstractField.ComponentValueChangeEvent;
import com.vaadin.flow.component.HasValue.ValueChangeListener;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class ComponentUtils {
	
	public static TextField createTextField(String label, String value, String placeholder, boolean readOnly, ValueChangeListener<? super ComponentValueChangeEvent<TextField, String>> listener, boolean clearButtonVisible) {
		TextField textField = new TextField();
		textField.setLabel(label);
		
		if (StringUtils.isNotBlank(value)) {
			textField.setValue(value);
		} else {
			textField.setPlaceholder(placeholder);
		}
		textField.setReadOnly(readOnly);
		
		if (!readOnly) {
			textField.focus();
			textField.setRequired(true);
			textField.setPattern("^[0-9]*[-s.]?[0-9]{0,2}$");
			textField.setHelperText("Format: 123456.99");
			textField.setErrorMessage("Please follow the format");
		}

		textField.setValueChangeMode(ValueChangeMode.EAGER);
		textField.addValueChangeListener(listener);
		textField.setClearButtonVisible(clearButtonVisible);
		return textField;
	}
	
	public static Button createButton(String label, Icon icon, ComponentEventListener<ClickEvent<Button>> listener) {
		Button button = new Button();
		button.setText(label);
		button.setIcon(icon);
		button.addClickListener(listener);
		button.setWidth("192px");
		return button;
	}
	
	public static CheckboxGroup<String> createCheckboxGroup(String label, String[] items, Integer[] selectedIndex, ValueChangeListener<? super ComponentValueChangeEvent<CheckboxGroup<String>,Set<String>>> listener) {
		CheckboxGroup<String> checkboxGroup = new CheckboxGroup<>();
		checkboxGroup.setLabel(label);
		checkboxGroup.setItems(items);
		
		List<String> selectedItems = new ArrayList<String>();
		for (int i=0; i<selectedIndex.length; i++) {
			selectedItems.add(items[i]);
		}
		checkboxGroup.select(selectedItems);
		checkboxGroup.addValueChangeListener(listener);
		return(checkboxGroup);
	}
}
