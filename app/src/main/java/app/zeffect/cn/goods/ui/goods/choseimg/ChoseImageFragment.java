package app.zeffect.cn.goods.ui.goods.choseimg;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.ui.base.BaseBottomSheetFragment;

public class ChoseImageFragment extends BaseBottomSheetFragment implements View.OnClickListener {

    public static final int TYPE_TAKE_PHOTO = 0x00;
    public static final int TYPE_GALLERY = 0x01;

    private View rootView;

    private ImgClick imgClick;

    public ChoseImageFragment appendClick(ImgClick tmpClick) {
        this.imgClick = tmpClick;
        return this;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.im_pop_talk_chose_photo, container, false);
            initView();
        }
        return rootView;
    }


    private void initView() {
        rootView.findViewById(R.id.ptcp_to_takephoto_btn).setOnClickListener(this);
        rootView.findViewById(R.id.ptcp_to_gally_btn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ptcp_to_gally_btn) {
            if (imgClick != null) imgClick.clickType(TYPE_GALLERY);
            this.dismiss();
        } else if (v.getId() == R.id.ptcp_to_takephoto_btn) {
            if (imgClick != null) imgClick.clickType(TYPE_TAKE_PHOTO);
            this.dismiss();
        }
    }

    public interface ImgClick {
        public void clickType(int type);
    }

}
