package sort.sortStaff;

import model.Staff;

import java.util.Comparator;

public class SortGenderFM implements Comparator<Staff> {
    @Override
    public int compare(Staff sf1, Staff sf2){
        return sf2.getGender().compareTo(sf1.getGender());
    }
}
