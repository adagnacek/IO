package komiii.dor.organisr.activities;

import android.app.Dialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;


import java.io.IOException;
import java.sql.SQLException;

import komiii.dor.organisr.ListGenerator;
import komiii.dor.organisr.containers.BoilerEnum;
import komiii.dor.organisr.R;
import komiii.dor.organisr.containers.BoilerFunctions;
import komiii.dor.organisr.containers.DialogMaker;
import komiii.dor.organisr.fragments.Shopping_BasketFragment;
import komiii.dor.organisr.fragments.Shopping_ProductListFragment;
import komiii.dor.organisr.fragments.Shopping_WishlistFragment;

public class ShoppingActivity extends AppCompatActivity implements Shopping_ProductListFragment.OnFragmentInteractionListener, Shopping_BasketFragment.OnFragmentInteractionListener, Shopping_WishlistFragment.OnFragmentInteractionListener {

    private Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ((BottomNavigationView)findViewById(R.id.shopping_navigationbar)).getMenu().getItem(1).setChecked(true);
        ((BottomNavigationView)findViewById(R.id.shopping_navigationbar)).setOnNavigationItemSelectedListener(onisl);
        fragment=new Shopping_BasketFragment();
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
                case R.id.shopping_bottommenu_wishbutt: fragment=new Shopping_WishlistFragment();loadFragment(fragment); return true;
                case R.id.shopping_bottommenu_basketbutt: fragment=new Shopping_BasketFragment();loadFragment(fragment); return true;
                case R.id.shopping_bottommenu_prodlistbutt: fragment=new Shopping_ProductListFragment();loadFragment(fragment); return true;
            }
            return false;
        }
    };

    @Override
    public void onFragmentInteraction(Uri uri){ }

    private void loadFragment(Fragment fragment){
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.shopping_frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    public void refreshFragment(){ getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit(); }

    public void doneB(View v){
        String str = "";
        switch((Integer)((View)((View)v.getParent()).getParent()).getTag(R.string.idThree)){
            case 0: str = BoilerEnum.shoppingBasketDoneFirstQuerry.getContent();break;
            case 1: str = BoilerEnum.shoppingBasketDoneSecondQuerry.getContent();break;
        }
        BoilerFunctions.update(this,str+((View)((View)v.getParent()).getParent()).getTag(R.string.idOne)+")");
        Toast.makeText(this,(((View)((View)v.getParent()).getParent()).getTag(R.string.idTwo))+": Bought",Toast.LENGTH_LONG).show();
        refreshFragment();
    }

    public void deleteB(View v){
        BoilerFunctions.update(this,BoilerEnum.shoppingBasketDoneFirstQuerry.getContent()+((View)((View)v.getParent()).getParent()).getTag(R.string.idOne)+")");
        Toast.makeText(this,((View)((View)v.getParent()).getParent()).getTag(R.string.idTwo)+": Removed",Toast.LENGTH_LONG).show();
        refreshFragment();
    }

    public void addL(View v){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        android.app.Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        DialogFragment newFragment = DialogMaker.newInstance(R.layout.dialog_shopping_productlist_add);
        newFragment.show(ft, "dialog");
    }

    public void addWishlist(View v){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        android.app.Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        DialogFragment newFragment = DialogMaker.newInstance(R.layout.dialog_shopping_wishlist_add);
        newFragment.show(ft, "dialog");
    }


    public void deleteW(final View v){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        android.app.Fragment prev = getFragmentManager().findFragmentByTag("dialog");
        DialogFragment newFragment = DialogMaker.newInstance(R.layout.dialog_product_delete,v);
        newFragment.show(ft, "dialog");
    }

    public void generateImage(View v) throws SQLException, IOException {
        ListGenerator.createShoppingList(this);
    }
}
