package app.zeffect.cn.goods.ui.goods.addGoods;

import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.bean.GoodRepertory;
import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.utils.ChoseImage;
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
        addViewModel.mSureAdd.postValue(false);
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

    private TextInputLayout titleLayout, desLayout;
    private TextInputEditText titleEt, desEt, costPriceEt, priceEt;
    private TextView goodsCountTv, addGoodsCountTv, goodsBarTv;
    private ImageView goodsImg;
    private View costLayout, lastCountLayout, addCountLayout, saleLayout, barLayout;

    private void initView() {
        saleLayout = rootView.findViewById(R.id.sale_layout);
        barLayout = rootView.findViewById(R.id.bar_layout);
        addCountLayout = rootView.findViewById(R.id.add_count_layout);
        lastCountLayout = rootView.findViewById(R.id.last_count_layout);
        goodsImg = rootView.findViewById(R.id.goods_img);
        titleLayout = rootView.findViewById(R.id.good_title_layout);
        desLayout = rootView.findViewById(R.id.goods_des_layout);
        titleEt = rootView.findViewById(R.id.goods_title_tv);
        desEt = rootView.findViewById(R.id.goods_des_tv);
        goodsCountTv = rootView.findViewById(R.id.goods_count_tv);
        addGoodsCountTv = rootView.findViewById(R.id.add_goods_count_tv);
        costPriceEt = rootView.findViewById(R.id.goods_cost_price_tv);
        priceEt = rootView.findViewById(R.id.goods_price_tv);
        costLayout = rootView.findViewById(R.id.cost_layout);
        goodsBarTv = rootView.findViewById(R.id.goods_bar_tv);
        //
        rootView.findViewById(R.id.remove_count_btn).setOnClickListener(this);
        rootView.findViewById(R.id.add_count_btn).setOnClickListener(this);
        rootView.findViewById(R.id.add_bar_btn).setOnClickListener(this);
        rootView.findViewById(R.id.left_back_btn).setOnClickListener(this);
        rootView.findViewById(R.id.sure_add_goods_btn).setOnClickListener(this);
        rootView.findViewById(R.id.goods_img).setOnClickListener(this);
        //
        titleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String data = editable.toString().trim();
                titleLayout.setErrorEnabled(TextUtils.isEmpty(data) ? true : false);
                addViewModel.addGoodsInfo.getValue().setGoodsName(data);
            }
        });
        desEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String data = editable.toString().trim();
                desLayout.setErrorEnabled(TextUtils.isEmpty(data) ? true : false);
                addViewModel.addGoodsInfo.getValue().setGoodsDescribe(data);
            }
        });
        priceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String data = editable.toString().trim();
                try {
                    addViewModel.addGoodsInfo.getValue().setGoodsPrice(Double.parseDouble(data));
                } catch (NumberFormatException e) {

                }

            }
        });
        costPriceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String data = editable.toString().trim();
                try {
                    addViewModel.addGoodsInfo.getValue().setGoodsCostPrice(Double.parseDouble(data));
                } catch (NumberFormatException e) {

                }

            }
        });

    }

    private void initData() {
        addViewModel.addGoodsInfo.observe(this, new Observer<Goods>() {
            @Override
            public void onChanged(@Nullable Goods goods) {
                if (goods != null) {
                    titleEt.setText(goods.getGoodsName());
                    desEt.setText(goods.getGoodsDescribe());
                    goodsBarTv.setText(goods.getBarCode());
                    costPriceEt.setText(goods.getGoodsCostPrice() + "");
                    priceEt.setText(goods.getGoodsPrice() + "");
                    addViewModel.findRepertory(goods.getId());
                }
            }
        });
        addViewModel.mGoodRepertory.observe(this, new Observer<GoodRepertory>() {
            @Override
            public void onChanged(@Nullable GoodRepertory goodRepertory) {
                if (goodRepertory != null) {
                    goodsCountTv.setText(goodRepertory.getRepertoryCount() + "");
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
        addViewModel.findGoods(barCode);
        addViewModel.mSureAdd.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if (aBoolean) {
                    showDiff();
                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.add_count_btn) {
            int count = addViewModel.mAddRepertoryCount.getValue();
            addViewModel.mAddRepertoryCount.postValue(count + 1);
        } else if (view.getId() == R.id.remove_count_btn) {
            int count = addViewModel.mAddRepertoryCount.getValue();
            if (count >= 1) {
                addViewModel.mAddRepertoryCount.postValue(count - 1);
            }
        } else if (view.getId() == R.id.left_back_btn) {
            getActivity().finish();
        } else if (view.getId() == R.id.sure_add_goods_btn) {
            boolean canAdd = addViewModel.mSureAdd.getValue();
            if (!canAdd) {
                canAdd = checkAdd();
                addViewModel.mSureAdd.postValue(canAdd);
                return;
            }
            //添加
        } else if (view.getId() == R.id.goods_img) {
            ChoseImage.choseImageFromGallery(getContext(), 0x10);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0x10) {
            if (resultCode == Activity.RESULT_OK) {
                String imgPath = ChoseImage.getGalleryPath(getContext(), data);
                Log.e("zeffect", "img path:" + imgPath);
            }
        }
    }

    /**
     * 检查添加
     */
    public boolean checkAdd() {
        boolean checkSuccess = true;
        //
        String goodsTitleStr = titleEt.getText().toString().trim();
        if (TextUtils.isEmpty(goodsTitleStr)) {
            checkSuccess = false;
        }
        titleLayout.setError("输入商品名");
        titleLayout.setErrorEnabled(TextUtils.isEmpty(goodsTitleStr) ? true : false);
        //
        String goodsDesStr = desEt.getText().toString().trim();
        if (TextUtils.isEmpty(goodsDesStr)) {
            checkSuccess = false;
        }
        desLayout.setError("输入商品描述");
        desLayout.setErrorEnabled(TextUtils.isEmpty(goodsDesStr) ? true : false);
        //
        return checkSuccess;
    }

    /**
     * 显示不同的东西，
     */
    private void showDiff() {
        Goods defaultGoods = addViewModel.defaultGoodsInfo.getValue();
        Goods addGoods = addViewModel.addGoodsInfo.getValue();
        goodsImg.setVisibility(defaultGoods.getGoodsImg().equalsIgnoreCase(addGoods.getGoodsImg()) ? View.INVISIBLE : View.VISIBLE);
        titleLayout.setVisibility(defaultGoods.getGoodsName().equalsIgnoreCase(addGoods.getGoodsName()) ? View.INVISIBLE : View.VISIBLE);
        desLayout.setVisibility(defaultGoods.getGoodsDescribe().equalsIgnoreCase(addGoods.getGoodsDescribe()) ? View.INVISIBLE : View.VISIBLE);
        costLayout.setVisibility(defaultGoods.getGoodsCostPrice() == addGoods.getGoodsCostPrice() ? View.INVISIBLE : View.VISIBLE);
        saleLayout.setVisibility(defaultGoods.getGoodsPrice() == addGoods.getGoodsPrice() ? View.INVISIBLE : View.VISIBLE);
        barLayout.setVisibility(defaultGoods.getBarCode().equalsIgnoreCase(addGoods.getBarCode()) ? View.INVISIBLE : View.VISIBLE);
        addCountLayout.setVisibility(addViewModel.mAddRepertoryCount.getValue() > 0 ? View.VISIBLE : View.INVISIBLE);
        lastCountLayout.setVisibility(View.INVISIBLE);
    }

}
