package app.zeffect.cn.goods.orm;

import android.content.Context;

import com.litesuits.orm.LiteOrm;

public class GoodsOrm {
    public static LiteOrm instance;

    public static LiteOrm getInstance() {
        return instance;
    }

    public static void init(Context pTarget) {
        if (instance == null) {
            instance = LiteOrm.newSingleInstance(pTarget.getApplicationContext(), pTarget.getPackageName() + ".db");
        }
    }
}
