package app.zeffect.cn.goods.utils;

import java.util.List;

import app.zeffect.cn.goods.bean.Goods;

public class ListUtils {
    public static void removeSame(List<Goods> goods1, List<Goods> goods2) {
        for (int i = 0; i < goods1.size(); i++) {
            long id = goods1.get(i).getId();
            for (int j = 0; j < goods2.size(); j++) {
                long id2 = goods2.get(j).getId();
                if (id == id2) {
                    goods2.remove(j);
                    j--;
                    break;
                }
            }
        }
    }
}
