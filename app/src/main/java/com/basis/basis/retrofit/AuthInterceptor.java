package com.basis.basis.retrofit;

import com.basis.basis.common.Constantes;
import com.basis.basis.common.SharedPreferencesManager;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
// COn esto encadeno el token que me dieron en Login con los request que envio despues para obtener datos o enviar.
public class AuthInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        String token = SharedPreferencesManager.getStringValue(Constantes.SP_TOKEN);
        Request request = chain.request().newBuilder().addHeader("Authorization", "Bearer " + token).build();
        return chain.proceed(request);
    }
}
