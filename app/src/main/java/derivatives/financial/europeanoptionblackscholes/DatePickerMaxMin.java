package derivatives.financial.europeanoptionblackscholes;

/**
 * Created by casa on 17/10/14.
 */
import android.app.DatePickerDialog;
import android.content.Context;
import android.widget.DatePicker;

/**
 * Created by casa on 5/10/14.
 */
public class DatePickerMaxMin extends DatePickerDialog {
    private int maxYear = 2050;
    private int maxMonth = 11;
    private int maxDay = 31;
    private int minYear = 1900;
    private int minMonth = 0;
    private int minDay = 1;
    private boolean fired = false;

    public DatePickerMaxMin(Context context, OnDateSetListener callBack, int minYear,
                            int minMonth, int minDay, int maxYear, int maxMonth, int maxDay){
        super(context, callBack, minYear, minMonth, minDay);
        this.maxYear = maxYear;
        this.maxMonth = maxMonth;
        this.maxDay = maxDay;
        this.minYear = minYear;
        this.minMonth = minMonth;
        this.minDay = minDay;
        this.fired = false;
    }

    @Override
    public void onDateChanged(DatePicker view, int year, int month, int day){
        super.onDateChanged(view, year, month, day);
        boolean actualiza_fecha = false;

        if (!fired)
        {
            fired = true;
            actualiza_fecha = year > maxYear || year == maxYear && month > maxMonth ||
                    year == maxYear && month == maxMonth && day > maxDay;
            if (actualiza_fecha){
                view.updateDate(maxYear, maxMonth, maxDay);
                return;
            }

            actualiza_fecha = year < minYear || year == minYear && month < minMonth ||
                    year == minYear && month == minMonth && day < minDay;

            if (actualiza_fecha){
                view.updateDate(minYear, minMonth, minDay);
                return;
            }

            view.updateDate(year, month, day);
            fired = false;
        }
        else
        {
            fired = false;
        }
    }

    public void setMaxDate(int year, int month, int day){
        this.maxYear = year;
        this.maxMonth = month;
        this.maxDay = day;
    }

    public void setMinDate(int year, int month, int day){
        this.minYear = year;
        this.minMonth = month;
        this.minDay = day;
    }
}
