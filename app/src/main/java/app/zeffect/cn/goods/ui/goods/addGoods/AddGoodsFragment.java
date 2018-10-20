package app.zeffect.cn.goods.ui.goods.addGoods;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.bean.GoodRepertory;
import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.utils.Constant;
import app.zeffect.cn.goods.utils.SnackbarUtil;

public class AddGoodsFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private String barCode = "";

    private AddViewModel addViewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity().getIntent().hasExtra(Constant.DATA)) {
            barCode = getActivity().getIntent().getStringExtra(Constant.DATA);
        }

        addViewModel = ViewModelProviders.of(this).get(AddViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_add_goods, container, false);
            if (TextUtils.isEmpty(barCode)) {
                SnackbarUtil.ShortSnackbar(rootView, "参数错误", SnackbarUtil.Warning);
                getActivity().finish();
            }
            initView();
            initData();
        }
        return rootView;
    }

    private TextInputEditText titleEt, desEt, costPriceEt, priceEt;
    private TextView goodsCountTv, addGoodsCountTv, goodsBarTv;
    private View costLayout;

    private void initView() {
        titleEt = rootView.findViewById(R.id.goods_title_tv);
        desEt = rootView.findViewById(R.id.goods_des_tv);
        goodsCountTv = rootView.findViewById(R.id.goods_count_tv);
        addGoodsCountTv = rootView.findViewById(R.id.add_goods_count_tv);
        costLayout = rootView.findViewById(R.id.cost_layout);
        //
        rootView.findViewById(R.id.remove_count_btn).setOnClickListener(this);
        rootView.findViewById(R.id.add_count_btn).setOnClickListener(this);
        rootView.findViewById(R.id.add_bar_btn).setOnClickListener(this);
    }

    private void initData() {
        addViewModel.addGoodsInfo.observe(this, new Observer<Goods>() {
            @Override
            public void onChanged(@Nullable Goods goods) {
                if (goods != null) {
                    titleEt.setText(goods.getGoodsName());
                    desEt.setText(goods.getGoodsDescribe());
                    addViewModel.findRepertory(goods);
                }
            }
        });
        addViewModel.mGoodRepertory.observe(this, new Observer<GoodRepertory>() {
            @Override
            public void onChanged(@Nullable GoodRepertory goodRepertory) {
                if (goodRepertory != null) {
                    goodsCountTv.setText(goodRepertory.getRepertoryCount());
                }
            }
        });
        addViewModel.mAddRepertoryCount.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null) {
                    costLayout.setVisibility(integer > 0 ? View.VISIBLE : View.GONE);
                    addGoodsCountTv.setText("" + integer);
                }
            }
        });
        addViewModel.mAddRepertoryCount.postValue(0);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_count_btn) {
            int count = addViewModel.mAddRepertoryCount.getValue();
            addViewModel.mAddRepertoryCount.postValue(count + 1);
        }
    }
}
