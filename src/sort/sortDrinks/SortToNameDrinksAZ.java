package sort.sortDrinks;

import model.Drinks;

import java.util.Comparator;

public class SortToNameDrinksAZ implements Comparator<Drinks> {
    @Override
    public int compare(Drinks dr1, Drinks dr2) {
        return dr1.getNameDrink().compareTo(dr2.getNameDrink());
    }
}
