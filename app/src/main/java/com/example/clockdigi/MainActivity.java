package com.example.clockdigi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.format.DateTimeFormat;

import java.util.Arrays;

import static com.example.clockdigi.SharedData.*;

public class MainActivity extends AppCompatActivity {



    //Handler handler;
    Menu menu;

    DateTime dateTime;
    TextView month, shift;
    ConstraintLayout parent;
    View divUp, divDown;

    Bundle bundle;
    static final int REQUEST_CODE = 2;
    boolean value = false;

    NumericsSet hourL, hourR, minL, minR, secL, secR;
    GestureDetectorCompat detectorCompat;
    Settings settings;
    int screen[] = {R.drawable.mainbkgrd, R.drawable.hourbkgrd, R.drawable.weatherbkgrd};
    int current_bg = 0;
    int hr, min, sec, h1, h2, m1, m2, s1, s2;


    String FINAL_ZONE_TIME = null;

    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Toolbar toolbar;

    int color_beige, color_brown, color_pink, color_brick, color_mustard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        JodaTimeAndroid.init(this);

        intitalSetup();
        setDateTime();
        tryingBlink();
        backgroundCheck();
        colorCheck();

    }

    //initial setup
    private void intitalSetup() {

        parent = findViewById(R.id.parentBkgrd);
        hourL = findViewById(R.id.hoursLeft);
        hourR = findViewById(R.id.hoursRight);
        minL = findViewById(R.id.minutesLeft);
        minR = findViewById(R.id.minutesRight);
        secR = findViewById(R.id.secondsRightCorner);
        secL = findViewById(R.id.secondsLeft);
        month = findViewById(R.id.dayMonth);
        shift = findViewById(R.id.apm);
        divUp = findViewById(R.id.div1);
        divDown = findViewById(R.id.div2);

        dateTime = new DateTime();
        detectorCompat = new GestureDetectorCompat(this, new GestureListener());
       // handler = new Handler();
        bundle = new Bundle();
        settings = new Settings();
        sharedPreferences = getSharedPreferences(SHARED_KEY, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        color_beige = getResources().getColor(R.color.beige);
        color_pink = getResources().getColor(R.color.pinkish);
        color_brick = getResources().getColor(R.color.brick);
        color_mustard = getResources().getColor(R.color.mustard);
        color_brown = getResources().getColor(R.color.brownish);


    }

    //menu functions
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.settings_menu, menu);
        this.menu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.settingsID) {
            Intent intent = new Intent(getApplicationContext(), Settings.class);
            startActivityForResult(intent, REQUEST_CODE);
        }
        return true;

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE) {
            if (resultCode == RESULT_OK) {

                if (data != null) {
                    if ((data.getStringExtra(COLOR_SELECT) != null)) {
                        String color_values = data.getStringExtra(COLOR_SELECT);
                        switch (color_values) {
                            case "Brown":
                                setColorDigits(color_brown);
                                break;
                            case "Light Pink":
                                setColorDigits(color_pink);
                                break;
                            case "Brick":
                                setColorDigits(color_brick);
                                break;
                            case "Mustard":
                                setColorDigits(color_mustard);
                                break;
                            case "Beige":
                                setColorDigits(color_beige);
                                break;
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), "Nothing selected", Toast.LENGTH_LONG).show();
                    }

                }
            }
        }
    }

    //gesture listener
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.detectorCompat.onTouchEvent(event)) {
            return true;
        }
        return super.onTouchEvent(event);
    }

    private class GestureListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {

            int SWIPE_MIN_DISTANCE = 50;
            int SWIPE_THRESHOLD_VELOCITY = 200;

            Log.d(TAG, "current_bg " + current_bg);

            if ((event1.getX() - event2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY)) {

                // swipe right
                if (current_bg < screen.length - 1) current_bg++;
                parent.setBackgroundResource(screen[current_bg]);

                editor.putInt(BACKGROUND_CHECK, screen[current_bg]);
                editor.commit();

            } else if (event2.getX() - event1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                // swipe left
                if (current_bg > 0) current_bg--;
                parent.setBackgroundResource(screen[current_bg]);

                editor.putInt(BACKGROUND_CHECK, screen[current_bg]);
                editor.commit();

            }
                return true;
            }
        }


        //set date time month and year
        public void setDateTime() {
            final Handler handler1 = new Handler();
            Runnable runnable1 = new Runnable() {
                @Override
                public void run() {
                    dateTime = new DateTime();

                    //zonal check
                    DateTimeZone dateTimeZone = dateTime.getZone();
                    String zone_places = sharedPreferences.getString(ZONAL_CODE, null);
                    FINAL_ZONE_TIME = zone_places;
                    if ((FINAL_ZONE_TIME == ZONE_ASIA) || (FINAL_ZONE_TIME == ZONE_LONDON) || (FINAL_ZONE_TIME == ZONE_INDIA) || (FINAL_ZONE_TIME == ZONE_AMERICA)
                            || (FINAL_ZONE_TIME != null)) {
                        DateTimeZone dz = DateTimeZone.forID(FINAL_ZONE_TIME);
                        dateTime = dateTime.withZone(dz);
                    } else {
                        dateTime = dateTime.withZone(dateTimeZone);
                    }


                    //switch check
                    boolean switch_value = sharedPreferences.getBoolean(BOOLEAN_RESULT, value);
                    int hour = dateTime.getHourOfDay();
                    if (switch_value) {

                        if (hour > 12) {
                            dateTime = dateTime.minusHours(12);
                            shift.setText("PM");
                        } else if ((00 < hour) && (hour < 12)) {
                            shift.setText("AM");
                        }
                    } else if (hour >= 12) {
                        shift.setText("PM");
                    } else {
                        shift.setText("AM");
                    }

                    hr = dateTime.getHourOfDay();
                    min = dateTime.getMinuteOfHour();
                    sec = dateTime.getSecondOfMinute();

                    h1 = hr / 10;
                    h2 = hr % 10;

                    m1 = min / 10;
                    m2 = min % 10;

                    s1 = sec / 10;
                    s2 = sec % 10;

                    hourL.setDigit(h1);
                    hourR.setDigit(h2);

                    minL.setDigit(m1);
                    minR.setDigit(m2);

                    secL.setDigit(s1);
                    secR.setDigit(s2);

                    String exact_rep = dateTime.toString(DateTimeFormat.longDate());
                    month.setText(exact_rep);

                    handler1.postDelayed(this, 1000);
                }
            };
            handler1.post(runnable1);
        }

        private void tryingBlink() {
            Animation anim = new AlphaAnimation(0.0f, 1.0f);
            anim.setDuration(500); //You can manage the time of the blink with this parameter
            anim.setStartOffset(20);
            anim.setRepeatMode(Animation.REVERSE);
            anim.setRepeatCount(Animation.INFINITE);
            divUp.startAnimation(anim);
            divDown.startAnimation(anim);
        }

        private void backgroundCheck() {

            int bkgrd_selected = sharedPreferences.getInt(BACKGROUND_CHECK, 0);

            if (bkgrd_selected != -1) {
                int bkgd_weather = R.drawable.weatherbkgrd;
                int bkgd_hour = R.drawable.hourbkgrd;
                int bkgd_main = R.drawable.mainbkgrd;
                if (bkgrd_selected == bkgd_hour) {
                    parent.setBackgroundResource(R.drawable.hourbkgrd);
                } else if (bkgrd_selected == bkgd_weather) {
                    parent.setBackgroundResource(R.drawable.weatherbkgrd);
                } else {
                    parent.setBackgroundResource(R.drawable.mainbkgrd);
                }
            }
        }

        private void colorCheck() {
            String color_selected = sharedPreferences.getString(EACH_COLOR_SELECT, null);
            if (color_selected != null) {
                if (color_selected.equals(beigeColorText)) {
                    setColorDigits( color_beige);
                } else if (color_selected.equals(pinkColorText)) {
                    setColorDigits(color_pink);
                } else if (color_selected.equals(brickColorText)) {
                    setColorDigits( color_brick);
                } else if (color_selected.equals(mustardColorText)) {
                    setColorDigits( color_mustard);
                } else if (color_selected.equals(brownColorText)) {
                    setColorDigits(color_brown);
                }

            }
        }

        private void setColorDigits(int color_choice) {

            hourL.setColor(color_choice);

            hourR.paint.setColor(color_choice);
            minL.paint.setColor(color_choice);
            minR.paint.setColor(color_choice);
            secL.paint.setColor(color_choice);
            secR.paint.setColor(color_choice);
            divUp.setBackgroundColor(color_choice);
            divDown.setBackgroundColor(color_choice);
            shift.setTextColor(color_choice);
            month.setTextColor(color_choice);
        }


    }
