package tv.xiaocong.sdk.demo;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import tv.xiaocong.sdk.XcAndroidUtils;
import tv.xiaocong.sdk.ad.GameSplashActivity;
import tv.xiaocong.sdk.security.LoginActivity;
import tv.xiaocong.sdk.security.RegisterActivity;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.xiaocong.sdk.PaymentResults;
import com.xiaocong.sdk.pay.PaymentActivity;

/**
 * The main activity for the demo.
 * 
 * @author yaoyuan
 * 
 */
public class MainActivity extends Activity {

    private static final int REQUEST_CODE_LOGIN = 1;

    private static final int REQUEST_CODE_REGISTER = 2;

    private static final int REQUEST_CODE_SPLASH = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.main);
    }

    public void register(View view) {
        Intent registerIntent = new Intent(this, RegisterActivity.class);

        startActivityForResult(registerIntent, REQUEST_CODE_REGISTER);
    }

    public void login(View view) {
        Intent loginIntent = new Intent(this, LoginActivity.class);
        // 需要传入client_id和client_secret，这两个值是Oauth标准的。
        loginIntent.putExtra("client_id", Keys.CLIENT_ID);
        loginIntent.putExtra("client_secret", Keys.CLIENT_SECRET);
        loginIntent.putExtra("usingCache", true); // 记住登录

        startActivityForResult(loginIntent, REQUEST_CODE_LOGIN);
    }

    public void changeUser(View view) {
        LoginActivity.startMe(this, REQUEST_CODE_LOGIN, Keys.CLIENT_ID, Keys.CLIENT_SECRET, true);
    }

    public void startup(View view) {
        Intent intent = new Intent(this, GameSplashActivity.class);

        intent.putExtra(GameSplashActivity.INTENT_GAME_NAME, "保卫奶茶");
        intent.putExtra(GameSplashActivity.INTENT_GAME_PKG_NAME, "com.telcotrend.snailderby");

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.game_icon);
        intent.putExtra(GameSplashActivity.INTENT_GAME_ICON, XcAndroidUtils.bitmapToBytes(icon));

        startActivityForResult(intent, REQUEST_CODE_SPLASH);
    }

    public void pay(View view) {
        final int amount = 10; // 10 cents/ 10 xiaocong coins

        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
        final String orderNo = Keys.PARTNER_ID + df.format(new Date());

        final String pkgName = "tv.xiaocong.sdk.demo";

        final String goodsDes = "A sword";

        final String sign = getSign(Keys.PARTNER_ID, amount, pkgName, orderNo, Keys.PARTNER_MD5KEY);

        final String callbackUrl = "www.xiaocong.tv";

        final String remark = "This is a test payment";

        XcPayUtils.pay(this, Keys.PARTNER_ID, amount, "md5", orderNo, pkgName, goodsDes, sign,
                callbackUrl, remark, null);
    }

    public void pay2(View view) {
        final int amount = 100;

        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmssSSS", Locale.getDefault());
        final String orderNo = Keys.PARTNER_ID + df.format(new Date());

        final String pkgName = "tv.xiaocong.sdk.demo";

        final String goodsDes = "10 swords";

        final String sign = getSign(Keys.PARTNER_ID, amount, pkgName, orderNo, Keys.PARTNER_MD5KEY);

        final String callbackUrl = "www.xiaocong.tv";

        final String remark = "This is a test payment";

        XcPayUtils.pay(this, Keys.PARTNER_ID, amount, "md5", orderNo, pkgName, goodsDes, sign,
                callbackUrl, remark, null);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK && data != null) {
            String accessToken = data.getStringExtra(LoginActivity.RESPONSE_ACCESS_TOKEN);

            Toast.makeText(this, "access_token:" + accessToken, Toast.LENGTH_LONG).show();

            Editor editor = getSharedPreferences("session", MODE_PRIVATE).edit();
            editor.putString("access_token", accessToken);
            editor.commit();

        } else if (requestCode == REQUEST_CODE_REGISTER) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, data.getStringExtra(RegisterActivity.USERNAME),
                        Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Failed to register!", Toast.LENGTH_LONG).show();
            }
        } else if (requestCode == REQUEST_CODE_SPLASH) {
            Toast.makeText(this, "Game started", Toast.LENGTH_LONG).show();
        } else if (requestCode == PaymentActivity.REQUEST_CODE_START_PAY) {
            if (resultCode == PaymentResults.PAYRESULT_OK) {
                Toast.makeText(this, "I got a sword!!!", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Result: " + resultCode, Toast.LENGTH_LONG).show();
            }
        }
    }

    /** Build the request signature. */
    static String getSign(int partnerId, int amount, String pkgName, String orderNo, String md5key)
            throws RuntimeException {
        try {
            return Md5Util.md5code(partnerId + "&" + pkgName + "&" + amount + "&" + orderNo + "&"
                    + md5key);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
