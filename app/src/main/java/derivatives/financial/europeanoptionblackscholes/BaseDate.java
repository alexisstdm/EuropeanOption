package derivatives.financial.europeanoptionblackscholes;

/**
 * Created by casa on 17/10/14.
 */
public class BaseDate {
    int day;
    int month;
    int year;

    public BaseDate(String date){
        try {
            int first_bar = date.indexOf('/', 0);
            this.day = Integer.parseInt(date.substring(0,first_bar));
            int second_bar = date.indexOf('/', first_bar+1);
            this.month = Integer.parseInt(date.substring(first_bar+1,second_bar));
            this.year = Integer.parseInt(date.substring(second_bar+1, date.length()));
        } catch (Exception e) {
            this.day = 0;
            this.month = 0;
            this.year = 0;
        }
    }

    public int getDay(){ return this.day;}
    public int getMonth(){ return this.month;}
    public int getYear(){ return this.year;}
    public void setDay(int day){ this.day = day;}
    public void setMonth(int month){ this.month = month;}
    public void setYear(int year){ this.year = year;}
}
