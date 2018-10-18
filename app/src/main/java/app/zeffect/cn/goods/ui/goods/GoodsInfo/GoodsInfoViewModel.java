package app.zeffect.cn.goods.ui.goods.GoodsInfo;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import app.zeffect.cn.goods.bean.Goods;

public class GoodsInfoViewModel extends ViewModel {
    public MutableLiveData<Goods> goodsMutableLiveData = new MutableLiveData<>();

}
