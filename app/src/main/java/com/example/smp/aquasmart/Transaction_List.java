package com.example.smp.aquasmart;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.app.Activity;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Transaction_List extends Activity {
    Button FromDate,ToDate,Show;
    ListView simpleList;
    String transURL="http://smartbizit.com/aquasmart/transaction_history.php";
    EditText editSearch;
    List<TransactionClass> transList;
    private Calendar calendar;
    private TextView dateView,dateViewTo;
    private int year, month, day;

    private CustomAdapter mCustomAdapterTran;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction__list);

        dateView = (TextView) findViewById(R.id.txtFromDate);
//        dateViewTo = (TextView) findViewById(R.id.txtToDate);

        calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);

        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        showDate(year, month+1, day);
//        showDateTo(year, month+1, day);

        simpleList = (ListView) findViewById(R.id.simpleListView);
        editSearch=(EditText) findViewById(R.id.editSearch);
        simpleList.setTextFilterEnabled(true);

        new GetHttpResponse(this).execute();

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(getApplicationContext(),"search+"+s,Toast.LENGTH_LONG).show();
                mCustomAdapterTran.getFilter().filter(s);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

//        FromDate=(Button)findViewById(R.id.btnFromDate);
//        ToDate=(Button)findViewById(R.id.btnToDate);
//        Show=(Button)findViewById(R.id.btnShow);
//
//        FromDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent i=new Intent(Transaction_List.this,MainActivity.class);
////                startActivity(i);
//            }
//        });
//        ToDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent i=new Intent(Transaction_List.this,CustomerAdd.class);
////                startActivity(i);
//            }
//        });
//
//        Show.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
////                Intent i=new Intent(Transaction_List.this,LoginActivity.class);
////                startActivity(i);
//            }
//        });

    }

    @SuppressWarnings("deprecation")
    public void setDate(View view) {
        showDialog(999);
    }

//    @SuppressWarnings("deprecation")
//    public void setDateTo(View view) {
//        showDialog(999);
//    }

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

//    private void showDateTo(int year, int month, int day) {
//        dateViewTo.setText(new StringBuilder().append(day).append("/")
//                .append(month).append("/").append(year));
//    }

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
                            TransactionClass trans;
                            transList = new ArrayList<TransactionClass>();
                            for(int i=0; i<jsonArray.length(); i++)
                            {
                                trans = new TransactionClass();
                                object = jsonArray.getJSONObject(i);
                                //trans.transaction_id=object.getString("transaction_id");
                                trans.customer_name = object.getString("name");
                                trans.date = object.getString("date");
                                trans.d_jar_type = object.getString("jar_type");
                                trans.d_qty = object.getString("qty");
                                trans.d_qty = object.getString("qty");
                                trans.balance = object.getString("balance");
                                trans.r_jar_type = object.getString("return_jar_type");
                                trans.r_qty = object.getString("return_qty");
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
                mCustomAdapterTran = new CustomAdapter(transList, context);
                simpleList.setAdapter(mCustomAdapterTran);

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