package com.basis.basis.retrofit;

import com.basis.basis.retrofit.Requests.RequestLogin;
import com.basis.basis.retrofit.Responses.ResponstAuth;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface BasisService {

    @POST("/login")
    Call<ResponstAuth> doLogin(@Body RequestLogin requestLogin);

}
