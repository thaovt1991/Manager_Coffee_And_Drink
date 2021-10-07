package sort.sortStaff;

import model.Staff;

import java.util.Comparator;

public class SortByNameStaffAZ implements Comparator<Staff> {
    @Override
    public int compare(Staff sf1, Staff sf2){
        String [] arrSf1  = sf1.getFullName().split(" ");
        String[] arrSf2 = sf2.getFullName().split(" ");
        String nameSf1 = arrSf1[arrSf1.length -1];
        String nameSf2 = arrSf2[arrSf2.length -1];
        if(nameSf1.compareTo(nameSf2) ==0) return sf1.getFullName().compareTo(sf2.getFullName());
        return nameSf1.compareTo(nameSf2) ;
    }
}
