package service;

import model.Drinks;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DrinksManager implements Serializable {
    public ArrayList<Drinks> drinksList;
    static Scanner input = new Scanner(System.in);
    public static final String ID_REGEX = "[A-Z]{2}+\\d{3}$";
    public static final String NAME_REGEX = "^([AÀẢÃÁẠĂẰẮẲẴẶÂẤẦẨẪẬBCDĐEÈÉẺẼẸÊỀẾỂỄỆFGHIÍÌỈĨỊJKLMNOÒÓỎÕỌÔỒỐỔỖỘƠỜỚỞỠỢPQRSTUÙÚỦŨỤƯỪỨỬỮỰVWXYÝỲỶỸỴZ]+[aàảãáạăằẳẵắặâầẩẫấậbcdđeèẻẽéẹêềểễếệfghiìỉĩíịjklmnoòỏõóọôồổỗốộơờởỡớợpqrstuùủũúụưừửữứựvwxyỳỷỹýỵz]+[ ]*)+$";
    public static final String QUALITY_REGEX = "^[0-9]{1,9}$"; //int
    public static final String PRIME_REGEX = "^[1-9][0-9]{1,14}[0]{3}$";
    public static final String FORMAT_CSV = "ID,NAME,QUALITY,PRICE,OTHER";
    public static final String DOWN_THE_LINE = "\n";
    public static final String COMMA_DELIMITER = ",";
    public static final String SAVE_OBJECT_DRINKS = "D:\\Manager_Coffee_And_Drink\\src\\data\\list_drinks.txt";
    public static final String SAVE_FORMAT_CSV = "D:\\Manager_Coffee_And_Drink\\out_data\\list_drinks.csv";

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
        char press = 'x';
        boolean isChoice = true;
        do {
            System.out.print("Nhấn 'Y' để đồng ý ! Nhấn 'N' để hủy bỏ thao tác !");
            press = input.nextLine().charAt(0);
            switch (press) {
                case 'Y':
                case 'y': {
                    drinksList.add(drink);
                    writeToFile(SAVE_OBJECT_DRINKS, drinksList);
                    writeDataFromFileFormatToCsv(SAVE_FORMAT_CSV, drinksList);
                    isChoice = false;
                    break;
                }
                case 'n':
                case 'N':
                    addDrinksList();
                    isChoice = false;
                    break;
                default:
                    isChoice = true;
            }
        } while (isChoice);
    }

    public void displayMenuDrinks() {
        displayDrinkFornmat();
        char press = 'x';
        boolean isChoice = true;
        do {
            System.out.print("Nhấn 'R' để quay trở về menu quản lý thức uống !");
            press = input.nextLine().charAt(0);
            switch (press) {
                case 'r':
                case 'R': {
                    System.out.println("gọi lai menu drink mânger");//thay lenh goi menu sau
                    isChoice = false;
                    break;
                }
                default:
                    isChoice = true;
            }
        } while (isChoice);
    }

    public void editDrink() {
        char choice = 'x';
        do {
            System.out.println("-----------------LỰA CHỌN THAY ĐỔI THÔNG TIN-------------------");
            System.out.println("|  1. Sửa thông tin thức uống theo ID                          |");
            System.out.println("|  2. Sửa thông tin thức uống theo tên                         |");
            System.out.println("|  0. Quay lại                                                 |");
            System.out.println("----------------------------------------------------------------");
            System.out.println();
            System.out.println("Lựa chọn : ");
            choice = input.nextLine().charAt(0);
            switch (choice) {
                case '1':
                    editDrinksById();
                    break;
                case '2':
                    editDrinksByName();
                    break;
                case '0':
                    //goi mene manager ;
                    break;
                default:
                    System.out.println("Lựa chọn theo menu trên !");
            }
        } while (choice != '0');
    }


    //sua theo id
    public void editDrinksById() {
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
                        System.out.println(dr);
                        System.out.println("Có phải bạn muốn sửa sản phẩm này !");

                        char press = 'x';
                        boolean isChoice = true;
                        do {
                            System.out.println("Nhấn 'Y' để tiếp tục, nhấn 'N' để tìm kiếm lại ");
                            press = input.nextLine().charAt(0);
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
                            System.out.println(dr);
                            System.out.println("Có phải bạn muốn sửa sản phẩm này !");

                            char press = 'x';
                            boolean isChoice = true;
                            do {
                                System.out.println("Nhấn 'Y' để tiếp tục, nhấn 'N' để tìm kiếm lại ");
                                press = input.nextLine().charAt(0);
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
        char choice;
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
            choice = input.nextLine().charAt(0);

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
                    writeDataFromFileFormatToCsv(SAVE_FORMAT_CSV, drinksList);
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


    public void displayDrinkFornmat() {
        DecimalFormat formater = new DecimalFormat("###,###,###");
        int count = 0;
        String stt, id, name, ql, pr;
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
        char choice = 'x';
        do {
            System.out.println("-----------------LỰA CHỌN THỨC UỐNG MUỐN XÓA-------------------");
            System.out.println("|  1. Xóa theo ID của thức uống                                |");
            System.out.println("|  2. Xóa theo tên thức uống                                   |");
            System.out.println("|  0. Quay lại                                                 |");
            System.out.println("----------------------------------------------------------------");
            System.out.println();
            System.out.println("Lựa chọn : ");
            choice = input.nextLine().charAt(0);
            switch (choice) {
                case '1':
                    deteleDrinkById();
                    break;
                case '2':
                    deteleDrinkByName();
                    break;
                case '0':
                    //goi mene manager ;
                    break;
                default:
                    System.out.println("Lựa chọn theo menu trên !");
            }
        } while (choice != '0');
    }

    public void deteleDrinkById() {
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
            choice = input.nextLine().charAt(0);
            switch (choice) {
                case 'y':
                case 'Y':
                    writeToFile(SAVE_OBJECT_DRINKS, drinksList);
                    writeDataFromFileFormatToCsv(SAVE_FORMAT_CSV, drinksList);
                    isChoice = false;
                    deleteDrink();
                    break;
                case 'n':
                case 'N':
                    drinksList = readDataFromFile(SAVE_OBJECT_DRINKS);
                    isChoice = false;
                    break;
                default:
                    System.out.println();
            }
        } while (isChoice);

    }


    //Hien thi danh sach
    public void optionDisplay() {
        char choice = 'x';
        boolean isChoice = true;
        do {
            System.out.println("----------------------LỰA CHỌN HIỂN THỊ------------------------");
            System.out.println("|  1. Hiển thị theo id thức uống                               |");
            System.out.println("|  2. Hiển thị theo tên thức uống                              |");
            System.out.println("|  3. Hiển thị theo số lượng                                   |");
            System.out.println("|  4. Hiển thị theo giá                                        |");
            System.out.println("|  0. Quay lại menu chính                                      |");
            System.out.println("----------------------------------------------------------------");
            System.out.println();
            System.out.print("Chọn :");
            choice = input.nextLine().charAt(0);
            switch (choice){
                case '1':
                    //displayDrinkById() ;
                    break;
                case '2':
                    //displayDrinkByName() ;
                    break;
                case '3':
                    //displayDrinkByQuality();
                    break;
                case '4':
                    //displayDrinkByPrice ;
                    break;
                case '0':
                    //menuchinh ;
                default:
                    System.out.println("Chọn theo menu !");
            }

        }while (isChoice);
    }

    public void displayDrinkById(){
        System.out.println("--------------SẮP XẾP THEO ID--------------");
        System.out.println("| 1. Theo thứ tự từ A-Z                    |");
        System.out.println("| 2. Theo thứ tự từ Z-A                    |");
        System.out.println("| 3. Quay lại menu                         |");
        System.out.println("--------------------------------------------");
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
            ex.printStackTrace();
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
            writer.append(FORMAT_CSV);
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
            e.printStackTrace();
        }
    }
}
