package app.zeffect.cn.goods.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

@Table(GoodsSale.TABLE_GOODS_SALE)
public class GoodsSale extends BaseGoods {
    public static final String TABLE_GOODS_SALE = "goods_sale";
    public static final String COL_ID = "id";
    public static final String COL_GOODS_ID = "goodsid";
    public static final String COL_GOODS_SALE_COUNT = "salecount";

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column(COL_ID)
    private long id;
    @Column(COL_GOODS_ID)
    private long goodsId;//关联商品ID（记下关联，可以追溯）
    @Column(COL_GOODS_SALE_COUNT)
    private int goodsSaleCount;

    public long getId() {
        return id;
    }

    public GoodsSale setId(long id) {
        this.id = id;
        return this;
    }

    public long getGoodsId() {
        return goodsId;
    }

    public GoodsSale setGoodsId(long goodsId) {
        this.goodsId = goodsId;
        return this;
    }

    public int getGoodsSaleCount() {
        return goodsSaleCount;
    }

    public GoodsSale setGoodsSaleCount(int goodsSaleCount) {
        this.goodsSaleCount = goodsSaleCount;
        return this;
    }
}
