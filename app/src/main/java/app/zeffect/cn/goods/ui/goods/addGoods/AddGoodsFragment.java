package app.zeffect.cn.goods.ui.goods.addGoods;

import android.Manifest;
import android.app.Activity;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.haibin.calendarview.Calendar;

import org.greenrobot.eventbus.EventBus;

import java.io.File;
import java.util.ArrayList;
import java.util.IllegalFormatCodePointException;
import java.util.List;
import java.util.UUID;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.bean.GoodRepertory;
import app.zeffect.cn.goods.bean.Goods;
import app.zeffect.cn.goods.bean.GoodsIn;
import app.zeffect.cn.goods.eventbean.UpdateGoods;
import app.zeffect.cn.goods.orm.GoodsOrm;
import app.zeffect.cn.goods.ui.calendar.ChoseCalendarFragment;
import app.zeffect.cn.goods.ui.goods.choseimg.ChoseImageFragment;
import app.zeffect.cn.goods.ui.main.GoodsRepository;
import app.zeffect.cn.goods.utils.ChoseImage;
import app.zeffect.cn.goods.utils.Constant;
import app.zeffect.cn.goods.utils.DoAsync;
import app.zeffect.cn.goods.utils.GoodsUtils;
import app.zeffect.cn.goods.utils.Md5Utils;
import app.zeffect.cn.goods.utils.PermissionUtils;
import app.zeffect.cn.goods.utils.SnackbarUtil;
import app.zeffect.cn.goods.utils.TimeUtils;
import cn.bertsir.zbar.QrConfig;
import cn.bertsir.zbar.QrManager;
import cn.pedant.SweetAlert.SweetAlertDialog;

public class AddGoodsFragment extends Fragment implements View.OnClickListener, ChoseImageFragment.ImgClick {
    private View rootView;
    private String barCode = "";
    private long goodsId = -1;

    private AddViewModel addViewModel;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getActivity().getIntent().hasExtra(Constant.DATA)) {
            barCode = getActivity().getIntent().getStringExtra(Constant.DATA);
        }
        if (getActivity().getIntent().hasExtra(Constant.ID)) {
            goodsId = getActivity().getIntent().getLongExtra(Constant.ID, -1);
        }
        addViewModel = ViewModelProviders.of(this).get(AddViewModel.class);
        addViewModel.mSureAdd.postValue(false);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_add_goods, container, false);
            if (TextUtils.isEmpty(barCode) && goodsId < 0) {
                SnackbarUtil.ShortSnackbar(rootView, "参数错误", SnackbarUtil.Warning).show();
                getActivity().finish();
            }
            initView();
            initData();
        }
        return rootView;
    }

    private TextInputLayout titleLayout, desLayout;
    private TextInputEditText titleEt, desEt, costPriceEt, priceEt, addGoodsCountTv;
    private TextView goodsCountTv, goodsBarTv;
    private ImageView goodsImg;
    private View costLayout, lastCountLayout, addCountLayout, saleLayout, barLayout;
    private View addCountBtn, removeCountBtn, addBarBtn, sureToAddBtn;
    private View trueToAddBtn, wantToChangeBtn;
    private View expirationLayout;
    private TextView expirationTv;


    private void initView() {
        expirationLayout = rootView.findViewById(R.id.goods_expiration_layout);
        expirationTv = rootView.findViewById(R.id.goods_expiration_tv);
        trueToAddBtn = rootView.findViewById(R.id.true_to_add_btn);
        wantToChangeBtn = rootView.findViewById(R.id.want_to_change_btn);
        saleLayout = rootView.findViewById(R.id.sale_layout);
        barLayout = rootView.findViewById(R.id.bar_layout);
        addCountLayout = rootView.findViewById(R.id.add_count_layout);
        lastCountLayout = rootView.findViewById(R.id.last_count_layout);
        goodsImg = rootView.findViewById(R.id.goods_img);
        titleLayout = rootView.findViewById(R.id.good_title_layout);
        desLayout = rootView.findViewById(R.id.goods_des_layout);
        titleEt = rootView.findViewById(R.id.goods_title_tv);
        desEt = rootView.findViewById(R.id.goods_des_tv);
        goodsCountTv = rootView.findViewById(R.id.goods_count_tv);
        addGoodsCountTv = rootView.findViewById(R.id.add_goods_count_tv);
        costPriceEt = rootView.findViewById(R.id.goods_cost_price_tv);
        priceEt = rootView.findViewById(R.id.goods_price_tv);
        costLayout = rootView.findViewById(R.id.cost_layout);
        goodsBarTv = rootView.findViewById(R.id.goods_bar_tv);
        //
        removeCountBtn = rootView.findViewById(R.id.remove_count_btn);
        removeCountBtn.setOnClickListener(this);
        addCountBtn = rootView.findViewById(R.id.add_count_btn);
        addCountBtn.setOnClickListener(this);
        addBarBtn = rootView.findViewById(R.id.add_bar_btn);
        addBarBtn.setOnClickListener(this);
        rootView.findViewById(R.id.left_back_btn).setOnClickListener(this);
        sureToAddBtn = rootView.findViewById(R.id.sure_add_goods_btn);
        sureToAddBtn.setOnClickListener(this);
        rootView.findViewById(R.id.goods_img).setOnClickListener(this);
        trueToAddBtn.setOnClickListener(this);
        expirationTv.setOnClickListener(this);
        wantToChangeBtn.setOnClickListener(this);
        //
        addGoodsCountTv.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String data = editable.toString().trim();
                try {
                    addViewModel.mAddRepertoryCount.postValue(Integer.parseInt(data));
                } catch (NumberFormatException e) {
                }
            }
        });
        titleEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String data = editable.toString().trim();
                titleLayout.setErrorEnabled(TextUtils.isEmpty(data) ? true : false);
                addViewModel.addGoodsInfo.getValue().setGoodsName(data);
            }
        });
        desEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String data = editable.toString().trim();
                desLayout.setErrorEnabled(TextUtils.isEmpty(data) ? true : false);
                addViewModel.addGoodsInfo.getValue().setGoodsDescribe(data);
            }
        });
        priceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String data = editable.toString().trim();
                try {
                    addViewModel.addGoodsInfo.getValue().setGoodsPrice(Double.parseDouble(data));
                } catch (NumberFormatException e) {

                }

            }
        });
        costPriceEt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String data = editable.toString().trim();
                try {
                    addViewModel.addGoodsInfo.getValue().setGoodsCostPrice(Double.parseDouble(data));
                } catch (NumberFormatException e) {

                }

            }
        });

    }

    private void initData() {
        addViewModel.mExpirationDay.observe(this, new Observer<Long>() {
            @Override
            public void onChanged(@Nullable Long aLong) {
                if (aLong > 0) {
                    expirationTv.setText(TimeUtils.format(aLong, "yyyy年MM月dd日"));
                }
            }
        });
        addViewModel.addGoodsInfo.observe(this, new Observer<Goods>() {
            @Override
            public void onChanged(@Nullable Goods goods) {
                if (goods != null) {
                    titleEt.setText(goods.getGoodsName());
                    desEt.setText(goods.getGoodsDescribe());
                    goodsBarTv.setText(goods.getBarCodeStr());
                    costPriceEt.setText(goods.getGoodsCostPrice() + "");
                    priceEt.setText(goods.getGoodsPrice() + "");
                    addViewModel.findRepertory(goods.getId());
                    String goodsImgPath = goods.getGoodsImg();
                    if (!TextUtils.isEmpty(goodsImgPath)) {
                        Glide.with(getContext()).load(new File(goodsImgPath)).into(goodsImg);
                    }
                }
            }
        });
        addViewModel.mGoodRepertory.observe(this, new Observer<GoodRepertory>() {
            @Override
            public void onChanged(@Nullable GoodRepertory goodRepertory) {
                if (goodRepertory != null) {
                    goodsCountTv.setText(goodRepertory.getRepertoryCount() + "");
                }
            }
        });
        addViewModel.mAddRepertoryCount.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer != null) {
                    costLayout.setVisibility(integer > 0 ? View.VISIBLE : View.GONE);
                    addGoodsCountTv.setText("" + integer);
                    expirationLayout.setVisibility(integer > 0 ? View.VISIBLE : View.GONE);
                }
            }
        });
        addViewModel.mAddRepertoryCount.postValue(0);
        addViewModel.findGoods(goodsId, barCode);
        addViewModel.mSureAdd.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                readToAdd(aBoolean);
            }
        });
        addViewModel.mExpirationDay.postValue(System.currentTimeMillis() + 7L * 24 * 60 * 60 * 1000);
    }

    @Override
    public void onClick(View view) {
        clearFouce();
        if (view.getId() == R.id.add_bar_btn) {
            scan();
        } else if (view.getId() == R.id.goods_expiration_tv) {
            new ChoseCalendarFragment().setCalendarClick(new ChoseCalendarFragment.CalendarClick() {
                @Override
                public void clickCalendat(Calendar calendar) {
                    addViewModel.mExpirationDay.postValue(calendar.getTimeInMillis());
                }
            }).show(getFragmentManager(), ChoseCalendarFragment.class.getName());
        } else if (view.getId() == R.id.add_count_btn) {
            int count = addViewModel.mAddRepertoryCount.getValue();
            addViewModel.mAddRepertoryCount.postValue(count + 1);
        } else if (view.getId() == R.id.remove_count_btn) {
            int count = addViewModel.mAddRepertoryCount.getValue();
            if (count >= 1) {
                addViewModel.mAddRepertoryCount.postValue(count - 1);
            }
        } else if (view.getId() == R.id.left_back_btn) {
            getActivity().finish();
        } else if (view.getId() == R.id.sure_add_goods_btn) {
            boolean canAdd = addViewModel.mSureAdd.getValue();
            if (!canAdd) {
                canAdd = checkAdd();
                addViewModel.mSureAdd.postValue(canAdd);
                return;
            }
            //添加
        } else if (view.getId() == R.id.goods_img) {
//            ChoseImage.choseImageFromGallery(getContext(), 0x10);
            new ChoseImageFragment().appendClick(this).show(getFragmentManager(), ChoseImageFragment.class.getName());
        } else if (view.getId() == R.id.want_to_change_btn) {
            addViewModel.mSureAdd.postValue(false);
        } else if (view.getId() == R.id.true_to_add_btn) {
            new DoAsync<Void, Void, Void>(getActivity()) {

                @Override
                protected void onPreExecute(Context pTarget) throws Exception {
                    super.onPreExecute(pTarget);
                    SnackbarUtil.ShortSnackbar(rootView, "正在添加……", SnackbarUtil.Info).show();
                }

                @Override
                protected Void doInBackground(Context pTarget, Void... voids) throws Exception {
                    Goods addGoods = addViewModel.addGoodsInfo.getValue();
                    //添加首拼
                    addGoods.setTitleSpell(GoodsUtils.getFirstSpell(addGoods.getGoodsName()));
                    long goodId = addGoods.getId();
                    String uuid = addGoods.getUuid();
                    if (TextUtils.isEmpty(uuid)) {
                        uuid = UUID.randomUUID().toString();
                        addGoods.setUuid(uuid);
                    }
                    //商品表
                    addGoods.setBarCodeStr(addGoods.getBarCodeStr2());
                    GoodsOrm.getInstance().save(addGoods);
                    //库存表
                    if (goodId < 1) {
                        List<Goods> searchGoods = GoodsRepository.findWithUuid(uuid);
                        if (searchGoods != null && !searchGoods.isEmpty()) {
                            goodId = searchGoods.get(0).getId();
                        }
                    }
                    GoodRepertory repertory = addViewModel.mGoodRepertory.getValue();
                    repertory.setGoodsId(goodId);
                    int addCount = addViewModel.mAddRepertoryCount.getValue();
                    int count = repertory.getRepertoryCount();
                    int total = repertory.getRepertoryTotal();
                    repertory.setRepertoryCount(count + addCount);
                    repertory.setRepertoryTotal(total + addCount);
                    GoodsOrm.getInstance().save(repertory);
                    //进货表
                    GoodsIn goodsIn = new GoodsIn();
                    goodsIn.setBarCode(addGoods.getBarCode());
                    goodsIn.setCreatTime(System.currentTimeMillis());
                    goodsIn.setGoodsCostPrice(addGoods.getGoodsCostPrice());
                    goodsIn.setGoodsDescribe(addGoods.getGoodsDescribe());
                    goodsIn.setGoodsImg(addGoods.getGoodsImg());
                    goodsIn.setGoodsName(addGoods.getGoodsName());
                    goodsIn.setGoodsPrice(addGoods.getGoodsPrice());
                    goodsIn.setTitleSpell(addGoods.getTitleSpell());
                    goodsIn.setGoodsId(goodId);
                    goodsIn.setGoodsInCount(addCount);
                    //生产日期，保质期，还没有填写
                    GoodsOrm.getInstance().save(goodsIn);
                    //
                    EventBus.getDefault().post(new UpdateGoods(goodId));
                    return null;
                }

                @Override
                protected void onPostExecute(Context pTarget, Void pResult) throws Exception {
                    super.onPostExecute(pTarget, pResult);
                    SnackbarUtil.ShortSnackbar(rootView, "添加成功", SnackbarUtil.Confirm).show();
                    if (pTarget instanceof Activity) {
                        getActivity().finish();
                    }
                }
            }.execute();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_GALLERY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                String imgPath = ChoseImage.getGalleryPath(getContext(), data);
                addViewModel.addGoodsInfo.getValue().setGoodsImg(imgPath);
                Glide.with(getContext()).load(new File(imgPath)).into(goodsImg);
            }
        } else if (requestCode == REQ_TAKE_PHOTO_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                addViewModel.addGoodsInfo.getValue().setGoodsImg(takePhotoFile.getAbsolutePath());
                Glide.with(getContext()).load(takePhotoFile).into(goodsImg);
            }
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
                ArrayList<String> barCodes = addViewModel.addGoodsInfo.getValue().getBarCode();
                boolean hasBar = false;
                for (int i = 0; i < barCodes.size(); i++) {
                    String tmpBar = barCodes.get(i);
                    if (tmpBar.equals(result)) {
                        hasBar = true;
                        break;
                    }
                }
                if (!hasBar) {
                    barCodes.add(result);
                }
                goodsBarTv.setText(addViewModel.addGoodsInfo.getValue().getBarCodeStr());
            }
        });
    }


    /**
     * 检查添加
     */
    public boolean checkAdd() {
        boolean checkSuccess = true;
        //
        String goodsTitleStr = titleEt.getText().toString().trim();
        if (TextUtils.isEmpty(goodsTitleStr)) {
            checkSuccess = false;
        }
        titleLayout.setError("输入商品名");
        titleLayout.setErrorEnabled(TextUtils.isEmpty(goodsTitleStr) ? true : false);
        //
        String goodsDesStr = desEt.getText().toString().trim();
        if (TextUtils.isEmpty(goodsDesStr)) {
            checkSuccess = false;
        }
        desLayout.setError("输入商品描述");
        desLayout.setErrorEnabled(TextUtils.isEmpty(goodsDesStr) ? true : false);
        //
        return checkSuccess;
    }

    /**
     * 准备添加
     *
     * @param ready true准备添加，false取消准备
     */
    private void readToAdd(boolean ready) {
        Goods defaultGoods = addViewModel.defaultGoodsInfo.getValue();
        Goods addGoods = addViewModel.addGoodsInfo.getValue();
        if (defaultGoods != null && addGoods != null) {
            goodsImg.setVisibility(defaultGoods.getGoodsImg().equalsIgnoreCase(addGoods.getGoodsImg()) && ready ? View.INVISIBLE : View.VISIBLE);
            titleLayout.setVisibility(defaultGoods.getGoodsName().equalsIgnoreCase(addGoods.getGoodsName()) && ready ? View.INVISIBLE : View.VISIBLE);
            desLayout.setVisibility(defaultGoods.getGoodsDescribe().equalsIgnoreCase(addGoods.getGoodsDescribe()) && ready ? View.INVISIBLE : View.VISIBLE);
            costLayout.setVisibility(defaultGoods.getGoodsCostPrice() == addGoods.getGoodsCostPrice() && ready ? View.INVISIBLE : View.VISIBLE);
            saleLayout.setVisibility(defaultGoods.getGoodsPrice() == addGoods.getGoodsPrice() && ready ? View.INVISIBLE : View.VISIBLE);
            barLayout.setVisibility(defaultGoods.getBarCodeStr().equalsIgnoreCase(addGoods.getBarCodeStr()) && ready ? View.INVISIBLE : View.VISIBLE);
            addCountLayout.setVisibility(addViewModel.mAddRepertoryCount.getValue() > 0 && ready ? View.VISIBLE : View.INVISIBLE);
            lastCountLayout.setVisibility(ready ? View.INVISIBLE : View.VISIBLE);
            sureToAddBtn.setVisibility(ready ? View.GONE : View.VISIBLE);
            wantToChangeBtn.setVisibility(ready ? View.VISIBLE : View.GONE);
            trueToAddBtn.setVisibility(ready ? View.VISIBLE : View.GONE);
            //全部不可操作
            goodsImg.setEnabled(ready ? false : true);
            titleEt.setEnabled(ready ? false : true);
            desEt.setEnabled(ready ? false : true);
            priceEt.setEnabled(ready ? false : true);
            costPriceEt.setEnabled(ready ? false : true);
            addBarBtn.setEnabled(ready ? false : true);
            addBarBtn.setVisibility(ready ? View.INVISIBLE : View.VISIBLE);
            addCountBtn.setEnabled(ready ? false : true);
            addCountBtn.setVisibility(ready ? View.INVISIBLE : View.VISIBLE);
            removeCountBtn.setEnabled(ready ? false : true);
            removeCountBtn.setVisibility(ready ? View.INVISIBLE : View.VISIBLE);
            //
        }
        clearFouce();
    }


    private void clearFouce() {
        titleEt.clearFocus();
        desEt.clearFocus();
        priceEt.clearFocus();
        costPriceEt.clearFocus();
    }

    public static final int REQ_GALLERY_CODE = 0X10, REQ_TAKE_PHOTO_CODE = 0X11;

    private File takePhotoFile = null;

    public static final int REQ_SD_PER_CODE = 0X12;
    public static final int REQ_CAMERA_CODE = 0x13;

    @Override
    public void clickType(int type) {
        if (type == ChoseImageFragment.TYPE_GALLERY) {
            if (PermissionUtils.checkPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE, REQ_SD_PER_CODE)) {
                ChoseImage.choseImageFromGallery(getContext(), REQ_GALLERY_CODE);
            }
        } else if (type == ChoseImageFragment.TYPE_TAKE_PHOTO) {
            if (PermissionUtils.checkPermission(this, Manifest.permission.CAMERA, REQ_CAMERA_CODE)) {
                takePhoto();
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (PermissionUtils.verifyPermissions(grantResults)) {
            if (requestCode == REQ_CAMERA_CODE) {
                takePhoto();
            } else if (requestCode == REQ_SD_PER_CODE) {
                ChoseImage.choseImageFromGallery(getContext(), REQ_GALLERY_CODE);
            }
        } else {
            SnackbarUtil.ShortSnackbar(rootView, "权限被拒绝", SnackbarUtil.Warning).show();
        }
    }


    private void takePhoto() {
        String goodsName = UUID.randomUUID().toString() + "_" + Md5Utils.md5(barCode) + goodsId + ".png";
        takePhotoFile = new File(getContext().getExternalFilesDir("goodimg"), goodsName);
        ChoseImage.choseFromCameraCapture(getContext(), takePhotoFile, REQ_TAKE_PHOTO_CODE);
    }
}
