import java.util.Collection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 *
 * @author yujar
 */
public class Dictionary {

    final private TreeMap<String, TreeMap<String, ArrayList<String>>> dict = new TreeMap<String, TreeMap<String, ArrayList<String>>>();
    final private TreeMap<String, String> canonicalcap = new TreeMap<String, String>();
    private final String ERROR_ONE = "<Not found in dictionary>";
    private final String ERROR_TWO = "<Second argument must be part of speech or \'distinct\'>";
    private final TreeSet<String> poses = new TreeSet<String>() {{
      this.addAll(Arrays.asList(new String[]{"adjective", "adverb", "conjunction", "interjection", "noun", "preposition", "pronoun", "verb"}));
    }};

    public Dictionary() {
      dict.put("Arrow".toLowerCase(), new TreeMap() {{
          this.put("noun", new ArrayList() {{
            this.add("Here is one arrow: <IMG> -=>> </IMG>");
        }});
      }});
      canonicalcap.put("Arrow".toLowerCase(), "Arrow");
      dict.put("Placeholder".toLowerCase(), new TreeMap() {{
          this.put("adjective", new ArrayList() {{
            this.add("To be updated...");
            this.add("To be updated...");
          }});
          this.put("adverb", new ArrayList() {{
            this.add("To be updated...");
          }});
          this.put("conjunction", new ArrayList() {{
            this.add("To be updated...");
          }});
          this.put("interjection", new ArrayList() {{
            this.add("To be updated...");
          }});
          this.put("noun", new ArrayList() {{
            this.add("To be updated...");
            this.add("To be updated...");
            this.add("To be updated...");
          }});
          this.put("preposition", new ArrayList() {{
            this.add("To be updated...");
          }});
          this.put("pronoun", new ArrayList() {{
            this.add("To be updated...");
          }});
          this.put("verb", new ArrayList() {{
            this.add("To be updated...");
          }});
        }});
        canonicalcap.put("Placeholder".toLowerCase(), "Placeholder");
        dict.put("Book".toLowerCase(), new TreeMap() {{
          this.put("noun", new ArrayList() {{
            this.add("A set of pages.");
            this.add("A written work published in printed or electronic form.");
          }});
          this.put("verb", new ArrayList() {{
            this.add("To arrange for someone to have a seat on a plane.");
            this.add("To arrange something on a particular date.");
          }});
        }});
        canonicalcap.put("Book".toLowerCase(), "Book");
        dict.put("CSC340".toLowerCase(), new TreeMap() {{
          this.put("adjective", new ArrayList() {{
            this.add("= C++ version of CSC210 + CSC220 + more.");
          }});
          this.put("noun", new ArrayList() {{
            this.add("A CS upper division course.");
            this.add("Many hours outside of class.");
            this.add("Programming Methodology.");
          }});
        }});
        canonicalcap.put("CSC340".toLowerCase(), "CSC340");
        dict.put("CSC220".toLowerCase(), new TreeMap(){{
          this.put("adjective", new ArrayList() {{
            this.add("Ready to create complex data structures.");
          }});
          this.put("noun", new ArrayList() {{
            this.add("Data Structures.");
          }});
          this.put("verb", new ArrayList() {{
            this.add("To create data structures.");
          }});
        }});
        canonicalcap.put("CSC220".toLowerCase(), "CSC220");
    }

    public ArrayList<String> querryDictionary(String s, String pos, boolean distinct) {
      ArrayList<String> returnval = new ArrayList<String>();
      Keeper keep = new Keeper(distinct);
      if (s == null) {
        return makeErrorOne();
      }
      s = s.toLowerCase();
      TreeMap<String, ArrayList<String>> result = dict.get(s);
      if(result == null) {
        return makeErrorOne();
      }
      if(pos == null) { // There are results, write the distinctiveness check later.
        for(Iterator<Map.Entry<String, ArrayList<String>>> a = result.entrySet().iterator(); a.hasNext(); ){
          Map.Entry<String, ArrayList<String>> defs = a.next();
          for (Iterator<String> d = defs.getValue().iterator(); d.hasNext();) {
            String def = d.next();
            if(keep.goAhead(def, defs.getKey())) {
              returnval.add(canonicalcap.get(s) + " [" + defs.getKey() + "] : " + def);
            }
          }
        }
        return returnval;
      } else { // Type of speech now.
        if (!poses.contains(pos)) {
          return new ArrayList<String>(){{ this.add(ERROR_TWO); }};
        }
        ArrayList resulting = result.get(pos);
        if (resulting == null) {
          return makeErrorOne();
        }
        for (Iterator<String> d = resulting.iterator(); d.hasNext();) {
          String def = d.next();
          if(keep.goAhead(def, pos)) {
            returnval.add(canonicalcap.get(s) + " [" + pos + "] : " + def);
          }
        }
        return returnval;
      }
    }

    private ArrayList<String> makeErrorOne() {
      return new ArrayList<String>() {{this.add(ERROR_ONE);}};
    }

    private class Keeper {
      boolean distinct;
      TreeMap<String, Set<String>> seen;
      public Keeper(boolean distinct) {
        this.distinct = distinct;
        if (distinct) seen = new TreeMap<String, Set<String>>();
      }
      public boolean goAhead(String s, String pos) {
        if (!distinct) return true;
        if (seen.containsKey(pos)) {
          Set<String> strings = seen.get(pos);
          if (strings.contains(s)) {
            return false;
          }
          strings.add(s);
          return true;
        } else {
          Set<String> strings = new TreeSet<String>();
          strings.add(s);
          seen.put(pos, strings);
          return true;
        }
      }
    }

    public void init() {
        //
        // dict.put("Placeholder", "[adjective] : To be updated...");
        // dict.put("Placeholder", "[adjective] : To be updated...");
        // dict.put("Placeholder", "[adverb] : To be updated...");
        // dict.put("Placeholder", "[conjunction] : To be updated...");
        // dict.put("Placeholder", "[interjection] : To be updated...");
        // dict.put("Placeholder", "[noun] : To be updated...");
        // dict.put("Placeholder", "[noun] : To be updated...");
        // dict.put("Placeholder", "[noun] : To be updated...");
        // dict.put("Placeholder", "[noun] : To be updated...");
        // dict.put("Placeholder", "[preposition] : To be updated...");
        // dict.put("Placeholder", "[pronoun] : To be updated...");
        // dict.put("Placeholder", "[verb] : To be updated...");
        // dict.put("CSC 340", "[adjective] : = C++ version of CSC210 + CSC220 + more.");
        // dict.put("CSC 340", "[noun] : A CS upper division course.");
        // dict.put("CSC 340", "[noun] : Many hours outside of class.");
        // dict.put("CSC 340", "[noun] : Programming Methodology.");
        // dict.put("CSC 220", "[adjective] : Ready to create complex data structures.");
        // dict.put("CSC 220", "[verb] : To create data structures");
        // dict.put("Book", "[noun] : A set of pages.");
        // dict.put("Book", "[noun] : A written work published in written or electronic form.");
        // dict.put("Book", "[verb] : To arrange for someone to have a seat on a plane.");
        // dict.put("Book", "[verb] : To arrange something on a particular date.");
        // dict.put("Adverb", "[noun] : Adverb is a word that adds more information about place, time, manner, cause or\n"
        //     + "degree to a verb, an adjective, a phrase or another adverb.");
        // dict.put("Adjective", "[noun] : Adjective [noun] : Adjective is a word that describes a person or thing, for example big, red and\n"
        //     + "clever in a big house, red wine and a clever idea.");
        // dict.put("Interjection", "[noun] : Adjective [noun] : Interjection is a short sound, word or phrase spoken suddenly to express an\n"
        //     + "emotion. Oh!, Look out! and Ow! are interjections.");
        // dict.put("Conjunction", "[noun] :  Noun is a word that refers to a person, (such as Ann or doctor), a place (such as Paris\n"
        //     + "or city) or a thing, a quality or an activity (such as plant, sorrow or tennis.");

    }


}
