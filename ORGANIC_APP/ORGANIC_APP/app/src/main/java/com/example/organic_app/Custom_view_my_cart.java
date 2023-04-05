package com.example.organic_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
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

public class Custom_view_my_cart extends BaseAdapter {
    private Context context;
    String []cid,qty,name,price,img;
    public Custom_view_my_cart(Context context,String []cid,String []name,String []qty,String []img,String []price)
    {
        this.context=context;
        this.cid=cid;
        this.qty=qty;
        this.name=name;
        this.price=price;
        this.img=img;
    }
    @Override
    public int getCount() {
        return img.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflator=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(view==null)
        {
            gridView=new View(context);

            gridView=inflator.inflate(R.layout.custom_view_mycart,null);
        }
        else
        {
            gridView=(View)view;
        }

        TextView tv1=(TextView)gridView.findViewById(R.id.textView7);
        TextView tv2=(TextView)gridView.findViewById(R.id.textView11);
        TextView tv3=(TextView)gridView.findViewById(R.id.textView12);
        ImageView im=(ImageView)gridView.findViewById(R.id.imageView4);
        Button bt=(Button)gridView.findViewById(R.id.button6) ;
        bt.setTag(i);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int ii=Integer.parseInt(view.getTag().toString());
                final String cids=cid[ii];
                final  SharedPreferences she= PreferenceManager.getDefaultSharedPreferences(context);
                String ip= she.getString("ip","");

                String url = "http://" + ip + ":5000/and_delete_cart";
//        String name="";

                RequestQueue requestQueue = Volley.newRequestQueue(context);
                StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {

                                try {
                                    JSONObject jsonObj = new JSONObject(response);
                                    if (jsonObj.getString("status").equalsIgnoreCase("Ok")) {

                                        Toast.makeText(context, "Deleted", Toast.LENGTH_LONG).show();
                                        Intent ne=new Intent(context,View_cart.class);
                                        ne.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        context.startActivity(ne);
                                    }


                                    // }
                                    else {
                                        Toast.makeText(context, "Not found", Toast.LENGTH_LONG).show();
                                    }

                                }    catch (Exception e) {
                                    Toast.makeText(context, "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // error
                                Toast.makeText(context, "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                ) {
                    @Override
                    protected Map<String, String> getParams() {
                        SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("cid",cids);
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
        });

        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);


        tv1.setText("Name       :   "+name[i]);
        tv2.setText("Quantity   :   "+qty[i]);
        tv3.setText("Total      :   "+price[i]);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");
        String url="http://" + ip + ":5000/"+img[i];
        Log.d("photoooo",url);
        Picasso.with(context).load(url).into(im);




        return gridView;
    }
}
