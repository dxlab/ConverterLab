package ua.com.dxlab.converterlab.util;

import android.annotation.TargetApi;
import android.os.Build;
import android.telephony.PhoneNumberUtils;

/**
 * Created by Dima on 17.09.2015.
 */
public class PhoneUtils {

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static String formatPhoneNumber(String phone) {
        String formattedPhone = phone;
        if (phone != null) {
            formattedPhone = PhoneNumberUtils.formatNumber(phone, "UA");
        }
        return formattedPhone;
    }
}
