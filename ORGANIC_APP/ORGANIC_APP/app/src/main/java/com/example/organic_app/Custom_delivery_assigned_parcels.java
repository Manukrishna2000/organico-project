package com.example.organic_app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;




public class Custom_delivery_assigned_parcels extends BaseAdapter {
    String[] date, parcel, place;
    private Context context;

    public Custom_delivery_assigned_parcels(Context appcontext, String[] date, String[] parcel, String[] place) {
        this.context = appcontext;
        this.date = date;
        this.parcel = parcel;
        this.place = place;

    }

    @Override
    public int getCount() {
        return parcel.length;

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
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        Log.d("alloi","hiiiiii");
        View gridView;
        if (view == null) {
            gridView = new View(context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView = inflator.inflate(R.layout.custom_delivery_assigned_parcels, null);

        } else {
            gridView = (View) view;

        }
        TextView tv1 = (TextView) gridView.findViewById(R.id.date123);
        TextView tv2 = (TextView) gridView.findViewById(R.id.parcel123);
        TextView tv3 = (TextView) gridView.findViewById(R.id.place123);
//        try {
//            int ji = Integer.parseInt(view.getTag().toString());
//            SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(context);
//            SharedPreferences.Editor ed = sh.edit();
//            ed.putString("date", date[ji]);
//            ed.putString("parcel", parcel[ji]);
//            ed.putString("place", place[ji]);
//
//
//            ed.commit();
//            Intent ii = new Intent(context, agent_viewrqst_more.class);
//            ii.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(ii);
//        } catch (Exception ex)
//        {
//            Toast.makeText(context,ex.getMessage().toString(),Toast.LENGTH_LONG).show();
//        }







        tv1.setTextColor(Color.BLACK);
        tv2.setTextColor(Color.BLACK);
        tv3.setTextColor(Color.BLACK);

        tv1.setText(date[i]);
        tv2.setText(parcel[i]);
        tv3.setText(place[i]);

        return gridView;

    }
}
