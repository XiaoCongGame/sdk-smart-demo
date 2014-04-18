package tv.xiaocong.sdk.demo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.xiaocong.activity.GetGameinfomation;

final class XcPayUtils {

    private XcPayUtils() {
    }

    /**
     * Execute payment.
     * 
     * @param context
     *            an {@link Context} instance
     * @param partnerId
     * @param amount
     *            the paying amount
     * @param signType
     *            md5 or RSA
     * @param orderNo
     *            the order number in your system
     * @param pkgname
     *            the package name of you App
     * @param goodsDes
     *            some descriptions about your goods
     * @param signature
     * @param notifyUrl
     *            the callback URL
     * @param remark
     */
    public static void pay(Context context, String partnerId, String amount, String signType,
            String orderNo, String pkgname, String goodsDes, String signature, String notifyUrl,
            String remark, String accessCode) {
        Bundle bundle = new Bundle();
        bundle.putString("partnerId", partnerId);
        bundle.putString("amount", amount);
        bundle.putString("signType", signType);
        bundle.putString("orderNo", orderNo);
        bundle.putString("PackageName", pkgname);
        bundle.putString("goodsDes", goodsDes);
        bundle.putString("sign", signature);

        if (notifyUrl != null && !notifyUrl.isEmpty()) {
            bundle.putString("notifyUrl", notifyUrl);
        }
        if (remark != null && !remark.isEmpty()) {
            bundle.putString("mark", remark);
        }
        if (accessCode != null && !accessCode.isEmpty()) {
            bundle.putString("accessCode", accessCode);
        }

        Intent payIntent = new Intent(context, GetGameinfomation.class);
        payIntent.putExtras(bundle);

        context.startActivity(payIntent);
    }

}
