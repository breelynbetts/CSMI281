package sentinal;

public class PhraseHash implements PhraseHashInterface {

    // -----------------------------------------------------------
    // Fields
    // -----------------------------------------------------------

    private final static int BUCKET_START = 1000;
    private final static double LOAD_MAX = 0.7;
    private int size, longest;
    private String[] buckets;


    // -----------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------

    PhraseHash () {
    	size = 0; 
    	longest = 0; 
    	buckets = new String[BUCKET_START];
    }


    // -----------------------------------------------------------
    // Public Methods
    // -----------------------------------------------------------

    public int size () {
        return size;
    }

    public boolean isEmpty () {
    	return size == 0;
    }

    public void put (String s) {
    	// call the checkDuplicates method before insertion
        throw new UnsupportedOperationException();
    }

    public String get (String s) {
        throw new UnsupportedOperationException();
    }

    public int longestLength () {
        throw new UnsupportedOperationException();
    }


    // -----------------------------------------------------------
    // Helper Methods
    // -----------------------------------------------------------

    private int hash (String s) {
        throw new UnsupportedOperationException();
    }

    private void checkAndGrow () {
        throw new UnsupportedOperationException();
    }
    
    private void checkDuplicates(String proposed) {
    	throw new UnsupportedOperationException();
    }

}
