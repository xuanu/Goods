package app.zeffect.cn.goods.ui.goods.GoodsInfo;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.orm.GoodsOrm;
import app.zeffect.cn.goods.ui.main.GoodsRepository;
import app.zeffect.cn.goods.utils.Constant;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class GoodsInfoFragment extends Fragment implements View.OnClickListener {

    private View rootView;

    private Goods goodsInfos;
    private GoodsInfoViewModel goodsInfoViewModel;

    private TextView mTitleTv, mDesTv, mCountTv, mPriceTv;
    private ImageView mGoodsImg;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodsInfoViewModel = ViewModelProviders.of(this).get(GoodsInfoViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_goods_info, container, false);
            initView();
            initData();
        }
        return rootView;
    }

    private void initView() {
        rootView.findViewById(R.id.left_back_btn).setOnClickListener(this);
        mTitleTv = (TextView) rootView.findViewById(R.id.goods_title_tv);
        mDesTv = (TextView) rootView.findViewById(R.id.goods_des_tv);
        mCountTv = rootView.findViewById(R.id.goods_count_tv);
        mPriceTv = rootView.findViewById(R.id.goods_price_tv);
        mGoodsImg = rootView.findViewById(R.id.goods_img);
        rootView.findViewById(R.id.goods_sale_btn).setOnClickListener(this);
    }

    private void initData() {
        //数据显示
        goodsInfoViewModel.goodsMutableLiveData.observe(this, new Observer<Goods>() {
            @Override
            public void onChanged(@Nullable Goods goods) {
                if (goods != null) {
                    mTitleTv.setText(goods.getGoodsName());
                    mDesTv.setText(goods.getGoodsDescribe());
//                    mCountTv.setText(goods.getGoodsCount() + "");todo
                    mPriceTv.setText("￥" + goods.getGoodsPrice() + "");
                }
            }
        });
        if (getActivity().getIntent().hasExtra(Constant.DATA)) {
            long id = getActivity().getIntent().getLongExtra(Constant.DATA, 0);
            goodsInfos = GoodsOrm.getInstance().queryById(id, Goods.class);
        }
        if (goodsInfos != null) goodsInfoViewModel.goodsMutableLiveData.postValue(goodsInfos);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.left_back_btn) {
            getActivity().finish();
        } else if (v.getId() == R.id.goods_sale_btn) {
            if (goodsInfos != null) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("售出")
                        .setContentText("售出一件商品?库存将减1")
                        .setCancelText("手误")
                        .setConfirmText("确认售出")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                // TODO: 2018/10/19  需要修改
//                                int count = goodsInfos.getGoodsCount();
//                                int hasCount = count - 1;
//                                if (hasCount < 0) hasCount = 0;
//                                goodsInfos.setGoodsCount(hasCount);
//                                if (GoodsRepository.saveGoods(goodsInfos)) {
//                                    goodsInfoViewModel.goodsMutableLiveData.postValue(goodsInfos);
//                                }
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        }
    }
}
