package cc.zafar.convertme;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {

	private Spinner unitTypeSpinner;
	private EditText amountTextView;

	private TextView
		teaspoonTextView, tablespoonTextView, cupTextView, ounceTextView,
		pintTextView, quartTextView, gallonTextView, poundTextView,
		milliliterTextView, literTextView, milligramTextView, kilogramTextView;

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addItemsToUnitTypeSpinner();

		addListenerToUnitTypeSpinner();

		amountTextView = (EditText)findViewById(R.id.amount_text_view);

		initializeTextViews();
	}

	private void addListenerToUnitTypeSpinner () {
		unitTypeSpinner = (Spinner) findViewById(R.id.unit_type_spinner);
		unitTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected (AdapterView<?> parent, View view, int position, long id) {
				String itemSelectedInSpinner = parent.getItemAtPosition(position).toString();

				// checkIfConvertingFromTsp(itemSelectedInSpinner);
				updateValues(itemSelectedInSpinner);
			}

			@Override
			public void onNothingSelected (AdapterView<?> parent) {
				// TODO: Add something here later! :)
			}
		});
	}

	private Quantity.Unit SpinnerToUnit (String currentUnit) {
		switch (currentUnit) {
			case "teaspoon": return Quantity.Unit.tsp;
			case "tablespoon": return Quantity.Unit.tbs;
			case "cup": return Quantity.Unit.cup;
			case "ounce": return Quantity.Unit.oz;
			case "pint": return Quantity.Unit.pint;
			case "quart": return Quantity.Unit.quart;
			case "gallon": return Quantity.Unit.gallon;
			case "pound": return Quantity.Unit.pound;
			case "milliliter": return Quantity.Unit.ml;
			case "liter": return Quantity.Unit.liter;
			case "milligram": return Quantity.Unit.mg;
			default: return Quantity.Unit.kg;
		}
	}

	private TextView SpinnerToTextView(String currentUnit) {
		switch (currentUnit) {
			case "teaspoon": return teaspoonTextView;
			case "tablespoon": return tablespoonTextView;
			case "cup": return cupTextView;
			case "ounce": return ounceTextView;
			case "pint": return pintTextView;
			case "quart": return quartTextView;
			case "gallon": return gallonTextView;
			case "pound": return poundTextView;
			case "milliliter": return milliliterTextView;
			case "liter": return literTextView;
			case "milligram": return milligramTextView;
			default: return kilogramTextView;
		}
	}

	public void updateValues(String selection /*What units are used as base*/) {
		// Amount to be converted:
		double doubleToConvert = Double.parseDouble(amountTextView.getText().toString());
		TextView currentTextView = SpinnerToTextView(selection);
		Quantity.Unit currentUnit = SpinnerToUnit(selection);
		Quantity currentQuantity = new Quantity(doubleToConvert, currentUnit);
		double teaspoonsToConvert;


		// Convert the current value to teaspoons:
		if (!selection.equals("teaspoon")) {
			teaspoonsToConvert = currentUnit.toBaseUnit(doubleToConvert);
		} else {
			teaspoonsToConvert = doubleToConvert;
		}

		// Update all fields:
		updateUnitFieldsUsingTsp(teaspoonsToConvert, Quantity.Unit.tsp, teaspoonTextView);
		updateUnitFieldsUsingTsp(teaspoonsToConvert, Quantity.Unit.tbs, tablespoonTextView);
		updateUnitFieldsUsingTsp(teaspoonsToConvert, Quantity.Unit.cup, cupTextView);
		updateUnitFieldsUsingTsp(teaspoonsToConvert, Quantity.Unit.oz, ounceTextView);
		updateUnitFieldsUsingTsp(teaspoonsToConvert, Quantity.Unit.pint, pintTextView);
		updateUnitFieldsUsingTsp(teaspoonsToConvert, Quantity.Unit.quart, quartTextView);
		updateUnitFieldsUsingTsp(teaspoonsToConvert, Quantity.Unit.gallon, gallonTextView);
		updateUnitFieldsUsingTsp(teaspoonsToConvert, Quantity.Unit.pound, poundTextView);
		updateUnitFieldsUsingTsp(teaspoonsToConvert, Quantity.Unit.ml, milliliterTextView);
		updateUnitFieldsUsingTsp(teaspoonsToConvert, Quantity.Unit.liter, literTextView);
		updateUnitFieldsUsingTsp(teaspoonsToConvert, Quantity.Unit.mg, milligramTextView);
		updateUnitFieldsUsingTsp(teaspoonsToConvert, Quantity.Unit.kg, kilogramTextView);
		// Return the base field to original:
		currentTextView.setText(currentQuantity.toString());
	}

	public void updateUnitFieldsUsingTsp (double numberOfTeaspoons,
	                                     Quantity.Unit unitConvertingTo, TextView theTextView) {
		Quantity unitQuantity = new Quantity(numberOfTeaspoons, Quantity.Unit.tsp);
		String tempUnit = unitQuantity.to(unitConvertingTo).toString();
		theTextView.setText(tempUnit);

	}

	/*
	private void checkIfConvertingFromTsp (String currentUnit) {
		double doubleToConvert = Double.parseDouble(amountTextView.getText().toString());

		if (currentUnit.equals("teaspoon")) {
			updateUnitTypeUsingTsp(Quantity.Unit.tsp, doubleToConvert);
		} else {
			updateUnitTypesUsingOther(SpinnerToUnit(currentUnit));

		}

	}

	public void updateUnitTypesUsingOther (Quantity.Unit currentUnit) {
		double doubleToConvert = Double.parseDouble(amountTextView.getText().toString());
		Quantity currentQuantitySelected = new Quantity(doubleToConvert, currentUnit);
		String valueInTeaspoons = currentQuantitySelected.to(Quantity.Unit.tsp).toString();
	}

	public void updateUnitTypeUsingTsp (Quantity.Unit currentUnit, double doubleToConvert) {
		// double doubleToConvert = Double.parseDouble(amountTextView.getText().toString());
		String teaspoonValueAndUnit = doubleToConvert + " tsp";
		teaspoonTextView.setText(teaspoonValueAndUnit);
		updateUnitTextFieldsUsingTsp(doubleToConvert, Quantity.Unit.tbs, tablespoonTextView);
		updateUnitTextFieldsUsingTsp(doubleToConvert, Quantity.Unit.cup, cupTextView);
		updateUnitTextFieldsUsingTsp(doubleToConvert, Quantity.Unit.oz, ounceTextView);
		updateUnitTextFieldsUsingTsp(doubleToConvert, Quantity.Unit.pint, pintTextView);
		updateUnitTextFieldsUsingTsp(doubleToConvert, Quantity.Unit.quart, quartTextView);
		updateUnitTextFieldsUsingTsp(doubleToConvert, Quantity.Unit.gallon, gallonTextView);
		updateUnitTextFieldsUsingTsp(doubleToConvert, Quantity.Unit.pound, poundTextView);
		updateUnitTextFieldsUsingTsp(doubleToConvert, Quantity.Unit.ml, milliliterTextView);
		updateUnitTextFieldsUsingTsp(doubleToConvert, Quantity.Unit.liter, literTextView);
		updateUnitTextFieldsUsingTsp(doubleToConvert, Quantity.Unit.mg, milligramTextView);
		updateUnitTextFieldsUsingTsp(doubleToConvert, Quantity.Unit.kg, kilogramTextView);
	}

	public void updateUnitTextFieldsUsingTsp (double doubleToConvert,
	                                          Quantity.Unit unitConvertingTo,
	                                          TextView theTextView) {
		Quantity unitQuantity = new Quantity(doubleToConvert, Quantity.Unit.tsp);
		String tempUnit = unitQuantity.to(unitConvertingTo).toString();
		theTextView.setText(tempUnit);

	}
	*/


	private void addItemsToUnitTypeSpinner () {
		unitTypeSpinner = (Spinner) findViewById(R.id.unit_type_spinner);
		ArrayAdapter<CharSequence> unitTypeSpinnerAdapter
			= ArrayAdapter.createFromResource(this,
			                                  R.array.conversion_types,
			                                  android.R.layout.simple_spinner_item);

		unitTypeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		unitTypeSpinner.setAdapter(unitTypeSpinnerAdapter);
	}

	private void initializeTextViews () {
		teaspoonTextView = (TextView) findViewById(R.id.tsp_text_view);
		tablespoonTextView = (TextView) findViewById(R.id.tbs_text_view);
		cupTextView = (TextView) findViewById(R.id.cup_text_view);
		ounceTextView = (TextView) findViewById(R.id.oz_text_view);
		pintTextView = (TextView) findViewById(R.id.pint_text_view);
		quartTextView = (TextView) findViewById(R.id.quart_text_view);
		gallonTextView = (TextView) findViewById(R.id.gallon_text_view);
		poundTextView = (TextView) findViewById(R.id.pound_text_view);
		milliliterTextView = (TextView) findViewById(R.id.ml_text_view);
		literTextView = (TextView) findViewById(R.id.liter_text_view);
		milligramTextView = (TextView) findViewById(R.id.mg_text_view);
		kilogramTextView = (TextView) findViewById(R.id.kg_text_view);
	}


	@Override
	public boolean onCreateOptionsMenu (Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected (MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}
}
