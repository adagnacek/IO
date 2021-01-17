package komiii.dor.organisr.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import komiii.dor.organisr.R;
import komiii.dor.organisr.activities.CalendarActivity;
import komiii.dor.organisr.containers.BoilerEnum;
import komiii.dor.organisr.containers.Event;

public class CalendarAdapter extends ArrayAdapter {

    private LayoutInflater inflater;
    private ArrayList<Date> days;
    private ArrayList<Event> events;
    private Context context;

    private Date selected;

    public CalendarAdapter(Context contextIn, ArrayList<Date> daysIn, ArrayList<Event> eventsIn){
        super(contextIn, R.layout.calendar_cal_item, daysIn);
        inflater = LayoutInflater.from(contextIn);
        days = daysIn;
        events = eventsIn;
        context = contextIn;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        if(convertView == null){
            convertView = inflater.inflate(R.layout.calendar_cal_item,parent,false);
        }
        Calendar cal = Calendar.getInstance(); cal.setTime(days.get(position));
        ((TextView)convertView.findViewById(R.id.dayNum)).setText(""+cal.get(Calendar.DAY_OF_MONTH));

        if(days.get(position).getYear()==selected.getYear() && days.get(position).getMonth()==selected.getMonth() && days.get(position).getDate()==selected.getDate()){
            ((TextView)convertView.findViewById(R.id.dayNum)).setTextColor(getContext().getResources().getColor(R.color.mainLightAccent,getContext().getTheme()));
        }

        int urgency_level = 0;
        for(Event e: events){
            if(days.get(position).getYear()==e.getDate().getYear() && days.get(position).getMonth()==e.getDate().getMonth() && days.get(position).getDate()==e.getDate().getDate())urgency_level+=e.getUrgency();
        }

        int colorId;
        if(urgency_level<=BoilerEnum.URGENCY_LEVEL0){colorId=R.color.Urg0;}
        else if(urgency_level<=BoilerEnum.URGENCY_LEVEL1){colorId=R.color.Urg1;}
        else if(urgency_level<=BoilerEnum.URGENCY_LEVEL2){colorId=R.color.Urg2;}
        else if(urgency_level<=BoilerEnum.URGENCY_LEVEL3){colorId=R.color.Urg3;}
        else {colorId=R.color.Urg4;}

        GradientDrawable bg = (GradientDrawable)((LayerDrawable)convertView.findViewById(R.id.cal_item_bg).getBackground()).findDrawableByLayerId(R.id.cal_item_color);
        bg.setColor(getContext().getResources().getColor(colorId,getContext().getTheme()));

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((CalendarView)((CalendarActivity)context).findViewById(R.id.calendar_topPart)).setCalendarSelected(days.get(position));
                ((CalendarView)((CalendarActivity)context).findViewById(R.id.calendar_topPart)).update();
            }
        });


        return convertView;
    }

    public void setSelected(Date in){
        selected = in;
    }

    @Override
    public int getCount() {
        return days.size();
    }
}
