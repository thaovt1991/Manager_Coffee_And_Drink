package service;

import model.Drinks;
import sort.sortDrinks.*;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrinksManager implements Serializable {
    public ArrayList<Drinks> drinksList;
    static Scanner input = new Scanner(System.in);
    public static final String ID_REGEX = "[A-Z]{2}+\\d{3}$";
    public static final String NAME_REGEX = "^([AÀẢÃÁẠĂẰẮẲẴẶÂẤẦẨẪẬBCDĐEÈÉẺẼẸÊỀẾỂỄỆFGHIÍÌỈĨỊJKLMNOÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢPQRSTUÙÚỦŨỤƯỪỨỬỮỰVWXYÝỲỶỸỴZ]+[aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]*+[ ]*)+$";
    public static final String QUALITY_REGEX = "^[0-9]{1,9}$"; //int
    public static final String PRIME_REGEX = "^[1-9][0-9]{1,14}[0]{3}$";
    public static final String LINK_REGEX = "(^([C|D][:])\\\\(?:[\\w]+\\\\)*\\w+$)|(^[C|D][:][\\\\]$)";
    public static final String FORMAT_CSV_DRINKS = "ID,NAME,QUALITY,PRICE,OTHER";
    public static final String DOWN_THE_LINE = "\n";
    public static final String COMMA_DELIMITER = ",";
    public static final String SAVE_OBJECT_DRINKS = "D:\\Manager_Coffee_And_Drink\\src\\data\\list_drinks.txt";
    public static final String SAVE_FORMAT_CSV_DRINKS = "D:\\Manager_Coffee_And_Drink\\out_data\\list_drinks.csv";

    public DrinksManager() {
        drinksList = readDataFromFile(SAVE_OBJECT_DRINKS);
    }

    public DrinksManager(ArrayList<Drinks> drinksList) {
        this.drinksList = drinksList;
    }


    public boolean isEmpty() {
        if (drinksList.isEmpty()) return true;
        return false;
    }

    public boolean isHaveIdDrinksList(String id) {
        if (!isIdFormat(id)) return false;
        if (!isEmpty()) {
            for (Drinks dr : drinksList) {
                if (dr.getIdDrink().equals(id)) return true;
            }
        }
        return false;
    }

    public boolean isHaveNameDrinksList(String name) {
        if (!isNameFormat(name)) return false;
        if (!isEmpty()) {
            for (Drinks dr : drinksList) {
                if (dr.getNameDrink().equals(name)) return true;
            }
        }
        return false;
    }

    public void addDrinksList() {
        String id;
        String name;
        int quality = 0;
        long price = 0;
        String other;
        do {
            System.out.print("Nhập id thức uống : ");
            id = input.nextLine();
            if (!isIdFormat(id)) {
                System.out.println("Định dạng id phải có dạng 'AA001' !");
            } else {
                if (isHaveIdDrinksList(id))
                    System.out.println("Id đã có trong danh sách thức uống");
            }
        } while (isHaveIdDrinksList(id) || !isIdFormat(id));
        do {
            System.out.print("Nhập tên thức uống : ");
            name = input.nextLine();
            if (!isNameFormat(name)) {
                System.out.println("Định dạng tên sai (ví dụ : 'Coffee Đen Đá' !");
            } else {
                if (isHaveNameDrinksList(name))
                    System.out.println("Tên thức uống đã có trong danh sách");
            }
        }
        while (isHaveNameDrinksList(name) || !isNameFormat(name));
        String q;
        do {
            System.out.print("Nhập số lượng thức uống : ");
            q = input.nextLine();
            if (!isQualityFormat(q)) {
                System.out.println("Định dạng số lượng sai !");
            } else {
                quality = Integer.parseInt(q);
            }
        } while (!isQualityFormat(q));
        String strPrice;
        do {
            System.out.print("Nhập giá thức uống : ");
            strPrice = input.nextLine();
            if (!isPriceFormat(strPrice)) {
                System.out.println("Định dạng giá thức uống sai !");
            } else {
                price = Long.parseLong(strPrice);
            }
        } while (!isPriceFormat(strPrice));
        System.out.println("Mô tả về thức uống :");
        other = input.nextLine();
        Drinks drink = new Drinks(id, name, quality, price, other);
        System.out.println("Thức uống mới : " + drink);
        System.out.println("Bạn muốn thêm '" + drink.getNameDrink() + "' vào danh sách thức uống của quán !");
        char press = ' ';
        boolean isChoice = true;
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
                    drinksList.add(drink);
                    writeToFile(SAVE_OBJECT_DRINKS, drinksList);
                    writeDataFromFileFormatToCsv(SAVE_FORMAT_CSV_DRINKS, drinksList);
                    isChoice = false;
                    break;
                }
                case 'n':
                case 'N':
                    menuDrinksManager();
                    isChoice = false;
                    break;
                default:
                    isChoice = true;
            }
        } while (isChoice);
    }

    public void displayMenuDrinks() {
        displayDrinkFornmat();
        char press = ' ';
        boolean isChoice = true;
        do {
            System.out.print("Nhấn 'R' để quay trở về menu quản lý thức uống !");
            try {
                press = input.nextLine().charAt(0);
            } catch (Exception e) {
                press = ' ';
            }
            switch (press) {
                case 'r':
                case 'R': {
                    menuDrinksManager();
                    isChoice = false;
                    break;
                }
                default:
                    isChoice = true;
            }
        } while (isChoice);
    }

    public void editDrink() {
        char choice = ' ';
        do {
            System.out.println("-----------------LỰA CHỌN THAY ĐỔI THÔNG TIN-------------------");
            System.out.println("|  1. Sửa thông tin thức uống theo ID                          |");
            System.out.println("|  2. Sửa thông tin thức uống theo tên                         |");
            System.out.println("|  0. Quay lại                                                 |");
            System.out.println("----------------------------------------------------------------");
            System.out.println();
            System.out.println("Lựa chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    editDrinksById();
                    break;
                case '2':
                    editDrinksByName();
                    break;
                case '0':
                    menuDrinksManager();
                    break;
                default:
                    System.out.println("Lựa chọn theo menu trên !");
            }
        } while (choice != '0');
    }

    //sua theo id
    public void editDrinksById() {
        displayFullDrinks();
        String id = "";
        System.out.print("Nhập id thức uống cần sửa : ");
        id = input.nextLine();
        if (!isIdFormat(id)) {
            System.out.println("Định dạng id phải có dạng 'AA001' !");
            editDrink();
        } else {
            if (!isHaveIdDrinksList(id)) {
                System.out.println("Id không có trong danh sách thức uống !");
                editDrink();
            } else {
                for (Drinks dr : drinksList) {
                    if (dr.getIdDrink().equals(id)) {
                        System.out.println("Thức uống cần tìm là : ");
                        displayOneDrinks(dr);
                        System.out.println("Có phải bạn muốn sửa sản phẩm này !");

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
                                    editDrinkOption(dr);
                                    isChoice = false;
                                    break;
                                case 'n':
                                case 'N':
                                    editDrink();
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

    // edit theo ten
    public void editDrinksByName() {
        displayFullDrinks();
        String name = "";
        do {
            System.out.print("Nhập tên thức uống cần sửa chữa thông tin : ");
            name = input.nextLine();
            if (!isNameFormat(name)) {
                System.out.println("Định dạng tên sai (ví dụ : 'Coffee Đen Đá' !");
                editDrink();
            } else {
                if (!isHaveNameDrinksList(name)) {
                    System.out.println("Tên thức uống không có trong danh sách");
                    editDrink();
                } else {
                    for (Drinks dr : drinksList) {
                        if (dr.getNameDrink().equals(name)) {
                            System.out.println("Thức uống cần tìm là : ");
                            displayOneDrinks(dr);
                            System.out.println("Có phải bạn muốn sửa sản phẩm này !");

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
                                        editDrinkOption(dr);
                                        isChoice = false;
                                        break;

                                    case 'n':
                                    case 'N':
                                        editDrink();
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
        while (isHaveNameDrinksList(name) || !isNameFormat(name));
    }

    public void editDrinkOption(Drinks drinks) {
        char choice = ' ';
        boolean isChoice = true;
        do {
            System.out.println("----------Thay đổi thông tin thức uống--------------------");
            System.out.println("|   1. Thay đổi id thức uống                             |");
            System.out.println("|   2. Thay đổi tên thức uống                            |");
            System.out.println("|   3. Thay đổi số lượng thức uống                       |");
            System.out.println("|   4. Thay đổi giá thức uống                            |");
            System.out.println("|   5. Thay đổi thông tin khác liên quan đến thức uống   |");
            System.out.println("|   6. Thay đổi toàn bộ thông tin thức uống              |");
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
                    System.out.println("----------Thay đổi id thức uống ----------");
                    editIdDrink(drinks);
                    break;
                case '2':
                    System.out.println("---------Thay đổi tên thức uống ----------");
                    editNameDrink(drinks);
                    break;
                case '3':
                    System.out.println("----------Thay đổi số lượng thức uống----------");
                    editQualityDrinks(drinks);
                    break;
                case '4':
                    System.out.println("----------Thay đổi giá thức uống-----------");
                    editPriceDrinks(drinks);
                    break;
                case '5':
                    System.out.println("----------Thay đổi thông tin thức uống-----------");
                    editOtherDescription(drinks);
                    break;
                case '6':
                    System.out.println("----------Thay đổi toàn bộ thông tin thức uống--------");
                    editIdDrink(drinks);
                    editNameDrink(drinks);
                    editQualityDrinks(drinks);
                    editPriceDrinks(drinks);
                    editOtherDescription(drinks);
                    break;
                case '7':
                    drinksList = readDataFromFile(SAVE_OBJECT_DRINKS);
                    editDrink();
                    isChoice = false;
                    break;
                case '8':
                    writeToFile(SAVE_OBJECT_DRINKS, drinksList);
                    writeDataFromFileFormatToCsv(SAVE_FORMAT_CSV_DRINKS, drinksList);
                    System.out.println("Menu sau khi sửa là :");
                    displayDrinkFornmat();
                    editDrink();
                    isChoice = false;
                    break;
                default:
                    System.out.println("Hãy chọn theo yêu cầu của menu ở trên !");
            }
        } while (isChoice);
    }

 ////fornmat theo thu tu id
    public void displayDrinkFornmat() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, name, ql, pr;
        SortIdDrinksAZ sortIdAZ = new SortIdDrinksAZ(); //for mat theo id
        Collections.sort(drinksList, sortIdAZ);
        System.out.println("------------------------------------------MENU COFFEE AND DRINKS----------------------------------------------");
        System.out.printf("%-3s%-12s%-12s%-40s%-20s%-23s%s\n", "|", "STT", "ID", "TÊN THỨC UỐNG", "SỐ LƯỢNG", "GIÁ (VND)", "|");
        for (Drinks dr : drinksList) {
            count++;
            stt = String.valueOf(count);
            id = dr.getIdDrink();
            name = dr.getNameDrink();
            ql = String.valueOf(dr.getQualityDrink());
            pr = formater.format(dr.getPriceDrink());
            System.out.printf("%-3s%-12s%-12s%-40s%-20s%-23s%s\n", "|", stt, id, name, ql, pr, "|");
        }
        System.out.println("------------------------------------------######################-----------------------------------------------");
        drinksList = readDataFromFile(SAVE_OBJECT_DRINKS);
    }

    public void editIdDrink(Drinks drinks) {
        String newId = "";
        do {
            System.out.print("Nhập id mới cho thức uống : ");
            newId = input.nextLine();
            if (!isIdFormat(newId)) {
                System.out.println("Định dạng id phải có dạng 'AA001' !");
            } else {
                if (isHaveIdDrinksList(newId))
                    System.out.println("Id đã có trong danh sách thức uống");
            }
        } while (isHaveIdDrinksList(newId) || !isIdFormat(newId));
        drinks.setIdDrink(newId);
    }


    public void editNameDrink(Drinks drinks) {
        String newName = "";
        do {
            System.out.print("Nhập tên mới cho thức uống : ");
            newName = input.nextLine();
            if (!isNameFormat(newName)) {
                System.out.println("Định dạng tên sai (ví dụ tên : 'Coffee Den Da' !");
            } else {
                if (isHaveNameDrinksList(newName))
                    System.out.println("Tên thức uống đã có trong danh sách !");
            }
        }
        while (isHaveNameDrinksList(newName) || !isNameFormat(newName));
        drinks.setNameDrink(newName);
    }


    public void editQualityDrinks(Drinks drinks) {
        String q = "";
        int quality = 0;
        do {
            System.out.print("Nhập số lượng mới cho thức uống : ");
            q = input.nextLine();
            if (!isQualityFormat(q)) {
                System.out.println("Định dạng số lượng sai !");
            } else {
                quality = Integer.parseInt(q);
            }
        } while (!isQualityFormat(q));
        drinks.setQualityDrink(quality);
    }


    public void editPriceDrinks(Drinks drinks) {
        String strPrice = "";
        long price = 0;
        do {
            System.out.print("Nhập giá mới cho thức uống : ");
            strPrice = input.nextLine();
            if (!isPriceFormat(strPrice)) {
                System.out.println("Định dạng giá thức uống sai !");
            } else {
                price = Long.parseLong(strPrice);
            }
        } while (!isPriceFormat(strPrice));
        drinks.setPriceDrink(price);
    }

    public void editOtherDescription(Drinks drinks) {
        System.out.println("Mô tả mới về thức uống :");
        String other = input.nextLine();
        drinks.setOtherDescription(other);
    }

    //delete
    public void deleteDrink() {
        char choice = ' ';
        do {
            System.out.println("-----------------LỰA CHỌN THỨC UỐNG MUỐN XÓA-------------------");
            System.out.println("|  1. Xóa theo ID của thức uống                                |");
            System.out.println("|  2. Xóa theo tên thức uống                                   |");
            System.out.println("|  0. Quay lại                                                 |");
            System.out.println("----------------------------------------------------------------");
            System.out.println();
            System.out.println("Lựa chọn : ");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    deteleDrinkById();
                    break;
                case '2':
                    deteleDrinkByName();
                    break;
                case '0':
                    menuDrinksManager();
                    break;
                default:
                    System.out.println("Lựa chọn theo menu trên !");
            }
        } while (choice != '0');
    }

    public void deteleDrinkById() {
        displayFullDrinks();
        String id = "";
        System.out.print("Nhập id thức uống muốn xóa : ");
        id = input.nextLine();
        if (!isIdFormat(id)) {
            System.out.println("Định dạng id phải có dạng 'AA001' !");
            deleteDrink();
        } else {
            if (!isHaveIdDrinksList(id)) {
                System.out.println("Id không có trong danh sách thức uống !");
                deleteDrink();
            } else {
                for (Drinks drinks : drinksList) {
                    if (drinks.getIdDrink().equals(id)) {
                        deteleDrinksInList(drinks);
                        break;
                    }
                }
            }
        }
    }

    public void deteleDrinkByName() {
        displayFullDrinks();
        String name = "";
        System.out.print("Nhập tên thức uống cần xóa : ");
        name = input.nextLine();
        if (!isNameFormat(name)) {
            System.out.println("Định dạng tên sai (ví dụ : 'Coffee Đen Đá' !");
            deleteDrink();
        } else {
            if (!isHaveNameDrinksList(name)) {
                System.out.println("Tên thức uống không có trong danh sách");
                deleteDrink();
            } else {
                for (Drinks drinks : drinksList) {
                    if (drinks.getNameDrink().equals(name)) {
                        deteleDrinksInList(drinks);
                        break;
                    }
                }
            }
        }
    }

    public void deteleDrinksInList(Drinks drinks) {
        drinksList.remove(drinks);
        System.out.println("Menu thức uống sau khi xóa '" + drinks.getNameDrink() + "'");
        displayDrinkFornmat();
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
                    writeToFile(SAVE_OBJECT_DRINKS, drinksList);
                    writeDataFromFileFormatToCsv(SAVE_FORMAT_CSV_DRINKS, drinksList);
                    isChoice = false;
                    deleteDrink();
                    break;
                case 'n':
                case 'N':
                    drinksList = readDataFromFile(SAVE_OBJECT_DRINKS);
                    isChoice = false;
                    deleteDrink();
                    break;
                default:
                    System.out.println();
            }
        } while (isChoice);
    }

    //Hien thi danh sach
    public void optionDisplay() {
        char choice = ' ';
        boolean isChoice = true;
        do {
            System.out.println("----------------------LỰA CHỌN HIỂN THỊ------------------------");
            System.out.println("|  1. Hiển thị sắp xếp theo id thức uống                       |");
            System.out.println("|  2. Hiển thị sắp xếp theo tên thức uống                      |");
            System.out.println("|  3. Hiển thị sắp xếp theo số lượng                           |");
            System.out.println("|  4. Hiển thị sắp xếp theo giá                                |");
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
                    displayDrinkById();
                    break;
                case '2':
                    displayDrinkByName();
                    break;
                case '3':
                    displayDrinkByQuality();
                    break;
                case '4':
                    displayDrinkByPrice();
                    break;
                case '0':
                    menuDrinksManager();
                default:
                    System.out.println("Chọn theo menu !");
            }

        } while (isChoice);
    }

    public void displayDrinkById() {
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("--------------SẮP XẾP THEO ID THỨC UỐNG--------------");
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
                    System.out.println("Sắp xếp theo ID từ A-Z");
                    SortIdDrinksAZ sortIdAZ = new SortIdDrinksAZ();
                    Collections.sort(drinksList, sortIdAZ);
                    displayFullDrinks();
                    drinksList = readDataFromFile(SAVE_OBJECT_DRINKS);
                    break;
                case '2':
                    System.out.println("Sắp xếp theo ID từ Z-A");
                    SortIdDrinksZA sortIdZA = new SortIdDrinksZA();
                    Collections.sort(drinksList, sortIdZA);
                    displayFullDrinks();
                    drinksList = readDataFromFile(SAVE_OBJECT_DRINKS);
                    break;
                case '0':
                    optionDisplay();
                    isChoice = false;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (isChoice);
    }

    public void displayDrinkByName() {
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("--------------SẮP XẾP THEO TÊN THỨC UỐNG--------------");
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
                    System.out.println("Sắp xếp theo tên từ A-Z");
                    SortToNameDrinksAZ sortNameAZ = new SortToNameDrinksAZ();
                    Collections.sort(drinksList, sortNameAZ);
                    displayFullDrinks();
                    drinksList = readDataFromFile(SAVE_OBJECT_DRINKS);
                    break;
                case '2':
                    System.out.println("Sắp xếp theo tên từ Z-A");
                    SortToNameDrinksZA sortNameZA = new SortToNameDrinksZA();
                    Collections.sort(drinksList, sortNameZA);
                    displayFullDrinks();
                    drinksList = readDataFromFile(SAVE_OBJECT_DRINKS);
                    break;
                case '0':
                    optionDisplay();
                    isChoice = false;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (isChoice);
    }

    public void displayDrinkByQuality() {
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("-----------SẮP XẾP THEO SỐ LƯỢNG THỨC UỐNG------------");
            System.out.println("| 1. Theo thứ tự từ tăng dần                          |");
            System.out.println("| 2. Theo thứ tự từ giảm dần                          |");
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
                    System.out.println("Sắp xếp theo số lượng tăng dần !");
                    SortQualityDrinksAscending sortQualityDrinksAscending = new SortQualityDrinksAscending();
                    Collections.sort(drinksList, sortQualityDrinksAscending);
                    displayFullDrinks();
                    drinksList = readDataFromFile(SAVE_OBJECT_DRINKS);
                    break;
                case '2':
                    System.out.println("Sắp xếp theo số lượng giảm dần");
                    SortQualityDrinksDecrease sortQualityDrinksDecrease = new SortQualityDrinksDecrease();
                    Collections.sort(drinksList, sortQualityDrinksDecrease);
                    displayFullDrinks();
                    drinksList = readDataFromFile(SAVE_OBJECT_DRINKS);
                    break;
                case '0':
                    optionDisplay();
                    isChoice = false;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (isChoice);
    }

    public void displayDrinkByPrice() {
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("-----------SẮP XẾP THEO GIÁ THỨC UỐNG-----------------");
            System.out.println("| 1. Theo thứ tự từ tăng dần                          |");
            System.out.println("| 2. Theo thứ tự từ giảm dần                          |");
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
                    System.out.println("Sắp xếp theo giá thức uống tăng dần !");
                    SortPriceDrinksAscending sortPriceDrinksAscending = new SortPriceDrinksAscending();
                    Collections.sort(drinksList, sortPriceDrinksAscending);
                    displayFullDrinks();
                    drinksList = readDataFromFile(SAVE_OBJECT_DRINKS);
                    break;
                case '2':
                    System.out.println("Sắp xếp theo giá thức uống giảm dần");
                    SortPriceDrinksDecrease sortPriceDrinksDecrease = new SortPriceDrinksDecrease();
                    Collections.sort(drinksList, sortPriceDrinksDecrease);
                    displayFullDrinks();
                    drinksList = readDataFromFile(SAVE_OBJECT_DRINKS);
                    break;
                case '0':
                    optionDisplay();
                    isChoice = false;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (isChoice);
    }


    public void displayFullDrinks() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, name, ql, pr, other;
        System.out.println();
        System.out.printf("%-3s%-12s%-12s%-40s%-20s%-23s%s\n", "", "STT", "ID", "TÊN THỨC UỐNG", "SỐ LƯỢNG", "GIÁ (VND)", "THÔNG TIN KHÁC");
        for (Drinks dr : drinksList) {
            count++;
            stt = String.valueOf(count);
            id = dr.getIdDrink();
            name = dr.getNameDrink();
            ql = String.valueOf(dr.getQualityDrink());
            pr = formater.format(dr.getPriceDrink());
            other = dr.getOtherDescription();
            System.out.printf("%-3s%-12s%-12s%-40s%-20s%-23s%s\n", "", stt, id, name, ql, pr, other);
        }
        System.out.println();
    }

    public void displayOneDrinks(Drinks drinks) {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        String  id, name, ql, pr, other;
        System.out.println();
        System.out.printf("%-3s%-12s%-40s%-20s%-23s%s\n", "", "ID", "TÊN THỨC UỐNG", "SỐ LƯỢNG", "GIÁ (VND)", "THÔNG TIN KHÁC");
        id = drinks.getIdDrink();
        name = drinks.getNameDrink();
        ql = String.valueOf(drinks.getQualityDrink());
        pr = formater.format(drinks.getPriceDrink());
        other = drinks.getOtherDescription();
        System.out.printf("%-3s%-12s%-40s%-20s%-23s%s\n", "", id, name, ql, pr, other);
    }

    public boolean isIdFormat(String id) {
        Pattern pattern = Pattern.compile(ID_REGEX);
        Matcher matcher = pattern.matcher(id);
        return matcher.matches();
    }

    public boolean isNameFormat(String name) {
        Pattern pattern = Pattern.compile(NAME_REGEX);
        Matcher matcher = pattern.matcher(name);
        return matcher.matches();
    }

    public boolean isQualityFormat(String quality) {
        Pattern pattern = Pattern.compile(QUALITY_REGEX);
        Matcher matcher = pattern.matcher(quality);
        return matcher.matches();
    }

    public boolean isPriceFormat(String strPrice) {
        Pattern pattern = Pattern.compile(PRIME_REGEX);
        Matcher matcher = pattern.matcher(strPrice);
        return matcher.matches();
    }

    public ArrayList<Drinks> readDataFromFile(String path) {
        ArrayList<Drinks> listDrinks = new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            listDrinks = (ArrayList<Drinks>) ois.readObject();
            fis.close();
            ois.close();
        } catch (Exception ex) {
            System.out.println("File chưa tồn tại, hãy nhập dữ liệu và tạo ra nó !");
        }
        return listDrinks;
    }

    public void writeToFile(String path, ArrayList<Drinks> listDrinks) {
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


    public void writeDataFromFileFormatToCsv(String path, ArrayList<Drinks> listDrink) {
        FileWriter writer = null;
        try {
            writer = new FileWriter(path);
            writer.append(FORMAT_CSV_DRINKS);
            writer.append(DOWN_THE_LINE);
            for (Drinks drink : listDrink) {
                writer.append(drink.getIdDrink());
                writer.append(COMMA_DELIMITER);
                writer.append(drink.getNameDrink());
                writer.append(COMMA_DELIMITER);
                writer.append(String.valueOf(drink.getQualityDrink()));
                writer.append(COMMA_DELIMITER);
                writer.append(String.valueOf(drink.getPriceDrink()));
                writer.append(COMMA_DELIMITER);
                writer.append(drink.getOtherDescription());
                writer.append(DOWN_THE_LINE);
            }
            writer.close();
        } catch (Exception e) {
        }
    }


    public void searchDrink() {
        boolean isChoice = true;
        char choice = ' ';
        do {
            System.out.println("----------------------TÌM KIẾM THỨC UỐNG----------------------------");
            System.out.println("| 1. Tìm kiếm theo ID thức uống                                     |");
            System.out.println("| 2. Tìm kiếm theo tên thức uống                                    |");
            System.out.println("| 3. Tìm kiếm theo số lượng thức uống                               |");
            System.out.println("| 4. Tìm kiếm theo giá thức uống                                    |");
            System.out.println("| 5. Tìm kiếm thông tin khác của thức uống                          |");
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
                    searchById();
                    break;
                case '2':
                    searchByName();
                    break;
                case '3':
                    searchByQuality();
                    break;
                case '4':
                    searchByPrice();
                    break;
                case '5':
                    searchByOtherDescription();
                    break;
                case '0':
                    menuDrinksManager();
                    isChoice = false;
                    break;
                default:
                    System.out.println("Hãy chọn theo menu tìm kiếm !");
            }

        } while (isChoice);
    }


    public void searchById() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, name, ql, pr, other;
        System.out.println();
        System.out.print("Nhập ID thức uống cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-3s%-12s%-12s%-40s%-20s%-23s%s\n", "", "STT", "ID", "TÊN THỨC UỐNG", "SỐ LƯỢNG", "GIÁ (VND)", "THÔNG TIN KHÁC");
        for (Drinks drinks : drinksList) {
            if (drinks.getIdDrink().toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = drinks.getIdDrink();
                name = drinks.getNameDrink();
                ql = String.valueOf(drinks.getQualityDrink());
                pr = formater.format(drinks.getPriceDrink());
                other = drinks.getOtherDescription();
                System.out.printf("%-3s%-12s%-12s%-40s%-20s%-23s%s\n", "", stt, id, name, ql, pr, other);
            }
        }
        displayReturnSearch(count);
    }

    public void searchByName() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, name, ql, pr, other;
        System.out.println();
        System.out.print("Nhập tên thức uống cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-3s%-12s%-12s%-40s%-20s%-23s%s\n", "", "STT", "ID", "TÊN THỨC UỐNG", "SỐ LƯỢNG", "GIÁ (VND)", "THÔNG TIN KHÁC");
        for (Drinks drinks : drinksList) {
            if (drinks.getNameDrink().toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = drinks.getIdDrink();
                name = drinks.getNameDrink();
                ql = String.valueOf(drinks.getQualityDrink());
                pr = formater.format(drinks.getPriceDrink());
                other = drinks.getOtherDescription();
                System.out.printf("%-3s%-12s%-12s%-40s%-20s%-23s%s\n", "", stt, id, name, ql, pr, other);
            }
        }
        displayReturnSearch(count);
    }

    public void searchByQuality() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, name, ql, pr, other;
        System.out.println();
        System.out.print("Nhập số lượng của thức uống cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-3s%-12s%-12s%-40s%-20s%-23s%s\n", "", "STT", "ID", "TÊN THỨC UỐNG", "SỐ LƯỢNG", "GIÁ (VND)", "THÔNG TIN KHÁC");
        for (Drinks drinks : drinksList) {
            if (String.valueOf(drinks.getQualityDrink()).toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = drinks.getIdDrink();
                name = drinks.getNameDrink();
                ql = String.valueOf(drinks.getQualityDrink());
                pr = formater.format(drinks.getPriceDrink());
                other = drinks.getOtherDescription();
                System.out.printf("%-3s%-12s%-12s%-40s%-20s%-23s%s\n", "", stt, id, name, ql, pr, other);
            }
        }
        displayReturnSearch(count);
    }

    public void searchByPrice() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, name, ql, pr, other;
        System.out.println();
        System.out.print("Nhập giá thức uống cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-3s%-12s%-12s%-40s%-20s%-23s%s\n", "", "STT", "ID", "TÊN THỨC UỐNG", "SỐ LƯỢNG", "GIÁ (VND)", "THÔNG TIN KHÁC");
        for (Drinks drinks : drinksList) {
            if (String.valueOf(drinks.getPriceDrink()).toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = drinks.getIdDrink();
                name = drinks.getNameDrink();
                ql = String.valueOf(drinks.getQualityDrink());
                pr = formater.format(drinks.getPriceDrink());
                other = drinks.getOtherDescription();
                System.out.printf("%-3s%-12s%-12s%-40s%-20s%-23s%s\n", "", stt, id, name, ql, pr, other);
            }
        }
        displayReturnSearch(count);
    }

    public void searchByOtherDescription() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, name, ql, pr, other;
        System.out.println();
        System.out.print("Nhập thông tin thức uống cần tìm kiếm : ");
        String search = input.nextLine();
        System.out.println("Kết quả tìm kiếm của từ khóa '" + search + "' là : ");
        search = search.toLowerCase();
        System.out.printf("%-3s%-12s%-12s%-40s%-20s%-23s%s\n", "", "STT", "ID", "TÊN THỨC UỐNG", "SỐ LƯỢNG", "GIÁ (VND)", "THÔNG TIN KHÁC");
        for (Drinks drinks : drinksList) {
            if (drinks.getOtherDescription().toLowerCase().contains(search)) {
                count++;
                stt = String.valueOf(count);
                id = drinks.getIdDrink();
                name = drinks.getNameDrink();
                ql = String.valueOf(drinks.getQualityDrink());
                pr = formater.format(drinks.getPriceDrink());
                other = drinks.getOtherDescription();
                System.out.printf("%-3s%-12s%-12s%-40s%-20s%-23s%s\n", "", stt, id, name, ql, pr, other);
            }
        }
        displayReturnSearch(count);
    }

    private void displayReturnSearch(int count) {
        System.out.println("Có '" + count + "' thức uống được tìm thấy !");
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
                    searchDrink();
                    isChoice = false;
                    break;
                }
                default:
                    isChoice = true;
            }
        } while (isChoice);
    }

    public void exportDataDrinksToCsv() {
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
                writeDataFromFileFormatToCsv(linkFull, drinksList);
                System.out.println("Đã xuất file thành công đến đường dẫn : " + linkFull);
                System.out.println();
                menuDrinksManager();
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
                            writeDataFromFileFormatToCsv(linkFull, drinksList);
                            System.out.println("Đã xuất file thành công đến đường dẫn : " + linkFull);
                            isChoice = false;
                            isPress = false;
                            menuDrinksManager();
                            break;
                        case 'n':
                        case 'N':
                            exportDataDrinksToCsv();
                            isPress = false;
                            break;
                        case 'R':
                        case 'r':
                            isChoice = false;
                            isPress = false;
                            menuDrinksManager();
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

    public void menuDrinksManager() {
        boolean isChoice = true;
        do {
            System.out.println("-------------------QUẢN LÝ THỨC UỐNG------------------------");
            System.out.println("| 1. Thêm thức uống mới                                    |");
            System.out.println("| 2. Sửa thông tin thức uống                               |");
            System.out.println("| 3. Xóa thức uống khỏi danh sách thức uống                |");
            System.out.println("| 4. Tìm kiếm thức uống                                    |");
            System.out.println("| 5. Hiển thị format menu                                  |");
            System.out.println("| 6. Hiển thị thông tin toàn bộ thức uống theo thứ tự      |");
            System.out.println("| 7. Xuất file thông tin thức uống                         |");
            System.out.println("| 0 . Quay lại menu chính                                  |");
            System.out.println("------------------------------------------------------------");
            System.out.println();
            System.out.print("Chọn : ");
            char choice = ' ';
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                menuDrinksManager();
            }

            switch (choice) {
                case '1':
                    addDrinksList();
                    break;
                case '2':
                    editDrink();
                    break;
                case '3':
                    deleteDrink();
                    break;
                case '4':
                    searchDrink();
                    break;
                case '5':
                    displayMenuDrinks();
                    break;
                case '6':
                    optionDisplay();
                    break;
                case '7':
                    exportDataDrinksToCsv();
                    break;
                case '0':
                    System.out.println("quay lai menu chinh");///dien menu chonh vao xos cai kia
                    System.exit(0);
                    isChoice = false;
                    break;
                default:
                    System.out.println("Chọn lại !");
            }
        } while (isChoice);
    }
}

