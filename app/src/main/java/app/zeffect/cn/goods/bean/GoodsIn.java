package app.zeffect.cn.goods.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

/**
 *
 */
@Table(GoodsIn.TABLE_GOODS_IN)
public class GoodsIn extends BaseGoods {
    public static final String TABLE_GOODS_IN = "goods_in";
    public static final String COL_ID = "id";
    public static final String COL_GOODS_ID = "goodsid";
    public static final String COL_GOODS_IN_COUNT = "incount";

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column(COL_ID)
    private long id;
    @Column(COL_GOODS_ID)
    private long goodsId;//关联商品ID（记下关联，可以追溯）
    @Column(COL_GOODS_IN_COUNT)
    private int goodsInCount;
}
