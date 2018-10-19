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
public class Goods implements Serializable {

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
    @Column(COL_NAME)
    private String goodsName = "";
    @Column(COL_PRICE)
    private double goodsPrice = 0;
    @Column(COL_DESCRIBE)
    private String goodsDescribe = "";//商品描述
    @Column(COL_GOODS_TOTAL)
    private int goodsTotal = 0;//商品总数，进货多少商品
    @Column(COL_GOODS_COUNT)
    private int goodsCount = 0;//库存
    @Column(COL_GOODS_PRIME_COSE)
    private double goodsPrimeCost = 0;//进货价格，成本
    @Column(COL_GOODS_IMG)
    private String goodsImg = "";
    @Column(COL_GOODS_CREAT_TIME)
    private long creatTime = 0;
    @Column(COL_BAR_CODE)
    private String barCode = "";

    @Ignore
    private SpannableStringBuilder nameBuilder;
    @Ignore
    private SpannableStringBuilder desBuilder;
    @Ignore
    private SpannableStringBuilder barBuilder;

    public SpannableStringBuilder getNameBuilder() {
        return nameBuilder;
    }

    public Goods setNameBuilder(SpannableStringBuilder nameBuilder) {
        this.nameBuilder = nameBuilder;
        return this;
    }

    public SpannableStringBuilder getDesBuilder() {
        return desBuilder;
    }

    public Goods setDesBuilder(SpannableStringBuilder desBuilder) {
        this.desBuilder = desBuilder;
        return this;
    }

    public SpannableStringBuilder getBarBuilder() {
        return barBuilder;
    }

    public Goods setBarBuilder(SpannableStringBuilder barBuilder) {
        this.barBuilder = barBuilder;
        return this;
    }

    public String getBarCode() {
        return barCode;
    }

    public Goods setBarCode(String barCode) {
        this.barCode = barCode;
        return this;
    }

    public long getId() {
        return id;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public Goods setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
        return this;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public Goods setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public Goods setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
        return this;
    }

    public String getGoodsDescribe() {
        return goodsDescribe;
    }

    public Goods setGoodsDescribe(String goodsDescribe) {
        this.goodsDescribe = goodsDescribe;
        return this;
    }

    public int getGoodsTotal() {
        return goodsTotal;
    }

    public Goods setGoodsTotal(int goodsTotal) {
        this.goodsTotal = goodsTotal;
        return this;
    }

    public int getGoodsCount() {
        return goodsCount;
    }

    public Goods setGoodsCount(int goodsCount) {
        this.goodsCount = goodsCount;
        return this;
    }

    public double getGoodsPrimeCost() {
        return goodsPrimeCost;
    }

    public Goods setGoodsPrimeCost(double goodsPrimeCost) {
        this.goodsPrimeCost = goodsPrimeCost;
        return this;
    }

    public long getCreatTime() {
        return creatTime;
    }

    public Goods setCreatTime(long creatTime) {
        this.creatTime = creatTime;
        return this;
    }
}
