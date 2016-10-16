package com.seagate.pharmacyratingreciever;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    List <Rate> rates;
    ListView listView;
    ArrayList <Integer> ratesPerStar;
    TextView t1,t2,t3,t4,t5,overAllRate,ratesNumber;
    ProgressBar p1,p2,p3,p4,p5;
    ProgressDialog progressDialog;
    RatingBar ratingBar;
    Result result;
    private MainAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseDatabase firebaseDatabase=FirebaseDatabase.getInstance();
        final DatabaseReference reference=firebaseDatabase.getReference().child("Rates");
        rates=new ArrayList<>();
        progressDialog=ProgressDialog.show(this,"Please wait","Getting Data",true,false);
        listView= (ListView) findViewById(R.id.listView);
        ratesNumber= (TextView) findViewById(R.id.ratesNumber);
        overAllRate= (TextView) findViewById(R.id.rate);
        ratingBar= (RatingBar) findViewById(R.id.ratingBar);
        String [] types = getResources().getStringArray(R.array.pharmacy_types);
        result=new Result(types);


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                result.clearRates();
                /*rates.clear();*/
                progressDialog.dismiss();
                for (DataSnapshot data:dataSnapshot.getChildren()){
                    Rate rate=data.getValue(Rate.class);
                    result.addToParent(rate);
                    /*rates.add(rate);*/



                }
                adapter=new MainAdapter(MainActivity.this,result.getDetailedRates());
                listView.setAdapter(adapter);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i=new Intent(MainActivity.this,DetailedActivity.class);
                i.putExtra("result",result);
                i.putExtra("position",position);
                startActivity(i);
            }
        });










    }



    private void settingMyProgress() {
        p1= (ProgressBar) findViewById(R.id.p1);
        p2= (ProgressBar) findViewById(R.id.p2);
        p3= (ProgressBar) findViewById(R.id.p3);
        p4= (ProgressBar) findViewById(R.id.p4);
        p5= (ProgressBar) findViewById(R.id.p5);
        ProgressBar [] progressBars=new ProgressBar[]{p1,p2,p3,p4,p5};
        int i=0;
        for (ProgressBar progressBar:progressBars){

            ArrayList<Integer> ratesPerSrar=getRatesPerStar();
            double value=((double)ratesPerSrar.get(i)/(double) rates.size())*100;
            progressBar.setProgress((int) value);

            i++;
        }
    }

    private void settingOverAllRate() {

        overAllRate.setText(String.format(java.util.Locale.US,"%.1f",getOverAllValue()));
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

    private void settingRateTextNumbers() {


        t1= (TextView) findViewById(R.id.t1);
        t2= (TextView) findViewById(R.id.t2);
        t3= (TextView) findViewById(R.id.t3);
        t4= (TextView) findViewById(R.id.t4);
        t5= (TextView) findViewById(R.id.t5);

        TextView[]textViews=new TextView[]{t1,t2,t3,t4,t5};
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
}
