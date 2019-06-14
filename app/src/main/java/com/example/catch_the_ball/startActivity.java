package com.example.catch_the_ball;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;

public class startActivity extends AppCompatActivity {

    ImageView more;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        more=(ImageView)findViewById(R.id.more);

        more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final PopupMenu popupMenu=new PopupMenu(getApplicationContext(),more);
                popupMenu.getMenuInflater().inflate(R.menu.pop_menu,popupMenu.getMenu());

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        Intent intent,chooser;
                        switch (item.getItemId())
                        {
                            case R.id.feedback:
                                intent=new Intent(Intent.ACTION_SEND);
                                intent.setData(Uri.parse("mailto:"));
                                String[] to={"rishabhanand33@gmail.com"};
                                intent.putExtra(Intent.EXTRA_EMAIL,to);
                                intent.setType("message/rfc822");
                                chooser=Intent.createChooser(intent,"Send FeedBack");
                                startActivity(chooser);
                                break;
                            case R.id.share:
                                intent=new Intent(Intent.ACTION_SEND);
                                intent.setType("text/plain");
                                intent.putExtra(Intent.EXTRA_SUBJECT,"Catch The Food");
                                String desc="\n Want to Play This Game \n";
                                intent.putExtra(Intent.EXTRA_TEXT,desc);
                                startActivity(Intent.createChooser(intent,"Share"));
                                break;
                        }

                        return true;
                    }
                });
                popupMenu.show();
            }
        });
    }
    public void startGame(View view){
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
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
