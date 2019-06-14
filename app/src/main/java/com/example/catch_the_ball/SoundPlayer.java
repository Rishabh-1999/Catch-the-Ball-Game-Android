package com.example.catch_the_ball;

import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;

public class SoundPlayer {
    private AudioAttributes audioAttributes;
    final int SOUND_POOL_MAX=2;
    private static SoundPool soundPool;
    private static int hitSound;
    private static int overSound;

    public SoundPlayer(Context context){

        //Sound Pool is deprecated in api 21.lollipop
        //so we use AudioAttributes
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            audioAttributes=new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_GAME)
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .build();
            soundPool=new SoundPool.Builder()
                    .setAudioAttributes(audioAttributes)
                    .setMaxStreams(SOUND_POOL_MAX)
                    .build();
        } else {
            soundPool=new SoundPool(SOUND_POOL_MAX, AudioManager.STREAM_MUSIC,0);
        }

        hitSound=soundPool.load(context,R.raw.hit,1);
        overSound=soundPool.load(context,R.raw.over,1);
    }

    public void playHitSound(){
        soundPool.play(hitSound,1.0f,1.0f,1,0,1.0f);
    }
    public  void playOverSound() {
        soundPool.play(overSound,1.0f,1.0f,1,0,1.0f);
    }
}
