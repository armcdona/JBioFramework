import java.io.File;
import java.io.FilenameFilter;


/**
 *  ImageFilter.java is a 1.4 example used by FileChooserDemo2.java.
 */
public class ImageFilter implements FilenameFilter {

    /**
     * Determined if the file should be accepted depending on its extension
     * @param f The file
     * @param s The file name
     * @return whether the file should be accepted or not
     */
    public boolean accept(File f, String s) {
        String extension = s.substring(s.lastIndexOf(".") + 1);
        if (extension != null) {
            if (extension.equals("e2d") ||
                    extension.equals("gbk") ||
                    extension.equals("faa") ||
                    extension.equals("pdb") ||
                    extension.equals("fasta")) {
                return true;
            } else {
                return false;
            }
        }

        return false;
    }

    /**
     * Gets the description of this filter.
     *
     * @return the description
     */
    public String getDescription() {
        return ".e2d, .gbk, .faa, .pdb, .fasta";
    }
}
