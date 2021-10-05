public class Drinks {
    private String idDrink ;
    private String nameDrink ;
    private int qualityDrink ;
    private long primeDrink ;
    private String otherDescription ;

    public Drinks(){};

    public Drinks(String idDrink, String nameDrink, int qualityDrink, long primeDrink, String otherDescription) {
        this.idDrink = idDrink;
        this.nameDrink = nameDrink;
        this.qualityDrink = qualityDrink;
        this.primeDrink = primeDrink;
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

    public long getPrimeDrink() {
        return primeDrink;
    }

    public void setPrimeDrink(long primeDrink) {
        this.primeDrink = primeDrink;
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
                ",Name = '" + nameDrink  +
                "', Quality='" + qualityDrink +
                "', Prime ='" + primeDrink +
                "', Other = '" + otherDescription +
                '}';
    }
}
