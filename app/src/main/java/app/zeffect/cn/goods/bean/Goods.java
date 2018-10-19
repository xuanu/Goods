package app.zeffect.cn.goods.bean;

import android.text.SpannableStringBuilder;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.Ignore;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import java.io.Serializable;

/***
 * 商品属性
 */
@Table("goods_infos")
public class Goods extends BaseGoods implements Serializable {

    public static final String COL_NAME = "name";
    public static final String COL_DESCRIBE = "describe";
    public static final String COL_PRICE = "price";
    public static final String COL_GOODS_TOTAL = "total";
    public static final String COL_GOODS_COUNT = "count";
    public static final String COL_GOODS_PRIME_COSE = "primecost";
    public static final String COL_GOODS_IMG = "goodsimg";
    public static final String COL_GOODS_CREAT_TIME = "creattime";
    public static final String COL_BAR_CODE = "barcode";

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    private long id;



    @Ignore
    private SpannableStringBuilder nameBuilder;
    @Ignore
    private SpannableStringBuilder desBuilder;
    @Ignore
    private SpannableStringBuilder barBuilder;


    public long getId() {
        return id;
    }

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
