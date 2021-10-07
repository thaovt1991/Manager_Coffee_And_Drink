package sort.sortStaff;

import model.Staff;

import java.util.Comparator;

public class SortByIdStaffZA implements Comparator<Staff> {
    @Override
    public int compare(Staff sf1, Staff sf2){
        return sf2.getIdStaff().compareTo(sf1.getIdStaff());
    }
}
