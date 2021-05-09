import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Base64;

import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Objects;

import javax.annotation.Nullable;

public class PictureManager {

    private static final String sdcard = Environment.getExternalStorageDirectory().getAbsolutePath();
    private static final String KAKAOTALK_PICTURE_PATH = sdcard + "/Android/data/com.kakao.talk/contents/Mg==";

    @NotNull
    public static String getKakaoTalkLastPictureFolderPath() {
        File[] list = new File(KAKAOTALK_PICTURE_PATH).listFiles();
        Arrays.sort(Objects.requireNonNull(list), new ModifiedDate());
        return list[0].toString();
    }

    public static File[] getKakaoTalkLastPictureFilePathFromFoldPath(String path) {
        File file = new File(path);
        File[] list = file.listFiles();
        Arrays.sort(Objects.requireNonNull(list), new ModifiedDate());
        return list;
    }

    @Nullable
    public static String getKakaoTalkLastPictureBase64() {
        try {
            File[] path = getKakaoTalkLastPictureFilePathFromFoldPath(getKakaoTalkLastPictureFolderPath());
            for (File file : path) {
                if (Objects.requireNonNull(file.listFiles()).length > 0) {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    BitmapFactory.decodeFile(getKakaoTalkLastPictureFilePathFromFoldPath(file.getPath())[0].toString())
                        .compress(Bitmap.CompressFormat.PNG, 100, baos);
                    
                    return Base64.encodeToString(baos.toByteArray(), 0);
                }
            }
            return null;
        } catch (Exception ignored) {
            return null;
        }
    }

    private static class ModifiedDate implements Comparator<File> {
        public int compare(@NotNull File f1, @NotNull File f2) {
            return Long.compare(f2.lastModified(), f1.lastModified());
        }
    }
}
