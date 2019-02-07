package forneymonegerie;

public class Forneymonegerie implements ForneymonegerieInterface {

    // Fields
    // ----------------------------------------------------------
    private ForneymonType[] collection;
    private int size;
    private int typeSize;
    private static final int START_SIZE = 16;
    
    
    // Constructor
    // ----------------------------------------------------------
    Forneymonegerie () {
    	collection = new ForneymonType[START_SIZE];
    	size = 0;
    	typeSize = 0;
    }
    
    
    // Methods
    // ----------------------------------------------------------
    public boolean empty () {
        return typeSize == 0; 
         
    }
    
    public int size () {
    	return size;
    }
    
    public int typeSize () {
    	return typeSize;
    }
    
    public boolean collect (String toAdd) {
    	ForneymonType forn = new ForneymonType(toAdd, 1);
    	
    	if (findType(toAdd) == -1) {
    		insertAt( forn, typeSize);
    		return true;
    	} else {
    		collection[findType(toAdd)].count++;
    		size++;
    		
    	}
    	return false;
    }
    
    public boolean release (String toRemove) {
    	if(findType(toRemove) == -1) {
    		return false;
    	}
    	else {
    		collection[findType(toRemove)].count--;
    		size--;
    		if (collection[findType(toRemove)].count == 0) {
    			removeAt(findType(toRemove));
    		}
    		return true;
    	}
        
    }
    
    //would this work once i get the release to work 
    public void releaseType (String toNuke) {
        if (findType(toNuke) != -1) {
        	size -= collection[findType(toNuke)].count;
        	removeAt(findType(toNuke));
        }
    }
    
    public int countType (String toCount) {
    	if (findType(toCount)== -1) {
    		return 0;
    	}
        return collection[findType(toCount)].count;
    }
    
    public boolean contains (String toCheck) {
        if (findType(toCheck) == -1) {
        	return false;
        }
        return true;
    }
    
    public String nth (int n) {
    	String nTerm = "";
    	int nForney = n + 1;
    	
    	if ( n >= size || n < 0 ) {
    		throw new IllegalArgumentException("Argument provided is beyond size ");
    	}
    	
    	for (int i = 0; i < typeSize; i++) {
        	if ( nForney - collection[i].count > 0) {
        		nForney -= collection[i].count;
        	} else {
        		nTerm = collection[i].type;
        		break;
        	}
        }	
    
    	return nTerm;
        
    }
   
    public String rarestType () {
    	// could try doing rare using ints instead
    	
    	ForneymonType rarest = collection[0];
    	
    	if (typeSize == 1) {
    		return collection[0].type;
    	}
    
   		for (int i = 0; i < typeSize; i++) {
   			if (rarest.count >= collection[i].count) {
   				rarest = collection[i];
   			}
   		}	

        return rarest.type; 	
    }
    
    public Forneymonegerie clone () {
    	Forneymonegerie clone = new Forneymonegerie();
    	for (int i = 0; i < this.typeSize; i++) {
    		clone.collect(this.collection[i].type);
    		clone.collection[i].count = this.collection[i].count;
    	}
    	clone.size = this.size;
    	clone.typeSize = this.typeSize;
    	return clone;
    }
    
    public void trade (Forneymonegerie other) {
    	ForneymonType[] tempCollection = this.collection;
    	int tempSize = this.size;
    	int tempTypeSize = this.typeSize;
    	
    	this.collection = other.collection;
    	this.size = other.size;
    	this.typeSize = other.typeSize;
    	other.collection = tempCollection;
    	other.size = tempSize;
    	other.typeSize = tempTypeSize;
    	
    }
    
    public String toString() {
    	String start = "[ \"" + collection[0].type + "\": " + collection[0].count + ", ";
    	String str = "";
    	String end = "\"" + collection[typeSize - 1].type + "\": " + collection[typeSize - 1].count + " ]";
    	
    	for (int i = 1; i < typeSize - 2; i++) {
    		 str =  "\"" + collection[i].type + "\": " + collection[i].count + ", ";
    	}
    	return start + str + end;
    }
    
    // Static methods
    // ----------------------------------------------------------
    public static Forneymonegerie diffMon (Forneymonegerie y1, Forneymonegerie y2) {
    	Forneymonegerie diff = y1.clone();
    	int diffCount = 0; 
   
    	
    	for (int i = 0; i < y2.typeSize; i++) {
    		if ( diff.contains(y2.collection[i].type)) {
    			if (diff.collection[diff.findType(y2.collection[i].type)].count != y2.collection[i].count) {
    				diffCount = Math.abs(diff.collection[diff.findType(y2.collection[i].type)].count - y2.collection[i].count);
    				for (int j = 0; j < diffCount; j++) {
    					diff.release(y2.collection[j].type);
    				}
    			} else {
    				diff.releaseType(y2.collection[i].type);
    			}
    		}
    		
    	} 
    	for (int i = 0; i < diff.typeSize; i++ ) {
    		if (y2.contains(diff.collection[i].type)) {
    			if ( y2.collection[y2.findType(diff.collection[i].type)].count != diff.collection[i].count ) {
    				diffCount = Math.abs(y2.collection[y2.findType(diff.collection[i].type)].count - diff.collection[i].count);
    				for (int j = 0; j < diffCount; j++) {
    					diff.release(diff.collection[i].type);
    				}
    			}
    		}
    	}
    	
    	return diff;
       
    }
    
    public static boolean sameCollection (Forneymonegerie y1, Forneymonegerie y2) {
    	return diffMon(y1,y2).empty() && diffMon(y2,y1).empty();
    	
    	
    }
    
    
    // Private helper methods
    // ----------------------------------------------------------
    
    //returns index of type
    //if type does not exist, returns -1
    private int findType (String t) {
    	for (int i = 0; i < typeSize; i++) {
    		if ( collection[i].type.equals(t) ) {
    			return i;
    		}
    	} 
    	return -1;
    }
    
    private void shiftLeft (int index) {
    	for (int i = index; i < typeSize; i++) {
    		collection[i] = collection[i+1];
    	}
    }
    
    private void shiftRight (int index) {
        for (int i = typeSize - 1; i >= index; i--) {
            collection[i+1] = collection[i];
        }
    }
   
    private void removeAt (int index) {
    	shiftLeft(index);
    	typeSize--;
    }
    
    public void insertAt (ForneymonType toAdd, int index) {
    	checkAndGrow();
    	shiftRight(index);
    	collection[index] = toAdd;
    	typeSize++;
        size++;
    }
    
    private void checkAndGrow () {
        
        if (size < collection.length) {
            return;
        }
        
        ForneymonType[] newCollection = new ForneymonType[collection.length * 2];
       
        for (int i = 0; i < collection.length; i++) {
            newCollection[i] = collection[i];
        }
        
        collection = newCollection;
    }
    
    // Private Classes
    // ----------------------------------------------------------
    private class ForneymonType {
        String type;
        int count;
        
        ForneymonType (String t, int c) {
            type = t;
            count = c;
        }
    }
    
}
