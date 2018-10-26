package app.zeffect.cn.goods.ui.main;

import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.io.File;
import java.util.List;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.bean.Goods;

public class GoodsAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {
    public GoodsAdapter(@Nullable List<Goods> data) {
        super(R.layout.item_goods_layout, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, Goods item) {
        if (item.getNameBuilder() != null) {
            helper.setText(R.id.item_title_tv, item.getNameBuilder());
        } else {
            helper.setText(R.id.item_title_tv, item.getGoodsName());
        }
        if (item.getDesBuilder() != null) {
            helper.setText(R.id.item_des_tv, item.getDesBuilder());
        } else {
            helper.setText(R.id.item_des_tv, item.getGoodsDescribe());
        }
        helper.setText(R.id.item_price_tv, "￥" + item.getGoodsPrice());
        if (item.getBarBuilder() != null) {
            helper.setText(R.id.item_bar_tv, "条形码：" + item.getBarBuilder());
        } else {
            helper.setText(R.id.item_bar_tv, "条形码：" + item.getBarCodeStr2());
        }
        String imgPath = item.getGoodsImg();
        if (!TextUtils.isEmpty(imgPath)) {
            Glide.with(helper.itemView.getContext()).load(new File(imgPath)).into((ImageView) helper.getView(R.id.item_goods_img));
        }
    }
}
