package service;

import precentation.Menu;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Scanner;
import java.util.regex.Pattern;

public class SystemManager {

    public static final String LINK_SOURCE_FOLDER = "src/data";
    public static final String LINK_REGEX = "(^([CDEF][:])\\\\(?:[\\w]+\\\\)*\\w+$)|(^[C|D][:][\\\\]$)";
    public static Scanner input = new Scanner(System.in);

    public boolean isFormatLink(String link) {
        return Pattern.compile(LINK_REGEX).matcher(link).matches();
    }

    public void copyFolder(File sourceFolder, File targetFolder) throws IOException {
        if (sourceFolder.isDirectory()) {
            if (!targetFolder.exists()) {
                targetFolder.mkdir();
            }
            String files[] = sourceFolder.list();
            for (String file : files) {
                File srcFile = new File(sourceFolder, file);
                File tarFile = new File(targetFolder, file);
                copyFolder(srcFile, tarFile);
            }
        } else {
            Files.copy(sourceFolder.toPath(), targetFolder.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);
            System.out.println("File da duoc copy " + targetFolder);
        }
    }

    public void exportSaveData() {
        String linkFolder;
        do {
            System.out.print("Nhập đường dẫn folder xuất ra : ");
            linkFolder = input.nextLine();
            if (!isFormatLink(linkFolder)) {
                System.out.println("Định dạng đường dẫn không đúng ! ví dụ đường dẫn file : D:\\nameFoder\\....");
            }
        }
        while (!isFormatLink(linkFolder));
        //tao duong dan folder
        String[] arr = linkFolder.split("\\\\");
        int i = 1;
        String link = arr[0];
        while (i < arr.length) {
            link = link + "\\" + arr[i];
            File dir = new File(link);
            dir.mkdir();
            i++;
        }
        File sourceFolder = new File(LINK_SOURCE_FOLDER);
        File targetFolder = new File(linkFolder);
        try {
            copyFolder(sourceFolder, targetFolder);
            System.out.println("Copy file data thành công đến " + linkFolder);
        } catch (IOException e) {
            System.out.println("Copy folder lỗi");
        }

    }

    public void importDataOld() {
        String linkFolder;
        do {
            System.out.print("Nhập đường dẫn folder chứa dữ liệu cũ : ");
            linkFolder = input.nextLine();
            if (!isFormatLink(linkFolder)) {
                System.out.println("Định dạng đường dẫn không đúng ! ví dụ đường dẫn file : D:\\nameFoder\\....");
            }
        }
        while (!isFormatLink(linkFolder));
        char choice = ' ';
        boolean isChoice = false;
        do {
            System.out.print("Bạn thực sự muốn khôi phục dữ liệu từ folder " + linkFolder + "cho hệ thống");
            System.out.println("Mọi thông số dữ liệu có sẵn sẽ thay đổi, bạn có muốn thực hiện điều đó !");
            System.out.println("------------------");
            System.out.println("|  1. Yes         |");
            System.out.println("|  0. No          |");
            System.out.println("-------------------");
            System.out.println();
            System.out.print("Chọn :");
            try {
                choice = input.nextLine().charAt(0);
            } catch (Exception e) {
                choice = ' ';
            }
            switch (choice) {
                case '1':
                    File sourceFolder = new File(linkFolder);
                    File targetFolder = new File(LINK_SOURCE_FOLDER);
                    try {
                        copyFolder(sourceFolder, targetFolder);
                        System.out.println("Khôi phục dữ liệu thành công ! Hãy kiểm tra lại dữ liệu của bạn !");
                    } catch (IOException e) {
                        System.out.println("Khôi phục data lỗi ! Hãy thử lại !");
                    }
                    menuSystemManager();
                    break;
                case '0':
                    menuSystemManager();
                    break;
                default:
                    System.out.println("Hãy lựa chọn cẩn thận vì nó sẽ làm thay đổi các dữ liệu của hệ thống đã có !");
                    isChoice = true;
            }

        } while (isChoice);

    }

    public void menuSystemManager() {
        if (Menu.username.equals(Menu.USERNAME_DEFAULT)) {
            char choice = ' ';
            do {
                System.out.println("---------------------------------------");
                System.out.println("|           QUẢN LÝ HỆ THỐNG           |");
                System.out.println("---------------------------------------");
                System.out.println("|  1. Save data System                 |");
                System.out.println("|  2. Khôi phục data                   |");
                System.out.println("|                           0.Quay lại |");
                System.out.println("---------------------------------------");
                System.out.println();
                System.out.print("Chọn : ");
                try {
                    choice = input.nextLine().charAt(0);
                } catch (Exception e) {
                    choice = ' ';
                }
                switch (choice) {
                    case '1':
                        exportSaveData();
                        break;
                    case '2':
                        importDataOld();
                        break;
                    case '0':
                        Menu.menuWorkWithAdmin();
                        break;
                    default:
                        System.out.println();
                }
            } while (choice != '0');
        } else System.out.println("Phải đăng nhập tài khoản admin hệ thống để thực hiện chức năng này ! ");
    }
}

