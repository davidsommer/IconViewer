package ch.dasoft.iconviewer;

import com.intellij.ide.IconProvider;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.ImageLoader;
import java.awt.Image;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.jdesktop.swingx.util.OS;
import org.jetbrains.annotations.NotNull;

/**
 * Created by David Sommer on 19.05.17.
 *
 * @author davidsommer
 */
public class ImageIconProvider extends IconProvider {

    private static final int IMG_WIDTH  = 16;
    private static final int IMG_HEIGHT = 16;

    public Icon getIcon(@NotNull PsiElement psiElement, int flags) {
        PsiFile containingFile = psiElement.getContainingFile();
        //System.setProperty()
        if (checkImagePath(containingFile)) {
            Image image =null;
            String canonicalPath = containingFile.getVirtualFile().getCanonicalFile().getCanonicalPath();
            if (!StringUtil.isEmpty(canonicalPath)) {
                try {
                    BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(canonicalPath));
                    if (canonicalPath.endsWith(".webp")|| OS.isMacOSX()) {
                        //// Obtain a WebP ImageReader instance
                        //ImageReader reader = ImageIO.getImageReadersByMIMEType("image/webp").next();
                        //// Configure decoding parameters
                        //WebPReadParam readParam = new WebPReadParam();
                        //readParam.setBypassFiltering(true);
                        //
                        //// Configure the input on the ImageReader
                        //reader.setInput(new FileImageInputStream(new File(canonicalPath)));
                        //
                        //// Decode the image
                        //image = reader.read(0, readParam);
                        //踏破铁鞋无觅处,得来全不废功夫
                        image = ImageIO.read(new File(canonicalPath));
                    } else {
                        image = ImageLoader.loadFromStream(inputStream);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    throw new IllegalStateException("Error loading preview Icon - " + canonicalPath);
                }
            }

            if (image != null) {
                return new ImageIcon(image.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_SMOOTH));
            }
        }
        return null;
    }

    private boolean checkImagePath(PsiFile containingFile) {
        return containingFile != null && containingFile.getVirtualFile() != null && containingFile.getVirtualFile().getCanonicalFile() != null &&
            containingFile.getVirtualFile().getCanonicalFile().getCanonicalPath() != null && UIUtils.isImageFile(containingFile.getName()) &&
            !containingFile.getVirtualFile().getCanonicalFile().getCanonicalPath().contains(".jar");
    }
}