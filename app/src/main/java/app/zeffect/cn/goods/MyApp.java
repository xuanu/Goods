package app.zeffect.cn.goods;

import android.app.Application;

import app.zeffect.cn.goods.orm.GoodsOrm;
import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;

public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        GoodsOrm.init(getApplicationContext());
        BGASwipeBackHelper.init(this, null);
    }
}
