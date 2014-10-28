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
        //double error = 0.001;
        //double differential = Math.abs(xf-x0)/calculateIterations(x0, xf, error);
        //if (xf <= x0)
        //    return (-1.0)*auxCalculateIntegralLinearSpline(xf, x0, differential);
        //else
        //    return auxCalculateIntegralLinearSpline(x0, xf, differential);
        double suma = 0.0;
        double h = 0.0;
        double factor = 0.0;
        double error = 0.001;

        int n = calculateIterations(x0, xf, error);

        if (n <= 0) n = 2;
        else if ((n%2)!=0) n = n + 1;

        h = (xf - x0)/ (double) n;

        for (int i=0; i<=n; i++){
            if ((i==0)||(i==n)) factor = 1.0;
            else factor = 2 + 2 * (i % 2);
            suma = suma + factor * density(x0 + (double) i * h);
        }

        return suma * h * 1.0/3.0;
    }

    @Override
    public double calculateError(double x0, double xf, int iterations){
        return Math.abs(xf-x0)/(double)iterations*Math.abs(density(xf)-density(x0));
    }

    @Override
    public int calculateIterations(double x0, double xf, double error){
        //  return (int)Math.round(Math.abs(xf-x0)/error*Math.abs(density(xf)-density(x0))) + 1;
        return 1000;
    }
}
