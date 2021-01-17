package komiii.dor.organisr;

import android.support.v4.app.Fragment;
import android.widget.Filter;
import komiii.dor.organisr.fragments.Shopping_ProductListFragment;

public class ProductFilter extends Filter {

    public Fragment fragment;

    @Override
    protected FilterResults performFiltering(CharSequence constraint) {


        return null;
    }

    @Override
    protected void publishResults(CharSequence constraint, FilterResults results) {
        ((Shopping_ProductListFragment)fragment).refreshFiltered(fragment.getView(),constraint);
    }
}
