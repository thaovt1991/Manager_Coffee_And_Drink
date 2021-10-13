package service;

import model.Staff;
import precentation.Menu;
import sort.sortStaff.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StaffManager implements Serializable {
    public ArrayList<Staff> staffList;
    static Scanner input = new Scanner(System.in);
    public static final String LINK_SAVE_OBJECT_STAFF = "src/data/list_staff.txt";
    public static final String LINK_SAVE_FORMAT_CSV_STAFF = "out_data/list_staff.csv";
    public static final String FORMAT_CSV_STAFF = "ID,HO VA TEN,GIOI TINH,NGAY SINH,CMND,SO DIEN THOAI,LUONG,THONG TIN KHAC";
    public static final String DOWN_THE_LINE = "\n";
    public static final String COMMA_DELIMITER = ",";
    public static final String ID_STAFF_REGEX = "[A-Z]{2}+\\d{6}$";
    public static final String FULL_NAME_REGEX = "^([AÀẢÃÁẠĂẰẮẲẴẶÂẤẦẨẪẬBCDĐEÈÉẺẼẸÊỀẾỂỄỆFGHIÍÌỈĨỊJKLMNOÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢPQRSTUÙÚỦŨỤƯỪỨỬỮỰVWXYÝỲỶỸỴZ]+[aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]+[ ]?)+$";
    public static final String NUMBER_PHONE_REGEX = "^[0][1-9][0-9]{8,9}$";
    public static final String DATE_OF_BIRTH_REGEX = "^[0|1|2|3]?[0-9][/][0-1]?[0-9][/][1|2]\\d{3}$"; //dinh dang 22/01/1991 ;
    public static final String PAY_REGEX = "^[1-9][0-9]{1,14}[0]{3}$";
    public static final String IDENTITY_CARD_REGEX = "^[1|2][0-9]{8}$";
    public static final String LINK_REGEX = "(^([C|D][:])\\\\(?:[\\w]+\\\\)*\\w+$)|(^[C|D][:][\\\\]$)";


    public StaffManager() {
        staffList = readDataFromFile(LINK_SAVE_OBJECT_STAFF);
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
       char check = ' '   ;
       boolean isCheck = false;
        do {
            System.out.print("Bạn có muốn thêm nhân viên ? Nhấn 'Y' để đồng ý, nhấn 'N' để quay trờ về menu !");
            try {
                check = input.nextLine().charAt(0);
            } catch (Exception e) {
                check = ' ';
            }
            switch (check) {
                case 'y':
                case 'Y':
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

                    String gender = "";
                    char press = ' ';
                    boolean isPress = true;
                    System.out.println("Giới tính nhân viên ");
                    do {
                        System.out.print("Nhấn 'M' nếu nhân viên là nam , nhấn 'F' nếu nhân viên là nữ ?  ");
                        try {
                            press = input.nextLine().charAt(0);
                        } catch (Exception e) {
                            press = ' ';
                        }
                        switch (press) {
                            case 'm':
                            case 'M':
                                gender = "Nam";
                                isPress = false;
                                break;
                            case 'f':
                            case 'F':
                                gender = "Nu";
                                isPress = false;
                                break;
                            default:
                        }

                    } while (isPress);


                    String dateOfBirth = "";
                    do {
                        System.out.print("Nhập ngày tháng năm sinh của nhân viên, có dạng day/month/year, ví dụ 2/2/1992 : ");
                        dateOfBirth = input.nextLine();
                        if (!isFormatDateOfBirth(dateOfBirth)) {
                            System.out.println("Không phải định dạng đúng !");
                        } else if (!isDatOfBirth(dateOfBirth)) {
                            System.out.println("Không phải ngày thực tế hoặc đã vượt ra khỏi nằm ngoài độ tuổi cho phép ! ");
                        }
                    } while (!isDatOfBirth(dateOfBirth));

                    String idCar = "";
                    do {
                        System.out.print("Nhập số CMND của nhân viên : ");
                        idCar = input.nextLine();
                        if (!isFormatIdentityCard(idCar)) {
                            System.out.println("CMND phải có 9 số và bắt đầu từ  1 hoặc 2 !");
                        } else if (isIdentityCardHaveInList(idCar)) {
                            System.out.println("CMND này đã được đăng kí , hãy kiểm tra lại !");
                        }
                    } while (!isFormatIdentityCard(idCar) || isIdentityCardHaveInList(idCar));

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
                    address = input.nextLine();

                    String strPay = "";
                    do {
                        System.out.print("Nhập tiền lương của nhân viên : ");
                        strPay = input.nextLine();
                        if (!isFormatPay(strPay)) {
                            System.out.println("Định dạng tiền lương chưa hợp lý !");
                        }
                    } while (!isFormatPay(strPay));

                    String other = "";
                    System.out.print("Nhập các thông tin khác của nhân viên (có thể bỏ trống) !");
                    other = input.nextLine();

                    Staff staff = new Staff(idStaff, fullName, gender, dateOfBirth, idCar, numberPhone, address, Long.parseLong(strPay), other);
                    System.out.println();
                    System.out.println("Thông tin nhân viên bạn vừa nhập vào là : ");
                    System.out.println(staff);
                    System.out.println();
                    char choice = ' ';
                    boolean isChoice = true;
                    do {
                        System.out.print("Bạn có muốn lưu vào dữ liệu nhân viên ! Nhấn 'Y' để đồng ý , nhấn 'N' để hủy dữ liệu vừa nhập  ");
                        try {
                            choice = input.nextLine().charAt(0);
                        } catch (Exception e) {
                            choice = ' ';
                        }
                        switch (choice) {
                            case 'y':
                            case 'Y':
                                staffList.add(staff);
                                writeToFile(LINK_SAVE_OBJECT_STAFF, staffList);
                                writeDataFromFileFormatToCsv(LINK_SAVE_FORMAT_CSV_STAFF, staffList);
                                isChoice = false;
                                menuStaffManager();
                                break;
                            case 'n':
                            case 'N':
                                menuStaffManager();
                                isChoice = false;
                                break;
                            default:
                        }
                    } while (isChoice);
                    break;
                case 'n':
                case 'N':
                    menuStaffManager();
                    break;
                default:
                    isCheck = true ;
            }
        }while (isCheck);
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
            System.out.println("File danh sách nhân viên chưa tồn tại, hãy nhập dữ liệu và tạo ra nó !");
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
                writer.append(staff.getGender());
                writer.append(COMMA_DELIMITER);
                writer.append(staff.getDateOfBirth());
                writer.append(COMMA_DELIMITER);
                writer.append(staff.getIdentityCard());
                writer.append(COMMA_DELIMITER);
                writer.append(staff.getNumberPhone());
                writer.append(COMMA_DELIMITER);
                writer.append(staff.getAddress().replace(",",";"));
                writer.append(COMMA_DELIMITER);
                writer.append(String.valueOf(staff.getPayStaff()));
                writer.append(COMMA_DELIMITER);
                writer.append(staff.getOther().replace(",",";"));
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


    public void displayAllStaff() {
        DecimalFormat format = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other;
        System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", "STT", "ID", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "CMND", "SĐT", "ĐỊA CHỈ", "LƯƠNG- VND", "THÔNG TIN KHÁC");
        for (Staff staff : staffList) {
            count++;
            stt = String.valueOf(count);
            id = staff.getIdStaff();
            fullname = staff.getFullName();
            gender = staff.getGender();
            dateOfBirth = staff.getDateOfBirth();
            idCar = staff.getIdentityCard();
            numberPhone = staff.getNumberPhone();
            address = staff.getAddress();
            pay = format.format(staff.getPayStaff());
            other = staff.getOther();
            System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other);
        }
        System.out.println();
    }

    public void displayAllStaff(ArrayList<Staff> staffList) {
        DecimalFormat format = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other;
        System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", "STT", "ID", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "CMND", "SĐT", "ĐỊA CHỈ", "LƯƠNG- VND", "THÔNG TIN KHÁC");
        for (Staff staff : staffList) {
            count++;
            stt = String.valueOf(count);
            id = staff.getIdStaff();
            fullname = staff.getFullName();
            gender = staff.getGender();
            dateOfBirth = staff.getDateOfBirth();
            idCar = staff.getIdentityCard();
            numberPhone = staff.getNumberPhone();
            address = staff.getAddress();
            pay = format.format(staff.getPayStaff());
            other = staff.getOther();
            System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other);
        }
        System.out.println();
    }

    public void optionDisplayStaff() {
        char choice = ' ';
        boolean isChoice = true;
        do {
            System.out.println("--------------------------------------------------------------");
            System.out.println("|                 LỰA CHỌN HIỂN THỊ                           |");
            System.out.println("---------------------------------------------------------------");
            System.out.println("|  1. Hiển thị sắp xếp theo id nhân viên                       |");
            System.out.println("|  2. Hiển thị sắp xếp theo tên nhân viên                      |");
            System.out.println("|  3. Hiển thị sắp xếp theo ngày sinh                          |");
            System.out.println("|  4. Hiển thị sắp xếp theo lương                              |");
            System.out.println("|  5. Hiển thị theo giới tính                                  |");
            System.out.println("|                                                  0. Quay lại |");
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
                    displayStaffById();
                    break;
                case '2':
                    displayStaffByName();
                    break;
                case '3':
                    displayStaffByDateOfDay();
                    break;
                case '4':
                    displayStaffByPay();
                    break;
                case '5':
                    displayStaffByGender();
                    break;
                case '0':
                    menuStaffManager();
                    isChoice = false;
                default:
                    System.out.println("Chọn theo menu !");
            }

        } while (isChoice);
    }

    public void displayStaffById() {
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("---------------------------------------------");
            System.out.println("|        SẮP XẾP THEO ID NHÂN VIÊN           |");
            System.out.println("----------------------------------------------");
            System.out.println("| 1. Sắp xếp ID theo thứ tự A-Z              |");
            System.out.println("| 2. Sắp xếp ID theo thứ tự Z-A              |");
            System.out.println("|                                0. Quay lại |");
            System.out.println("---------------------------------------------|");
            System.out.println();
            System.out.print("Lựa chọn :");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    System.out.println("Sắp xếp ID theo thứ tự A-Z    ");
                    SortByIdStaffAZ sortISAZ = new SortByIdStaffAZ();
                    Collections.sort(staffList, sortISAZ);
                    displayAllStaff();
                    staffList = readDataFromFile(LINK_SAVE_OBJECT_STAFF);
                    break;
                case '2':
                    System.out.println("Sắp xếp theo thứ tự Z-A    ");
                    SortByIdStaffZA sortISZA = new SortByIdStaffZA();
                    Collections.sort(staffList, sortISZA);
                    displayAllStaff();
                    staffList = readDataFromFile(LINK_SAVE_OBJECT_STAFF);
                    break;
                case '0':
                    optionDisplayStaff();
                    isChoice = false;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (isChoice);
    }

    public void displayStaffByName() {
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("----------------------------------------------------");
            System.out.println("|         SẮP XẾP THEO TÊN NHÂN VIÊN                |");
            System.out.println("-----------------------------------------------------");
            System.out.println("| 1. Sắp xếp tên theo thứ tự A-Z                    |");
            System.out.println("| 2. Sắp xếp tên theo thứ tự Z-A                    |");
            System.out.println("|                                       0. Quay lại |");
            System.out.println("-----------------------------------------------------");
            System.out.println();
            System.out.print("Lựa chọn :");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    System.out.println("Sắp xếp theo thứ tự A-Z    ");
                    SortByNameStaffAZ sortNSAZ = new SortByNameStaffAZ();
                    Collections.sort(staffList, sortNSAZ);
                    displayAllStaff();
                    staffList = readDataFromFile(LINK_SAVE_OBJECT_STAFF);
                    break;
                case '2':
                    System.out.println("Sắp xếp theo thứ tự Z-A    ");
                    SortByNameStaffZA sortNSZA = new SortByNameStaffZA();
                    Collections.sort(staffList, sortNSZA);
                    displayAllStaff();
                    staffList = readDataFromFile(LINK_SAVE_OBJECT_STAFF);
                    break;
                case '0':
                    optionDisplayStaff();
                    isChoice = false;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (isChoice);
    }

    public void displayStaffByPay() {
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("-------------------------------------------------");
            System.out.println("|              SẮP XẾP THEO LƯƠNG                |");
            System.out.println("--------------------------------------------------");
            System.out.println("| 1. Sắp xếp lương tăng dần                      |");
            System.out.println("| 2. Sắp xếp lương giảm dần                      |");
            System.out.println("|                                    0. Quay lại |");
            System.out.println("-------------------------------------------------");
            System.out.println();
            System.out.print("Lựa chọn :");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    System.out.println("Sắp xếp lương tăng dần    ");
                    SortByPayStaffAscending sortPSA = new SortByPayStaffAscending();
                    Collections.sort(staffList, sortPSA);
                    displayAllStaff();
                    staffList = readDataFromFile(LINK_SAVE_OBJECT_STAFF);
                    break;
                case '2':
                    System.out.println("Sắp xếp lương giảm dần    ");
                    SortByPayStaffDecrease sortPSD = new SortByPayStaffDecrease();
                    Collections.sort(staffList, sortPSD);
                    displayAllStaff();
                    staffList = readDataFromFile(LINK_SAVE_OBJECT_STAFF);
                    break;
                case '0':
                    optionDisplayStaff();
                    isChoice = false;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (isChoice);
    }

    public void displayStaffByGender() {
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("------------------------------------------");
            System.out.println("|         SẮP XẾP THEO GIỚI TÍNH         |");
            System.out.println("------------------------------------------");
            System.out.println("| 1. Sắp xếp Nu - Nam                    |");
            System.out.println("| 2. Sắp xếp Nam - Nu                    |");
            System.out.println("|                            0. Quay lại |");
            System.out.println("------------------------------------------");
            System.out.println();
            System.out.print("Lựa chọn :");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    System.out.println("Sắp xếp Nu - Nam ");
                    SortGenderFM sortGender = new SortGenderFM();
                    Collections.sort(staffList, sortGender);
                    displayAllStaff();
                    staffList = readDataFromFile(LINK_SAVE_OBJECT_STAFF);
                    break;
                case '2':
                    System.out.println("Sắp xếp Nam - Nu ");
                    SortGenderMF sortGenderMF = new SortGenderMF();
                    Collections.sort(staffList, sortGenderMF);
                    displayAllStaff();
                    staffList = readDataFromFile(LINK_SAVE_OBJECT_STAFF);
                    break;
                case '0':
                    optionDisplayStaff();
                    isChoice = false;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (isChoice);
    }

    public void displayStaffByDateOfDay() {
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("--------------------------------------------------");
            System.out.println("|            SẮP XẾP THEO NGÀY SINH               |");
            System.out.println("--------------------------------------------------");
            System.out.println("| 1. Sắp xếp giảm dần độ tuổi                     |");
            System.out.println("| 2. Sắp xếp tăng dần độ tuổi                     |");
            System.out.println("|                                     0. Quay lại |");
            System.out.println("--------------------------------------------------");
            System.out.println();
            System.out.print("Lựa chọn :");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    System.out.println("Sắp xếp giảm dần độ tuổi ");//ngay sinh tang dan
                    SortByDateOfBirthAscending sortByDoBAsc = new SortByDateOfBirthAscending();
                    Collections.sort(staffList, sortByDoBAsc);
                    displayAllStaff();
                    staffList = readDataFromFile(LINK_SAVE_OBJECT_STAFF);
                    break;
                case '2':
                    System.out.println("Sắp xếp tăng dần độ tuổi "); //ngay sinh giam dần
                    SortByDateOfBirthDecrease sortByDoBDec = new SortByDateOfBirthDecrease();
                    Collections.sort(staffList, sortByDoBDec);
                    displayAllStaff();
                    staffList = readDataFromFile(LINK_SAVE_OBJECT_STAFF);
                    break;
                case '0':
                    optionDisplayStaff();
                    isChoice = false;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (isChoice);

    }

    public  void displayOneStaff(Staff staff){
        DecimalFormat format = new DecimalFormat("###,###,###");
        String  id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other;
        System.out.printf("%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", "ID", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "CMND", "SĐT", "ĐỊA CHỈ", "LƯƠNG- VND", "THÔNG TIN KHÁC");
        id = staff.getIdStaff();
        fullname = staff.getFullName();
        gender = staff.getGender();
        dateOfBirth = staff.getDateOfBirth();
        idCar = staff.getIdentityCard();
        numberPhone = staff.getNumberPhone();
        address = staff.getAddress();
        pay = format.format(staff.getPayStaff());
        other = staff.getOther();
        System.out.printf("%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other);
    }


    public void searchStaff() {
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("--------------------------------------------------------------------");
            System.out.println("|                     TÌM KIẾM NHÂN VIÊN                            |");
            System.out.println("--------------------------------------------------------------------");
            System.out.println("| 1. Tìm kiếm theo ID nhân viên                                     |");
            System.out.println("| 2. Tìm kiếm theo tên nhân viên                                    |");
            System.out.println("| 3. Tìm kiếm theo giới tính                                        |");
            System.out.println("| 4. Tìm kiếm theo ngày tháng năm sinh                              |");
            System.out.println("| 5. Tìm kiếm theo số điện thoại                                    |");
            System.out.println("| 6. Tìm kiếm theo địa chỉ                                          |");
            System.out.println("| 7 .Tìm kiếm theo lương                                            |");
            System.out.println("| 8. Tìm kiếm thông tin khác của nhân viên                          |");
            System.out.println("|                                                      0. Quay lại  |");
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
                    searchStaffById();
                    break;
                case '2':
                    searchStaffByName();
                    break;
                case '3':
                    searchStaffByGender();
                    break;
                case '4':
                    searchStaffByDateOfBirth();
                    break;
                case '5':
                    searchStaffByNumberPhone();
                    break;
                case '6':
                    searchStaffByAddress();
                    break;
                case '7':
                    searchStaffByPay();
                    break;
                case '8':
                    searchStaffByOther();
                case '0':
                    menuStaffManager();
                    isChoice = false;
                    break;
                default:
                    System.out.println("Hãy chọn theo menu tìm kiếm !");
            }

        } while (isChoice);
    }

    public void searchStaffById() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other;
        System.out.println();
        System.out.print("Nhập ID nhân viên cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", "STT", "ID", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "CMND", "SĐT", "ĐỊA CHỈ", "LƯƠNG- VND", "THÔNG TIN KHÁC");
        for (Staff staff : staffList) {
            if (staff.getIdStaff().toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = staff.getIdStaff();
                fullname = staff.getFullName();
                gender = staff.getGender();
                dateOfBirth = staff.getDateOfBirth();
                idCar = staff.getIdentityCard();
                numberPhone = staff.getNumberPhone();
                address = staff.getAddress();
                pay = formater.format(staff.getPayStaff());
                other = staff.getOther();
                System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other);
            }
        }
        displayReturnSearch(count);
    }

    public void searchStaffByName() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other;
        System.out.println();
        System.out.print("Nhập tên nhân viên cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", "STT", "ID", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "CMND", "SĐT", "ĐỊA CHỈ", "LƯƠNG- VND", "THÔNG TIN KHÁC");
        for (Staff staff : staffList) {
            if (staff.getFullName().toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = staff.getIdStaff();
                fullname = staff.getFullName();
                gender = staff.getGender();
                dateOfBirth = staff.getDateOfBirth();
                idCar = staff.getIdentityCard();
                numberPhone = staff.getNumberPhone();
                address = staff.getAddress();
                pay = formater.format(staff.getPayStaff());
                other = staff.getOther();
                System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other);
            }
        }
        displayReturnSearch(count);
    }

    public void searchStaffByGender() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other;
        System.out.println();
        System.out.print("Nhập giới tính nhân viên cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", "STT", "ID", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "CMND", "SĐT", "ĐỊA CHỈ", "LƯƠNG- VND", "THÔNG TIN KHÁC");
        for (Staff staff : staffList) {
            if (staff.getGender().toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = staff.getIdStaff();
                fullname = staff.getFullName();
                gender = staff.getGender();
                dateOfBirth = staff.getDateOfBirth();
                idCar = staff.getIdentityCard();
                numberPhone = staff.getNumberPhone();
                address = staff.getAddress();
                pay = formater.format(staff.getPayStaff());
                other = staff.getOther();
                System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other);
            }
        }
        displayReturnSearch(count);
    }

    public void searchStaffByDateOfBirth() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other;
        System.out.println();
        System.out.print("Nhập ngày tháng năm sinh nhân viên cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", "STT", "ID", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "CMND", "SĐT", "ĐỊA CHỈ", "LƯƠNG- VND", "THÔNG TIN KHÁC");
        for (Staff staff : staffList) {
            if (staff.getDateOfBirth().toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = staff.getIdStaff();
                fullname = staff.getFullName();
                gender = staff.getGender();
                dateOfBirth = staff.getDateOfBirth();
                idCar = staff.getIdentityCard();
                numberPhone = staff.getNumberPhone();
                address = staff.getAddress();
                pay = formater.format(staff.getPayStaff());
                other = staff.getOther();
                System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other);
            }
        }
        displayReturnSearch(count);
    }

    public void searchStaffByNumberPhone() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other;
        System.out.println();
        System.out.print("Nhập số điện thoại nhân viên cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", "STT", "ID", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "CMND", "SĐT", "ĐỊA CHỈ", "LƯƠNG- VND", "THÔNG TIN KHÁC");
        for (Staff staff : staffList) {
            if (staff.getNumberPhone().toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = staff.getIdStaff();
                fullname = staff.getFullName();
                gender = staff.getGender();
                dateOfBirth = staff.getDateOfBirth();
                idCar = staff.getIdentityCard();
                numberPhone = staff.getNumberPhone();
                address = staff.getAddress();
                pay = formater.format(staff.getPayStaff());
                other = staff.getOther();
                System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other);
            }
        }
        displayReturnSearch(count);
    }

    public void searchStaffByAddress() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other;
        System.out.println();
        System.out.print("Nhập địa chỉ nhân viên cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", "STT", "ID", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "CMND", "SĐT", "ĐỊA CHỈ", "LƯƠNG- VND", "THÔNG TIN KHÁC");
        for (Staff staff : staffList) {
            if (staff.getAddress().toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = staff.getIdStaff();
                fullname = staff.getFullName();
                gender = staff.getGender();
                dateOfBirth = staff.getDateOfBirth();
                idCar = staff.getIdentityCard();
                numberPhone = staff.getNumberPhone();
                address = staff.getAddress();
                pay = formater.format(staff.getPayStaff());
                other = staff.getOther();
                System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other);
            }
        }
        displayReturnSearch(count);
    }

    public void searchStaffByPay() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other;
        System.out.println();
        System.out.print("Nhập lương nhân viên cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", "STT", "ID", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "CMND", "SĐT", "ĐỊA CHỈ", "LƯƠNG- VND", "THÔNG TIN KHÁC");
        for (Staff staff : staffList) {
            if (String.valueOf(staff.getPayStaff()).toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = staff.getIdStaff();
                fullname = staff.getFullName();
                gender = staff.getGender();
                dateOfBirth = staff.getDateOfBirth();
                idCar = staff.getIdentityCard();
                numberPhone = staff.getNumberPhone();
                address = staff.getAddress();
                pay = formater.format(staff.getPayStaff());
                other = staff.getOther();
                System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other);
            }
        }
        displayReturnSearch(count);
    }

    public void searchStaffByOther() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other;
        System.out.println();
        System.out.print("Nhập thông tin về nhân viên cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", "STT", "ID", "HỌ VÀ TÊN", "GIỚI TÍNH", "NGÀY SINH", "CMND", "SĐT", "ĐỊA CHỈ", "LƯƠNG- VND", "THÔNG TIN KHÁC");
        for (Staff staff : staffList) {
            if (staff.getOther().toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = staff.getIdStaff();
                fullname = staff.getFullName();
                gender = staff.getGender();
                dateOfBirth = staff.getDateOfBirth();
                idCar = staff.getIdentityCard();
                numberPhone = staff.getNumberPhone();
                address = staff.getAddress();
                pay = formater.format(staff.getPayStaff());
                other = staff.getOther();
                System.out.printf("%-5s%-15s%-30s%-15s%-15s%-15s%-20s%-35s%-20s%s\n", stt, id, fullname, gender, dateOfBirth, idCar, numberPhone, address, pay, other);
            }
        }
        displayReturnSearch(count);
    }

    private void displayReturnSearch(int count) {
        System.out.println("Có '" + count + "' nhân viên được tìm thấy !");
        char press = ' ';
        boolean isChoice = true;
        System.out.println();
        do {
            System.out.print("Nhấn 'R' để quay trở về menu tìm kiếm : ");
            try {
                press = input.nextLine().charAt(0);
            } catch (Exception e) {
                press = ' ';
            }
            switch (press) {
                case 'r':
                case 'R': {
                    searchStaff();
                    isChoice = false;
                    break;
                }
                default:
                    isChoice = true;
            }
        } while (isChoice);
    }

    public void deleteStaff() {
        char choice = ' ';
        do {
            System.out.println("------------------------------------------------");
            System.out.println("|          LỰA CHỌN NHÂN VIÊN MUỐN XÓA           |");
            System.out.println("--------------------------------------------------");
            System.out.println("|  1. Xóa theo ID của nhân viên                  |");
            System.out.println("|  2. Xóa theo tên của nhân viên                 |");
            System.out.println("|                                    0. Quay lại |");
            System.out.println("--------------------------------------------------");
            System.out.println();
            System.out.print("Lựa chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    deteleStaffById();
                    break;
                case '2':
                    deleteStaffByFullName();
                    break;
                case '0':
                    menuStaffManager();
                    break;
                default:
                    System.out.println("Lựa chọn theo menu trên !");
            }
        } while (choice != '0');
    }

    public void deteleStaffById() {
        displayAllStaff();
        String id = "";
        System.out.print("Nhập ID nhân viên muốn xóa : ");
        id = input.nextLine();
        if (!isFormatPay(id)) {
            System.out.println("Định dạng id phải có dạng 'NV123456' !");
            deleteStaff();
        } else {
            if (!isIdHaveInList(id)) {
                System.out.println("Không có nhân viên nào có ID = '" + id + "' !");
                deleteStaff();
            } else {
                for (Staff staff : staffList) {
                    if (staff.getIdStaff().equals(id)) {
                        deteleStaffInList(staff);
                        break;
                    }
                }
            }
        }
    }

    public void deleteStaffByFullName() {
        displayAllStaff();
        String fullname = "";
        System.out.print("Nhập họ và tên nhân viên muốn xóa : ");
        fullname = input.nextLine();
        if (!isFormatFullName(fullname)) {
            System.out.println("Đinh dạng tên không đúng, vui lòng nhập theo mẫu để tìm kiếm chi tiết  !");
            deleteStaff();
        } else {
            if (!isFullNameHaveInList(fullname)) {
                System.out.println("Không có nhân viên nào tên '" + fullname + "' !");
                deleteStaff();
            } else {
                int count = 0;
                ArrayList<Staff> listStaffTemp = new ArrayList<>();
                for (Staff staff : staffList) {
                    if (staff.getFullName().equals(fullname)) {
                        count++;
                        listStaffTemp.add(staff);
                    }
                }
                if (count == 1) {
                    deteleStaffInList(listStaffTemp.get(0));
                } else {
                    displayAllStaff(listStaffTemp);
                    System.out.println();
                    int choose = 0;
                    do {
                        System.out.println("Hãy nhập 'SỐ THỨ TỰ 'trong danh sách ở trên để xóa nhân viên, nhấn 'Q' để quay về menu quản lý");

                        String num = input.nextLine();
                        try {
                            if (num.charAt(0) == 'q' || num.charAt(0) == 'Q') {
                                deleteStaff();
                                choose = -1;
                            } else {
                                choose = Integer.parseInt(num);
                            }
                        } catch (Exception e) {
                            choose = 0;
                        }
                        if (choose > 0 && choose <= count) {
                            deteleStaffInList(listStaffTemp.get(choose - 1));
                        } else choose = 0;
                    } while (choose == 0);
                }
            }
        }
    }

    public void deteleStaffInList(Staff staff) {
        staffList.remove(staff);
        System.out.println("Danh sách nhân viên sau khi xóa nhân viên '" + staff.getFullName() + "'");
        displayAllStaff();
        char choice;
        boolean isChoice = true;
        do {
            System.out.println("Bạn muốn lưu thay đổi ? 'Y' = Yes / 'N' = No");
            System.out.println("Chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case 'y':
                case 'Y':
                    writeToFile(LINK_SAVE_OBJECT_STAFF, staffList);
                    writeDataFromFileFormatToCsv(LINK_SAVE_FORMAT_CSV_STAFF, staffList);
                    isChoice = false;
                    deleteStaff();
                    break;
                case 'n':
                case 'N':
                    staffList = readDataFromFile(LINK_SAVE_OBJECT_STAFF);
                    isChoice = false;
                    deleteStaff();
                    break;
                default:
                    System.out.println();
            }
        } while (isChoice);
    }

    public void editStaff() {
        char choice = ' ';
        do {
            System.out.println("----------------------------------------------------------");
            System.out.println("|             LỰA CHỌN THAY ĐỔI THÔNG TIN                 |");
            System.out.println("-----------------------------------------------------------");
            System.out.println("|  1. Sửa thông tin nhân viên theo ID                     |");
            System.out.println("|  2. Sửa thông tin nhân viên theo tên                    |");
            System.out.println("|                                             0. Quay lại |");
            System.out.println("-----------------------------------------------------------");
            System.out.println();
            System.out.print("Lựa chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    editStaffById();
                    break;
                case '2':
                    editStaffByName();
                    break;
                case '0':
                    menuStaffManager();
                    break;
                default:
                    System.out.println("Lựa chọn theo hướng dẫn trên menu !");
            }
        } while (choice != '0');
    }

    public void editStaffById() {
        displayAllStaff();
        String id = "";
        System.out.print("Nhập id nhân viên cần thay đổi thông tin : ");
        id = input.nextLine();
        if (!isFormatIdStaff(id)) {
            System.out.println("Định dạng ID chưa hợp lệ ! ( ví dụ id đúng : NV201294)");
            editStaff();
        } else {
            if (!isIdHaveInList(id)) {
                System.out.println("Không có nhân viên nào có id '"+id+"' !");
                editStaff();
            } else {
                for (Staff staff : staffList) {
                    if (staff.getIdStaff().equals(id)) {
                        System.out.println("Nhân viên tìm được là : ");
                        displayOneStaff(staff);
                        System.out.println("Có phải bạn muốn thay đổi thông tin nhân viên này !");

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
                                    editOptionStaff(staff);
                                    isChoice = false;
                                    break;
                                case 'n':
                                case 'N':
                                    editStaff();
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

    public void  editStaffByName(){
        displayAllStaff();
        String fullname = "";
        System.out.print("Nhập họ và tên nhân viên muốn thay đổi thông tin : ");
        fullname = input.nextLine();
        if (!isFormatFullName(fullname)) {
            System.out.println("Đinh dạng tên không đúng, vui lòng nhập theo mẫu để tìm kiếm chi tiết  !");
            editStaff();
        } else {
            if (!isFullNameHaveInList(fullname)) {
                System.out.println("Không có nhân viên nào tên '" + fullname + "' !");
                editStaff();
            } else {
                int count = 0;
                ArrayList<Staff> listStaffTemp = new ArrayList<>();
                for (Staff staff : staffList) {
                    if (staff.getFullName().equals(fullname)) {
                        count++;
                        listStaffTemp.add(staff);
                    }
                }
                if (count == 1) {
                    editOptionStaff(listStaffTemp.get(0));
                } else {
                    displayAllStaff(listStaffTemp);
                    System.out.println();
                    int choose = 0;
                    do {
                        System.out.println("Hãy nhập 'SỐ THỨ TỰ 'trong danh sách ở trên để chọn nhân viên muốn thay dổi thông tin ! , nhấn 'Q' để quay về menu quản lý nhân viên");

                        String num = input.nextLine();
                        try {
                            if (num.charAt(0) == 'q' || num.charAt(0) == 'Q') {
                                editStaff();
                                choose = -1;
                            } else {
                                choose = Integer.parseInt(num);
                            }
                        } catch (Exception e) {
                            choose = 0;
                        }
                        if (choose > 0 && choose <= count) {
                            editOptionStaff(listStaffTemp.get(choose - 1));
                        } else choose = 0;
                    } while (choose == 0);
                }
            }
        }
    };

    public void editOptionStaff(Staff staff) {
        char choice = ' ';
        boolean isChoice = true;
        do {
            System.out.println("----------------------------------------------------------------");
            System.out.println("|                THAY ĐỔI THÔNG TIN NHÂN VIÊN                   |");
            System.out.println("------------------------------------------------------------------");
            System.out.println("|   1.  Thay đổi id nhân viên - Nhấn phím 'I'                    |");
            System.out.println("|   2.  Thay đổi tên nhân viên - Nhấn phím 'F'                   |");
            System.out.println("|   3.  Thay đổi giới tính - Nhấn phím 'G'                       |");
            System.out.println("|   4.  Thay đổi ngày tháng năm sinh- Nhấn phím 'B'              |");
            System.out.println("|   5.  Thay đổi CMND - Nhấn phím 'C'                            |");
            System.out.println("|   6.  Thay đổi số điện thoại - Nhấn phím 'N'                   |");
            System.out.println("|   7.  Thay đổi địa chỉ - Nhấn phím 'A'                         |");
            System.out.println("|   8.  Thay đổi mức lương nhân viên - Nhấn phím 'P'             |");
            System.out.println("|   9.  Thay đổi thông tin khác - Nhấn phím 'O'                  |");
            System.out.println("|   10. Thay đổi toàn bộ thông tin nhân viên - Nhấn phím 'E'     |");
            System.out.println("|   11.  Thoát và hủy thay đổi - Nhấn phím 'R'                   |");
            System.out.println("|   12.  Thoát và lưu thay đổi - Nhấn phím 'S'                   |");
            System.out.println("------------------------------------------------------------------");
            System.out.println();
            System.out.print("Chọn :");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }

            switch (choice) {
                case 'I':
                case 'i':
                    System.out.println("----------Thay đổi id nhân viên ----------");
                      editIdOfStaff(staff);
                    break;
                case 'F':
                case 'f':
                    System.out.println("---------Thay đổi tên nhân viên ----------");
                    editFullNameOfStaff(staff);
                    break;
                case 'G':
                case 'g':
                    System.out.println("----------Thay đổi giới tính nhân viên----------");
                    editGenderOfStaff(staff);
                    break;
                case 'B':
                case 'b':
                    System.out.println("----------Thay đổi ngày tháng năm sinh nhân viên-----------");
                    editDateOfBirthOfStaff(staff);
                    break;
                case 'C':
                case 'c':
                    System.out.println("----------Thay đổi CMND của nhân viên-----------");
                    editIdentifyCardOfStaff(staff);
                    break;
                case 'N':
                case 'n':
                    System.out.println("----------Thay đổi số điện thoại nhân viên--------");
                    editNumberPhoneOfStaff(staff);
                    break;
                case 'A':
                case 'a':
                    System.out.println("----------Thay đổi địa chỉ nhân viên--------");
                    editAddresssOfStaff(staff);
                    break;
                case 'P':
                case 'p':
                    System.out.println("----------Thay đổi  mức lương nhân viên--------");
                    editPayOfStaff(staff);
                    break;
                case 'O':
                case 'o':
                    System.out.println("----------Thay đổi thông tin khác của nhân viên--------");
                    editOtherOfStaff(staff);
                    break;
                case 'E':
                case 'e':
                    System.out.println("----------Thay đổi toàn bộ thông tin nhân viên--------");
                    editIdOfStaff(staff);
                    editFullNameOfStaff(staff);
                    editGenderOfStaff(staff);
                    editDateOfBirthOfStaff(staff);
                    editIdentifyCardOfStaff(staff);
                    editNumberPhoneOfStaff(staff);
                    editAddresssOfStaff(staff);
                    editPayOfStaff(staff);
                    editOtherOfStaff(staff);
                    break;
                case 'R':
                case 'r':
                    staffList = readDataFromFile(LINK_SAVE_OBJECT_STAFF);
                    editStaff();
                    isChoice = false;
                    break;
                case 'S':
                case 's':
                    writeToFile(LINK_SAVE_OBJECT_STAFF, staffList);
                    writeDataFromFileFormatToCsv(LINK_SAVE_FORMAT_CSV_STAFF, staffList);
                    System.out.println("Danh sách nhân viên sau khi sửa là :");
                    displayAllStaff();
                    editStaff();
                    isChoice = false;
                    break;
                default:
                    System.out.println("Hãy chọn theo hướng của menu ở trên !");
            }
        } while (isChoice);
    }

    public void  editIdOfStaff(Staff staff){
        String idStaff = "";
        do {
            System.out.print("Nhập id mới của nhân viên :");
            idStaff = input.nextLine();
            if (!isFormatIdStaff(idStaff)) {
                System.out.println("Định dạng ID chưa hợp lệ ! ( ví dụ id đúng : NV201294)");
            } else if (isIdHaveInList(idStaff)) {
                System.out.println("Id đã được đăng kí , vui lòng nhập lại id mới !");
            }
        } while (isIdHaveInList(idStaff) || !isFormatIdStaff(idStaff));
        staff.setIdStaff(idStaff);
    }

    public void editFullNameOfStaff(Staff staff){
        String fullName = "";
        do {
            System.out.print("Nhập họ và tên mới của nhân viên : ");
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
        staff.setFullName(fullName);
    }

    public void editGenderOfStaff(Staff staff){
        String gender = "";
        char press = ' ';
        boolean isPress = true;
        System.out.println("Thay đổi giới tính của nhân viên ");
        do {
            System.out.print("Nhấn 'M' nếu nhân viên là nam , nhấn 'F' nếu nhân viên là nữ ?  ");
            try {
                press = input.nextLine().charAt(0);
            } catch (Exception e) {
                press = ' ';
            }
            switch (press) {
                case 'm':
                case 'M':
                    gender = "Nam";
                    isPress = false;
                    break;
                case 'f':
                case 'F':
                    gender = "Nu";
                    isPress = false;
                    break;
                default:
            }
        } while (isPress);
        staff.setGender(gender);
    }

    public void editDateOfBirthOfStaff(Staff staff){
        String dateOfBirth = "";
        do {
            System.out.print("Nhập ngày tháng năm sinh mới cho nhân viên, có dạng day/month/year, ví dụ 2/2/1992 : ");
            dateOfBirth = input.nextLine();
            if (!isFormatDateOfBirth(dateOfBirth)) {
                System.out.println("Không phải định dạng đúng !");
            } else if (!isDatOfBirth(dateOfBirth)) {
                System.out.println("Không phải ngày thực tế hoặc đã vượt ra khỏi nằm ngoài độ tuổi cho phép ! ");
            }
        } while (!isDatOfBirth(dateOfBirth));
        staff.setDateOfBirth(dateOfBirth);
    }

    public void editIdentifyCardOfStaff(Staff staff){
        String idCar = "";
        do {
            System.out.print("Nhập số CMND mới cho nhân viên : ");
            idCar = input.nextLine();
            if (!isFormatIdentityCard(idCar)) {
                System.out.println("CMND phải có 9 số và bắt đầu từ  1 hoặc 2 !");
            } else if (isIdentityCardHaveInList(idCar)) {
                System.out.println("CMND này đã được đăng kí , hãy kiểm tra lại !");
            }
        } while (!isFormatIdentityCard(idCar) || isIdentityCardHaveInList(idCar));
        staff.setIdentityCard(idCar);
    }

    public  void editNumberPhoneOfStaff(Staff staff){
        String numberPhone = "";
        do {
            System.out.print("Nhập số điện thoại mới nhân viên : ");
            numberPhone = input.nextLine();
            if (!isFormatNumberPhone(numberPhone)) {
                System.out.println("Định dạng số điện chưa hợp lệ !  ");
            }
        } while (!isFormatNumberPhone(numberPhone));
        staff.setNumberPhone(numberPhone);
    }

    public void editAddresssOfStaff(Staff staff){
        String address = "";
        System.out.print("Nhập địa chỉ mới của nhân viên : ");
        address = input.nextLine();
        staff.setAddress(address);
    }

    public void editPayOfStaff(Staff staff){
        String strPay = "";
        do {
            System.out.print("Nhập mức lương mới của nhân viên : ");
            strPay = input.nextLine();
            if (!isFormatPay(strPay)) {
                System.out.println("Định dạng tiền lương chưa hợp lý !");
            }
        } while (!isFormatPay(strPay));
        staff.setPayStaff(Long.parseLong(strPay));
    }

    public void editOtherOfStaff(Staff staff){
        String other = "";
        System.out.print("Thay đổi thông tin khác cho nhân viên (có thể bỏ trống) !");
        other = input.nextLine();
        staff.setOther(other);
    }


    public void menuStaffManager() {
        char choice = ' ';
        do {
            System.out.println("-----------------------------------------------------");
            System.out.println("|             QUẢN LÝ NHÂN VIÊN                      |");
            System.out.println("-----------------------------------------------------");
            System.out.println("|  1. Thêm nhân viên                                 |");
            System.out.println("|  2. Thay đổi thông tin nhân viên                   |");
            System.out.println("|  3. Xóa nhân viên                                  |");
            System.out.println("|  4. Tìm kiếm thông tin nhân viên                   |");
            System.out.println("|  5. Hiển thị danh sách nhân viên                   |");
            System.out.println("|  6. Xuất file thông tin nhân viên                  |");
            System.out.println("|                                        0. Quay lại |");
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
                    addStaff();
                    break;
                case '2':
                    editStaff();
                    break;
                case '3':
                    deleteStaff();
                    break;
                case '4':
                    searchStaff();
                    break;
                case '5':
                    optionDisplayStaff();
                    break;
                case '6':
                    exportFileStaffToCsv();
                    break;
                case '0':
                    Menu.menuWorkWithAdmin();
                    break;
                default:
                    System.out.println("Chọn theo menu !");
            }
        } while (choice != '0');
    }
}
