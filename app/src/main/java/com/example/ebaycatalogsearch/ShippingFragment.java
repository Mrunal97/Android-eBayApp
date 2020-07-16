package com.example.ebaycatalogsearch;

import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static android.text.TextUtils.replace;

public class ShippingFragment extends Fragment {
    //private final String id;
    ListView listView;
    String[] shipping_list;
    List<Ebay> ebay;
    String shippinglist;
    String shippinglistend;
    TextView shipInfo;
    JSONObject shippingObject;
    String shippingInfoStr;



    public ShippingFragment(List<Ebay> ebay, String shippingInfo) {
        this.ebay = ebay;
        this.shippingInfoStr = shippingInfo;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View shippingFragmentView = inflater.inflate(R.layout.shipping_layout, container, false);
        shipInfo = shippingFragmentView.findViewById(R.id.shipInfo_id);
        printJsonObjectShipping(shippingInfoStr);
        shipInfo.setText(Html.fromHtml(shippinglist+shippinglistend, HtmlCompat.FROM_HTML_MODE_LEGACY));

        return shippingFragmentView;
    }

    private void printJsonObjectShipping(String shippingObject) {
        try {
            JSONObject json = new JSONObject(shippingObject);

            shippinglist = "<ul>";
            shippinglistend = "</ul>";
            JSONArray keys = json.names();

            try{
                String s = json.getString("shippingType");
                Log.d("s",s);
                shippinglist=shippinglist+"<li><b>&nbsp Shipping Type : </b>"+s.substring(2,s.length()-2)+"</li>";
            }catch (JSONException e){
                e.printStackTrace();
            }
            try{
                String s = json.getString("shipToLocations");
                Log.d("s",s);
                shippinglist=shippinglist+"<li><b>&nbsp Ship To Locations : </b>"+s.substring(2,s.length()-2)+"</li>";
            }catch (JSONException e){
                e.printStackTrace();
            }
            try{
                String s = json.getString("expeditedShipping");
                Log.d("s",s);
                shippinglist=shippinglist+"<li><b>&nbsp Expedited Shipping : </b>"+s.substring(2,s.length()-2)+"</li>";
            }catch (JSONException e){
                e.printStackTrace();
            }
            try{
                String s = json.getString("oneDayShippingAvailable");
                Log.d("s",s);
                shippinglist=shippinglist+"<li><b>&nbsp One Day Shipping Available : </b>"+s.substring(2,s.length()-2)+"</li>";
            }catch (JSONException e){
                e.printStackTrace();
            }
            try{
                String s = json.getString("handlingTime");
                Log.d("s",s);
                shippinglist=shippinglist+"<li><b>&nbsp Handling Time: </b>"+s.substring(2,s.length()-2)+"</li>";
            }catch (JSONException e){
                e.printStackTrace();
            }
            try{
                String s = json.getString("shippingFrom");
                Log.d("s",s);
                shippinglist=shippinglist+"<li><b>&nbsp Shipping From : </b>"+s.substring(2,s.length()-2)+"</li>";
            }catch (JSONException e){
                e.printStackTrace();
            }



//            for (int i = 0; i < keys.length(); ++i) {
//
//
//                String key = null; // Here's your key
//                String value = null;
//                JSONArray valArray = new JSONArray();
//                try {
//                    key = keys.getString(i);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                try {
//                    value = json.getString(key);
//                    valArray = new JSONArray(value);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//                Log.d("key", key);
//                Log.d("value", valArray.getString(0));
//                shippinglist=shippinglist+ "<li>" +(key)+ ":&nbsp;&nbsp;" + valArray.getString(0) + "</li>";
//
//            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

//        shipInfo.setText(shippingInfoStr);
        }

//    private String caseConversion(String key) {
//        return key.replace(/([a-z])([A-Z])/g, '$1 $2').replace(/\b([A-Z]+)([A-Z])([a-z])/, '$1 $2$3').replace(/^./, function(str){ return str.toUpperCase(); });
//
//
//    }

    public void setNewText(String string)
    {
        shipInfo.setText(string);
    }

}




