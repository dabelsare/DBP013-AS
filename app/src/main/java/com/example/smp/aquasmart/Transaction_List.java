package com.example.smp.aquasmart;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.app.Activity;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Transaction_List extends Activity {
    ListView simpleList;
    List<String> listString = new ArrayList<String>();
    ArrayAdapter<String> arrayAdapter;
    String transURL="http://smartbizit.com/aquasmart/transaction_list.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction__list);
        
        simpleList = (ListView) findViewById(R.id.simpleListView);
        new GetHttpResponse(this).execute();

    }

    private class GetHttpResponse extends AsyncTask<Void, Void, Void>
    {
        private Context context;
        String result;
        List<TransactionClass> transList;
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
            HttpServicesClass httpService = new HttpServicesClass(transURL);
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
                            JSONArray array;
                            TransactionClass trans;
                            transList = new ArrayList<TransactionClass>();
                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                trans = new TransactionClass();
                                object = jsonArray.getJSONObject(i);
                                //trans.transaction_id=object.getString("transaction_id");
                                trans.customer_name = object.getString("name");
                                trans.mobile = object.getString("mobile");
                                trans.balance = object.getString("balance");
                                trans.remain_jar = object.getString("Remain_Jar");
                                trans.remain_bottle = object.getString("Remain_Bottle");
                                transList.add(trans);
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
            simpleList.setVisibility(View.VISIBLE);

            if(transList != null)
            {
                CustomAdapter customAdapter = new CustomAdapter(transList, context);
                simpleList.setAdapter(customAdapter);

//                simpleList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
//                        Intent intent=new Intent(getApplicationContext(),Transaction.class);
//                        intent.putExtra("customerId",customerList.get(position).customer_id.toString());
//                        startActivity(intent);
//                    }
//                });

            }else {
                Toast.makeText(getApplicationContext(),"Transaction list is empty",Toast.LENGTH_SHORT).show();
            }
        }
    }
}
