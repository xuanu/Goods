package app.zeffect.cn.goods.bean;

import android.text.TextUtils;

import com.litesuits.orm.db.annotation.Column;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class BaseGoods implements Serializable {

    public static final String COL_GOODS_COST_PRICE = "costprice";
    public static final String COL_TITLE_SPELL = "titlespell";


    @Column(Goods.COL_NAME)
    private String goodsName = "";
    @Column(Goods.COL_PRICE)
    private double goodsPrice = 0;
    @Column(Goods.COL_DESCRIBE)
    private String goodsDescribe = "";//商品描述
    @Column(Goods.COL_GOODS_IMG)
    private String goodsImg = "";
    @Column(Goods.COL_BAR_CODE)
    private ArrayList<String> barCode = new ArrayList<>();
    @Column(COL_GOODS_COST_PRICE)
    private double goodsCostPrice = 0;//进价

    @Column(Goods.COL_GOODS_CREAT_TIME)
    private long creatTime = 0;

    @Column(COL_TITLE_SPELL)
    private ArrayList<String> titleSpell;

    public ArrayList<String> getTitleSpell() {
        return titleSpell;
    }

    public BaseGoods setTitleSpell(ArrayList<String> titleSpell) {
        this.titleSpell = titleSpell;
        return this;
    }

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

    public ArrayList<String> getBarCode() {
        if (barCode == null) barCode = new ArrayList<>();
        return barCode;
    }

    public String getBarCodeStr() {
        if (barCode == null) barCode = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < barCode.size(); i++) {
            builder.append(barCode.get(i));
            if (i < barCode.size() - 1) builder.append(",");
        }
        return builder.toString();
    }

    public BaseGoods setBarCode(ArrayList<String> barCode) {
        this.barCode = barCode;
        return this;
    }

    public String getGoodsImg() {
        if (TextUtils.isEmpty(goodsImg)) goodsImg = "";
        return goodsImg;
    }

    public BaseGoods setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
        return this;
    }

    public String getGoodsName() {
        if (TextUtils.isEmpty(goodsName)) goodsName = "";
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
        if (TextUtils.isEmpty(goodsDescribe)) goodsDescribe = "";
        return goodsDescribe;
    }

    public BaseGoods setGoodsDescribe(String goodsDescribe) {
        this.goodsDescribe = goodsDescribe;
        return this;
    }


}
