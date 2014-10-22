package derivatives.financial.europeanoptionblackscholes;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.app.Activity;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;

import derivatives.financial.europeanoptionblackscholes.Date;
import derivatives.financial.europeanoptionblackscholes.BlackScholesModel;
import derivatives.financial.europeanoptionblackscholes.DatePickerMaxMin;
import derivatives.financial.europeanoptionblackscholes.EuropeanCall;
import derivatives.financial.europeanoptionblackscholes.EuropeanOption;
import derivatives.financial.europeanoptionblackscholes.EuropeanPut;
import android.widget.TextView;

// import com.google.android.gms.ads.*;


import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.Calendar;

public class MainActivity extends Activity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    EditText spotLevel;
    EditText strike;
    EditText rate;
    EditText dividend;
    EditText maturity;
    EditText volatility;
    AlertDialog Error;
    Spinner european_type_spinner;
    String selected_type;
    Button evaluation_date_set;
    TextView evaluation_date;
    Button maturity_date_set;
    TextView maturity_date;
    DatePickerMaxMin evaluation_date_picker;
    DatePickerMaxMin maturity_date_picker;
    Date myEvaluationDate;
    Date myMaturityDate;
    Date minMaturityDate;
    Date maxEvalDate;

    final Calendar c = Calendar.getInstance();
    final Calendar initial_maturity = Calendar.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Buscar AdView como recurso y cargar una solicitud.
        AdView adView = (AdView)this.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);
        adView.setFocusableInTouchMode(true);
        adView.requestFocus();

        spotLevel = (EditText) findViewById(R.id.spot_level);
        strike = (EditText) findViewById(R.id.strike);
        rate = (EditText) findViewById(R.id.rate);
        dividend = (EditText) findViewById(R.id.dividend);
        maturity = (EditText) findViewById(R.id.maturity);
        volatility = (EditText) findViewById(R.id.volatility);
        european_type_spinner = (Spinner) findViewById(R.id.type);


        ArrayAdapter<CharSequence> european_type_adapter = ArrayAdapter.createFromResource(this, R.array.european_types,
                android.R.layout.simple_spinner_item);
        european_type_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        european_type_spinner.setAdapter(european_type_adapter);

        View __price = findViewById(R.id.priceButton);
        __price.setOnClickListener(this);
        //__price.setFocusableInTouchMode(true);
        //__price.requestFocus();

        View __greeks = findViewById(R.id.greeksButton);
        __greeks.setOnClickListener(this);

        european_type_spinner.setOnItemSelectedListener(this);

        AlertDialog.Builder builderOk = new AlertDialog.Builder(this);
        builderOk.setMessage("Data Type Error")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        Error = builderOk.create();

        evaluation_date_set = (Button) findViewById(R.id.evaluation_date_set);
        evaluation_date = (TextView) findViewById(R.id.evaluation_date);
        maturity_date_set = (Button) findViewById(R.id.maturity_date_set);
        maturity_date = (TextView) findViewById(R.id.maturity_date);


        evaluation_date.setText(new StringBuilder().append(c.get(Calendar.DAY_OF_MONTH)).append("/")
                .append(c.get(Calendar.MONTH) + 1).append("/")
                .append(c.get(Calendar.YEAR)));

        myEvaluationDate = new Date(new String(Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + "/" +
                Integer.toString(c.get(Calendar.MONTH)+1) + "/" +
                Integer.toString(c.get(Calendar.YEAR))));

        initial_maturity.add(Calendar.MONTH, 3);

        maturity_date.setText(new StringBuilder().append(initial_maturity.get(Calendar.DAY_OF_MONTH)).append("/")
                .append(initial_maturity.get(Calendar.MONTH) + 1).append("/")
                .append(initial_maturity.get(Calendar.YEAR)));

        myMaturityDate = new Date(new String(Integer.toString(initial_maturity.get(Calendar.DAY_OF_MONTH)) + "/" +
                Integer.toString(initial_maturity.get(Calendar.MONTH)+1) + "/" +
                Integer.toString(initial_maturity.get(Calendar.YEAR))));

        minMaturityDate = new Date(new String(Integer.toString(c.get(Calendar.DAY_OF_MONTH)) + "/" +
                Integer.toString(c.get(Calendar.MONTH)+1) + "/" +
                Integer.toString(c.get(Calendar.YEAR))));

        maxEvalDate = new Date(new String(Integer.toString(31) + "/" +
                Integer.toString(12) + "/" +
                Integer.toString(2075)));

        maturity.setText(new StringBuilder().append(myMaturityDate.getValueInYears()-
                myEvaluationDate.getValueInYears()));

        evaluation_date_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.evaluation_date_picker = new DatePickerMaxMin(
                        MainActivity.this, MainActivity.this.pickerListenerEvalDate,
                        MainActivity.this.myEvaluationDate.getYear(),
                        MainActivity.this.myEvaluationDate.getMonth()-1,
                        MainActivity.this.myEvaluationDate.getDay(),
                        maxEvalDate.getYear(),
                        maxEvalDate.getMonth()-1,
                        maxEvalDate.getDay());
                MainActivity.this.evaluation_date_picker.show();
            }
        });

        maturity_date_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MainActivity.this.maturity_date_picker = new DatePickerMaxMin(
                        MainActivity.this, MainActivity.this.pickerListenerMaturityDate,
                        MainActivity.this.myMaturityDate.getYear(),
                        MainActivity.this.myMaturityDate.getMonth()-1,
                        MainActivity.this.myMaturityDate.getDay(), 2070, 11, 31);
                MainActivity.this.maturity_date_picker.show();
            }
        });

    }

    private DatePickerDialog.OnDateSetListener pickerListenerEvalDate = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            // Show selected date
            evaluation_date.setText(new StringBuilder().append(selectedDay)
                    .append("/").append(selectedMonth+1).append("/").append(selectedYear));
            minMaturityDate.setDay(selectedDay);
            minMaturityDate.setMonth(selectedMonth+1);
            minMaturityDate.setYear(selectedYear);
            Date auxDate = new Date(maturity_date.getText().toString());
            boolean actualiza_fecha = auxDate.getYear() < selectedYear || auxDate.getYear() ==
                    selectedYear && auxDate.getMonth() < selectedMonth + 1 ||
                    auxDate.getYear() == selectedYear && auxDate.getMonth() == selectedMonth + 1
                            && auxDate.getDay() < selectedDay;
            if (actualiza_fecha){
                MainActivity.this.maturity_date.setText(new StringBuilder().append(selectedDay)
                        .append("/").append(selectedMonth+1).append("/").append(selectedYear));
                myMaturityDate.setDay(selectedDay);
                myMaturityDate.setMonth(selectedMonth+1);
                myMaturityDate.setYear(selectedYear);
            }

            maxEvalDate.setDay(selectedDay);
            maxEvalDate.setMonth(selectedMonth+1);
            maxEvalDate.setYear(selectedYear);

            myEvaluationDate.setDay(selectedDay);
            myEvaluationDate.setMonth(selectedMonth+1);
            myEvaluationDate.setYear(selectedYear);

            MainActivity.this.maturity.setText(new StringBuilder().append(
                    MainActivity.this.myMaturityDate.getValueInYears()-
                            MainActivity.this.myEvaluationDate.getValueInYears()
            ));
        }
    };

    private DatePickerDialog.OnDateSetListener pickerListenerMaturityDate = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            // Show selected date
            maturity_date.setText(new StringBuilder().append(selectedDay)
                    .append("/").append(selectedMonth+1).append("/").append(selectedYear));

            myMaturityDate.setDay(selectedDay);
            myMaturityDate.setMonth(selectedMonth+1);
            myMaturityDate.setYear(selectedYear);

            MainActivity.this.maturity.setText(new StringBuilder().append(
                    MainActivity.this.myMaturityDate.getValueInYears()-
                            MainActivity.this.myEvaluationDate.getValueInYears()
            ));
        }
    };

    private Boolean validate(String _spotLevel, String _strike, String _rate, String _dividend, String _maturity,
                             String _volatility, String _type){
        int fieldNumber = 0;
        String textError;
        try {
            fieldNumber = 1;
            double spotLevel = Double.parseDouble(_spotLevel);
            fieldNumber = 2;
            double strike = Double.parseDouble(_strike);
            fieldNumber = 3;
            double rate = Double.parseDouble(_rate);
            fieldNumber = 4;
            double dividend = Double.parseDouble(_dividend);
            fieldNumber = 5;
            double maturity = Double.parseDouble(_maturity);
            fieldNumber = 6;
            double volatility = Double.parseDouble(_volatility);
        } catch(Exception e){
            switch (fieldNumber) {
                case 1:
                    textError = "Spot Level must be a real number";
                    break;
                case 2:
                    textError = "Strike be a real number";
                    break;
                case 3:
                    textError = "Rate must be a real number";
                    break;
                case 4:
                    textError = "Dividend must be an integer number";
                    break;
                case 5:
                    textError = "Maturity must be an integer number";
                    break;
                case 6:
                    textError = "Volatility must be an integer number";
                    break;
                default:
                    textError = "Fatal error";
                    break;
            }
            Error.setMessage(textError);
            Error.show();
            return true;
        }

        if (!_type.equals("Put payoff") && !_type.equals("Call payoff")) {
            textError = "The type must be Call or Put payoff";
            Error.setMessage(textError);
            Error.show();
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View view){
        if (!this.validate(this.spotLevel.getText().toString(), this.strike.getText().toString(),
                this.rate.getText().toString(), this.dividend.getText().toString(),
                this.maturity.getText().toString(), this.volatility.getText().toString(),
                this.selected_type)) {
            double _spotLevel = Double.parseDouble(this.spotLevel.getText().toString());
            double _strike = Double.parseDouble(this.strike.getText().toString());
            double _volatility = Double.parseDouble(this.volatility.getText().toString());
            double _rate = Double.parseDouble(this.rate.getText().toString());
            double _dividend = Double.parseDouble(this.dividend.getText().toString());
            double _maturity = Double.parseDouble(this.maturity.getText().toString());

            BlackScholesModel myModel = new BlackScholesModel(_spotLevel, _volatility, _rate, _dividend);



            EuropeanOption myOption;

            if (this.selected_type.equals("Call payoff")){
                myOption = new EuropeanCall(myModel, _strike, _maturity);
            }
            else {
                myOption = new EuropeanPut(myModel, _strike, _maturity);
            }

            if (view.getId()==R.id.greeksButton){
                Intent i = new Intent(this, GreekResults.class);
                i.putExtra("financial.stockmarket.derivatives.DELTA", myOption.getGreek("DELTA", true));
                i.putExtra("financial.stockmarket.derivatives.GAMMA", myOption.getGreek("GAMMA", false));
                i.putExtra("financial.stockmarket.derivatives.VEGA", myOption.getGreek("VEGA", false));
                i.putExtra("financial.stockmarket.derivatives.THETA", myOption.getGreek("THETA", false));
                i.putExtra("financial.stockmarket.derivatives.RHO", myOption.getGreek("RHO", false));
                i.putExtra("financial.stockmarket.derivatives.DIVIDEND_DELTA",
                        myOption.getGreek("DIVIDEND_DELTA", false));
                startActivity(i);
            }
            else {
                Intent i = new Intent(this, Results.class);
                i.putExtra("financial.stockmarket.derivatives.PRICE_RESULT", myOption.getPrice(true));
                startActivity(i);
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id){
        this.selected_type = parent.getItemAtPosition(pos).toString();
    }

    @Override
    public void onNothingSelected(AdapterView parent){

    }

}
