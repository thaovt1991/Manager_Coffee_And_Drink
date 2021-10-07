package sort.sortStaff;

import model.Staff;

import java.util.Comparator;

public class SortByDateOfBirthDecrease implements Comparator<Staff> {
    @Override
    public int compare(Staff sf1, Staff sf2) {
        String[] arrSf1 = sf1.getDateOfBirth().split("/");
        String[] arrSf2 = sf2.getDateOfBirth().split("/");
        int daySf1 = Integer.parseInt(arrSf1[0]);
        int monthSf1 = Integer.parseInt(arrSf1[1]);
        int yearSf1 = Integer.parseInt(arrSf1[2]);

        int daySf2 = Integer.parseInt(arrSf2[0]);
        int monthSf2 = Integer.parseInt(arrSf2[1]);
        int yearSf2 = Integer.parseInt(arrSf2[2]);

        if (compareInt(yearSf1, yearSf2) != 0) return compareInt(yearSf1, yearSf2);
        else if (compareInt(monthSf1, monthSf2) != 0) return compareInt(monthSf1, monthSf2);
        return compareInt(daySf1, daySf2);

    }

    private int compareInt(int num1, int num2) {
        if (num1 < num2) {
            return 1;
        } else if (num1 == num2) {
            return 0;
        }
        return -1;
    }
}
