package com.example.organic_app;


import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class custom_items extends BaseAdapter {

    private Context Context;
    String[] name;
    String[] description;
    String[] ytlink;
    String[] photo;




    public custom_items(Context applicationContext, String[] name, String[] description, String[] ytlink, String[] photo) {

        this.Context=applicationContext;
        this.name=name;
        this.description=description;
        this.ytlink=ytlink;
        this.photo=photo;


    }

    @Override
    public int getCount() {

        return name.length;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int position, View convertview, ViewGroup parent) {


        LayoutInflater inflator=(LayoutInflater)Context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View gridView;
        if(convertview==null)
        {
            gridView=new View(Context);
            //gridView=inflator.inflate(R.layout.customview, null);
            gridView=inflator.inflate(R.layout.custom_items, null);

        }
        else
        {
            gridView=(View)convertview;

        }

        TextView tcrop=(TextView)gridView.findViewById(R.id.textView30);
        TextView tdesc=(TextView)gridView.findViewById(R.id.textView31);
        TextView tlink=(TextView)gridView.findViewById(R.id.textView32);
        ImageView im=(ImageView)gridView.findViewById(R.id.imageView6);



        tcrop.setText(name[position]);
        tdesc.setText(description[position]);
        tlink.setText(ytlink[position]);

        SharedPreferences sh= PreferenceManager.getDefaultSharedPreferences(Context);
        String ip=sh.getString("ip","");
        String localhost="http://"+ip+":5000/"+photo[position];
        //Toast.makeText(Context,localhost,Toast.LENGTH_SHORT).show();

        Picasso.with(Context).load(localhost).into(im);




        return gridView;
    }


}
