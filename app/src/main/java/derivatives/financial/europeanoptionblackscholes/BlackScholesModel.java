package derivatives.financial.europeanoptionblackscholes;

/**
 * Created by casa on 17/10/14.
 */
public class BlackScholesModel {
    private double spotLevel;
    private double volatility;
    private double rate;
    private double dividend;

    public BlackScholesModel(double spotLevel, double volatility, double rate, double dividend){
        this.spotLevel = spotLevel;
        this.volatility = volatility;
        this.rate = rate;
        this.dividend = dividend;
    }

    public double getSpotLevel(){return spotLevel; }
    public double getVolatility(){return volatility;}
    public double getRate(){return rate; }
    public double getDividend(){return dividend; }

    public void setSpotLevel(double spotLevel){this.spotLevel = spotLevel; }
    public void setVolatility(double volatility){this.volatility = volatility; }
    public void setRate(double rate){this.rate = rate; }
    public void setDividend(double dividend){this.dividend = dividend; }
}
