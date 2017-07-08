package com.sharingif.cube.components.sequence;

import com.sharingif.cube.core.util.DateUtils;

/**
 * 日期序列，返回指定格式的当前日期
 * 2017/6/3 下午9:41
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class DateSequenceGenerator extends AbstractSequenceGenerator<String> {

    private String dateFormat;

    public DateSequenceGenerator(String dateFormat) {
        this.dateFormat = dateFormat;
    }

    @Override
    public String generateSequence() {
        return DateUtils.getCurrentDate(dateFormat);
    }

}
