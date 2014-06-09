package tv.xiaocong.sdk.demo;

import android.app.Activity;

import com.xiaocong.sdk.pay.PaymentHelper;

final class XcPayUtils {

    private XcPayUtils() {
    }

    /**
     * Execute payment.
     * 
     * @param caller
     *            (Required) the activity that starts {@link PaymentStartActivity}.
     * @param partnerId
     *            (Required)
     * @param amount
     *            (Required) the money to pay. The unit is RMB cent. (1 Xiaocong coin == 1 RMB
     *            cent.)
     * @param signType
     *            (Required) md5 or RSA
     * @param orderNo
     *            (Required) the order number in your system. Should be unique for all your request.
     *            Prefixed by your partnerId. Format: ^{12,30}$.
     * @param pkgname
     *            (Required) the package name of you application
     * @param goodsDes
     *            (Required) some descriptions about your goods
     * @param signature
     *            (Required) the request signature. For the format of the signature, refer to
     *            {@link MainActivity#getSign(int, int, String, String, String)}.
     * @param notifyUrl
     *            (Required) the callback URL in your server.
     * @param remark
     *            (Optional) some remark for this order
     * @param accessToken
     *            (Optional) If you don't want users to change their account, provide a accessToken
     *            yourself; If you pass null, then we'll pop up login dialog to get the accessToken
     *            if necessary.
     */
    public static void pay(Activity caller, int partnerId, int amount, String signType,
            String orderNo, String pkgname, String goodsDes, String signature, String notifyUrl,
            String remark, String accessToken) {
        PaymentHelper.startMe(caller, partnerId, amount, signType, orderNo, pkgname, goodsDes,
                signature, notifyUrl, remark, Keys.CLIENT_ID, Keys.CLIENT_SECRET, accessToken);
    }

}
