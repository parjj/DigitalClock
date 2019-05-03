package com.example.clockdigi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Display;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Switch;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "settings";
    private ListView listView;

    private List<String> dataList;
    private ArrayAdapter<String> adapter;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    Button mustardColorB5, pinkColorB1, brickColorB2, brownColorB3, beigeColorB4, common_Button;
    Switch aSwitch;
    Intent timeZoneIntent;

    private static final String ZONE_INDIA = "Indian/Mahe";
    private static final String ZONE_ASIA = "Asia/Singapore";
    private static final String ZONE_LONDON = "Europe/London";
    private static final String ZONE_AMERICA = "America/New_York";
    private static final String ZONAL_CODE = "zone area";
    private static final String COLOR_SELECT = "color choice";
    private static final String EACH_COLOR_SELECT = "each color";
    private static final String BOOLEAN_RESULT = "boolean value";
    private static final String SHARED_KEY = "digital clock";
    private static String pinkColorText = null, brickColorText = null, brownColorText = null, beigeColorText = null, mustardColorText = null;
    private HashMap<View, Boolean> colorButtonStatus;
    private HashMap<Button, String> colorButtonText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        inti();
        onitemlistner();
        savedStates();

    }

    private void onitemlistner() {

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String zone_chosen = (String) listView.getItemAtPosition(position);
                view.setBackgroundColor(getResources().getColor(R.color.beige));
                view.setPressed(true);
                editor.putString(ZONAL_CODE, zone_chosen);
                editor.putInt("position", position);
                editor.commit();
            }
        });
    }


    private void inti() {
        pinkColorB1 = findViewById(R.id.b1);
        Log.d("pink", String.valueOf(pinkColorB1));
        brickColorB2 = findViewById(R.id.b2);
        brownColorB3 = findViewById(R.id.b3);
        beigeColorB4 = findViewById(R.id.b4);
        mustardColorB5 = findViewById(R.id.b5);
        aSwitch = findViewById(R.id.ampmMode);
        listView = findViewById(R.id.list);

        pinkColorText = pinkColorB1.getText().toString();
        brickColorText = brickColorB2.getText().toString();
        brownColorText = brownColorB3.getText().toString();
        beigeColorText = beigeColorB4.getText().toString();
        mustardColorText = mustardColorB5.getText().toString();


        dataList = new ArrayList<>();
        dataList.add(ZONE_INDIA);
        dataList.add(ZONE_ASIA);
        dataList.add(ZONE_LONDON);
        dataList.add(ZONE_AMERICA);
//        dataList.addAll( Arrays.asList(TimeZone.getAvailableIDs())  );
        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice
                , dataList);

        listView.setAdapter(adapter);

        sharedPreferences = getSharedPreferences(SHARED_KEY, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        colorButtonStatus = new HashMap<View, Boolean>();
        colorButtonStatus.put(pinkColorB1, false);
        colorButtonStatus.put(brickColorB2, false);
        colorButtonStatus.put(brownColorB3, false);
        colorButtonStatus.put(beigeColorB4, false);
        colorButtonStatus.put(mustardColorB5, false);

        colorButtonText = new HashMap<Button, String>();
        colorButtonText.put(pinkColorB1, pinkColorText);
        colorButtonText.put(brickColorB2, brickColorText);
        colorButtonText.put(brownColorB3, brownColorText);
        colorButtonText.put(beigeColorB4, beigeColorText);
        colorButtonText.put(mustardColorB5, mustardColorText);

        pinkColorB1.setOnClickListener(this);
        brickColorB2.setOnClickListener(this);
        brownColorB3.setOnClickListener(this);
        beigeColorB4.setOnClickListener(this);
        mustardColorB5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

       // buttonDeselect(v);
        selectStatus(v);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            this.finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public void selectStatus(View view) {

        colorButtonStatus.put(view, !colorButtonStatus.get(view));

        String colorText = colorButtonText.get(view);

        if (colorButtonStatus.get(view)) {

            for(View vw : colorButtonStatus.keySet()){
                if(view.equals(vw)) {
                    view.setBackgroundResource(R.drawable.button_background_pressed);
                }else{
                    vw.setBackgroundResource(R.drawable.button_background);
                }
                }
            } else {
                view.setBackgroundResource(R.drawable.button_background);
            }

            editor.putString(EACH_COLOR_SELECT, colorText);
            editor.commit();

            timeZoneIntent = new Intent(this, MainActivity.class);
            timeZoneIntent.putExtra(COLOR_SELECT, colorText);
            setResult(RESULT_OK, timeZoneIntent);


        }

        //switch works
        public void switchCall (View view){

            if (aSwitch.isChecked()) {
                editor.putBoolean(BOOLEAN_RESULT, true);
                editor.commit();
            } else {
                editor.putBoolean(BOOLEAN_RESULT, false);
                editor.commit();
            }
        }


        private void savedStates () {

            boolean switch_value = sharedPreferences.getBoolean(BOOLEAN_RESULT, false);
            aSwitch.setChecked(switch_value);

            final int pos = sharedPreferences.getInt("position", 0);
            listView.post(new Runnable() {
                @Override
                public void run() {
                    listView.setItemChecked(pos, true);
                    listView.setSelection(pos);
                }
            });


            String color_selected = sharedPreferences.getString(EACH_COLOR_SELECT, null);


            switch (color_selected) {
                case "Brown":
                    brownColorB3.setBackgroundResource(R.drawable.button_background_pressed);
                    break;
                case "Light Pink":
                    pinkColorB1.setBackgroundResource(R.drawable.button_background_pressed);
                    break;
                case "Brick":
                    brickColorB2.setBackgroundResource(R.drawable.button_background_pressed);
                    break;
                case "Mustard":
                    mustardColorB5.setBackgroundResource(R.drawable.button_background_pressed);
                    break;
                case "Beige":
                    beigeColorB4.setBackgroundResource(R.drawable.button_background_pressed);
                    break;
                default:
                    pinkColorB1.setBackgroundResource(R.drawable.button_background);
                    brickColorB2.setBackgroundResource(R.drawable.button_background);
                    brownColorB3.setBackgroundResource(R.drawable.button_background);
                    beigeColorB4.setBackgroundResource(R.drawable.button_background);
                    mustardColorB5.setBackgroundResource(R.drawable.button_background);
            }


        }


    }
