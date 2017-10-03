package com.example.joaos.ementas;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;

public class UaMenusParserUtility {


    /**
     * parses the web service response to instantiate and fill a FoodMenus object
     * @param jsonResults the response from the remote web service
     * @return a navigable collection of daily food options
     * @throws IOException
     */
    static public UAMenus parseJson(String jsonResults) {
        UAMenus menusToReturn = new UAMenus();  // return object

        JSONArray menuOptionsArray, jsonArray2;
        JSONObject workingJsonObject;
        DailyOption dailyOption;
        SimpleDateFormat dateParser = new SimpleDateFormat("EEE, dd MMM yyyy", Locale.US);

        try {
            workingJsonObject = new JSONObject(jsonResults);    // access root object
            workingJsonObject = workingJsonObject.getJSONObject("menus"); // access menus within root
            menuOptionsArray = workingJsonObject.getJSONArray("menu"); // options for a zone

            // go through the several entries (day,canteen)
            for (int dailyEntry = 0; dailyEntry < menuOptionsArray.length(); dailyEntry++) {
                dailyOption = new DailyOption(
                        dateParser.parse(menuOptionsArray.getJSONObject(dailyEntry).getJSONObject("@attributes").getString("date")),
                        menuOptionsArray.getJSONObject(dailyEntry).getJSONObject("@attributes").getString("canteen"),
                        menuOptionsArray.getJSONObject(dailyEntry).getJSONObject("@attributes").getString("meal"),
                        menuOptionsArray.getJSONObject(dailyEntry).getJSONObject("@attributes").getString("disabled").compareTo("0") == 0);

                if (dailyOption.isAvailable()) {
                    jsonArray2 = menuOptionsArray.getJSONObject(dailyEntry).getJSONObject("items").getJSONArray("item");
                    // get the several meals in a canteen, in a day
                    dailyOption.addMealCourse(new Meal(UAMenus.COURSE_ORDER_SOUP, parseForObjectOrString(jsonArray2, UAMenus.COURSE_ORDER_SOUP) ));
                    dailyOption.addMealCourse(new Meal(UAMenus.COURSE_ORDER_MEAT_NORMAL, parseForObjectOrString(jsonArray2, UAMenus.COURSE_ORDER_MEAT_NORMAL) ));
                    dailyOption.addMealCourse(new Meal(UAMenus.COURSE_ORDER_FISH_NORMAL, parseForObjectOrString(jsonArray2, UAMenus.COURSE_ORDER_FISH_NORMAL) ));
                    //// TODO: complete with more meal courses
                }
                menusToReturn.add(dailyOption);
            }
        } catch (JSONException | ParseException ex) {
            ex.printStackTrace();
        }
        return menusToReturn;
    }



    /**
     * parses the meal course options; the JSON is not coherent, and can be an object or a string
     * @param array array with the meal options
     * @param index position to retrieve
     * @return the meal course option
     * @throws JSONException
     */
    static private String parseForObjectOrString(JSONArray array, int index) throws JSONException {
        JSONObject tempJsonObject = array.optJSONObject(index);
        if( null == tempJsonObject ) {
            // no json object, treat as string
            return array.getString(index);
        } else {
            return array.getJSONObject(index).getJSONObject("@attributes").getString("name");
        }

    }
}
