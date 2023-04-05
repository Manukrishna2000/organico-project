package com.example.organic_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class View_sellers extends AppCompatActivity implements TextWatcher, AdapterView.OnItemClickListener {

    ListView lv;
    EditText edsearch;

    String [] crpid,crpname,crpdesc,crplink,crpphoto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_sellers);


        lv=(ListView) findViewById(R.id.lst1);
        edsearch=(EditText) findViewById(R.id.editText6);
    lv.setOnItemClickListener(this);

        edsearch.addTextChangedListener(this);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ipa=sh.getString("ip","");

        String url = "http://" + ipa + ":5000/and_cust_view_nearby_seller_no";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //     Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js= jsonObj.getJSONArray("res");
                                crpid=new String[js.length()];
                                crpname=new String[js.length()];
                                crpdesc=new String[js.length()];
                                crplink=new String[js.length()];
                                crpphoto=new String[js.length()];

                                for (int y=0;y<js.length();y++)
                                {


                                    //`Photo`,`Name`,`Description`,`yt_link`,`crpid`

                                    JSONObject jk= js.getJSONObject(y);
                                    crpid[y]=jk.getString("sel_lid");
                                    crpname[y]=jk.getString("sel_name");
                                    crpdesc[y]=jk.getString("sel_place");
                                    crplink[y]=jk.getString("sel_phn");
                                    crpphoto[y]=jk.getString("sel_image");
                                }

                                lv.setAdapter(new custom_crops(getApplicationContext(),crpname,crpdesc,crplink,crpphoto));


                            }


                            else {
                                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
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


               // params.put("kw",edsearch.getText().toString());





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

    public  void search()
    {
        String nm=edsearch.getText().toString();

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ipa=sh.getString("ip","");

        String url = "http://" + ipa + ":5000/and_cust_view_nearby_seller";
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        //     Toast.makeText(getApplicationContext(), response, Toast.LENGTH_LONG).show();

                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("ok")) {

                                JSONArray js= jsonObj.getJSONArray("res");
                                crpid=new String[js.length()];
                                crpname=new String[js.length()];
                                crpdesc=new String[js.length()];
                                crplink=new String[js.length()];
                                crpphoto=new String[js.length()];

                            for (int y=0;y<js.length();y++)
                            {


                                //`Photo`,`Name`,`Description`,`yt_link`,`crpid`

                                JSONObject jk= js.getJSONObject(y);
                                crpid[y]=jk.getString("sel_lid");
                                crpname[y]=jk.getString("sel_name");
                                crpdesc[y]=jk.getString("sel_place");
                                crplink[y]=jk.getString("sel_phn");
                                crpphoto[y]=jk.getString("sel_image");
                            }

                            lv.setAdapter(new custom_crops(getApplicationContext(),crpname,crpdesc,crplink,crpphoto));


                            }


                            else {
                                Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_LONG).show();
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


                params.put("kw",edsearch.getText().toString());





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
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
search();
    }

    @Override
    public void afterTextChanged(Editable editable) {
search();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("seller_id",crpid[i]);
        ed.commit();
        Intent ik=new Intent(getApplicationContext(),View_items.class);
        startActivity(ik);
    }
}
