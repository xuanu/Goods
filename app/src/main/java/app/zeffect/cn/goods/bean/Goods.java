package app.zeffect.cn.goods.bean;

import com.litesuits.orm.db.annotation.Table;

import java.io.Serializable;

/***
 * 商品属性
 */
@Table("goods_infos")
public class Goods implements Serializable {
    private int id;
    private String goodsName;
    private double goodsPrice;
    private String goodsDescribe;//商品描述
    private int goodsTotal;//商品总数，进货多少商品
    private int goodsCount;//库存
    private double goodsPrimeCost;//进货价格，成本
    private String goodsImg;
    private long creatTime;

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
