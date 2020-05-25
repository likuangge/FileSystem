package ring.central.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

/**
 * @Description: 文件通用工具
 * @Author: zuo.li zuo.li@luckincoffee.com
 * @Date: 2020/5/25 23:43
 */
@Slf4j
public class FileUtil {

    /**
     * 文件存放目录
     */
    private static final String FILE_DIR = "C:\\Project\\FileSystem\\file";

    /**
     * 文件分隔符
     */
    private static final String FILE_SEPARATOR = "\\";

    /**
     * 写文件
     *
     * @param fileName 文件名称
     * @param fileContent 文件内容
     * @throws IOException IO异常
     */
    public static void writeFile(String fileName, String fileContent) throws IOException {
        String filePath = FILE_DIR + FILE_SEPARATOR + fileName;
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(filePath));
        bufferedWriter.write(fileContent);
        bufferedWriter.close();
    }

    /**
     * 读取文件
     *
     * @param fileName 文件路径
     * @return 文件内容
     */
    public static String readFile(String fileName) throws IOException {
        String filePath = FILE_DIR + FILE_SEPARATOR + fileName;
        File file = new File(filePath);
        if (file.exists() && file.isFile()) {
            InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();
            String line = null;
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line);
            }
            bufferedReader.close();
            inputStreamReader.close();
            return stringBuilder.toString();
        }
        return null;
    }

    /**
     * 获取文件
     *
     * @param fileName 文件名称
     * @return 文件
     */
    public static File getFile(String fileName) {
        return new File(FILE_DIR + FILE_SEPARATOR + fileName);
    }
}
