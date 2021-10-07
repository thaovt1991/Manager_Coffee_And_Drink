package sort.sortStaff;

import model.Staff;

import java.util.Comparator;

public class SortByPayStaffAscending implements Comparator<Staff> {
    @Override
    public int compare(Staff sf1, Staff sf2){
        if(sf1.getPayStaff() > sf2.getPayStaff()){
            return 1;
        }else if(sf1.getPayStaff() == sf2.getPayStaff()) return 0;
        return -1;
    }
}
