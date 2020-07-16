package com.example.ebaycatalogsearch;

import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.text.HtmlCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.List;


public class ProductFragment extends Fragment {
    List<Ebay> ebay;
    String[] shipping_list;
    TextView subtitle, subtitle_label, prod_features;
    TextView brand, brand_label;
    TextView specText;
    JSONObject nameValueList1;
    View product_view;
    String brand_var=null;
    String subtitle_var=null;
    String speclist,speclistend;
    ProgressBar progressBar;
    TextView progressText;
    TextView title_view ,currentPrice_view ,shippingServiceCost_view;
    String title,currentPrice,shippingServiceCost;
    ImageView prod;
    View line1;
   ProductFragmentAdapter productFragmentAdapter;
    public ProductFragment(List<Ebay> ebay, String title, String currentPrice, String shippingServiceCost) {
        this.ebay=ebay;
        this.title=title;
        this.currentPrice=currentPrice;
        this.shippingServiceCost=shippingServiceCost;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        product_view=inflater.inflate(R.layout.product_layout,container,false);
        subtitle = product_view.findViewById(R.id.subtitle_id);
        brand=product_view.findViewById(R.id.brand_id);
        specText=product_view.findViewById(R.id.specifications_text_id);
        progressBar=product_view.findViewById(R.id.progress_id);
        progressText=product_view.findViewById(R.id.progressText_id);
        title_view=product_view.findViewById(R.id.title_id);
        currentPrice_view=product_view.findViewById(R.id.price_id);
        shippingServiceCost_view=product_view.findViewById(R.id.shipPrice_id);
        prod = product_view.findViewById(R.id.product_info_id);
        line1 = product_view.findViewById(R.id.line1);
        subtitle_label=product_view.findViewById(R.id.subtitleLabel_id);
        brand_label=product_view.findViewById(R.id.brandLabel_id);
        prod_features=product_view.findViewById(R.id.prodFeatures_id);

        title_view.setText(title);

        currentPrice_view.setText("$"+currentPrice);

        String start = "<b>";
        String end = "</b>";

        if(shippingServiceCost.equals("0.0")){

            shippingServiceCost_view.setText(Html.fromHtml(start+"FREE "+end+ "Shipping", HtmlCompat.FROM_HTML_MODE_LEGACY));
            //shippingServiceCost_view.setText(start+ "FREE"+end+" Shipping");
        }
        else{
            shippingServiceCost_view.setText(Html.fromHtml("Ships for "+ start +"$"+end + start+ shippingServiceCost+ end,HtmlCompat.FROM_HTML_MODE_LEGACY));
        }


//        if (subtitle_var == null && brand_var == null){
//            //Log.d("subtitle", subtitle_var);
//            //Log.d("brand",brand_var);
//            prod.setVisibility(View.GONE);
//            line1.setVisibility(View.GONE);
//            subtitle.setVisibility(View.GONE);
//            brand.setVisibility(View.GONE);
//            subtitle_label.setVisibility(View.GONE);
//            brand_label.setVisibility(View.GONE);
//            prod_features.setVisibility(View.GONE);
//        }
//        else if( subtitle_var == null){
//            subtitle.setVisibility(View.GONE);
//            subtitle_label.setVisibility(View.GONE);
//        }
//
//        else if(brand_var == null)
//        {
//            brand.setVisibility(View.GONE);
//            brand_label.setVisibility(View.GONE);
//        }
//        else {
//            subtitle_var = ebay.get(0).getE_subTitle();
//            subtitle.setText(subtitle_var);
//            Log.d("subtitle", subtitle_var);
//            brand_var = ebay.get(0).getE_brandValue();
//            brand.setText(brand_var);
//            Log.d("brand",brand_var);
        boolean q1=false,q2=false;
            try {
                subtitle_var = ebay.get(0).getE_subTitle();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (subtitle_var != "null") {
                subtitle.setText(subtitle_var);
            }
            else{
                subtitle.setVisibility(View.GONE);
                subtitle_label.setVisibility(View.GONE);
                q1=true;
            }

            try {
                brand_var = ebay.get(0).getE_brandValue();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (brand_var != "null") {
                brand.setText(brand_var);
            }
            else{
                brand.setVisibility(View.GONE);
                brand_label.setVisibility(View.GONE);
                q2=true;
            }
            if(q1 && q2)
            {
                prod.setVisibility(View.GONE);
                line1.setVisibility(View.GONE);
                prod_features.setVisibility(View.GONE);
            }
//        }

        //specText.setText(ebay.get(0).getE_nameValueList1().toString());
        specText=product_view.findViewById(R.id.specifications_text_id);
            try{
        printJsonObjectSpec(ebay.get(0).getE_nameValueList1());}
            catch (Exception e)
            {
                e.printStackTrace();
            }
        progressBar.setVisibility(View.GONE);
        progressText.setVisibility(View.GONE);

        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.HORIZONTAL,false);
        RecyclerView recyclerView=product_view.findViewById(R.id.horizontal_recycler);
        recyclerView.setLayoutManager(linearLayoutManager);
//        Log.d("pic", String.valueOf(ebay.get(0).getE_pictureURL()));
        try{
        ProductFragmentAdapter adapter=new ProductFragmentAdapter(getActivity().getApplicationContext(),ebay.get(0).getE_pictureURL());
            recyclerView.setAdapter(adapter);
        }
        catch (Exception e){
            e.printStackTrace();
        }

        specText.setText(Html.fromHtml(speclist+speclistend, HtmlCompat.FROM_HTML_MODE_LEGACY));

        return product_view;

    }

    private void printJsonObjectSpec(JSONArray e_nameValueList1) {
        JSONArray keys = e_nameValueList1;
        String s=null;
        speclist = "<ul>";
        speclistend = "</ul>";
        for (int i = 0; i < keys.length(); ++i) {
            try {
               // JSONObject object=keys.getJSONObject(i);
                //String value=object.getString(String.valueOf(i));
            //    Log.d("value_munnu", value);
                 s = keys.getString(i);
                Log.d("keys", keys.getString(i));
           //     Log.d("s",  s.substring(2,s.length()-2));
           //   Log.d("keys", keys.getString(i));

            } catch (JSONException e) {
                e.printStackTrace();
            }
      //      Log.d("len", String.valueOf(keys.length()));
            speclist=speclist+ "<li>" +s.substring(2,s.length()-2) + "</li>";
        }


    }
    public void setNewText(String string)
    {
        specText.setText(string);
    }
}





  //     printJsonObjectSpec(ebay.get(0).getE_nameValueList1());


    //  product_view.setText(Html.fromHtml(prodFeatureslist+prodFeatureslistend, HtmlCompat.FROM_HTML_MODE_LEGACY));


//    private void printJsonObjectSpec(JSONObject nameValueList1) {
//        nameValueList1 = ebay.get(0).getE_nameValueList1();
//        speclist = "<ul>";
//        speclistend = "</ul>";
//        for (int i = 0; i < nameValueList1.length(); ++i) {
//            String value = null;
//            try {
//                value = nameValueList1.getString(String.valueOf(i));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//            Log.d("val", String.valueOf(nameValueList1));
//    //    }
//    }

//    public void setNewText(String string)
//    {
//        product_view.setText(string);
//    }

