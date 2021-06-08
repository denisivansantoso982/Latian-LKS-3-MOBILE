package com.anew.denis.testproject;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.nio.channels.Channel;
import java.nio.channels.Channels;

public class LandingPageActivity extends AppCompatActivity {
    Context context;
    TextView textView;
    Session session;
    static String CHANNEL_ID = "Denis Ivan Santoso";
    static String CHANNEL_NAME = "Denis Ivan Santoso";
    static String CHANNEL_DESC = "Ganteng";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);

        context = this;
        session = new Session(context);
        getSupportActionBar().setTitle("Menu");
        textView = findViewById(R.id.textView);
        textView.setText(session.getNama());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nav, menu);

        return  true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.logout:
                Session s = new Session(context);
                s.clearSession();
                Intent i = new Intent(LandingPageActivity.this, LoginActivity.class);
                startActivity(i);
                finish();
                break;
            case R.id.notif:
                NotificationManager notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel notificationChannel = new NotificationChannel("Denis", "Ivan", NotificationManager.IMPORTANCE_DEFAULT);
                    notificationChannel.setDescription("Santoso");
                    notificationManager.createNotificationChannel(notificationChannel);
                }

                NotificationCompat.Builder notification = new NotificationCompat.Builder(LandingPageActivity.this, CHANNEL_ID)
                        .setSmallIcon(R.drawable.ic_collections_bookmark)
                        .setContentTitle("New Book Available!")
                        .setSubText("Let's make you the first one read this book!")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

                Intent intent = new Intent(context, BukuActivity.class);
                PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
                notification.setContentIntent(pendingIntent);
                notificationManager.notify(0, notification.build());
                break;
        }

        return true;
    }

    public void actionClick(View view){
        switch (view.getId()){
            case R.id.history:
                Intent ih = new Intent(LandingPageActivity.this, HistoryActivity.class);
                startActivity(ih);
                break;
            case R.id.cari:
                Intent ic = new Intent(LandingPageActivity.this, BukuActivity.class);
                startActivity(ic);
                break;
        }
    }

}
