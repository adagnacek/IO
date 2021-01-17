package komiii.dor.organisr.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import komiii.dor.organisr.ProductFilter;
import komiii.dor.organisr.R;
import komiii.dor.organisr.containers.BoilerFunctions;
import komiii.dor.organisr.containers.Product;

public class Shopping_ProductList_Adapter extends ArrayAdapter implements Filterable {

    private ArrayList<Product> products;
    private Context context;
    public Fragment fragment;
    private Filter filter;

    public Shopping_ProductList_Adapter(Context context, int resource, int textViewResourceId, ArrayList<Product> objects) {
        super(context, resource, textViewResourceId, objects);
        this.products = objects;
        this.context = context;
    }
    public void setFragment(Fragment newFragment){
        fragment = newFragment;
    }
    @Override
    public View getView(int childPosition, View convertView, ViewGroup parent) {

        LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView = infalInflater.inflate(R.layout.shopping_productlist_item, null);

        convertView.setTag(R.string.idOne,(products.get(childPosition).getResultId()));
        convertView.setTag(R.string.idTwo,(products.get(childPosition).getResult()));
        convertView.setTag(R.string.idThree,(products.get(childPosition).getResultList()));
        convertView.setTag(R.string.idFour,(products.get(childPosition).getResultPMin()));
        convertView.setTag(R.string.idFive,(products.get(childPosition).getResultPMax()));
        convertView.setTag(R.string.idSix,(products.get(childPosition).getResultType()));

        final View cp = convertView;

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoilerFunctions.makeDialog(getContext(), R.layout.dialog_shopping_productlist_basket, cp);
            }
        });
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BoilerFunctions.makeDialog(getContext(),R.layout.dialog_shopping_productlist_edit,cp);
                return true;
            }
        });

        TextView name = convertView.findViewById(R.id.shopping_productlist_item_name);
        name.setText(products.get(childPosition).getResult());


        if(products.get(childPosition).getResultPMin()!=0 && products.get(childPosition).getResultPMax() !=0) {
            TextView price = convertView.findViewById(R.id.shopping_basket_item_pricing);
            price.setText(products.get(childPosition).getResultPMin() + "zł - " + Double.toString(products.get(childPosition).getResultPMax()) + "zł");
        }
        Drawable tempDrawable = getContext().getResources().getDrawable(R.drawable.shape_wishlist);
        LayerDrawable bubble = (LayerDrawable) tempDrawable;
        GradientDrawable solidColor = (GradientDrawable) bubble.findDrawableByLayerId(R.id.wishlist_item_tint);
        switch(((Product)products.toArray()[childPosition]).getResultType()){
            case 0: solidColor.setColor(getContext().getResources().getColor(R.color.ShopColor1));break;
            case 1: solidColor.setColor(getContext().getResources().getColor(R.color.ShopColor2));break;
            case 2: solidColor.setColor(getContext().getResources().getColor(R.color.ShopColor3));break;
            case 3: solidColor.setColor(getContext().getResources().getColor(R.color.ShopColor4));break;
            case 4: solidColor.setColor(getContext().getResources().getColor(R.color.ShopColor5));break;
        }
        convertView.setBackground(tempDrawable);
        return convertView;
    }

    public Filter getFilter() {
        if(filter==null){
            filter = new ProductFilter();
            ((ProductFilter)filter).fragment = fragment;
        }
        return filter;
    }

    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
