package com.example.organic_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

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
import java.util.prefs.PreferenceChangeEvent;

public class View_my_orders extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[]total,status,hotel,img,oid;
    ListView lv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_my_orders);
        lv=(ListView) findViewById(R.id.lv);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip= sh.getString("ip","");
        Toast.makeText(getApplicationContext(),"hi",Toast.LENGTH_SHORT).show();
lv.setOnItemClickListener(this);

        String url1 = "http://" + ip + ":5000/viewmyorders";


        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        Toast.makeText(getApplicationContext(),"hai",Toast.LENGTH_SHORT).show();
        StringRequest postRequest = new StringRequest(Request.Method.POST, url1,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {

                        Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();


                        // response
                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            String sucs=   jsonObj.getString("status");
                            if(sucs.equalsIgnoreCase("ok"))
                            {
                                JSONArray jsa=jsonObj.getJSONArray("data");

                                oid=new String[jsa.length()];
                                hotel=new String[jsa.length()];
                                img=new String[jsa.length()];
                                status=new String[jsa.length()];
                                total=new String[jsa.length()];


                                for(int i=0;i<jsa.length();i++)
                                {
                                    JSONObject jsob=jsa.getJSONObject(i);
                                    oid[i]=jsob.getString("o_id");
                                    hotel[i]=jsob.getString("sel_name");
                                    img[i]=jsob.getString("sel_image");
                                    status[i]=jsob.getString("status");
                                    total[i]=jsob.getString("total");
                                }

                                lv.setAdapter(new Custom_view_my_orders(getApplicationContext(),oid,hotel,img,total,status));

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
                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                Map<String, String> params = new HashMap<String, String>();

                params.put("lid", sh.getString("lid",""));
                // params.put("sh_id", "2");

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("oid",oid[i]);
        ed.commit();

        Intent j=new Intent(getApplicationContext(),Viewcartitems.class);
        startActivity(j);
    }
}
