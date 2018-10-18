package app.zeffect.cn.goods.ui.main;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.bean.Goods;

public class GoodsAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {
    public GoodsAdapter(@Nullable List<Goods> data) {
        super(R.layout.item_goods_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods item) {
        helper.setText(R.id.item_title_tv, item.getGoodsName())
                .setText(R.id.item_des_tv, item.getGoodsDescribe())
                .setText(R.id.item_price_tv, "￥" + item.getGoodsPrice());
    }
}