package com.example.smp.aquasmart;

/**
 * Created by Dipak on 6/2/2017.
 */
public class TransactionClass {
    public String transaction_id;
    public String customer_name;
    public String mobile;
    public String balance;
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

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
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