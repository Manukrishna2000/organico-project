package com.example.organic_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Payment extends AppCompatActivity implements View.OnClickListener {
Spinner s1;
EditText ac,pin;
TextView t10;
Button bt;
String [] bnk={"sbi","icici","axis"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        bt=(Button)findViewById(R.id.button7);
        bt.setOnClickListener(this);
        s1=(Spinner)findViewById(R.id.spinner);
        ac=(EditText)findViewById(R.id.editText2);
        pin=(EditText)findViewById(R.id.editText3);
        t10=(TextView)findViewById(R.id.textView10);
        SharedPreferences she= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        t10.setText(she.getString("tot",""));
        ArrayAdapter<String> ad=new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,bnk);
        s1.setAdapter(ad);
//        ArrayAdapter<String>adpt=ArrayAdapter.createFromResource(this,bnk,R.layout.sp_item);
    }

    @Override
    public void onClick(View view) {
        SharedPreferences she= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip= she.getString("ip","");

        String url = "http://" + ip + ":5000/insertorder";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                Toast.makeText(getApplicationContext(), "successfully Inserted", Toast.LENGTH_LONG).show();
Intent ii=new Intent(getApplicationContext(),Customer_home.class);
startActivity(ii);
                            }



                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "Insufficient balance", Toast.LENGTH_LONG).show();
                            }

                        }    catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();

                params.put("bank",s1.getSelectedItem().toString());
                params.put("acc",ac.getText().toString());
                params.put("pin",pin.getText().toString());
                params.put("oid", sh.getString("order_id",""));
params.put("tot",sh.getString("tot",""));

                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS=100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);

    }
}
