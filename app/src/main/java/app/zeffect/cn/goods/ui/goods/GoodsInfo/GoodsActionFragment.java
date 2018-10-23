package app.zeffect.cn.goods.ui.goods.GoodsInfo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.ui.base.BaseBottomSheetFragment;

public class GoodsActionFragment extends BaseBottomSheetFragment implements View.OnClickListener {

    private View rootView;
    private OnClickAction mClickAction;
    private long goodsId;

    public GoodsActionFragment appendAction(OnClickAction onClickAction, long tmpGoodsId) {
        this.mClickAction = onClickAction;
        this.goodsId = tmpGoodsId;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.dialog_goods_action_fragment, container, false);
            initView();
        }
        return rootView;
    }

    private void initView() {
        rootView.findViewById(R.id.goods_log_tv).setOnClickListener(this);
        rootView.findViewById(R.id.del_goods_tv).setOnClickListener(this);
        rootView.findViewById(R.id.edit_goods_tv).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.goods_log_tv) {

        } else if (v.getId() == R.id.del_goods_tv) {
            if (mClickAction != null) mClickAction.delGoods(goodsId);
            this.dismiss();
        } else if (v.getId() == R.id.edit_goods_tv) {
            if (mClickAction != null) mClickAction.editGoods(goodsId);
            this.dismiss();
        }
    }


    public interface OnClickAction {
        public void delGoods(long goodId);

        public void editGoods(long goodId);

        public void goodsLog(long goodId);
    }

}
