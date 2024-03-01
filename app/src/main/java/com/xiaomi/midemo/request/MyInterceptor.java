package com.xiaomi.midemo.request;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class MyInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        //请求完成后断开，下次请求时先连接
        Request request = chain.request()
                .newBuilder()
                .addHeader("Connection", "close")
                .build();
        return chain.proceed(request);
    }
}
