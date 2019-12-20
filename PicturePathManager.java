package com.sungbin.reply.bot.utils;


import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class PicturePathManager{
    private static String lastPath = null;
    private static String PICTURE_PATH = Utils.sdcard + "/Android/data/com.kakao.talk/contents/Mg==";

    private static class ModifiedDate implements Comparator<File>{
        public int compare(File f1, File f2) {
            return Long.compare(f2.lastModified(), f1.lastModified());
        }
    }

    public static String getLastPictureFolderPath(){
        File file = new File(PICTURE_PATH);
        File[] list = file.listFiles();
        Arrays.sort(list, new ModifiedDate());
        return list[0].toString();
    }

    public static File[] getLastPictureFilePathFromFoldPath(String path){
        File file = new File(path);
        File[] list = file.listFiles();
        Arrays.sort(list, new ModifiedDate());
        return list;
    }

    public static String getLastPicture(){
        try{
            File[] path = getLastPictureFilePathFromFoldPath(getLastPictureFolderPath());
            for(int i=0;i<path.length;i++){
                File file = new File(path[i].toString());
                if(file.listFiles().length == 0) continue;
                else {
                    String picture = getLastPictureFilePathFromFoldPath(file.getPath())[0].toString();
                    Bitmap bm = BitmapFactory.decodeFile(picture);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
                    byte[] bImage = baos.toByteArray();
                    String base64 = Base64.encodeToString(bImage, 0);
                    return base64;
                }
            }
            return null;
        }
        catch(Exception e){
            return null;
        }
    }
}