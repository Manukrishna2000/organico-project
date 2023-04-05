package com.example.organic_app;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;

public class Custom_view_my_orders extends BaseAdapter {
    private Context context;
    String []oid,hotel,img,total,status;
    public Custom_view_my_orders(Context context,String []oid,String []hotel,String []img,String []total,String []status)
    {
        this.context=context;
        this.oid=oid;
        this.hotel=hotel;
        this.img=img;
        this.total=total;
        this.status=status;
    }
    @Override
    public int getCount() {
        return hotel.length;
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
            //   gridView=inflator.inflate(R.layout.mac_custom, null);
            gridView=inflator.inflate(R.layout.custom_view_myorders,null);
        }
        else
        {
            gridView=(View)view;
        }

        TextView tv1=(TextView)gridView.findViewById(R.id.txtname);
        TextView tv2=(TextView)gridView.findViewById(R.id.txtprice);
        TextView tv3=(TextView)gridView.findViewById(R.id.txtcity);
        ImageView im=(ImageView)gridView.findViewById(R.id.imv);


        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);


        tv1.setText("Name   :   "+hotel[i]);
        tv2.setText("Total  :   "+total[i]);
        tv3.setText("Status :   "+status[i]);
        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(context);
        String ip=sh.getString("ip","");
        String url="http://" + ip + ":5000"+img[i];
        // Picasso.with(Context).load(url).into(im);
        Picasso.with(context).load(url).memoryPolicy(MemoryPolicy.NO_CACHE).transform(new CircleTransform()).into(im);




        return gridView;
    }
}
