package com.example.organic_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.text.Editable;
import android.text.PrecomputedText;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
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

public class View_items extends AppCompatActivity implements TextWatcher, AdapterView.OnItemClickListener {
    ListView lv;
    EditText edsearch;

    String [] fid,Name,Photo,fdes,fuse,category;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_items);

        lv=(ListView) findViewById(R.id.lst1);
        edsearch=(EditText) findViewById(R.id.editText6);


        edsearch.addTextChangedListener(this);
        lv.setOnItemClickListener(this);

        search();

    }



    public  void search()
    {
        String nm=edsearch.getText().toString();

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ipa=sh.getString("ip","");

        String url = "http://" + ipa + ":5000/and_view_product_each_seller";
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
                                fid=new String[js.length()];
                                Name=new String[js.length()];
                                Photo=new String[js.length()];
                                fdes=new String[js.length()];
                                fuse=new String[js.length()];

                                category=new String[js.length()];
                                for (int y=0;y<js.length();y++)
                                {


                                    //`Photo`,`Name`,`Description`,`yt_link`,`crpid`

                                    JSONObject jk= js.getJSONObject(y);
                                    fid[y]=jk.getString("product_id");
                                    Name[y]=jk.getString("product_name");
                                    Photo[y]=jk.getString("photo");
                                    fdes[y]=jk.getString("price");
                                    fuse[y]=jk.getString("quantity");
                                    category[y]=jk.getString("category");
                                }

                                lv.setAdapter(new custom_items(getApplicationContext(),Name,fdes,category,Photo));


                            }


                            else {
                                Toast.makeText(getApplicationContext(), "No Item found", Toast.LENGTH_LONG).show();
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


                params.put("lid",sh.getString("seller_id",""));





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
        search();
    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("pid",fid[i]);
        ed.putString("photo",Photo[i]);
        ed.putString("price",fdes[i]);
        ed.putString("quantity",fuse[i]);
        ed.putString("itemname",Name[i]);
        ed.putString("category",category[i]);
        ed.commit();
        Intent ik=new Intent(getApplicationContext(),Cart_add.class);
        startActivity(ik);
    }
}
