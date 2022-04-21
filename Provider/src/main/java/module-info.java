import org.example.provide.PensionerCalculator;
import org.example.provide.WorkingAgeCalculator;
import org.example.taxcalculator.TaxCalculator;

module Provider {
    requires org.example.taxcalculator;
    provides TaxCalculator with PensionerCalculator, WorkingAgeCalculator;
}