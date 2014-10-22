package derivatives.financial.europeanoptionblackscholes;

/**
 * Created by casa on 17/10/14.
 */
import java.util.HashMap;

/**
 * Created by casa on 10/10/14.
 */
public abstract class Function {
    public abstract double evaluate(HashMap<String, Double> parameters);
}
