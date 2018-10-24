package app.zeffect.cn.goods.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtils {

    public static String format(long time, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        Date date = new Date(time);
        return dateFormat.format(date);
    }
}
