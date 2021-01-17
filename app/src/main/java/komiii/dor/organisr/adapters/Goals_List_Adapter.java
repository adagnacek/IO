package komiii.dor.organisr.adapters;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

import komiii.dor.organisr.R;
import komiii.dor.organisr.containers.BoilerFunctions;

import static java.time.temporal.ChronoUnit.DAYS;

public class Goals_List_Adapter extends ArrayAdapter {

    ArrayList<String> result;
    ArrayList<Integer> resultId;
    ArrayList<Integer> resultType;
    ArrayList<Date> resultStartDate;
    ArrayList<Integer> resultInterval;
    ArrayList<Integer> resultGoals;
    ArrayList<Integer> resultProgress;
    ArrayList<String> resultIntervalUnit;

    public Goals_List_Adapter(Context context, int resource, int txtResource, List<String> l) {
        super(context, resource, txtResource,  l);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);
        convertView.setTag(R.string.idOne,resultId.toArray()[position]);
        convertView.setTag(R.string.idTwo,result.toArray()[position]);
        convertView.setTag(R.string.idThree,resultType.toArray()[position]);
        convertView.setTag(R.string.idFour,resultInterval.toArray()[position]);
        convertView.setTag(R.string.idFive,resultGoals.toArray()[position]);
        convertView.setTag(R.string.idSix,resultStartDate.toArray()[position]);
        convertView.setTag(R.string.idSeven,resultProgress.toArray()[position]);
        convertView.setTag(R.string.idEight,resultIntervalUnit.toArray()[position]);
        String type = "";
        switch((int)resultType.toArray()[position]){
            case 0:type=(String) getContext().getText(R.string.GoalType1);break;
            case 1:type=(String) getContext().getText(R.string.GoalType2);break;
            case 2:type=(String) getContext().getText(R.string.GoalType3);break;
            case 3:type=(String) getContext().getText(R.string.GoalType4);break;
            case 4:type=(String) getContext().getText(R.string.GoalType5);break;
        }
        final View cp = convertView;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoilerFunctions.makeDialog(getContext(),R.layout.dialog_goals_increase,cp);
            }
        });
        convertView.findViewById(R.id.goal_delete_butt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoilerFunctions.makeDialog(getContext(),R.layout.dialog_goals_delete,cp);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BoilerFunctions.makeDialog(getContext(),R.layout.dialog_goals_edit,cp);
                return true;
            }
        });

        TextView nameTV = convertView.findViewById(R.id.goals_item_name); nameTV.setText(result.toArray()[position]+" //"+type);
        DateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH);
        try{
            String firstDate = sdf.format(resultStartDate.toArray()[position]);
            Date secondDate = (Date) resultStartDate.toArray()[position];
            int unit = Calendar.DAY_OF_MONTH;
            switch(resultIntervalUnit.get(position)){
                case "w": unit = Calendar.WEEK_OF_YEAR;break;
                case "m": unit = Calendar.MONTH;break;
                case "y": unit = Calendar.YEAR;break;
            }
            Calendar cal = Calendar.getInstance();cal.setTime(secondDate);
            cal.add(unit,resultInterval.get(position));
            secondDate = cal.getTime();
            long duration = DAYS.between(resultStartDate.get(position).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),secondDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate() );
            long diff = DAYS.between((sdf.parse(firstDate)).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate() );
            TextView daysTV = convertView.findViewById(R.id.goals_item_days); daysTV.setText("Day: "+(diff+1)+"/"+duration);//daysTV.setText((String)resultStartDate.toArray()[position]+":"+type);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();}
        TextView progressTV = convertView.findViewById(R.id.goals_item_progress); progressTV.setText("Progress: "+resultProgress.toArray()[position]+"/"+resultGoals.toArray()[position]);

        if(resultProgress.toArray()[position]==resultGoals.toArray()[position]) convertView.setBackgroundResource(R.color.gray);
        return convertView;
    }

    public void setResult(ArrayList<String> result) {
        this.result = result;
    }

    public void setResultId(ArrayList<Integer> resultId) {
        this.resultId = resultId;
    }

    public void setResultType(ArrayList<Integer> resultType) {
        this.resultType = resultType;
    }

    public void setResultStartDate(ArrayList<Date> resultStartDate) {
        this.resultStartDate = resultStartDate;
    }

    public void setResultInterval(ArrayList<Integer> resultInterval) {
        this.resultInterval = resultInterval;
    }

    public void setResultGoals(ArrayList<Integer> resultGoals) {
        this.resultGoals = resultGoals;
    }

    public void setResultProgress(ArrayList<Integer> resultProgress) {
        this.resultProgress = resultProgress;
    }

    public void setResultIntervalUnit(ArrayList<String> resultIntervalUnit) {
        this.resultIntervalUnit = resultIntervalUnit;
    }


}
