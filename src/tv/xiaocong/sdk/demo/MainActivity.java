package tv.xiaocong.sdk.demo;

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
import android.widget.Toast;

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

    public void startup(View view) {
        Intent intent = new Intent(this, GameSplashActivity.class);

        intent.putExtra(GameSplashActivity.INTENT_GAME_NAME, "保卫奶茶");
        intent.putExtra(GameSplashActivity.INTENT_GAME_PKG_NAME, "com.telcotrend.snailderby");

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.game_icon);
        intent.putExtra(GameSplashActivity.INTENT_GAME_ICON, XcAndroidUtils.bitmapToBytes(icon));

        startActivityForResult(intent, REQUEST_CODE_SPLASH);
    }

    public void pay(View view) {
        Intent intent = new Intent(this, PayActivity.class);

        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE_LOGIN && resultCode == RESULT_OK && data != null) {
            String accessToken = data.getStringExtra(LoginActivity.RESPONSE_ACESS_TOKEN);

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
        }
    }
}
