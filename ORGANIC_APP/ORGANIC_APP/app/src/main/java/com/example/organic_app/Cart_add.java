package com.example.organic_app;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Cart_add extends AppCompatActivity implements View.OnClickListener {
    ImageView im;
    TextView tn,tp,tem,tlic,tpl;
    Button b;
    EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_add_more);
        tn=(TextView)findViewById(R.id.tname);
        tp=(TextView)findViewById(R.id.cat);
        tem=(TextView)findViewById(R.id.price);
        tlic=(TextView)findViewById(R.id.offer);

        im=(ImageView)findViewById(R.id.imageView4);
        b=(Button)findViewById(R.id.button6);
        ed=(EditText)findViewById(R.id.editText12);
        b.setOnClickListener(this);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        tn.setText(sh.getString("itemname",""));
        tp.setText(sh.getString("category",""));
        tem.setText(sh.getString("quantity",""));
        tlic.setText(sh.getString("price",""));

        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/"+sh.getString("photo","");
        Picasso.with(getApplicationContext()).load(url).memoryPolicy(MemoryPolicy.NO_CACHE).transform(new CircleTransform()).into(im);

    }

    @Override
    public void onClick(View view) {

        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/and_add_to_cart";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("Ok")) {


                                Toast.makeText(getApplicationContext(), "Added To cart", Toast.LENGTH_LONG).show();


                                Intent i=new Intent(getApplicationContext(),View_items.class);
                                startActivity(i);
                            }


                            // }
                            else {
                                Toast.makeText(getApplicationContext(), "Not found", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
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


                params.put("qty", ed.getText().toString());
                params.put("pid", sh.getString("pid",""));
                params.put("user_id", sh.getString("lid",""));
              //  params.put("hid",sh.getString("hid",""));

                return params;
            }
        };

        int MY_SOCKET_TIMEOUT_MS = 100000;

        postRequest.setRetryPolicy(new DefaultRetryPolicy(
                MY_SOCKET_TIMEOUT_MS,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        requestQueue.add(postRequest);
    }
}
