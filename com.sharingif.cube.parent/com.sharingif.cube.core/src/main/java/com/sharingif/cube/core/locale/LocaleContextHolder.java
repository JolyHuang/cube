package com.sharingif.cube.core.locale;

import java.util.Locale;

/**
 * Locale 绑定到当前线程
 * 2017/6/1 下午6:55
 *
 * @author Joly
 * @version v1.0
 * @since v1.0
 */
public class LocaleContextHolder {

    private static final ThreadLocal<Locale> CONTEXT_HOLDER = new ThreadLocal<Locale>();

    public static void clearContext() {
        CONTEXT_HOLDER.remove();
    }

    @SuppressWarnings("unchecked")
    public static Locale getContext() {
        return CONTEXT_HOLDER.get();
    }

    public static void setContext(Locale locale) {
        CONTEXT_HOLDER.set(locale);
    }

}
