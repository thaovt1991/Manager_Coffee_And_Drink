package precentation;

import model.Account;
import service.AccountManager;
import service.DrinksManager;
import service.SellManager;
import service.StaffManager;


import java.util.ArrayList;
import java.util.Scanner;

public class Menu {

    public static final String USERNAME_DEFAULT = "admin";
    public static final String PASSWORD_DEFAULT = "Admin123";
    public static Scanner input = new Scanner(System.in);
    public static final String LINK_SAVE_OBJECT_ACCOUNT = "D:\\Manager_Coffee_And_Drink\\src\\data\\list_account.txt";
    public static AccountManager accountManager = new AccountManager();
    public static StaffManager staffManager = new StaffManager();
    public static DrinksManager drinksManager = new DrinksManager();
    public static SellManager sellManager = new SellManager();
    public static ArrayList<Account> listAccount;
    public static String username, password, decentralization, timelogin, timeOut;

    public static boolean isUserName(String username) {
        accountManager = new AccountManager();
        listAccount = accountManager.readDataAccountToFile(LINK_SAVE_OBJECT_ACCOUNT);
        if (username.equals(USERNAME_DEFAULT)) return true;
        for (Account account : listAccount) {
            if (account.getUserName().equals(username)) return true;
        }
        return false;
    }

    public static void updateData() {
        drinksManager = new DrinksManager();
        accountManager = new AccountManager();
        staffManager = new StaffManager();
        sellManager = new SellManager();
        listAccount = (new AccountManager()).readDataAccountToFile(LINK_SAVE_OBJECT_ACCOUNT);
    }

    public static boolean isTruePass(String username, String pass) {
        updateData();
        if (username.equals(USERNAME_DEFAULT) && pass.equals(PASSWORD_DEFAULT)) return true;
        for (Account account : listAccount) {
            if (account.getUserName().equals(username) && account.getPassword().equals(pass)) return true;
        }
        return false;
    }

    public static String decentralizationAccount(String username) {
        updateData();
        String decen = "Admin";
        for (Account account : listAccount) {
            if (account.getUserName().equals(username)) {
                decen = account.getDecentralization();
                break;
            }
        }
        decentralization = decen;
        return decentralization;
    }

    public static void menuWorkWithAdmin() {
        updateData();
        boolean isTrue = true;
        if (!username.equals(USERNAME_DEFAULT)) {
            String ownerId = "";
            for (Account account : listAccount) {
                if (account.getUserName().equals(username)) {
                    ownerId = account.getOwnerId();
                    break;
                }
            }
            if (staffManager.isIdHaveInList(ownerId)) {
                isTrue = true;
            } else isTrue = false;
        }

        if (isTrue) {
            System.out.println("User name : " + username + "- Time login : " + timelogin);
            System.out.println();
            char choice = ' ';
            do {
                System.out.println("--------------------------QUẢN LÝ - ADMIN ------------------------------");
                System.out.println("| 1. Quản lý thức uống                                                  |");
                System.out.println("| 2. Quản lý bán hàng                                                   |");
                System.out.println("| 3. Quản lý nhân viên                                                  |");
                System.out.println("| 4. Quản lý account                                                    |");
                System.out.println("| 5. Quản lý doanh thu                                                  |");
                System.out.println("| 6. Quản lý hệ thống                                                   |");
                System.out.println("| 7. Đổi password                                                       |");
                System.out.println("| 0. Đăng xuất                                                          |");
                System.out.println("-------------------------------------------------------------------------");
                System.out.println();
                System.out.print("Chọn : ");
                try {
                    choice = input.nextLine().charAt(0);
                } catch (Exception e) {
                    choice = ' ';
                }

                switch (choice) {
                    case '1':

                        drinksManager.menuDrinksManager();
                        break;
                    case '2':
                        sellManager.menuSellDrinksManager();
                        break;
                    case '3':
                        staffManager.menuStaffManager();
                        break;
                    case '4':
                        accountManager.menuAccountManager();
                        break;
                    case '5':
                        //revenueManger.menuRevenueManager()
                        break;
                    case '6':
                        //systemManager.menuManager() ;
                        break;
                    case '7':
                        changePassword();
                    case '0':
                        timeOut = String.valueOf(java.time.LocalTime.now()) + " " + String.valueOf(java.time.LocalDate.now());
                        menuLogIn();
                }

            } while (choice != '0');
        } else System.out.println("Tài khoản hiện tại dang bị khóa, liên hệ người quản lý để được kiểm tra lại");
    }

    public static void menuWorkWithGuest() {
        updateData();
        String ownerId = "";
        for (Account account : listAccount) {
            if (account.getUserName().equals(username)) {
                ownerId = account.getOwnerId();
                break;
            }
        }
        if (staffManager.isIdHaveInList(ownerId)) {
            System.out.println("User name : " + username + "- Time login : " + timelogin);
            System.out.println();
            char choice = ' ';
            do {
                System.out.println("--------------------------QUẢN LÝ - GUEST ------------------------------");
                System.out.println("| 1. Quản lý bán hàng                                                   |");
                System.out.println("| 2. Kiểm tra doanh thu cá nhân                                         |");
                System.out.println("| 3. Đổi password                                                       |");
                System.out.println("| 0. Đăng xuất                                                          |");
                System.out.println("-------------------------------------------------------------------------");
                System.out.println();
                System.out.print("Chọn : ");
                try {
                    choice = input.nextLine().charAt(0);
                } catch (Exception e) {
                    choice = ' ';
                }
                switch (choice) {
                    case '1':
                        sellManager.menuSellDrinksManager();
                        break;
                    case '2':
                        //displayRevenue();
                        break;
                    case '3':
                       changePassword();
                        break;
                    case '0':
                        menuLogIn();
                }
            } while (choice != '0');
        } else System.out.println("Tài khoản hiện tại dang bị khóa, liên hệ người quản lý để được kiểm tra lại");

    }



    public static void changePassword() {

        if (username.equals(USERNAME_DEFAULT)) {
            System.out.println("Đây là tài khoản 'Admin' mặc định của hệ thống, hãy liên hệ với bên cung cấp phần mềm để tiến hành đổi password !");
            menuWorkWithAdmin();
        } else {
            updateData();
            String passOld = "";
            String passNew1 = "";
            String passNew2 = "";
            char choice = ' ';
            boolean isChoice = false;
            do {
                System.out.print("Bạn có muốn đổi password ? Nhấn 'Y' để đồng ý, nhấn 'N' để quay trờ về menu !");
                try {
                    choice = input.nextLine().charAt(0);
                } catch (Exception e) {
                    choice = ' ';
                }
                switch (choice) {
                    case 'y':
                    case 'Y':
                        do {
                            System.out.print("Nhập password cũ : ");
                            passOld = input.nextLine();
                            System.out.print("Nhập password mới lần 1:");
                            passNew1 = input.nextLine();
                            System.out.print("Nhập password mới lần 2 :");
                            passNew2 = input.nextLine();
                            if (!passOld.equals(password)) {
                                System.out.println("Password cũ không đúng !");
                            } else {
                                if (!accountManager.isFormatPassword(passNew1)) {
                                    System.out.println("password có ít nhất 8 kí tự gồm chữ và số, bắt đầu bằng chữ và có ít nhất 1 chữ số");
                                } else if (!passNew1.equals(passNew2)) {
                                    System.out.println("Password lần 2 không trùng khớp với lần 1 !");
                                }
                            }
                        } while (!passOld.equals(password) || !accountManager.isFormatPassword(passNew1) || !passNew1.equals(passNew2));
                        password = passNew1;
                        for (Account account : listAccount) {
                            if (account.getUserName().equals(username)) {
                                account.setPassword(passNew1);
                                System.out.println("Thay đổi password thành công !");
                                accountManager.writeDataAccountToFile(LINK_SAVE_OBJECT_ACCOUNT, listAccount);
                                accountManager.writeDataAccountToFileCsv(AccountManager.LINK_SAVE_FORMAT_CSV_ACCOUNT, listAccount);
                                break;
                            }
                        }
                        if (decentralization.equals("Guest")) {
                             menuWorkWithGuest();
                        } else menuWorkWithAdmin();
                        break;
                    case 'n':
                    case 'N':
                        if (decentralization.equals("Guest")) {
                             menuWorkWithGuest();
                        } else menuWorkWithAdmin();
                        break;
                    default:
                        isChoice = true;
                }
            } while (isChoice);
        }
    }

    public static void menuLogIn() {
        updateData();
        char choice = ' ';
        do {
            System.out.println("Chào mừng đến với quán Cofffe Demo ! Hãy đăng nhập để thực hiện phiên làm việc của mình !");
            System.out.println();
            System.out.println("----------------ĐĂNG NHẬP-----------------");
            System.out.println("| 1.  Đăng nhập                           |");
            System.out.println("| 2.  Thoát                               |");
            System.out.println("| 3.  Thông tin nhà hàng                  |");
            System.out.println(" ------------------------------------------");
            System.out.println();
            System.out.print("Chọn :");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    do {
                        System.out.print("Username : ");
                        username = input.nextLine();
                        System.out.print("Password : ");
                        password = input.nextLine();
                        if (!isUserName(username)) {
                            System.out.println("Tên đăng nhập hoặc password không đúng ! Hãy thử lại !");
                        } else if (!isTruePass(username, password)) {
                            System.out.println("Password không đúng ! Hãy thử lại !");
                        }
                    } while (!isTruePass(username, password));

                    timelogin = String.valueOf(java.time.LocalTime.now()) + " " + String.valueOf(java.time.LocalDate.now());
                    if (decentralizationAccount(username).equals("Guest")) {
                        menuWorkWithGuest();
                    } else {
                        menuWorkWithAdmin();
                    }
                    break;
                case '2':
                    System.out.println("Cám ơn bạn đã sử dụng phần mềm của chúng tôi ! Mọi thắc mắc xin liên hệ CodeGymHue để đặt mua hoặc được hướng dẫn sử dụng phần mềm !");
                    System.out.println("Hot-line :  0234 6291 888 ");
                    System.out.println("Email : hue@codegym.vn");
                    System.exit(0);
                    break;
                case '3':
                    System.out.println("Quán Coffe Demo xin kính chào, thông tin quán sẽ update sau ! Cám ơn bạn !");
                    break;
                default:
                    System.out.println();
            }
        } while (choice != '2');
    }


    public static void main(String[] args) {
        menuLogIn();
    }
}