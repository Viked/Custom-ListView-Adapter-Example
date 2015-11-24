package viked.customlistviewadapterexample;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FileNavigator {
    private List<File> files = new ArrayList<File>();
    private String currentPath;
    private String pathTread;
    private String rootPath;


    public FileNavigator() {
        rootPath = getRootDir();
        currentPath = rootPath;
        pathTread = currentPath;
        updateFileList();
    }


    public boolean goToUpperFolder(){
        if(!currentPath.equals(rootPath)){
            currentPath = currentPath.substring(0, currentPath.lastIndexOf("/"));
            updateFileList();
            return true;
        }else{
            return false;
        }
    }

    public boolean goToLowerFolder(){
        int startIndex = pathTread.indexOf("/", currentPath.length() + 1);
        currentPath = startIndex > 0 ? pathTread.substring(0, startIndex) : pathTread;
        updateFileList();
        return true;
    }

    public boolean isFolderOpen(File folder){
        return pathTread.equals(folder.getAbsolutePath());
    }

    public boolean isNavigateUp(){
        return !currentPath.equals(rootPath);
    }

    public boolean isNavigateDown(){
        return !pathTread.equals(currentPath);
    }


    public List<File> getFiles() {
        return files;
    }

    public String getCurrentPath() {
        return currentPath;
    }

    public boolean goToFolder(int position){
        currentPath = files.get(position).getAbsolutePath();
        updateFileList();
        if (!currentPath.equals(pathTread) && !pathTread.contains(currentPath)) {
            pathTread = currentPath;
            return true;
        }else{
            return false;
        }
    }

    void updateFileList(){
        files.clear();
        files.addAll(getFileList(currentPath));
    }


    public static List<File> getFileList(String file){
        return getFileList(new File(file));
    }

    public static List<File> getFileList(File file){
        File[] filesArray = file.listFiles();
        List<File> files = new ArrayList<File>();
        if(filesArray != null && filesArray.length>0)
        {
            files = Arrays.asList(filesArray);
            Collections.sort(files, fileNameComparator());
        }
        return files;
    }

    public static Comparator<File> fileNameComparator(){
        return new Comparator<File>() {
            @Override
            public int compare(File file, File file2) {
                if (file.isDirectory() && file2.isFile())
                    return -1;
                else if (file.isFile() && file2.isDirectory())
                    return 1;
                else
                    return file.getPath().compareToIgnoreCase(file2.getPath());
            }
        };
    }

    public static int getFoldersCount(File file){
        return getFoldersCount(getFileList(file));
    }

    public static int getFoldersCount(List<File> files){
        int foldersCount = 0;
        for (File file:files) {
            if(file.isDirectory()){
                foldersCount++;
            }
        }
        return foldersCount;
    }

    public static String getRootDir(){
        return (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)?
                Environment.getExternalStorageDirectory().getAbsolutePath() :
                "/"
        );
    }
}
