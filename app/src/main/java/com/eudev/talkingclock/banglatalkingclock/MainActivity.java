package com.eudev.talkingclock.banglatalkingclock;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.eudev.talkingclock.banglatalkingclock.ClockServices.SharePreferenceUtils;
import com.eudev.talkingclock.banglatalkingclock.ClockServices.TService;
import com.eudev.talkingclock.banglatalkingclock.ClockServices.Type;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyTalkingCheckBox";
    private static final String TAG = null;
    Activity activity;
    LinearLayout load;
    ScrollView scrollView;
    TextView textView;
    TextView textView2;


    public class CustomDialog extends Dialog {
        Activity activity;
        ImageView imageView;
        RadioButton radioButton1;
        RadioButton radioButton2;
        RadioButton radioButton3;

        public CustomDialog(Activity activity) {
            super(activity, R.style.AppTheme);
            this.activity = activity;
        }

        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Display display = ((WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            DisplayMetrics metrics = new DisplayMetrics();
            display.getMetrics(metrics);
            getWindow().setLayout(Double.valueOf(((double) metrics.widthPixels) * 0.8d).intValue(), Double.valueOf(((double) metrics.heightPixels) * 0.8d).intValue());
            setContentView(R.layout.custom_dialog);
            this.imageView = (ImageView) findViewById(R.id.imageView1);
            this.imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CustomDialog.this.dismiss();
                }
            });

            this.radioButton1 = (RadioButton) findViewById(R.id.radioButton1);
            this.radioButton1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.this.stopService(new Intent(MainActivity.this.getApplicationContext(), TService.class));
                    CustomDialog.this.radioButton2.setChecked(false);
                    CustomDialog.this.radioButton3.setChecked(false);
                    SharePreferenceUtils.putInt(MainActivity.this.getApplicationContext(), Type.moment_time, 15);
                    MainActivity.this.startService(new Intent(MainActivity.this.getApplicationContext(), TService.class));
                }
            });

            this.radioButton2 = (RadioButton) findViewById(R.id.radioButton2);
            this.radioButton2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MainActivity.this.stopService(new Intent(MainActivity.this.getApplicationContext(), TService.class));
                    CustomDialog.this.radioButton1.setChecked(false);
                    CustomDialog.this.radioButton3.setChecked(false);
                    SharePreferenceUtils.putInt(MainActivity.this.getApplicationContext(), Type.moment_time, 30);
                    MainActivity.this.startService(new Intent(MainActivity.this.getApplicationContext(), TService.class));
                }
            });



            this.radioButton3 = (RadioButton) findViewById(R.id.radioButton3);
            this.radioButton3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    MainActivity.this.stopService(new Intent(MainActivity.this.getApplicationContext(), TService.class));
                    CustomDialog.this.radioButton2.setChecked(false);
                    CustomDialog.this.radioButton1.setChecked(false);
                    SharePreferenceUtils.putInt(MainActivity.this.getApplicationContext(), Type.moment_time, 60);
                    MainActivity.this.startService(new Intent(MainActivity.this.getApplicationContext(), TService.class));
                }
            });

            int timeset = SharePreferenceUtils.getInt(MainActivity.this.getApplicationContext(), Type.moment_time, 0);
            if (timeset == 15) {
                this.radioButton1.setChecked(true);
            } else if (timeset == 30) {
                this.radioButton2.setChecked(true);
            } else if (timeset == 60) {
                this.radioButton3.setChecked(true);
            }
        }
    }




    //TODO Mai Activity onCreate method

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //sub();
        this.scrollView = (ScrollView) findViewById(R.id.scrollView1);
        this.scrollView.setVisibility(View.VISIBLE);
        this.load = (LinearLayout) findViewById(R.id.layload);
        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                if (key.equals("link")) {
                    startActivity(new Intent("android.intent.action.VIEW", Uri.parse(getIntent().getExtras().getString(key))));
                    finish();
                }
            }
        }
        this.activity = this;
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        this.textView = (TextView) findViewById(R.id.sleep);
        this.textView2 = (TextView) findViewById(R.id.startText);
        final Switch Switch_Sleep = (Switch) findViewById(R.id.switch1);
        Switch_Sleep.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences settings = MainActivity.this.getSharedPreferences(MainActivity.PREFS_NAME, 0);
                String string = settings.getString("switch", "off");
                if (string.equals("on")) {
                    String str = BuildConfig.FLAVOR;
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("switch", "off");
                    editor.commit();
                    Switch_Sleep.setChecked(false);
                    MainActivity.this.textView.setText("\u09a8\u09be");
                    MainActivity.this.showToast("off", "\u09e8\u09ea \u0998\u09a8\u09cd\u099f\u09be \u09b8\u09ae\u09df \u09ac\u09b2\u09ac\u09c7");
                    SharePreferenceUtils.putBoolean(MainActivity.this, Type.SLEEP_MODE, false);
                }

                if (string.equals("off")) {
                    String str = BuildConfig.FLAVOR;
                    SharedPreferences.Editor editor = settings.edit();
                    editor = settings.edit();
                    editor.putString("switch", "on");
                    editor.commit();
                    Switch_Sleep.setChecked(true);
                    MainActivity.this.textView.setText("\u09b9\u09cd\u09af\u09be");
                    MainActivity.this.showToast("on", "\u09b0\u09be\u09a4 \u09e7\u09e8.\u09e6\u09e6 \u09a5\u09c7\u0995\u09c7 \u09ad\u09cb\u09b0 \u09e6\u09ec.\u09ea\u09eb \u09aa\u09b0\u09cd\u09af\u09a8\u09cd\u09a4 \u09b8\u09ae\u09df \u09ac\u09b2\u09be \u09ac\u09a8\u09cd\u09a7  \u09b9\u09df\u09c7\u099b\u09c7");
                    SharePreferenceUtils.putBoolean(MainActivity.this, Type.SLEEP_MODE, true);
                }
            }
        });
        final Switch aSwitch2 = (Switch) findViewById(R.id.startswitch);
        aSwitch2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                SharedPreferences settings = MainActivity.this.getSharedPreferences(MainActivity.PREFS_NAME, 0);
                String string = settings.getString("startswitch", "startoff");
                if (string.equals("starton")) {
                    String str = BuildConfig.FLAVOR;
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString("startswitch", "startoff");
                    editor.commit();
                    MainActivity.this.textView2.setText("\u09b8\u09ae\u09df \u09ac\u09b2\u09be \u09ac\u09a8\u09cd\u09a7 \u09b9\u09df\u09c7\u099b\u09c7");
                    aSwitch2.setChecked(false);
                    SharePreferenceUtils.putInt(MainActivity.this.getApplicationContext(), Type.moment_time, 0);
                    MainActivity.this.showToast("alarm_off", "\u09b8\u09ae\u09df \u09ac\u09b2\u09be \u09ac\u09a8\u09cd\u09a7 \u09b9\u09df\u09c7\u099b\u09c7");
                }
                if (string.equals("startoff")) {
                    String str = BuildConfig.FLAVOR;
                    SharedPreferences.Editor editor = settings.edit();
                    editor = settings.edit();
                    editor.putString("startswitch", "starton");
                    editor.commit();
                    MainActivity.this.textView2.setText("\u09b8\u09ae\u09df \u09ac\u09b2\u09be \u099a\u09be\u09b2\u09c1 \u09b9\u09df\u09c7\u099b\u09c7");
                    aSwitch2.setChecked(true);
                    SharePreferenceUtils.putInt(MainActivity.this.getApplicationContext(), Type.moment_time, 15);
                    MainActivity.this.startService(new Intent(MainActivity.this.getApplicationContext(), TService.class));
                    MainActivity.this.showToast("alarm_on", "\u09b8\u09ae\u09df \u09ac\u09b2\u09be \u099a\u09be\u09b2\u09c1 \u09b9\u09df\u09c7\u099b\u09c7");
                }
            }
        });
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        String skipMessage = settings.getString("startswitch", "d");
        String string = settings.getString("switch", "d");
        if (skipMessage.equals("starton")) {
            aSwitch2.setChecked(true);
            this.textView2.setText("\u09b8\u09ae\u09df \u09ac\u09b2\u09be \u099a\u09be\u09b2\u09c1 \u0986\u099b\u09c7");
        }
        if (skipMessage.equals("startoff")) {
            aSwitch2.setChecked(false);
            this.textView2.setText("\u09b8\u09ae\u09df \u09ac\u09b2\u09be \u09ac\u09a8\u09cd\u09a7 \u0986\u099b\u09c7");
        }
        if (string.equals("on")) {
            Switch_Sleep.setChecked(true);
            SharePreferenceUtils.putBoolean(this, Type.SLEEP_MODE, true);
            this.textView.setText("\u09b9\u09cd\u09af\u09be");
        }
        if (string.equals("off")) {
            this.textView.setText("\u09a8\u09be");
            Switch_Sleep.setChecked(false);
            SharePreferenceUtils.putBoolean(this, Type.SLEEP_MODE, false);
        }
//        mInterstitialAd = newInterstitialAd();
//        loadInterstitial();
//        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//        drawer.setDrawerListener(toggle);
//        toggle.syncState();
//        ((NavigationView) findViewById(R.id.nav_view)).setNavigationItemSelectedListener(this);


  }



    private void wc() {
        this.scrollView.setVisibility(View.VISIBLE);
        this.load.setVisibility(View.VISIBLE);
    }

    private void showToast(String image, String message) {
        Toast toast = new Toast(getApplicationContext());
        toast.setDuration(Toast.LENGTH_SHORT);
        View mainLayout = ((LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.toast_layout, null);
        View rootLayout = mainLayout.findViewById(R.id.toast_layout_root);
        ImageView imageView = (ImageView) mainLayout.findViewById(R.id.image);
        if (image.equals("on")) {
            imageView.setImageResource(R.drawable.sound_off);
        } else if (image.equals("off")) {
            imageView.setImageResource(R.drawable.sound_on);
        } else if (image.equals("alarm_on")) {
            imageView.setImageResource(R.drawable.alarm_on);
        } else if (image.equals("alarm_off")) {
            imageView.setImageResource(R.drawable.alarm_off);
        }
        ((TextView) mainLayout.findViewById(R.id.text)).setText(message);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(49, 10, R.styleable.AppCompatTheme_ratingBarStyleSmall);
        toast.setMargin(0.0f, 0.0f);
        toast.setView(rootLayout);
        toast.show();
    }

//    private void shareIt() {
//        Intent sharingIntent = new Intent("android.intent.action.SEND");
//        sharingIntent.setType("text/plain");
//        sharingIntent.putExtra("android.intent.extra.TEXT", "\u0986\u09ae\u09be\u09b0 \u0995\u09be\u099b\u09c7 \u0985\u09cd\u09af\u09be\u09aa \u099f\u09bf \u0985\u09a8\u09c7\u0995 \u09ad\u09be\u09b2\u09cb \u09b2\u09be\u0997\u099b\u09c7. \u0986\u09aa\u09a8\u09bf\u0993 \u09a1\u09be\u0989\u09a8\u09b2\u09cb\u09a1 \u0995\u09b0\u09a4\u09c7 \u09aa\u09be\u09b0\u09c7\u09a8" + BuildConfig.FLAVOR + ": https://play.google.com/store/apps/details?id=" + getPackageName());
//        startActivity(Intent.createChooser(sharingIntent, "Share via HM SOFT"));
//    }



//    private void rateIt() {
//        startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + getPackageName())));
//    }

    public void onBackPressed() {
         super.onBackPressed();

    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings /*2131624122*/:
                new CustomDialog(this.activity).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }




}
