package komiii.dor.organisr.adapters;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import komiii.dor.organisr.R;
import komiii.dor.organisr.containers.BoilerFunctions;
import komiii.dor.organisr.containers.Event;

public class CalendarView extends LinearLayout {

    TextView yearText;
    TextView monthText;
    LinearLayout head;
    Button today;
    Button rmCancels;
    Button prevYear;
    Button nextYear;
    Button prevMonth;
    Button nextMonth;
    GridView calendarDays;
    GridView dayCard;

    Calendar calendar;
    public Date calendarSelected;

    public CalendarView(Context context){
        this(context, null);
    }

    public CalendarView(Context context, AttributeSet attrs){
        this(context,attrs,0);
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyle){
        super(context,attrs);
        initControl(context,attrs);
    }

    private void initControl(Context context, AttributeSet attrs) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.calendar_view, this);
        assignViews();
    }

    public void assignViews() {
        this.yearText = findViewById(R.id.year_Text);
        this.monthText = findViewById(R.id.month_Text);
        this.head = findViewById(R.id.calendar_head);
        this.today = findViewById(R.id.today_button);
        this.prevYear = findViewById(R.id.prevYear_button);
        this.nextYear = findViewById(R.id.nextYear_button);
        this.prevMonth = findViewById(R.id.prevMonth_button);
        this.nextMonth = findViewById(R.id.nextMonth_button);
        this.calendarDays = findViewById(R.id.calendar_days);
        this.dayCard = findViewById(R.id.calendar_botPart);
        this.rmCancels = findViewById(R.id.remove_cancels_button);

        prevYear.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prevYear();
                    }
                }
        );
        nextYear.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextYear();
                    }
                }
        );
        prevMonth.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        prevMonth();
                    }
                }
        );
        nextMonth.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nextMonth();
                    }
                }
        );
        today.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        today();
                    }
                }
        );
        rmCancels.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                BoilerFunctions.makeDialog(getContext(),R.layout.dialog_cal_decancel,v);
            }
        });


        calendar = Calendar.getInstance();
        calendarSelected = calendar.getTime();
        update();
    }

    private void prevYear(){
        calendar.add(Calendar.YEAR,-1);
        update();
    }

    private void nextYear(){
        calendar.add(Calendar.YEAR,1);
        update();
    }

    private void prevMonth(){
        calendar.add(Calendar.MONTH,-1);
        update();
    }

    private void nextMonth(){
        calendar.add(Calendar.MONTH,1);
        update();
    }

    private void today(){
        calendar = Calendar.getInstance();
        calendarSelected = new Date();
        update();
    }

    public void decancel(){
        BoilerFunctions.update(getContext(),"DELETE FROM EVENTS_CANCELS where cancel_date='"+BoilerFunctions.dateFormat.format(calendarSelected)+"'");
        update();
    }

    public void setCalendarSelected(Date newDate){
        calendarSelected = newDate;
    }

    public void update() {
        this.yearText.setText(String.format("%d",calendar.get(Calendar.YEAR)));
        this.monthText.setText(calendar.getDisplayName(Calendar.MONTH,Calendar.LONG, Locale.ENGLISH));

        Calendar dummyCal = Calendar.getInstance(); Calendar dummyCal2 = Calendar.getInstance();
        dummyCal.setTime(calendar.getTime());dummyCal.set(Calendar.DAY_OF_MONTH,1);
        int startDay = (dummyCal.get(Calendar.DAY_OF_WEEK)+5)%7+1;
        for(;startDay>1;startDay--)dummyCal.add(Calendar.DAY_OF_MONTH,-1);
        dummyCal2.setTime(dummyCal.getTime());
        ArrayList<Date> ad = new ArrayList<Date>();
        ad.add(dummyCal2.getTime());
        for(int i = 0;i<41;i++){dummyCal2.add(Calendar.DAY_OF_MONTH,1);ad.add(dummyCal2.getTime());}

        ArrayList<Event> events = BoilerFunctions.getEventsBetween(getContext(),dummyCal.getTime(),dummyCal2.getTime());

        ArrayAdapter aaTop = new CalendarAdapter(getContext(),ad, events);
        ((CalendarAdapter)aaTop).setSelected(calendarSelected);
        calendarDays.setAdapter(aaTop);

        ArrayList<Event> dayEvents = BoilerFunctions.getEventsBetween(getContext(),calendarSelected,calendarSelected);
        ArrayAdapter aaBottom = new DayAdapter(getContext(),dayEvents);
        dayCard.setAdapter(aaBottom);
    }

}
