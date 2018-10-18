package app.zeffect.cn.goods.ui.main;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.ui.goods.GoodsInfo.GoodsInfoActivity;
import app.zeffect.cn.goods.utils.Constant;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;

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
        findViewById(R.id.scan_btn).setOnClickListener(this);
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
        } else if (v.getId() == R.id.scan_btn) {
            scan();
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

    private void scan() {
        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("(识别二维码)")//扫描框下文字
                .setShowDes(false)//是否显示扫描框下面文字
                .setShowLight(true)//显示手电筒按钮
                .setShowTitle(true)//显示Title
                .setShowAlbum(true)//显示从相册选择按钮
                .setCornerColor(Color.WHITE)//设置扫描框颜色
                .setLineColor(Color.WHITE)//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(QrConfig.TYPE_BARCODE)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_BARCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
                .setPlaySound(true)//是否扫描成功后bi~的声音
                .setIsOnlyCenter(true)//是否只识别框中内容(默认为全屏识别)
                .setTitleText("扫描条形码")//设置Tilte文字
                .setTitleBackgroudColor(ContextCompat.getColor(this,R.color.colorPrimary))//设置状态栏颜色
                .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                .create();
        QrManager.getInstance().init(qrConfig).startScan(MainActivity.this, new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(String result) {
                Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
            }
        });
    }

}
