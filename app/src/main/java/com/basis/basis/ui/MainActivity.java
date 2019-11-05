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
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.work.Constraints;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.basis.basis.LocationWorker;
import com.basis.basis.R;
import com.basis.basis.common.Constantes;
import com.basis.basis.common.SharedPreferencesManager;
import com.basis.basis.retrofit.BasisClient;
import com.basis.basis.retrofit.BasisService;
import com.basis.basis.retrofit.Requests.RequestLogin;
import com.basis.basis.retrofit.Responses.ResponstAuth;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {

    Context context;

    private View view;
    private EditText etUser;
    private EditText etPass;
    private Button btnLogin;
    private String user;
    private String pass;

    BasisClient basisClient;
    BasisService basisService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etUser = view.findViewById(R.id.tvEmail);
        etPass = view.findViewById(R.id.tvPassword);
        btnLogin = view.findViewById(R.id.btnLogin);

        //Pedir permisos para GPS. Si me rechaza los permisos no tiraa el hilo de servicios de GPS.
        if(!Permisos()) {
            ArrancarServicio();
        }

        //Guardo en la sharedPreferneces el usuario ya validado alguna vez
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        user = sharedPreferences.getString("usuario","default");
        pass = sharedPreferences.getString("password", "default");

        //Si nunca se valido ningun usuario pide logIn, si no usa el guardado.
        if(!user.equals("default"))
        {
            btnLogin.setVisibility(View.GONE);
            etPass.setVisibility(View.GONE);
            etUser.setVisibility(View.GONE);
            RequestIngreso();
        }
        else
        {
            retrofitInit();
            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    user = etUser.getText().toString();
                    pass = etPass.getText().toString();
                    RequestIngreso();
                }
            });
        }
    }

    private void retrofitInit() {
        basisClient = BasisClient.getInstance();
        basisService = basisClient.getBasisService();
    }

    //metodo que se ejecuta cuando hay un requestPermissions, si me aprobo: joya. SI no: lo pido de nuevo.
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

    private void RequestIngreso() {

        if(user.isEmpty()){
            etUser.setError("Ingrese nombre de usuario");
        } else if(pass.isEmpty()) {
            etPass.setError("Ingrese la contraseña");
        } else {
            RequestLogin requestLogin = new RequestLogin(user,pass);
            Call<ResponstAuth> call = basisService.doLogin(requestLogin);
            call.enqueue(new Callback<ResponstAuth>() {
                @Override
                public void onResponse(Call<ResponstAuth> call, Response<ResponstAuth> response) {
                    if(response.isSuccessful()) {
                        Toast.makeText(MainActivity.this,"Login correcto",LENGTH_LONG).show();
                        SharedPreferencesManager.setStringValue(Constantes.SP_TOKEN, response.body().getToken());
                        SharedPreferencesManager.setStringValue(Constantes.SP_USUARIO, response.body().getUsuario());
                        SharedPreferencesManager.setStringValue(Constantes.SP_PASS, response.body().getPassword());
                        Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(MainActivity.this,"Usuario o contraseña incorrectos", LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponstAuth> call, Throwable t) {
                    //Reemplazar con un snackbar!!!!!!!!!!!!!
                    Toast.makeText(MainActivity.this,"Revise conexion a la VPN!",Toast.LENGTH_LONG).show();
                }
            });
        }
    }

}
