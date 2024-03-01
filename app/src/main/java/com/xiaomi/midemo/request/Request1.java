package com.xiaomi.midemo.request;

import com.xiaomi.midemo.entity.Content;
import com.xiaomi.midemo.entity.FilterData;
import com.xiaomi.midemo.entity.Frontpage;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface Request1 {
    @GET("/api/frontpage")
    Observable<Frontpage> getFrontpage();

    @GET("/api/filter")
    Observable<FilterData> getItemDTO(@QueryMap Map<String, String> args);

    @GET("/api/content")
    Observable<Content> getContent(@QueryMap Map<String, String> args);
}
