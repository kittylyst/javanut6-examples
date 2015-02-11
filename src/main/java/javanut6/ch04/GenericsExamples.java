package javanut6.ch04;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

// A couple of bonus examples to explain some generics points that readers
// have asked me about - an expanded discussion of this may end up in 7th Ed.
public class GenericsExamples {

    private static GenericsExamples instance = null;

    // Constructor
    public GenericsExamples() {
        super();
    }

    /*
     * This is a quick example to explain the difference between 
     * type variance for Lists (and other generic types) and arrays
     */
    private void run() {
        // Won't compile
//        List<Object> objects = new ArrayList<String>();
        // This line fine
        List<?> unknownObjects = new ArrayList<String>();

        // But we can't add anything to the List-of-unknown-type 
        // The next line is a compiler error
//        unknownObjects.add(new Object());
        // If we could, then we could write code like this:
//        List<?> unknownObjects2 = new ArrayList<Object>();
//        Object o = new String("X");
//        unknownObjects2.add(o);
        // The situation is different for arrays - this is completely legal
        String[] words = {"Hello World!"};
        Object[] objects = words;

        // However, now, oh dear, we can get runtime errors
        objects[0] = new Integer(42);
    }

    // An example of a generic method - the equivalent of Perl's comma operator
    public static <T> T comma(T a, T b) {
        return a;
    }

    /**
     * @param args
     * @throws IOException 
     */
    public static void main(String[] args) throws IOException {
        instance = new GenericsExamples();
        instance.run();
    }

}
