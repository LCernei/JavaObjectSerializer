public class Animal {

    private boolean vegetarian;

    private String[] eats;

    private String[][] nestedArray = null;
    private  Plant[] objectArray;
    public Plant pllant = null;

    private int noOfLegs;

    public Animal(){}

    public Animal(boolean veg, String[] food, int legs){
        this.vegetarian = veg;
        this.eats = food;
        this.noOfLegs = legs;
        this.pllant = new Plant("lemon", 30);
        String[][] na = {{"one", "two"}, {"three", "four"}};
        this.nestedArray = na;
        Plant[] parr = {new Plant("rosii", 5), new Plant("castraveti", 7)};
        this.objectArray = parr;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public void setVegetarian(boolean vegetarian) {
        this.vegetarian = vegetarian;
    }

    public String[] getEats() {
        return eats;
    }

    public void setEats(String[] eats) {
        this.eats = eats;
    }

    public int getNoOfLegs() {
        return noOfLegs;
    }

    public void setNoOfLegs(int noOfLegs) {
        this.noOfLegs = noOfLegs;
    }

}