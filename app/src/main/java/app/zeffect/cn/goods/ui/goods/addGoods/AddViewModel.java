package app.zeffect.cn.goods.ui.goods.addGoods;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.os.AsyncTask;
import android.text.TextUtils;

import com.litesuits.orm.db.assit.QueryBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import app.zeffect.cn.goods.bean.GoodRepertory;
import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.orm.GoodsOrm;
import app.zeffect.cn.goods.ui.main.GoodsRepository;
import app.zeffect.cn.goods.utils.DoAsync;
import app.zeffect.cn.goods.utils.GoodsUtils;

public class AddViewModel extends ViewModel {
    public MutableLiveData<Goods> defaultGoodsInfo = new MutableLiveData<>();//原始商品属性
    public MutableLiveData<Goods> addGoodsInfo = new MutableLiveData<>();//添加商品属性
    public MutableLiveData<GoodRepertory> mGoodRepertory = new MutableLiveData<>();
    public MutableLiveData<Integer> mAddRepertoryCount = new MutableLiveData<>();
    public MutableLiveData<Boolean> mSureAdd = new MutableLiveData<>();//确认添加
    public MutableLiveData<Long> mExpirationDay = new MutableLiveData<>();


    public void findGoods(final long goodsId, String barCode) {
        if (barCode == null) barCode = "";
        new AsyncTask<String, Void, Goods>() {

            private String mBarCode = "";

            @Override
            protected Goods doInBackground(String... strings) {
                mBarCode = strings[0];
                if (goodsId >= 0) {
                    Goods goods = GoodsOrm.getInstance().queryById(goodsId, Goods.class);
                    return goods;
                }
                return null;
            }

            @Override
            protected void onPostExecute(Goods goods) {
                super.onPostExecute(goods);
                if (goods == null) {
                    goods = new Goods();
                    ArrayList<String> barCodes = new ArrayList<>();
                    barCodes.add(mBarCode);
                    goods.setBarCode(barCodes);
                }
                addGoodsInfo.postValue(goods);
                try {
                    defaultGoodsInfo.postValue((Goods) GoodsUtils.cloneObject(goods));
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }.execute(barCode);
    }

    public void findRepertory(long goodsId) {

        new AsyncTask<Long, Void, GoodRepertory>() {

            private long goodsId;

            @Override
            protected GoodRepertory doInBackground(Long... longs) {
                goodsId = longs[0];
                if (goodsId < 0) {
                    return null;
                }
                List<GoodRepertory> repertories = GoodsOrm.getInstance().query(new QueryBuilder<>(GoodRepertory.class).whereEquals(GoodRepertory.COL_GOODS_ID, longs[0]));
                if (repertories != null && !repertories.isEmpty()) {
                    return repertories.get(0);
                }
                return null;
            }

            @Override
            protected void onPostExecute(GoodRepertory goodRepertory) {
                super.onPostExecute(goodRepertory);
                if (goodRepertory == null) {
                    goodRepertory = new GoodRepertory();
                }
                mGoodRepertory.postValue(goodRepertory);
            }
        }.execute(goodsId);
    }

}
