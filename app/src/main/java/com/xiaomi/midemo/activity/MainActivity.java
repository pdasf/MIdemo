package com.xiaomi.midemo.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.xiaomi.midemo.R;
import com.xiaomi.midemo.adapter.RecyclerAdapterContent;
import com.xiaomi.midemo.adapter.RecyclerAdapterTable;
import com.xiaomi.midemo.decoration.SpacesItemDecoration;
import com.xiaomi.midemo.entity.FilterData;
import com.xiaomi.midemo.entity.Frontpage;
import com.xiaomi.midemo.entity.ItemDTO;
import com.xiaomi.midemo.entity.SelectOptionsDTO;
import com.xiaomi.midemo.request.Request1;
import com.xiaomi.midemo.request.RetrofitHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends BaseActivity {
    private static final String TAG = "MainActivity";
    private TextView title;
    private RecyclerView table1;
    private RecyclerView table2;
    private Spinner spinner1;
    private Spinner spinner2;
    private Spinner spinner3;
    private RecyclerView content;
    private Map<String, String> args;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        args = new HashMap<>();
        View back = findViewById(R.id.back);
        back.setOnClickListener(v -> {
            Toast.makeText(this, "123", Toast.LENGTH_SHORT).show();
            ActivityManager.finishAll();
        });
        title = findViewById(R.id.title_text);
        table1 = findViewById(R.id.table_1);
        LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
        layoutManager1.setOrientation(LinearLayoutManager.HORIZONTAL);
        table1.setLayoutManager(layoutManager1);
        table1.addItemDecoration(new SpacesItemDecoration(SpacesItemDecoration.HORIZONTAL, 0));
        table2 = findViewById(R.id.table_2);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(this);
        layoutManager2.setOrientation(LinearLayoutManager.HORIZONTAL);
        table2.setLayoutManager(layoutManager2);
        table2.addItemDecoration(new SpacesItemDecoration(SpacesItemDecoration.HORIZONTAL, 0));
        spinner1 = findViewById(R.id.spinner_1);

        spinner2 = findViewById(R.id.spinner_2);
        spinner3 = findViewById(R.id.spinner_3);
        content = findViewById(R.id.content);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 6);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        content.setLayoutManager(gridLayoutManager);
        content.addItemDecoration(new SpacesItemDecoration(SpacesItemDecoration.GRID, 15, 6));

        getFrontpage();
    }

    private void initSpinnerAdapter(List<SelectOptionsDTO> options, Spinner spinner, String whichOne) {
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this,
                R.layout.spinner_item_topic,
                R.id.spinner_text,
                options.stream().map(SelectOptionsDTO::getTitle).toArray(String[]::new)
        );
        spinnerAdapter.setDropDownViewResource(R.layout.spinner_item_normal);
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                args.put(whichOne.equals("spinner1") ? "year" : (whichOne.equals("spinner2") ? "right" : "sort")
                        , options.get(position).getId());
                filterData(args);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d(TAG, "nothing can select");
            }
        });
    }

    private void getFrontpage() {
        Request1 request = RetrofitHandler.retrofit.create(Request1.class);
        Observable<Frontpage> observable = request.getFrontpage();
        observable.subscribeOn(Schedulers.io())               // 在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 回到主线程 处理请求结果
                .subscribe(new Observer<Frontpage>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "Frontpage request 开始采用subscribe连接");
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onNext(Frontpage frontpage) {
                        // 对返回的数据进行处理
                        String name = frontpage.getTitle();
                        List<List<SelectOptionsDTO>> selectOptions = frontpage.getSelectOptions();
                        List<ItemDTO> items = frontpage.getItems();
                        args.put("region", selectOptions.get(0).get(0).getId());
                        args.put("type", selectOptions.get(1).get(0).getId());
                        args.put("year", selectOptions.get(2).get(0).getId());
                        args.put("right", selectOptions.get(3).get(0).getId());
                        args.put("sort", selectOptions.get(4).get(0).getId());

                        title.setText(name);
                        RecyclerAdapterTable adapter1 = new RecyclerAdapterTable(
                                selectOptions.get(0).stream()
                                        .map(SelectOptionsDTO::getTitle)
                                        .collect(Collectors.toList()), MainActivity.this
                        );
                        adapter1.setOnItemClickListener((view, position) -> {
                            args.put("region", selectOptions.get(0).get(position).getId());
                            filterData(args);
                            adapter1.selectedItem = position;
                            adapter1.notifyDataSetChanged();
                        });
                        table1.setAdapter(adapter1);
                        RecyclerAdapterTable adapter2 = new RecyclerAdapterTable(
                                selectOptions.get(1).stream()
                                        .map(SelectOptionsDTO::getTitle)
                                        .collect(Collectors.toList()), MainActivity.this
                        );
                        adapter2.setOnItemClickListener((view, position) -> {
                            args.put("type", selectOptions.get(1).get(position).getId());
                            filterData(args);
                            adapter2.selectedItem = position;
                            adapter2.notifyDataSetChanged();
                        });
                        table2.setAdapter(adapter2);
                        initSpinnerAdapter(selectOptions.get(2), spinner1, "spinner1");
                        initSpinnerAdapter(selectOptions.get(3), spinner2, "spinner2");
                        initSpinnerAdapter(selectOptions.get(4), spinner3, "spinner3");
                        content.setAdapter(new RecyclerAdapterContent(items, MainActivity.this));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "Frontpage 请求失败");
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "Frontpage 请求成功");
                    }
                });
    }

    private void filterData(Map<String, String> args) {
        Request1 request = RetrofitHandler.retrofit.create(Request1.class);
        Observable<FilterData> observable = request.getItemDTO(args);
        observable.subscribeOn(Schedulers.io())               // 在IO线程进行网络请求
                .observeOn(AndroidSchedulers.mainThread())  // 回到主线程 处理请求结果
                .subscribe(new Observer<FilterData>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "filter request 开始采用subscribe连接");
                    }

                    @SuppressLint("NotifyDataSetChanged")
                    @Override
                    public void onNext(FilterData filterData) {
                        content.setAdapter(new RecyclerAdapterContent(filterData.getItems(), MainActivity.this));
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "filter 请求失败" + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "filter 请求成功");
                    }
                });
    }
}