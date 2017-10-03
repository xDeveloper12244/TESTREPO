package com.example.joaos.ementas;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private String selectedSite = null;
    private UAApi client = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        final ArrayAdapter<String> adapterItem = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_activated_1, UAMenus.getDefaultCanteens());
        ListView listView = (ListView) findViewById(R.id.lstSites);
        listView.setAdapter(adapterItem);
        listView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedSite = adapterItem.getItem(position);
            }
        });

    }

    public void buttonGetMenus_OnAction(View view) {

        String url = this.getString(R.string.food_menus_endpoint);
        if (null == client) {
            client = new UAApi(url);
        }


        client.getMenusForCanteen(this, selectedSite, new UAApi.UAMenusApiResponseListener() {
            @Override
            public void handleRetrievedResults(UAMenus response) {
                Toast.makeText(MainActivity.this, "Got " + response.getDailyMenusPerCanteen().size() + " results! See the log for more.", Toast.LENGTH_SHORT).show();
                Log.i("FoCa", response.formatedContentsForDebugging());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
