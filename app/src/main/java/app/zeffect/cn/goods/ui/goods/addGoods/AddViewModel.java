package app.zeffect.cn.goods.ui.goods.addGoods;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.List;

import app.zeffect.cn.goods.bean.GoodRepertory;
import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.orm.GoodsOrm;
import app.zeffect.cn.goods.ui.main.GoodsRepository;
import app.zeffect.cn.goods.utils.DoAsync;

public class AddViewModel extends ViewModel {
    public MutableLiveData<Goods> addGoodsInfo = new MutableLiveData<>();
    public MutableLiveData<GoodRepertory> mGoodRepertory = new MutableLiveData<>();
    public MutableLiveData<Integer> mAddRepertoryCount = new MutableLiveData<>();


    public void findGoods(String barCode) {
        if (TextUtils.isEmpty(barCode)) return;
        new AsyncTask<String, Void, Goods>() {

            private String mBarCode = "";

            @Override
            protected Goods doInBackground(String... strings) {
                mBarCode = strings[0];
                List<Goods> goods = GoodsRepository.likeGoodsBar(strings[0]);
                if (goods != null && !goods.isEmpty()) {
                    return goods.get(0);
                }
                return null;
            }

            @Override
            protected void onPostExecute(Goods goods) {
                super.onPostExecute(goods);
                if (goods == null) {
                    goods = new Goods();
                    goods.setBarCode(mBarCode);
                }
                addGoodsInfo.postValue(goods);
            }
        }.execute(barCode);
    }

    public void findRepertory(long goodsId) {
        if (goodsId < 0) {
            mGoodRepertory.postValue(new GoodRepertory().setGoodsId(goodsId));
            return;
        }
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
        }.execute(goodsId);
    }

}
