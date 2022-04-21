import org.example.calculatorprovider.PensionerCalculator;
import org.example.calculatorprovider.WorkingAgeCalculator;
import org.example.taxcalculator.TaxCalculator;

module Provider {
    requires org.example.taxcalculator;
    provides TaxCalculator with PensionerCalculator, WorkingAgeCalculator;
}