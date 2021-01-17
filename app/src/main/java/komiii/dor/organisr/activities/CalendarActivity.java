package komiii.dor.organisr.activities;

import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import komiii.dor.organisr.ListGenerator;
import komiii.dor.organisr.R;
import komiii.dor.organisr.adapters.CalendarView;
import komiii.dor.organisr.containers.BoilerFunctions;

public class CalendarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendar);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void addEvent(View v){

        BoilerFunctions.makeDialog(this,R.layout.dialog_cal_add_event,v);

    }

    public void generateDayPlan(View v){
        Date selected =((CalendarView)findViewById(R.id.calendar_topPart)).calendarSelected;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

        try {
            ListGenerator.createDayPlan(this, dateFormat.format(selected),selected);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

}
