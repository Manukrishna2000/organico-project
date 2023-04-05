package com.example.organic_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

public class View_cart extends AppCompatActivity implements View.OnClickListener {
    String []cid,qty,name,price,img,total;
    ListView lv;
    Button b1;
    TextView t1;
    EditText ed;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cart);
        lv=(ListView)findViewById(R.id.lv);
        b1=(Button)findViewById(R.id.button7);
        b1.setOnClickListener(this);
        t1=(TextView)findViewById(R.id.textView18);
        ed=(EditText)findViewById(R.id.editText4);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String ip= sh.getString("ip","");

        String url1 = "http://" + ip + ":5000/userviewcart";


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

                                cid=new String[jsa.length()];
                                name=new String[jsa.length()];
                                qty=new String[jsa.length()];
                                price=new String[jsa.length()];
                                img=new String[jsa.length()];
                                total=new String[jsa.length()];
                                for(int i=0;i<jsa.length();i++)
                                {
                                    JSONObject jsob=jsa.getJSONObject(i);
                                    cid[i]=jsob.getString("cart_id");
                                    name[i]=jsob.getString("product_name");
                                    qty[i]=jsob.getString("qty");
                                    price[i]=jsob.getString("price");
                                    int ko=(Integer.parseInt(qty[i])*Integer.parseInt(price[i]));
                                    total[i]=ko+"";
                                    img[i]=jsob.getString("photo");


                                }int to=0;
                                for(int ij=0;ij<total.length;ij++)
                                {
                                    to=to+Integer.parseInt(total[ij].toString());
                                }
                                t1.setText(to+"");
                                lv.setAdapter(new Custom_view_my_cart(getApplicationContext(), cid, name, price, img, total));

                            }
                            else {
                                Toast.makeText(getApplicationContext(),"No Values",Toast.LENGTH_LONG).show();
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

                params.put("selid", sh.getString("seller_id",""));
                params.put("lid", sh.getString("lid",""));
                // params.put("sh_id", "2");

                return params;
            }
        };
        requestQueue.add(postRequest);
    }

    @Override
    public void onClick(View view) {
        final String address=ed.getText().toString();
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        SharedPreferences.Editor ed=sh.edit();
        ed.putString("tot",t1.getText().toString());
        ed.commit();
//        Intent i=new Intent(getApplicationContext(),Payment.class);
//        startActivity(i);
        String hu = sh.getString("ip", "");
        String url = "http://" + hu + ":5000/and_add_to_cart_and_payment";

        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            JSONObject jsonObj = new JSONObject(response);
                            if (jsonObj.getString("status").equalsIgnoreCase("Ok")) {


                                Toast.makeText(getApplicationContext(), "Do the Payment Now", Toast.LENGTH_LONG).show();

                                SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                SharedPreferences.Editor ed=sh.edit();
                                ed.putString("order_id",jsonObj.getString("oid"));
                                ed.commit();

                                Intent i=new Intent(getApplicationContext(),Payment.class);
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


                params.put("address", address);
                params.put("selid", sh.getString("seller_id",""));
                params.put("lid", sh.getString("lid",""));
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
