package sort.sortStaff;

import model.Staff;

import java.util.Comparator;

public class SortGenderMF implements Comparator<Staff> {
    @Override
    public int compare(Staff sf1 , Staff sf2){
        return sf1.getGender().compareTo(sf2.getGender());
    }
}
