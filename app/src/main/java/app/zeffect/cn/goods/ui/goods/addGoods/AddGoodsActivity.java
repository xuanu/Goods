package app.zeffect.cn.goods.ui.goods.addGoods;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.ui.BaseActivity;

public class AddGoodsActivity extends BaseActivity {

    private Fragment addGoodsFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_goods);
        addGoodsFragment = new AddGoodsFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.add_goods_layout, addGoodsFragment).commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (addGoodsFragment != null) {
            addGoodsFragment.onActivityResult(requestCode, resultCode, data);
        }
    }
}
