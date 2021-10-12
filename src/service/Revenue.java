package service;

import model.Bill;
import model.Drinks;

import java.io.*;
import java.text.DecimalFormat;
import java.util.*;

public class Revenue implements Serializable {

    public static Scanner input = new Scanner(System.in);
    public static DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    public ArrayList<Bill> listBills;
    public ArrayList<Drinks> drinksList;
    public static final String FORMAT_CSV_BILLS = "STT,DATE PAY,USERNAME MANAGER,ID ODER,DRINKS ODER,ID STAFF SERVING,TIME INPUT,TIME OUT,TOTAL ";
    public static final String DOWN_THE_LINE = "\n";
    public static final String COMMA_DELIMITER = ",";
    public static final String LINK_SAVE_OBJECT_BILL = "src/data/list_bills.txt";
    public static final String LINK_SAVE_FORMAT_CSV_BILLS = "out_data/list_bills.csv";
    public static final String LINK_SAVE_OBJECT_DRINKS = "src/data/list_drinks.txt";
    //  public static final String DATE_OF_BIRTH_REGEX = "^[0|1|2|3]?[0-9][-][0-1]?[0-9][/][1|2]\\d{3}$";

    public Revenue() {
        listBills = readDataBillsToFile(LINK_SAVE_OBJECT_BILL);
        drinksList = readDataDrinksFromFile(LINK_SAVE_OBJECT_DRINKS);
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
                writer.append(bill.getIdOder());
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

    public String nameDrinks(String idDrink) {
        String nameDrinks = "";
        for (Drinks drinks : drinksList) {
            if (drinks.getIdDrink().equals(idDrink)) {
                nameDrinks = drinks.getNameDrink();
            }
        }
        return nameDrinks;
    }


    public void menuDisplayRevenue() {
        char choice = ' ';
        System.out.println("--------------------------------------------------");
        System.out.println("|               QUẢN LÝ DOANH THU                 |");
        System.out.println("--------------------------------------------------");
        System.out.println("|  1. Doanh thu theo ngày                         |");
        System.out.println("|  2. Doanh thu theo tháng                        |");
        System.out.println("|  3. Doanh thu theo người quản lý bán hàng       |");
        System.out.println("|  4. Doanh thu theo nhân viên phục vụ            |");
        System.out.println("|  5. Doanh thu tùy chọn                          |");
        System.out.println("|                                   0. Quay lại   |");
        System.out.println("---------------------------------------------------");
    }

    public void revenueByDay() {
        System.out.println("Nhập ngày tháng năm muốn xem ! ");
        System.out.print("Ngày : ");
        String day = input.nextLine();
        System.out.print("Tháng: ");
        String month = input.nextLine();
        System.out.print("Năm :");
        String year = input.nextLine();
        String date = year + "-" + month + "-" + day;
        ArrayList<Bill> listBillSearch = new ArrayList<>();
        for (Bill bill : listBills) {
            if (bill.getDateBill().contains(date)) {
                listBillSearch.add(bill);
            }
        }
        //dispalylistBilssSeac(Stt,ngay,user,idoder,nvoder,in,omn,total
        System.out.println();
        System.out.println("Tổng tiền thu nhập trong ngày " + date + "là :" + decimalFormat.format(totalByListBills(listBillSearch)));

    }

    public void revenueByDayToDay() {
        String dayBegin, monthBegin, yearBegin, dayEnd, monthEnd, yearEnd,dateBegin,dateEnd;
        System.out.println("Nhập ngày tháng năm bắt đầu tìm kiếm ! ");
        System.out.print("Ngày : ");
        dayBegin = input.nextLine();
        System.out.print("Tháng: ");
        monthBegin = input.nextLine();
        System.out.print("Năm :");
        yearBegin = input.nextLine();
        dateBegin = yearBegin + "-" + monthBegin + "-" + dayBegin;
        System.out.println("Nhập ngày tháng năm kết thúc tìm kiếm ! ");
        System.out.print("Ngày : ");
        dayEnd = input.nextLine();
        System.out.print("Tháng: ");
        monthEnd = input.nextLine();
        System.out.print("Năm :");
        yearEnd = input.nextLine();
        dateEnd = yearEnd + "-" + monthEnd + "-" + dayEnd;
        ArrayList<Bill> listBillSearch = new ArrayList<>();
        int indexBegin , indexEnd ;
        indexBegin = listBills.indexOf(dateBegin);
        indexEnd = listBills.lastIndexOf(dateEnd);
        while (indexBegin<=indexEnd){
            listBillSearch.add(listBills.get(indexBegin)) ;
            indexBegin++ ;
        }

        //showlistBillSearch
        System.out.println();
        System.out.println("Tổng tiền thu nhập từ ngày " + dateBegin +" đến ngày" + dateEnd +" là :" + decimalFormat.format(totalByListBills(listBillSearch)));
        }



    public long totalByListBills(ArrayList<Bill> listBills) {
        long totalMoneyListBills = 0;
        for (Bill bill : listBills) {
            totalMoneyListBills += bill.getTotalMoney();
        }
        return totalMoneyListBills;
    }
}
