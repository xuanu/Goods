package app.zeffect.cn.goods.bean;

import android.text.SpannableStringBuilder;

import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/***
 * 商品属性
 */
@Table("goods_infos")
public class Goods extends BaseGoods implements Serializable, Cloneable {

    public static final String COL_NAME = "name";
    public static final String COL_DESCRIBE = "describe";
    public static final String COL_PRICE = "price";
    public static final String COL_GOODS_IMG = "goodsimg";
    public static final String COL_GOODS_CREAT_TIME = "creattime";
    public static final String COL_BAR_CODE = "barcode";

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private long id;


    public long getId() {
        return id;
    }


    @Ignore
    private transient SpannableStringBuilder nameBuilder;
    @Ignore
    private transient SpannableStringBuilder desBuilder;
    @Ignore
    private transient SpannableStringBuilder barBuilder;

    public SpannableStringBuilder getNameBuilder() {
        return nameBuilder;
    }

    public BaseGoods setNameBuilder(SpannableStringBuilder nameBuilder) {
        this.nameBuilder = nameBuilder;
        return this;
    }

    public SpannableStringBuilder getDesBuilder() {
        return desBuilder;
    }

    public BaseGoods setDesBuilder(SpannableStringBuilder desBuilder) {
        this.desBuilder = desBuilder;
        return this;
    }

    public SpannableStringBuilder getBarBuilder() {
        return barBuilder;
    }

    public BaseGoods setBarBuilder(SpannableStringBuilder barBuilder) {
        this.barBuilder = barBuilder;
        return this;
    }
}
