package io.loli.util.string;

/**
 * 短地址生成类 <br>
 * User: choco(loli@linux.com) <br>
 * Date: 2014年4月26日 <br>
 * Time: 下午3:48:08 <br>
 * 
 * @author choco
 */
public class ShortUrl {
    /**
     * 生成短地址
     * 
     * @param string 需要hash的字符串
     * @return 生成的短地址数组, 一共有四个
     */
    public static String[] shortText(String string) {
        // 自定义生成MD5加密字符串前的混合KEY
        String key = String.valueOf(Math.random());
        String[] chars = new String[] { // 要使用生成URL的字符
        "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n",
                "o", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
                "0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "A", "B",
                "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N",
                "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };

        String hex = MD5Util.hash(key + string);
        int hexLen = hex.length();
        int subHexLen = hexLen / 8;
        String[] ShortStr = new String[4];

        for (int i = 0; i < subHexLen; i++) {
            String outChars = "";
            int j = i + 1;
            String subHex = hex.substring(i * 8, j * 8);
            long idx = Long.valueOf("3FFFFFFF", 16) & Long.valueOf(subHex, 16);

            for (int k = 0; k < 6; k++) {
                int index = (int) (Long.valueOf("0000003D", 16) & idx);
                outChars += chars[index];
                idx = idx >> 5;
            }
            ShortStr[i] = outChars;
        }

        return ShortStr;
    }
}