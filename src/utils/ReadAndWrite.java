package utils;

import model.Bill;
import model.Table;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;

public class ReadAndWrite implements Serializable {

    public static final String LINK_SAVE_FORMAT_CSV_TABLE = "out_data/list_tables.csv";
    public static final String LINK_SAVE_OBJECT_TABLE = "src/data/list_tables.txt";
    public static final String LINK_SAVE_FORMAT_CSV_CA = "out_data/list_carried_away.csv";
    public static final String LINK_SAVE_OBJECT_CARRIED_AWAY = "src/data/list_carried_away.txt";
    public static final String LINK_SAVE_OBJECT_STAFF = "src/data/list_staff.txt";
    public static final String LINK_SAVE_OBJECT_DRINKS = "src/data/list_drinks.txt";
    public static final String LINK_SAVE_OBJECT_BILL = "src/data/list_bills.txt";
    public static final String FORMAT_CSV_TABLE = "STT,ID TABLE,DRINKS ODER,ID STAFF SERVING,TIME INPUT,TIME OUT,TOTAL ";
    public static final String FORMAT_CSV_CA = "STT,ID CA,DRINKS ODER,ID STAFF SERVING,TIME INPUT,TIME OUT,TOTAL ";
    public static final String FORMAT_CSV_BILLS = "STT,DATE PAY,USERNAME MANAGER,ID ODER,DRINKS ODER,ID STAFF SERVING,TIME INPUT,TIME OUT,TOTAL ";
    public static final String DOWN_THE_LINE = "\n";
    public static final String COMMA_DELIMITER = ",";

//    public static void writeDataOfTableFromFileFormatToCsv(String path, ArrayList<Table> listTable) {
//        FileWriter writer = null;
//        try {
//            writer = new FileWriter(path);
//            writer.append(FORMAT_CSV_CA);
//            writer.append(DOWN_THE_LINE);
//            int count = 0;
//            for (Table table : listTable) {
//                count++;
//                writer.append(String.valueOf(count));
//                writer.append(COMMA_DELIMITER);
//                writer.append(table.getIdTable());
//                writer.append(COMMA_DELIMITER);
//                writer.append(toStringTreeOder(table.getTreeOder()));
//                writer.append(COMMA_DELIMITER);
//                writer.append(table.getIdStaffServing());
//                writer.append(COMMA_DELIMITER);
//                writer.append(table.getTimeInput());
//                writer.append(COMMA_DELIMITER);
//                writer.append(table.getTimeOut());
//                writer.append(COMMA_DELIMITER);
//                writer.append(String.valueOf(table.getTotalMoney()));
//                writer.append(DOWN_THE_LINE);
//            }
//            writer.close();
//        } catch (Exception e) {
//        }
//    }
//    public static ArrayList<Bill> readDataBillsToFile(String path) {
//        ArrayList<Bill> listBill = new ArrayList<>();
//        try {
//            FileInputStream fis = new FileInputStream(path);
//            ObjectInputStream ois = new ObjectInputStream(fis);
//            listBill = (ArrayList<Bill>) ois.readObject();
//            ois.close();
//            fis.close();
//        } catch (Exception e) {
//            System.out.println("File danh sách thanh toán chưa có dữ liệu, hãy nhập dữ liệu và tạo ra nó !");
//        }
//        return listBill;
//    }
//
//    public static String toStringTreeOder(TreeMap<String, Integer> treeOder) {
//        String idDrinks, nameDrinks, qualityDrinks, stt;
//        Object obj = new Object();
//        Set set = treeOder.keySet();
//        Iterator i = set.iterator();
//        String strDisplay = "";
//        int count = 0;
//        while (i.hasNext()) {
//            count++;
//            stt = String.valueOf(count);
//            idDrinks = (String) i.next();
//            nameDrinks = nameDrinks(idDrinks);
//            qualityDrinks = String.valueOf(treeOder.get(idDrinks));
//            strDisplay = stt + "{" + idDrinks + "-" + nameDrinks + "-" + qualityDrinks + "}" + ";";
//        }
//        return strDisplay;
//    }
}
