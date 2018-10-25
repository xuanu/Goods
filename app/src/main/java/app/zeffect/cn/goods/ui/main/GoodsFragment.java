package app.zeffect.cn.goods.ui.main;

import android.app.ActivityOptions;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.litesuits.orm.db.assit.WhereBuilder;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.eventbean.DelGoods;
import app.zeffect.cn.goods.eventbean.UpdateGoods;
import app.zeffect.cn.goods.orm.GoodsOrm;
import app.zeffect.cn.goods.ui.goods.GoodsInfo.GoodsInfoActivity;
import app.zeffect.cn.goods.ui.goods.addGoods.AddGoodsActivity;
import app.zeffect.cn.goods.ui.match.MatchBarActivity;
import app.zeffect.cn.goods.utils.Constant;
import app.zeffect.cn.goods.utils.DoAsync;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class GoodsFragment extends Fragment implements View.OnClickListener, BaseQuickAdapter.OnItemClickListener {

    private View rootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isRegistered(this)) EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void delGoods(DelGoods delGoods) {
        long goodsId = delGoods.goodId;
        if (goodsId <= 0) return;
        new DoAsync<Long, Void, Integer>(getActivity()) {
            @Override
            protected Integer doInBackground(Context pTarget, Long... longs) throws Exception {
                int pos = -1;
                long goodsId = longs[0];
                for (int i = 0; i < goodsList.size(); i++) {
                    if (goodsId == goodsList.get(i).getId()) {
                        pos = i;
                        break;
                    }
                }
                return pos;
            }

            @Override
            protected void onPostExecute(Context pTarget, Integer pResult) throws Exception {
                super.onPostExecute(pTarget, pResult);
                if (pResult != null) {
                    goodsList.remove(pResult);
                    goodsAdapter.notifyItemRemoved(pResult);
                }
            }
        }.execute(goodsId);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void updateGoods(UpdateGoods updateGoods) {
        long goodsId = updateGoods.goodId;
        if (goodsId <= 0) return;
        new DoAsync<Long, Void, Integer>(getActivity()) {
            @Override
            protected Integer doInBackground(Context pTarget, Long... longs) throws Exception {
                int pos = -1;
                long goodsId = longs[0];
                for (int i = 0; i < goodsList.size(); i++) {
                    if (goodsId == goodsList.get(i).getId()) {
                        Goods tmpGoood = GoodsOrm.getInstance().queryById(goodsId, Goods.class);
                        if (tmpGoood != null) {
                            goodsList.set(i, tmpGoood);
                        }
                        pos = i;
                        break;
                    }
                }
                return pos;
            }

            @Override
            protected void onPostExecute(Context pTarget, Integer pResult) throws Exception {
                super.onPostExecute(pTarget, pResult);
                if (pResult != null) {
                    goodsAdapter.notifyItemChanged(pResult);
                }
            }
        }.execute(goodsId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_goods, container, false);
            initView();
            initEvent();
            initData();
        }
        return rootView;
    }

    private RecyclerView goodsRecy;
    private GoodsAdapter goodsAdapter;
    private List<Goods> goodsList = new ArrayList<>();
    GoodsViewModel goodsViewModel;
    private EditText searchEdit;


    private void initData() {
        if (goodsViewModel != null) goodsViewModel.getGoods("");
    }


    private void initView() {
        searchEdit = rootView.findViewById(R.id.goods_search_edit);
        goodsRecy = rootView.findViewById(R.id.good_recy);
        goodsRecy.setLayoutManager(new LinearLayoutManager(getContext()));
        goodsAdapter = new GoodsAdapter(goodsList);
        goodsRecy.setAdapter(goodsAdapter);
        //
        rootView.findViewById(R.id.add_goods_btn).setOnClickListener(this);
        rootView.findViewById(R.id.scan_btn).setOnClickListener(this);
        rootView.findViewById(R.id.fab_scan).setOnClickListener(this);
        rootView.findViewById(R.id.add_goods_btn).setOnClickListener(this);
        goodsAdapter.setOnItemClickListener(this);
        goodsAdapter.openLoadAnimation(BaseQuickAdapter.SLIDEIN_RIGHT);
        searchEdit.addTextChangedListener(textWatcher);
    }

    private void initEvent() {
        goodsViewModel = ViewModelProviders.of(this).get(GoodsViewModel.class);
        goodsViewModel.getGoodsLiveData().observe(this, new Observer<List<Goods>>() {
            @Override
            public void onChanged(@Nullable List<Goods> goods) {
                goodsList.clear();
                goodsList.addAll(goods);
                if (goodsAdapter != null) goodsAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.add_goods_btn) {
        } else if (v.getId() == R.id.scan_btn) {
//            scan();
            startActivity(new Intent(getContext(), AddGoodsActivity.class).putExtra(Constant.DATA, "2233423"));
        } else if (v.getId() == R.id.fab_scan) {
            scan();
        } else if (v.getId() == R.id.add_goods_btn) {

        }
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Goods goods = goodsList.get(position);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Pair<View, String> imgPair = Pair.create(view.findViewById(R.id.item_goods_img), getResources().getString(R.string.tran_img));
            Pair<View, String> titlePair = Pair.create(view.findViewById(R.id.item_title_tv), getResources().getString(R.string.tran_title));
            Pair<View, String> desPair = Pair.create(view.findViewById(R.id.item_des_tv), getResources().getString(R.string.tran_des));
            Pair<View, String> pricePair = Pair.create(view.findViewById(R.id.item_price_tv), getResources().getString(R.string.tran_price));
            ActivityCompat.startActivity(getContext(), new Intent(getContext(), GoodsInfoActivity.class).putExtra(Constant.DATA, goods.getId()), ActivityOptions.makeSceneTransitionAnimation(getActivity(), imgPair, titlePair, desPair, pricePair).toBundle());
        } else {
            startActivity(new Intent(getContext(), GoodsInfoActivity.class).putExtra(Constant.DATA, goods));
        }
    }

    private void scan() {
        QrConfig qrConfig = new QrConfig.Builder()
                .setDesText("(识别二维码)")//扫描框下文字
                .setShowDes(false)//是否显示扫描框下面文字
                .setShowLight(true)//显示手电筒按钮
                .setShowTitle(true)//显示Title
                .setShowAlbum(true)//显示从相册选择按钮
                .setCornerColor(Color.WHITE)//设置扫描框颜色
                .setLineColor(Color.WHITE)//设置扫描线颜色
                .setLineSpeed(QrConfig.LINE_MEDIUM)//设置扫描线速度
                .setScanType(QrConfig.TYPE_BARCODE)//设置扫码类型（二维码，条形码，全部，自定义，默认为二维码）
                .setScanViewType(QrConfig.SCANVIEW_TYPE_BARCODE)//设置扫描框类型（二维码还是条形码，默认为二维码）
                .setPlaySound(true)//是否扫描成功后bi~的声音
                .setIsOnlyCenter(true)//是否只识别框中内容(默认为全屏识别)
                .setTitleText("扫描条形码")//设置Tilte文字
                .setTitleBackgroudColor(ContextCompat.getColor(getContext(), R.color.colorPrimary))//设置状态栏颜色
                .setTitleTextColor(Color.WHITE)//设置Title文字颜色
                .create();
        QrManager.getInstance().init(qrConfig).startScan(getActivity(), new QrManager.OnScanResultCallback() {
            @Override
            public void onScanSuccess(String result) {
                new DoAsync<String, Void, List<Goods>>(getContext()) {

                    private String barCode;

                    @Override
                    protected List<Goods> doInBackground(Context pTarget, String... strings) throws Exception {
                        String barCode = strings[0].trim();
                        this.barCode = barCode;
                        return GoodsRepository.searchGoodsBarCode(barCode);
                    }

                    @Override
                    protected void onPostExecute(final Context pTarget, List<Goods> pResult) throws Exception {
                        super.onPostExecute(pTarget, pResult);
                        if (pResult != null && !pResult.isEmpty()) {
                            goodsViewModel.getGoodsLiveData().postValue(pResult);
                        } else {//提示用户添加商品
                            new SweetAlertDialog(pTarget, SweetAlertDialog.ERROR_TYPE)
                                    .setTitleText("添加商品")
                                    .setContentText("未找到相关商品，是否添加此商品?")
                                    .setCancelText("取消")
                                    .setConfirmText("添加")
                                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                                        @Override
                                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                                            sweetAlertDialog.dismissWithAnimation();
                                            startActivity(new Intent(pTarget, AddGoodsActivity.class).putExtra(Constant.DATA, barCode));
                                        }
                                    })
                                    .show();
                        }
                    }
                }.execute(result);
            }
        });
    }


    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            if (goodsViewModel != null) goodsViewModel.getGoods(s.toString().trim());
        }
    };
}
