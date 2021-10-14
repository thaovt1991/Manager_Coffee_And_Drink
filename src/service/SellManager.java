package service;

import model.*;
import precentation.Menu;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;
import java.util.regex.Pattern;

public class SellManager implements IManagerSell {

    public ArrayList<Staff> staffList;
    public ArrayList<Table> tablesListHaveCustomer;
    public ArrayList<CarriedAway> listCarriedAway;
    public ArrayList<Bill> listBills ;
    public ArrayList<Drinks> drinksList;
    public static long numIdCarryAway = 0;
    public final Integer NUM_TABLE = 10;
  //  public final Integer SIZE_CA = 10;
    static Scanner input = new Scanner(System.in);
    public static final String LINK_SAVE_FORMAT_CSV_TABLE = "out_data/list_tables.csv";
    public static final String LINK_SAVE_OBJECT_TABLE = "src/data/list_tables.txt";
    public static final String LINK_SAVE_FORMAT_CSV_CA = "out_data/list_carried_away.csv";
    public static final String LINK_SAVE_OBJECT_CARRIED_AWAY = "src/data/list_carried_away.txt";
    public static final String LINK_SAVE_OBJECT_STAFF = "src/data/list_staff.txt";
    public static final String LINK_SAVE_OBJECT_DRINKS = "src/data/list_drinks.txt";
    public static final String LINK_SAVE_OBJECT_BILL = "src/data/list_bills.txt";
    public static final String LINK_SAVE_FORMAT_CSV_BILLS = "out_data/list_bills.csv";
    public static final String FORMAT_CSV_TABLE = "STT,ID TABLE,DRINKS ODER,ID STAFF SERVING,TIME INPUT,TIME OUT,TOTAL ";
    public static final String FORMAT_CSV_CA = "STT,ID CA,DRINKS ODER,ID STAFF SERVING,TIME INPUT,TIME OUT,TOTAL ";
    public static final String FORMAT_CSV_BILLS = "STT,DATE PAY,USERNAME MANAGER,ID ORDER,DRINKS ODER,ID STAFF SERVING,TIME INPUT,TIME OUT,TOTAL ";
    public static final String DOWN_THE_LINE = "\n";
    public static final String COMMA_DELIMITER = ",";
    // public static final String LINK_REGEX = "(^([C|D][:])\\\\(?:[\\w]+\\\\)*\\w+$)|(^[C|D][:][\\\\]$)";
    public static final String ID_DRINKS_REGEX = "^[A-Z]{2}+\\d{3}$";
    public static final String ID_TABLE_REGEX = "^(([T]+[B])+\\d{2})$";
    public static final String ID_CARRIED_AWAYS = "^([C]+[A])+\\d";


    public SellManager() {
        staffList = readDataStaffFromFile(LINK_SAVE_OBJECT_STAFF);
        tablesListHaveCustomer = readDataTableToFile(LINK_SAVE_OBJECT_TABLE);
        drinksList = readDataDrinksFromFile(LINK_SAVE_OBJECT_DRINKS);
        listCarriedAway = readDataCarriedAwayToFile(LINK_SAVE_OBJECT_CARRIED_AWAY);
        try{
        numIdCarryAway =Integer.parseInt(listCarriedAway.get(listCarriedAway.size()-1).getIdCa().replace("CA","")) ;}
        catch (Exception e){
            numIdCarryAway = 0 ;
        }
        listBills = readDataBillsToFile(LINK_SAVE_OBJECT_BILL);

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
            System.out.println("File danh sách nhân viên chưa tồn tại, hãy khởi tạo danh sách nhân viên !");
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
            System.out.println("File danh sách thức uống chưa tồn tại, hãy khởi tạo danh sách để quản lý !");
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
           // System.out.println("Đã lưu lại mọi thay đổi vào dữ liệu gốc !");
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            System.out.println("File danh sách khách tại quán, hãy nhập dữ liệu và tạo ra nó !");
        }
        return tablesList;
    }

    public void writeDataTableToFile(String path, ArrayList<Table> tablesList) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(tablesList);
            oos.close();
            fos.close();
         //   System.out.println("Đã lưu lại mọi thay đổi vào dữ liệu gốc !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataOfTableFromFileFormatToCsv(String path, ArrayList<Table> listTable) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            writer.append(FORMAT_CSV_CA);
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

    public ArrayList<Bill> readDataBillsToFile(String path) {
        ArrayList<Bill> listBill = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listBill = (ArrayList<Bill>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            System.out.println("File danh sách thanh toán chưa có dữ liệu, hãy nhập dữ liệu và tạo ra nó !");
        }
        return listBill;
    }

    public void writeDataBillsToFile(String path, ArrayList<Bill> listBill) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listBill);
            oos.close();
            fos.close();
        //    System.out.println("Đã lưu lại mọi thay đổi vào dữ liệu gốc !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataOfBillsFromFileFormatToCsv(String path, ArrayList<Bill> listBills) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            writer.append(FORMAT_CSV_BILLS);
            writer.append(DOWN_THE_LINE);
            int count = 0;
            for (Bill bill : listBills) {
                count++;
                writer.append(String.valueOf(count));
                writer.append(COMMA_DELIMITER);
                writer.append(bill.getDateBill());
                writer.append(COMMA_DELIMITER);
                writer.append(bill.getUserName());
                writer.append(COMMA_DELIMITER);
                writer.append(bill.getIdOrder());
                writer.append(COMMA_DELIMITER);
                writer.append(toString(bill.getTreeOder()));
                writer.append(COMMA_DELIMITER);
                writer.append(bill.getIdStaffServing());
                writer.append(COMMA_DELIMITER);
                writer.append(bill.getTimeIn());
                writer.append(COMMA_DELIMITER);
                writer.append(bill.getTimeOut());
                writer.append(COMMA_DELIMITER);
                writer.append(String.valueOf(bill.getTotalMoney()));
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

    public ArrayList<CarriedAway> readDataCarriedAwayToFile(String path) {
        ArrayList<CarriedAway> carriedAwaysList = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            carriedAwaysList = (ArrayList<CarriedAway>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            System.out.println("File danh sách khách mang đi, hãy nhập dữ liệu và tạo ra nó !");
        }
        return carriedAwaysList;
    }

    public void writeDataCarriedAwayToFile(String path, ArrayList<CarriedAway> carriedAwaysList) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(carriedAwaysList);
            oos.close();
            fos.close();
        //    System.out.println("Đã lưu lại mọi thay đổi vào dữ liệu gốc !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeDataOfCarriedAwayFromFileFormatToCsv(String path, ArrayList<CarriedAway> carriedAwaysList) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            writer.append(FORMAT_CSV_CA);
            writer.append(DOWN_THE_LINE);
            int count = 0;
            for (CarriedAway carriedAway : carriedAwaysList) {
                count++;
                writer.append(String.valueOf(count));
                writer.append(COMMA_DELIMITER);
                writer.append(carriedAway.getIdCa());
                writer.append(COMMA_DELIMITER);
                writer.append(toString(carriedAway.getTreeOder()));
                writer.append(COMMA_DELIMITER);
                writer.append(carriedAway.getIdStaffServing());
                writer.append(COMMA_DELIMITER);
                writer.append(carriedAway.getTimeInput());
                writer.append(COMMA_DELIMITER);
                writer.append(carriedAway.getTimeOut());
                writer.append(COMMA_DELIMITER);
                writer.append(String.valueOf(carriedAway.getTotalMoney()));
                writer.append(DOWN_THE_LINE);
            }
            writer.close();
        } catch (Exception e) {
        }
    }

    public long getTotalMoney(TreeMap<String, Integer> treeOder) {
        long totalMoney = 0;
        for (String idDrinks : treeOder.keySet()) {
            for (Drinks drinks : drinksList) {
                if (drinks.getIdDrink().equals(idDrinks)) {
                    totalMoney += drinks.getPriceDrink() * treeOder.get(idDrinks);
                }
            }
        }
        return totalMoney;
    }

    public boolean isHaveDrinksInTreeOder(TreeMap<String, Integer> treeOder, String idDrinksCheck) {
        if (treeOder.containsKey(idDrinksCheck)) return true;
        return false;
    }


    public TreeMap<String, Integer> addTreeOder(TreeMap<String, Integer> treeOder, String idDrinksInput, int qualityDrinks) {
        int value;
        if (treeOder.containsKey(idDrinksInput)) {
            value = treeOder.get(idDrinksInput);
            treeOder.remove(idDrinksInput);
            treeOder.put(idDrinksInput, qualityDrinks + value);
        } else treeOder.put(idDrinksInput, qualityDrinks);
        return treeOder;
    }

    public ArrayList<String> listIdTable() {
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

    public boolean isHaveInlistTable(ArrayList<Table> tablesListHaveCustomer, ArrayList<String> listCheck) {
        for (Table table : tablesListHaveCustomer) {
            for (String idDrinks : listCheck) {
                if (table.getIdTable().equals(idDrinks)) return true;
            }
        }
        return false;
    }


    public String listTableEmpty() {
        ArrayList<String> listIdTable = listIdTable();
        if (isFullTable()) return "Hết bàn ! Vui lòng đợi hoặc mua mang về !";
        if (isEmptyAllTable()) return "Id bàn còn trống là : " + listIdTable;
        String listTableEmpty = "Id bàn còn trống là : ";
        while (isHaveInlistTable(tablesListHaveCustomer, listIdTable)) {
            String id = null;
            for (Table table : tablesListHaveCustomer) {
                for (String idDrinks : listIdTable) {
                    if (table.getIdTable().equals(idDrinks)) {
                        id = idDrinks;
                        break;
                    }
                }
            }
            if (id != null) listIdTable.remove(id);
        }
        return listTableEmpty + listIdTable;
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
    public long priceDrinks(String idDrink) {
        long priceDrinks = 0;
        for (Drinks drinks : drinksList) {
            if (drinks.getIdDrink().equals(idDrink)) {
                priceDrinks = drinks.getPriceDrink();
            }
        }
        return priceDrinks;
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

    public ArrayList<Drinks> changerQualityDrinks(ArrayList<Drinks> drinksList, String idDrinks,
                                                  int qualityDrinks) {
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
        System.out.printf("%-5s%-20s%-20s%s\n", "STT", "ID THỨC UỐNG", "TÊN THỨC UỐNG", "SỐ LƯỢNG");
        int count = 0;
        while (i.hasNext()) {
            count++;
            stt = String.valueOf(count);
            idDrinks = (String) i.next();
            nameDrinks = nameDrinks(idDrinks);
            qualityDrinks = String.valueOf(treeOder.get(idDrinks));
            System.out.printf("%-5s%-20s%-20s%s\n", stt, idDrinks, nameDrinks, qualityDrinks);
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


    public void sellDrinksInTable() {
        if (isFullTable()) {
            System.out.println("Hết bàn ! Vui lòng đợi hoặc mua mang về !");
            menuSell();
        } else {
            char check = ' ';
            boolean isCheck = false;
            do {
                System.out.println("-------------------------------");
                System.out.println("| Bạn đang order bàn mới ?     |");
                System.out.println("|   1. Đồng ý                  |");
                System.out.println("|   0. Hủy bỏ                  |");
                System.out.println("--------------------------------");
                System.out.println();
                System.out.print("Chọn : ");
                try {
                    check = input.nextLine().charAt(0);
                } catch (Exception e) {
                    check = ' ';
                }
                switch (check) {
                    case '1':
                        displaylistTable();
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
                                        menuSell();
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
                        boolean isChoose = false;

                        do {
                            System.out.println("------------------------------------------");
                            System.out.println("|  Bạn muốn khởi tạo bàn vừa order ?     |");
                            System.out.println("|     1. Yes                             |");
                            System.out.println("|     0. No                              |");
                            System.out.println("------------------------------------------");
                            System.out.println();
                            System.out.print("Chọn : ");
                            try {
                                press = input.nextLine().charAt(0);
                            } catch (Exception e) {
                                press = ' ';
                            }
                            switch (press) {
                                case '1': {
                                    tablesListHaveCustomer.add(newTable);
                                    writeDataDrinksToFile(LINK_SAVE_OBJECT_DRINKS, drinksList);
                                    writeDataTableToFile(LINK_SAVE_OBJECT_TABLE, tablesListHaveCustomer);
                                    writeDataOfTableFromFileFormatToCsv(LINK_SAVE_FORMAT_CSV_TABLE, tablesListHaveCustomer);
                                    break;
                                }
                                case '0':
                                    drinksList = readDataDrinksFromFile(LINK_SAVE_OBJECT_DRINKS);
                                    menuSell();
                                    break;
                                default:
                                    isChoose = true;
                            }
                        } while (isChoose);
                        break;
                    case '0':
                        menuSellDrinksManager();
                        break;
                    default:
                        isCheck = true;
                }
            } while (isCheck);
        }
    }

    public void sellDrinksCarriedAway() {
        char check = ' ';
        boolean isCheck = false;
        do {
            System.out.println("------------------------------------");
            System.out.println("| Bạn đang order khách mang về ?     |");
            System.out.println("|   1. Đồng ý                        |");
            System.out.println("|   0. Hủy bỏ                        |");
            System.out.println("--------------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                check = input.nextLine().charAt(0);
            } catch (Exception e) {
                check = ' ';
            }
            switch (check) {
                case '1':
                    numIdCarryAway++;
                    String idCa = "CA" + numIdCarryAway;
                    System.out.println("Tạo oder mang về cho khách có id '" + idCa + "' !!!");
                    System.out.println("Danh sách thức uống : ");
                    displayMenuDrinks();
                    TreeMap<String, Integer> treeOder = new TreeMap<>();
                    char choice = ' ';
                    boolean isChoice = true;
                    do {
                        System.out.print("Nhấn 'T' để tiếp tục lên danh sách thức uống cho khách mang về với id " + idCa + ", nhấn 'X để kết thúc nhập : ");
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
                                    menuSell();
                                    numIdCarryAway--;
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
                    System.out.println("Khách mua mang về với id '" + idCa + " ' oder : ");
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

                    CarriedAway carriedAway = new CarriedAway(idCa, treeOder, idStaff, timeBeginSell, timeOutSell, totalMoney);
                    char press = ' ';
                    boolean isChoose = true;
                    do {
                        System.out.println("----------------------------------------------------");
                        System.out.println("|  Bạn muốn khởi tạo order cho khách mang về  ?     |");
                        System.out.println("|     1. Yes                                        |");
                        System.out.println("|     0. No                                         |");
                        System.out.println("-----------------------------------------------------");
                        System.out.println();
                        System.out.print("Chọn : ");
                        try {
                            press = input.nextLine().charAt(0);
                        } catch (Exception e) {
                            press = ' ';
                        }
                        switch (press) {
                            case '1': {
                                listCarriedAway.add(carriedAway);
                                writeDataDrinksToFile(LINK_SAVE_OBJECT_DRINKS, drinksList);
                                writeDataCarriedAwayToFile(LINK_SAVE_OBJECT_CARRIED_AWAY, listCarriedAway);
                                writeDataOfCarriedAwayFromFileFormatToCsv(LINK_SAVE_FORMAT_CSV_CA, listCarriedAway);
                                isChoose = false;
                                break;
                            }
                            case '0':
                                numIdCarryAway--;
                                drinksList = readDataDrinksFromFile(LINK_SAVE_OBJECT_DRINKS);
                                menuSell();
                                isChoose = false;
                                break;
                            default:
                                isChoose = true;
                        }
                    } while (isChoose);
                    break;

                case '0':
                    menuSellDrinksManager();
                    break;
                default:
                    isCheck = true;
            }
        } while (isCheck);
    }

    public void menuSell() {
        char choice = ' ';
        do {
            System.out.println("--------------------------------------------");
            System.out.println("|             ORDER THỨC UỐNG               |");
            System.out.println("---------------------------------------------");
            System.out.println("| 1. Phục vụ khách hàng tại quán            |");
            System.out.println("| 2. Phục vụ khách hàng mang đi             |");
            System.out.println("|                               0. Quay lại |");
            System.out.println(" -------------------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    sellDrinksInTable();
                    break;
                case '2':
                    sellDrinksCarriedAway();
                    break;
                case '0':
                    menuSellDrinksManager();
                    break;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (choice != '0');
    }

    public Table getTableInListTableHaveCustomer(String idTableCheck) {
        for (Table table : tablesListHaveCustomer) {
            if (table.getIdTable().equals(idTableCheck)) {
                return table;
            }
        }
        return null ;
    }

    public CarriedAway getIdTableInListCA(String idCA) {
        for (CarriedAway cA : listCarriedAway) {
            if (cA.getIdCa().equals(idCA)) return cA;
        }
        return null;
    }

    public void menuEditListSell() {
        char choice = ' ';
        do {
            System.out.println("-------------------------------------------------------");
            System.out.println("|            SỬA ORDER THỨC UỐNG CHO KHÁCH             |");
            System.out.println("-------------------------------------------------------");
            System.out.println("| 1. Phục vụ khách hàng tại quán                       |");
            System.out.println("| 2. Phục vụ khách hàng mang đi                        |");
            System.out.println("|                                          0. Quay lại |");
            System.out.println(" ------------------------------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    editListTable();
                    break;
                case '2':
                    editListCarriedAway();
                    break;
                case '0':
                    menuSellDrinksManager();
                    break;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (choice != '0');
    }

    public void editListTable() {
        displaylistTable();
        String idTableEdit;
        do {
            System.out.println("Nhập id bàn cần thay đổi : ");
            idTableEdit = input.nextLine();
            if (getTableInListTableHaveCustomer(idTableEdit) == null) {
                System.out.println("Bàn có id không có khách hoặc không tồn tại !");
                menuEditListSell();
            }
        } while (getTableInListTableHaveCustomer(idTableEdit) == null);

        Table table = getTableInListTableHaveCustomer(idTableEdit);
        System.out.println("Bàn có id '" + idTableEdit + "' đã oder: ");
        displayTreeOder(table.getTreeOder());
        System.out.println();
        char press = ' ';
        do {
            System.out.println("--------------------------------------------------");
            System.out.println("|                YÊU CẦU SỬA ORDER               |");
            System.out.println("--------------------------------------------------");
            System.out.println("| 1. Thêm thức uống                              |");
            System.out.println("| 2. Trả lại thức uống                           |");
            System.out.println("|                                    0. Quay lại |");
            System.out.println("-------------------------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                press = input.nextLine().charAt(0);
            } catch (Exception e) {
                press = ' ';
            }
            switch (press) {
                case '1':
                    addDrinksToTable(table);
                    break;
                case '2':
                    removeDrinksToTable(table);
                    break;
                case '0':
                    menuEditListSell();
                    break;
                default:

            }
        }
        while (press != '0');
    }

    public void addDrinksToTable(Table table) {
        System.out.println("Danh sách thức uống : ");
        displayMenuDrinks();
        TreeMap<String, Integer> treeOder = table.getTreeOder();
        char choice = ' ';
        boolean isChoice = true;
        do {
            System.out.print("Nhấn 'T' để tiếp tục lên danh sách thức uống cho bàn " + table.getIdTable() + ", nhấn 'X để kết thúc nhập : ");
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
                        System.out.print("Nhập id thức uống muốn oder thêm : ");
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
                        menuSell();
                        return;
                    } else {
                        drinksList = changerQualityDrinks(drinksList, idDrinks, qualityDrinks);
                    }
                    treeOder = addTreeOder(treeOder, idDrinks, qualityDrinks);
                    System.out.println("Danh sách thức uống sau khi oder thêm của '" + table.getIdTable() + "' : ");
                    displayTreeOder(treeOder);
                    break;
                case 'x':
                case 'X':
                    isChoice = false;
                    break;
                default:
            }
        } while (isChoice);
        saveChangeTable(drinksList, table, treeOder);
    }

    public void removeDrinksToTable(Table table) {
        System.out.println("Danh sách thức uống : ");
        displayMenuDrinks();
        TreeMap<String, Integer> treeOder = table.getTreeOder();
        char choice = ' ';
        boolean isChoice = true;
        do {
            System.out.print("Nhấn 'T' để tiếp tục lên danh sách thức uống cho bàn " + table.getIdTable() + ", nhấn 'X để kết thúc nhập : ");
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
                        System.out.print("Nhập id thức uống khách muốn trả lại : ");
                        idDrinks = input.nextLine();
                        if (!isIdDrinksInMenu(idDrinks)) {
                            System.out.println("Không có thức uống nào có id '" + idDrinks + "' ! Hãy nhập lại");
                            displayMenuDrinks();
                        }
                        if (!treeOder.containsKey(idDrinks)) {
                            System.out.println("Khách chưa gọi sản phầm này ! Hãy kiểm tra lại");
                            displayTreeOder(treeOder);
                        }
                    } while (!isIdDrinksInMenu(idDrinks));

                    String srtQualityDrinks = "";
                    boolean isQuality = false;
                    int qualityDrinks = -1;
                    do {
                        System.out.print("Số lượng của thức uống '" + nameDrinks(idDrinks) + "' khách trả lại : ");
                        srtQualityDrinks = input.nextLine();
                        try {
                            qualityDrinks = Integer.parseInt(srtQualityDrinks);
                        } catch (Exception e) {
                            qualityDrinks = -1;
                        }
                        if (qualityDrinks > treeOder.get(idDrinks)) {
                            System.out.println("Số lượng thức uống '" + nameDrinks(idDrinks) + "' trả lại lớn hơn danh sách oder lúc đầu hãy kiểm tra lại ! ");
                            editListTable();
                        }
                    } while (qualityDrinks < 0 || (qualityDrinks > treeOder.get(idDrinks)));

                    if (qualityDrinks == treeOder.get(idDrinks)) {
                        treeOder.remove(idDrinks);
                    } else {
                        treeOder = addTreeOder(treeOder, idDrinks, -qualityDrinks);
                    }
                    drinksList = changerQualityDrinks(drinksList, idDrinks, -qualityDrinks);
                    break;
                case 'x':
                case 'X':
                    isChoice = false;
                    break;
                default:
            }
        } while (isChoice);
        saveChangeTable(drinksList, table, treeOder);
    }

    public void saveChangeTable(ArrayList<Drinks> drinksList, Table table, TreeMap<String, Integer> treeOder) {
        char choose = ' ';
        boolean isNotChoose = false;
        do {
            System.out.println("------------------------------");
            System.out.println("| Bạn muốn lưu thay đổi ?     |");
            System.out.println("|   1. Yes                    |");
            System.out.println("|   0. No                     |");
            System.out.println("-------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                choose = input.nextLine().charAt(0);
            } catch (Exception e) {
                choose = ' ';
            }
            switch (choose) {
                case '1':
                    table.setTreeOder(treeOder);
                    table.setTotalMoney(getTotalMoney(treeOder));
                    writeDataDrinksToFile(LINK_SAVE_OBJECT_DRINKS, drinksList);
                    writeDataTableToFile(LINK_SAVE_OBJECT_TABLE, tablesListHaveCustomer);
                    writeDataOfTableFromFileFormatToCsv(LINK_SAVE_FORMAT_CSV_TABLE, tablesListHaveCustomer);
                    break;
                case '0':
                    drinksList = readDataDrinksFromFile(LINK_SAVE_OBJECT_DRINKS);
                    editListTable();
                    break;
                default:
                    isNotChoose = true;
            }
        } while (isNotChoose);
    }

    public void editListCarriedAway() {
        displaylistCA();
        String idCAEdit;
        do {
            System.out.println("Nhập id của khách mang đi cần thay đổi : ");
            idCAEdit = input.nextLine();
            if (getIdTableInListCA(idCAEdit) == null) {
                System.out.println("Không có khách hàng nào được tìm thấy !");
                menuEditListSell();
            }
        } while (getIdTableInListCA(idCAEdit) == null);

        CarriedAway carriedAway = getIdTableInListCA(idCAEdit);
        System.out.println("Bàn có id '" + idCAEdit + "' đã oder: ");
        displayTreeOder(carriedAway.getTreeOder());
        System.out.println();
        char press = ' ';
        do {
            System.out.println("------------------------------------------------");
            System.out.println("|            YÊU CẦU SỬA ORDER                  |");
            System.out.println("------------------------------------------------");
            System.out.println("| 1. Thêm thức uống                              |");
            System.out.println("| 2. Trả lại thức uống                           |");
            System.out.println("|                                    0. Quay lại |");
            System.out.println("-------------------------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                press = input.nextLine().charAt(0);
            } catch (Exception e) {
                press = ' ';
            }
            switch (press) {
                case '1':
                    addDrinksToCA(carriedAway);
                    break;
                case '2':
                    removeDrinksToCA(carriedAway);
                    break;
                case '0':
                    menuEditListSell();
                    break;
                default:
            }
        }
        while (press != '0');
    }

    public void addDrinksToCA(CarriedAway carriedAway) {
        System.out.println("Danh sách thức uống : ");
        displayMenuDrinks();
        TreeMap<String, Integer> treeOder = carriedAway.getTreeOder();
        char choice = ' ';
        boolean isChoice = true;
        do {
            System.out.print("Nhấn 'T' để tiếp tục lên danh sách thức uống cho bàn " + carriedAway.getIdCa() + ", nhấn 'X để kết thúc nhập : ");
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
                        System.out.print("Nhập id thức uống muốn oder thêm : ");
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
                        menuSell();
                        return;
                    } else {
                        drinksList = changerQualityDrinks(drinksList, idDrinks, qualityDrinks);
                    }
                    treeOder = addTreeOder(treeOder, idDrinks, qualityDrinks);
                    System.out.println("Danh sách thức uống của khách sau khi oder thêm '" + carriedAway.getIdCa() + "' : ");
                    displayTreeOder(treeOder);
                    break;
                case 'x':
                case 'X':
                    isChoice = false;
                    break;
                default:
            }
        } while (isChoice);
        saveChangeCA(drinksList, carriedAway, treeOder);
    }

    public void removeDrinksToCA(CarriedAway carriedAway) {
        System.out.println("Danh sách thức uống : ");
        displayMenuDrinks();
        TreeMap<String, Integer> treeOder = carriedAway.getTreeOder();
        char choice = ' ';
        boolean isChoice = true;
        do {
            System.out.print("Nhấn 'T' để tiếp tục lên danh sách thức uống cho bàn " + carriedAway.getIdCa() + ", nhấn 'X để kết thúc nhập : ");
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
                        System.out.print("Nhập id thức uống khách muốn trả lại : ");
                        idDrinks = input.nextLine();
                        if (!isIdDrinksInMenu(idDrinks)) {
                            System.out.println("Không có thức uống nào có id '" + idDrinks + "' ! Hãy nhập lại");
                            displayMenuDrinks();
                        }
                        if (!treeOder.containsKey(idDrinks)) {
                            System.out.println("Khách chưa gọi sản phầm này ! Hãy kiểm tra lại");
                            displayTreeOder(treeOder);
                        }
                    } while (!isIdDrinksInMenu(idDrinks));

                    String srtQualityDrinks = "";
                    boolean isQuality = false;
                    int qualityDrinks = -1;
                    do {
                        System.out.print("Số lượng của thức uống '" + nameDrinks(idDrinks) + "' khách trả lại : ");
                        srtQualityDrinks = input.nextLine();
                        try {
                            qualityDrinks = Integer.parseInt(srtQualityDrinks);
                        } catch (Exception e) {
                            qualityDrinks = -1;
                        }
                        if (qualityDrinks > treeOder.get(idDrinks)) {
                            System.out.println("Số lượng thức uống '" + nameDrinks(idDrinks) + "' trả lại lớn hơn danh sách oder lúc đầu hãy kiểm tra lại ! ");
                            editListTable();
                        }
                    } while (qualityDrinks < 0 || (qualityDrinks > treeOder.get(idDrinks)));

                    if (qualityDrinks == treeOder.get(idDrinks)) {
                        treeOder.remove(idDrinks);
                    } else {
                        treeOder = addTreeOder(treeOder, idDrinks, -qualityDrinks);
                    }
                    drinksList = changerQualityDrinks(drinksList, idDrinks, -qualityDrinks);
                    break;
                case 'x':
                case 'X':
                    isChoice = false;
                    break;
                default:
            }
        } while (isChoice);
        saveChangeCA(drinksList, carriedAway, treeOder);
    }

    public void saveChangeCA(ArrayList<Drinks> drinksList, CarriedAway carriedAway, TreeMap<String, Integer> treeOder) {
        char choose = ' ';
        boolean isNotChoose = false;
        do {
            System.out.println("-------------------------------");
            System.out.println("| Bạn muốn lưu thay dổi ?      |");
            System.out.println("|   1. Đồng ý                  |");
            System.out.println("|   0. Hủy bỏ                  |");
            System.out.println("--------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                choose = input.nextLine().charAt(0);
            } catch (Exception e) {
                choose = ' ';
            }
            switch (choose) {
                case '1':
                    carriedAway.setTreeOder(treeOder);
                    carriedAway.setTotalMoney(getTotalMoney(treeOder));
                    writeDataDrinksToFile(LINK_SAVE_OBJECT_DRINKS, drinksList);
                    writeDataCarriedAwayToFile(LINK_SAVE_OBJECT_CARRIED_AWAY, listCarriedAway);
                    writeDataCarriedAwayToFile(LINK_SAVE_FORMAT_CSV_CA, listCarriedAway);
                    System.out.println("Lưu thành công !");
                    break;
                case '0':
                    drinksList = readDataDrinksFromFile(LINK_SAVE_OBJECT_DRINKS);
                    menuEditListSell();
                    break;
                default:
                    isNotChoose = true;
            }
        } while (isNotChoose);
    }


    public void displaylistTable() {
        int count = 0;
        String Count, idTable, idStaff, timeInput;
        System.out.println("Danh sách các bàn đang có khách :");
        System.out.printf("%-5s%-15s%-20s%s\n", "STT", "ID BÀN", "ID NHÂN VIÊN ODER", "THỜI GIAN VÀO");
        for (Table table : tablesListHaveCustomer) {
            count++;
            Count = String.valueOf(count);
            idTable = table.getIdTable();
            idStaff = table.getIdStaffServing();
            timeInput = table.getTimeInput();
            System.out.printf("%-5s%-15s%-20s%s\n", Count, idTable, idStaff, timeInput);
        }
        System.out.println();
    }

    public void displaylistCA() {
        int count = 0;
        String Count, idCA, idStaff, timeInput;
        System.out.println("Danh sách khách đang đợi mang về :");
        System.out.printf("%-5s%-15s%-20s%s\n", "STT", "ID HÀNG ĐỢI", "ID NHÂN VIÊN ODER", "THỜI GIAN VÀO");
        for (CarriedAway cA : listCarriedAway) {
            count++;
            Count = String.valueOf(count);
            idCA = cA.getIdCa();
            idStaff = cA.getIdStaffServing();
            timeInput = cA.getTimeInput();
            System.out.printf("%-5s%-15s%-20s%s\n", Count, idCA, idStaff, timeInput);
        }
        System.out.println();
    }

    public void menuPay() {
        char choice = ' ';
        do {
            System.out.println("--------------------------------------------------");
            System.out.println("|                  THANH TOÁN                     |");
            System.out.println("--------------------------------------------------");
            System.out.println("| 1. Tính tiền cho khách trong quán               |");
            System.out.println("| 2. Tính tiền cho khách mang đi                  |");
            System.out.println("|                                     0. Quay lại |");
            System.out.println("---------------------------------------------------");
            System.out.println();
            System.out.println("Chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    payForTable();
                    break;
                case '2':
                    payForCA();
                    break;
                case '0':
                    menuSellDrinksManager();
                    break;
                default:
            }
        } while (choice != '0');
    }
    public void payForTable() {
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String idTable = "";
        displaylistTable();
        System.out.println("nhập id bàn cần thanh toán :");
        idTable = input.nextLine();
        if (getTableInListTableHaveCustomer(idTable) == null) {
            System.out.println("Không có bàn nào cần tìm !");
            menuPay();
        } else {
            System.out.println("Bạn muốn thanh toán bàn '" + idTable + "'");
            Table table = getTableInListTableHaveCustomer(idTable);
            displayTreeOder(table.getTreeOder());
            System.out.println();
            System.out.println("Tổng tiền là : " + decimalFormat.format(table.getTotalMoney()) + " VND.");
            System.out.println();
            String timeOut = String.valueOf(java.time.LocalTime.now()) + " " + String.valueOf(java.time.LocalDate.now());
            table.setTimeOut(timeOut);
            String dateBill = String.valueOf(java.time.LocalDate.now());
            String username = Menu.username ;
            Bill bill = new Bill(dateBill,username,table.getIdTable(),table.getTreeOder(),table.getIdStaffServing(),table.getTimeInput(),table.getTimeOut(),table.getTotalMoney());
            System.out.println("Hóa đơn thanh toán : ");
            displayBill(bill);
            char choice = ' ';
            boolean isNotChoice = false;
            do {
                System.out.println("---------------------------");
                System.out.println("|  Bạn muốn thanh toán ?   |");
                System.out.println("|    1. Yes                |");
                System.out.println("|    0. No                 |");
                System.out.println("----------------------------");
                System.out.println();
                System.out.print("Chọn : ");
                try {
                    choice = input.nextLine().charAt(0);
                } catch (Exception e) {
                    choice = ' ';
                }
                switch (choice) {
                    case '1':
                        System.out.println("In hóa đơn");
                        displayBill(bill);
                        System.out.println();
                        listBills.add(bill) ;
                        writeDataBillsToFile(LINK_SAVE_OBJECT_BILL,listBills);
                        writeDataOfBillsFromFileFormatToCsv(LINK_SAVE_FORMAT_CSV_BILLS,listBills);
                        tablesListHaveCustomer.remove(table);
                        writeDataTableToFile(LINK_SAVE_OBJECT_TABLE,tablesListHaveCustomer);
                        writeDataOfTableFromFileFormatToCsv(LINK_SAVE_FORMAT_CSV_TABLE,tablesListHaveCustomer);
                        break;
                    case '0':
                        table.setTimeOut("Empty");
                        menuPay();
                        break;
                    default:
                }
            } while (isNotChoice);
        }
    }

    public void payForCA(){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        String idCA = "";
        displaylistCA();
        System.out.println("Nhập id khách mang về cần thanh toán :");
        idCA = input.nextLine();
        if (getIdTableInListCA(idCA) == null) {
            System.out.println("Không có khách hàng cần nào cần tìm !");
            menuPay();
        } else {
            System.out.println("Bạn muốn thanh toán cho khách hàng '" + idCA + "' mang về !");
            CarriedAway carriedAway = getIdTableInListCA(idCA);
            displayTreeOder(carriedAway.getTreeOder());
            System.out.println();
            System.out.println("Tổng tiền là : " + decimalFormat.format(carriedAway.getTotalMoney()) + " VND.");
            System.out.println();
            String timeOut = String.valueOf(java.time.LocalTime.now()) + " " + String.valueOf(java.time.LocalDate.now());
            carriedAway.setTimeOut(timeOut);
            String dateBill = String.valueOf(java.time.LocalDate.now());
            String username = Menu.username ;
            Bill bill = new Bill(dateBill,username,idCA,carriedAway.getTreeOder(),carriedAway.getIdStaffServing(),carriedAway.getTimeInput(),carriedAway.getTimeOut(),carriedAway.getTotalMoney());
            System.out.println("Hóa đơn thanh toán : ");
            displayBill(bill);
            char choice = ' ';
            boolean isNotChoice = false;
            do {
                System.out.println("---------------------------");
                System.out.println("|  Bạn muốn thanh toán ?   |");
                System.out.println("|    1. Yes                |");
                System.out.println("|    0. No                 |");
                System.out.println("----------------------------");
                System.out.println();
                System.out.print("Chọn : ");
                try {
                    choice = input.nextLine().charAt(0);
                } catch (Exception e) {
                    choice = ' ';
                }
                switch (choice) {
                    case '1':
                        System.out.println("In hóa đơn");
                        displayBill(bill);
                        System.out.println();
                        listBills.add(bill) ;
                        writeDataBillsToFile(LINK_SAVE_OBJECT_BILL,listBills);
                        writeDataOfBillsFromFileFormatToCsv(LINK_SAVE_FORMAT_CSV_BILLS,listBills);
                        listCarriedAway.remove(carriedAway);
                        writeDataCarriedAwayToFile(LINK_SAVE_OBJECT_CARRIED_AWAY,listCarriedAway);
                        writeDataOfCarriedAwayFromFileFormatToCsv(LINK_SAVE_FORMAT_CSV_CA,listCarriedAway);
                        break;
                    case '0':
                        carriedAway.setTimeOut("Empty");
                        menuPay();
                        break;
                    default:
                }
            } while (isNotChoice);
        }
    }

    public String getFullNameOfIdStaff(String idSfaff){
        for (Staff staff : staffList){
            if(staff.getIdStaff().equals(idSfaff))return staff.getFullName();
        }
        return null;
    }

    public void displayBill(Bill bill){
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
      String date, username , idOder , timeIn, timeOut,idStaff;
      TreeMap<String,Integer> treeOder = bill.getTreeOder();
      date = bill.getDateBill();
      username = bill.getUserName();
      idOder = bill.getIdOrder();
      idStaff = bill.getIdStaffServing();
      timeIn = bill.getTimeIn();
      timeOut = bill.getTimeOut();
      long totalMoney = bill.getTotalMoney();
        System.out.println("-------------------------------------- BILL -------------------------------------------");
        System.out.println("Date : "+ date +" - Username manager : "+username);
        System.out.println("Nhân viên phục vụ : "+ getFullNameOfIdStaff(idStaff));
        System.out.println("ID : " + idOder + " - Time in : " + timeIn+" - Time Out : "+ timeOut);
        System.out.println();
        String idDrinks, nameDrinks, qualityDrinks, stt,priceDrinks, money;
        Object obj = new Object();
        Set set = treeOder.keySet();
        Iterator i = set.iterator();
        System.out.printf("%-5s%-20s%-20s%-10s%-15s%s\n", "STT", "ID THỨC UỐNG", "TÊN THỨC UỐNG", "SỐ LƯỢNG","GIÁ","TIỀN");
        int count = 0;
        while (i.hasNext()) {
            count++;
            stt = String.valueOf(count);
            idDrinks = (String) i.next();
            nameDrinks = nameDrinks(idDrinks);
            qualityDrinks = String.valueOf(treeOder.get(idDrinks));
            priceDrinks = decimalFormat.format(priceDrinks(idDrinks));
            money = decimalFormat.format(priceDrinks(idDrinks)*treeOder.get(idDrinks));
            System.out.printf("%-5s%-20s%-20s%-10s%-15s%s\n", stt, idDrinks, nameDrinks, qualityDrinks,priceDrinks,money);
        }
        System.out.println("---------------------------------------------------------------------------------------");
        System.out.printf("%-60s%-10s%s\n","","TỔNG CỘNG : ",decimalFormat.format(totalMoney)+" VND");
        System.out.println();
    }



    public void menuSellDrinksManager() {
        char choice = ' ';
        do {
            System.out.println("------------------------------------------------------");
            System.out.println("|                  QUẢN LÝ BÁN HÀNG                   |");
            System.out.println("-------------------------------------------------------");
            System.out.println("| 1. Order thức uống cho khách                        |");
            System.out.println("| 2. Sửa order cho khách                              |");
            System.out.println("| 3. Thanh toán tiền                                  |");
            System.out.println("|                                         0. Quay lại |");
            System.out.println("-------------------------------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    menuSell();
                    break;
                case '2':
                    menuEditListSell();
                    break;
                case '3':
                    menuPay();
                    break;
                case '0':
                    if (Menu.decentralization.equals("Guest")) {
                        Menu.menuWorkWithGuest();
                    } else Menu.menuWorkWithAdmin();
                default:
                    System.out.println("Chọn lại !");
            }
        } while (choice != '0');
    }

    @Override
    public void menuManager() {
        menuSellDrinksManager();
    }

    @Override
    public void order() {
        menuSell();
    }

    @Override
    public void editOder() {
       menuEditListSell();
    }

    @Override
    public void pay() {
       menuPay();
    }
}