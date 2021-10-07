package service;

import model.Staff;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaffManager implements Serializable {
    public ArrayList<Staff> staffList;
    static Scanner input = new Scanner(System.in);
    public static final String SAVE_OBJECT_STAFF = "D:\\Manager_Coffee_And_Drink\\src\\data\\list_staff.txt";
    public static final String SAVE_FORMAT_CSV_STAFF = "D:\\Manager_Coffee_And_Drink\\out_data\\list_staff.csv";
    public static final String FORMAT_CSV_STAFF = "ID,HO VA TEN,NGAY SINH,CMND,SO DIEN THOAI,LUONG,THONG TIN KHAC";
    public static final String DOWN_THE_LINE = "\n";
    public static final String COMMA_DELIMITER = ",";
    public static final String ID_STAFF_REGEX = "[A-Z]{2}+\\d{6}$";
    public static final String FULL_NAME_REGEX = "^([AÀẢÃÁẠĂẰẮẲẴẶÂẤẦẨẪẬBCDĐEÈÉẺẼẸÊỀẾỂỄỆFGHIÍÌỈĨỊJKLMNOÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢPQRSTUÙÚỦŨỤƯỪỨỬỮỰVWXYÝỲỶỸỴZ]+[aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]+[ ]*)+$";
    public static final String NUMBER_PHONE_REGEX = "^[0][1-9][0-9]{8,9}$";
    public static final String DATE_OF_BIRTH_REGEX = "^[0|1|2|3]?[0-9][/][0-1]?[0-9][/][1|2]\\d{3}$"; //dinh dang 22/01/1991 ;
    public static final String PAY_REGEX = "^[1-9][0-9]{1,14}[0]{3}$";
    public static final String IDENTITY_CARD_REGEX = "^[1|2][0-9]{8}$";
    public static final String LINK_REGEX = "(^([C|D][:])\\\\(?:[\\w]+\\\\)*\\w+$)|(^[C|D][:][\\\\]$)";


    public StaffManager() {
        staffList = readDataFromFile(SAVE_OBJECT_STAFF);
    }

    public boolean isEmpty() {
        if (staffList.isEmpty()) return true;
        return false;
    }

    public boolean isIdHaveInList(String id) {
        if (isEmpty()) return false;
        if (!isFormatIdStaff(id)) return false;
        for (Staff staff : staffList) {
            if (staff.getIdStaff().equals(id)) return true;
        }
        return false;
    }

    public boolean isIdentityCardHaveInList(String idCar) {
        if (isEmpty()) return false;
        if (!isFormatIdStaff(idCar)) return false;
        for (Staff staff : staffList) {
            if (staff.getIdentityCard().equals(idCar)) return true;
        }
        return false;
    }

    public boolean isFullNameHaveInList(String fullName) {
        if (isEmpty()) return false;
        if (!isFormatFullName(fullName)) return false;
        for (Staff staff : staffList) {
            if (staff.getFullName().equals(fullName)) return true;
        }
        return false;
    }


    public void addStaff() {
        String idStaff = "";
        do {
            System.out.print("Nhập id nhân viên :");
            idStaff = input.nextLine();
            if (!isFormatIdStaff(idStaff)) {
                System.out.println("Định dạng ID chưa hợp lệ ! ( ví dụ id đúng : NV201294)");
            } else if (isIdHaveInList(idStaff)) {
                System.out.println("Id đã được đăng kí , vui lòng nhập lại id mới !");
            }
        } while (isIdHaveInList(idStaff) || !isFormatIdStaff(idStaff));

        String fullName = "";
        do {
            System.out.print("Nhập họ và tên nhân viên : ");
            fullName = input.nextLine();
            if (!isFormatFullName(fullName)) {
                System.out.println("Định dạng tên chưa hợp lệ ! ( ví dụ tên đúng :Nguyen Van A)");
            } else if (isFullNameHaveInList(fullName)) {
                //in doi tuong da co ten ra ;
                System.out.println("'" + fullName + "' đã có trong dãy , bạn có chắc chắn muốn thêm !");
                char choice = ' ';
                boolean isChoice = true;
                do {
                    System.out.print("Nhấn 'Y' để tiếp tục , nhấn 'N' để thay đổi ! : ");
                    try {
                        choice = input.nextLine().charAt(0);
                    } catch (Exception e) {
                        choice = ' ';
                    }
                    switch (choice) {
                        case 'y':
                        case 'Y':
                            isChoice = false;
                            break;
                        case 'n':
                        case 'N':
                            fullName = "";
                            isChoice = false;
                            break;
                        default:
                    }
                } while (isChoice);
            }
        } while (!isFormatFullName(fullName));

        String dateOfBirth = "";
        do {
            System.out.print("Nhập ngày tháng năm sinh của nhân viên, có dạng day/month/year, ví dụ 2/2/1992 : ");
            dateOfBirth = input.nextLine() ;
            if (!isFormatDateOfBirth(dateOfBirth)){
                System.out.println("Không phải định dạng đúng !");
            }else if(!isDatOfBirth(dateOfBirth)){
                System.out.println("Không phải ngày thực tế hoặc đã vượt ra khỏi nằm ngoài độ tuổi cho phép ! ");
            }
        }while (!isDatOfBirth(dateOfBirth));

        String idCar ="";
        do{
            System.out.print("Nhập số CMND của nhân viên : ");
            idCar = input.nextLine() ;
            if(!isFormatIdentityCard(idCar)){
                System.out.println("CMND phải có 9 số và bắt đầu từ  1 hoặc 2 !");
            }else if(isIdentityCardHaveInList(idCar)){
                System.out.println("CMND này đã được đăng kí , hãy kiểm tra lại !");
            }
        }while (!isFormatIdentityCard(idCar) || isIdentityCardHaveInList(idCar) );

        String numberPhone = "";
        do {
            System.out.print("Nhập số điện thoại nhân viên : ");
            numberPhone = input.nextLine();
            if (!isFormatNumberPhone(numberPhone)) {
                System.out.println("Định dạng số điện chưa hợp lệ !  ");
            }
        } while (!isFormatNumberPhone(numberPhone));

        String address = "";
        System.out.print("Nhập địa chỉ nhân viên : ");
        address = input.nextLine() ;

        String strPay = "" ;
        do{
            System.out.print("Nhập tiền lương của nhân viên : ");
            strPay = input.nextLine() ;
            if(!isFormatPay(strPay)) {
                System.out.println("Định dạng tiền lương chưa hợp lý !");
            }
        }while (!isFormatPay(strPay));

        String other = "";
        System.out.print("Nhập các thông tin khác của nhân viên (có thể bỏ trống) !");
        other = input.nextLine();

        Staff staff = new Staff(idStaff,fullName,dateOfBirth,idCar,numberPhone,address,Long.parseLong(strPay),other) ;
        System.out.println();
        System.out.println("Thông tin nhân viên bạn vừa nhập vào là : ");
        System.out.println(staff);
        System.out.println();
        char choice = ' ';
        boolean isChoice = true ;
        do {
            System.out.print("Bạn có muốn lưu vào dữ liệu nhân viên ! Nhấn 'Y' để đồng ý , nhấn 'N' để hủy dữ liệu vừa nhập  ");
            try {
                choice = input.nextLine().charAt(0);
            }catch (Exception e){
                choice = ' ';
            }
            switch (choice){
                case 'y':
                case 'Y':
                    staffList.add(staff);
                    writeToFile(SAVE_OBJECT_STAFF,staffList);
                    writeDataFromFileFormatToCsv(SAVE_FORMAT_CSV_STAFF,staffList);
                    isChoice = false;
                    menuStaffManager();
                    break;
                case 'n':
                case 'N':
                    //menuStaffManager();
                    isChoice = false ;
                    break;
                default:
            }
        }while (isChoice) ;

    }


    public boolean isDatOfBirth(String dateOfBirth) {
        if (!isFormatDateOfBirth(dateOfBirth)) return false;
        String[] arr = dateOfBirth.split("/");
        int day = Integer.parseInt(arr[0]);
        int month = Integer.parseInt(arr[1]);
        int year = Integer.parseInt(arr[2]);
        if (!isYearOfBirth(year)) return false;
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

    private boolean isYearOfBirth(int year) {
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

    public boolean isFormatIdentityCard(String identityCard) {
        Pattern pattern = Pattern.compile(IDENTITY_CARD_REGEX);
        Matcher matcher = pattern.matcher(identityCard);
        return matcher.matches();
    }

    public boolean isFormatPay(String pay) {
        Pattern pattern = Pattern.compile(PAY_REGEX);
        Matcher matcher = pattern.matcher(pay);
        return matcher.matches();
    }

    public boolean isFormatDateOfBirth(String dateOfBirth) {
        Pattern pattern = Pattern.compile(DATE_OF_BIRTH_REGEX);
        Matcher matcher = pattern.matcher(dateOfBirth);
        return matcher.matches();
    }

    public boolean isFormatNumberPhone(String numberPhone) {
        Pattern pattern = Pattern.compile(NUMBER_PHONE_REGEX);
        Matcher matcher = pattern.matcher(numberPhone);
        return matcher.matches();
    }

    public boolean isFormatFullName(String fullName) {
        Pattern pattern = Pattern.compile(FULL_NAME_REGEX);
        Matcher matcher = pattern.matcher(fullName);
        return matcher.matches();
    }

    public boolean isFormatIdStaff(String id) {
        Pattern pattern = Pattern.compile(ID_STAFF_REGEX);
        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
    }

    public ArrayList<Staff> readDataFromFile(String path) {
        ArrayList<Staff> listStaff = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listStaff = (ArrayList<Staff>) ois.readObject();
            fis.close();
            ois.close();
        } catch (Exception ex) {
            System.out.println("File chua tồn tại, hãy nhập dữ liệu và tạo ra nó !");
        }
        return listStaff;
    }

    public void writeToFile(String path, ArrayList<Staff> listStaff) {
        try {
            FileOutputStream fos = new FileOutputStream(path);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(listStaff);
            oos.close();
            fos.close();
            System.out.println("Đã lưu lại mọi thay đổi vào dữ liệu gốc !");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void writeDataFromFileFormatToCsv(String path, ArrayList<Staff> listStaff) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            writer.append(FORMAT_CSV_STAFF);
            writer.append(DOWN_THE_LINE);
            for (Staff staff : listStaff) {
                writer.append(staff.getIdStaff());
                writer.append(COMMA_DELIMITER);
                writer.append(staff.getFullName());
                writer.append(COMMA_DELIMITER);
                writer.append(staff.getDateOfBirth());
                writer.append(COMMA_DELIMITER);
                writer.append(staff.getIdentityCard());
                writer.append(COMMA_DELIMITER);
                writer.append(staff.getNumberPhone());
                writer.append(COMMA_DELIMITER);
                writer.append(staff.getAddress());
                writer.append(COMMA_DELIMITER);
                writer.append(String.valueOf(staff.getPayStaff()));
                writer.append(COMMA_DELIMITER);
                writer.append(staff.getOther());
                writer.append(DOWN_THE_LINE);
            }
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exportFileStaffToCsv() {
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
                writeDataFromFileFormatToCsv(linkFull, staffList);
                System.out.println("Đã xuất file thành công đến đường dẫn : " + linkFull);
                System.out.println();
                menuStaffManager();
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
                            writeDataFromFileFormatToCsv(linkFull, staffList);
                            System.out.println("Đã xuất file thành công đến đường dẫn : " + linkFull);
                            isChoice = false;
                            isPress = false;
                            menuStaffManager();
                            break;
                        case 'n':
                        case 'N':
                            exportFileStaffToCsv();
                            isPress = false;
                            break;
                        case 'R':
                        case 'r':
                            isChoice = false;
                            isPress = false;
                            menuStaffManager();
                        default:
                    }
                } while (isPress);
            }

        } while (isChoice);
    }

    public boolean isLink(String link) {
        Pattern pattern = Pattern.compile(LINK_REGEX);
        Matcher matcher = pattern.matcher(link);
        return matcher.matches();
    }

    public void menuStaffManager(){
        char choice = ' ';
        do{
            System.out.println("------------------QUẢN LÝ NHÂN VIÊN------------------");
            System.out.println("|  1. Thêm nhân viên                                 |");
            System.out.println("|  2. Thay đổi thông tin nhân viên                   |");
            System.out.println("|  3. Xóa nhân viên                                  |");
            System.out.println("|  4. Tìm kiếm thông tin nhân viên                   |");
            System.out.println("|  5. Hiển thị danh sách nhân viên                   |");
            System.out.println("|  6. Xuất file thông tin nhân viên                  |");
            System.out.println("|  0. Quay lại                                       |");
            System.out.println("------------------------------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            }catch (Exception e){
                choice = ' ';
            }

            switch (choice){
                case '1':
                    addStaff();
                    break;
                case '2':
                    //editStaff();
                    break;
                case '3':
                    //deleteStaff();
                   break;
                case '4':
                    //searchStaff();
                    break;
                case '5':
                    //displayStaff();
                    break;
                case '6' :
                    exportFileStaffToCsv();
                    break;
                case '0':
                    //menuManager
                    break;
                default:
                    System.out.println("Chọn theo menu !");
            }
        }while (choice !='0');
    }
}
