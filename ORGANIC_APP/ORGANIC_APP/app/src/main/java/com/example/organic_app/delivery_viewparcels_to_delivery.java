package com.example.organic_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class delivery_viewparcels_to_delivery extends AppCompatActivity implements View.OnClickListener {
    TextView p1,p2,p3,p4,p5,p6,p7,p8;
    Button b1;
Spinner s1;
String []mo= {"Order Picked","Order On the way","Order Delivered"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_viewparcels_to_delivery);
        p1=(TextView)findViewById(R.id.textView56);
        p2=(TextView)findViewById(R.id.textView17);
        p3=(TextView)findViewById(R.id.textView51);
        p4=(TextView)findViewById(R.id.textView58);
        p5=(TextView)findViewById(R.id.textView62);
        p6=(TextView)findViewById(R.id.textView65);
        p7=(TextView)findViewById(R.id.textView67);
        s1=(Spinner) findViewById(R.id.spinner);
        b1=(Button)findViewById(R.id.button7);
        ArrayAdapter<String> ad=new ArrayAdapter<>(getApplicationContext(),android.R.layout.simple_list_item_1,mo);
        s1.setAdapter(ad);
        b1.setOnClickListener(this);
        final SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        String uid=sh.getString("uid","");
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/delivery_view_orders";

        //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // responsessss
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                                p1.setText(jsonObj.getString("o_id"));
                                p2.setText(jsonObj.getString("name"));
                                p3.setText(jsonObj.getString("date"));
                                p4.setText(jsonObj.getString("conatct"));
                                p5.setText(jsonObj.getString("total"));
                                p6.setText(jsonObj.getString("delivary_address"));
                                p7.setText(jsonObj.getString("status1"));








                            }


                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
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



                params.put("parcelid",sh.getString("parcelid",""));

//                params.put("mac",maclis);

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


    @Override
    public void onClick(View view) {
        final SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//        String uid=sh.getString("uid","");
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/deliver_parcel";



        //Toast.makeText(getApplicationContext(), url, Toast.LENGTH_LONG).show();


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // responsessss
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {
                               // String id=jsonObj.getString("data");
                              //  SharedPreferences sp=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                               // SharedPreferences.Editor ed=sp.edit();
                              //  ed.putString("uid",id);
                              //  ed.commit();
                                Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_LONG).show();

                                Intent we=new Intent(getApplicationContext(),Delivery_homr.class);
                                startActivity(we);








                            }


                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
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


//
                params.put("parcelid",sh.getString("parcelid",""));
                params.put("status",s1.getSelectedItem().toString());
//                params.put("password",password);

//                params.put("mac",maclis);

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
