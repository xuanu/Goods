package app.zeffect.cn.goods.utils;

import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GoodsUtils {

    public static SpannableStringBuilder makeSearchSpan(String text, String findTxt) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);
        ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#ff5000"));
        Pattern pattern = Pattern.compile(findTxt);
        Matcher matcher = pattern.matcher(text);
        while (matcher.find()) {
            builder.setSpan(CharacterStyle.wrap(colorSpan), matcher.start(), matcher.end(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);
        }
        return builder;
    }

    public static Object cloneObject(Object obj) throws Exception {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(obj);
        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        return in.readObject();
    }

    public static ArrayList<String> getFirstSpell(String word) throws Exception {
        List<StringBuilder> buffers = new LinkedList<>();
        buffers.add(new StringBuilder());
        char[] arr = word.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                Set<Character> tmpStr = new HashSet(temp.length);
                if (temp != null) {
                    for (int j = 0; j < temp.length; j++) {
                        tmpStr.add(temp[j].charAt(0));
                    }
                }
                if (tmpStr.size() == 1) {
                    addBuffer(buffers, temp[0].charAt(0));// 都一样，加哪个都行
                } else if (tmpStr.size() >= 2) {
                    List<StringBuilder> laStringBuilders = (List<StringBuilder>) cloneObject(buffers);
                    buffers.clear();
                    for (Character tmpChar : tmpStr) {// 给以前每一个都加上值
                        List<StringBuilder> tmpBuilder = (List<StringBuilder>) cloneObject(laStringBuilders);
                        addBuffer(tmpBuilder, tmpChar);
                        buffers.addAll(tmpBuilder);
                        tmpBuilder.clear();
                    }
                }
            } else {
                addBuffer(buffers, arr[i]);
            }
        }
        ArrayList<String> spells = new ArrayList<>(buffers.size());
        for (int i = 0; i < buffers.size(); i++) {
            spells.add(buffers.get(i).toString());
        }
        return spells;
    }


    private static void addBuffer(List<StringBuilder> buffers, char tmpChar) {
        for (int i = 0; i < buffers.size(); i++) {
            buffers.get(i).append(tmpChar);
        }
    }
}
