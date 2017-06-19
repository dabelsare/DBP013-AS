package com.example.smp.aquasmart;

/**
 * Created by Dipak on 6/2/2017.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;


public class CustomAdapter extends BaseAdapter {
    Context context;
    LayoutInflater inflter;
    List<TransactionClass> valueList;
    public CustomAdapter(List<TransactionClass> listValue, Context context) {
        this.context = context;
        this.valueList =listValue;
        inflter = (LayoutInflater.from(context));
    }

    @Override
    public int getCount() {
        return this.valueList.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.transaction_listview, null);
        //TextView trans_id = (TextView) view.findViewById(R.id.textView);
        TextView name = (TextView) view.findViewById(R.id.textViewName);
        //TextView mobile = (TextView) view.findViewById(R.id.textViewMobile);
        TextView balance = (TextView) view.findViewById(R.id.textViewBalance);
        TextView jar = (TextView) view.findViewById(R.id.txtVRemainJar);
        TextView bottle = (TextView) view.findViewById(R.id.txtVRemainBottle);

        //trans_id.setText(valueList.get(i).transaction_id);
        name.setText(valueList.get(i).customer_name);
        //mobile.setText(valueList.get(i).mobile);
        balance.setText(valueList.get(i).balance);
        jar.setText(valueList.get(i).remain_jar);
        bottle.setText(valueList.get(i).remain_bottle);
        return view;
    }
}