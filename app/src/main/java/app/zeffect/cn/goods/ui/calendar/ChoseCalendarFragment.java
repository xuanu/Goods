package app.zeffect.cn.goods.ui.calendar;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatDialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.haibin.calendarview.Calendar;
import com.haibin.calendarview.CalendarView;

import app.zeffect.cn.goods.R;
import app.zeffect.cn.goods.utils.SnackbarUtil;
import app.zeffect.cn.goods.utils.TimeUtils;

/**
 * Created by Administrator on 2018/9/12.
 */

public class ChoseCalendarFragment extends AppCompatDialogFragment implements CalendarView.OnCalendarSelectListener, CalendarView.OnMonthChangeListener {
    private View rootView;
    private CalendarView calendarView;
    private TextView showTimeTv;
    private long choseTime = System.currentTimeMillis();
    private CalendarClick calendarClick;

    public ChoseCalendarFragment setCalendarClick(CalendarClick calendarClick) {
        this.calendarClick = calendarClick;
        return this;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(R.layout.schoollib_calendar_chose, container, false);
            initView();
        }
        return rootView;
    }

    private void initView() {
        calendarView = (CalendarView) rootView.findViewById(R.id.calendarView);
        showTimeTv = (TextView) rootView.findViewById(R.id.show_time_tv);
        //
        showTimeTv.setText(TimeUtils.format(choseTime, "yyyy年MM月"));
        java.util.Calendar now = java.util.Calendar.getInstance();
        int year = now.get(java.util.Calendar.YEAR);
        int month = now.get(java.util.Calendar.MONTH) + 1; // 0-based!
        int day = now.get(java.util.Calendar.DAY_OF_MONTH);
        calendarView.setRange(2000, 0, 0, year, month, day);
        calendarView.setOnCalendarSelectListener(this);
        calendarView.setOnMonthChangeListener(this);
    }

    @Override
    public void onCalendarOutOfRange(Calendar calendar) {
        SnackbarUtil.ShortSnackbar(rootView, "不可选", SnackbarUtil.Warning).show();
    }

    @Override
    public void onCalendarSelect(Calendar calendar, boolean isClick) {
        if (isClick) {
            if (calendarClick != null) calendarClick.clickCalendat(calendar);
            this.dismiss();
        }
    }

    @Override
    public void onMonthChange(int year, int month) {
        showTimeTv.setText(year + "年" + month + "月");
    }

    public interface CalendarClick {
        public void clickCalendat(Calendar calendar);
    }
}
