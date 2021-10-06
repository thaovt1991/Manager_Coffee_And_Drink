package sort.sortDrinks;

import model.Drinks;

import java.util.Comparator;

public class SortIdDrinksZA implements Comparator<Drinks> {
    @Override
    public int compare(Drinks dr1, Drinks dr2) {
        return dr2.getIdDrink().compareTo(dr1.getIdDrink());
    }
}
