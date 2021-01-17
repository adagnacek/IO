package komiii.dor.organisr.adapters;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import komiii.dor.organisr.R;
import komiii.dor.organisr.containers.BoilerEnum;
import komiii.dor.organisr.containers.BoilerFunctions;
import komiii.dor.organisr.containers.Event;

public class DayAdapter extends ArrayAdapter<Event> {

    LayoutInflater inflater;

    ArrayList<Event> events;
    Context context;

    public DayAdapter(Context context, ArrayList<Event> events){
        super(context, R.layout.calendar_day_item ,events);
        inflater = LayoutInflater.from(context);

        this.context = context;
        this.events = events;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){
            convertView = inflater.inflate(R.layout.calendar_day_item,parent,false);
        }

        Event currentEvent = events.get(position);
        convertView.setTag(R.string.idOne,currentEvent.getId());
        convertView.setTag(R.string.idTwo,currentEvent.getName());
        convertView.setTag(R.string.idThree,currentEvent.getDate());
        convertView.setTag(R.string.idFour,currentEvent.getHour());
        convertView.setTag(R.string.idFive,currentEvent.getDuration());
        convertView.setTag(R.string.idSix,currentEvent.getRepeatence());
        convertView.setTag(R.string.idSeven,currentEvent.getRepeatenceUnit());
        convertView.setTag(R.string.idEight,currentEvent.getUrgency());
        convertView.setTag(R.string.idNine,currentEvent.getDescription());

        DateFormat dateFormat = new SimpleDateFormat("HH:mm");

        ((TextView)convertView.findViewById(R.id.event_name)).setText(currentEvent.getName());
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,currentEvent.getHour().getHours());
        calendar.set(Calendar.MINUTE,currentEvent.getHour().getMinutes());
        String startTime = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.MINUTE,currentEvent.getDuration());
        ((TextView)convertView.findViewById(R.id.event_time)).setText(String.format("%s-%s",startTime,dateFormat.format(calendar.getTime())));

        int colorId;
        if(currentEvent.getUrgency()<= BoilerEnum.URGENCY_LEVELA){colorId=R.color.Urg0;}
        else if(currentEvent.getUrgency()<=BoilerEnum.URGENCY_LEVELB){colorId=R.color.Urg1;}
        else if(currentEvent.getUrgency()<=BoilerEnum.URGENCY_LEVELC){colorId=R.color.Urg2;}
        else if(currentEvent.getUrgency()<=BoilerEnum.URGENCY_LEVELD){colorId=R.color.Urg3;}
        else {colorId=R.color.Urg4;}

        GradientDrawable bg = (GradientDrawable)((LayerDrawable)convertView.findViewById(R.id.day_item_bg).getBackground()).findDrawableByLayerId(R.id.cal_item_color);
        bg.setColor(getContext().getResources().getColor(colorId,getContext().getTheme()));

        final View cp = convertView;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoilerFunctions.makeDialog(context,R.layout.dialog_calendar_event_edit,cp);
            }
        });

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BoilerFunctions.makeDialog(context,R.layout.dialog_calendar_event_delete,cp);
                return true;
            }
        });

        return convertView;
    }

    @Override
    public int getCount() {
        return events.size();
    }
}
