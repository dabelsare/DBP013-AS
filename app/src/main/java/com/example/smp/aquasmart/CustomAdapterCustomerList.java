package com.example.smp.aquasmart;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Dipak on 6/3/2017.
 */
    public class CustomAdapterCustomerList extends BaseAdapter {
        Context context;
        LayoutInflater inflter;
        List<Customer> valueList;
        public CustomAdapterCustomerList(List<Customer> listValue, Context context) {
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
    }