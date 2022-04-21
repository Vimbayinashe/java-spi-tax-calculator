import org.example.calculatorprovider.PensionerCalculator;
import org.example.calculatorprovider.WorkingAgeCalculator;
import org.example.taxcalculator.TaxCalculator;

module org.example.calculatorprovider {
    requires org.example.taxcalculator;
    provides TaxCalculator with PensionerCalculator, WorkingAgeCalculator;
}