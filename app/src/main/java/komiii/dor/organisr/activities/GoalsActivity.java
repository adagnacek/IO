package komiii.dor.organisr.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;

import komiii.dor.organisr.R;
import komiii.dor.organisr.adapters.Goals_List_Adapter;
import komiii.dor.organisr.containers.BoilerEnum;
import komiii.dor.organisr.containers.BoilerFunctions;
import komiii.dor.organisr.containers.DialogMaker;

public class GoalsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goals);

        refresh();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void edit(final View v){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Edit : "+((View)((View)v.getParent()).getParent()).getTag(R.string.idTwo));
        LayoutInflater inflater = getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.dialog_goals_add, null));

        builder.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { dialog.dismiss();
            }
        });
        final AlertDialog alert = builder.create();

        alert.setButton(Dialog.BUTTON_POSITIVE,"SAVE",new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int which) {
                BoilerFunctions.update(getBaseContext(),"UPDATE goals SET NAME ='"+ ((EditText)alert.findViewById(R.id.dialog_goals_name)).getText() +"', type ="+
                        ((Spinner) alert.findViewById(R.id.goals_spinner)).getSelectedItemId()+", start_date ='"+
                        ((DatePicker)alert.findViewById(R.id.dialog_goals_datePicker)).getYear()+"-"+(((DatePicker)alert.findViewById(R.id.dialog_goals_datePicker)).getMonth()+1)+"-"+((DatePicker)alert.findViewById(R.id.dialog_goals_datePicker)).getDayOfMonth()+"', `interval`='"+
                        ((EditText)alert.findViewById(R.id.dialog_goals_frequency)).getText()+"', goal='"+
                        ((EditText)alert.findViewById(R.id.dialog_goals_goal)).getText()+"' WHERE (id = "+
                        ((View)((View)v.getParent()).getParent()).getTag(R.string.idOne)+");");
                refresh();
                Toast.makeText(getBaseContext(),"Goal edited",Toast.LENGTH_LONG).show();
                dialog.dismiss();
            }
        });
        String[] date = (((View)((View)v.getParent()).getParent()).getTag(R.string.idSix)).toString().split("-");
        alert.show();
        ((TextView) alert.findViewById(R.id.dialog_goals_name)).setText((String)((View)((View)v.getParent()).getParent()).getTag(R.string.idTwo));
//        ((Spinner) alert.findViewById(R.id.goals_spinner)).setAdapter(typesSpinnerAdapter);((Spinner) alert.findViewById(R.id.goals_spinner)).setSelection((int)((View)((View)v.getParent()).getParent()).getTag(R.string.idThree));
        ((TextView) alert.findViewById(R.id.dialog_goals_frequency)).setText(((View)((View)v.getParent()).getParent()).getTag(R.string.idFour)+"");
        ((TextView) alert.findViewById(R.id.dialog_goals_goal)).setText(((View)((View)v.getParent()).getParent()).getTag(R.string.idFive)+"");
        ((DatePicker)alert.findViewById(R.id.dialog_goals_datePicker)).updateDate(Integer.parseInt(date[0]),Integer.parseInt(date[1])-1,Integer.parseInt(date[2]));
    }

    public void add(View v){
        BoilerFunctions.makeDialog(this,R.layout.dialog_goals_add,v);
    }

    public void refresh(){
        final ListView goals_list = findViewById(R.id.goals_lv);
        ArrayList<String> result = new ArrayList<>();
        ArrayList<Integer> resultId = new ArrayList<>();
        ArrayList<Integer> resultType = new ArrayList<>();
        ArrayList<Date> resultStartDate = new ArrayList<>();
        ArrayList<Integer> resultInterval = new ArrayList<>();
        ArrayList<Integer> resultGoals = new ArrayList<>();
        ArrayList<Integer> resultProgress = new ArrayList<>();
        ArrayList<String> resultIntervalUnit = new ArrayList<>();

        try {
            ResultSet rs = BoilerFunctions.query(this, BoilerEnum.goalsRefreshQuery.getContent());
            while(rs.next()) {
                resultId.add(rs.getInt(1));
                result.add(rs.getString(2));
                resultType.add(rs.getInt(3));
                resultStartDate.add(rs.getDate(4));
                resultInterval.add(rs.getInt(5));
                resultGoals.add(rs.getInt(6));
                resultProgress.add(rs.getInt(7));
                resultIntervalUnit.add(rs.getString(8));
            }
        }catch (Exception e){
            Log.e("Error",e.getMessage());
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        final Goals_List_Adapter adapter = new Goals_List_Adapter(this, R.layout.goals_item, R.id.goals_item_name, result);
            adapter.setResultId(resultId);
            adapter.setResult(result);
            adapter.setResultType(resultType);
            adapter.setResultStartDate(resultStartDate);
            adapter.setResultInterval(resultInterval);
            adapter.setResultGoals(resultGoals);
            adapter.setResultProgress(resultProgress);
            adapter.setResultIntervalUnit(resultIntervalUnit);

            goals_list.setAdapter(adapter);
    }
}
