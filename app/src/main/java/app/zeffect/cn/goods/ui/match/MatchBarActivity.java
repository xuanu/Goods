package app.zeffect.cn.goods.ui.match;

import android.os.Bundle;
import android.support.annotation.Nullable;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.ui.BaseActivity;

/***
 * 本来想做用户可以匹配类似条形码的，
 * 如11位没有成功，匹配10位，再匹配9位....直到匹配6位左右。
 * 暂时不需要这个界面
 */
@Deprecated
public class MatchBarActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match);
    }
}
