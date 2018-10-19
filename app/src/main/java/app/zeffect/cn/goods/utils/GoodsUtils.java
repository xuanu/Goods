package app.zeffect.cn.goods.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoodsUtils {

    public static SpannableStringBuilder makeSearchSpan(String text, String findTxt) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#ff5000"));
        Pattern pattern = Pattern.compile(findTxt);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            builder.setSpan(colorSpan, matcher.start(), matcher.end(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        return builder;
    }

}
