package komiii.dor.organisr.adapters;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import komiii.dor.organisr.R;
import komiii.dor.organisr.containers.BoilerFunctions;


public class Reminders_List_Adapter extends ArrayAdapter {

    private ArrayList<Integer> resultId;
    private ArrayList<String> results;
    private ArrayList<Integer> resultType;
    Context context;

    public Reminders_List_Adapter(Context context, int resource, int txtResource, List<String> l) {
        super(context, resource, txtResource,  l);
        this.context = context;
    }

    public void setResultIds(ArrayList<Integer> result){
        resultId=result;
    }

    public void setCaptions(ArrayList<String> result){
        this.results=result;
    }

    public void setTypes(ArrayList<Integer> resultT){
        this.resultType=resultT;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        convertView = super.getView(position, convertView, parent);
        convertView.setTag(R.string.idOne,resultId.toArray()[position]);
        convertView.setTag(R.string.idTwo,results.toArray()[position]);
        convertView.setTag(R.string.idThree,resultType.toArray()[position]);
        final View cp = convertView;
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                BoilerFunctions.makeDialog(context,R.layout.dialog_reminders_edit,cp);
                return true;
            }
        });
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView text = (TextView)v.findViewById(R.id.reminders_item_text);
                if(text.getMaxLines()==1) {
                    text.setSingleLine(false);text.setEllipsize(null);}
                else{
                    text.setSingleLine(true);text.setEllipsize(TextUtils.TruncateAt.END);}
            }
        });

        switch((Integer)resultType.toArray()[position]){
            case 0: convertView.setBackground(context.getResources().getDrawable(R.drawable.shape_reminders_one)); break;
            case 1: convertView.setBackground(context.getResources().getDrawable(R.drawable.shape_reminders_two)); break;
            case 2: convertView.setBackground(context.getResources().getDrawable(R.drawable.shape_reminders_three)); break;
            default: break;
        }
        return convertView;
    }

}
