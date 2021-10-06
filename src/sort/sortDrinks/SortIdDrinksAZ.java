package sort.sortDrinks;

import model.Drinks;

import java.util.Comparator;

public class SortIdDrinksAZ implements Comparator<Drinks> {
    @Override
    public int compare(Drinks dr1, Drinks dr2) {
        return dr1.getIdDrink().compareTo(dr2.getIdDrink());
    }
}
