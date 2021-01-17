package komiii.dor.organisr.adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import komiii.dor.organisr.R;
import komiii.dor.organisr.containers.BoilerFunctions;

public class TodosAdapter extends ArrayAdapter {

    Context context;

    ArrayList<Integer> ids;
    ArrayList<String> names;
    ArrayList<Date> dates;
    ArrayList<Integer> checks;

    public TodosAdapter(Context context, int resource, int txtResource, List<String> l) {
        super(context, resource, txtResource,  l);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);
        convertView.setTag(R.string.idOne,ids.get(position));
        convertView.setTag(R.string.idTwo,names.get(position));
        convertView.setTag(R.string.idThree,dates.get(position));
        convertView.setTag(R.string.idFour,checks.get(position));
        final View cp = convertView;
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BoilerFunctions.makeDialog(getContext(),R.layout.dialog_todos_delete,cp);
                return true;
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoilerFunctions.makeDialog(getContext(),R.layout.dialog_todos_edit,cp);
            }
        });

        switch((Integer)checks.toArray()[position]){
            case 0: convertView.setBackground(context.getResources().getDrawable(R.drawable.shape_todos_zero)); ((CheckBox)convertView.findViewById(R.id.todo_check)).setChecked(false);break;
            case 1: convertView.setBackground(context.getResources().getDrawable(R.drawable.shape_todos_one)); ((CheckBox)convertView.findViewById(R.id.todo_check)).setChecked(true);break;
            default: break;
        }
        return convertView;
    }

    public void setIds(ArrayList<Integer> ids) {
        this.ids = ids;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public void setDates(ArrayList<Date> dates) {
        this.dates = dates;
    }

    public void setChecks(ArrayList<Integer> checked) {
        this.checks = checked;
    }
}
