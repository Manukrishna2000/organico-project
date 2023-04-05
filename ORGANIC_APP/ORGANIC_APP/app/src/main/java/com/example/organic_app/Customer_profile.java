package com.example.organic_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Customer_profile extends AppCompatActivity {
    TextView ed1,ed2,ed3,ed4,ed5,ed6,ed7,ed8,ed9,ed10;
    ImageView img;
    SharedPreferences sh;
    String url="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_profile);
        img=(ImageView)findViewById(R.id.imageView);
        ed1=(TextView) findViewById(R.id.textView15);
        ed2=(TextView) findViewById(R.id.textView18);
        ed3=(TextView) findViewById(R.id.textView17);
        ed4=(TextView) findViewById(R.id.textView16);
        ed5=(TextView) findViewById(R.id.textView23);
        ed6=(TextView) findViewById(R.id.textView22);


        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String hu = sh.getString("ip", "");
        url = "http://" + hu + ":5000/customer_view_profile";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
//Toast.makeText(getApplicationContext(),response,Toast.LENGTH_LONG).show();
                        try{
                            JSONObject jsonObject=new JSONObject(response);
                            String status=jsonObject.getString("status");
                            if(status.equalsIgnoreCase("ok")){

                                ed1.setText(jsonObject.getString("name"));
                                ed2.setText(jsonObject.getString("phone"));
                                ed3.setText(jsonObject.getString("place"));
                                ed4.setText(jsonObject.getString("post"));
                                ed5.setText(jsonObject.getString("pin"));
                                ed6.setText(jsonObject.getString("email"));

                                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                String ip=sh.getString("ip","");
                                String url="http://" + ip + ":5000"+jsonObject.getString("email");
                                Log.d("photoooo",url);
                                Picasso.with(getApplicationContext()).load(url).into(img);



                                //   Toast.makeText(VIEWPROFILE.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(Customer_profile.this, "Try again later", Toast.LENGTH_SHORT).show();
                            }

                        }catch (Exception e){}

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
                Map<String, String> params = new HashMap<>();
                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                params.put("lid",sh.getString("lid",""));


                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(60000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));


        requestQueue.add(postRequest);
    }
}
