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

public class Results extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState){
        Bundle extra = this.getIntent().getExtras();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.results);

        DecimalFormat myFormatter = new DecimalFormat("###,###,###,###,###.#####");

        TextView _price = (TextView) findViewById(R.id.result_text);
        String paymentString = '\t' +
                myFormatter.format(extra.getDouble("financial.stockmarket.derivatives.PRICE_RESULT")) + '\n';
        _price.setText(paymentString);
    }
}
