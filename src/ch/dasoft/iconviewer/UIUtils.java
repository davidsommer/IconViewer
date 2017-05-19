package ch.dasoft.iconviewer;

/**
 * Created by davidsommer on 19.05.17.
 */
public class UIUtils {

    public static final String[] IMAGE_EXTENSIONS  = {"*.gif", "*.png", "*.bmp", "*.jpg", "*.tif"};
    public static final int      IMG_WIDTH         = 16;
    public static final int      IMG_HEIGHT        = 16;

    public static boolean isImageFile(String fileName) {
        int dot = fileName.lastIndexOf(".");
        if (dot==-1) {
            return false;
        }
        String fileExt = fileName.substring(dot,fileName.length());
        for (String extension : IMAGE_EXTENSIONS){
            extension = extension.substring(1);
            if (fileExt.equalsIgnoreCase(extension)){
                return true;
            }
        }
        return false;
    }

}