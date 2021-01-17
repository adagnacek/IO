package komiii.dor.organisr.activities;

import android.app.Fragment;
import android.os.StrictMode;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

import komiii.dor.organisr.containers.BoilerEnum;
import komiii.dor.organisr.adapters.Reminders_List_Adapter;
import komiii.dor.organisr.R;
import komiii.dor.organisr.containers.BoilerFunctions;
import komiii.dor.organisr.containers.DialogMaker;

public class RemindersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminders);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        refresh();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    public void add(View v){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        DialogFragment newFragment = DialogMaker.newInstance(R.layout.dialog_reminders_add);
        newFragment.show(ft, "dialog");
    }

    public void delete(final View v){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        DialogFragment newFragment = DialogMaker.newInstance(R.layout.dialog_reminders_delete,v);
        newFragment.show(ft, "dialog");
    }

    public void refresh(){
        final ListView reminders_list = findViewById(R.id.reminders_list);
        ArrayList<String> result = new ArrayList<String>();
        ArrayList<Integer> resultId = new ArrayList<Integer>();
        ArrayList<Integer> resultType = new ArrayList<Integer>();

        try {
            ResultSet rs = BoilerFunctions.query(this,BoilerEnum.reminderQuery.getContent());
            while(rs.next()) {
                resultId.add(rs.getInt(1));
                result.add(rs.getString(2));
                resultType.add(rs.getInt(3));
            }
        }catch (Exception e){
            Log.e("Error",e.getMessage());
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
        }

        final Reminders_List_Adapter adapter = new Reminders_List_Adapter(this, R.layout.reminders_item, R.id.reminders_item_text, result);
        adapter.setResultIds(resultId);
        adapter.setCaptions(result);
        adapter.setTypes(resultType);

        reminders_list.setAdapter(adapter);
    }


}