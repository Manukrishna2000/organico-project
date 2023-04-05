package com.example.organic_app;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import android.os.Bundle;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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

public class Viewcartitems extends AppCompatActivity {
GridView lv;
    String[]foodid,itemname,category,type,price,description,image;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewcartitems);
        lv=(GridView)findViewById(R.id.gv);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip= sh.getString("ip","");

        String url1 = "http://" + ip + ":5000/and_view_orderitems";


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
//            Toast.makeText(getApplicationContext(),"hai",Toast.LENGTH_SHORT).show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

//                          Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();


                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String sucs=   jsonObj.getString("status");
                            if(sucs.equalsIgnoreCase("ok"))
                            {
                                JSONArray jsa=jsonObj.getJSONArray("data");

                                foodid=new String[jsa.length()];
                                itemname=new String[jsa.length()];
                                image=new String[jsa.length()];
                                category=new String[jsa.length()];
                                type=new String[jsa.length()];
                                price=new String[jsa.length()];
                                description=new String[jsa.length()];

                                for(int i=0;i<jsa.length();i++)
                                {
                                    JSONObject jsob=jsa.getJSONObject(i);
                                    foodid[i]=jsob.getString("product_id");
                                    itemname[i]=jsob.getString("product_name");
                                    price[i]=jsob.getString("price");
                                    description[i]=jsob.getString("quantity");
                                    image[i]=jsob.getString("photo");
                                }

                                lv.setAdapter(new custom_items(getApplicationContext(),itemname,price,description,image));

                            }
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(),"eeeee"+e.toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // error
                        Toast.makeText(getApplicationContext(),"eeeee"+error.toString(),Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();

                 params.put("oid", sh.getString("oid",""));
                // params.put("sh_id", "2");

                return params;
            }
        };
        requestQueue.add(postRequest);

    }
}
