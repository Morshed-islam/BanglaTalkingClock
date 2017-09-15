package com.eudev.talkingclock.banglatalkingclock.ClockServices;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Handler;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;

import com.eudev.talkingclock.banglatalkingclock.R;

import java.util.Calendar;

/**
 * Created by Morshed on 9/2/2017.
 */

public class TService extends Service {

    public static final String MYPREFERENCE = "MyPeferenceTalkingClock";
    static int NowTime;
    static int doon;
    static int h = 0;
    static int r = 0;
    static int sound0 = 0;
    static int sound1 = 0;
    static int sound2 = 0;
    static int sound3 = 0;
    static int tH = 12;
    static int tM = 0;
    static int tP = 0;
    static int tS = 0;
    static int time_say;
    boolean flag;
    int l_a = 1;
    private Handler myHandler = new Handler();
    PowerManager powerManager;
    SharedPreferences sharedpreferences;
    private SoundPool soundPool;
    PowerManager.WakeLock wakeLock;



    class TalkkingTime implements Runnable {
        int talking_moment;

        TalkkingTime(int p) {
            this.talking_moment = p;
        }


        public void run() {
            if (TService.this.flag) {
                TService.this.collectTime();
                TService.this.soundPool = new SoundPool(1, 3, 0);
                if (this.talking_moment == 15) {
                    if (TService.tM == 15 || TService.tM == 30 || TService.tM == 45 || TService.tM == 0) {
                        if (!SharePreferenceUtils.getBoolean(TService.this.getApplicationContext(), Type.SLEEP_MODE, false)) {
                            if (TService.this.l_a == 1) {
                                TService.this.loadTimeAudio(TService.this, TService.tH, TService.tM, TService.tP);
                            }
                            TService.time_say = 0;
                        } else if (TService.tP == 1) {
                            if (TService.this.l_a == 1) {
                                TService.this.loadTimeAudio(TService.this, TService.tH, TService.tM, TService.tP);
                            }
                            TService.time_say = 0;
                        } else if (!(TService.tH == 12 || TService.tH == 1 || TService.tH == 2 || TService.tH == 3 || TService.tH == 4 || TService.tH == 5 || TService.tH == 6)) {
                            if (TService.this.l_a == 1) {
                                TService.this.loadTimeAudio(TService.this, TService.tH, TService.tM, TService.tP);
                            }
                            TService.time_say = 0;
                        }
                    }
                } else if (this.talking_moment == 30) {
                    if (TService.tM == 30 || TService.tM == 0) {
                        if (!SharePreferenceUtils.getBoolean(TService.this.getApplicationContext(), Type.SLEEP_MODE, false)) {
                            if (TService.this.l_a == 1) {
                                TService.this.loadTimeAudio(TService.this, TService.tH, TService.tM, TService.tP);
                            }
                            TService.time_say = 0;
                        } else if (TService.tP == 1) {
                            if (TService.this.l_a == 1) {
                                TService.this.loadTimeAudio(TService.this, TService.tH, TService.tM, TService.tP);
                            }
                            TService.time_say = 0;
                        } else if (!(TService.tH == 12 || TService.tH == 1 || TService.tH == 2 || TService.tH == 3 || TService.tH == 4 || TService.tH == 5 || TService.tH == 6)) {
                            if (TService.this.l_a == 1) {
                                TService.this.loadTimeAudio(TService.this, TService.tH, TService.tM, TService.tP);
                            }
                            TService.time_say = 0;
                        }
                    }
                } else if (this.talking_moment == 60) {
                    if (TService.tM == 0) {
                        if (!SharePreferenceUtils.getBoolean(TService.this.getApplicationContext(), Type.SLEEP_MODE, false)) {
                            if (TService.this.l_a == 1) {
                                TService.this.loadTimeAudio(TService.this, TService.tH, TService.tM, TService.tP);
                            }
                            TService.time_say = 0;
                        } else if (TService.tP == 1) {
                            if (TService.this.l_a == 1) {
                                TService.this.loadTimeAudio(TService.this, TService.tH, TService.tM, TService.tP);
                            }
                            TService.time_say = 0;
                        } else if (!(TService.tH == 12 || TService.tH == 1 || TService.tH == 2 || TService.tH == 3 || TService.tH == 4 || TService.tH == 5 || TService.tH == 6)) {
                            if (TService.this.l_a == 1) {
                                TService.this.loadTimeAudio(TService.this, TService.tH, TService.tM, TService.tP);
                            }
                            TService.time_say = 0;
                        }
                    }
                } else if (this.talking_moment == 0) {
                    TService.this.flag = false;
                    TService.this.stopSelf();
                }
                TService.this.soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                        TService.r++;
                        if (TService.h == TService.r) {
                            AudioManager audioManager = (AudioManager) TService.this.getSystemService(Context.AUDIO_SERVICE);
                            int cv = audioManager.getStreamVolume(3);
                            audioManager.setStreamVolume(3, audioManager.getStreamMaxVolume(3), 0);
                            TService.this.playTime();
                            TService.this.wait_service(8);
                            audioManager.setStreamVolume(3, cv, 0);
                            TService.this.soundPool.release();
                            TService.this.wait_service(220);
                        }
                    }
                });
                TService.this.myHandler.postDelayed(this, 60000);
                return;
            }
            TService.this.stopSelf();
        }
    }




    public void loadTimeAudio(Context context, int hour, int minute, int pm) {
        NowTime = this.soundPool.load(context, R.raw.one, 1);
        h = 3;
        r = 0;
        if (pm == 1) {
            if (hour < 4) {
                sound3 = this.soundPool.load(context, R.raw.one, 1);
            } else if (hour == 12) {
                sound3 = this.soundPool.load(context, R.raw.one, 1);
            } else if (hour < 6) {
                sound3 = this.soundPool.load(context, R.raw.one, 1);
            } else if (hour < 8) {
                sound3 = this.soundPool.load(context, R.raw.one, 1);
            } else if (hour >= 8) {
                sound3 = this.soundPool.load(context, R.raw.one, 1);
            } else {
                sound3 = this.soundPool.load(context, R.raw.one, 1);
            }
        } else if (hour < 4) {
            sound3 = this.soundPool.load(context, R.raw.one, 1);
        } else if (hour < 6) {
            sound3 = this.soundPool.load(context, R.raw.one, 1);
        } else if (hour < 12) {
            sound3 = this.soundPool.load(context, R.raw.one, 1);
        } else if (hour == 12) {
            sound3 = this.soundPool.load(context, R.raw.one, 1);
        } else {
            sound3 = this.soundPool.load(context, R.raw.one, 1);
        }
        if (hour == 1) {
            sound0 = this.soundPool.load(context, R.raw.one, 1);
        } else if (hour == 2) {
            sound0 = this.soundPool.load(context, R.raw.one, 1);
        } else if (hour == 3) {
            sound0 = this.soundPool.load(context, R.raw.one, 1);
        } else if (hour == 4) {
            sound0 = this.soundPool.load(context, R.raw.one, 1);
        } else if (hour == 5) {
            sound0 = this.soundPool.load(context, R.raw.one, 1);
        } else if (hour == 6) {
            sound0 = this.soundPool.load(context, R.raw.one, 1);
        } else if (hour == 7) {
            sound0 = this.soundPool.load(context, R.raw.one, 1);
        } else if (hour == 8) {
            sound0 = this.soundPool.load(context, R.raw.one, 1);
        } else if (hour == 9) {
            sound0 = this.soundPool.load(context, R.raw.one, 1);
        } else if (hour == 10) {
            sound0 = this.soundPool.load(context, R.raw.one, 1);
        } else if (hour == 11) {
            sound0 = this.soundPool.load(context, R.raw.one, 1);
        } else if (hour == 12) {
            sound0 = this.soundPool.load(context, R.raw.one, 1);
        } else {
            sound0 = this.soundPool.load(context, R.raw.one, 1);
        }
        sound1 = this.soundPool.load(context, R.raw.one, 1);
        if (minute == 15) {
            sound1 = this.soundPool.load(context, R.raw.one, 1);
        } else if (minute == 30) {
            sound1 = this.soundPool.load(context, R.raw.one, 1);
        } else if (minute == 45) {
            sound1 = this.soundPool.load(context, R.raw.one, 1);
        }
    }


    public void playTime() {
        if (this.l_a == 1) {
            this.soundPool.play(NowTime, 1.0f, 1.0f, 0, 0, 1.0f);
            wait_service(6);
            this.soundPool.play(sound3, 1.0f, 1.0f, 0, 0, 1.0f);
            wait_service(6);
            this.soundPool.play(sound0, 1.0f, 1.0f, 0, 0, 1.0f);
            wait_service(6);
            this.soundPool.play(sound1, 1.0f, 1.0f, 0, 0, 1.0f);
            this.soundPool.stop(sound3);
            this.soundPool.stop(sound0);
            this.soundPool.stop(sound1);
        }
    }

    public void wait_service(int x) {
        try {
            Thread.sleep((long) (x * 250));
        } catch (InterruptedException e) {
        }
    }

    private void collectTime() {
        Calendar calendar = Calendar.getInstance();
        tH = calendar.get(Calendar.HOUR);
        tM = calendar.get(Calendar.MINUTE);
        tS = calendar.get(Calendar.SECOND);
        tP = calendar.get(Calendar.AM_PM); //TODO don't know what is tP??
        if (tH == 0) {
            tH = 12;
        }
    }

    void TalkStart(int talkingPeriod) {
        new Thread(new TalkkingTime(talkingPeriod)).start();
    }


    public void onCreate() {
        super.onCreate();
        this.powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE); //TODO this was "power"
        this.wakeLock = this.powerManager.newWakeLock(1, "WakeLock");
        this.wakeLock.acquire();
    }



    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        this.sharedpreferences = getSharedPreferences(MYPREFERENCE, 0);
        this.flag = true;
        TalkStart(SharePreferenceUtils.getInt(this, Type.moment_time, 0));
        return Service.START_STICKY; //TODO it was 1
    }

    public void onStart(Intent intent, int startId) {
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    public void onDestroy() {
        this.flag = false;
        super.onDestroy();
    }

}
