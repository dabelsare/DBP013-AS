package com.example.smp.aquasmart;

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


/**
 * Created by Dipak on 6/3/2017.
 */
    public class CustomAdapterCustomerList extends BaseAdapter implements Filterable {
    Context context;
    LayoutInflater inflter;
    List<Customer> valueList;
    private List<Customer> mDataFiltered;//the filtered data

    public CustomAdapterCustomerList(List<Customer> listValue, Context context) {
        super();
        this.context = context;
        this.valueList = listValue;
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
        view = inflter.inflate(R.layout.list_adapter_view, null);
        //TextView cust_id = (TextView) view.findViewById(R.id.txtVHeaderCID);
        TextView c_name = (TextView) view.findViewById(R.id.txtVCName);
        TextView c_address = (TextView) view.findViewById(R.id.txtVCAddress);
        TextView c_mobile = (TextView) view.findViewById(R.id.txtVMobile);
        TextView c_deposit = (TextView) view.findViewById(R.id.txtVDeposit);

        //cust_id.setText(valueList.get(i).customer_id);
        c_name.setText(valueList.get(i).name);
        c_address.setText(valueList.get(i).address);
        c_mobile.setText(valueList.get(i).mobile);
        c_deposit.setText(valueList.get(i).deposit);
        return view;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();
                if (constraint == null || constraint.length() == 0) {
                    //no constraint given, just return all the data. (no search)
                    results.count = valueList.size();
                    results.values = valueList;
                } else {//do the search
                    List<Customer> resultsData = new ArrayList<>();
                    String searchStr = constraint.toString().toUpperCase();
                    for (Customer o : valueList)
                        if (o.name.toUpperCase().startsWith(searchStr)) resultsData.add(o);
                    results.count = resultsData.size();
                    results.values = resultsData;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mDataFiltered = (ArrayList<Customer>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}