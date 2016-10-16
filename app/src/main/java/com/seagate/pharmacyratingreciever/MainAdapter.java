package com.seagate.pharmacyratingreciever;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Workstation on 04/10/2016.
 */

public class MainAdapter extends ArrayAdapter<List<Rate>> {

    private ArrayList<Integer> ratesPerStar;
    List<List<Rate>> allBranchesRates;
    List<Rate> rates;
    public MainAdapter(Context context ,List<List<Rate>> rates) {
        super(context, R.layout.branch_row,rates);

        allBranchesRates=rates;
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder=null;
        if (convertView==null){
            LayoutInflater inflater= (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.branch_row,null);
            holder=new Holder();
            holder.listView= (ListView) convertView.findViewById(R.id.listView);
            holder.ratesNumber= (TextView) convertView.findViewById(R.id.ratesNumber);
            holder.overAllRate= (TextView) convertView.findViewById(R.id.rate);
            holder.ratingBar= (RatingBar) convertView.findViewById(R.id.ratingBar);
            holder.p1= (ProgressBar) convertView.findViewById(R.id.p1);
            holder.p2= (ProgressBar) convertView.findViewById(R.id.p2);
            holder.p3= (ProgressBar) convertView.findViewById(R.id.p3);
            holder.p4= (ProgressBar) convertView.findViewById(R.id.p4);
            holder.p5= (ProgressBar) convertView.findViewById(R.id.p5);
            holder.t1= (TextView) convertView.findViewById(R.id.t1);
            holder.t2= (TextView) convertView.findViewById(R.id.t2);
            holder.t3= (TextView) convertView.findViewById(R.id.t3);
            holder.t4= (TextView) convertView.findViewById(R.id.t4);
            holder.t5= (TextView) convertView.findViewById(R.id.t5);
            holder.branchName= (TextView) convertView.findViewById(R.id.branch);
            convertView.setTag(holder);
        }else {
            holder= (Holder) convertView.getTag();
        }
        rates=allBranchesRates.get(position);
        settingRateTextNumbers(holder);
        settingOverAllRate(holder);
        settingMyProgress(holder);
        holder.ratingBar.setRating((float) getOverAllValue());
        holder.ratesNumber.setText(String.valueOf(rates.size()));
        if (position==0){
            holder.branchName.setText("Over All Rating");
        }else {
            holder.branchName.setText(rates.get(0).getPharmacyType());
        }


        return convertView;
    }

    class Holder{
        TextView t1,t2,t3,t4,t5,overAllRate,ratesNumber,branchName;
        ProgressBar p1,p2,p3,p4,p5;
        RatingBar ratingBar;
        ListView listView;
    }



    private void settingRateTextNumbers(Holder holder) {

        TextView[]textViews=new TextView[]{holder.t1,holder.t2,holder.t3,holder.t4,holder.t5};
        int i=0;
        for (TextView textView:textViews){
            textView.setText(String.valueOf(getRatesPerStar().get(i)));
            i++;
        }


    }

    private ArrayList<Integer> getRatesPerStar() {
        ratesPerStar=new ArrayList<>();
        ratesPerStar.add(0);
        ratesPerStar.add(0);
        ratesPerStar.add(0);
        ratesPerStar.add(0);
        ratesPerStar.add(0);
        int x;
        for (Rate rate:rates){
            int score= (int) rate.getRateValue();
            for (int i=0;i<5;i++){
                if (score==i+1) {
                    x = ratesPerStar.get(i) == 0 ? 0 : ratesPerStar.get(i);
                    x++;
                    ratesPerStar.set(i, x);
                    break;
                }
            }

        }
        return ratesPerStar;
    }



    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    private void settingOverAllRate(Holder holder) {

        holder.overAllRate.setText(String.format(java.util.Locale.US,"%.1f",getOverAllValue()));
    }

    private double getOverAllValue(){
        int comulative=0;
        for (int i=0;i<rates.size();i++){
            comulative= (int) (comulative+rates.get(i).getRateValue());
        }
        double value;
        value= (double)comulative/rates.size();

        value=round(value,1);


        return value;
    }
    private void settingMyProgress(Holder holder) {
        ProgressBar [] progressBars=new ProgressBar[]{holder.p1,holder.p2,holder.p3,holder.p4,holder.p5};
        int i=0;
        for (ProgressBar progressBar:progressBars){

            ArrayList<Integer> ratesPerSrar=getRatesPerStar();
            double value=((double)ratesPerSrar.get(i)/(double) rates.size())*100;
            progressBar.setProgress((int) value);

            i++;
        }
    }
}
