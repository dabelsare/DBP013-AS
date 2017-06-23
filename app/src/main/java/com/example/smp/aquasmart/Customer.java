package com.example.smp.aquasmart;

/**
 * Created by Dipak on 5/16/2017.
 */
public class Customer {
    public String company_id;
    public String name;
    public String mobile;
    public String address,customer_id;
    public String deposit,balance,remain_jar,remain_bottle;

    public String getCustomer_id()
    {
        return customer_id;
    }

    public void setCustomer_id(String customer_id)
    {
        this.customer_id=customer_id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getMobile() {
        return mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getDeposit()
    {
        return deposit;
    }
    public void setDeposit(String deposit)
    {
        this.deposit=deposit;
    }

}