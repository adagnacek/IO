package komiii.dor.organisr.containers;

import android.content.Context;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class BoilerFunctions {

    public static Connection connection;
    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void connect(Context context){
        try{
        connection = DriverManager.getConnection(BoilerEnum.connectionURL.getContent(), BoilerEnum.connectionUsername.getContent(), BoilerEnum.connectionPassword.getContent());
        }catch (Exception e){
            Log.e("Error",e.getMessage());
            Toast.makeText(context,"Couldn't connect: "+e.getMessage(),Toast.LENGTH_LONG).show();
            connect(context);
        }
    }

    public static ResultSet query(Context context, String string){
        try{
            Statement st = connection.createStatement();
            return st.executeQuery(string);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            connect(context);
            query(context,string);
        }
        return null;
    }

    public static void update(Context context, String string){
        try{
            Statement st = connection.createStatement();
            st.executeUpdate(string);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
            connect(context);
            update(context,string);
        }
    }

    public static void disconnect(Context context){
        try{
            connection.close();
        }catch (Exception e){
            Log.e("Error",e.getMessage());
            Toast.makeText(context,e.getMessage(),Toast.LENGTH_LONG).show();
        }
    }

    public static void makeDialog(Context context,int layoutId, View v){

        FragmentTransaction ft = ((AppCompatActivity)context).getSupportFragmentManager().beginTransaction();
        Fragment prev = ((AppCompatActivity)context).getSupportFragmentManager().findFragmentByTag("dialog");
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        DialogFragment newFragment = DialogMaker.newInstance(layoutId,v);
        newFragment.show(ft, "dialog");
    }

    //EVENT FUNCTIONS
    public static int translateDateUnit(String str){
        switch(str){
            case "d" : return Calendar.DAY_OF_MONTH;
            case "w" : return Calendar.WEEK_OF_MONTH;
            case "m" : return Calendar.MONTH;
            case "y" : return Calendar.YEAR;
            default : return -1;
        }
    }

    private static ArrayList<Event> extractEvents(ResultSet eventsQuery){
        ArrayList<Event> result = new ArrayList<>();

        try {
            while (eventsQuery.next()) {
                Event newEvent = new Event();
                newEvent.setId(eventsQuery.getInt("id"));
                newEvent.setName(eventsQuery.getString("name"));
                newEvent.setDate(eventsQuery.getDate("date"));
                newEvent.setHour(eventsQuery.getTime("hour"));
                newEvent.setDuration(eventsQuery.getInt("duration"));
                newEvent.setRepeatence(eventsQuery.getInt("repeatance"));
                newEvent.setRepeatenceUnit(eventsQuery.getString("repeatance_unit"));
                newEvent.setUrgency(eventsQuery.getInt("urgency"));
                newEvent.setDescription(eventsQuery.getString("description"));
                result.add(newEvent);
            }
        }catch(SQLException e){e.printStackTrace();}

        return result;
    }

    private static ArrayList<EventCancel> extractEventCancels(ResultSet eventCancelsQuery){
        ArrayList<EventCancel> result = new ArrayList<>();
        try {
            while (eventCancelsQuery.next()) {
                EventCancel newEvent = new EventCancel();
                newEvent.setId(eventCancelsQuery.getInt("id"));
                newEvent.setEvent_id(eventCancelsQuery.getInt("event_id"));
                newEvent.setCancel_date(eventCancelsQuery.getDate("cancel_date"));
                result.add(newEvent);
            }
        }catch(SQLException e){e.printStackTrace();}
        return result;
    }

    public static ArrayList<Event> getEventsBetween(Context context, Date dateFrom, Date dateTo){
        try {
            String strFrom = dateFormat.format(dateFrom);
            dateFrom = dateFormat.parse(dateFormat.format(dateFrom));
            String today = dateFormat.format(new Date());
            String strTo = dateFormat.format(dateTo);

            ResultSet eventsQuery = query(context, String.format("SELECT * FROM events where date between '%s' and '%s' order by hour", today, strTo));
            ArrayList<Event> result = extractEvents(eventsQuery);
            int n = result.size();
            Calendar calendar = Calendar.getInstance();
            for (int i = 0; i < n; i++) {
                Event current_event = result.get(i);
                if (current_event.getRepeatence() != 0) {
                    calendar.setTime(current_event.getDate());
                    int unit = translateDateUnit(current_event.repeatence_unit);
                    calendar.add(unit, current_event.getRepeatence());
                    while (calendar.getTime().compareTo(dateTo) <= 0) {
                        Event copy = new Event();
                        copy.setId(current_event.getId());
                        copy.setName(current_event.getName());
                        copy.setDate(new java.sql.Date(calendar.getTime().getTime()));
                        copy.setHour(current_event.getHour());
                        copy.setDuration(current_event.getDuration());
                        copy.setRepeatence(current_event.getRepeatence());
                        copy.setRepeatenceUnit(current_event.getRepeatenceUnit());
                        copy.setUrgency(current_event.getUrgency());
                        copy.setDescription(current_event.getDescription());
                        result.add(copy);
                        calendar.add(unit, current_event.getRepeatence());
                    }
                }
            }
            for (int i = result.size() - 1; i >= 0; i--)
                if (result.get(i).getDate().compareTo(dateFrom) < 0) {
                    result.remove(i);
                }

            ResultSet cancelQuery = query(context, String.format("SELECT * FROM events_cancels where cancel_date between '%s' and '%s'", strFrom, strTo));
            ArrayList<EventCancel> cancels = extractEventCancels(cancelQuery);

            for (EventCancel cancel : cancels) {
                for (Event event : result) {
                    if (event.getId() == cancel.getEvent_id() && event.getDate().toString().equals(cancel.getCancel_date().toString())) {
                        result.remove(event);
                        break;
                    }
                }
            }

            for (int i = 0;i<result.size();i++){
                for(int j=1;j<result.size()-i;j++){
                    if(result.get(j).hour.before(result.get(j-1).hour)){
                        Event dummy = result.get(j);
                        result.set(j,result.get(j-1));
                        result.set(j-1,dummy);
                    }
                }
            }

            return result;
        }catch(ParseException e){e.printStackTrace();}
        return null;
    }

}
