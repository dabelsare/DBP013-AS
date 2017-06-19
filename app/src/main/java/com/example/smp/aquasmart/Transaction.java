package com.example.smp.aquasmart;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.Calendar;

import android.app.DatePickerDialog;
import android.app.Dialog;

import android.widget.DatePicker;
import android.widget.Toast;

public class Transaction extends AppCompatActivity {
    EditText qty, rate,paymentReceived,
            paymentBalance,returnQty;
    Button saveTransaction,ClearTrans,Cus,Trans,LogOt;
    static final int Dialog_Id=0;
    String URL1= "http://smartbizit.com/aquasmart/transaction_add.php";
    JSONParser jsonParser = new JSONParser();
    int i=0;float amount=0;
    String customer_id ="1",user_id="1",amt="",jarType="Jar",
            payType="Cash",ReturnType="Jar",created_at;
    RadioGroup radioJarType,radioCashType,radioReturnType;
    RadioButton radioCash,radioCredit,radioReturnJar;

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView,c_nameV,balanceV,remain_jarV,remain_bottleV;
    private int year, month, day;
    String c_name,balance,remain_jar,remain_bottle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);

        dateView = (TextView) findViewById(R.id.textView3);
        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);

        //Spinner spinner = (Spinner) findViewById(R.id.jar_type_spinner);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.jar_type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        //spinner.setAdapter(adapter);


        ClearTrans=(Button)findViewById(R.id.btnTransClear);
        Cus=(Button)findViewById(R.id.btnCustomersinTrans);
        Trans=(Button)findViewById(R.id.btnTransactioninTrans);
        LogOt=(Button)findViewById(R.id.btnLogoutinTras);
        qty=(EditText)findViewById(R.id.edtQty);
        rate=(EditText)findViewById(R.id.edtRate);

        paymentReceived=(EditText)findViewById(R.id.edtPaymentReceived);
        paymentBalance=(EditText)findViewById(R.id.edtPaymentBalance);
        returnQty=(EditText)findViewById(R.id.edtReturnQty);
        saveTransaction=(Button)findViewById(R.id.btnTransactionSave);
        radioCash=(RadioButton)findViewById(R.id.rbtCash);
        radioCredit=(RadioButton)findViewById(R.id.rbtCredit);
        radioReturnJar=(RadioButton)findViewById(R.id.rbtRtnJar);
        Intent i=getIntent();

        customer_id=i.getStringExtra("customerId");
        c_name=i.getStringExtra("c_name");
        balance=i.getStringExtra("balance");
        remain_jar=i.getStringExtra("remain_jar");
        remain_bottle=i.getStringExtra("remain_bottle");

        c_nameV = (TextView) findViewById(R.id.txtCName);
        balanceV = (TextView) findViewById(R.id.txtBalance);
        remain_jarV = (TextView) findViewById(R.id.txtRemainJar);
        remain_bottleV = (TextView) findViewById(R.id.txtRemainBottle);

        c_nameV.setText(c_name);
        balanceV.setText(balance);
        remain_jarV.setText(remain_jar);
        remain_bottleV.setText(remain_bottle);

        saveTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String quantity=qty.getText().toString();
                String jar_rate=rate.getText().toString();
                amount=Integer.parseInt(quantity)*Integer.parseInt(jar_rate);
                amt=String.valueOf(amount);

                created_at = dateView.getText().toString();
                AddTransaction addTrans= new AddTransaction();
                addTrans.execute(customer_id,user_id,jarType,
                        qty.getText().toString(),rate.getText().toString(),
                        amt,payType,paymentReceived.getText().toString(),
                       paymentBalance.getText().toString(),
                        ReturnType,returnQty.getText().toString(),
                        created_at
                );
            }
        });

        paymentReceived.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (qty.getText().toString().trim().equals("")) {
                    qty.setError( "Quantity is required!" );
                }else
                if (rate.getText().toString().trim().equals("")) {
                    rate.setError( "Rate is required!" );
                }
                else
                if (paymentReceived.getText().toString().trim().equals("")) {
                    paymentReceived.setError( "Payment Received is required!" );
                }
                else {
                    Float Quantity = Float.parseFloat(qty.getText().toString());
                    Float RatePerQty = Float.parseFloat(rate.getText().toString());
                    Float PaymentReceived = Float.parseFloat(paymentReceived.getText().toString());
                    Float Tot = (Quantity * RatePerQty) - PaymentReceived;
                    amount=Tot;
                    paymentBalance.setText(Tot.toString());
                }
            }
        });

        radioJarType=(RadioGroup)findViewById(R.id.radioBtnGroupJarType);
        radioJarType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.rbtJar){
                    jarType="Jar";
                } else{
                    jarType="Bottle";
                }
            }
        });

        radioCashType=(RadioGroup)findViewById(R.id.radioPaymentType);
        radioCashType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.rbtCash){
                    payType="Cash";
                } else{
                    payType="Credit";
                }
            }
        });

        radioReturnType=(RadioGroup)findViewById(R.id.radioPaymentType);
        radioCashType.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if(i == R.id.rbtRtnJar){
                    ReturnType="Jar";
                } else{
                    ReturnType="Bottle";
                }
            }
        });
        Cus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Transaction.this,MainActivity.class);
                startActivity(i);
            }
        });
        LogOt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Transaction.this,LoginActivity.class);
                startActivity(i);
            }
        });
        Trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(Transaction.this,Transaction_List.class);
                startActivity(i);
            }
        });
        ClearTrans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                qty.setText("");
                rate.setText("");
                paymentReceived.setText("");
                paymentBalance.setText("");
                returnQty.setText("");
            }
        });


    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
        //Toast.makeText(getApplicationContext(), "ca",Toast.LENGTH_SHORT).show();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        // TODO Auto-generated method stub
        if (id == 999) {
            return new DatePickerDialog(this,
                    myDateListener, year, month, day);
        }
        return null;
    }

    private DatePickerDialog.OnDateSetListener myDateListener = new
            DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker arg0,
                                      int arg1, int arg2, int arg3) {
                    // TODO Auto-generated method stub
                    // arg1 = year
                    // arg2 = month
                    // arg3 = day
                    showDate(arg1, arg2+1, arg3);
                }
            };

    private void showDate(int year, int month, int day) {
        dateView.setText(new StringBuilder().append(day).append("/")
                .append(month).append("/").append(year));
    }

    private class AddTransaction extends AsyncTask<String,String,JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected JSONObject doInBackground(String[] args) {
            String trans_date = args[11];
            String return_qty = args[10];
            String return_jar_type = args[9];
            String payment_balance= args[8];
            String payment_received= args[7];
            String pay_type= args[6];
            String amt= args[5];
            String rate= args[4];
            String qty = args[3];
            String jarType = args[2];
            String user_id = args[1];
            String customer_id= args[0];

            ArrayList params = new ArrayList();
            params.add(new BasicNameValuePair("customer_id", customer_id));
            params.add(new BasicNameValuePair("user_id", user_id));
            params.add(new BasicNameValuePair("jar_type", jarType));
            params.add(new BasicNameValuePair("qty", qty));
            params.add(new BasicNameValuePair("rate",rate));
            params.add(new BasicNameValuePair("amount",amt));
            params.add(new BasicNameValuePair("pay_type",pay_type));
            params.add(new BasicNameValuePair("payment_received",payment_received));
            params.add(new BasicNameValuePair("payment_balance",payment_balance));
            params.add(new BasicNameValuePair("return_jar_type",return_jar_type));
            params.add(new BasicNameValuePair("return_qty",return_qty));
            params.add(new BasicNameValuePair("created_at",trans_date));

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
                    Intent intent = new Intent(getApplicationContext(),Transaction_List.class);
                    startActivity(intent);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}