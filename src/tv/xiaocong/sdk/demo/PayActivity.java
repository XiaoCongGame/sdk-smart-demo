package tv.xiaocong.sdk.demo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.xiaocong.activity.PaymentStartActivity;

/**
 * The demo for payment.
 * 
 * @author yaoyuan
 * 
 */
public class PayActivity extends Activity {

    private EditText orderNoView;
    private EditText accessTokenView;
    private String accessToken;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pay);

        orderNoView = (EditText) findViewById(R.id.order_num);
        accessTokenView = (EditText) findViewById(R.id.access_token);
        accessToken = getSharedPreferences("session", MODE_PRIVATE).getString("access_token", "");
        accessTokenView.setText(accessToken);
    }

    /** The event handler for pay button. */
    public void pay(View view) {
        String partnerId = ((EditText) findViewById(R.id.partner_id)).getText().toString();
        String amount = ((EditText) findViewById(R.id.amount)).getText().toString();
        String pkgName = ((EditText) findViewById(R.id.pkgName)).getText().toString();

        String orderNo = orderNoView.getText().toString();
        String md5key = ((EditText) findViewById(R.id.secure_key)).getText().toString();

        String pay_for = ((EditText) findViewById(R.id.pay_for)).getText().toString();
        String callbackUrl = ((EditText) findViewById(R.id.callback_url)).getText().toString();
        String remark = ((EditText) findViewById(R.id.remark)).getText().toString();

        String signature = null;
        try {
            signature = getSign(Integer.parseInt(partnerId), Integer.parseInt(amount), pkgName,
                    orderNo, md5key);
        } catch (Exception e) {
            Toast.makeText(this, "Fail to sign: " + e.getMessage(), Toast.LENGTH_LONG).show();
            return;
        }

        XcPayUtils.pay(this, partnerId, amount, "md5", orderNo, pkgName, pay_for, signature,
                callbackUrl, remark, accessToken);
    }

    /** Build the request signature. */
    private static String getSign(int partnerId, int amount, String pkgName, String orderNo,
            String md5key) throws Exception {
        return Md5Util.md5code(partnerId + "&" + pkgName + "&" + amount + "&" + orderNo + "&"
                + md5key);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PaymentStartActivity.REQUEST_CODE_START_PAY) {
            Toast.makeText(this, "Result: " + resultCode, Toast.LENGTH_LONG).show();
        }
    }
}
