package Median;

import java.io.File;
import java.util.Comparator;

/**
 * Created by  on 3/8/15.
 */
public class FileNameComparator implements Comparator<File> {
    public int compare( File a, File b ) {
        return a.getName().compareTo( b.getName() );
    }
}
