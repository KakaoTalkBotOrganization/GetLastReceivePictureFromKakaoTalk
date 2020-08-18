const PICTURE_PATH = "/sdcard/Android/data/com.kakao.talk/contents/Mg==";

const getLastPictureAsBase64 = () => {
    try {
        const getLatestModified = (array) => array.reduce((a, b) => (a.lastModified() < b.lastModified()) ? b : a);
        const level1 = getLatestModified(new java.io.File(PICTURE_PATH).listFiles());
        const level2 = getLatestModified(level1.listFiles().filter((f) => f.list().length !== 0));
        const file = getLatestModified(level2.listFiles());
        const bm = android.graphics.BitmapFactory.decodeFile(file.toString());
        const baos = new java.io.ByteArrayOutputStream();
        bm.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, baos);
        const bImage = baos.toByteArray();
        const base64 = android.util.Base64.encodeToString(bImage, 0);
        return base64;
    }
    catch (e) {
        return null;
    }
};

const getLastPicture = getLastPictureAsBase64;
