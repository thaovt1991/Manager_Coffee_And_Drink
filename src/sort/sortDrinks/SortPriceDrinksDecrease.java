package sort.sortDrinks;

import model.Drinks;

import java.util.Comparator;

public class SortPriceDrinksDecrease implements Comparator<Drinks> {
    @Override
    public int compare(Drinks dr1, Drinks dr2) {
        if(dr1.getPriceDrink() < dr2.getPriceDrink()){
            return 1;
        }else if(dr1.getPriceDrink() == dr2.getPriceDrink()){
            return 0;
        }else{
            return -1;
        }
    }
}
