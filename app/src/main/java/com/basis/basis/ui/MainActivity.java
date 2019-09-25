package com.basis.basis.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basis.basis.MyIntentService;
import com.basis.basis.R;

import java.util.HashMap;
import java.util.Map;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener {

    View fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrancarServicio();

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

    private void ArrancarServicio() {
        //startService(new Intent(this, MyIntentService.class));
        Intent serviceIntent = new Intent(this, MyIntentService.class);
        startService(serviceIntent);
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
