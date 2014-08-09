package io.loli.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.security.MessageDigest;

/**
 * 文件工具类, 支持对文件的各种操作<br>
 * 目前只有对文件求md5值
 * 
 * @author choco (loli@linux.com)
 */
public class FileUtils {

    /**
     * 取文件的md5值
     * 
     * @param path 需要取md5的文件路径，必须是绝对路径
     * @return 该文件的md5值, 小写
     */
    public static String md5Hash(String path) {
        File file = new File(path);
        return md5Hash(file);
    }

    /**
     * 取文件的md5值
     * 
     * @param file 目标文件
     * @return 该文件的md5值, 小写
     */
    public static String md5Hash(File file) {
        String value = "";

        try (FileInputStream in = new FileInputStream(file);) {
            MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0,
                    file.length());
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(byteBuffer);
            BigInteger bi = new BigInteger(1, md5.digest());
            value = bi.toString(16);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return value;

    }

    /**
     * 传入的参数为nio的Path
     * 
     * @param path 目标文件
     * @return 该文件的md5值, 小写
     */
    public static String md5Hash(Path path) {
        return md5Hash(path.toFile());
    }

}
