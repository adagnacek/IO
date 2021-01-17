package komiii.dor.organisr.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;

import komiii.dor.organisr.containers.BoilerEnum;
import komiii.dor.organisr.containers.BoilerFunctions;
import komiii.dor.organisr.R;
import komiii.dor.organisr.SQLUpdater;

public class MainActivity extends AppCompatActivity  implements NavigationView.OnNavigationItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //Set Views
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_Open, R.string.drawer_Closed);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        toolbar.setVisibility(View.GONE);
        navigationView.setNavigationItemSelectedListener(this);
        //
        //Start Service
        startService(new Intent(this, SQLUpdater.class));
        //
        //Queries
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        //Connect
        BoilerFunctions.connect(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ((TextView)findViewById(R.id.main_event1)).setText("");
        ((TextView)findViewById(R.id.main_event2)).setText("");
        ((TextView)findViewById(R.id.main_event3)).setText("");
        ((TextView)findViewById(R.id.main_shop1)).setText("");
        ((TextView)findViewById(R.id.main_shop2)).setText("");
        ((TextView)findViewById(R.id.main_shop3)).setText("");
        ((TextView)findViewById(R.id.main_goal1)).setText("");
        ((TextView)findViewById(R.id.main_goal2)).setText("");
        ((TextView)findViewById(R.id.main_goal3)).setText("");
        ((TextView)findViewById(R.id.main_rem1)).setText("");
        ((TextView)findViewById(R.id.main_rem2)).setText("");
        ((TextView)findViewById(R.id.main_rem3)).setText("");
        ((TextView)findViewById(R.id.main_check1)).setText("");
        ((TextView)findViewById(R.id.main_check2)).setText("");
        ((TextView)findViewById(R.id.main_check3)).setText("");
        refresh();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            BoilerFunctions.disconnect(this);
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        BoilerFunctions.disconnect(this);
        super.onDestroy();
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        int id = item.getItemId();

        switch(id){
            case R.id.main_drawer_remindersItem:
                goReminders(null);
                break;
            case R.id.main_drawer_shoppingItem:
                goShopping(null);
                break;
            case R.id.main_drawer_goalsItem:
                goGoals(null);
                break;
            case R.id.main_drawer_calendarItem:
                goEvents(null);
                break;
            case R.id.main_drawer_todosItem:
                goTodos(null);
                break;
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void goReminders(View view){
        startActivity(new Intent(this, RemindersActivity.class));
    }

    public void goShopping(View view){
        startActivity(new Intent(this, ShoppingActivity.class));
    }

    public void goGoals(View view){
        startActivity(new Intent(this, GoalsActivity.class));
    }

    public void goEvents(View view){
        startActivity(new Intent(this, CalendarActivity.class));
    }

    public void goTodos(View view){
        startActivity(new Intent(this, TodosActivity.class));
    }

    public void refresh(){
        try {
            //
            ResultSet rs = BoilerFunctions.query(this, BoilerEnum.mainQuery.getContent());
            rs.next();
            String html = "\""+rs.getString(2)+"\" <i>~ "+rs.getString(3)+"</i>";
            ((TextView)findViewById(R.id.main_quote)).setText(Html.fromHtml(html));
            //
            rs = BoilerFunctions.query(this, BoilerEnum.mainSecondQuery.getContent());
            if(rs.next()){((TextView)findViewById(R.id.main_event1)).setText(rs.getString(2)+" ("+rs.getTime(4).toString().substring(0,5)+")");
                if(rs.next()){((TextView)findViewById(R.id.main_event2)).setText(rs.getString(2)+" ("+rs.getTime(4).toString().substring(0,5)+")");
                    if(rs.next()){((TextView)findViewById(R.id.main_event3)).setText(rs.getString(2)+" ("+rs.getTime(4).toString().substring(0,5)+")");}}}
            //
            rs = BoilerFunctions.query(this, BoilerEnum.mainThirdQuery.getContent());
            if(rs.next()){((TextView)findViewById(R.id.main_shop1)).setText(rs.getString(2)+" x"+rs.getInt(7));
                if(rs.next()){((TextView)findViewById(R.id.main_shop2)).setText(rs.getString(2)+" x"+rs.getInt(7));
                    if(rs.next()){((TextView)findViewById(R.id.main_shop3)).setText(rs.getString(2)+" x"+rs.getInt(7));}}}
            //
            rs = BoilerFunctions.query(this, BoilerEnum.mainFourthQuery.getContent());
            if(rs.next()){((TextView)findViewById(R.id.main_rem1)).setText(rs.getString(2));
                if(rs.next()){((TextView)findViewById(R.id.main_rem2)).setText(rs.getString(2));
                    if(rs.next()){((TextView)findViewById(R.id.main_rem3)).setText(rs.getString(2));}}}
            //
            rs = BoilerFunctions.query(this, BoilerEnum.mainFifthQuery.getContent());
            if(rs.next()){((TextView)findViewById(R.id.main_goal1)).setText(rs.getString(2)+": "+rs.getInt(7)+"/"+rs.getInt(6));
                if(rs.next()){((TextView)findViewById(R.id.main_goal2)).setText(rs.getString(2)+": "+rs.getInt(7)+"/"+rs.getInt(6));
                    if(rs.next()){((TextView)findViewById(R.id.main_goal3)).setText(rs.getString(2)+": "+rs.getInt(7)+"/"+rs.getInt(6));}}}
            //
            rs = BoilerFunctions.query(this, BoilerEnum.mainSixthQuery.getContent());
            if(rs.next()){((TextView)findViewById(R.id.main_check1)).setText(rs.getString(2));
                if(rs.next()){((TextView)findViewById(R.id.main_check2)).setText(rs.getString(2));
                    if(rs.next()){((TextView)findViewById(R.id.main_check3)).setText(rs.getString(2));}}}
            //
        }catch (Exception e){e.printStackTrace(); Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG);}
    }
}
