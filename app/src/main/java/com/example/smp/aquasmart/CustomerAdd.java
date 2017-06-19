package com.example.smp.aquasmart;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CustomerAdd extends AppCompatActivity {
    EditText FullName, Mobile, Address,Deposit;
    Button AddCus,btnCus,btnLogOut,btnTrans,btnClr;
    String UserName="Varad",c_id="",c_name,c_address,c_mobile,c_deposit;
    TextView txtUname;
    String URL1= "http://smartbizit.com/aquasmart/customer_add.php";
    JSONParser jsonParser = new JSONParser();
    int i=0;
    String compid ="1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_customer);

        FullName=(EditText)findViewById(R.id.edtName);
        Mobile=(EditText)findViewById(R.id.edtMobile);
        Address=(EditText)findViewById(R.id.edtAddress);
        Deposit=(EditText)findViewById(R.id.edtDeposit);
        AddCus=(Button)findViewById(R.id.btnNewCustomer);
        btnCus=(Button)findViewById(R.id.btnCustomers);
        btnLogOut=(Button)findViewById(R.id.btnLogout);
        btnTrans=(Button)findViewById(R.id.btnTransaction);
        btnClr=(Button)findViewById(R.id.btnClrCustomer);
        txtUname=(TextView)findViewById(R.id.txtUsername);
        Intent i=getIntent();
        //UserName=i.getStringExtra("UserName");


        c_id = i.getStringExtra("customerId");
        txtUname.setText("Welcome "+UserName);

        if(c_id!=""){
            c_name = i.getStringExtra("c_name");
            c_address = i.getStringExtra("address");
            c_mobile = i.getStringExtra("mobile");
            c_deposit = i.getStringExtra("deposit");

            FullName.setText(c_name);
            Mobile.setText(c_mobile);
            Address.setText(c_address);
            Deposit.setText(c_deposit);
        }

        btnCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CustomerAdd.this,MainActivity.class);
                startActivity(i);
            }
        });
        btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CustomerAdd.this,LoginActivity.class);
                startActivity(i);
            }
        });
        btnTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(CustomerAdd.this,Transaction_List.class);
                startActivity(i);
            }
        });

        AddCus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               //Toast.makeText(getApplicationContext(),"This is click"+c_id,Toast.LENGTH_LONG).show();
               AddCustomer addCus= new AddCustomer();
               addCus.execute(FullName.getText().toString(),Mobile.getText().toString(),
                       Address.getText().toString(),Deposit.getText().toString(),compid,c_id);
            }
        });

        btnClr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FullName.setText("");
                Deposit.setText("");
                Mobile.setText("");
                Address.setText("");
                c_id="";
            }
        });
    }

    private class AddCustomer extends AsyncTask<String,String,JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String[] args) {
            String Cust= args[5];
            String Com= args[4];
            String dip = args[3];
            String add = args[2];
            String mob = args[1];
            String name= args[0];
            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("name", name));
            params.add(new BasicNameValuePair("address", add));
            params.add(new BasicNameValuePair("mobile", mob));
            params.add(new BasicNameValuePair("deposit", dip));
            params.add(new BasicNameValuePair("company_id",Com));
            params.add(new BasicNameValuePair("customer_id",Cust));

            JSONObject json = jsonParser.makeHttpRequest(URL1, "POST", params);
            return json;
        }

        protected void onPostExecute(JSONObject result) {

            // dismiss the dialog once product deleted
            try {
                if (result.getString("success").equals("null")) {
                    Toast.makeText(getApplicationContext(), result.getString("message"), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(),result.getString("message"),Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getApplicationContext(),CustomerAdd.class);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}