package komiii.dor.organisr;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.content.ContextCompat;
import android.widget.CalendarView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import komiii.dor.organisr.containers.BoilerFunctions;
import komiii.dor.organisr.containers.Event;
import komiii.dor.organisr.containers.Product;

public class ListGenerator {

    public static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    public static void createShoppingList(Context context) throws IOException, SQLException {
        ResultSet rs = BoilerFunctions.query(context,"SELECT * FROM PRODUCTS WHERE QUANTITY > 0 ORDER BY CATEGORY");
        ArrayList<Product> productList = new ArrayList<>();
        while(rs.next()) {
            Product newProduct = new Product();
            newProduct.setResultId(rs.getInt(1));
            newProduct.setResult(rs.getString(2));
            newProduct.setResultType(rs.getInt(3));
            newProduct.setResultPMin(rs.getDouble(4));
            newProduct.setResultPMax(rs.getDouble(5));
            newProduct.setResultList(rs.getInt(6));
            newProduct.setResultQuantity(rs.getInt(7));
            productList.add(newProduct);
        }
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
                File.separator + "ShoppingLists");
        boolean success = true;
        if (!folder.exists()) {
            success=folder.mkdirs();
        }
        if(success) {
            File newList = new File(folder, dateFormat.format(new Date())+"-shop.jpg");
            success = newList.createNewFile();
            FileOutputStream out = new FileOutputStream(newList);
            ListGenerator.shoppingPNG(context, productList).compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            if (success) {
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(newList)));
                Toast.makeText(context, "ListCreated: " + newList.getName(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    private static int LINE_SIZE = 50;private static int LINE_WIDTH = 400;
    public static Bitmap shoppingPNG(Context context, ArrayList<Product> products){
        Bitmap output = Bitmap.createBitmap(LINE_WIDTH,(int)(LINE_SIZE*(products.size()+1.5)), Bitmap.Config.ARGB_8888);
        output.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();paint.setColor(Color.BLACK);paint.setTextSize(35);paint.setTextAlign(Paint.Align.CENTER);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_shopping_black);
        drawable.setBounds((int)(LINE_SIZE*0.75*1.5),10,(int)(LINE_SIZE*0.75*2.5),(int)(LINE_SIZE*0.75)+10);
        canvas.drawText("Shopping list", LINE_WIDTH/2, LINE_SIZE* 0.8f, paint);
        canvas.drawLine( 30, LINE_SIZE, LINE_WIDTH-30, LINE_SIZE ,paint);
        paint.setColor(Color.BLACK);paint.setTextSize(20);paint.setTextAlign(Paint.Align.LEFT);
        for(int i=0;i<products.size();i++) {
            canvas.drawText(products.get(i).getResultQuantity()+"x ", LINE_SIZE/2f, LINE_SIZE * (i + 1.7f), paint);
            canvas.drawText(products.get(i).getResult(), LINE_SIZE*1.5f, LINE_SIZE * (i + 1.7f), paint);
            if(i!=products.size()-1) {
                paint.setColor(Color.GRAY);
                canvas.drawLine(20, LINE_SIZE * (i + 2), LINE_WIDTH - 20, LINE_SIZE * (i + 2), paint);
                paint.setColor(Color.BLACK);
            }
        }
        drawable.draw(canvas);
        return output;
    }

    public static void createDayPlan(Context context,String date,Date date2) throws SQLException,IOException{
//        ResultSet rs = BoilerFunctions.query(context,"SELECT * FROM events WHERE date='"+date+"'");
//        ArrayList<Event> eventsList = new ArrayList<>();
//        while(rs.next()) {
//            Event newEvent = new Event();
//            newEvent.setName(rs.getString(2));
//            newEvent.setHour(rs.getTime(4));
//            newEvent.setDuration(rs.getInt(5));
//            eventsList.add(newEvent);
//        }
        ArrayList<Event> eventsList = BoilerFunctions.getEventsBetween(context,date2,date2);

        ResultSet rs = BoilerFunctions.query(context,"SELECT * FROM todos WHERE date='"+date+"'");
        ArrayList<String> todosList = new ArrayList<>();
        while(rs.next()) {
            todosList.add(rs.getString(2));
        }

        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) +
                File.separator + "DayPlans");
        boolean success = true;
        if (!folder.exists()) {
            success=folder.mkdirs();
        }
        if(success) {
            File newList = new File(folder, dateFormat.format(new Date())+"-plan.jpg");
            success = newList.createNewFile();
            FileOutputStream out = new FileOutputStream(newList);
            ListGenerator.dayPNG(context, eventsList, todosList).compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
            if (success) {
                context.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(newList)));
                Toast.makeText(context, "DayPlanCreated: " + newList.getName(), Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show();
        }
    }

    private static int LINE_SIZE2 = 50;private static int LINE_WIDTH2 = 400;
    public static Bitmap dayPNG(Context context, ArrayList<Event> events, ArrayList<String> todos) {
        Bitmap output = Bitmap.createBitmap(LINE_WIDTH2, (int) (LINE_SIZE2 * (events.size() + 1.5)) + (int) (LINE_SIZE2 * (todos.size() + 1.5)), Bitmap.Config.ARGB_8888);
        output.eraseColor(Color.WHITE);
        Canvas canvas = new Canvas(output);
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(35);
        paint.setTextAlign(Paint.Align.CENTER);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.icon_calendar_black);
        drawable.setBounds((int) (LINE_SIZE2 * 0.75 * 2.5), 10, (int) (LINE_SIZE2 * 0.75 * 3.5), (int) (LINE_SIZE2 * 0.75) + 10);
        canvas.drawText("Events", LINE_WIDTH2 / 2, LINE_SIZE2 * 0.8f, paint);
        canvas.drawLine(30, LINE_SIZE2, LINE_WIDTH2 - 30, LINE_SIZE2, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        paint.setTextAlign(Paint.Align.LEFT);
        for (int i = 0; i < events.size(); i++) {
            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.HOUR_OF_DAY,events.get(i).getHour().getHours());
            cal.set(Calendar.MINUTE,events.get(i).getHour().getMinutes());
            cal.add(Calendar.MINUTE,events.get(i).getDuration());
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            String time = sdf.format(events.get(i).getHour())+"-"+sdf.format(cal.getTime());
            canvas.drawText(time, LINE_SIZE2 / 3f, LINE_SIZE2 * (i + 1.7f), paint);
            canvas.drawText(events.get(i).getName(), LINE_SIZE2 * 2.6f, LINE_SIZE2 * (i + 1.7f), paint);
            if (i != todos.size() - 1) {
                paint.setColor(Color.GRAY);
                canvas.drawLine(20, LINE_SIZE2 * (i + 2), LINE_WIDTH2 - 20, LINE_SIZE2 * (i + 2), paint);
                paint.setColor(Color.BLACK);
            }
        }
        drawable.draw(canvas);
        float offsetY = LINE_SIZE2 * (events.size()-1 + 2.5f);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(35);
        paint.setTextAlign(Paint.Align.CENTER);
        Drawable drawable2 = ContextCompat.getDrawable(context, R.drawable.icon_todos_black);
        drawable2.setBounds((int) (LINE_SIZE2 * 0.75 * 2.5), (int)(10+offsetY), (int) (LINE_SIZE2 * 0.75 * 3.5), (int) ((LINE_SIZE2 * 0.75) + 10+offsetY));
        canvas.drawText("ToDos", LINE_WIDTH2 / 2, LINE_SIZE2 * 0.8f+offsetY, paint);
        canvas.drawLine(30, LINE_SIZE2+offsetY, LINE_WIDTH2 - 30, LINE_SIZE2+offsetY, paint);
        paint.setColor(Color.BLACK);
        paint.setTextSize(20);
        for (int i = 0; i < todos.size(); i++) {
            canvas.drawText(todos.get(i), LINE_WIDTH2 / 2, LINE_SIZE2 * (i + 1.7f)+offsetY, paint);
            if (i != todos.size() - 1) {
                paint.setColor(Color.GRAY);
                canvas.drawLine(20, LINE_SIZE2 * (i + 2)+offsetY, LINE_WIDTH2 - 20, LINE_SIZE2 * (i + 2)+offsetY, paint);
                paint.setColor(Color.BLACK);
            }
        }
        drawable2.draw(canvas);
        return output;
    }
}
