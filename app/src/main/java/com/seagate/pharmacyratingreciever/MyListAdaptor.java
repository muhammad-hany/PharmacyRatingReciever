package com.seagate.pharmacyratingreciever;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by Muhammad Workstation on 25/09/2016.
 */

public class MyListAdaptor extends ArrayAdapter<Rate> {

    List<Rate> rates;
    public MyListAdaptor(Context context, List<Rate> rates) {
        super(context, R.layout.list_row,rates);
        this.rates=rates;
        Collections.reverse(this.rates);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder=null;
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.list_row,null);
            holder=new ViewHolder();
            holder.name= (TextView) convertView.findViewById(R.id.name);
            holder.description= (TextView) convertView.findViewById(R.id.description);
            holder.image1= (ImageView) convertView.findViewById(R.id.star1);
            holder.image2= (ImageView) convertView.findViewById(R.id.star2);
            holder.image3= (ImageView) convertView.findViewById(R.id.star3);
            holder.image4= (ImageView) convertView.findViewById(R.id.star4);
            holder.image5= (ImageView) convertView.findViewById(R.id.star5);
            holder.image1.setVisibility(View.INVISIBLE);
            holder.image2.setVisibility(View.INVISIBLE);
            holder.image3.setVisibility(View.INVISIBLE);
            holder.image4.setVisibility(View.INVISIBLE);
            holder.image5.setVisibility(View.INVISIBLE);
            holder.phone= (TextView) convertView.findViewById(R.id.phone);
            holder.date= (TextView) convertView.findViewById(R.id.date);
            convertView.setTag(holder);
        }else {
            holder= (ViewHolder) convertView.getTag();
        }
        Rate rate=rates.get(position);
        ImageView[] imageViews={holder.image1,holder.image2,holder.image3,holder.image4,holder
                .image5};
        holder.name.setText(rate.getName());
        holder.description.setText(rate.getDescription());
        for (int i=0;i<rate.getRateValue();i++){
            imageViews[i].setVisibility(View.VISIBLE);
        }
        holder.phone.setText(rate.getPhoneNumber());
        holder.date.setText(rate.getDate());
        return convertView;
    }

    class ViewHolder{
        ImageView image1,image2,image3,image4,image5;
        TextView name,description,phone,date;
    }
}
