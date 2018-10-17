package app.zeffect.cn.goods.bean;

/***
 * 商品属性
 */
public class Goods {
    private int id;
    private String goodsName;
    private double goodsPrice;
    private String goodsDescribe;//商品描述
    private int goodsTotal;//商品总数，进货多少商品
    private int goodsCount;//库存
    private double goodsPrimeCost;//进货价格，成本
    private long creatTime;
}
