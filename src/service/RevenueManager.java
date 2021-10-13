package service;

import model.Account;
import model.Bill;
import model.Drinks;
import precentation.Menu;

import java.io.*;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.Month;
import java.util.*;
import java.util.regex.Pattern;

public class RevenueManager implements Serializable {

    public static Scanner input = new Scanner(System.in);
    public static DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
    public ArrayList<Bill> listBills;
    public ArrayList<Drinks> drinksList;
    public ArrayList<Account> accountsList;
    public static final String FORMAT_CSV_BILLS = "STT,DATE PAY,USERNAME MANAGER,ID ORDER,DRINKS ODER,ID STAFF SERVING,TIME INPUT,TIME OUT,TOTAL ";
    public static final String DOWN_THE_LINE = "\n";
    public static final String COMMA_DELIMITER = ",";
    public static final String LINK_SAVE_OBJECT_ACCOUNT = "src/data/list_account.txt";
    public static final String LINK_SAVE_OBJECT_BILL = "src/data/list_bills.txt";
    public static final String LINK_SAVE_FORMAT_CSV_BILLS = "out_data/list_bills.csv";
    public static final String LINK_SAVE_OBJECT_DRINKS = "src/data/list_drinks.txt";
    public static final String LINK_REGEX = "(^([C|D][:])\\\\(?:[\\w]+\\\\)*\\w+$)|(^[C|D][:][\\\\]$)";
    //    public static final String DATE_REGEX = "^[1|2]\\d{3}[-][0-1]?[0-9][-][0|1|2|3]?[0-9]$";
    public static final String MONTH_REGEX = "^[0-1]?[0-9]$";
    public static final String DAY_REGEX = "^[0|1|2|3]?[0-9]$";
    public static final String YEAR_REGEX = "^[1|2]\\d{3}$";

    public RevenueManager() {
        listBills = readDataBillsToFile(LINK_SAVE_OBJECT_BILL);
        drinksList = readDataDrinksFromFile(LINK_SAVE_OBJECT_DRINKS);
        accountsList = (new AccountManager()).readDataAccountToFile(LINK_SAVE_OBJECT_ACCOUNT);
    }

    public boolean isLink(String link) {
        return Pattern.compile(LINK_REGEX).matcher(link).matches();
    }

    public boolean isFormatDay(String day) {
        return Pattern.compile(DAY_REGEX).matcher(day).matches();
    }

    public boolean isFormatMonth(String month) {
        return Pattern.compile(MONTH_REGEX).matcher(month).matches();
    }

    public boolean isFormatYear(String year) {
        return Pattern.compile(YEAR_REGEX).matcher(year).matches();
    }

    public boolean isDate(String dateOfBirth) {
        String[] arr = dateOfBirth.split("-");
        int day = Integer.parseInt(arr[2]);
        int month = Integer.parseInt(arr[1]);
        int year = Integer.parseInt(arr[0]);
        if (!isYearLimit(year)) return false;
        if (!isMonth(month)) return false;
        boolean isDay = false;
        switch (month) {
            case 4:
            case 6:
            case 9:
            case 11:
                if (day > 0 && day <= 30) isDay = true;
                break;
            case 2:
                if (isLeapYear(year)) {
                    if (day > 0 && day <= 29) isDay = true;
                } else if (day > 0 && day <= 28) isDay = true;
                break;
            default:
                if (day > 0 && day <= 31) isDay = true;
        }
        return isDay;
    }

    private boolean isYearLimit(int year) {      //giơi
        if (year >= 1900 && year < 2022) return true;
        return false;
    }

    private boolean isMonth(int month) {
        if (month >= 1 && month <= 12) return true;
        return false;
    }

    private boolean isLeapYear(int year) {
        if (year % 400 == 0 || (year % 4 == 0 && year % 100 != 0)) return true;
        return false;
    }

    public void displayListStaff() {
        SellManager sellManager = new SellManager();
        sellManager.displayStaff();
    }

    public void displayListUserName() {
        int count = 0;
        System.out.printf("%-9s%s\n", "STT", "USERNAME");
        for (Account account : accountsList) {
            count++;
            System.out.printf("%-9s%s\n", String.valueOf(count), account.getUserName());
        }
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
        do {
            System.out.println("--------------------------------------------------");
            System.out.println("|               QUẢN LÝ DOANH THU                 |");
            System.out.println("--------------------------------------------------");
            System.out.println("|  1. Doanh thu theo ngày                         |");
            System.out.println("|  2. Doanh thu theo tháng                        |");
            System.out.println("|  3. Doanh thu trong khoảng thời gian tùy chọn   |");
            System.out.println("|  4. Doanh thu theo người quản lý bán hàng       |");
            System.out.println("|  5. Doanh thu theo nhân viên phục vụ            |");
            System.out.println("|                                   0. Quay lại   |");
            System.out.println("---------------------------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    revenueByDay();
                    break;
                case '2':
                    revenueByMonth();
                    break;
                case '3':
                    revenueByDayToDay(listBills);
                    break;
                case '4':
                    revenueByUserName();
                    break;
                case '5':
                    revenueByStaff();
                    break;
                case '0':
                    Menu.menuWorkWithAdmin();
            }
        } while (choice != '0');
    }


    public void revenueByDay() {
        String date, day, month, year;
        do {
            System.out.println("Nhập ngày tháng năm muốn xem ! ");
            do {
                System.out.print("Ngày : ");
                day = input.nextLine();
            } while (!isFormatDay(day));
            do {
                System.out.print("Tháng: ");
                month = input.nextLine();
            } while (!isFormatMonth(month));
            do {
                System.out.print("Năm :");
                year = input.nextLine();
            } while (!isFormatYear(year));
            date = year + "-" + month + "-" + day;
            if (!isDate(date)) {
                System.out.println("Không phải ngày trong giới hạn hoặc ngày đó không tồn tại ! Hãy nhập lại");
            }
        } while (!isDate(date));
        ArrayList<Bill> listBillSearch = new ArrayList<>();
        for (Bill bill : listBills) {
            if (bill.getDateBill().contains(date)) {
                listBillSearch.add(bill);
            }
        }
        showListBillsSearch(listBillSearch);
        System.out.println();
        System.out.println("Tổng tiền thu nhập trong ngày " + date + " là :" + decimalFormat.format(totalByListBills(listBillSearch)) + " VND");
        exportDataRevenueToCsv(listBillSearch);

    }

    public void revenueByMonth() {
        String month, year, date;
        System.out.println("Nhập tháng năm muốn xem ! ");
        do {
            System.out.print("Tháng: ");
            month = input.nextLine();
            if(isFormatMonth(month)){
                System.out.println("Không phải định dạng tháng !");
            }else if(!isMonth(Integer.parseInt(month))){
                System.out.println("Không phải là tháng trong năm !");
            }
        } while (!isFormatMonth(month));
        do {
            System.out.print("Năm :");
            year = input.nextLine();
        } while (!isFormatYear(year));
        date = year + "-" + month + "-";
        ArrayList<Bill> listBillSearch = new ArrayList<>();
        for (Bill bill : listBills) {
            if (bill.getDateBill().contains(date)) {
                listBillSearch.add(bill);
            }
        }
        showListBillsSearch(listBillSearch);
        System.out.println();
        System.out.println("Tổng tiền thu nhập trong tháng " + month + "-" + year + " là :" + decimalFormat.format(totalByListBills(listBillSearch)) + " VND");
        exportDataRevenueToCsv(listBillSearch);

    }

    public void revenueByDayToDay(ArrayList<Bill> listBills) {
        String dayBegin, monthBegin, yearBegin, dayEnd, monthEnd, yearEnd, dateBegin, dateEnd;
        do {
            do {
                System.out.println("Nhập ngày tháng năm bắt đầu tìm kiếm ! ");
                do {
                    System.out.print("Ngày : ");
                    dayBegin = input.nextLine();
                } while (!isFormatDay(dayBegin));
                do {
                    System.out.print("Tháng: ");
                    monthBegin = input.nextLine();
                } while (!isFormatMonth(monthBegin));
                do {
                    System.out.print("Năm :");
                    yearBegin = input.nextLine();
                } while (!isFormatYear(yearBegin));
                dateBegin = yearBegin + "-" + monthBegin + "-" + dayBegin;
                if (!isDate(dateBegin)) {
                    System.out.println("Không phải ngày trong giới hạn hoặc ngày đó không tồn tại ! Hãy nhập lại");
                }
            } while (!isDate(dateBegin));

            do {
                System.out.println("Nhập ngày tháng năm kết thúc tìm kiếm ! ");

                do {
                    System.out.print("Ngày : ");
                    dayEnd = input.nextLine();
                } while (!isFormatDay(dayEnd));
                do {
                    System.out.print("Tháng: ");
                    monthEnd = input.nextLine();
                } while (!isFormatMonth(monthEnd));
                do {
                    System.out.print("Năm :");
                    yearEnd = input.nextLine();
                } while (!isFormatYear(yearEnd));
                dateEnd = yearEnd + "-" + monthEnd + "-" + dayEnd;
                if (!isDate(dateEnd)) {
                    System.out.println("Không phải ngày trong giới hạn hoặc ngày đó không tồn tại ! Hãy nhập lại");
                }
            } while (!isDate(dateEnd));
            if (dateBegin.compareTo(dateEnd) > 0) {
                System.out.println("Ngày kết thúc phải lớn hơn ngày bắt đầu !");
            }
        } while (dateBegin.compareTo(dateEnd) > 0);
        ArrayList<Bill> listBillSearch = new ArrayList<>();
        for(Bill bill : listBills){
            if(bill.getDateBill().compareTo(dateBegin)>=0 &&bill.getDateBill().compareTo(dateEnd)<=0){
                listBillSearch.add(bill);
            }
        }
        showListBillsSearch(listBillSearch);
        System.out.println();
        System.out.println("Tổng tiền thu nhập từ ngày " + dateBegin + " đến ngày " + dateEnd + " là : " + decimalFormat.format(totalByListBills(listBillSearch)) + " VND");
        exportDataRevenueToCsv(listBillSearch);
    }

    public void revenueByUserName() {
        displayListUserName();
        System.out.print("Nhập tên username cần kiểm tra doanh thu : ");
        String username = input.nextLine();
        ArrayList<Bill> listBillsSearch = new ArrayList<>();
        for (Bill bill : listBills) {
            if (bill.getUserName().equals(username)) {
                listBillsSearch.add(bill);
            }
        }
        if (listBillsSearch.size() == 0) {
            System.out.println("Không tìm thấy lịch sử buôn bán của username " + username);
            System.out.println();
            menuDisplayRevenue();
        } else {
            System.out.println("Nhập khoảng thời gian tìm kiếm ?");
            revenueByDayToDay(listBillsSearch);
        }
    }

    public void revenueByStaff() {
        displayListStaff();
        System.out.print("Nhập id nhân viên cần kiểm tra doanh thu : ");
        String idStaff = input.nextLine();
        ArrayList<Bill> listBillsSearch = new ArrayList<>();
        for (Bill bill : listBills) {
            if (bill.getIdStaffServing().equals(idStaff)) {
                listBillsSearch.add(bill);
            }
        }
        if (listBillsSearch.size() == 0) {
            System.out.println("Không tìm thấy lịch sử phục vụ của nhân viên có id " + idStaff);
            System.out.println();
            menuDisplayRevenue();
        } else {
            System.out.println("Nhập khoảng thời gian tìm kiếm ?");
            revenueByDayToDay(listBillsSearch);
        }
    }

    public void showListBillsSearch(ArrayList<Bill> listBills) {
        System.out.printf("%-5s%-20s%-20s%-20s%-20s%-30s%-30s%s\n", "STT", "DATE", "USERNAME", "ID ORDER", "ID NV PHỤC VỤ", "TIME IN", "TIME OUT", "TIỀN");
        System.out.println();
        int count = 0;
        String date, username, idOrder, idStaffSer, timeIn, timeOut, moneyTotal;
        for (Bill bill : listBills) {
            count++;
            date = bill.getDateBill();
            username = bill.getUserName();
            idOrder = bill.getIdOrder();
            idStaffSer = bill.getIdStaffServing();
            timeIn = bill.getTimeIn();
            timeOut = bill.getTimeOut();
            moneyTotal = String.valueOf(bill.getTotalMoney());
            System.out.printf("%-5s%-20s%-20s%-20s%-20s%-30s%-30s%s\n", String.valueOf(count), date, username, idOrder, idStaffSer, timeIn, timeOut, moneyTotal);
        }
        System.out.println();
    }

    public void exportDataRevenueToCsv(ArrayList<Bill> listBills) {
        char choice = ' ';
        do {
            System.out.println("Bạn muốn xuất file để thống kê chi tiêt !");
            System.out.println("---------------------");
            System.out.println("|   1. Xuất file    |");
            System.out.println("|   0.Quay lại      |");
            System.out.println("----------------------");
            System.out.println("Chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    String link = "";
                    String nameFileCsv = "";
                    String linkFull = "";
                    boolean isChoice = true;
                    do {
                        isChoice = false;
                        do {
                            System.out.print("Nhập đường dẫn file xuất ra : ");
                            link = input.nextLine();
                            if (!isLink(link)) {
                                System.out.println("Định dạng đường dẫn không đúng ! ví dụ đường dẫn file : D:\\nameFoder\\....");
                            }
                        }
                        while (!isLink(link));

                        System.out.print("Nhập tên file : ");
                        nameFileCsv = input.nextLine();
                        linkFull = link + "\\" + nameFileCsv + ".csv";

                        //tao file dan
                        String[] arr = link.split("\\\\");
                        int i = 0;
                        String l = "";
                        while (i < arr.length) {
                            l = l + arr[i] + "\\";
                            File dir = new File(l);
                            dir.mkdir();
                            i++;
                        }

                        File file = new File(linkFull);
                        if (!file.exists()) {
                            writeDataOfBillsFromFileFormatToCsv(linkFull, listBills);
                            System.out.println("Đã xuất file thành công đến đường dẫn : " + linkFull);
                            System.out.println();
                            menuDisplayRevenue();
                        } else {
                            System.out.println("File đã tồn tại ! bạn có muốn ghi đè !");
                            char press = ' ';
                            boolean isPress = true;
                            do {
                                System.out.print("Nhấn 'Y' để thực hiện,  'N' để thay đổi đường dẫn, 'R' để quay lại menu  ");
                                try {
                                    press = input.nextLine().charAt(0);
                                } catch (Exception e) {
                                    press = ' ';
                                }
                                switch (press) {
                                    case 'y':
                                    case 'Y':
                                        writeDataOfBillsFromFileFormatToCsv(linkFull, listBills);
                                        System.out.println("Đã xuất file thành công đến đường dẫn : " + linkFull);
                                        isChoice = false;
                                        isPress = false;
                                        menuDisplayRevenue();
                                        break;
                                    case 'n':
                                    case 'N':
                                        exportDataRevenueToCsv(listBills);
                                        isPress = false;
                                        break;
                                    case 'R':
                                    case 'r':
                                        isChoice = false;
                                        isPress = false;
                                        menuDisplayRevenue();
                                    default:
                                }
                            } while (isPress);
                        }
                    } while (isChoice);
                    System.out.println();
                    break;
                case '0':
                    menuDisplayRevenue();
                    break;
                default:
            }
        } while (choice != '0');
    }

    public long totalByListBills(ArrayList<Bill> listBills) {
        long totalMoneyListBills = 0;
        for (Bill bill : listBills) {
            totalMoneyListBills += bill.getTotalMoney();
        }
        return totalMoneyListBills;
    }

    public void displayRevenueOfUserNameInToDay(String username) {
        String toDay = LocalDate.now().toString();
        ArrayList<Bill> listBillsInToDay = new ArrayList<>();
        for (Bill bill : listBills) {
            if (bill.getDateBill().equals(toDay)) {
                listBillsInToDay.add(bill);
            }
        }
        ArrayList<Bill> listBillsOfUser = new ArrayList<>();
        for (Bill bill : listBillsInToDay) {
            if (bill.getUserName().equals(username)) {
                listBillsOfUser.add(bill);
            }
        }
        showListBillsSearch(listBillsOfUser);
        System.out.println();
        System.out.println("Doanh thu của username " + username + " trong ngày hôm nay " + toDay + " là :" + decimalFormat.format(totalByListBills(listBillsOfUser)) + " VND");
        System.out.println();
        Menu.menuWorkWithGuest();
    }

}
