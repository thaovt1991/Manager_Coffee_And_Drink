package service;

import model.Drinks;
import model.Staff;
import model.Table;

import java.io.*;
import java.sql.Time;
import java.util.*;
import java.util.regex.Pattern;

public class SellManager implements Serializable {

    public ArrayList<Staff> staffList;
    public ArrayList<Table> tablesListHaveCustomer;
    public ArrayList<Drinks> drinksList;
    public static final Integer NUM_TABLE = 10;
    public static final ArrayList<String> LIST_ID_TABLE = listIdTable();
    static Scanner input = new Scanner(System.in);
    public static final String LINK_SAVE_FORMAT_CSV_TABLE = "D:\\Manager_Coffee_And_Drink\\out_data\\list_tables.csv";
    public static final String LINK_SAVE_OBJECT_TABLE = "D:\\Manager_Coffee_And_Drink\\src\\data\\list_tables.txt";
    public static final String LINK_SAVE_OBJECT_STAFF = "D:\\Manager_Coffee_And_Drink\\src\\data\\list_staff.txt";
    public static final String LINK_SAVE_OBJECT_DRINKS = "D:\\Manager_Coffee_And_Drink\\src\\data\\list_drinks.txt";
    public static final String FORMAT_CSV_TABLE = "STT,ID TABLE,DRINKS ODER,ID STAFF SERVING,TIME INPUT,TIME OUT,TOTAL ";
    public static final String DOWN_THE_LINE = "\n";
    public static final String COMMA_DELIMITER = ",";
    // public static final String LINK_REGEX = "(^([C|D][:])\\\\(?:[\\w]+\\\\)*\\w+$)|(^[C|D][:][\\\\]$)";
    public static final String ID_DRINKS_REGEX = "^[A-Z]{2}+\\d{3}$";
    public static final String ID_TABLE_REGEX = "^(([T]+[B])+\\d{2})$";


    public SellManager() {
        staffList = readDataStaffFromFile(LINK_SAVE_OBJECT_STAFF);
        tablesListHaveCustomer = readDataTableToFile(LINK_SAVE_OBJECT_TABLE);
        drinksList = readDataDrinksFromFile(LINK_SAVE_OBJECT_DRINKS);
    }

    public boolean isFormatIdTable(String idTableCheck) {
        return Pattern.compile(ID_TABLE_REGEX).matcher(idTableCheck).matches();
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

    public void writeDataDrinksToFile(String path, ArrayList<Drinks> listDrinks) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listDrinks);
            oos.close();
            fos.close();
            System.out.println("Đã lưu lại mọi thay đổi vào dữ liệu gốc !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataFromFileFormatToCsv(String path, ArrayList<Table> listTable) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            writer.append(FORMAT_CSV_TABLE);
            writer.append(DOWN_THE_LINE);
            int count = 0;
            for (Table table : listTable) {
                count++;
                writer.append(String.valueOf(count));
                writer.append(COMMA_DELIMITER);
                writer.append(table.getIdTable());
                writer.append(COMMA_DELIMITER);
                writer.append(toString(table.getTreeOder()));
                writer.append(COMMA_DELIMITER);
                writer.append(table.getIdStaffServing());
                writer.append(COMMA_DELIMITER);
                writer.append(table.getTimeInput());
                writer.append(COMMA_DELIMITER);
                writer.append(table.getTimeOut());
                writer.append(COMMA_DELIMITER);
                writer.append(String.valueOf(table.getTotalMoney()));
                writer.append(DOWN_THE_LINE);
            }
            writer.close();
        } catch (Exception e) {
        }
    }

    public String toString(TreeMap<String, Integer> treeOder) {
        String idDrinks, nameDrinks, qualityDrinks, stt;
        Object obj = new Object();
        Set set = treeOder.keySet();
        Iterator i = set.iterator();
        String strDisplay = "";
        int count = 0;
        while (i.hasNext()) {
            count++;
            stt = String.valueOf(count);
            idDrinks = (String) i.next();
            nameDrinks = nameDrinks(idDrinks);
            qualityDrinks = String.valueOf(treeOder.get(idDrinks));
            strDisplay = stt + "{" + idDrinks + "-" + nameDrinks + "-" + qualityDrinks + "}" + ";";
        }
        return strDisplay;
    }

    ;

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


    public TreeMap<String, Integer> addTreeOder(TreeMap<String, Integer> treeOder, String idDrinksInput, int qualityDrinks) {
        int value;
        if (treeOder.keySet() != null) {
            for (String idDrinks : treeOder.keySet()) {
                if (isHaveDrinksInTreeOder(treeOder, idDrinksInput)) {
                    value = treeOder.get(idDrinksInput);
                    treeOder.remove(idDrinksInput);
                    treeOder.put(idDrinksInput, value + qualityDrinks);
                } else {
                    treeOder.put(idDrinksInput, qualityDrinks);
                }
            }
        } else treeOder.put(idDrinksInput, qualityDrinks);
        return treeOder;
    }

    public static ArrayList<String> listIdTable() {
        ArrayList<String> listIdtable = new ArrayList<>();
        for (int i = 1; i <= NUM_TABLE; i++) {
            if (i < 10) {
                listIdtable.add("TB0" + i);
            } else listIdtable.add("TB" + i);
        }
        return listIdtable;
    }

    public boolean isEmptyAllTable() {
        if (tablesListHaveCustomer.isEmpty()) return true;
        return false;
    }

    public boolean isFullTable() {
        if (tablesListHaveCustomer.size() == NUM_TABLE) return true;
        return false;
    }

    public boolean isTableEmpty(String idTableCheck) {
        if (tablesListHaveCustomer.isEmpty()) return true;
        for (Table table : tablesListHaveCustomer) {
            if (table.getIdTable().equals(idTableCheck)) return false;
        }
        return true;
    }

    public String listTableEmpty() {
        if (isFullTable()) return "Hết bàn ! Vui lòng đợi hoặc mua mang về !";
        if (isEmptyAllTable()) return "Id bàn còn trống là : " + listIdTable();
        String listTableEmpty = "Id bàn còn trống là : ";

        for (String idTable : LIST_ID_TABLE) {
            for (Table table : tablesListHaveCustomer) {
                if (!idTable.equals(table.getIdTable())) {
                    listTableEmpty += idTable + ", ";
                }
            }
        }
        return listTableEmpty;
    }

    public void displayMenuDrinks() {
        DrinksManager drinksManager = new DrinksManager();
        drinksManager.displayDrinkFornmat();
    }

    public boolean isIdDrinksInMenu(String idDrinksCheck) {
        for (Drinks drinks : drinksList) {
            if (drinks.getIdDrink().equals(idDrinksCheck)) return true;
        }
        return false;
    }

    public String nameDrinks(String idDrink) {
        String nameDrinks = "";
        for (Drinks drinks : drinksList) {
            if (drinks.getIdDrink().equals(idDrink)) {
                nameDrinks = drinks.getNameDrink();
            }
        }
        return nameDrinks;
    }

    public int qualityDrinks(String idDrinks) {
        int qualityDrinks = 0;
        for (Drinks drinks : drinksList) {
            if (drinks.getIdDrink().equals(idDrinks)) {
                qualityDrinks = drinks.getQualityDrink();
            }
        }
        return qualityDrinks;
    }

    public ArrayList<Drinks> changerQualityDrinks(ArrayList<Drinks> drinksList, String idDrinks, int qualityDrinks) {
        for (Drinks drinks : drinksList) {
            if (drinks.getIdDrink().equals(idDrinks)) {
                drinks.setQualityDrink(drinks.getQualityDrink() - qualityDrinks);
                break;
            }
        }
        return drinksList;
    }


    public void displayTreeOder(TreeMap<String, Integer> treeOder) {
        String idDrinks, nameDrinks, qualityDrinks, stt;
        Object obj = new Object();
        Set set = treeOder.keySet();
        Iterator i = set.iterator();
        System.out.printf("%-3s%-10s%-20s%s\n", "STT", "ID THỨC UỐNG", "TÊN THỨC UỐNG", "SỐ LƯỢNG");
        int count = 0;
        while (i.hasNext()) {
            count++;
            stt = String.valueOf(count);
            idDrinks = (String) i.next();
            nameDrinks = nameDrinks(idDrinks);
            qualityDrinks = String.valueOf(treeOder.get(idDrinks));
            System.out.printf("%-3s%-10s%-20s%s\n", stt, idDrinks, nameDrinks, qualityDrinks);
        }
        System.out.println();
    }

    public void displayStaff() {
        System.out.printf("%-5s%-15s%-20s%s\n", "", "STT", "ID NHÂN VIÊN", "HỌ VA TÊN");
        int count = 0;
        for (Staff staff : staffList) {
            count++;
            System.out.printf("%-5s%-15s%-20s%s\n", "", String.valueOf(count), staff.getIdStaff(), staff.getFullName());
        }
    }

    public boolean isIdStaff(String idStaffCheck) {
        for (Staff staff : staffList) {
            if (staff.getIdStaff().equals(idStaffCheck)) return true;
        }
        return false;
    }

//    public void saveTable(Table table, ArrayList<Drinks> drinksList){
//        char press = ' ';
//        boolean isChoice = true;
//        do {
//            System.out.print("Nhấn 'Y' để đồng ý ! Nhấn 'N' để hủy bỏ thao tác !");
//            try {
//                press = input.nextLine().charAt(0);
//            } catch (Exception e) {
//                press = ' ';
//            }
//            switch (press) {
//                case 'Y':
//                case 'y': {
//                    tablesListHaveCustomer.add(table);
//                    writeDataDrinksToFile(LINK_SAVE_OBJECT_DRINKS,drinksList);
//                    writeDataTableToFile(LINK_SAVE_OBJECT_TABLE, tablesListHaveCustomer);
//                    //writeDataFromFileFormatToCsv(LINK_SAVE_FORMAT_CSV_TABLE, tablesListHaveCustomer);
//                    isChoice = false;
//                    break;
//                }
//                case 'n':
//                case 'N':
//                    drinksList = readDataDrinksFromFile(LINK_SAVE_OBJECT_DRINKS);
//                    //menuSell();
//                    isChoice = false;
//                    break;
//                default:
//                    isChoice = true;
//            }
//        } while (isChoice);
//    }

    public void sellDrinksInTable() {
        if (isFullTable()) {
            System.out.println("Hết bàn ! Vui lòng đợi hoặc mua mang về !");
            //sellDrinks();
        } else {
            char check = ' ';
            boolean isCheck = false;
            do {
                System.out.print("Bạn đang oder bàn mới ? Nhấn 'Y' để đồng ý, nhấn 'N' để quay trờ về menu !");
                try {
                    check = input.nextLine().charAt(0);
                } catch (Exception e) {
                    check = ' ';
                }
                switch (check) {
                    case 'y':
                    case 'Y':
                        //displayTableList();
                        System.out.println(listTableEmpty());

                        String idTable;
                        do {
                            System.out.print("Nhập id bàn oder : ");
                            idTable = input.nextLine();
                            if (!isFormatIdTable(idTable)) {
                                System.out.println("Không phải format id của bàn ");
                            } else if (!isTableEmpty(idTable)) {
                                System.out.println("Bàn đã có khách ngồi!");
                                System.out.println(listTableEmpty());
                            }
                        } while (!isTableEmpty(idTable) || !isFormatIdTable(idTable));
                        System.out.println("Danh sách thức uống : ");
                        displayMenuDrinks();
                        TreeMap<String, Integer> treeOder = new TreeMap<>();
                        char choice = ' ';
                        boolean isChoice = true;
                        do {
                            System.out.print("Nhấn 'T' để tiếp tục lên danh sách thức uống cho bàn " + idTable + ", nhấn 'X để kết thúc nhập : ");
                            try {
                                choice = input.nextLine().charAt(0);
                            } catch (Exception e) {
                                choice = ' ';
                            }
                            switch (choice) {
                                case 't':
                                case 'T':
                                    String idDrinks;
                                    do {
                                        System.out.print("Nhập id thức uống : ");
                                        idDrinks = input.nextLine();
                                        if (!isIdDrinksInMenu(idDrinks)) {
                                            System.out.println("Không có thức uống nào có id '" + idDrinks + "' ! Hãy nhập lại");
                                            displayMenuDrinks();
                                        }
                                    } while (!isIdDrinksInMenu(idDrinks));

                                    String srtQualityDrinks = "";
                                    boolean isQuality = false;
                                    int qualityDrinks = -1;
                                    do {
                                        System.out.print("Số lượng của thức uống '" + nameDrinks(idDrinks) + "' theo yêu cầu của khách : ");
                                        srtQualityDrinks = input.nextLine();
                                        try {
                                            qualityDrinks = Integer.parseInt(srtQualityDrinks);
                                        } catch (Exception e) {
                                            qualityDrinks = -1;
                                        }
                                    } while (qualityDrinks < 0);

                                    if (qualityDrinks > qualityDrinks(idDrinks)) {
                                        System.out.println("Số lượng thức uống '" + nameDrinks(idDrinks) + "' trong kho không đủ để cung cấp cho khách hàng! Hãy thông báo khách để oder lại thức uống ! ");
                                        //menuSell();
                                        return; // lenh thoat khoi vong;
                                    } else {
                                        drinksList = changerQualityDrinks(drinksList, idDrinks, qualityDrinks);
                                    }
                                    treeOder = addTreeOder(treeOder, idDrinks, qualityDrinks);//ok
                                    break;
                                case 'x':
                                case 'X':
                                    isChoice = false;
                                default:
                            }
                        } while (isChoice);
                        System.out.println("Bàn có id '" + idTable + " ' oder : ");
                        displayTreeOder(treeOder);

                        String idStaff = "";
                        do {
                            displayStaff();
                            System.out.print("Id nhân viên oder : ");
                            idStaff = input.nextLine();
                            if (!isIdStaff(idStaff)) {
                                System.out.println("Hãy nhập đúng id nhân viên ! Hãy căn cứ vào danh sách nhân viên để nhập !");
                            }
                        } while (!isIdStaff(idStaff));

                        String dateSell = String.valueOf(java.time.LocalDate.now());
                        String timeSell = String.valueOf(java.time.LocalTime.now());
                        String timeBeginSell = timeSell + " " + dateSell;
                        String timeOutSell = "Empty";

                        long totalMoney = getTotalMoney(treeOder);

                        Table newTable = new Table(idTable, treeOder, idStaff, timeBeginSell, timeOutSell, totalMoney);
                        char press = ' ';
                        boolean isChoose = true;
                        System.out.println("Bạn muốn khởi tạo bàn trên !");
                        do {
                            System.out.print("Nhấn 'Y' để đồng ý ! Nhấn 'N' để hủy bỏ thao tác !");
                            try {
                                press = input.nextLine().charAt(0);
                            } catch (Exception e) {
                                press = ' ';
                            }
                            switch (press) {
                                case 'Y':
                                case 'y': {
                                    tablesListHaveCustomer.add(newTable);
                                    writeDataDrinksToFile(LINK_SAVE_OBJECT_DRINKS, drinksList);
                                    writeDataTableToFile(LINK_SAVE_OBJECT_TABLE, tablesListHaveCustomer);
                                    writeDataFromFileFormatToCsv(LINK_SAVE_FORMAT_CSV_TABLE, tablesListHaveCustomer);
                                    isChoose = false;
                                    break;
                                }
                                case 'n':
                                case 'N':
                                    drinksList = readDataDrinksFromFile(LINK_SAVE_OBJECT_DRINKS);
                                    //menuSell();
                                    isChoose = false;
                                    break;
                                default:
                                    isChoose = true;
                            }
                        } while (isChoose);
                        break;
                    case 'n':
                    case 'N':
                        // menuSellDrinksManager();
                        break;
                    default:
                        isCheck = true;
                }
            } while (isCheck);
        }

    }
}