package com.example.womensskill;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.NotificationCompat;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class MainActivity extends BaseClass {
    CardView performanceStore, skillUpload, BuyerRequest, admin, productUpload, manageOrder;
    TextView userName;
    ImageView userImage;
    String uid;
    int count = 0;
    private Uri imagePath;
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    /////Only for Notification////
    public static final String NOTIFICATION_CHANNEL_ID = "10001";
    private final static String default_notification_channel_id = "default";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setContentView(R.layout.activity_main);
        userName = findViewById(R.id.txtUserName);
        userImage = findViewById(R.id.userProfile);

        performanceStore = (CardView) findViewById(R.id.performanceCard);
        skillUpload = (CardView) findViewById(R.id.skilluploadCard);
        BuyerRequest = findViewById(R.id.BuyerRequest);
        admin = findViewById(R.id.adminCard);
        productUpload = findViewById(R.id.productUploadCard);
        manageOrder = findViewById(R.id.manageorderCard);

        //Check and generate Notifications
        ////////////////////
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (0 <= 1) {
                    try {
                        Thread.sleep(5000);
                        final String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
//                        Toast.makeText(MainActivity.this, ""+uid, Toast.LENGTH_LONG).show();
                        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
                        databaseReference.child("Notification").addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                if (dataSnapshot.exists()) {
                                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()) {
                                        try {
                                            String status = dataSnapshot1.child("status").getValue().toString();
                                            String senderId = dataSnapshot1.child("receiverid").getValue().toString();
                                            if (status.equals("unread") && uid.equals(senderId)) {
                                                String id = dataSnapshot1.child("id").getValue().toString();
                                                // String name = dataSnapshot1.child("name").getValue().toString();
                                                String msg = dataSnapshot1.child("description").getValue().toString();
                                                databaseReference.child("Notification").child(id).child("status").setValue("read");
                                                scheduleNotification(getNotification(msg), 5000);

                                            }
                                        } catch (Exception e) {
                                        }
                                    }

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }
                        });


                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        // return START_STICKY;
////////////////////////////////////////////




        manageOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SkillOrderActivity.class));
            }
        });
        BuyerRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, BuyersRequestActivity.class));
            }
        });
        admin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, AdminLogin.class));
            }
        });
        productUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, ProductFormActivity.class));
            }
        });
        skillUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, SkillFormActivity.class));
            }
        });
        userImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });
        performanceStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), StorePerformanceActivity.class));
            }
        });

        uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        databaseReference.child("Users").child(uid).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    try {
                        String eName = dataSnapshot.child("username").getValue().toString();
                        userName.setText(String.valueOf(eName));
                        if (dataSnapshot.child("img").getValue().toString().equals(" ")) {
//                            String firstLetter = eName.substring(0, 1);
//                            ColorGenerator generator = ColorGenerator.MATERIAL; // or use DEFAULT
//                            int color = generator.getRandomColor();
//
//                            TextDrawable drawable = TextDrawable.builder()
//                                    .buildRound(firstLetter, color); // radius in px
//                            userImage.setImageDrawable(drawable);
                        } else {
                            Picasso.get().load(dataSnapshot.child("img").getValue().toString()).into(userImage);

                        }
                    } catch (Exception e) {
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    //Notification function

    private void scheduleNotification(Notification notification, int delay) {
        Intent notificationIntent = new Intent(this, NotificationGernetor.class);
        // Intent notificationIintent = new Intent(this, NotificationActivity.class);
        notificationIntent.putExtra(NotificationGernetor.NOTIFICATION_ID, 1);
        notificationIntent.putExtra(NotificationGernetor.NOTIFICATION, notification);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        long futureInMillis = SystemClock.elapsedRealtime() + delay;
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        assert alarmManager != null;
        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, futureInMillis, pendingIntent);
    }

    private Notification getNotification(String content) {
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, default_notification_channel_id);
        builder.setContentTitle("Notification");
        Intent intent = new Intent(this, NotificationsActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, 0);
        builder.setContentIntent(pendingIntent);
        builder.setContentText(content);
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
        builder.setAutoCancel(true);
        builder.setChannelId(NOTIFICATION_CHANNEL_ID);
        return builder.build();
    }

    @Override
    int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    int getNavigationMenuItemId() {
        return R.id.nav_home;
    }
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == requestCode && resultCode == resultCode
                && data != null && data.getData() != null) {

            imagePath = data.getData();
            try {

                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), imagePath);
                userImage.setImageBitmap(bitmap);
                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("images/" + FirebaseDatabase.getInstance().getReference().child("Users").push().getKey());
                storageReference.putFile(imagePath).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isSuccessful()) ;
                        Uri downloadUri = uriTask.getResult();
                        String img1 = downloadUri.toString();
                        databaseReference.child("Users").child(uid).child("img").setValue(img1);

                    }
                });


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
}