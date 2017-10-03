package com.example.joaos.ementas;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONObject;


public class UAApi {
    private final String url;

    public interface UAMenusApiResponseListener {
        void handleRetrievedResults(UAMenus response);
    }

    public UAApi(String url) {
        this.url = url;
    }

    /**
     * calls the remote resource adn delivers the food options to the listener object, asynchronously,
     * by using on the Volley library
     * @param context   calling activity
     * @param selectedSite  the id (name) of the canteen. if null, retrieves all canteens
     * @param resultsHandler  the callback object. note that the response is asynchronous
     */
    public void getMenusForCanteen(Context context, final String selectedSite, final UAMenusApiResponseListener resultsHandler) {

        RequestQueue queue = Volley.newRequestQueue(context);

        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        UAMenus menus = UaMenusParserUtility.parseJson(response.toString());
                        // should the results be filter for a given canteen?
                        if (null == selectedSite) {
                            menus.sortByCanteen();
                        } else {
                            menus.filterByCanteen(selectedSite);
                        }
                        resultsHandler.handleRetrievedResults(menus);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("FoCa", "Errors calling endpoint: ".concat(error.getLocalizedMessage()));
                    }
                });

        // Add the request to the RequestQueue.
        Log.i("FoCa", "request queued");
        queue.add(jsObjRequest);
    }
}
