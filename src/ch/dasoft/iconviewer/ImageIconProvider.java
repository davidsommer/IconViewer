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
 * Created by davidsommer on 19.05.17.
 * @author davidsommer
 */
public class ImageIconProvider  extends IconProvider {

    public Icon getIcon(@NotNull PsiElement psiElement, int flags) {
        PsiFile containingFile = psiElement.getContainingFile();
        if (containingFile != null && UIUtils.isImageFile(containingFile.getName())) {
            ImageIcon imageIcon = new ImageIcon(containingFile.getVirtualFile().getCanonicalFile().getCanonicalPath());
            Image image = null;
            try {
                image = ImageLoader.loadFromStream(new BufferedInputStream(new FileInputStream(containingFile.getVirtualFile().getCanonicalFile().getCanonicalPath())));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                throw new IllegalStateException("Error loading preview Icon - " + containingFile.getVirtualFile().getCanonicalFile().getCanonicalPath());
            }

            if (image != null) {
                return new ImageIcon(image.getScaledInstance(16, 16, Image.SCALE_SMOOTH));
            }
        }
        return null;
    }
}