package pro.jeong.molithackathon2018.utils;

import java.io.File;
import java.util.ArrayList;

public class Utilities {
    private static ArrayList<String> endPointPaths = new ArrayList<>();

    public static ArrayList<String> listEndPointDirectories(String rootDir) {
        if(!new File(rootDir).isDirectory()) {
            String discoveredFilePath = rootDir;
            endPointPaths.add(discoveredFilePath);
        } else if(new File(rootDir).isDirectory()) {
            String[] pathList = new File(rootDir).list();
            for(String f : pathList) {
                String togo = rootDir + "/" + f;
                listEndPointDirectories(togo);
            }
        }
        return endPointPaths;
    }
}
