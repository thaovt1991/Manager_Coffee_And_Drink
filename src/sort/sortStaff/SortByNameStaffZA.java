package sort.sortStaff;

import model.Staff;

import java.util.Comparator;

public class SortByNameStaffZA implements Comparator<Staff> {
    public int compare(Staff sf1, Staff sf2){
        String [] arrSf1  = sf1.getFullName().split(" ");
        String[] arrSf2 = sf2.getFullName().split(" ");
        String nameSf1 = arrSf1[arrSf1.length -1];
        String nameSf2 = arrSf2[arrSf2.length -1];
        if(nameSf2.compareTo(nameSf1) ==0) return sf2.getFullName().compareTo(sf1.getFullName());
        return nameSf2.compareTo(nameSf1) ;
    }
}
