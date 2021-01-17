package komiii.dor.organisr.activities;

import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;

import komiii.dor.organisr.R;
import komiii.dor.organisr.containers.BoilerFunctions;
import komiii.dor.organisr.fragments.Checks_TodayFragment;
import komiii.dor.organisr.fragments.Checks_TommorowFragment;

public class TodosActivity extends AppCompatActivity implements Checks_TodayFragment.OnFragmentInteractionListener{

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_todos);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ((BottomNavigationView)findViewById(R.id.checks_navigationbar)).getMenu().getItem(0).setChecked(true);
        ((BottomNavigationView)findViewById(R.id.checks_navigationbar)).setOnNavigationItemSelectedListener(onisl);
        fragment=new Checks_TodayFragment();
        loadFragment(fragment);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed(){
        this.finish();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onisl = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
            switch (menuItem.getItemId()){
                case R.id.checks_bottommenu_today: fragment=new Checks_TodayFragment();loadFragment(fragment); return true;
                case R.id.checks_bottommenu_tomorrow: fragment=new Checks_TommorowFragment();loadFragment(fragment); return true;
            }
            return false;
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri){ }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.checks_frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void refreshFragment(){ getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit(); }

    public void checkboxChanged(View view){
        if(((CheckBox)view).isChecked()) {
            ((View) view.getParent()).setBackground(getResources().getDrawable(R.drawable.shape_todos_one));
            BoilerFunctions.update(this,"UPDATE todos set checked=1 where id="+((View) view.getParent()).getTag(R.string.idOne));
        }else{
            ((View) view.getParent()).setBackground(getResources().getDrawable(R.drawable.shape_todos_zero));
            BoilerFunctions.update(this,"UPDATE todos set checked=0 where id="+((View) view.getParent()).getTag(R.string.idOne));
        }
    }

    public void addItem(View v){
        BoilerFunctions.makeDialog(this,R.layout.dialog_todos_add,((View) v.getParent()));
    }
    public void addItemTomorrow(View v){
        BoilerFunctions.makeDialog(this,R.layout.dialog_todos_add_tomorrow,((View) v.getParent()));
    }

}
