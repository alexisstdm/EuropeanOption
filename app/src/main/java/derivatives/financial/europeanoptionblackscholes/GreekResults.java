package derivatives.financial.europeanoptionblackscholes;

/**
 * Created by casa on 17/10/14.
 */
import android.app.Activity;
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
import android.widget.TextView;

import java.text.DecimalFormat;

public class GreekResults extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        Bundle extra = this.getIntent().getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.results_greeks);

        DecimalFormat myFormatter = new DecimalFormat("###,###,###,###,###.#####");

        TextView _delta = (TextView) findViewById(R.id.DELTA);
        TextView _gamma = (TextView) findViewById(R.id.GAMMA);
        TextView _vega = (TextView) findViewById(R.id.VEGA);
        TextView _theta = (TextView) findViewById(R.id.THETA);
        TextView _rho = (TextView) findViewById(R.id.RHO);
        TextView _dividend_delta = (TextView) findViewById(R.id.DIVIDEND_DELTA);

        String greeksString = '\t' + "Delta . . :"  +
                myFormatter.format(extra.getDouble("financial.stockmarket.derivatives.DELTA")) + '\n';
        _delta.setText(greeksString);
        greeksString = '\t' +        "Gamma . . :"  +
                myFormatter.format(extra.getDouble("financial.stockmarket.derivatives.GAMMA")) + '\n';
        _gamma.setText(greeksString);
        greeksString = '\t' +        "Vega  . . :"  +
                myFormatter.format(extra.getDouble("financial.stockmarket.derivatives.VEGA")) + '\n';
        _vega.setText(greeksString);
        greeksString = '\t' +        "Theta . . :" +
                myFormatter.format(extra.getDouble("financial.stockmarket.derivatives.THETA")) + '\n';
        _theta.setText(greeksString);
        greeksString = '\t' +        "Rho . . . :" +
                myFormatter.format(extra.getDouble("financial.stockmarket.derivatives.RHO")) + '\n';
        _rho.setText(greeksString);
        greeksString = '\t' +        "Dividend  :" +
                myFormatter.format(extra.getDouble("financial.stockmarket.derivatives.DIVIDEND_DELTA")) + '\n';
        _dividend_delta.setText(greeksString);
    }
}
