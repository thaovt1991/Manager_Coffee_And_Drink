package sort.sortDrinks;

import model.Drinks;

import java.util.Comparator;

public class SortQualityDrinksAscending implements Comparator<Drinks> {
    @Override
    public int compare(Drinks dr1, Drinks dr2) {
        if(dr1.getQualityDrink() > dr2.getQualityDrink()){
            return 1;
        }else if(dr1.getQualityDrink() == dr2.getQualityDrink()){
            return 0;
        }else{
            return -1;
        }
    }
}
