package derivatives.financial.europeanoptionblackscholes;

/**
 * Created by casa on 17/10/14.
 */
public class Date extends BaseDate{

    double valueInYears;
    double valueInDays;
    BaseDate referenceDate;
    String countDaysConvention;
    int daysInMonth[] = new int[] {31,28,31,30,31,30,31,31,30,31,30,31};

    public Date(String date){
        super(date);
        this.calculate("01/01/1900", "ACT/365");
    }

    public boolean isLeapYear(){
        if (this.year % 100 == 0) {
            return (this.year % 400 == 0);
        }
        else {
            return (this.year % 4 == 0);
        }
    }

    private int Months2Days(int month){
        int total = 0;
        for (int i=0; i<month; i++){
            total = total + this.daysInMonth[i];
        }
        return total;
    }

    private void calculate(String referenceDate, String countDayConvention){

        int days_in_this_year;

        this.referenceDate = new BaseDate(referenceDate);
        this.countDaysConvention = countDayConvention;
        this.valueInDays = 365*(this.year - this.referenceDate.getYear()) + (this.year - this.referenceDate.getYear())/4;
        this.valueInDays = this.valueInDays + Months2Days(this.month) - Months2Days(this.referenceDate.getMonth());

        if (isLeapYear()){
            days_in_this_year = 366;
        }
        else {
            days_in_this_year = 365;
        }

        this.valueInYears = this.year - this.referenceDate.getYear() +
                (double)Months2Days(this.month)/(double)days_in_this_year -
                (double)Months2Days(this.referenceDate.getMonth())/(double)days_in_this_year +
                (double)this.day/(double)days_in_this_year -
                (double)this.referenceDate.getDay()/(double)days_in_this_year;
    }

    public double getValueInYears(){
        this.calculate("01/01/1900", "ACT/365");
        return this.valueInYears;
    }
}
