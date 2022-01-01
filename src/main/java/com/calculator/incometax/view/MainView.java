package com.calculator.incometax.view;

import java.text.DecimalFormat;

import com.calculator.incometax.model.Donation;
import com.calculator.incometax.model.SRS;
import com.calculator.incometax.model.Tax;
import com.calculator.incometax.repository.DonationRepository;
import com.calculator.incometax.repository.SRSRepository;
import com.calculator.incometax.repository.TaxRepository;
import com.calculator.incometax.utils.CalculationUtils;
import com.calculator.incometax.utils.ComponentUtils;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value="calculator", layout = MenuBar.class)
@PageTitle("Calculator")
public class MainView<T> extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5386437084703158053L;
	
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	private TaxRepository taxRepo; 
	private DonationRepository donationRepo;
	private SRSRepository srsRepo;

	private Double taxableIncome;
	private TextField totalIncomeTextField;
	private HorizontalLayout contributionLayout;
	private CheckboxGroup<String> taxReliefLayout;
	private TextField taxableIncomeTextField;
	private HorizontalLayout taxCalculationLayout;
	private TextField totalTaxTextField;
	private TextField totalTaxRateTextField;
	private HorizontalLayout savedTaxLayout;
	private HorizontalLayout savedTaxRateLayout;
	private HorizontalLayout deleteButtonLayout;

	public MainView(TaxRepository taxRepo, DonationRepository donationRepo, SRSRepository srsRepo) {
		this.taxRepo = taxRepo;
		this.donationRepo = donationRepo;
		this.srsRepo = srsRepo;

		this.taxableIncome = 0.00;
		
		createTotalIncomeTextField();
		createContributionLayout();
		createTaxReliefCheckbox();
		createTaxableIncomeTextField();
		createTaxCalculationLayout();
		createTotalTaxTextField();
		createTotalTaxRateTextField();
		createSaveValueButton();
		createSavedTaxLayout();
		createSavedTaxRateLayout();
		createDeleteButtonLayout();
	}
	
	private void createTotalIncomeTextField() {
		totalIncomeTextField = ComponentUtils.createTextField("Total Income ($)", "0.00", "", false, e -> calculateTax(), true);
		add(totalIncomeTextField);
	}
	
	private void createContributionLayout() {
		HorizontalLayout layout = new HorizontalLayout();
		layout.add(ComponentUtils.createTextField("CPF Contribution", "0.00", "", true, e -> e.getValue(), false));
		String[] labels = new String[]{ "SRS Contribution (1 : 1)", "Donation (1 : 2.5)" };
		for (int i=0; i<labels.length; i++) {
			String label = labels[i] + " ($)";
			
			TextField textField = ComponentUtils.createTextField(label, "0.00", "", false, e -> calculateTax(), true);
			layout.add(textField);
		}
		contributionLayout = layout;
		add(contributionLayout);
	}
	
	private void createTaxReliefCheckbox() {
		String[] items = new String[] { 
			"Earned Income (Below 55) - $1000",
			"Earned Income (55 to 59) - $6000",
			"Earned Income (60 and above) - $8000",
			"NSman Self Relief (No NS activity, Non-Appointment Holder) - $1500",
			"NSman Self Relief (No NS activity, Appointment Holder) - $3500",
			"NSman Self Relief (Have NS activity, Non-Appointment Holder) - $3000",
			"NSman Self Relief (Have NS activity, Appointment Holder) - $5000",
			"Parent Relief (Same household) (1) - $9000",
			"Parent Relief (Same household) (2) - $9000",
			"Parent Relief (Different household) (1) - $5500",
			"Parent Relief (Different household) (2) - $5500",
			"Grandparent Caregiver Relief - $3000",
			"Child Relief (1) - $4000",
			"Child Relief (2) - $4000",
			"Working Mother's Child Relief (WMCR) - $0",
			"Foreign Domestic Worker Levy (FDWL) Relief - $0",
			"CPF Cash Top-up Relief - $7000",
			"Course Fees Relief - $0",
			"Compulsory and Voluntary Medisave Contributions - $0"
		};
		Integer[] selectedIndex = new Integer[] { 0 };
		taxReliefLayout = ComponentUtils.createCheckboxGroup("Tax Relief", items, selectedIndex, e -> calculateTax());
		add(taxReliefLayout);
	}
	
	private void createTaxableIncomeTextField() {
		taxableIncomeTextField = ComponentUtils.createTextField("Taxable Income ($)", df.format(taxableIncome), "", true, e -> e.getValue(), false);
		add(taxableIncomeTextField);
	}
	
	private void createTaxCalculationLayout() {
		HorizontalLayout layout = new HorizontalLayout();
		for (Tax tax : taxRepo.findAll()) {
			String label = "$" + tax.getFrom() + " to $" + tax.getTo() + " (" + tax.getRate() + "%)";
			
			TextField textField = ComponentUtils.createTextField(label, "0.00", "", true, e -> e.getValue(), false);
			layout.add(textField);
		}
		taxCalculationLayout = layout;
		add(taxCalculationLayout);
	}
	
	private void createTotalTaxTextField() {
		totalTaxTextField = ComponentUtils.createTextField("Total Tax ($)", "0.00", "", true, e -> e.getValue(), false);
		add(totalTaxTextField);
	}
	
	private void createTotalTaxRateTextField() {
		totalTaxRateTextField = ComponentUtils.createTextField("Total Tax Rate (%)", "0.00", "", true, e -> e.getValue(), false);
		add(totalTaxRateTextField);
	}

	private void createSaveValueButton() {
		Button addNewBtn = ComponentUtils.createButton("Save Value", VaadinIcon.PLUS.create(), e -> saveValue());
		add(addNewBtn);
	}
	
	private void createSavedTaxLayout() {
		savedTaxLayout = new HorizontalLayout();
		add(savedTaxLayout);
	}
	
	private void createSavedTaxRateLayout() {
		savedTaxRateLayout = new HorizontalLayout();
		add(savedTaxRateLayout);
	}
	
	private void createDeleteButtonLayout() {
		deleteButtonLayout = new HorizontalLayout();
		add(deleteButtonLayout);
	}
	
	private void calculateTax() {
		
		calculateTaxableIncome();
		double totalTax = 0.00; 
		for (int index=0; index<taxCalculationLayout.getComponentCount(); index++) {
			Component component = taxCalculationLayout.getComponentAt(index);
			TextField textField = (TextField) component;
			String label = textField.getLabel();

			double taxAmount = getTaxAmount(label);
			totalTax += taxAmount;
			textField.setValue(String.valueOf(taxAmount));
		}
		
		totalTaxTextField.setValue(df.format(totalTax));
		totalTaxRateTextField.setValue(df.format(CalculationUtils.calculateTaxRate(taxableIncome, totalTax)));
	}
	
	private void calculateTaxableIncome() {
		try {
			double totalIncome = Double.valueOf(totalIncomeTextField.getValue());
			double cpfContribution = totalIncome * 0.2; 
			double srsContribution = Double.valueOf(((TextField)contributionLayout.getComponentAt(1)).getValue());
			double donationAmount = Double.valueOf(((TextField)contributionLayout.getComponentAt(2)).getValue());
			
			SRS srs = srsRepo.findAll().get(0);
			Donation donation = donationRepo.findAll().get(0);
			
			((TextField)contributionLayout.getComponentAt(0)).setValue(df.format(cpfContribution));
			
			taxableIncome = totalIncome - cpfContribution - 
					CalculationUtils.calculateDeduction(srsContribution, srs.getRate()) - 
					CalculationUtils.calculateDeduction(donationAmount, donation.getRate());

			Object[] selectedItems = taxReliefLayout.getSelectedItems().toArray();
			for (int i=0; i<selectedItems.length; i++) {
				String[] splitValue = String.valueOf(selectedItems[i]).split(" - \\$");
				taxableIncome -= Double.valueOf(splitValue[1]);
			}
			
		} catch (Exception e) {
			System.out.println("Exception occur in input value: " + e);
			taxableIncome = 0.00;
		}
		taxableIncomeTextField.setValue(df.format(taxableIncome));
	}
	
	private double getTaxAmount(String label) {
		
		String[] labelSplit = label.split(" ");
		
		Tax tax = taxRepo.findRateByFromAndTo(labelSplit[0].replace("$", ""), labelSplit[2].replace("$", ""));
		return CalculationUtils.getTaxAmount(taxableIncome, tax);
	}
	
	private void saveValue() {
		int index = savedTaxLayout.getComponentCount();

		savedTaxLayout.add(ComponentUtils.createTextField("Tax", totalTaxTextField.getValue(), "", true, e -> e.getValue(), false));
		savedTaxRateLayout.add(ComponentUtils.createTextField("Tax Rate", totalTaxRateTextField.getValue(), "", true, e -> e.getValue(), false));
		deleteButtonLayout.add(ComponentUtils.createButton("", VaadinIcon.CLOSE_SMALL.create(), e -> deleteSavedValue(index)));
	}
	
	private void deleteSavedValue(Integer index) {
		savedTaxLayout.remove(savedTaxLayout.getComponentAt(index));
		savedTaxRateLayout.remove(savedTaxRateLayout.getComponentAt(index));
		deleteButtonLayout.remove(deleteButtonLayout.getComponentAt(index));
	}
}