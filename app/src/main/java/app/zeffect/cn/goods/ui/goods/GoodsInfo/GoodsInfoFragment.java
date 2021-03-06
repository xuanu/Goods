package app.zeffect.cn.goods.ui.goods.GoodsInfo;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.litesuits.orm.db.assit.QueryBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.List;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.bean.GoodRepertory;
import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.eventbean.DelGoods;
import app.zeffect.cn.goods.orm.GoodsOrm;
import app.zeffect.cn.goods.ui.goods.addGoods.AddGoodsActivity;
import app.zeffect.cn.goods.ui.main.GoodsRepository;
import app.zeffect.cn.goods.utils.Constant;
import app.zeffect.cn.goods.utils.DoAsync;
import app.zeffect.cn.goods.utils.SnackbarUtil;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class GoodsInfoFragment extends Fragment implements View.OnClickListener, GoodsActionFragment.OnClickAction {

    private View rootView;

    private GoodsInfoViewModel goodsInfoViewModel;

    private TextView mTitleTv, mDesTv, mCountTv, mPriceTv;
    private ImageView mGoodsImg;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        goodsInfoViewModel = ViewModelProviders.of(this).get(GoodsInfoViewModel.class);
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateGoods(Goods updateGoods) {
        long goodsId = updateGoods.getId();
        if (goodsId <= 0) return;
        new DoAsync<Long, Void, Integer>(getActivity()) {
            @Override
            protected Integer doInBackground(Context pTarget, Long... longs) throws Exception {
                int pos = -1;
                long goodsId = longs[0];
                Goods tmpGoood = GoodsOrm.getInstance().queryById(goodsId, Goods.class);
                if (tmpGoood != null) {
                    goodsInfoViewModel.goodsMutableLiveData.postValue(tmpGoood);
                }
                return pos;
            }
        }.execute(goodsId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_goods_info, container, false);
            initView();
            initData();
        }
        return rootView;
    }

    private Button saleBtn;

    private void initView() {
        rootView.findViewById(R.id.left_back_btn).setOnClickListener(this);
        mTitleTv = (TextView) rootView.findViewById(R.id.goods_title_tv);
        mDesTv = (TextView) rootView.findViewById(R.id.goods_des_tv);
        mCountTv = rootView.findViewById(R.id.goods_count_tv);
        mPriceTv = rootView.findViewById(R.id.goods_price_tv);
        mGoodsImg = rootView.findViewById(R.id.goods_img);
        saleBtn = rootView.findViewById(R.id.goods_sale_btn);
        saleBtn.setOnClickListener(this);
        rootView.findViewById(R.id.more_action_btn).setOnClickListener(this);
    }

    private void initData() {
        //数据显示
        goodsInfoViewModel.goodsMutableLiveData.observe(this, new Observer<Goods>() {
            @Override
            public void onChanged(@Nullable Goods goods) {
                if (goods != null) {
                    mTitleTv.setText(goods.getGoodsName());
                    mDesTv.setText(goods.getGoodsDescribe());
                    mPriceTv.setText("￥" + goods.getGoodsPrice() + "");
                    String imgPath = goods.getGoodsImg();
                    if (!TextUtils.isEmpty(imgPath)) {
                        Glide.with(getContext()).load(new File(imgPath)).into(mGoodsImg);
                    }
                    long goodsId = goods.getId();
                    if (goodsId > -1) {
                        List<GoodRepertory> repertorys = GoodsOrm.getInstance().query(new QueryBuilder<>(GoodRepertory.class).whereEquals(GoodRepertory.COL_GOODS_ID, goodsId));
                        if (repertorys != null && !repertorys.isEmpty()) {
                            goodsInfoViewModel.goodRepertoryLiveData.postValue(repertorys.get(0));
                        }
                    }
                }
            }
        });
        goodsInfoViewModel.goodRepertoryLiveData.observe(this, new Observer<GoodRepertory>() {
            @Override
            public void onChanged(@Nullable GoodRepertory goodRepertory) {
                if (goodRepertory != null) {
                    mCountTv.setText(goodRepertory.getRepertoryCount() + "");
                    saleBtn.setEnabled(goodRepertory.getRepertoryCount() >= 1);
                    saleBtn.setText(goodRepertory.getRepertoryCount() >= 1 ? "售出商品" : "已售罄");
                }
            }
        });
        Goods goodsInfos = null;
        if (getActivity().getIntent().hasExtra(Constant.DATA)) {
            long id = getActivity().getIntent().getLongExtra(Constant.DATA, 0);
            goodsInfos = GoodsOrm.getInstance().queryById(id, Goods.class);
        }
        if (goodsInfos != null) {
            goodsInfoViewModel.goodsMutableLiveData.postValue(goodsInfos);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.more_action_btn) {
            long goodsId = 0;
            Goods goods = goodsInfoViewModel.goodsMutableLiveData.getValue();
            if (goods != null) {
                goodsId = goods.getId();
            }
            new GoodsActionFragment().appendAction(this, goodsId).show(getFragmentManager(), GoodsActionFragment.class.getName());
        } else if (v.getId() == R.id.left_back_btn) {
            getActivity().finish();
        } else if (v.getId() == R.id.goods_sale_btn) {
            GoodRepertory goodRepertory = goodsInfoViewModel.goodRepertoryLiveData.getValue();
            int count = goodRepertory.getRepertoryCount();
            if (count < 1) {
                SnackbarUtil.ShortSnackbar(rootView, "已售罄", SnackbarUtil.Warning).show();
                return;
            }
            if (goodsInfoViewModel.goodsMutableLiveData.getValue() != null) {
                new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("售出")
                        .setContentText("售出一件商品?库存将减1")
                        .setCancelText("手误")
                        .setConfirmText("确认售出")
                        .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                                GoodRepertory goodRepertory = goodsInfoViewModel.goodRepertoryLiveData.getValue();
                                if (goodRepertory != null) {
                                    int count = goodRepertory.getRepertoryCount();
                                    int hasCount = count - 1;
                                    if (hasCount < 0) hasCount = 0;
                                    goodRepertory.setRepertoryCount(hasCount);
                                    if (GoodsRepository.saleGoods(goodsInfoViewModel.goodsMutableLiveData.getValue(), 1)) {
                                        goodsInfoViewModel.goodRepertoryLiveData.postValue(goodRepertory);
                                    }
                                }
                                sweetAlertDialog.dismissWithAnimation();
                            }
                        })
                        .show();
            }
        }
    }


    private void gotoChangeGoods(long goodsId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Pair<View, String> imgPair = Pair.create((View) mGoodsImg, getResources().getString(R.string.tran_img));
            Pair<View, String> titlePair = Pair.create((View) mTitleTv, getResources().getString(R.string.tran_title));
            Pair<View, String> desPair = Pair.create((View) mDesTv, getResources().getString(R.string.tran_des));
            Pair<View, String> pricePair = Pair.create((View) mPriceTv, getResources().getString(R.string.tran_price));
            ActivityCompat.startActivity(getContext(), new Intent(getContext(), AddGoodsActivity.class).putExtra(Constant.ID, goodsId), ActivityOptions.makeSceneTransitionAnimation(getActivity(), imgPair, titlePair, desPair, pricePair).toBundle());
        } else {
            startActivity(new Intent(getContext(), AddGoodsActivity.class).putExtra(Constant.ID, goodsId));
        }
    }


    private void readyDeleteGoods() {
        new SweetAlertDialog(getContext(), SweetAlertDialog.WARNING_TYPE)
                .setTitleText("删除商品")
                .setContentText("确定要删除该商品吗？与它关联的所有信息都将删除！")
                .setCancelText("取消")
                .setConfirmText("确认删除")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.changeAlertType(SweetAlertDialog.SUCCESS_TYPE);
                        deleteGoods();
                        sweetAlertDialog.dismissWithAnimation();
                    }
                })
                .show();
    }

    private void deleteGoods() {
        new DoAsync<Void, Void, Boolean>(getActivity()) {
            @Override
            protected Boolean doInBackground(Context pTarget, Void... voids) throws Exception {
                return GoodsRepository.delGoods(goodsInfoViewModel.goodsMutableLiveData.getValue());
            }

            @Override
            protected void onPostExecute(Context pTarget, Boolean pResult) throws Exception {
                super.onPostExecute(pTarget, pResult);
                if (pResult != null && pResult) {
                    EventBus.getDefault().post(new DelGoods(goodsInfoViewModel.goodsMutableLiveData.getValue().getId()));
                    SnackbarUtil.ShortSnackbar(rootView, "删除成功", SnackbarUtil.Warning).show();
                    getActivity().finish();
                } else {
                    SnackbarUtil.ShortSnackbar(rootView, "删除失败", SnackbarUtil.Info).show();
                }
            }
        }.execute();
    }

    @Override
    public void delGoods(long goodId) {
        readyDeleteGoods();
    }

    @Override
    public void editGoods(long goodId) {
        gotoChangeGoods(goodId);
    }

    @Override
    public void goodsLog(long goodId) {

    }
}
