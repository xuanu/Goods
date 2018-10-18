package app.zeffect.cn.goods.ui.main;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

import app.zeffect.cn.goods.bean.Goods;

public final class GoodsViewModel extends ViewModel {



    private MutableLiveData<List<Goods>> goodsLiveData = new MutableLiveData<>();

    public LiveData<List<Goods>> getGoodsLiveData() {
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
                searchGoods.addAll(GoodsRepository.searchGoods1(searchKey));
                onProgressUpdate(searchGoods);
                if (!TextUtils.isEmpty(searchKey)) {
                    List<Goods> searchGoods2 = GoodsRepository.searchGoods2(searchKey);
                    if (searchGoods2 != null && !searchGoods2.isEmpty()) {
                        searchGoods.addAll(searchGoods2);
                        onProgressUpdate(searchGoods);
                    }
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
