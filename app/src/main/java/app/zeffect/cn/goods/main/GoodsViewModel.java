package app.zeffect.cn.goods.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import app.zeffect.cn.goods.bean.Goods;

public final class GoodsViewModel extends ViewModel {
    private MutableLiveData<List<Goods>> goodsLiveData = new MutableLiveData<>();

    public LiveData<List<Goods>> getGoodsLiveData() {
        return goodsLiveData;
    }

    public void getGoods() {
        List<Goods> goods = new ArrayList<>();
        goods.add(new Goods());
        goods.add(new Goods());
        goods.add(new Goods());
        goods.add(new Goods());
        goods.add(new Goods());
        goods.add(new Goods());
        goodsLiveData.postValue(goods);
    }
}
