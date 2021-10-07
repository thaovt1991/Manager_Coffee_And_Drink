package sort.sortStaff;

import model.Staff;

import java.util.Comparator;

public class SortByIdStaffAZ implements Comparator<Staff> {
    @Override
    public int compare(Staff sf1, Staff sf2){
        return sf1.getIdStaff().compareTo(sf2.getIdStaff());
    }
}
