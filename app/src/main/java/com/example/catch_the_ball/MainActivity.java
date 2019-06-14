package com.example.catch_the_ball;

import android.content.Intent;
import android.graphics.Point;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private TextView scoreLable;
    private TextView startLabel;
    private ImageView box;
    private ImageView orange;
    private ImageView black;
    private ImageView pink;
    private ImageView playoption;

    //Size
    private int frameHeight;
    private int boxSize;
    private int screenWidth;
    private int screenHeight;


    //position
    private int boxY;
    private int orangeX;
    private int orangeY;
    private int pinkX;
    private int pinkY;
    private int blackX;
    private int blackY;

    //Speed
    private int boxSpeed;
    private int orangeSpeed;
    private int pinkSpeed;
    private int blackSpeed;

    //Score
    private int score=0;

    //Initilaize Class
    private Handler handler=new Handler();
    private Timer timer=new Timer();
    private SoundPlayer sound;


    //status Check
    private boolean action_flag=false;
    private boolean start_flag=false;
    private boolean pause_flg=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sound=new SoundPlayer(this);

        scoreLable=(TextView)findViewById(R.id.scoreLabel);
        startLabel=(TextView)findViewById(R.id.startLabel);
        box=(ImageView)findViewById(R.id.box);
        orange=(ImageView)findViewById(R.id.orange);
        black=(ImageView)findViewById(R.id.black);
        pink=(ImageView)findViewById(R.id.pink);
        playoption=(ImageView)findViewById(R.id.playoption);

        playoption.setEnabled(false);

        //Get Screen Size;
        WindowManager windowManager=getWindowManager();
        Display display=windowManager.getDefaultDisplay();
        Point size=new Point();
        display.getSize(size);

        screenWidth=size.x;
        screenHeight=size.y;


        //Move Out of screen
        black.setY(-80);
        black.setX(-80);
        pink.setY(-80);
        pink.setX(-80);
        orange.setY(-80);
        orange.setX(-80);

        scoreLable.setText("Score : 0");
    }

    public void changepos()
    {
        hitCheck();

        //orange
        orangeX -=orangeSpeed;
        if( orangeX<0) {
            orangeX = screenWidth + 20;
            orangeY = (int)Math.floor(Math.random()*(frameHeight - orange.getHeight()));
        }
        orange.setY(orangeY);
        orange.setX(orangeX);

        //black
        blackX -=blackSpeed;
        if( blackX<0) {
            blackX = screenWidth + 10;
            blackY = (int)Math.floor(Math.random()*(frameHeight - black.getHeight()));
        }
        black.setY(blackY);
        black.setX(blackX);

        boxSpeed=Math.round(screenHeight/60F);
        orangeSpeed=Math.round(screenWidth/60F);
        pinkSpeed=Math.round(screenWidth/36F);
        blackSpeed=Math.round(screenWidth/ 45F);

        Log.v("SPEED_BOX : ",boxSpeed+"");
        Log.v("SPEED_PINK : ",pinkSpeed+"");
        Log.v("SPEED_ORANGE : ",orangeSpeed+"");
        Log.v("SPEED_BLACK : ",blackSpeed+"");

        //pink
        pinkX -=pinkSpeed;
        if( pinkX<0) {
            pinkX = screenWidth + 5000;
            pinkY = (int)Math.floor(Math.random()*(frameHeight - pink.getHeight()));
        }
        pink.setY(pinkY);
        pink.setX(pinkX);

        //Move Box
        if(action_flag==true) {
            boxY -= boxSpeed;
            box.setImageResource(R.drawable.down);
        }
        else {
            boxY += boxSpeed;
            box.setImageResource(R.drawable.up);
        }

        //check box position
        if(boxY<0) boxY=0;

        if(boxY>frameHeight - boxSize) boxY = frameHeight - boxSize;

        box.setY(boxY);

        scoreLable.setText("Score : "+score);
    }

    public void hitCheck(){

        //orange
        int orangeCenterX=orangeX+orange.getWidth()/2;
        int orangeCenterY=orangeY+orange.getHeight()/2;

        if(0<=orangeCenterX && orangeCenterX<= boxSize
                && boxY <= orangeCenterY && orangeCenterY <= boxY + boxSize){
            score+=10;
            orangeX = -10;
            sound.playHitSound();
        }

        //pink
        int pinkCenterX=pinkX+pink.getWidth()/2;
        int pinkCenterY=pinkY+pink.getHeight()/2;

        if(0<=pinkCenterX && pinkCenterX<= boxSize
                && boxY <= pinkCenterY && pinkCenterY <= boxY + boxSize){
            score+=30;
            pinkX= -10;
            sound.playHitSound();
        }

        //Black
        int blackCenterX=blackX+black.getWidth()/2;
        int blackCenterY=blackY+black.getHeight()/2;

        if(0<=blackCenterX && blackCenterX<= boxSize
                && boxY <= blackCenterY && blackCenterY <= boxY + boxSize){
            // Stop Timer!!
            timer.cancel();
            timer = null;
            sound.playOverSound();


            //Show Result
            Intent intent=new Intent(getApplicationContext(),resultActivity.class);
            intent.putExtra("SCORE",score);
            startActivity(intent);
        }
    }

    public void pausePushed(View view) {

        if (pause_flg == false) {

            pause_flg = true;

            // Stop the timer.
            timer.cancel();
            timer = null;

            // Change Button Text.
            playoption.setImageResource(R.drawable.play);


        } else {

            pause_flg = false;

            // Change Button Text.
            playoption.setImageResource(R.drawable.pause);

            // Create and Start the timer.
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changepos();
                        }
                    });
                }
            }, 0, 20);

        }
    }

    public boolean onTouchEvent(MotionEvent me)
    {
        if(start_flag==false) {
            start_flag=true;

            FrameLayout frame =(FrameLayout)findViewById(R.id.frame);
            frameHeight=frame.getHeight();

            boxY=(int)box.getY();

            boxSize=box.getHeight();

            startLabel.setVisibility(View.GONE);

            playoption.setEnabled(true);

            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            changepos();
                        }
                    });
                }
            },0,20);
        }
        else {
            if(me.getAction()==MotionEvent.ACTION_DOWN) {
                action_flag = true;
            }
            else if (me.getAction()==MotionEvent.ACTION_UP) {
                action_flag = false;
            }
        }
        return true;
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        if(event.getAction()==KeyEvent.ACTION_DOWN) {
            switch (event.getKeyCode()){
                case KeyEvent.KEYCODE_BACK:
                    return true;
            }
        }
        return super.dispatchKeyEvent(event);
    }
}
