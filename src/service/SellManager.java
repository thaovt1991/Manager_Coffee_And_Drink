package service;

import model.Account;
import model.Drinks;
import model.Staff;
import model.Table;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.TreeMap;

public class SellManager implements Serializable {

    private ArrayList<Staff> staffList;
    private ArrayList<Table> tablesList;
    private ArrayList<Drinks> drinksList;
    static Scanner input = new Scanner(System.in);
    public static final String LINK_SAVE_OBJECT_TABLE = "D:\\Manager_Coffee_And_Drink\\src\\data\\list_tables.txt";
    public static final String LINK_SAVE_OBJECT_STAFF = "D:\\Manager_Coffee_And_Drink\\src\\data\\list_staff.txt";
    public static final String LINK_SAVE_OBJECT_DRINKS = "D:\\Manager_Coffee_And_Drink\\src\\data\\list_drinks.txt";
    public static final String DOWN_THE_LINE = "\n";
    public static final String COMMA_DELIMITER = ",";
   // public static final String LINK_REGEX = "(^([C|D][:])\\\\(?:[\\w]+\\\\)*\\w+$)|(^[C|D][:][\\\\]$)";
    public static final String ID_DRINKS_REGEX = "^[A-Z]{2}+\\d{3}$";
    public static final String ID_TABLE_REGEX = "[A-Z]{2}+\\d{3}$";


    public SellManager() {
        this.staffList = readDataStaffFromFile(LINK_SAVE_OBJECT_STAFF);
        this.tablesList = readDataTableToFile(LINK_SAVE_OBJECT_TABLE);
        this.drinksList = readDataDrinksFromFile(LINK_SAVE_OBJECT_DRINKS);
    }

    public ArrayList<Staff> readDataStaffFromFile(String path) {
        ArrayList<Staff> listStaff = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listStaff = (ArrayList<Staff>) ois.readObject();
            fis.close();
            ois.close();
        } catch (Exception ex) {
            System.out.println("File chưa tồn tại, hãy khởi tạo danh sách nhân viên !");
            StaffManager staffManager = new StaffManager();
            staffManager.addStaff();
        }
        return listStaff;
    }

    public ArrayList<Drinks> readDataDrinksFromFile(String path) {
        ArrayList<Drinks> drinksList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            drinksList = (ArrayList<Drinks>) ois.readObject();
            fis.close();
            ois.close();
        } catch (Exception ex) {
            System.out.println("File chưa tồn tại, hãy khởi tạo danh sách nhân viên !");
            DrinksManager drinksManager = new DrinksManager();
            drinksManager.addDrinksList();
        }
        return drinksList;
    }

    public ArrayList<Table> readDataTableToFile(String path) {
        ArrayList<Table> tablesList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            tablesList = (ArrayList<Table>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            System.out.println("File chưa tồn tại, hãy nhập dữ liệu và tạo ra nó !");
        }
        return tablesList;
    }

    public void writeDataTableToFile(String path, ArrayList<Table> accountsList) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(accountsList);
            oos.close();
            fos.close();
            System.out.println("Đã lưu lại mọi thay đổi vào dữ liệu gốc !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public long getTotalMoney(TreeMap<String, Integer> treeOder) {
        long totalMoney = 0;
        for (String idDrinks : treeOder.keySet()) {
            for (Drinks drinks : drinksList) {
                if (drinks.getIdDrink().equals(idDrinks)) {
                    totalMoney = drinks.getPriceDrink() * treeOder.get(idDrinks);
                }
            }
        }
        return totalMoney;
    }

    public boolean isHaveDrinksInTreeOder(TreeMap<String, Integer> treeOder, String idDrinksCheck) {
        for (String idDrinks : treeOder.keySet()) {
            if (idDrinks.equals(idDrinksCheck)) return true;
        }
        return false;
    }


    public void addTreeOder(TreeMap<String, Integer> treeOder, String idDrinksInput) {
        int value;
        for (String idDrinks : treeOder.keySet()) {
            if (isHaveDrinksInTreeOder(treeOder, idDrinksInput)) {
                value = treeOder.get(idDrinks);
                treeOder.remove(idDrinks);
                treeOder.put(idDrinks, value + 1);
            } else {
                treeOder.put(idDrinks, 1);
            }
        }
    }


}