package komiii.dor.organisr;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.StrictMode;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.util.Log;
import android.widget.Toast;

import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import komiii.dor.organisr.containers.BoilerEnum;
import komiii.dor.organisr.containers.BoilerFunctions;

public class SQLUpdaterBR extends BroadcastReceiver {

    public static final int UPDATE_FREQUENCY = 1000 * 60 * 10;

    long nine = 1000 * 60 * 60 * 24 * 7;
    long eight = 1000 * 60 * 60 * 24 * 3;
    long seven = 1000 * 60 * 60 * 24 * 2;
    long six = 1000 * 60 * 60 * 24;
    long five = 1000 * 60 * 60 * 6;
    long four = 1000 * 60 * 60 * 3;
    long three = 1000 * 60 * 60 * 2;
    long two = 1000 * 60 * 60 * 1;
    long one = 1000 * 60 * 30;

    long margin = 1000 * 60 * 5;

    @Override
    public void onReceive(Context context, Intent intent) {


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        createNotificationChannel(context);

        try {
            BoilerFunctions.connect(context);

            ArrayList<Integer> events_cancelled = new ArrayList<>();
            ArrayList<java.sql.Date> events_cancelledDates = new ArrayList<>();
            ResultSet rsDummy = BoilerFunctions.query(context, BoilerEnum.calendarSecondQuery.getContent());
            while (rsDummy.next()) {
                events_cancelled.add(rsDummy.getInt(2));
                events_cancelledDates.add(rsDummy.getDate(3));
            }

            ResultSet rs = BoilerFunctions.query(context, BoilerEnum.calendarFirstQuery.getContent());

            while (rs.next()) {
                SimpleDateFormat fdate = new SimpleDateFormat("yyyy-MM-dd kk:mm");
                String s1 = rs.getDate(3).toString() + " " + rs.getTime(4);
                Date d1 = fdate.parse(s1);
                Date now = new Date();
                int urgency = rs.getInt(8);

                String mssg = "";
                long difference = d1.getTime() - now.getTime();
                if (Math.abs(nine - difference) < margin && urgency>=9) {
                    mssg = "7 days";
                } else if (Math.abs(eight - difference) < margin && urgency>=8) {
                    mssg = "3 days";
                } else if (Math.abs(seven - difference) < margin && urgency>=7) {
                    mssg = "2 days";
                } else if (Math.abs(six - difference) < margin && urgency>=6) {
                    mssg = "1 day";
                } else if (Math.abs(five - difference) < margin && urgency>=5) {
                    mssg = "6 hours";
                } else if (Math.abs(four - difference) < margin && urgency>=4) {
                    mssg = "3 hours";
                } else if (Math.abs(three - difference) < margin && urgency>=3) {
                    mssg = "2 hours";
                } else if (Math.abs(two - difference) < margin && urgency>=2) {
                    mssg = "1 hour";
                } else if (Math.abs(one - difference) < margin && urgency>=1) {
                    mssg = "30 minutes";
                }

                if (!mssg.equals("")) {
                    boolean flag = true;
                    for(int i = 0;i<events_cancelled.size();i++){
                        if(rs.getInt(1)==events_cancelled.get(i) && rs.getDate(3).equals(events_cancelledDates.get(i))){
                            flag= false;
                            break;
                        }
                    }
                    if(flag) {
                        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
                        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, "0")
                                .setSmallIcon(R.drawable.icon_calendar)
                                .setContentTitle(rs.getString(2) + " in " + mssg)
                                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                                .setSound(alarmSound)
                                .setContentText(rs.getString(9));
                        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);
                        notificationManager.notify(0, builder.build());
                    }
                }
            }

            BoilerFunctions.disconnect(context);
        } catch (Exception e) {
            Log.e("Error", e.getMessage());
            Toast.makeText(context, "ERROR: " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

        Toast.makeText(context,"Fetching data...",Toast.LENGTH_LONG).show();
    }

    public void update(Context context) {
        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(context, SQLUpdaterBR.class);
        PendingIntent pi = PendingIntent.getBroadcast(context, 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), UPDATE_FREQUENCY, pi); // Millisec * Second * Minute

//        Calendar firingCal= Calendar.getInstance();
//        Calendar currentCal = Calendar.getInstance();

//        //firingCal.set(Calendar.HOUR, 0);
//        //firingCal.set(Calendar.MINUTE, 0);
//        firingCal.set(Calendar.SECOND, 0);
//
//        long intendedTime = firingCal.getTimeInMillis();
//        long currentTime = currentCal.getTimeInMillis();
//
//        if(intendedTime >= currentTime){
//            am.setRepeating(AlarmManager.RTC_WAKEUP, intendedTime, UPDATE_FREQUENCY, pi);
//        } else {
//            firingCal.add(Calendar.MINUTE, 1);
//            intendedTime = firingCal.getTimeInMillis();
//
//            am.setRepeating(AlarmManager.RTC_WAKEUP, intendedTime, UPDATE_FREQUENCY, pi);
//        }
    }

    private void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "n";
            String description = "d";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("0", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}