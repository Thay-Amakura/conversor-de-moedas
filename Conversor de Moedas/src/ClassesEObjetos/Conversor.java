package ClassesEObjetos;

import java.util.Map;

public class Conversor {
    private String base_code;
    private Map<String, Double> conversion_rates;

    public String getBaseCode(){
        return base_code;
    }

    public Map<String, Double> getConversionRates(){
        return conversion_rates;
    }
}