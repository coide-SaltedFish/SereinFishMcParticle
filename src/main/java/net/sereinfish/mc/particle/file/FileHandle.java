package net.sereinfish.mc.particle.file;

import java.io.*;

public class FileHandle {
    public final static String encoding = "UTF-8";//文件编码

    /**
     * 写入文件
     * @param file
     * @param str
     * @return
     */
    public static void write(File file, String str) throws IOException {
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }
        OutputStreamWriter outputStreamWriter=new OutputStreamWriter(new FileOutputStream(file),encoding);
        outputStreamWriter.write(str);
        outputStreamWriter.close();
    }

    /**
     * 文件读取
     * @param file
     * @return
     */
    public static String read(File file) throws IOException {
        if(!file.exists()) {
            file.getParentFile().mkdirs();
            file.createNewFile();
        }

        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);// 考虑到编码格式
        BufferedReader bufferedReader = new BufferedReader(read);
        String lineTxt = bufferedReader.readLine();
        while (lineTxt != null) {
            stringBuilder.append(lineTxt);

            lineTxt = bufferedReader.readLine();
            if(lineTxt != null){
                stringBuilder.append("\n");
            }
        }
        read.close();
        return stringBuilder.toString();
    }
}
