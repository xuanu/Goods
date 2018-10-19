package app.zeffect.cn.goods.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.utils.ListUtils;

public final class GoodsViewModel extends ViewModel {


    private MutableLiveData<List<Goods>> goodsLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Goods>> getGoodsLiveData() {
        return goodsLiveData;
    }


    private AsyncTask searchTask;

    public void getGoods(String searchKey) {
        if (searchTask != null) {
            searchTask.cancel(true);
        }
        if (searchKey == null) searchKey = "";
        searchTask = new AsyncTask<String, List<Goods>, List<Goods>>() {
            @Override
            protected List<Goods> doInBackground(String[] objects) {
                String searchKey = objects[0];
                List<Goods> searchGoods = new ArrayList<>();
                searchGoods.addAll(GoodsRepository.searchGoodsBarCode(searchKey));
                onProgressUpdate(searchGoods);
                if (!TextUtils.isEmpty(searchKey)) {
                    //模糊搜索名字
                    List<Goods> searchGood4 = GoodsRepository.searchGoods1(searchKey);
                    if (searchGood4 != null && !searchGood4.isEmpty()) {
                        ListUtils.removeSame(searchGoods, searchGood4);
                        searchGoods.addAll(searchGood4);
                        onProgressUpdate(searchGoods);
                    }
                    //模糊搜索价格
                    List<Goods> searchGood3 = GoodsRepository.searchGoodsPrice(searchKey);
                    if (searchGood3 != null && !searchGood3.isEmpty()) {
                        ListUtils.removeSame(searchGoods, searchGood3);
                        searchGoods.addAll(searchGood3);
                        onProgressUpdate(searchGoods);
                    }
                    //模糊搜索描述
                    List<Goods> searchGoods2 = GoodsRepository.searchGoodsDescribe(searchKey);
                    if (searchGoods2 != null && !searchGoods2.isEmpty()) {
                        ListUtils.removeSame(searchGoods, searchGoods2);
                        searchGoods.addAll(searchGoods2);//去重
                        onProgressUpdate(searchGoods);
                    }
                    //模糊搜索条形码

                }
                return null;
            }

            @Override
            protected void onProgressUpdate(List<Goods>... values) {
                super.onProgressUpdate(values);
                if (values[0] != null) {
                    goodsLiveData.postValue(values[0]);
                }
            }
        }.execute(searchKey);
    }
}
