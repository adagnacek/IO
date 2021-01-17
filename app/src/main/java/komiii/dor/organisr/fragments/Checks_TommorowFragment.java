package komiii.dor.organisr.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;

import komiii.dor.organisr.R;
import komiii.dor.organisr.adapters.TodosAdapter;
import komiii.dor.organisr.containers.BoilerEnum;
import komiii.dor.organisr.containers.BoilerFunctions;

public class Checks_TommorowFragment extends Fragment {

        public Checks_TommorowFragment() { }

        public void refresh(View v){

            ArrayList<Integer> ids = new ArrayList<>();
            ArrayList<String> names = new ArrayList<>();
            ArrayList<Date> dates = new ArrayList<>();
            ArrayList<Integer> checks = new ArrayList<>();

            try {
                ResultSet rs = BoilerFunctions.query(getContext(), BoilerEnum.todosTomorrowQuery.getContent());
                while(rs.next()) {
                    ids.add(rs.getInt(1));
                    names.add(rs.getString(2));
                    dates.add(rs.getDate(3));
                    checks.add(rs.getInt(4));
                }
            }catch (Exception e){
                Log.e("Error",e.getMessage());
                Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
            }

            final ArrayAdapter adapter = new TodosAdapter(getContext(),R.layout.todos_item,R.id.todo_name,names);
            ((TodosAdapter) adapter).setIds(ids);
            ((TodosAdapter) adapter).setNames(names);
            ((TodosAdapter) adapter).setDates(dates);
            ((TodosAdapter) adapter).setChecks(checks);

            ((ListView)v.findViewById(R.id.todosList)).setAdapter(adapter);
        }


        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_todos_tomorrow, container, false);
            refresh(v);
            return v;
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
        }

        @Override
        public void onDetach() {
            super.onDetach();
        }

        public interface OnFragmentInteractionListener {

            void onFragmentInteraction(Uri uri);
        }

}
