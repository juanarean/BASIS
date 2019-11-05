package com.basis.basis.retrofit;

import com.basis.basis.R;
import com.basis.basis.common.Constantes;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class BasisClient {

    private static BasisClient instance = null;
    private BasisService basisService;
    private Retrofit retrofit;

    public BasisClient() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Constantes.API_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        basisService = retrofit.create(BasisService.class);
    }

    public static BasisClient getInstance() {
        if(instance == null){
            instance = new BasisClient();
        }
        return instance;
    }

    public BasisService getBasisService() {
        return basisService;
    }
}
