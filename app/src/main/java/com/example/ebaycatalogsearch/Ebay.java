package com.example.ebaycatalogsearch;

import org.json.JSONArray;
import org.json.JSONObject;

public class Ebay {
    private String e_title;
    private String e_image;
    private String e_condition;
    private String e_price;
    private String e_topRated;
    private String e_shipPrice;
    private String e_productId;
    private String e_user_id;
    private String e_feedback_rate;
    private String e_feedback_score;
    private String e_positive_feed;
    private JSONObject e_seller;
    private JSONObject e_return_policy;
    private JSONObject e_shippingInfo;
    private JSONArray e_pictureURL;
    private JSONArray e_nameValueList1;
    private String  e_brandValue;
    private String e_subTitle;
    private String e_itemUrl;
    private String e_count;



    public Ebay(){}

    public Ebay(String e_title,String e_image, String e_condition, String e_price, String e_topRated, String e_shipPrice, String e_productId, String e_user_id, String e_feedback_rate, String e_feedback_score, String e_positive_feed, JSONObject e_return_policy, JSONObject e_shippingInfo, JSONObject e_seller, JSONArray e_pictureURL, JSONArray e_nameValueList1, String e_brandValue, String e_subTitle, String e_itemUrl, String e_count){
        this.e_title = e_title;
        this.e_image = e_image;
        this.e_condition = e_condition;
        this.e_price = e_price;
        this.e_topRated = e_topRated;
        this.e_shipPrice = e_shipPrice;
        this.e_productId = e_productId;
        this.e_user_id = e_user_id;
        this.e_feedback_rate = e_feedback_rate;
        this.e_feedback_score = e_feedback_score;
        this.e_positive_feed = e_positive_feed;
        this.e_return_policy= e_return_policy;
        this.e_shippingInfo=e_shippingInfo;
        this.e_seller=e_seller;
        this.e_itemUrl=e_itemUrl;
        this.e_count=e_count;
        this.e_pictureURL=e_pictureURL;
        this.e_subTitle=e_subTitle;
        this.e_brandValue=e_brandValue;
    }
    public String getE_title(){
        return e_title;
    }

    public void setE_title(String e_title) {
        this.e_title = e_title;
    }

    public String getE_image() {
        return e_image;
    }

    public void setE_image(String e_image) {
        this.e_image = e_image;
    }

    public String getE_condition() {
        return e_condition;
    }

    public void setE_condition(String e_condition) {
        this.e_condition = e_condition;
    }

    public String getE_price() {
        return e_price;
    }

    public void setE_price(String e_price) {
        this.e_price = e_price;
    }

    public String getE_topRated() {
        return e_topRated;
    }

    public void setE_topRated(String e_topRated) {
        this.e_topRated = e_topRated;
    }

    public String getE_shipPrice() {
        return e_shipPrice;
    }

    public void setE_shipPrice(String e_shipPrice) {
        this.e_shipPrice = e_shipPrice;
    }

    public String getE_productId(){
        return e_productId;
    }

    public void setE_productId(String e_productId) {
        this.e_productId = e_productId;
    }

    public String getE_user_id(){return e_user_id;}

    public void setE_user_id(String e_user_id) {
        this.e_user_id = e_user_id;
    }

    public String getE_feedback_rate() {
        return e_feedback_rate;
    }

    public void setE_feedback_rate(String e_feedback_rate) {
        this.e_feedback_rate = e_feedback_rate;
    }

    public String getE_feedback_score() {
        return e_feedback_score;
    }

    public void setE_feedback_score(String e_feedback_score) {
        this.e_feedback_score = e_feedback_score;
    }

    public String getE_positive_feed() {
        return e_positive_feed;
    }

    public void setE_positive_feed(String e_positive_feed) {
        this.e_positive_feed = e_positive_feed;
    }

    public JSONObject getE_return_policy() {
        return e_return_policy;
    }

    public void setE_return_policy(JSONObject e_return_policy) {
        this.e_return_policy = e_return_policy;
    }

    public JSONObject getE_shippingInfo() {
        return e_shippingInfo;
    }

    public void setE_shippingInfo(JSONObject e_shippingInfo) {
        this.e_shippingInfo = e_shippingInfo;
    }

    public JSONObject getE_seller() {
        return e_seller;
    }

    public void setE_seller(JSONObject e_seller) {
        this.e_seller = e_seller;
    }



    public JSONArray getE_nameValueList1() {
        return e_nameValueList1;
    }

    public void setE_nameValueList1(JSONArray e_nameValueList1) {
        this.e_nameValueList1 = e_nameValueList1;
    }

    public String getE_brandValue() {
        return e_brandValue;
    }

    public void setE_brandValue(String e_brandValue) {
        this.e_brandValue = e_brandValue;
    }

    public String getE_subTitle() {
        return e_subTitle;
    }

    public void setE_subTitle(String e_subTitle) {
        this.e_subTitle = e_subTitle;
    }

    public String getE_itemUrl() {
        return e_itemUrl;
    }

    public void setE_itemUrl(String e_itemUrl) {
        this.e_itemUrl = e_itemUrl;
    }

    public String getE_count() {
        return e_count;
    }

    public void setE_count(String e_count) {
        this.e_count = e_count;
    }

    public JSONArray getE_pictureURL() {
        return e_pictureURL;
    }

    public void setE_pictureURL(JSONArray e_pictureURL) {
        this.e_pictureURL = e_pictureURL;
    }
}
