package com.basis.basis.retrofit;

import com.basis.basis.common.Constantes;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AuthBasisClient {

    private static AuthBasisClient instance = null;
    private AuthBasisService authBasisService;
    private Retrofit retrofit;

    public AuthBasisClient() {
        // Agrego en la cabecera el token
        OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient.Builder();
        okHttpClientBuilder.addInterceptor(new AuthInterceptor());
        OkHttpClient client = okHttpClientBuilder.build();

        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        authBasisService = retrofit.create(AuthBasisService.class);
    }

    public static AuthBasisClient getInstance() {
        if(instance == null){
            instance = new AuthBasisClient();
        }
        return instance;
    }

    public AuthBasisService getAuthBasisService() {
        return authBasisService;
    }
}
