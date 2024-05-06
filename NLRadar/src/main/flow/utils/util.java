package utils;

import java.util.Vector;
import java.io.File;

public class util {

    public static Vector<String> getapk(String droidbench){
        Vector<String> app_name_vec = new Vector<String>();
        File file = new File(droidbench);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                int lenname =tempList[i].getName().length();
                if(!tempList[i].getName().substring(lenname-4).equals("done"))
                {
                    app_name_vec.addElement(tempList[i].getName());
                }
            }
        }
        return app_name_vec;
    }
    
}
