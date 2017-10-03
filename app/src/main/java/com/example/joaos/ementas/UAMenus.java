package com.example.joaos.ementas;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import com.example.joaos.ementas.Meal;
public class UAMenus {

    static final public int COURSE_ORDER_SOUP = 0;
    static final public int COURSE_ORDER_MEAT_NORMAL = 1;
    static final public int COURSE_ORDER_FISH_NORMAL = 2;

    private List<DailyOption> dailyMenusPerCanteen = new ArrayList<>();

    public void add(DailyOption dailyOption) {
        this.getDailyMenusPerCanteen().add(dailyOption);
    }

    /**
     * dumps the content of the object into a string for logging/dubbuging
     * @return the contents as String
     */
    public String formatedContentsForDebugging() {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        StringBuilder builder = new StringBuilder();
        for (DailyOption option : this.getDailyMenusPerCanteen()) {
            builder.append("\nDay: ");
            builder.append(dateFormater.format(option.getDate()));
            builder.append("\nCanteen: ");
            builder.append(option.getCanteenSite());
            builder.append("\nMeal type: ");
            builder.append(option.getDailyMeal());
            builder.append("\nIs open? ");
            builder.append(option.isAvailable());
            builder.append("\n");
            for (Meal mealOption: option.getMealCourseList() ) {
                builder.append("\nCourse: ");   builder.append(mealOption.getMealCourseOrder());
                builder.append("\nmeal: ");   builder.append(mealOption.getFoodOptionDescription());
                builder.append("\n");
            }
        }
        return builder.toString();
    }

    public List<DailyOption> getDailyMenusPerCanteen() {
        return dailyMenusPerCanteen;
    }

    public void sortByCanteen() {
        Collections.sort(dailyMenusPerCanteen, new Comparator<DailyOption>() {
            @Override
            public int compare(DailyOption do1, DailyOption do2) {
                int same = do1.getCanteenSite().compareTo( do2.getCanteenSite());
                if( same == 0) {
                    return do1.getDate().compareTo( do2.getDate());

                }
                return same;

            }
        });

    }

    public void filterByCanteen(String selectedSite) {
        List<DailyOption> filtered = new ArrayList<>();
        for ( DailyOption entry: getDailyMenusPerCanteen()  ) {
            if( entry.getCanteenSite().compareToIgnoreCase( selectedSite) == 0) {
                filtered.add(entry);
            }
        }
        dailyMenusPerCanteen = filtered;
    }

    /**
     * todo: verify list and complete
     * @return
     */
    public static List<String> getDefaultCanteens() {
        ArrayList<String> sites = new ArrayList<>();
        sites.add( "Refeitório de Santiago");
        sites.add( "Refeitório do Crasto");
        sites.add( "Snack-Bar/Self");
        return sites;
    }
}
