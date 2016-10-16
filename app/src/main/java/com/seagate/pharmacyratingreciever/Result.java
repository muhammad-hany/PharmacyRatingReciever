package com.seagate.pharmacyratingreciever;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Muhammad Workstation on 04/10/2016.
 */

public class Result implements Serializable {
    ArrayList<Rate> arrayList;
    String [] types;
    public Result(String [] types){
        arrayList=new ArrayList<>();
        this.types=types;
    }

    public void addToParent (Rate rate){
        arrayList.add(rate);
    }

    public ArrayList<Rate> getRatesByType(String type){
        ArrayList<Rate> smallarray=new ArrayList<>();
        for (Rate rate: arrayList){
            if (rate.getPharmacyType().equals(type)){
                smallarray.add(rate);
            }
        }
        return smallarray;
    }

    public List<Rate> getAllRates(){
        return arrayList;


    }

    public void clearRates(){
        arrayList.clear();
    }



    public List<List<Rate>> getDetailedRates(){

        List<List<Rate>> list=new ArrayList<>();
        list.add(getAllRates());
        int i=0;
        for (String type:types){
            if (getRatesByType(type).size()!=0){
                list.add(getRatesByType(type));
            }
            i++;
        }
        return list;
    }

    /*public List<ArrayList<Rate>> getAdapterData(){
        HashMap<Integer,ArrayList<Rate>> map=getDetailedRates();
        for (Map.Entry<Integer,ArrayList<Rate>>)
    }*/


}
