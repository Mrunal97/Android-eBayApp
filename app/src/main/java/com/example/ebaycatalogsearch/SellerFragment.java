package com.example.ebaycatalogsearch;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
//import org.json.simple.JSONObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Iterator;
import java.util.List;

public class SellerFragment extends Fragment {


    List<Ebay> ebay;
    TextView textView;
    String shippinglist,shippinglistend, returnPolicylist, returnPolicylistend;
    JSONObject returnPolicyObject, sellerObject;
    public SellerFragment(List<Ebay> ebay) {
        this.ebay=ebay;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View seller_fragment=inflater.inflate(R.layout.seller_info_layout,container,false);
       textView=seller_fragment.findViewById(R.id.s_id);

       try{
        printJsonObjectSeller(ebay.get(0).getE_seller());}
       catch (Exception e){
           e.printStackTrace();
       }

//        shippinglist=shippinglist+ "<li>Feedback Score : &nbsp;&nbsp;"+ebay.get(0).getE_feedback_score()+"</li>";
//        shippinglist=shippinglist+ "<li>User ID : &nbsp;&nbsp;"+ebay.get(0).getE_user_id()+"</li>";
//        shippinglist=shippinglist+ "<li>Positive Feedback Percent : &nbsp;&nbsp;"+ebay.get(0).getE_positive_feed()+"</li>";
//        shippinglist=shippinglist+ "<li>Feedback Rating Star : &nbsp;&nbsp;"+ebay.get(0).getE_feedback_rate()+"</li>";

        textView.setText(Html.fromHtml(shippinglist+shippinglistend, HtmlCompat.FROM_HTML_MODE_LEGACY));

        textView=seller_fragment.findViewById(R.id.rp_id);

        try{
            printJsonObject(ebay.get(0).getE_return_policy());}
        catch (Exception e){
            e.printStackTrace();
        }

        textView.setText(Html.fromHtml(returnPolicylist+returnPolicylistend, HtmlCompat.FROM_HTML_MODE_LEGACY));
        return seller_fragment;
    }

    private void printJsonObjectSeller(JSONObject sellerObject) {
        sellerObject = ebay.get(0).getE_seller();
        JSONArray keys = sellerObject.names();
        shippinglist = "<ul>";
        shippinglistend = "</ul>";

        try{
            shippinglist=shippinglist+"<li><b>&nbsp User I D : </b>"+sellerObject.get("UserID")+"</li>";
        }catch (JSONException e){
            e.printStackTrace();
        }

        try{
            shippinglist=shippinglist+"<li><b>&nbsp Feedback Rating Star : </b>"+sellerObject.get("FeedbackRatingStar")+"</li>";
        }catch (JSONException e){
            e.printStackTrace();
        }

        try{
            shippinglist=shippinglist+"<li><b>&nbsp Feedback Score : </b>"+sellerObject.get("FeedbackScore")+"</li>";
        }catch (JSONException e){
            e.printStackTrace();
        }

        try{
            shippinglist=shippinglist+"<li><b>&nbsp Positive Feedback Percent : </b>"+sellerObject.get("PositiveFeedbackPercent")+"</li>";
        }catch (JSONException e){
            e.printStackTrace();
        }

        try{
            shippinglist=shippinglist+"<li><b>&nbsp Top Rated Seller : </b>"+sellerObject.get("TopRatedSeller")+"</li>";
        }catch (JSONException e){
            e.printStackTrace();
        }


//        for (int i = 0; i < keys.length(); ++i) {
//
//
//            String key = null; // Here's your key
//            String value = null;
//            try {
//                key = keys.getString(i);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            try {
//                value = sellerObject.getString(key);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
////            Log.d("key", key);
////            Log.d("value", value);
//            shippinglist=shippinglist+ "<li>" + key + ":&nbsp;&nbsp;" + value + "</li>";
//
//        }
    }

    public void printJsonObject(JSONObject returnPolicyObject) {
        //JSONObject object = new JSONObject ();

        returnPolicyObject = ebay.get(0).getE_return_policy();
        JSONArray keys = returnPolicyObject.names ();
//        Log.d("returnpolicy", String.valueOf(returnPolicyObject));
//        Log.d("keys", String.valueOf(keys));
        returnPolicylist="<ul>";
        returnPolicylistend="</ul>";

        try{
            returnPolicylist=returnPolicylist+"<li><b>&nbsp Refund : </b>"+returnPolicyObject.get("Refund")+"</li>";
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            returnPolicylist=returnPolicylist+"<li><b>&nbsp Returns Within : </b>"+returnPolicyObject.get("ReturnsWithin")+"</li>";
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            returnPolicylist=returnPolicylist+"<li><b>&nbsp Returns Accepted : </b>"+returnPolicyObject.get("ReturnsAccepted")+"</li>";
        }catch (JSONException e){
            e.printStackTrace();
        }
        try{
            returnPolicylist=returnPolicylist+"<li><b>&nbsp Shipping CostPaid By : </b>"+returnPolicyObject.get("ShippingCostPaidBy")+"</li>";
        }catch (JSONException e){
            e.printStackTrace();
        }



//        for (int i = 0; i < keys.length (); ++i) {
//
//
//            String key = null; // Here's your key
//            String value = null;
//            try {
//                key = keys.getString(i);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            try {
//                value = returnPolicyObject.getString (key);
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
////            Log.d("key", key);
////            Log.d("value", value);
//            returnPolicylist=returnPolicylist+ "<li>"+key+":&nbsp;&nbsp;"+value+"</li>";
//        }

    }

    public void setNewText(String string)
    {
        textView.setText(string);
    }
}
