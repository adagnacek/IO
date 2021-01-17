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
import android.widget.Toast;

import java.sql.ResultSet;
import java.util.ArrayList;

import komiii.dor.organisr.R;
import komiii.dor.organisr.adapters.Shopping_Wishlist_Adapter;
import komiii.dor.organisr.containers.BoilerEnum;
import komiii.dor.organisr.containers.BoilerFunctions;
import komiii.dor.organisr.containers.Product;

public class Shopping_WishlistFragment extends Fragment {

    public Shopping_WishlistFragment() {}

    public void refresh(View v){

        ListView productEList = v.findViewById(R.id.shopping_wishlist_listview);

        ArrayList<Product> products = new ArrayList<>();
        ArrayList<String> names = new ArrayList<>();

        try {
            ResultSet rs = BoilerFunctions.query(getContext(),BoilerEnum.shoppingWishListQuery.getContent());
            while(rs.next()) {
                Product newProduct = new Product();
                newProduct.setResultId(rs.getInt(1));
                newProduct.setResult(rs.getString(2));names.add(rs.getString(2));
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

        Shopping_Wishlist_Adapter adapter = new Shopping_Wishlist_Adapter(getContext(),R.layout.shopping_wishlist_item,R.id.shopping_basket_item_name,names);
        adapter.setProducts(products);
        productEList.setAdapter(adapter);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_shopping_wishlist, container, false);
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
