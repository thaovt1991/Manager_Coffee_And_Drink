package sort.sortDrinks;

import model.Drinks;

import java.util.Comparator;

public class SortToNameDrinksZA implements Comparator<Drinks> {
    @Override
    public int compare(Drinks dr1, Drinks dr2) {
        return dr2.getNameDrink().compareTo(dr1.getNameDrink());
    }
}
