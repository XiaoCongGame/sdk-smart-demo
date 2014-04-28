package tv.xiaocong.sdk.demo;

import android.app.Activity;

import com.xiaocong.activity.PaymentStartActivity;

final class XcPayUtils {

    private XcPayUtils() {
    }

    /**
     * Execute payment.
     * 
     * @param caller
     *            caller that starts {@link PaymentStartActivity}.
     * @param partnerId
     * @param amount
     *            the paying amount
     * @param signType
     *            md5 or RSA
     * @param orderNo
     *            the order number in your system
     * @param pkgname
     *            the package name of you Application
     * @param goodsDes
     *            some descriptions about your goods
     * @param signature
     * @param notifyUrl
     *            the callback URL
     * @param remark
     */
    public static void pay(Activity caller, int partnerId, int amount, String signType,
            String orderNo, String pkgname, String goodsDes, String signature, String notifyUrl,
            String remark, String accessToken) {
        PaymentStartActivity.startMe(caller, partnerId, amount, signType, orderNo, pkgname,
                goodsDes, signature, notifyUrl, remark, accessToken);
    }

}
