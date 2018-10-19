package app.zeffect.cn.goods.ui.goods.addGoods;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;

import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.List;

import app.zeffect.cn.goods.bean.GoodRepertory;
import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.orm.GoodsOrm;
import app.zeffect.cn.goods.utils.DoAsync;

public class AddViewModel extends ViewModel {
    public MutableLiveData<Goods> addGoodsInfo = new MutableLiveData<>();
    public MutableLiveData<GoodRepertory> mGoodRepertory = new MutableLiveData<>();
    public MutableLiveData<Integer> mAddRepertoryCount = new MutableLiveData<>();


    public void findRepertory(Goods goods) {
        if (goods == null) return;
        new AsyncTask<Long, Void, GoodRepertory>() {
            @Override
            protected GoodRepertory doInBackground(Long... longs) {
                List<GoodRepertory> repertories = GoodsOrm.getInstance().query(new QueryBuilder<>(GoodRepertory.class).whereEquals(GoodRepertory.COL_GOODS_ID, longs[0]));
                if (repertories != null && !repertories.isEmpty()) {
                    return repertories.get(0);
                }
                return null;
            }

            @Override
            protected void onPostExecute(GoodRepertory goodRepertory) {
                super.onPostExecute(goodRepertory);
                mGoodRepertory.postValue(goodRepertory);
            }
        }.execute(goods.getId());
    }

}
