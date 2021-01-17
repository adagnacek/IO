package komiii.dor.organisr.containers;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import komiii.dor.organisr.R;
import komiii.dor.organisr.activities.CalendarActivity;
import komiii.dor.organisr.activities.GoalsActivity;
import komiii.dor.organisr.activities.RemindersActivity;
import komiii.dor.organisr.activities.ShoppingActivity;
import komiii.dor.organisr.activities.TodosActivity;
import komiii.dor.organisr.adapters.CalendarView;

public class DialogMaker extends DialogFragment {

    int layoutId;
    View taggs;

    public static DialogMaker newInstance(int layout) {
        DialogMaker dialog = new DialogMaker();

        dialog.layoutId = layout;

        return dialog;
    }

    public static DialogMaker newInstance(int layout, View tagges) {
        DialogMaker dialog = new DialogMaker();

        dialog.layoutId = layout;
        dialog.taggs = tagges;

        return dialog;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        View v = inflater.inflate(layoutId, container, false);
        switch (layoutId) {
            case R.layout.dialog_reminders_add:
                v = remindersAdd(v);
                break;
            case R.layout.dialog_reminders_edit:
                v = remindersEdit(v);
                break;
            case R.layout.dialog_reminders_delete:
                v = remindersDelete(v);
                break;
            case R.layout.dialog_shopping_productlist_basket:
            case R.layout.dialog_shopping_wishlist_basket:
                v = shoppingwishlistBasket(v);
                break;
            case R.layout.dialog_shopping_productlist_add:
                v = shoppingAddProduct(v,0);
                break;
            case R.layout.dialog_shopping_wishlist_add:
                v = shoppingAddProduct(v,1);
                break;
            case R.layout.dialog_shopping_productlist_edit:
            case R.layout.dialog_shopping_wishlist_edit:
                v = shoppingEditProduct(v);
                break;
            case R.layout.dialog_product_delete:
                v = shoppingDeleteProduct(v);
                break;
            case R.layout.dialog_shopping_basket_edit:
                v = shoppingEditBasket(v);
                break;
            case R.layout.dialog_calendar_event_edit:
                v = calendarEventView(v);
                break;
            case R.layout.dialog_cal_decancel:
                v = calendarDecancel(v);
                break;
            case R.layout.dialog_cal_add_event:
                v = calendarAddEvent(v);
                break;
            case R.layout.dialog_calendar_event_delete:
                v = calendarDeleteEvent(v);
                break;
            case R.layout.dialog_todos_add:
                v = todosAdd(v);
                break;
            case R.layout.dialog_todos_edit:
                v = todosEdit(v);
                break;
            case R.layout.dialog_todos_add_tomorrow:
                v = todosAddTomorrow(v);
                break;
            case R.layout.dialog_todos_delete:
                v = todosDelete(v);
                break;
            case R.layout.dialog_goals_add:
                v = goalsAdd(v);
                break;
            case R.layout.dialog_goals_increase:
                v = goalsIncrease(v);
                break;
            case R.layout.dialog_goals_edit:
                v = goalsEdit(v);
                break;
            case R.layout.dialog_goals_delete:
                v = goalsDelete(v);
                break;
        }


        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        return v;
    }

    private View remindersAdd(final View v) {
        Button button = (Button) v.findViewById(R.id.cancel_reminders_butt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button button2 = (Button) v.findViewById(R.id.add_reminders_butt);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vx) {
                int radioChoice;
                switch (((RadioGroup) v.findViewById(R.id.addRadioGroup)).getCheckedRadioButtonId()) {
                    case R.id.ideaButton:
                        radioChoice = 0;
                        break;
                    case R.id.oweButton:
                        radioChoice = 1;
                        break;
                    case R.id.tofixButton:
                        radioChoice = 2;
                        break;
                    default:
                        radioChoice = 0;
                        break;
                }
                BoilerFunctions.update(getContext(), "INSERT INTO reminders VALUES (NULL,'" + ((EditText) v.findViewById(R.id.reminderContent)).getText() + "'," + radioChoice + ");");
                ((RemindersActivity) getActivity()).refresh();
                Toast.makeText(getContext(), "Reminder added", Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });
        return v;
    }

    private View remindersEdit(final View v) {
        Button button = (Button) v.findViewById(R.id.cancel_reminders_butt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button button2 = (Button) v.findViewById(R.id.add_reminders_butt);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vx) {
                int radioChoice;
                switch (((RadioGroup) v.findViewById(R.id.addRadioGroup)).getCheckedRadioButtonId()) {
                    case R.id.ideaButton:
                        radioChoice = 0;
                        break;
                    case R.id.oweButton:
                        radioChoice = 1;
                        break;
                    case R.id.tofixButton:
                        radioChoice = 2;
                        break;
                    default:
                        radioChoice = 0;
                        break;
                }
                BoilerFunctions.update(getContext(), "UPDATE REMINDERS SET reminder='" + ((EditText) v.findViewById(R.id.reminderContent)).getText() + "', type=" + radioChoice + " where id=" + taggs.getTag(R.string.idOne));
                ((RemindersActivity) getContext()).refresh();
                Toast.makeText(getContext(), "Reminder updated: " + ((TextView) v.findViewById(R.id.reminderContent)).getText(), Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });
        ((TextView) v.findViewById(R.id.dialog_remedit_title)).setText("Editing: \"" + taggs.getTag(R.string.idTwo) + "\"");
        ((EditText) v.findViewById(R.id.reminderContent)).setText((String) taggs.getTag(R.string.idTwo));
        RadioGroup radio = ((RadioGroup) v.findViewById(R.id.addRadioGroup));
        switch ((int) taggs.getTag(R.string.idThree)) {
            case 0:
                radio.check(R.id.ideaButton);
                break;
            case 1:
                radio.check(R.id.oweButton);
                break;
            case 2:
                radio.check(R.id.tofixButton);
                break;
        }
        return v;
    }

    public View remindersDelete(View v){
        Button button = (Button) v.findViewById(R.id.cancel_reminders_butt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button button2 = (Button) v.findViewById(R.id.add_reminders_butt);
        button2.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                BoilerFunctions.update(getContext(),BoilerEnum.reminderDeleteQuery.getContent()+((View)taggs.getParent()).getTag(R.string.idOne));
                ((RemindersActivity)getActivity()).refresh();
                Toast.makeText(getContext(),"Reminder deleted: "+((View)taggs.getParent()).getTag(R.string.idTwo),Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });
        ((TextView)v.findViewById(R.id.remdelcont)).setText((String) ((View)taggs.getParent()).getTag(R.string.idTwo));
        return v;
    }

    private View shoppingwishlistBasket(final View v) {
        Button button = (Button) v.findViewById(R.id.cancel_reminders_butt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button button2 = (Button) v.findViewById(R.id.basket_wishlist_butt);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vx) {
                try {
                    int x = Integer.parseInt(((EditText) v.findViewById(R.id.product_Quantity)).getText().toString());
                    if (x >= 0) {
                        BoilerFunctions.update(getContext(), "UPDATE PRODUCTS SET QUANTITY = " + x + " WHERE (`id` =" + taggs.getTag(R.string.idOne) + ");");
                    }
                    ((ShoppingActivity) getActivity()).refreshFragment();
                    Toast.makeText(getContext(), "Product added to basket", Toast.LENGTH_LONG).show();
                    getDialog().dismiss();
                } catch (Exception e) {
                    Log.e("Error", e.getMessage());
                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_LONG).show();
                    getDialog().dismiss();
                }
            }
        });

        return v;
    }

    private View shoppingAddProduct(final View v,final int wishlistStatus) {

        Button button = (Button) v.findViewById(R.id.cancel_reminders_butt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button button2 = (Button) v.findViewById(R.id.basket_wishlist_butt);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vx) {
                BoilerFunctions.update(getContext(), "INSERT INTO products VALUES ( NULL,'" + ((EditText) v.findViewById(R.id.dialog_shopping_productlist_edit_name)).getText().toString() + "', " +
                        ((Spinner) v.findViewById(R.id.dialog_shopping_productlist_edit_type)).getSelectedItemId() + ", " +
                        ((EditText) v.findViewById(R.id.dialog_shopping_productlist_edit_pmin)).getText().toString() + ", " +
                        ((EditText) v.findViewById(R.id.dialog_shopping_productlist_edit_pmax)).getText().toString() + ","+wishlistStatus+",0);");
                ((ShoppingActivity) getActivity()).refreshFragment();
                Toast.makeText(getContext(), "Product added", Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });
        ArrayList<String> types = new ArrayList<>();
        types.add((String) getText(R.string.ShopType1));
        types.add((String) getText(R.string.ShopType2));
        types.add((String) getText(R.string.ShopType3));
        types.add((String) getText(R.string.ShopType4));
        types.add((String) getText(R.string.ShopType5));
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.shopping_spinneritem_one,R.id.shopping_spinneritem_content,types);
        ((Spinner) v.findViewById(R.id.dialog_shopping_productlist_edit_type)).setAdapter(spinnerAdapter);
        ((Spinner) v.findViewById(R.id.dialog_shopping_productlist_edit_type)).setSelection(0);
        return v;
    }

    private View shoppingEditProduct(final View v) {
        Button button = (Button) v.findViewById(R.id.cancel_reminders_butt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button button2 = (Button) v.findViewById(R.id.basket_wishlist_butt);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vx) {
                BoilerFunctions.update(getContext(),"UPDATE products SET NAME ='"+ ((EditText)v.findViewById(R.id.dialog_shopping_productlist_edit_name)).getText().toString() +"', category ="+
                        ((Spinner) v.findViewById(R.id.dialog_shopping_productlist_edit_type)).getSelectedItemId()+", pricemin ="+
                        ((EditText)v.findViewById(R.id.dialog_shopping_productlist_edit_pmin)).getText().toString()+", pricemax = "+
                        ((EditText)v.findViewById(R.id.dialog_shopping_productlist_edit_pmax)).getText().toString()+" WHERE (id = "+
                        taggs.getTag(R.string.idOne)+");");
                ((ShoppingActivity)getActivity()).refreshFragment();
                Toast.makeText(getContext(),"Product edited",Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });
        ArrayList<String> types = new ArrayList<>();
        types.add((String) getText(R.string.ShopType1));
        types.add((String) getText(R.string.ShopType2));
        types.add((String) getText(R.string.ShopType3));
        types.add((String) getText(R.string.ShopType4));
        types.add((String) getText(R.string.ShopType5));
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(getContext(), R.layout.shopping_spinneritem_one,R.id.shopping_spinneritem_content,types);
        ((Spinner) v.findViewById(R.id.dialog_shopping_productlist_edit_type)).setAdapter(spinnerAdapter);
        ((TextView) v.findViewById(R.id.dialog_shopping_productlist_edit_name)).setText((String)taggs.getTag(R.string.idTwo));
        ((Spinner) v.findViewById(R.id.dialog_shopping_productlist_edit_type)).setAdapter(spinnerAdapter);((Spinner) v.findViewById(R.id.dialog_shopping_productlist_edit_type)).setSelection((int)taggs.getTag(R.string.idSix));
        ((EditText) v.findViewById(R.id.dialog_shopping_productlist_edit_pmin)).setText(""+(Double)taggs.getTag(R.string.idFour));
        ((EditText) v.findViewById(R.id.dialog_shopping_productlist_edit_pmax)).setText(""+(Double)taggs.getTag(R.string.idFive));
        return v;
    }

    private View shoppingDeleteProduct(final View v){
        Button button = (Button) v.findViewById(R.id.cancel_reminders_butt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button button2 = (Button) v.findViewById(R.id.add_reminders_butt);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vx) {
                BoilerFunctions.update(getContext(),BoilerEnum.shoppingBasketDoneSecondQuerry.getContent()+((View)((View)taggs.getParent()).getParent()).getTag(R.string.idOne)+")");
                Toast.makeText(getContext(),((String)((View)((View)taggs.getParent()).getParent()).getTag(R.string.idTwo))+": Removed",Toast.LENGTH_LONG).show();
                ((ShoppingActivity)getActivity()).refreshFragment();
                getDialog().dismiss();
            }
        });
        ((TextView)v.findViewById(R.id.remdelcont)).setText((String)((View)((View)taggs.getParent()).getParent()).getTag(R.string.idTwo));
        return v;
    }

    private View shoppingEditBasket(final View v){
        Button button = (Button) v.findViewById(R.id.cancel_reminders_butt);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button button2 = (Button) v.findViewById(R.id.basket_wishlist_butt);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vx) {
                int x = Integer.parseInt(((EditText)v.findViewById(R.id.product_Quantity)).getText().toString());
                if (x >= 0) {
                    BoilerFunctions.update(getContext(),"UPDATE PRODUCTS SET QUANTITY = "+x+" WHERE (`id` =" + taggs.getTag(R.string.idOne)  + ");");
                }
                ((ShoppingActivity)getActivity()).refreshFragment();
                Toast.makeText(getContext(),"Product edited",Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });
        ((TextView)v.findViewById(R.id.product_Name)).setText((String)taggs.getTag(R.string.idTwo));
        ((EditText)v.findViewById(R.id.product_Quantity)).setText(""+(Integer)taggs.getTag(R.string.idFour));
        return v;
    }

    private View calendarEventView(final View v){
        DateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,((Date)taggs.getTag(R.string.idFour)).getHours());
        calendar.set(Calendar.MINUTE,((Date)taggs.getTag(R.string.idFour)).getMinutes());
        String startTime = dateFormat.format(calendar.getTime());
        calendar.add(Calendar.MINUTE,(int)taggs.getTag(R.string.idFive));
        int repUnit = 0;
        switch (((String)taggs.getTag(R.string.idSeven)).charAt(0)){
            case 'w' : repUnit = 1; break;
            case 'm' : repUnit = 2; break;
            case 'y' : repUnit = 3; break;
        }

        ((EditText) v.findViewById(R.id.dialog_events_name)).setText((String)taggs.getTag(R.string.idTwo));
        ((EditText) v.findViewById(R.id.dialog_events_starthour)).setText(startTime);
        ((EditText) v.findViewById(R.id.dialog_events_endhour)).setText(dateFormat.format(calendar.getTime()));
        ((EditText) v.findViewById(R.id.dialog_events_repeatance)).setText(""+((int) taggs.getTag(R.string.idSix)));
        ((Spinner) v.findViewById(R.id.dialog_events_repeatanceunit)).setSelection(repUnit);
        ((EditText) v.findViewById(R.id.dialog_events_urgency)).setText(""+((int) taggs.getTag(R.string.idEight)));
        ((EditText) v.findViewById(R.id.dialog_events_description)).setText((String) taggs.getTag(R.string.idNine));

        //Spinner
        Spinner spinner = (Spinner) v.findViewById(R.id.dialog_events_repeatanceunit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.repeatance_units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //
        Button exit = (Button) v.findViewById(R.id.exit_event_view);
        exit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button cancel = (Button) v.findViewById(R.id.cancel_event_view);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                BoilerFunctions.update(getContext(),"INSERT INTO EVENTS_CANCELS (event_id,cancel_date) VALUES ("+taggs.getTag(R.string.idOne)+",'"+BoilerFunctions.dateFormat.format(((CalendarView)((CalendarActivity)getContext()).findViewById(R.id.calendar_topPart)).calendarSelected)+"')");
                ((CalendarView)((CalendarActivity)getContext()).findViewById(R.id.calendar_topPart)).update();
                getDialog().dismiss();
            }
        });

        Button save = v.findViewById(R.id.save_event_view);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vx) {
                try {
                    String time1 = ((EditText) v.findViewById(R.id.dialog_events_starthour)).getText().toString();
                    String time2 = ((EditText) v.findViewById(R.id.dialog_events_endhour)).getText().toString();

                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    Date date1 = format.parse(time1);
                    Date date2 = format.parse(time2);
                    long minDifference = (date2.getTime() - date1.getTime())/(1000*60);

                    String option = "d";
                    switch ((int)((Spinner)v.findViewById(R.id.dialog_events_repeatanceunit)).getSelectedItemId()){
                        case 1: option = "w"; break;
                        case 2: option = "m"; break;
                        case 3: option = "y"; break;
                    }

                    BoilerFunctions.update(getContext(),
                            "UPDATE EVENTS SET name='"+((EditText) v.findViewById(R.id.dialog_events_name)).getText()+
                            "', date='"+BoilerFunctions.dateFormat.format(((CalendarView) ((CalendarActivity) getContext()).findViewById(R.id.calendar_topPart)).calendarSelected) +
                            "', hour='"+((EditText) v.findViewById(R.id.dialog_events_starthour)).getText()+
                            "', duration="+minDifference+
                            ", repeatance="+((EditText)v.findViewById(R.id.dialog_events_repeatance)).getText().toString()+
                            ", repeatance_unit='"+option+
                            "', urgency="+((EditText)v.findViewById(R.id.dialog_events_urgency)).getText().toString()+
                            ", description='"+((EditText)v.findViewById(R.id.dialog_events_description)).getText().toString()+
                            "' WHERE id=" +  taggs.getTag(R.string.idOne)
                    );
                    ((CalendarView)((CalendarActivity)getContext()).findViewById(R.id.calendar_topPart)).update();
                    getDialog().dismiss();
                }catch (ParseException e){e.printStackTrace();}
            }
        });

        return v;
    }

    private View calendarDecancel(final View v){
        Button buttonNo = (Button) v.findViewById(R.id.cal_question_decancel_no);
        buttonNo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button buttonYes = (Button) v.findViewById(R.id.cal_question_decancel_yes);
        buttonYes.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ((CalendarView)((CalendarActivity)getContext()).findViewById(R.id.calendar_topPart)).decancel();
                getDialog().dismiss();
            }
        });

        TextView question = v.findViewById(R.id.cal_question_decancel);
        question.setText("Are you sure you want to remove cancels for: "+BoilerFunctions.dateFormat.format(((CalendarView)((CalendarActivity)getContext()).findViewById(R.id.calendar_topPart)).calendarSelected)+"?");

        return v;
    }

    private View calendarAddEvent(final View v){
        //Spinner
        Spinner spinner = (Spinner) v.findViewById(R.id.dialog_events_repeatanceunit);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.repeatance_units_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        //

        Button cancel = v.findViewById(R.id.cancel_add_event);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        Button buttonAdd = (Button) v.findViewById(R.id.add_event);
        buttonAdd.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vx) {
                try {
                    String time1 = ((EditText) v.findViewById(R.id.dialog_events_starthour)).getText().toString();
                    String time2 = ((EditText) v.findViewById(R.id.dialog_events_endhour)).getText().toString();

                    SimpleDateFormat format = new SimpleDateFormat("HH:mm");
                    Date date1 = format.parse(time1);
                    Date date2 = format.parse(time2);
                    long minDifference = (date2.getTime() - date1.getTime())/(1000*60);

                    String option = "d";
                    switch ((int)((Spinner)v.findViewById(R.id.dialog_events_repeatanceunit)).getSelectedItemId()){
                        case 1: option = "w"; break;
                        case 2: option = "m"; break;
                        case 3: option = "y"; break;
                    }

                    BoilerFunctions.update(getContext(),
                            "INSERT INTO EVENTS (name, date, hour, duration, repeatance, repeatance_unit, urgency, description) VALUES ('" + ((EditText) v.findViewById(R.id.dialog_events_name)).getText() + "','" +
                                    BoilerFunctions.dateFormat.format(((CalendarView) ((CalendarActivity) getContext()).findViewById(R.id.calendar_topPart)).calendarSelected) + "','" +
                                    ((EditText) v.findViewById(R.id.dialog_events_starthour)).getText()+"',"+
                                    minDifference+","+
                                    ((EditText)v.findViewById(R.id.dialog_events_repeatance)).getText().toString()+",'"+
                                    option+"',"+
                                    ((EditText)v.findViewById(R.id.dialog_events_urgency)).getText().toString()+",'"+
                                    ((EditText)v.findViewById(R.id.dialog_events_description)).getText().toString()+"')"
                    );
                    ((CalendarView)((CalendarActivity)getContext()).findViewById(R.id.calendar_topPart)).update();
                    getDialog().dismiss();
                }catch (ParseException e){e.printStackTrace();}
            }
        });
        return v;
    }

    private View calendarDeleteEvent(final View v) {
        TextView header = v.findViewById(R.id.delete_header);
        header.setText("Do you want to delete: " + taggs.getTag(R.string.idTwo) + "?");

        Button cancel = v.findViewById(R.id.cancel_event_view);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        Button buttonDelete = v.findViewById(R.id.delete_event_view);
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoilerFunctions.update(getContext(), "DELETE FROM EVENTS WHERE ID=" + taggs.getTag(R.string.idOne));
                ((CalendarView) ((CalendarActivity) getContext()).findViewById(R.id.calendar_topPart)).update();
                getDialog().dismiss();
            }
        });
        return v;
    }

    private View todosAdd(final View v){

        Button cancel = v.findViewById(R.id.cancel_todos_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        Button add = v.findViewById(R.id.add_todos_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vx) {
                BoilerFunctions.update(getContext(),"INSERT INTO todos (name,date) values ('"+((EditText)v.findViewById(R.id.todoContent)).getText()+"',CURDATE())");
                ((TodosActivity)getActivity()).refreshFragment();
                getDialog().dismiss();
            }
        });

        return v;
    }

    private View todosEdit(final View v){

        Button cancel = v.findViewById(R.id.cancel_todos_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        Button add = v.findViewById(R.id.add_todos_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vx) {
                BoilerFunctions.update(getContext(),"UPDATE todos set name='"+((EditText)v.findViewById(R.id.todoContent)).getText()+"' where id="+taggs.getTag(R.string.idOne));
                ((TodosActivity)getActivity()).refreshFragment();
                getDialog().dismiss();
            }
        });
        ((EditText)v.findViewById(R.id.todoContent)).setText((String)taggs.getTag(R.string.idTwo));

        return v;
    }
    private View todosAddTomorrow(final View v){

        Button cancel = v.findViewById(R.id.cancel_todos_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        Button add = v.findViewById(R.id.add_todos_button);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vx) {
                BoilerFunctions.update(getContext(),"INSERT INTO todos (name,date) values ('"+((EditText)v.findViewById(R.id.todoContent)).getText()+"',ADDDATE(CURDATE(),1))");
                ((TodosActivity)getActivity()).refreshFragment();
                getDialog().dismiss();
            }
        });

        return v;
    }

    private View todosDelete(final View v){
        ((TextView)v.findViewById(R.id.todoDelName)).setText(((String)taggs.getTag(R.string.idTwo)));
        Button cancel = v.findViewById(R.id.cancel_todos_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vx) {
                getDialog().dismiss();
            }
        });
        Button delete = v.findViewById(R.id.delete_todos_button);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View vx) {
                BoilerFunctions.update(getContext(),"DELETE FROM TODOS WHERE ID="+((int)taggs.getTag(R.string.idOne)));
                ((TodosActivity)getActivity()).refreshFragment();
                getDialog().dismiss();
            }
        });
        return v;
    }

    private View goalsAdd(final View v){
        ArrayList<String> types = new ArrayList<>();
        types.add((String) getText(R.string.GoalType1));
        types.add((String) getText(R.string.GoalType2));
        types.add((String) getText(R.string.GoalType3));
        types.add((String) getText(R.string.GoalType4));
        types.add((String) getText(R.string.GoalType5));
        ArrayAdapter<String> typesSpinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, types);
        ArrayList<String> units = new ArrayList<>();
        units.add("Day");
        units.add("Week");
        units.add("Month");
        units.add("Year");
        ArrayAdapter<String> unitsSpinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, units);

        v.findViewById(R.id.cancel_goals_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vx) {
                getDialog().dismiss();
            }
        });

        v.findViewById(R.id.add_goals_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vx) {
                String s = ((DatePicker)v.findViewById(R.id.dialog_goals_datePicker)).getYear()+"-"+(((DatePicker)v.findViewById(R.id.dialog_goals_datePicker)).getMonth()+1)+"-"+((DatePicker)v.findViewById(R.id.dialog_goals_datePicker)).getDayOfMonth();
                BoilerFunctions.update(getContext(),"INSERT INTO goals VALUES (NULL,'"+((EditText)v.findViewById(R.id.dialog_goals_name)).getText()+"',"+((Spinner)v.findViewById(R.id.goals_spinner)).getSelectedItemId() +",'"+s+"',"+((EditText)v.findViewById(R.id.dialog_goals_frequency)).getText()+","+((EditText)v.findViewById(R.id.dialog_goals_goal)).getText()+",0,'"+((Spinner) v.findViewById(R.id.dialog_goals_repeatanceunit)).getSelectedItem().toString().toLowerCase().substring(0,1)+"');");
                ((GoalsActivity)getActivity()).refresh();
                Toast.makeText(getContext(),"Goal added",Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });
        ((Spinner) v.findViewById(R.id.goals_spinner)).setAdapter(typesSpinnerAdapter);
        ((Spinner) v.findViewById(R.id.dialog_goals_repeatanceunit)).setAdapter(unitsSpinnerAdapter);
        return v;
    }

    private View goalsIncrease(final View v){
        v.findViewById(R.id.cancel_goals_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        v.findViewById(R.id.increase_goals_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vx) {
                int i = (int)taggs.getTag(R.string.idSeven)+Integer.parseInt(((EditText)v.findViewById(R.id.dialog_goal_progress)).getText().toString());
                BoilerFunctions.update(getContext(),"UPDATE goals SET PROGRESS="+i+" WHERE (id = "+ taggs.getTag(R.string.idOne)+");");
                ((GoalsActivity)getContext()).refresh();
                Toast.makeText(getContext(),"Progressed",Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });
        return v;
    }

    private View goalsEdit(final View v){
        ArrayList<String> types = new ArrayList<>();
        types.add((String) getText(R.string.GoalType1));
        types.add((String) getText(R.string.GoalType2));
        types.add((String) getText(R.string.GoalType3));
        types.add((String) getText(R.string.GoalType4));
        types.add((String) getText(R.string.GoalType5));
        ArrayAdapter<String> typesSpinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, types);
        ArrayList<String> units = new ArrayList<>();
        units.add("Day");
        units.add("Week");
        units.add("Month");
        units.add("Year");
        ArrayAdapter<String> unitsSpinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, units);

        v.findViewById(R.id.cancel_goals_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });

        v.findViewById(R.id.edit_goals_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vx) {
                BoilerFunctions.update(getContext(),"UPDATE goals SET NAME ='"+ ((EditText)v.findViewById(R.id.dialog_goals_name)).getText() +"', type ="+
                        ((Spinner) v.findViewById(R.id.goals_spinner)).getSelectedItemId()+", start_date ='"+
                        ((DatePicker)v.findViewById(R.id.dialog_goals_datePicker)).getYear()+"-"+(((DatePicker)v.findViewById(R.id.dialog_goals_datePicker)).getMonth()+1)+"-"+((DatePicker)v.findViewById(R.id.dialog_goals_datePicker)).getDayOfMonth()+"', `interval`='"+
                        ((EditText)v.findViewById(R.id.dialog_goals_frequency)).getText()+"', goal='"+
                        ((EditText)v.findViewById(R.id.dialog_goals_goal)).getText()+"', interval_unit='"+
                        ((Spinner) v.findViewById(R.id.dialog_goals_repeatanceunit)).getSelectedItem().toString().toLowerCase().substring(0,1)+
                        "' WHERE (id = "+
                        taggs.getTag(R.string.idOne)+");");
                ((GoalsActivity)getContext()).refresh();
                Toast.makeText(getContext(),"Goal edited",Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });

        String[] date = taggs.getTag(R.string.idSix).toString().split("-");
        ((TextView) v.findViewById(R.id.dialog_goals_name)).setText((String)taggs.getTag(R.string.idTwo));
        ((Spinner) v.findViewById(R.id.goals_spinner)).setAdapter(typesSpinnerAdapter);((Spinner) v.findViewById(R.id.goals_spinner)).setSelection((int)taggs.getTag(R.string.idThree));
        ((TextView) v.findViewById(R.id.dialog_goals_frequency)).setText(taggs.getTag(R.string.idFour)+"");
        ((TextView) v.findViewById(R.id.dialog_goals_goal)).setText(taggs.getTag(R.string.idFive)+"");
        ((DatePicker)v.findViewById(R.id.dialog_goals_datePicker)).updateDate(Integer.parseInt(date[0]),Integer.parseInt(date[1])-1,Integer.parseInt(date[2]));
        ((Spinner) v.findViewById(R.id.dialog_goals_repeatanceunit)).setAdapter(unitsSpinnerAdapter);
        int position = 0;
        switch((String)taggs.getTag(R.string.idEight)){
            case "w":position=1;break;
            case "m":position=2;break;
            case "y":position=3;break;
        }
        ((Spinner) v.findViewById(R.id.dialog_goals_repeatanceunit)).setSelection(position);
        return v;
    }

    private View goalsDelete(final View v){
        ((TextView)v.findViewById(R.id.deleted_goal_content)).setText((String)taggs.getTag(R.string.idTwo));
        v.findViewById(R.id.cancel_goals_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDialog().dismiss();
            }
        });
        v.findViewById(R.id.delete_goals_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BoilerFunctions.update(getContext(),BoilerEnum.goalDeleteQuery.getContent()+taggs.getTag(R.string.idOne));
                ((GoalsActivity)getContext()).refresh();
                Toast.makeText(getContext(),"Goal deleted: "+taggs.getTag(R.string.idTwo),Toast.LENGTH_LONG).show();
                getDialog().dismiss();
            }
        });
        return v;
    }
}