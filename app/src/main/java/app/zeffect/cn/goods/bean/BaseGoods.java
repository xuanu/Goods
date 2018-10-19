package app.zeffect.cn.goods.bean;

import com.litesuits.orm.db.annotation.Column;

public class BaseGoods {

    public static final String COL_GOODS_COST_PRICE="costprice";

    @Column(Goods.COL_NAME)
    private String goodsName = "";
    @Column(Goods.COL_PRICE)
    private double goodsPrice = 0;
    @Column(Goods.COL_DESCRIBE)
    private String goodsDescribe = "";//商品描述
    @Column(Goods.COL_GOODS_IMG)
    private String goodsImg = "";
    @Column(Goods.COL_BAR_CODE)
    private String barCode = "";
    @Column(COL_GOODS_COST_PRICE)
    private double goodsCostPrice = 0;//进价

    @Column(Goods.COL_GOODS_CREAT_TIME)
    private long creatTime = 0;

    public double getGoodsCostPrice() {
        return goodsCostPrice;
    }

    public BaseGoods setGoodsCostPrice(double goodsCostPrice) {
        this.goodsCostPrice = goodsCostPrice;
        return this;
    }

    public long getCreatTime() {
        return creatTime;
    }

    public BaseGoods setCreatTime(long creatTime) {
        this.creatTime = creatTime;
        return this;
    }

    public String getBarCode() {
        return barCode;
    }

    public BaseGoods setBarCode(String barCode) {
        this.barCode = barCode;
        return this;
    }


    public String getGoodsImg() {
        return goodsImg;
    }

    public BaseGoods setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
        return this;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public BaseGoods setGoodsName(String goodsName) {
        this.goodsName = goodsName;
        return this;
    }

    public double getGoodsPrice() {
        return goodsPrice;
    }

    public BaseGoods setGoodsPrice(double goodsPrice) {
        this.goodsPrice = goodsPrice;
        return this;
    }

    public String getGoodsDescribe() {
        return goodsDescribe;
    }

    public BaseGoods setGoodsDescribe(String goodsDescribe) {
        this.goodsDescribe = goodsDescribe;
        return this;
    }


}
