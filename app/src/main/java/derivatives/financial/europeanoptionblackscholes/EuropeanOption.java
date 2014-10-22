package derivatives.financial.europeanoptionblackscholes;

/**
 * Created by casa on 17/10/14.
 */
import android.support.v7.appcompat.R;

import java.util.HashMap;
import java.util.Map;

import derivatives.financial.europeanoptionblackscholes.Date;

/**
 * Created by casa on 14/01/14.
 */
public abstract class EuropeanOption {
    private BlackScholesModel model;
    private double strike;
    private double startDate;
    private double maturityDate;
    private HashMap<String, Double> greeks_map;

    protected abstract void calculateGreeks();

    public EuropeanOption(BlackScholesModel model, double strike, double startDate, double maturityDate){
        this.model = model;
        this.strike = strike;
        this.startDate = startDate;
        this.maturityDate = maturityDate;
        this.greeks_map = new HashMap<String, Double>();
        greeks_map.put("DELTA", new Double(0.0));
        greeks_map.put("GAMMA", new Double(0.0));
        greeks_map.put("VEGA", new Double(0.0));
        greeks_map.put("THETA", new Double(0.0));
        greeks_map.put("RHO", new Double(0.0));
        greeks_map.put("DIVIDEND_DELTA", new Double(0.0));
        ;    }

    public EuropeanOption(BlackScholesModel model, double strike, double maturityDate){
        this(model, strike, 0.0f, maturityDate);
    }

    public BlackScholesModel getModel(){return model; }

    public double getStrike(){return strike; }

    public double getStartDate(){return startDate; }

    public double getMaturityDate(){return maturityDate; }

    public void setModel(BlackScholesModel model){this.model = model; }

    public void setStrike(double strike){this.strike = strike; }

    public void setStartDate(double startDate){this.startDate = startDate; }

    public void setMaturityDate(double maturityDate){this.maturityDate = maturityDate; }

    public double getGreek(String greek, boolean recalculate){
        if (recalculate){
            this.calculateGreeks();
        }
        return greeks_map.get(greek);
    }

    public HashMap<String, Double> getGreeksMap(){
        return greeks_map;
    }

    public abstract double getPrice(boolean recalculate);
}
