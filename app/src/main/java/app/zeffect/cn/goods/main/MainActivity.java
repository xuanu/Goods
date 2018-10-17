package app.zeffect.cn.goods.main;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.bean.Goods;

public class MainActivity extends AppCompatActivity {

    private RecyclerView goodsRecy;
    private GoodsAdapter goodsAdapter;
    private List<Goods> goodsList = new ArrayList<>();
    private GoodsViewModel goodsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        initData();
    }

    private void initData() {
        if (goodsViewModel != null) goodsViewModel.getGoods();
    }


    private void initView() {
        goodsRecy = findViewById(R.id.good_recy);
        goodsRecy.setLayoutManager(new LinearLayoutManager(this));
        goodsAdapter = new GoodsAdapter(goodsList);
        goodsRecy.setAdapter(goodsAdapter);
    }

    private void initEvent() {
        goodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel.class);
        goodsViewModel.getGoodsLiveData().observe(this, new Observer<List<Goods>>() {
            @Override
            public void onChanged(@Nullable List<Goods> goods) {
                goodsList.clear();
                goodsList.addAll(goods);
                if (goodsAdapter != null) goodsAdapter.notifyDataSetChanged();
            }
        });
    }
}
