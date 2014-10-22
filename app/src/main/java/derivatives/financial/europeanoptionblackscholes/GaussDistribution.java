package derivatives.financial.europeanoptionblackscholes;

/**
 * Created by casa on 17/10/14.
 */
public class GaussDistribution implements Integrable {

    private double mu;
    private double sigma;

    private double auxCalculateIntegralLinearSpline(double x0, double xf, double differential){
        double suma_darboux = 0.0;
        while (x0 < xf){
            suma_darboux = suma_darboux + differential * 1.0/2.0*(density(x0)+density(x0+differential));
            x0 = x0 + differential;
        }
        return suma_darboux;
    }

    public GaussDistribution(double __mu, double __sigma){
        this.mu = __mu;
        this.sigma = __sigma;
    }

    public double N(double x){
        return 1.0/2.0+calculateIntegral(0,x);
    }

    public double density(double x){
        return 1.0/(sigma*Math.sqrt(2.0*Math.PI))*Math.exp(-1.0/(2.0*Math.pow(sigma,2.0))*Math.pow((x-mu),2.0));
    }

    @Override
    public double calculateIntegral(double x0, double xf){
        double error = 0.001;
        double differential = Math.abs(xf-x0)/calculateIterations(x0, xf, error);
        if (xf <= x0)
            return (-1.0)*auxCalculateIntegralLinearSpline(xf, x0, differential);
        else
            return auxCalculateIntegralLinearSpline(x0, xf, differential);
    }

    @Override
    public double calculateError(double x0, double xf, int iterations){
        return Math.abs(xf-x0)/(double)iterations*Math.abs(density(xf)-density(x0));
    }

    @Override
    public int calculateIterations(double x0, double xf, double error){
        return (int)Math.round(Math.abs(xf-x0)/error*Math.abs(density(xf)-density(x0))) + 1;
    }
}
