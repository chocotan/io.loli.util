package io.loli.util.string;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5工具类
 * <p>
 * 
 * <pre>
 * <code>
 * String md5Str = MD5Util.hash("String to hash");
 * </code>
 * </pre>
 * 
 * </p>
 * 
 * @author choco (loli@linux.com)
 */

public class MD5Util {
    /**
     * 对字符串md5加密
     * 
     * @param strToHash 需要加密的字符串
     * @return 加密后的md5字符串
     * @throws NoSuchAlgorithmException 当此加密不存在时抛出异常
     */
    public static String hash(String strToHash) throws NoSuchAlgorithmException {
        char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F' };
        byte[] btInput = strToHash.getBytes();
        // 获得MD5摘要算法的 MessageDigest 对象
        MessageDigest mdInst = MessageDigest.getInstance("MD5");
        // 使用指定的字节更新摘要
        mdInst.update(btInput);
        // 获得密文
        byte[] md = mdInst.digest();
        // 把密文转换成十六进制的字符串形式
        int j = md.length;
        char str[] = new char[j * 2];
        int k = 0;
        for (int i = 0; i < j; i++) {
            byte byte0 = md[i];
            str[k++] = hexDigits[byte0 >>> 4 & 0xf];
            str[k++] = hexDigits[byte0 & 0xf];
        }
        return new String(str);
    }
}
