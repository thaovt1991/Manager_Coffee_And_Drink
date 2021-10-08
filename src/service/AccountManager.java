package service;

import model.Account;
import model.Drinks;
import model.Staff;
import sort.sortAccount.*;
import sort.sortDrinks.SortIdDrinksAZ;
import sort.sortDrinks.SortIdDrinksZA;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountManager implements Serializable {

    private ArrayList<Account> accountsList;
    private ArrayList<Staff> staffList;
    static Scanner input = new Scanner(System.in);
    public static final String LINK_SAVE_OBJECT_STAFF = "D:\\Manager_Coffee_And_Drink\\src\\data\\list_staff.txt";
    public static final String LINK_SAVE_OBJECT_ACCOUNT = "D:\\Manager_Coffee_And_Drink\\src\\data\\list_account.txt";
    public static final String LINK_SAVE_FORMAT_CSV_ACCOUNT = "D:\\Manager_Coffee_And_Drink\\out_data\\list_account.csv";
    public static final String DOWN_THE_LINE = "\n";
    public static final String COMMA_DELIMITER = ",";
    public static final String FORMAT_CSV_ACCOUNT = "ID,USERNAME,PASSWORD,DECENTRALIZATION";
    public static final String OWNER_ID_REGEX = "[A-Z]{2}+\\d{6}$";
    public static final String USERNAME_REGEX = "^[a-z][a-z0-9_]{6,16}$"; //user name 6-16 ki tu bat dau bang chu ;
    public static final String PASSWORD_REGEX = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"; // có ít nhất 8 kí tự chu va so khong có ki tu dat biet
    public static final String LINK_REGEX = "(^([C|D][:])\\\\(?:[\\w]+\\\\)*\\w+$)|(^[C|D][:][\\\\]$)";

    public AccountManager() {
        this.accountsList = readDataAccountToFile(LINK_SAVE_OBJECT_ACCOUNT);
        this.staffList = readDataStaffFromFile(LINK_SAVE_OBJECT_STAFF);
    }

    public boolean isEmpty() {
        if (accountsList.isEmpty()) return true;
        return false;
    }

    public boolean isOwnerIdHaveInListAccount(String id) {
        for (Account account : accountsList) {
            if (account.getOwnerId().equals(id)) return true;
        }
        return false;
    }

    public boolean isOwerIdHaveInListStaff(String id) {
        for (Staff staff : staffList) {
            if (staff.getIdStaff().equals(id)) return true;
        }
        return false;
    }

    public boolean isUserNameHaveInListAccount(String userName) {
        for (Account account : accountsList) {
            if (account.getUserName().equals(userName)) return true;
        }
        return false;
    }


    public boolean isFormatPassword(String pass) {
        return Pattern.compile(PASSWORD_REGEX).matcher(pass).matches();
    }

    public boolean isFormatUserName(String userName) {
        return Pattern.compile(USERNAME_REGEX).matcher(userName).matches();
    }

    public boolean isFormatOwnerId(String owberId) {
        return Pattern.compile(OWNER_ID_REGEX).matcher(owberId).matches();
    }

    public ArrayList<Account> readDataAccountToFile(String path) {
        ArrayList<Account> listAccounts = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listAccounts = (ArrayList<Account>) ois.readObject();
            ois.close();
            fis.close();
        } catch (Exception e) {
            System.out.println("File chưa tồn tại, hãy nhập dữ liệu và tạo ra nó !");
        }
        return listAccounts;
    }

    public void writeDataAccountToFile(String path, ArrayList<Account> accountsList) {
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

    public void writeDataAccountToFileCsv(String path, ArrayList<Account> accountsList) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            writer.append(FORMAT_CSV_ACCOUNT);
            writer.append(DOWN_THE_LINE);
            for (Account account : accountsList) {
                writer.append(account.getOwnerId());
                writer.append(COMMA_DELIMITER);
                writer.append(account.getUserName());
                writer.append(COMMA_DELIMITER);
                writer.append(account.getPassword());
                writer.append(COMMA_DELIMITER);
                writer.append(account.getDecentralization());
                writer.append(DOWN_THE_LINE);
            }
            writer.close();
        } catch (Exception e) {
            System.out.println();
        }
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

    public void displayAllListStaff() {
        StaffManager staffManager = new StaffManager();
        staffManager.displayAllStaff();
    }

    //tao tai khoan
    public void createAccount() {
        displayAllListStaff();
        String ownerID = "";
        boolean isChoice = true;
        do {
            System.out.print("Hãy nhập ID của nhân viên để khởi tạo : ");
            ownerID = input.nextLine();
            if (!isFormatOwnerId(ownerID)) {
                System.out.println("Không phải định dạng id nhân viên ");
            } else if (!isOwerIdHaveInListStaff(ownerID)) {
                System.out.println("Id không có nhân viên nào có id '" + ownerID + "'");
            } else if (isOwnerIdHaveInListAccount(ownerID)) {
                System.out.println("Id đã được đăng kí tài khoản ");
            } else isChoice = false;
        } while (isChoice);

        String userName = "" ;
        do{
            System.out.print("Nhập username : ");
            userName = input.nextLine();
            if(!isFormatUserName(userName)){
                System.out.println("Username gồm số và chữ thường, phải bắt đầu là một chữ và có 6-16 kí tự !");
            }else if (isUserNameHaveInListAccount(userName)){
                System.out.println("username đã được sử dụng !");
            }
        }while (isUserNameHaveInListAccount(userName) || !isFormatUserName(userName));

        String pass = "" ;
        String pass2 = "";
        do {
            System.out.print("Nhập pass lần 1 : ");
            pass = input.nextLine();
            if(!isFormatPassword(pass)){
                System.out.println("password có ít nhất 8 kí tự gồm chữ và số, bắt đầu bằng chữ và có ít nhất 1 chữ số");
            }else {
                System.out.print("Nhập pass lần 2 : ");
                pass2= input.nextLine();
                if(!pass2.equals(pass)){
                    System.out.println("Hai lần nhập password phải giống nhau !");
                }
            }
        }while (!isFormatPassword(pass)|| !pass2.equals(pass));

        String decentralization = "";
        char press = ' ';
        boolean isPress = true;
        System.out.println("Phân quyền cho account ?");
        do {
            System.out.print("Nhấn 'A' nếu người dùng là quản lý , nhấn 'G' nếu người dùng là nhân viên thông thường ?  ");
            try {
                press = input.nextLine().charAt(0);
            } catch (Exception e) {
                press = ' ';
            }
            switch (press) {
                case 'a':
                case 'A':
                    decentralization = "Admin";
                    isPress = false;
                    break;
                case 'G':
                case 'g':
                    decentralization = "Guest";
                    isPress = false;
                    break;
                default:
            }
        } while (isPress);

        Account account = new Account(ownerID,userName,pass,decentralization) ;
        System.out.println("Tài khoản bạn vừa tạo có :");
        System.out.println("Là tài khoản của người dùng có  ID = " + ownerID );
        System.out.println("Tài khoản đăng nhập có username =  "+userName);
        System.out.println("Tài khoản đăng nhập có password = "+ pass);
        System.out.println("Tài khoản được phân quyền là '"+decentralization+"'");
        System.out.println("Bạn có muốn lưu tài khoản vào dữ liệu quản lý !");
        char choose = ' ';
        boolean isChoose = true;
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
                    accountsList.add(account);
                    writeDataAccountToFile(LINK_SAVE_OBJECT_ACCOUNT, accountsList);
                    writeDataAccountToFileCsv(LINK_SAVE_FORMAT_CSV_ACCOUNT, accountsList);
                    isChoose = false;
                    break;
                }
                case 'n':
                case 'N':
                    menuAccountManager();
                    isChoose = false;
                    break;
                default:
                    isChoose = true;
            }
        } while (isChoose);
    }

    public void editAccount(){
        char choice = ' ';
        do {
            System.out.println("-----------------THAY ĐỔI THÔNG TIN TÀI KHOẢN-------------------");
            System.out.println("|  1. Thay đổi theo ID của người sử dụng                         |");
            System.out.println("|  2. Thay đổi theo username của tài khoản                       |");
            System.out.println("|  0. Quay lại                                                   |");
            System.out.println("----------------------------------------------------------------");
            System.out.println();
            System.out.print("Lựa chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    editAccountById();
                    break;
                case '2':
                    editAccountByUserName();
                    break;
                case '0':
                    menuAccountManager();
                    break;
                default:
                    System.out.println("Lựa chọn lại !");
            }
        } while (choice != '0');
    }

    public void editAccountById(){
        displayFullAccount();
        String id = "";
        System.out.print("Nhập id nhân viên của tài khoản cần thay đổi thông tin : ");
        id = input.nextLine();
        if (!isFormatOwnerId(id)) {
            System.out.println("Định dạng ID chưa hợp lệ ! ( ví dụ id đúng : NV201294)");
            editAccount();
        } else {
            if (!isOwnerIdHaveInListAccount(id)) {
                System.out.println("Không có tài khoản đăng kí cho nhân viên nào có id '"+id+"' !");
                editAccount();
            } else {
                for (Account account : accountsList) {
                    if (account.getOwnerId().equals(id)) {
                        System.out.println("Tài khoản tìm được là : ");
                        displayOneAccount(account);
                        System.out.println("Có phải bạn muốn thay đổi thông tin tài khoản này !");

                        char press = ' ';
                        boolean isChoice = true;
                        do {
                            System.out.println("Nhấn 'Y' để tiếp tục, nhấn 'N' để tìm kiếm lại ");
                            try {
                                press = input.nextLine().charAt(0);
                            } catch (Exception e) {
                                press = ' ';
                            }
                            switch (press) {
                                case 'Y':
                                case 'y':
                                    editOptionAccount(account);
                                    isChoice = false;
                                    break;
                                case 'n':
                                case 'N':
                                    editAccount();
                                    isChoice = false;
                                    break;
                                default:
                                    isChoice = true;
                            }
                        } while (isChoice);
                        break;
                    }
                }
            }
        }
    }

    public void editAccountByUserName(){
        displayFullAccount();
        String username = "";
        do {
            System.out.print("Nhập username của tài khoản cần sửa chữa thông tin : ");
            username = input.nextLine();
            if (!isFormatUserName(username)) {
                System.out.println("Username gồm số và chữ thường, phải bắt đầu là một chữ và có 6-16 kí tự !");
                editAccount();
            } else {
                if (!isUserNameHaveInListAccount(username)) {
                    System.out.println("username này chưa được đăng kí !");
                    editAccount();
                } else {
                    for (Account account : accountsList) {
                        if (account.getUserName().equals(username)) {
                            System.out.println("Tài khoản cần tìm là : ");
                            displayOneAccount(account);
                            System.out.println("Có phải bạn muốn thông tin tài khoản này !");

                            char press = ' ';
                            boolean isChoice = true;
                            do {
                                System.out.println("Nhấn 'Y' để tiếp tục, nhấn 'N' để tìm kiếm lại ");
                                try {
                                    press = input.nextLine().charAt(0);
                                } catch (Exception e) {
                                    press = ' ';
                                }
                                switch (press) {
                                    case 'Y':
                                    case 'y':
                                        editOptionAccount(account);
                                        isChoice = false;
                                        break;

                                    case 'n':
                                    case 'N':
                                        editAccount();
                                        isChoice = false;
                                        break;
                                    default:
                                        isChoice = true;
                                }
                            } while (isChoice);
                            break;
                        }
                    }
                }

            }
        }
        while (!isUserNameHaveInListAccount(username) || !isFormatUserName(username));
    }

    public void editOptionAccount(Account account){
        char choice = ' ';
        boolean isChoice = true;
        do {
            System.out.println("----------THAY ĐỔI THÔNG TIN TÀI KHOẢN--------------------");
            System.out.println("|   1. Thay đổi ID người dùng                            |");
            System.out.println("|   2. Thay đổi username                                 |");
            System.out.println("|   3. Thay đổi password                                 |");
            System.out.println("|   4. Thay đổi quyền truy cập                           |");
            System.out.println("|   6. Thay đổi toàn bộ thông tin tài khoản              |");
            System.out.println("|   7. Thoát và hủy thay đổi                             |");
            System.out.println("|   8. Thoát và lưu thay đổi                             |");
            System.out.println("----------------------------------------------------------");
            System.out.println();
            System.out.println("Chọn :");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }

            switch (choice) {
                case '1':
                    System.out.println("----------Thay đổi ID người sử dụng của tài khoản ----------");
                    editOwnerId(account);
                    break;
                case '2':
                    System.out.println("---------Thay đổi username của tài khoản ----------");
                    editUserNameAccount(account);
                    break;
                case '3':
                    System.out.println("----------Thay đổi password --------------");
                    editPasswordAccount(account) ;
                    break;
                case '4':
                    System.out.println("----------Thay đổi quyền truy cập -----------");
                    editDecentralizationAccount(account);
                    break;
                case '6':
                    System.out.println("----------Thay đổi toàn bộ thông tin tài khoản--------");
                    editOwnerId(account);
                    editUserNameAccount(account);
                    editPasswordAccount(account) ;
                    editDecentralizationAccount(account);
                    break;
                case '7':
                    accountsList = readDataAccountToFile(LINK_SAVE_OBJECT_ACCOUNT);
                    editAccount();
                    isChoice = false;
                    break;
                case '8':
                    writeDataAccountToFile(LINK_SAVE_OBJECT_ACCOUNT, accountsList);
                    writeDataAccountToFile(LINK_SAVE_FORMAT_CSV_ACCOUNT, accountsList);
                    System.out.println("Danh sách tài khoản sau khi sửa :");
                    displayFullAccount();
                    editAccount();
                    isChoice = false;
                    break;
                default:
                    System.out.println("Hãy chọn theo yêu cầu của menu ở trên !");
            }
        } while (isChoice);
    }

    public void editOwnerId(Account account){
        String ownerID = "";
        boolean isChoice = true;
        do {
            System.out.print("Hãy nhập id người sẽ sử dụng tài khoản này : ");
            ownerID = input.nextLine();
            if (!isFormatOwnerId(ownerID)) {
                System.out.println("Không phải định dạng id nhân viên ");
            } else if (!isOwerIdHaveInListStaff(ownerID)) {
                System.out.println("Id không có nhân viên nào có id '" + ownerID + "'");
            } else if (isOwnerIdHaveInListAccount(ownerID)) {
                System.out.println("Id đã được đăng kí tài khoản ");
            } else isChoice = false;
        } while (isChoice);
        account.setOwnerId(ownerID);
    }

    public void editUserNameAccount(Account account){
        String userName = "" ;
        do{
            System.out.print("Nhập username mới : ");
            userName = input.nextLine();
            if(!isFormatUserName(userName)){
                System.out.println("Username gồm số và chữ thường, phải bắt đầu là một chữ và có 6-16 kí tự !");
            }else if (isUserNameHaveInListAccount(userName)){
                System.out.println("username đã được sử dụng !");
            }
        }while (isUserNameHaveInListAccount(userName) || !isFormatUserName(userName));
        account.setUserName(userName);
    }

    public void editPasswordAccount(Account account){
        String pass = "" ;
        String pass2 = "";
        do {
            System.out.print("Nhập pass mới lần 1 : ");
            pass = input.nextLine();
            if(!isFormatPassword(pass)){
                System.out.println("password có ít nhất 8 kí tự gồm chữ và số, bắt đầu bằng chữ và có ít nhất 1 chữ số");
            }else {
                System.out.print("Nhập pass mới lần 2 : ");
                pass2= input.nextLine();
                if(!pass2.equals(pass)){
                    System.out.println("Hai lần nhập password phải giống nhau !");
                }
            }
        }while (!isFormatPassword(pass)|| !pass2.equals(pass));
        account.setPassword(pass);
    }

    public void editDecentralizationAccount(Account account){
        String decentralization = "";
        char press = ' ';
        boolean isPress = true;
        System.out.println("Phân quyền lại account ?");
        do {
            System.out.print("Nhấn 'A' nếu người dùng là quản lý , nhấn 'G' nếu người dùng là nhân viên thông thường ?  ");
            try {
                press = input.nextLine().charAt(0);
            } catch (Exception e) {
                press = ' ';
            }
            switch (press) {
                case 'a':
                case 'A':
                    decentralization = "Admin";
                    isPress = false;
                    break;
                case 'G':
                case 'g':
                    decentralization = "Guest";
                    isPress = false;
                    break;
                default:
            }
        } while (isPress);
        account.setDecentralization(decentralization);
    }



    private void displayReturnSearch(int count) {
        System.out.println("Có '" + count + "' tài khoảm được tìm thấy !");
        char press = ' ';
        boolean isChoice = true;
        System.out.println();
        do {
            System.out.print("Nhấn 'R' để quay trở về menu tìm kiếm !");
            try {
                press = input.nextLine().charAt(0);
            } catch (Exception e) {
                press = ' ';
            }
            switch (press) {
                case 'r':
                case 'R': {
                 //   searchAccount();
                    isChoice = false;
                    break;
                }
                default:
                    isChoice = true;
            }
        } while (isChoice);
    }

    public void optionDisplayAccount() {
        char choice = ' ';
        boolean isChoice = true;
        do {
            System.out.println("----------------------LỰA CHỌN HIỂN THỊ------------------------");
            System.out.println("|  1. Hiển thị sắp xếp theo id người sử dụng accont            |");
            System.out.println("|  2. Hiển thị sắp xếp theo username                           |");
            System.out.println("|  3. Hiển thị sắp xếp theo phân quyền                         |");
            System.out.println("|  0. Quay lại menu chính                                      |");
            System.out.println("----------------------------------------------------------------");
            System.out.println();
            System.out.print("Chọn :");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    displayAccountByOwnerId();
                    break;
                case '2':
                    displayAccountByUsername();
                    break;
                case '3':
                    displayAccountByDecentralization();
                    break;
                case '0':
                    menuAccountManager();
                default:
                    System.out.println("Chọn theo menu !");
            }

        } while (isChoice);
    }

    public void displayFullAccount() {
        int count = 0;
        String stt, id, username, pass, decentralization;
        System.out.println();
        System.out.printf("%-3s%-7s%-15s%-25s%-25s%s\n", "", "STT", "ID", "USER NAME", "PASSWORD", "DECENTRALIZATION" );
        for (Account account : accountsList) {
            count++;
            stt = String.valueOf(count);
            id = account.getOwnerId();
            username = account.getUserName();
            pass = account.getPassword();
            decentralization = account.getDecentralization() ;
            System.out.printf("%-3s%-7s%-15s%-25s%-25s%s\n", "", stt, id, username, pass, decentralization);
        }
        System.out.println();
    }

    public void displayOneAccount(Account account) {
        String  id, username, pass, decentralization;
        System.out.println();
        System.out.printf("%-3s%-15s%-25s%-25s%s\n", "", "ID", "USER NAME", "PASSWORD", "DECENTRALIZATION" );
        id = account.getOwnerId();
        username = account.getUserName();
        pass = account.getPassword();
        decentralization = account.getDecentralization() ;
        System.out.printf("%-3s%-15s%-25s%-25s%s\n", "", id, username, pass, decentralization);
    }

    public void displayAccountByOwnerId(){
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("--------------SẮP XẾP THEO ID NGƯỜI DÙNG--------------");
            System.out.println("| 1. Theo thứ tự từ A-Z                               |");
            System.out.println("| 2. Theo thứ tự từ Z-A                               |");
            System.out.println("| 0. Quay lại menu                                    |");
            System.out.println("-------------------------------------------------------");
            System.out.println();
            System.out.print("Lựa chọn :");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    System.out.println("Sắp xếp ID từ A-Z");
                    SortByIdAtoZ sortIdAZ = new SortByIdAtoZ();
                    Collections.sort(accountsList, sortIdAZ);
                    displayFullAccount();
                    accountsList = readDataAccountToFile(LINK_SAVE_OBJECT_ACCOUNT);
                    break;
                case '2':
                    System.out.println("Sắp xếp ID từ Z-A");
                    SortByIdZtoA sortIdZA = new SortByIdZtoA();
                    Collections.sort(accountsList, sortIdZA);
                    displayFullAccount();
                    accountsList = readDataAccountToFile(LINK_SAVE_OBJECT_ACCOUNT);
                    break;
                case '0':
                    optionDisplayAccount();
                    isChoice = false;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (isChoice);
    }

    public void displayAccountByUsername(){
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("----------------SẮP XẾP THEO USERNAME-----------------");
            System.out.println("| 1. Theo thứ tự từ A-Z                               |");
            System.out.println("| 2. Theo thứ tự từ Z-A                               |");
            System.out.println("| 0. Quay lại menu                                    |");
            System.out.println("-------------------------------------------------------");
            System.out.println();
            System.out.print("Lựa chọn :");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    System.out.println("Sắp xếp ID từ A-Z");
                    SortUserNameAtoZ sortUserAZ = new SortUserNameAtoZ();
                    Collections.sort(accountsList, sortUserAZ);
                    displayFullAccount();
                    accountsList = readDataAccountToFile(LINK_SAVE_OBJECT_ACCOUNT);
                    break;
                case '2':
                    System.out.println("Sắp xếp ID từ Z-A");
                    SortUserNameZtoA sortUserZA = new SortUserNameZtoA();
                    Collections.sort(accountsList, sortUserZA);
                    displayFullAccount();
                    accountsList = readDataAccountToFile(LINK_SAVE_OBJECT_ACCOUNT);
                    break;
                case '0':
                    optionDisplayAccount();
                    isChoice = false;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (isChoice);
    }

    public void displayAccountByDecentralization(){
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("----------------HIỂN THỊ THEO PHÂN QUYỀN---------------");
            System.out.println("| 1. Theo thứ tự từ Admin - Guest                     |");
            System.out.println("| 2. Theo thứ tự từ Guest - Admin                     |");
            System.out.println("| 0. Quay lại menu                                    |");
            System.out.println("-------------------------------------------------------");
            System.out.println();
            System.out.print("Lựa chọn :");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    System.out.println("Theo thứ tự từ Admin - Guest ");
                    SortDecentralizationAtoZ sortDecenAZ = new SortDecentralizationAtoZ();
                    Collections.sort(accountsList, sortDecenAZ);
                    displayFullAccount();
                    accountsList = readDataAccountToFile(LINK_SAVE_OBJECT_ACCOUNT);
                    break;
                case '2':
                    System.out.println("Theo thứ tự từ Guest - Admin ");
                    SortDecentralizationZtoA sortDecenZA = new SortDecentralizationZtoA();
                    Collections.sort(accountsList, sortDecenZA);
                    displayFullAccount();
                    accountsList = readDataAccountToFile(LINK_SAVE_OBJECT_ACCOUNT);
                    break;
                case '0':
                    optionDisplayAccount();
                    isChoice = false;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (isChoice);
    }

    public void   searchAccount(){
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("----------------------TÌM KIẾM NHÂN VIÊN----------------------------");
            System.out.println("| 1. Tìm kiếm theo tài khoản theo ID người sử dụng                  |");
            System.out.println("| 2. Tìm kiếm theo username                                         |");
            System.out.println("| 3. Tìm kiếm theo phân quyền                                       |");
            System.out.println("| 0. Quay lại                                                       |");
            System.out.println(" -------------------------------------------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    searchAccountById();
                    break;
                case '2':
                    searchAccountByUserName();
                    break;
                case '3':
                    searchAccountByDecentralization();
                    break;
                case '0':
                    menuAccountManager();
                    isChoice = false;
                    break;
                default:
                    System.out.println("Hãy chọn theo menu tìm kiếm !");
            }

        } while (isChoice);
    }

    public  void searchAccountById(){
        int count = 0;
        String stt, id, username, pass, decentralization;
        System.out.println();
        System.out.print("Nhập ID của nhân viên có tài khoản cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-3s%-7s%-15s%-25s%-25s%s\n", "", "STT", "ID", "USER NAME", "PASSWORD", "DECENTRALIZATION" );
        for (Account account : accountsList) {
            if (account.getOwnerId().toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = account.getOwnerId();
                username = account.getUserName();
                pass = account.getPassword();
                decentralization = account.getDecentralization() ;
                System.out.printf("%-3s%-7s%-15s%-25s%-25s%s\n", "", stt, id, username, pass, decentralization);
            }
        }
        displayReturnSearch(count);
    }

    public  void searchAccountByUserName(){
        int count = 0;
        String stt, id, username, pass, decentralization;
        System.out.println();
        System.out.print("Nhập username của tài khoản cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-3s%-7s%-15s%-25s%-25s%s\n", "", "STT", "ID", "USER NAME", "PASSWORD", "DECENTRALIZATION" );
        for (Account account : accountsList) {
            if (account.getUserName().toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = account.getOwnerId();
                username = account.getUserName();
                pass = account.getPassword();
                decentralization = account.getDecentralization() ;
                System.out.printf("%-3s%-7s%-15s%-25s%-25s%s\n", "", stt, id, username, pass, decentralization);
            }
        }
        displayReturnSearch(count);
    }

    public  void searchAccountByDecentralization(){
        int count = 0;
        String stt, id, username, pass, decentralization;
        System.out.println();
        System.out.print("Nhập quyền hạn của tài khoản cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-3s%-7s%-15s%-25s%-25s%s\n", "", "STT", "ID", "USER NAME", "PASSWORD", "DECENTRALIZATION" );
        for (Account account : accountsList) {
            if (account.getDecentralization().toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = account.getOwnerId();
                username = account.getUserName();
                pass = account.getPassword();
                decentralization = account.getDecentralization() ;
                System.out.printf("%-3s%-7s%-15s%-25s%-25s%s\n", "", stt, id, username, pass, decentralization);
            }
        }
        displayReturnSearch(count);
    }



    public void exportFileAccountToCsv(){
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

            //tao foder
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
                writeDataAccountToFileCsv(linkFull,accountsList);
                System.out.println("Đã xuất file thành công đến đường dẫn : " + linkFull);
                System.out.println();
                menuAccountManager();
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
                            writeDataAccountToFileCsv(linkFull, accountsList);
                            System.out.println("Đã xuất file thành công đến đường dẫn : " + linkFull);
                            isChoice = false;
                            isPress = false;
                            menuAccountManager();
                            break;
                        case 'n':
                        case 'N':
                            exportFileAccountToCsv();
                            isPress = false;
                            break;
                        case 'R':
                        case 'r':
                            isChoice = false;
                            isPress = false;
                            menuAccountManager();
                        default:
                    }
                } while (isPress);
            }

        } while (isChoice);
    }
    private boolean isLink(String link){
        return Pattern.compile(LINK_REGEX).matcher(link).matches();
    }

    public void menuAccountManager() {
        char choice = ' ';
        do {
            System.out.println("------------------QUẢN LÝ TÀI KHOẢN------------------");
            System.out.println("|  1. Tạo tài khoản                                  |");
            System.out.println("|  2. Thay đổi thông tin tài khoản                   |");
            System.out.println("|  3. Xóa tài khoản                                  |");//mai lam
            System.out.println("|  4. Tìm kiếm thông tin tài khoản                   |");
            System.out.println("|  5. Hiển thị danh sách các tài khoản               |");
            System.out.println("|  6. Xem thông tin người sủ dụng tài khoản          |"); //mai lam
            System.out.println("|  7. Xuất file thông tin tài khoản                  |");
            System.out.println("|  0. Quay lại                                       |");
            System.out.println("------------------------------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }

            switch (choice) {
                case '1':
                    createAccount();
                    break;
                case '2':
                     editAccount();
                    break;
                case '3':
                    //  deleteAccount();
                    break;
                case '4':
                     searchAccount();
                    break;
                case '5':
                      optionDisplayAccount();
                    break;
                case '6':
                   //xem thong tin nguoi su dung tk
                    break;
                case '7':
                      exportFileAccountToCsv();
                    break;
                case '0':
                    //menuManager
                    break;
                default:
                    System.out.println("Chọn theo menu !");
            }
        } while (choice != '0');

    }
}
