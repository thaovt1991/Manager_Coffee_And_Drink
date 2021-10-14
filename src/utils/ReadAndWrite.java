package utils;

import model.LoginHistory;

import java.io.*;
import java.util.ArrayList;

public class ReadAndWrite implements Serializable {
    public static final String LINK_HISTORY_LOG = "src/data/list_history_log.txt";

    public static ArrayList<LoginHistory> readDataHistoryLogToFile(String path){
        ArrayList<LoginHistory> listHistoryLog = new ArrayList<>();
        try{
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listHistoryLog = (ArrayList<LoginHistory>) ois.readObject();
            fis.close();
            ois.close();
        }catch (Exception e){
            System.out.println();
        }
        return listHistoryLog;
    }

    public static void writeHistoryLogInFile(String path, ArrayList<LoginHistory> listHistoryLog){
        try{
            FileOutputStream fos = new FileOutputStream(path) ;
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listHistoryLog);
            fos.close();
            oos.close();
        }catch (Exception e){
            System.out.println();
        }
    }
}
