package com.basis.basis.ui;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basis.basis.LocationWorker;
import com.basis.basis.R;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {

    View fragment;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(!Permisos()) {
            ArrancarServicio();
        }

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String usuario = sharedPreferences.getString("usuario","default");
        String password = sharedPreferences.getString("password", "default");

        if(!usuario.equals("default"))
        {
            fragment = findViewById(R.id.fragmentLogin);
            fragment.setVisibility(View.GONE);

            RequestIngreso(usuario, password);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == 100) {
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Tengo permisos", LENGTH_LONG).show();
                ArrancarServicio();
            }
            else {
                Permisos();
            }
        }
    }

    private boolean Permisos() {
        if(Build.VERSION.SDK_INT >= 23 && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},100);
            return true;
        }
        return false;
    }

    private void ArrancarServicio() {
        //startService(new Intent(this, MyIntentService.class));
        Constraints constraints = new Constraints.Builder()
                .build();

        PeriodicWorkRequest workRequest = new PeriodicWorkRequest.Builder(LocationWorker.class,15, TimeUnit.MINUTES)
                .setConstraints(constraints)
                .build();

        WorkManager.getInstance(context).enqueue(workRequest);
    }

    private void RequestIngreso(final String usuario, final String password) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.1.131/ingreso";

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Toast.makeText(getApplicationContext(),"Ingreso exitoso", LENGTH_LONG).show();
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        startActivity(intent);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Verificar VPN", LENGTH_LONG).show();
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                startActivity(intent);
            }
        }){
            //Data a enviar
            @Override
            protected Map<String, String> getParams () {
                Map<String, String> params = new HashMap<String, String>();
                params.put("usuario", usuario);
                params.put("contrasena", password);

                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
