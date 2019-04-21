public class Plant {

    /** Maximum size for a plant */
    public static final int MAX_PLANT_SIZE = 1000;

    /** Portion of plant that is eaten */
    public static final int PLANT_BITE_SIZE = 75;

    private int size;
    private String name;

    /** Standard constructor that merely initializes the fields from the parameters */
    public Plant(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public Plant() {
        name = "plant";
        size = 0;
    }

    /** returns true if size is bigger than zero, false otherwise. */
    public boolean isAlive() {
        if(size>0){
            return true;
        }else{
            return false;
        }
    }

    /** returns size */
    public int getSize() {
        return size;
    }

    /** increments size by one unit */
    public void grow() {
        size++;
    }

    /** decreases size by biteSize units */
    public void removeBite(int biteSize) {
        size-=biteSize;
    }
}