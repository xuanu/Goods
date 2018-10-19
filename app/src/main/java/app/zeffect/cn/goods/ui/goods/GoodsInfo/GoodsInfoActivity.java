package app.zeffect.cn.goods.ui.goods.GoodsInfo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.orm.GoodsOrm;
import app.zeffect.cn.goods.ui.BaseActivity;
import app.zeffect.cn.goods.ui.main.GoodsRepository;
import app.zeffect.cn.goods.utils.Constant;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class GoodsInfoActivity extends BaseActivity implements View.OnClickListener {

    private Goods goodsInfos;
    private GoodsInfoViewModel goodsInfoViewModel;

    private TextView mTitleTv, mDesTv, mCountTv, mPriceTv;
    private ImageView mGoodsImg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_goods_info);
        goodsInfoViewModel = ViewModelProviders.of(this).get(GoodsInfoViewModel.class);
        initView();
        initData();
    }

    private void initView() {
        findViewById(R.id.left_back_btn).setOnClickListener(this);
        mTitleTv = (TextView) findViewById(R.id.goods_title_tv);
        mDesTv = (TextView) findViewById(R.id.goods_des_tv);
        mCountTv = findViewById(R.id.goods_count_tv);
        mPriceTv = findViewById(R.id.goods_price_tv);
        mGoodsImg = findViewById(R.id.goods_img);
        findViewById(R.id.goods_sale_btn).setOnClickListener(this);
    }

    private void initData() {
        //数据显示
        goodsInfoViewModel.goodsMutableLiveData.observe(this, new Observer<Goods>() {
            @Override
            public void onChanged(@Nullable Goods goods) {
                if (goods != null) {
                    mTitleTv.setText(goods.getGoodsName());
                    mDesTv.setText(goods.getGoodsDescribe());
                    mCountTv.setText(goods.getGoodsCount() + "");
                    mPriceTv.setText("￥" + goods.getGoodsPrice() + "");
                }
            }
        });
        if (getIntent().hasExtra(Constant.DATA)) {
            long id = getIntent().getLongExtra(Constant.DATA, 0);
            goodsInfos = GoodsOrm.getInstance().queryById(id, Goods.class);
        }
        if (goodsInfos != null) goodsInfoViewModel.goodsMutableLiveData.postValue(goodsInfos);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.left_back_btn) {
            finish();
        } else if (v.getId() == R.id.goods_sale_btn) {
            if (goodsInfos != null) {
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("售出")
                        .setContentText("售出一件商品?库存将减1")
                        .setCancelText("手误")
                        .setConfirmText("确认售出")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                int count = goodsInfos.getGoodsCount();
                                int hasCount = count - 1;
                                if (hasCount < 0) hasCount = 0;
                                goodsInfos.setGoodsCount(hasCount);
                                if (GoodsRepository.saveGoods(goodsInfos)) {
                                    goodsInfoViewModel.goodsMutableLiveData.postValue(goodsInfos);
                                }
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        }
    }
}
