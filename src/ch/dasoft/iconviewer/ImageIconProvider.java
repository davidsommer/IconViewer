package ch.dasoft.iconviewer;

import com.intellij.ide.IconProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.util.ImageLoader;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


/**
 * Created by David Sommer on 19.05.17.
 * @author davidsommer
 */
public class ImageIconProvider  extends IconProvider {

    private static final int IMG_WIDTH  = 16;
    private static final int IMG_HEIGHT = 16;

    public Icon getIcon(@NotNull PsiElement psiElement, int flags) {
        PsiFile containingFile = psiElement.getContainingFile();
        if (checkImagePath(containingFile)) {
            Image image;
            try {
                image = ImageLoader.loadFromStream(new BufferedInputStream(new FileInputStream(containingFile.getVirtualFile().getCanonicalFile().getCanonicalPath())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new IllegalStateException("Error loading preview Icon - " + containingFile.getVirtualFile().getCanonicalFile().getCanonicalPath());
            }

            if (image != null) {
                return new ImageIcon(image.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, Image.SCALE_SMOOTH));
            }
        }
        return null;
    }

    private boolean checkImagePath(PsiFile containingFile) {
        if (containingFile == null || containingFile.getVirtualFile() == null || containingFile.getVirtualFile().getCanonicalFile() == null || containingFile.getVirtualFile().getCanonicalFile().getCanonicalPath() == null) {
            return false;
        }
        return UIUtils.isImageFile(containingFile.getName()) && ! containingFile.getVirtualFile().getCanonicalFile().getCanonicalPath().contains(".jar");
    }
}