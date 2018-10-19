package app.zeffect.cn.goods.bean;

import com.litesuits.orm.db.annotation.Column;
import com.litesuits.orm.db.annotation.PrimaryKey;
import com.litesuits.orm.db.annotation.Table;
import com.litesuits.orm.db.enums.AssignType;

import app.zeffect.cn.goods.ui.main.GoodsRepository;

/**
 * 库存表
 */
@Table(GoodRepertory.TABLE_GOOD_REPERTORY)
public class GoodRepertory {
    public static final String TABLE_GOOD_REPERTORY = "goods_repertory";

    public static final String COL_ID = "id";
    public static final String COL_GOODS_ID = "goodsid";
    public static final String COL_GOODS_REPERTORY_COUNT = "repertorycount";
    public static final String COL_GOODS_REPERTORY_TOTAL = "repertorytotal";

    @PrimaryKey(AssignType.AUTO_INCREMENT)
    @Column(COL_ID)
    private long id;
    @Column(COL_GOODS_ID)
    private long goodsId;//关联商品ID（记下关联，可以追溯）
    @Column(COL_GOODS_REPERTORY_COUNT)
    private int repertoryCount;//库存数量
    @Column(COL_GOODS_REPERTORY_TOTAL)
    private int repertoryTotal;

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public int getRepertoryCount() {
        return repertoryCount;
    }

    public void setRepertoryCount(int repertoryCount) {
        this.repertoryCount = repertoryCount;
    }

    public int getRepertoryTotal() {
        return repertoryTotal;
    }

    public void setRepertoryTotal(int repertoryTotal) {
        this.repertoryTotal = repertoryTotal;
    }
}
