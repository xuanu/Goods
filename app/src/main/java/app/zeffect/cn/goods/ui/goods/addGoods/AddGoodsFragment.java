package app.zeffect.cn.goods.ui.goods.addGoods;

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
import app.zeffect.cn.goods.utils.Constant;

public class AddGoodsFragment extends Fragment implements View.OnClickListener {
    private View rootView;
    private String barCode = "";



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity().getIntent().hasExtra(Constant.DATA)) {
            barCode = getActivity().getIntent().getStringExtra(Constant.DATA);
        }
        if (TextUtils.isEmpty(barCode)) {
            // TODO: 2018/10/19 SnackBar参数错数
            getActivity().finish();
        }

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_add_goods, container, false);
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

    }

    @Override
    public void onClick(View view) {

    }
}
