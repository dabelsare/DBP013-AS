package com.example.smp.aquasmart;

/**
 * Created by Dipak on 6/2/2017.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter implements Filterable{
    Context context;
    LayoutInflater inflter;
    List<TransactionClass> valueList;
    private List<TransactionClass> mDataTemp;//the temp data

    public CustomAdapter(List<TransactionClass> listValue, Context context) {
        super();
        this.context = context;
        this.valueList = listValue;
        this.mDataTemp=listValue;
        inflter = (LayoutInflater.from(context));
        getFilter();
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

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if(constraint!="" && constraint.length()>0) {
                    ArrayList<TransactionClass> filterList = new ArrayList<TransactionClass>();
                    for (int i = 0; i < valueList.size(); i++) {
                        if ((valueList.get(i).customer_name.toUpperCase())
                                .contains(constraint.toString().toUpperCase())) {
                            TransactionClass transactionClass = new TransactionClass();
//                            transactionClass.setTransaction_id(valueList.get(i).transaction_id);
                            transactionClass.setCustomer_name(valueList.get(i).customer_name);
                            transactionClass.setBalance(valueList.get(i).balance);
                            transactionClass.setRemain_jar(valueList.get(i).remain_jar);
                            transactionClass.setRemain_bottle(valueList.get(i).remain_bottle);
                            filterList.add(transactionClass);
                        }
                    }
                    results.count=filterList.size();
                    results.values=filterList;
                }else{
                    synchronized (this) {
                        results.count = mDataTemp.size();
                        results.values = mDataTemp;
                    }
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                valueList = (ArrayList<TransactionClass>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}