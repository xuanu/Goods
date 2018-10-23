package app.zeffect.cn.goods.ui.goods.GoodsInfo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.ui.base.BaseBottomSheetFragment;

public class GoodsActionFragment extends BaseBottomSheetFragment {

    private View rootView;
    private OnClickAction mClickAction;

    public GoodsActionFragment appendAction(OnClickAction onClickAction) {
        this.mClickAction = onClickAction;
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
        }
        return rootView;
    }


    public interface OnClickAction {
        public void delGoods(long goodId);

        public void editGoods(long goodId);

        public void goodsLog(long goodId);
    }

}
