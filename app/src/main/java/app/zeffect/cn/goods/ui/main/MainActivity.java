package app.zeffect.cn.goods.ui.main;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.ui.goods.GoodsInfo.GoodsInfoActivity;
import app.zeffect.cn.goods.utils.Constant;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    private RecyclerView goodsRecy;
    private GoodsAdapter goodsAdapter;
    private List<Goods> goodsList = new ArrayList<>();
    GoodsViewModel goodsViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
        initData();
    }

    private void initData() {
        if (goodsViewModel != null) goodsViewModel.getGoods("");
    }


    private void initView() {
        goodsRecy = findViewById(R.id.good_recy);
        goodsRecy.setLayoutManager(new LinearLayoutManager(this));
        goodsAdapter = new GoodsAdapter(goodsList);
        goodsRecy.setAdapter(goodsAdapter);
        //
        findViewById(R.id.add_goods_btn).setOnClickListener(this);
        goodsAdapter.setOnItemClickListener(this);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_goods_btn) {
        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Goods goods = goodsList.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            Pair<View, String> imgPair = Pair.create(view.findViewById(R.id.item_goods_img), getResources().getString(R.string.tran_img));
            Pair<View, String> titlePair = Pair.create(view.findViewById(R.id.item_title_tv), getResources().getString(R.string.tran_title));
            Pair<View, String> desPair = Pair.create(view.findViewById(R.id.item_des_tv), getResources().getString(R.string.tran_des));
            Pair<View, String> pricePair = Pair.create(view.findViewById(R.id.item_price_tv), getResources().getString(R.string.tran_price));
            ActivityCompat.startActivity(this, new Intent(this, GoodsInfoActivity.class).putExtra(Constant.DATA, goods), ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, imgPair, titlePair, desPair, pricePair).toBundle());
        } else {
            startActivity(new Intent(this, GoodsInfoActivity.class).putExtra(Constant.DATA, goods));
        }
    }
}
