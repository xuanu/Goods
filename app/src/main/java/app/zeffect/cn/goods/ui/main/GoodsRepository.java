package app.zeffect.cn.goods.ui.main;

import android.text.TextUtils;

import java.util.Collections;
import java.util.List;

import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.orm.GoodsOrm;

/***
 * 商品库
 */
public class GoodsRepository {

    /***
     * 模糊搜索商品，%前后%匹配
     * @return
     */
    public static List<Goods> searchGoods1(String searchKey) {
        if (TextUtils.isEmpty(searchKey)) {
            return GoodsOrm.getInstance().query(Goods.class);
        }
        return Collections.emptyList();
    }


    /***
     * 模糊搜索商品，%前%中%后%匹配
     * @return
     */
    public static List<Goods> searchGoods2(String searchKey) {
        if (TextUtils.isEmpty(searchKey)) {
            return GoodsOrm.getInstance().query(Goods.class);
        }
        return Collections.emptyList();
    }


    public static boolean saveGoods(Goods goods) {
        if (goods == null) return false;
        return GoodsOrm.getInstance().save(goods) > 0;
    }
}
