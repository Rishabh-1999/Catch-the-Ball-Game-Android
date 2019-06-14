package com.example.catch_the_ball;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

public class resultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        TextView scoreLabel=(TextView)findViewById(R.id.scoreLabel);
        TextView highScoreLabel=(TextView)findViewById(R.id.highScoreLabel);


        int score=getIntent().getIntExtra("SCORE",0);
        scoreLabel.setText(score+"");

        SharedPreferences settings=getSharedPreferences("GAME_DATA", Context.MODE_PRIVATE);
        int highscore=settings.getInt("HIGH_SCORE",0);

        if(score > highscore) {
            highScoreLabel.setText("High Score : "+score);

            //save
            SharedPreferences.Editor editor=settings.edit();
            editor.putInt("HIGH_SCORE",score);
            editor.commit();
        }
        else{
            highScoreLabel.setText("High Score : "+highscore);
        }
    }
    public void tryAgain(View view) {
        startActivity(new Intent(getApplicationContext(),startActivity.class));
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
