package com.example.smp.aquasmart;

/**
 * Created by Dipak on 6/2/2017.
 */
public class TransactionClass {
    public String transaction_id,date;
    public String customer_name;
    public String mobile;
    public String balance,d_jar_type,d_qty,r_jar_type,r_qty;
    public String remain_jar, remain_bottle;

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    public String getD_jar_type() {
        return d_jar_type;
    }

    public void setD_jar_type(String d_jar_type) {
        this.d_jar_type = d_jar_type;
    }

    public String getD_qty() {
        return d_qty;
    }

    public void setD_qty(String d_qty) {
        this.d_qty = d_qty;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public String getR_jar_type() {
        return r_jar_type;
    }


    public void setR_jar_type(String r_jar_type) {
        this.r_jar_type = r_jar_type;
    }


    public String getR_qty() {
        return r_qty;
    }

    public void setR_qty(String r_qty) {
        this.r_qty = r_qty;
    }

    public String getRemain_jar() {
        return remain_jar;
    }

    public void setRemain_jar(String remain_jar) {
        this.remain_jar = remain_jar;
    }

    public String getRemain_bottle() {
        return remain_bottle;
    }

    public void setRemain_bottle(String remain_bottle) {
        this.remain_bottle = remain_bottle;
    }
}