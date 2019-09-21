import org.junit.jupiter.api.*;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DictionaryTester {

    private static int totalScore, extraCredit;
    private final String ERROR_ONE = "<Not found in dictionary>";
    private final String ERROR_TWO = "<Second argument must be part of speech or \'distinct\'>";
    private static Dictionary d;

    @BeforeAll
    static void setUp() {
        totalScore = 0;
        extraCredit = 0;
        d = new Dictionary();
        d.init();
    }

    @Test
    void testEmpty() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add(ERROR_ONE);
        //testArray.toArray();
        assertEquals(testArray, d.querryDictionary(null, null, false), "Make sure to return the properly formatted Error Message");
        totalScore += 10;
    }

    @Test
    void testPlaceholder() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("Placeholder [adjective] : To be updated...");
        testArray.add("Placeholder [adjective] : To be updated...");
        testArray.add("Placeholder [adverb] : To be updated...");
        testArray.add("Placeholder [conjunction] : To be updated...");
        testArray.add("Placeholder [interjection] : To be updated...");
        testArray.add("Placeholder [noun] : To be updated...");
        testArray.add("Placeholder [noun] : To be updated...");
        testArray.add("Placeholder [noun] : To be updated...");
        testArray.add("Placeholder [preposition] : To be updated...");
        testArray.add("Placeholder [pronoun] : To be updated...");
        testArray.add("Placeholder [verb] : To be updated...");
        assertEquals(testArray, d.querryDictionary("Placeholder", null, false), "Make sure the Order is correct");
        totalScore += 3;

    }

    @Test
    void testMatchCase() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("Placeholder [adjective] : To be updated...");
        testArray.add("Placeholder [adjective] : To be updated...");
        testArray.add("Placeholder [adverb] : To be updated...");
        testArray.add("Placeholder [conjunction] : To be updated...");
        testArray.add("Placeholder [interjection] : To be updated...");
        testArray.add("Placeholder [noun] : To be updated...");
        testArray.add("Placeholder [noun] : To be updated...");
        testArray.add("Placeholder [noun] : To be updated...");
        testArray.add("Placeholder [preposition] : To be updated...");
        testArray.add("Placeholder [pronoun] : To be updated...");
        testArray.add("Placeholder [verb] : To be updated...");
        assertEquals(testArray, d.querryDictionary("PLAceHoldeR", null, false), "Make sure the Order is correct");

        testArray.clear();


        testArray.clear();
        testArray.add("Book [noun] : A set of pages.");
	testArray.add("Book [noun] : A written work published in printed or electronic form.");
 	testArray.add("Book [verb] : To arrange for someone to have a seat on a plane.");
 	testArray.add("Book [verb] : To arrange something on a particular date.");
        assertEquals(testArray, d.querryDictionary("book", null, false), "Make sure the Order is correct");


        testArray.clear();
        testArray.add("CSC340 [adjective] : = C++ version of CSC210 + CSC220 + more.");
 	testArray.add("CSC340 [noun] : A CS upper division course.");
 	testArray.add("CSC340 [noun] : Many hours outside of class.");
  	testArray.add("CSC340 [noun] : Programming Methodology.");
        assertEquals(testArray, d.querryDictionary("csC340", null, false), "Make sure the Order is correct");

        totalScore += 10;

    }

    @Test
    void testTypeOfSpeech() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("CSC340 [noun] : A CS upper division course.");
        testArray.add("CSC340 [noun] : Many hours outside of class.");
        testArray.add("CSC340 [noun] : Programming Methodology.");
        assertEquals(testArray, d.querryDictionary("CSC340", "noun", false), "Make sure you have the correct types of speech");

        testArray.clear();

        testArray.add("CSC220 [adjective] : Ready to create complex data structures.");
        assertEquals(testArray, d.querryDictionary("CSC220", "adjective", false), "Make sure you have the correct types of speech");
        testArray.clear();

        testArray.add("CSC220 [noun] : Data Structures.");
        assertEquals(testArray, d.querryDictionary("CSC220", "noun", false), "Make sure you have the correct types of speech");
        testArray.clear();

        testArray.add("CSC220 [verb] : To create data structures.");
        assertEquals(testArray, d.querryDictionary("CSC220", "verb", false), "Make sure you have the correct types of speech");
        testArray.clear();

        testArray.add(ERROR_ONE);
        assertEquals(testArray, d.querryDictionary("CSC220", "pronoun", false), "Make sure the error is correctly formatted");
        testArray.clear();

        testArray.add(ERROR_TWO);
        assertEquals(testArray, d.querryDictionary("CSC220", "reverb", false), "Make sure the error is correctly formatted");
	testArray.add("CSC340 [adjective] : = C++ version of CSC210 + CSC220 + more.");

 	testArray.add("CSC340 [noun] : A CS upper division course.");

 	testArray.add("CSC340 [noun] : Many hours outside of class.");

  	testArray.add("CSC340 [noun] : Programming Methodology.");


        totalScore += 5;
    }

    @Test
    void testDistinct() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("Placeholder [noun] : To be updated...");
        assertEquals(testArray, d.querryDictionary("Placeholder", "noun", true), "Should only be one item");
        testArray.clear();

        testArray.add("CSC340 [noun] : A CS upper division course.");
        testArray.add("CSC340 [noun] : Many hours outside of class.");
        testArray.add("CSC340 [noun] : Programming Methodology.");
        assertEquals(testArray, d.querryDictionary("CSC340", "noun", true), "Should have three items");
        testArray.clear();


        testArray.add("Placeholder [adjective] : To be updated...");
        assertEquals(testArray, d.querryDictionary("Placeholder", "adjective", true), "Should have one item");

        totalScore += 5;


    }

    @Test
    void testErrors() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add(ERROR_TWO);
        assertEquals(testArray, d.querryDictionary("Placeholder", "placeholder", false), "Check your error format");
        testArray.clear();

        testArray.add(ERROR_ONE);
        assertEquals(testArray, d.querryDictionary("Book", "adjective", true), "Check your error format");
        assertEquals(testArray, d.querryDictionary("BookableBook", "noun", false), "Check your error format");
        assertEquals(testArray, d.querryDictionary("P", "noun", false), "Check your error format");
        assertEquals(testArray, d.querryDictionary("Place", "verb", true), "Check your error format");

        totalScore += 2;
    }

    @Test
    void testExtraCredit() {
        ArrayList<String> testArray = new ArrayList<String>();
        testArray.add("Placeholder [adjective] : To be updated...");
        testArray.add("Placeholder [adverb] : To be updated...");
        testArray.add("Placeholder [conjunction] : To be updated...");
        testArray.add("Placeholder [interjection] : To be updated...");
        testArray.add("Placeholder [noun] : To be updated...");
        testArray.add("Placeholder [preposition] : To be updated...");
        testArray.add("Placeholder [pronoun] : To be updated...");
        testArray.add("Placeholder [verb] : To be updated...");
        assertEquals(testArray, d.querryDictionary("Placeholder", null, true), "This is Extra, don't worry if it doesn't pass");

        extraCredit += 3;

    }

    @AfterAll
    static void printResults() {
        System.out.println("Assuming the implementations were not hard-coded specifically for the test cases");
        System.out.println("Total Score: " + totalScore);
        System.out.println("Total Extra Credit: " + extraCredit);


    }

}
