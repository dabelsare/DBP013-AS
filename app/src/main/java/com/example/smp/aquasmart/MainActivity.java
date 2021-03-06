package com.example.smp.aquasmart;
import android.content.Intent;
import android.os.Bundle;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.EditText;
import android.text.TextWatcher;

public class MainActivity extends Activity {
    Button CustAdd,Trans,LogOt;
    ListView listCustomer;

    List<Customer> customerList;
    EditText inputSearch;
    String serverURL="http://smartbizit.com/aquasmart/customers.php";

    private CustomAdapterCustomerList mCustomAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        CustAdd=(Button)findViewById(R.id.btnCustomersAdd);
        Trans=(Button)findViewById(R.id.btnTransactionInList);
        LogOt=(Button)findViewById(R.id.btnLogoutInList);
        listCustomer = (ListView)findViewById(R.id.listCustomer);
        inputSearch=(EditText) findViewById(R.id.inputSearch);
        listCustomer.setTextFilterEnabled(true);

        new GetHttpResponse(this).execute();

        CustAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,CustomerAdd.class);
                startActivity(i);
            }
        });
        Trans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,Transaction_List.class);
                startActivity(i);
            }
        });
        LogOt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(MainActivity.this,LoginActivity.class);
                startActivity(i);
            }
        });

        inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                mCustomAdapter.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        private Context context;
        String result;
        public GetHttpResponse(Context context)
        {
            this.context = context;
        }

        @Override
        protected void onPreExecute()
        {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0)
        {
            HttpServicesClass httpService = new HttpServicesClass(serverURL);
            try
            {
                httpService.ExecutePostRequest();

                if(httpService.getResponseCode() == 200)
                {
                    result = httpService.getResponse();
                    Log.d("Result", result);
                    if(result != null)
                    {
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(result);

                            JSONObject object;
                            Customer customer;
                            customerList = new ArrayList<Customer>();
                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                customer = new Customer();
                                object = jsonArray.getJSONObject(i);
                                customer.customer_id = object.getString("customer_id");
                                customer.name = object.getString("name");
                                customer.address = object.getString("address");
                                customer.mobile = object.getString("mobile");
                                customer.deposit = object.getString("deposit");
                                customer.balance = object.getString("balance");
                                customer.remain_jar = object.getString("Remain_Jar");
                                customer.remain_bottle = object.getString("Remain_Bottle");
                                customerList.add(customer);
                            }
                        }
                        catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                }
                else
                {
                    Toast.makeText(context, httpService.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            }
            catch (Exception e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            //proCustomerList.setVisibility(View.GONE);
            listCustomer.setVisibility(View.VISIBLE);
            if(customerList != null)
            {
                mCustomAdapter = new CustomAdapterCustomerList(customerList,context);
                listCustomer.setAdapter(mCustomAdapter);

                listCustomer.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        //Toast.makeText(getApplicationContext(),""+mCustomAdapter.valueList.get(position).name,Toast.LENGTH_LONG).show();
                        Intent intent=new Intent(MainActivity.this,Transaction.class);
                        intent.putExtra("customerId",mCustomAdapter.valueList.get(position).customer_id.toString());
//                        intent.putExtra("c_name",mCustomAdapter.valueList.get(position).name.toString());
//                        intent.putExtra("balance",mCustomAdapter.valueList.get(position).balance.toString());
//                        intent.putExtra("remain_jar",mCustomAdapter.valueList.get(position).remain_jar.toString());
//                        intent.putExtra("remain_bottle",mCustomAdapter.valueList.get(position).remain_bottle.toString());
                        startActivity(intent);
                    }
                });
                listCustomer.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                    @Override
                    public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
                                                   int pos, long id) {
                        Intent intent=new Intent(MainActivity.this,CustomerAdd.class);
                        intent.putExtra("customerId",mCustomAdapter.valueList.get(pos).customer_id.toString());
                        intent.putExtra("c_name",mCustomAdapter.valueList.get(pos).name.toString());
                        intent.putExtra("address",mCustomAdapter.valueList.get(pos).address.toString());
                        intent.putExtra("mobile",mCustomAdapter.valueList.get(pos).mobile.toString());
                        intent.putExtra("deposit",mCustomAdapter.valueList.get(pos).deposit.toString());
                        startActivity(intent);
                        return true;
                    }
                });
            }
        }
    }
}