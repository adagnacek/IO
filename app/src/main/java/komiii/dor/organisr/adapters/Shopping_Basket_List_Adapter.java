package komiii.dor.organisr.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.LayerDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import komiii.dor.organisr.R;
import komiii.dor.organisr.containers.BoilerFunctions;
import komiii.dor.organisr.containers.Product;


public class Shopping_Basket_List_Adapter extends ArrayAdapter {

    private ArrayList<Product> products;

    public Shopping_Basket_List_Adapter(Context context, int resource, int txtResource, List<String> l) {
        super(context, resource, txtResource,  l);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);
        convertView.setTag(R.string.idOne,((Product)products.toArray()[position]).getResultId());
        convertView.setTag(R.string.idTwo,((Product)products.toArray()[position]).getResult());
        convertView.setTag(R.string.idThree,((Product)products.toArray()[position]).getResultList());
        convertView.setTag(R.string.idFour,((Product)products.toArray()[position]).getResultQuantity());

        final View cp = convertView;

        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BoilerFunctions.makeDialog(getContext(),R.layout.dialog_shopping_basket_edit,cp);
                return true;
            }
        });

        int id = ((Product) products.toArray()[position]).getResultType();
        ((TextView)convertView.findViewById(R.id.shopping_basket_item_pricing)).setText(((Product)products.toArray()[position]).getResultPMin()+" ~ "+((Product)products.toArray()[position]).getResultPMax());
        ((TextView)convertView.findViewById(R.id.shopping_basket_item_ammount)).setText("x"+((Product)products.toArray()[position]).getResultQuantity());

        Drawable tempDrawable = getContext().getResources().getDrawable(R.drawable.shape_wishlist);
        LayerDrawable bubble = (LayerDrawable) tempDrawable;
        GradientDrawable solidColor = (GradientDrawable) bubble.findDrawableByLayerId(R.id.wishlist_item_tint);
        switch(((Product)products.toArray()[position]).getResultType()){
            case 0: solidColor.setColor(getContext().getResources().getColor(R.color.ShopColor1));break;
            case 1: solidColor.setColor(getContext().getResources().getColor(R.color.ShopColor2));break;
            case 2: solidColor.setColor(getContext().getResources().getColor(R.color.ShopColor3));break;
            case 3: solidColor.setColor(getContext().getResources().getColor(R.color.ShopColor4));break;
            case 4: solidColor.setColor(getContext().getResources().getColor(R.color.ShopColor5));break;
        }
        convertView.setBackground(tempDrawable);
        return convertView;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

}
