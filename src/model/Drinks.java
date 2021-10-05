package model;

import java.io.Serializable;

public class Drinks implements Serializable {
    private String idDrink ;
    private String nameDrink ;
    private int qualityDrink ;
    private long priceDrink ;
    private String otherDescription ;

    public Drinks(){};

    public Drinks(String idDrink, String nameDrink, int qualityDrink, long priceDrink, String otherDescription) {
        this.idDrink = idDrink;
        this.nameDrink = nameDrink;
        this.qualityDrink = qualityDrink;
        this.priceDrink = priceDrink;
        this.otherDescription = otherDescription;
    }

    public String getIdDrink() {
        return idDrink;
    }

    public void setIdDrink(String idDrink) {
        this.idDrink = idDrink;
    }

    public String getNameDrink() {
        return nameDrink;
    }

    public void setNameDrink(String nameDrink) {
        this.nameDrink = nameDrink;
    }

    public int getQualityDrink() {
        return qualityDrink;
    }

    public void setQualityDrink(int qualityDrink) {
        this.qualityDrink = qualityDrink;
    }

    public long getPriceDrink() {
        return priceDrink;
    }

    public void setPriceDrink(long primeDrink) {
        this.priceDrink = primeDrink;
    }

    public String getOtherDescription() {
        return otherDescription;
    }

    public void setOtherDescription(String otherDescription) {
        this.otherDescription = otherDescription;
    }

    @Override
    public String toString() {
        return "Drink : ID ='" + idDrink  +
                "',Name = '" + nameDrink  +
                "', Quality='" + qualityDrink +
                "', Prime ='" + priceDrink +
                " VND', Other = '" + otherDescription +
                '}';
    }
}
