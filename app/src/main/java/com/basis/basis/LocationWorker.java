package com.basis.basis;


import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import com.basis.basis.ui.MainActivity;
import com.google.android.gms.location.FusedLocationProviderClient;

import java.util.List;

public class LocationWorker extends Worker {

    private static final String CHANNEL_ID = "BASIS";
    private NotificationManagerCompat notificationManager;
    private double Longitud = 0;
    private double Latitud = 0;
    Context thisContext;
    private LocationManager locationManager;
    private Location location;

    public LocationWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        thisContext = context;
    }

    @SuppressLint("MissingPermission")
    @NonNull
    @Override
    public Result doWork() {

        createNotificationChannel();

        notificationManager = NotificationManagerCompat.from(thisContext);

        locationManager = (LocationManager) getApplicationContext().getSystemService(Context.LOCATION_SERVICE);
        location = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

        Longitud = location.getLongitude();
        Latitud = location.getLatitude();

        Intent intentService = new Intent(thisContext, MainActivity.class);
        intentService.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(thisContext, 0, intentService, 0);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(thisContext, CHANNEL_ID)
                .setSmallIcon(R.drawable.logo_basis)
                .setLargeIcon(BitmapFactory.decodeResource(thisContext.getResources(), R.drawable.logo_basis))
                .setContentTitle("UBICACION")
                .setContentText("Latitud: " + Latitud + " Longitud: " + Longitud)
                .setDefaults(Notification.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true);

        notificationManager.notify(1, builder.build());

        return Result.success();
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "NofificacionBasis";
            String description = "DescripcionNotificacionBasis";
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = thisContext.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

}
