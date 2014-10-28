package derivatives.financial.europeanoptionblackscholes;

/**
 * Created by casa on 17/10/14.
 */
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import derivatives.financial.europeanoptionblackscholes.GaussDistribution;
import derivatives.financial.europeanoptionblackscholes.Function;

/**
 * Created by casa on 14/01/14.
 */
public class EuropeanCall extends EuropeanOption {
    private double price;
    private HashMap<String, Function> greeksFunctions;

    private class calculateDelta extends  Function{
        public double evaluate(HashMap<String, Double> parameters){

            final GaussDistribution gaussDistribution = new GaussDistribution(0.0, 1.0);

            double d_plus = 0.0;
            //double v = EuropeanCall.this.getModel().getVolatility();
            //double s = EuropeanCall.this.getModel().getSpotLevel();
            //double tau = EuropeanCall.this.getMaturityDate()-EuropeanCall.this.getStartDate();
            //double k = EuropeanCall.this.getStrike();
            //double d = EuropeanCall.this.getModel().getDividend();
            //double r = EuropeanCall.this.getModel().getRate();
            double v = parameters.get("VOLATILITY");
            double s = parameters.get("SPOT_LEVEL");
            double tau = parameters.get("MATURITY");
            double k = parameters.get("STRIKE");
            double d = parameters.get("DIVIDEND");
            double r = parameters.get("RATE");
            d_plus = 1.0/(v*Math.sqrt(tau))*Math.log(s/(k*Math.exp((d-r)*tau))) + 1.0/2.0*v*Math.sqrt(tau);

            return Math.exp(-d*tau)*gaussDistribution.N(d_plus);
        }
    }

    private class calculateGamma extends  Function{
        public double evaluate(HashMap<String, Double> parameters){

            GaussDistribution gaussDistribution = new GaussDistribution(0.0, 1.0);

            double d_plus = 0.0;
            //double v = EuropeanCall.this.getModel().getVolatility();
            //double s = EuropeanCall.this.getModel().getSpotLevel();
            //double tau = EuropeanCall.this.getMaturityDate()-EuropeanCall.this.getStartDate();
            //double k = EuropeanCall.this.getStrike();
            //double d = EuropeanCall.this.getModel().getDividend();
            //double r = EuropeanCall.this.getModel().getRate();
            double v = parameters.get("VOLATILITY");
            double s = parameters.get("SPOT_LEVEL");
            double tau = parameters.get("MATURITY");
            double k = parameters.get("STRIKE");
            double d = parameters.get("DIVIDEND");
            double r = parameters.get("RATE");
            d_plus = 1.0/(v*Math.sqrt(tau))*Math.log(s/(k*Math.exp((d-r)*tau))) + 1.0/2.0*v*Math.sqrt(tau);

            return 1.0/(v*Math.sqrt(tau))*Math.exp(-d*tau)/s*gaussDistribution.density(d_plus);
        }
    }

    private class calculateVega extends  Function{
        public double evaluate(HashMap<String, Double> parameters){

            GaussDistribution gaussDistribution = new GaussDistribution(0.0, 1.0);

            double d_plus = 0.0;
            double d_minus= 0.0;
            //double v = EuropeanCall.this.getModel().getVolatility();
            //double s = EuropeanCall.this.getModel().getSpotLevel();
            //double tau = EuropeanCall.this.getMaturityDate()-EuropeanCall.this.getStartDate();
            //double k = EuropeanCall.this.getStrike();
            //double d = EuropeanCall.this.getModel().getDividend();
            //double r = EuropeanCall.this.getModel().getRate();
            double v = parameters.get("VOLATILITY");
            double s = parameters.get("SPOT_LEVEL");
            double tau = parameters.get("MATURITY");
            double k = parameters.get("STRIKE");
            double d = parameters.get("DIVIDEND");
            double r = parameters.get("RATE");
            d_plus = 1.0/(v*Math.sqrt(tau))*Math.log(s/(k*Math.exp((d-r)*tau))) + 1.0/2.0*v*Math.sqrt(tau);
            d_minus = d_plus - v*Math.sqrt(tau);

            return s*Math.sqrt(tau)*gaussDistribution.density(d_plus)*Math.exp(-d*tau);
        }
    }

    private class calculateTheta extends  Function{
        public double evaluate(HashMap<String, Double> parameters){

            GaussDistribution gaussDistribution = new GaussDistribution(0.0, 1.0);

            double d_plus = 0.0;
            double d_minus= 0.0;
            //double v = EuropeanCall.this.getModel().getVolatility();
            //double s = EuropeanCall.this.getModel().getSpotLevel();
            //double tau = EuropeanCall.this.getMaturityDate()-EuropeanCall.this.getStartDate();
            //double k = EuropeanCall.this.getStrike();
            //double d = EuropeanCall.this.getModel().getDividend();
            //double r = EuropeanCall.this.getModel().getRate();
            double v = parameters.get("VOLATILITY");
            double s = parameters.get("SPOT_LEVEL");
            double tau = parameters.get("MATURITY");
            double k = parameters.get("STRIKE");
            double d = parameters.get("DIVIDEND");
            double r = parameters.get("RATE");
            d_plus = 1.0/(v*Math.sqrt(tau))*Math.log(s/(k*Math.exp((d-r)*tau))) + 1.0/2.0*v*Math.sqrt(tau);
            d_minus = d_plus - v*Math.sqrt(tau);

            return (d*gaussDistribution.N(d_plus)-v/(2.0*Math.sqrt(tau))*gaussDistribution.density(d_minus)) *
                    s*Math.exp(-d*tau)-k*r*Math.exp(-r*tau)*
                    gaussDistribution.N(d_minus);
        }
    }

    private class calculateRho extends  Function{
        public double evaluate(HashMap<String, Double> parameters){

            GaussDistribution gaussDistribution = new GaussDistribution(0.0, 1.0);

            double d_plus = 0.0;
            double d_minus= 0.0;
            //double v = EuropeanCall.this.getModel().getVolatility();
            //double s = EuropeanCall.this.getModel().getSpotLevel();
            //double tau = EuropeanCall.this.getMaturityDate()-EuropeanCall.this.getStartDate();
            //double k = EuropeanCall.this.getStrike();
            //double d = EuropeanCall.this.getModel().getDividend();
            //double r = EuropeanCall.this.getModel().getRate();
            double v = parameters.get("VOLATILITY");
            double s = parameters.get("SPOT_LEVEL");
            double tau = parameters.get("MATURITY");
            double k = parameters.get("STRIKE");
            double d = parameters.get("DIVIDEND");
            double r = parameters.get("RATE");
            d_plus = 1.0/(v*Math.sqrt(tau))*Math.log(s/(k*Math.exp((d-r)*tau))) + 1.0/2.0*v*Math.sqrt(tau);
            d_minus = d_plus - v*Math.sqrt(tau);

            return tau*k*Math.exp(-r*tau)*gaussDistribution.N(d_minus);
        }
    }

    private class calculateDividendDelta extends  Function{
        public double evaluate(HashMap<String, Double> parameters){

            GaussDistribution gaussDistribution = new GaussDistribution(0.0, 1.0);

            double d_plus = 0.0;
            double d_minus= 0.0;
            //double v = EuropeanCall.this.getModel().getVolatility();
            //double s = EuropeanCall.this.getModel().getSpotLevel();
            //double tau = EuropeanCall.this.getMaturityDate()-EuropeanCall.this.getStartDate();
            //double k = EuropeanCall.this.getStrike();
            //double d = EuropeanCall.this.getModel().getDividend();
            //double r = EuropeanCall.this.getModel().getRate();
            double v = parameters.get("VOLATILITY");
            double s = parameters.get("SPOT_LEVEL");
            double tau = parameters.get("MATURITY");
            double k = parameters.get("STRIKE");
            double d = parameters.get("DIVIDEND");
            double r = parameters.get("RATE");
            d_plus = 1.0/(v*Math.sqrt(tau))*Math.log(s/(k*Math.exp((d-r)*tau))) + 1.0/2.0*v*Math.sqrt(tau);
            d_minus = d_plus - v*Math.sqrt(tau);

            return -tau*s*Math.exp(-d*tau)*gaussDistribution.N(d_plus);
        }
    }

    public EuropeanCall(BlackScholesModel model, double strike, double startDate, double maturityDate) {
        super(model, strike, startDate, maturityDate);
        this.price = 0.0;
        this.greeksFunctions = new HashMap<String, Function>();
        this.greeksFunctions.put("DELTA", new calculateDelta());
        this.greeksFunctions.put("GAMMA", new calculateGamma());
        this.greeksFunctions.put("VEGA", new calculateVega());
        this.greeksFunctions.put("THETA", new calculateTheta());
        this.greeksFunctions.put("RHO", new calculateRho());
        this.greeksFunctions.put("DIVIDEND_DELTA", new calculateDividendDelta());
    }

    public EuropeanCall(BlackScholesModel model, double strike, double maturityDate) {
        super(model, strike, maturityDate);
        this.price = 0.0;
        this.greeksFunctions = new HashMap<String, Function>();
        this.greeksFunctions.put("DELTA", new calculateDelta());
        this.greeksFunctions.put("GAMMA", new calculateGamma());
        this.greeksFunctions.put("VEGA", new calculateVega());
        this.greeksFunctions.put("THETA", new calculateTheta());
        this.greeksFunctions.put("RHO", new calculateRho());
        this.greeksFunctions.put("DIVIDEND_DELTA", new calculateDividendDelta());
    }

    protected void calculateGreeks(){

        // Construimos parámetros de invocación para la función
        HashMap<String, Double> parameters = new HashMap<String, Double>();

        parameters.put("SPOT_LEVEL", this.getModel().getSpotLevel());
        parameters.put("VOLATILITY", this.getModel().getVolatility());
        parameters.put("RATE", this.getModel().getRate());
        parameters.put("DIVIDEND", this.getModel().getDividend());
        parameters.put("STRIKE", this.getStrike());
        parameters.put("MATURITY", this.getMaturityDate());

        for (Map.Entry<String, Double> entry : this.getGreeksMap().entrySet()) {
            this.getGreeksMap().put(entry.getKey(), this.greeksFunctions.get(entry.getKey()).evaluate(parameters));
        }
    }

    private double calculatePrice(){
        GaussDistribution gaussDistribution = new GaussDistribution(0.0, 1.0);
        double d_plus = 0.0;
        double d_minus= 0.0;
        double v = this.getModel().getVolatility();
        double s = this.getModel().getSpotLevel();
        double tau = this.getMaturityDate()-this.getStartDate();
        double k = this.getStrike();
        double d = this.getModel().getDividend();
        double r = this.getModel().getRate();
        d_plus = 1.0/(v*Math.sqrt(tau))*Math.log(s/(k*Math.exp((d-r)*tau))) + 1.0/2.0*v*Math.sqrt(tau);
        d_minus = d_plus - v*Math.sqrt(tau);
        return s*Math.exp(-d*tau)*gaussDistribution.N(d_plus)-k*Math.exp(-r*tau)*gaussDistribution.N(d_minus);
    }

    public double getPrice(boolean recalculate){
        if (recalculate) {
            this.price = calculatePrice();
        }
        return this.price;
    }
}
