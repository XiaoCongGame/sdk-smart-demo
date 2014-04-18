package tv.xiaocong.sdk.demo;

import java.security.MessageDigest;

public class Md5Util {

    public static String md5code(String srcStr) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(srcStr.getBytes());
        byte[] mdbytes = md.digest();

        // convert the byte to hex format method 1
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mdbytes.length; i++) {
            sb.append(Integer.toString((mdbytes[i] & 0xff) + 0x100, 16).substring(1));
        }

        return sb.toString();
    }
}
