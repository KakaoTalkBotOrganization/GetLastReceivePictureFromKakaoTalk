const PICTURE_PATH = "/sdcard/Android/data/com.kakao.talk/contents/Mg==";

getLastPictureFolderPath = _  => {
    var file = new java.io.File(PICTURE_PATH);
    var list = file.listFiles().sort(function(a, b){
        return b.lastModified() -a.lastModified();
    });
    return list[0].toString();
}

getLastPictureFilePathFromFoldPath = (path) => {
    var file = new java.io.File(path);
    var list = file.listFiles().sort(function(a, b){
        return b.lastModified() - a.lastModified();
    });
    return list;
}

getLastPicture = _ => {
    try{
        var path = getLastPictureFilePathFromFoldPath(getLastPictureFolderPath());
        for(var i=0;i<path.length;i++){
            var file = new java.io.File(path[i].toString());
            if(file.listFiles().length == 0) continue;
            else {
                var picture = getLastPictureFilePathFromFoldPath(file.getPath())[0].toString();
                var bm = android.graphics.BitmapFactory.decodeFile(picture);
                var baos = new java.io.ByteArrayOutputStream();
                bm.compress(android.graphics.Bitmap.CompressFormat.PNG, 100, baos);
                var bImage = baos.toByteArray();
                var base64 = android.util.Base64.encodeToString(bImage, 0);
                return base64;
            }
        }
        return null;
    }
    catch(e){
        return null;
    }
}