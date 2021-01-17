package komiii.dor.organisr.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

import komiii.dor.organisr.R;
import komiii.dor.organisr.adapters.Shopping_ProductList_Adapter;
import komiii.dor.organisr.containers.BoilerEnum;
import komiii.dor.organisr.containers.BoilerFunctions;
import komiii.dor.organisr.containers.Product;

public class Shopping_ProductListFragment extends Fragment {

    public Shopping_ProductListFragment() { }

    private ArrayList<Product> products = new ArrayList<>();

    public void refresh(View v){

        products = new ArrayList<>();
        ListView productEList = v.findViewById(R.id.product_elist);

        try {
            ResultSet rs = BoilerFunctions.query(getContext(),BoilerEnum.shoppingProductListQuery.getContent());
            while(rs.next()) {
                Product newProduct = new Product();
                newProduct.setResultId(rs.getInt(1));
                newProduct.setResult(rs.getString(2));
                newProduct.setResultType(rs.getInt(3));
                newProduct.setResultPMin(rs.getDouble(4));
                newProduct.setResultPMax(rs.getDouble(5));
                newProduct.setResultList(rs.getInt(6));
                newProduct.setResultQuantity(rs.getInt(7));
                products.add(newProduct);
            }
        }catch (Exception e){
            Log.e("Error",e.getMessage());
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

        final ArrayAdapter adapter = new Shopping_ProductList_Adapter(getContext(),R.layout.shopping_productlist_item,R.id.shopping_productlist_item_name,products);
        ((Shopping_ProductList_Adapter) adapter).setFragment(this);
        EditText searchField = v.findViewById(R.id.searchField);

        TextWatcher tw = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        searchField.addTextChangedListener(tw);
        productEList.setAdapter(adapter);
    }

    public void refreshFiltered(View v,CharSequence constant){

        products = new ArrayList<>();
        ListView productEList = v.findViewById(R.id.product_elist);

        try {
            ResultSet rs = BoilerFunctions.query(getContext(),BoilerEnum.shoppingProductListQuery.getContent());
            while(rs.next()) {
                Product newProduct = new Product();
                newProduct.setResultId(rs.getInt(1));
                newProduct.setResult(rs.getString(2));
                newProduct.setResultType(rs.getInt(3));
                newProduct.setResultPMin(rs.getDouble(4));
                newProduct.setResultPMax(rs.getDouble(5));
                newProduct.setResultList(rs.getInt(6));
                newProduct.setResultQuantity(rs.getInt(7));
                if(newProduct.getResult().toLowerCase().contains(constant.toString().toLowerCase()))products.add(newProduct);
            }
        }catch (Exception e){
            Log.e("Error",e.getMessage());
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

        ArrayAdapter adapter = new Shopping_ProductList_Adapter(getContext(),R.layout.shopping_productlist_item,R.id.shopping_productlist_item_name,products);
        productEList.setAdapter(adapter);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Toolbar toolbar = getActivity().findViewById(R.id.main_toolbar);
        getActivity().findViewById(R.id.main_toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shopping_productlist, container, false);
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
