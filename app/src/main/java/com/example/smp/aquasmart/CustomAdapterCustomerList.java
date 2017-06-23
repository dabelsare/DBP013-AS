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
    private List<Customer> mDataTemp;//the temp data

    public CustomAdapterCustomerList(List<Customer> listValue, Context context) {
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
                if(constraint!="" && constraint.length()>0) {
                    ArrayList<Customer> filterList = new ArrayList<Customer>();
                    for (int i = 0; i < valueList.size(); i++) {
                        if ((valueList.get(i).name.toUpperCase())
                                .contains(constraint.toString().toUpperCase())) {
                            Customer contacts = new Customer();
                            contacts.setCustomer_id(valueList.get(i).customer_id);
                            contacts.setName(valueList.get(i).name);
                            contacts.setAddress(valueList.get(i).address);
                            contacts.setMobile(valueList.get(i).mobile);
                            contacts.setDeposit(valueList.get(i).deposit);
                            filterList.add(contacts);
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
                valueList = (ArrayList<Customer>) results.values;
                notifyDataSetChanged();
            }
        };
    }
}