package komiii.dor.organisr.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

import komiii.dor.organisr.R;
import komiii.dor.organisr.adapters.Shopping_Basket_List_Adapter;
import komiii.dor.organisr.containers.BoilerEnum;
import komiii.dor.organisr.containers.BoilerFunctions;
import komiii.dor.organisr.containers.Product;

public class Shopping_BasketFragment extends Fragment {

    public void refresh(View v){
        final ListView products_list = v.findViewById(R.id.shopping_basket_list);
        ArrayList<Product> products = new ArrayList<Product>();
        double minSum = 0;
        double maxSum = 0;
        try {
            ResultSet rs = BoilerFunctions.query(getContext(),BoilerEnum.shoppingBasketQuery.getContent());
            while(rs.next()) {
                Product newProduct = new Product();
                newProduct.setResultId(rs.getInt(1));
                newProduct.setResult(rs.getString(2));
                newProduct.setResultType(rs.getInt(3));
                newProduct.setResultPMin(rs.getDouble(4));minSum+=rs.getDouble(4)*rs.getInt(7);
                newProduct.setResultPMax(rs.getDouble(5));maxSum+=rs.getDouble(5)*rs.getInt(7);
                newProduct.setResultList(rs.getInt(6));
                newProduct.setResultQuantity(rs.getInt(7));
                products.add(newProduct);
            }
        }catch (Exception e){
            Log.e("Error",e.getMessage());
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }

        ArrayList<String> result = new ArrayList<String>();
        for(Product x:products)result.add(x.getResult());
        final Shopping_Basket_List_Adapter adapter = new Shopping_Basket_List_Adapter(getContext(), R.layout.shopping_basket_item, R.id.shopping_basket_item_name, result);
        adapter.setProducts(products);

        products_list.setAdapter(adapter);

        String str1 = String.format("%.2f",minSum);;
        String str2 = String.format("%.2f",maxSum);;
        String str = str1+"zł - "+str2+"zł";
        ((TextView)v.findViewById(R.id.shop_basket_estimation)).setText(str);
    }

    public Shopping_BasketFragment() { }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shopping_basket, container, false);
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
