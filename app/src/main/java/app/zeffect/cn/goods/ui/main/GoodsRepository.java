package app.zeffect.cn.goods.ui.main;

import android.text.SpannableStringBuilder;
import android.text.TextUtils;

import com.litesuits.orm.db.assit.QueryBuilder;
import com.litesuits.orm.db.assit.WhereBuilder;

import java.util.Collections;
import java.util.List;

import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.orm.GoodsOrm;
import app.zeffect.cn.goods.utils.GoodsUtils;

/***
 * 商品库
 */
public class GoodsRepository {

    public static List<Goods> searchGoodsBarCode(String searchKey) {
        if (TextUtils.isEmpty(searchKey)) {
            return GoodsOrm.getInstance().query(Goods.class);
        }
        List<Goods> findGoods = GoodsOrm.getInstance().query(new QueryBuilder<>(Goods.class).whereEquals(Goods.COL_BAR_CODE, searchKey));
        //搜索变色
        for (int i = 0; i < findGoods.size(); i++) {
            Goods tempGoods = findGoods.get(i);
            String goodsBar = tempGoods.getBarCode();
            tempGoods.setBarBuilder(GoodsUtils.makeSearchSpan(goodsBar, searchKey));
        }
        return findGoods;
    }

    public static List<Goods> likeGoodsBar(String searchKey) {
        if (TextUtils.isEmpty(searchKey)) {
            return Collections.emptyList();
        }
        List<Goods> findGoods = GoodsOrm.getInstance().query(new QueryBuilder<>(Goods.class).where(Goods.COL_BAR_CODE + " like ?", new Object[]{"%" + searchKey + "%"}));
        //搜索变色
        for (int i = 0; i < findGoods.size(); i++) {
            Goods tempGoods = findGoods.get(i);
            String goodsBar = tempGoods.getBarCode();
            tempGoods.setBarBuilder(GoodsUtils.makeSearchSpan(goodsBar, searchKey));
        }
        return findGoods;
    }

    /***
     * 模糊搜索商品，%前后%匹配
     * @return
     */
    public static List<Goods> searchGoods1(String searchKey) {
        if (TextUtils.isEmpty(searchKey)) {
            return Collections.emptyList();
        }
        List<Goods> findGoods = GoodsOrm.getInstance().query(new QueryBuilder<>(Goods.class).where(Goods.COL_NAME + " like ?", new Object[]{"%" + searchKey + "%"}));
        //搜索变色
        for (int i = 0; i < findGoods.size(); i++) {
            Goods tempGoods = findGoods.get(i);
            String goodsBar = tempGoods.getGoodsName();
            tempGoods.setNameBuilder(GoodsUtils.makeSearchSpan(goodsBar, searchKey));
        }
        return findGoods;
    }


    /***
     * 模糊搜索商品，%前%中%后%匹配
     * @return
     */
    public static List<Goods> searchGoodsDescribe(String searchKey) {
        if (TextUtils.isEmpty(searchKey)) {
            return Collections.emptyList();
        }
        List<Goods> findGoods = GoodsOrm.getInstance().query(new QueryBuilder<>(Goods.class).where(Goods.COL_DESCRIBE + " like ?", new Object[]{"%" + searchKey + "%"}));
        //搜索变色
        for (int i = 0; i < findGoods.size(); i++) {
            Goods tempGoods = findGoods.get(i);
            String goodsBar = tempGoods.getGoodsDescribe();
            tempGoods.setDesBuilder(GoodsUtils.makeSearchSpan(goodsBar, searchKey));
        }
        return findGoods;

    }

    /***
     * 模糊搜索商品，%前%中%后%匹配
     * @return
     */
    public static List<Goods> searchGoodsPrice(String price) {
        if (TextUtils.isEmpty(price)) {
            return Collections.emptyList();
        }
        List<Goods> findGoods = GoodsOrm.getInstance().query(new QueryBuilder<>(Goods.class).where(Goods.COL_PRICE + " like ?", new Object[]{"%" + price + "%"}));
        return findGoods;
    }


    public static boolean saveGoods(Goods goods) {
        if (goods == null) return false;
        return GoodsOrm.getInstance().save(goods) > 0;
    }
}
