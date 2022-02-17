package fireopal.uwuchat;

import java.util.Random;
import java.util.HashMap;
import java.util.List;

public class Uwuifier {
    public static String uwuify(String str, Random random) {
        String output = "";
        String[] words = str.split(" ");
        
        for (String word : words) {
            output += handleWord(word, random) + " ";
        }

        if (random.nextBoolean()) {
            if (output.charAt(output.length() - 1) != ' ') output += ' ';
            output += ENDING.get(random);
        }
        
        return output;
    }
    
    private static record SpecialCaseWord(String... words) {
        public String get(Random random) {
            return this.words[random.nextInt(this.words.length)];
        }
    }
    
    private static record Pair<T, U>(T v0, U v1) {}
    
    private static Pair<String, SpecialCaseWord> specialCaseWord(String word, String... specialWords) {
        return new Pair<String, SpecialCaseWord>(word, new SpecialCaseWord(specialWords));
    }
    
    private static class LetterChar {
        private final Boolean isCaps;
        private final Character cha;
        
        public LetterChar(Character cha) {
            this.cha = (cha.toString().toUpperCase()).charAt(0);
            this.isCaps = this.cha == cha;
        }
        
        public boolean isSimilarTo(Character... chars) {
            for (Character c : chars) {
                if (this.cha.equals(c.toString().toUpperCase().charAt(0))) {
                    return true;
                }
            }
            
            return false;
        }
        
        public Boolean isCaps() { return this.isCaps; }
        
        public Character get() {
            Character c = this.cha;
            return this.isCaps ? c : (cha.toString().toLowerCase()).charAt(0);
        }
        
        public Character returnChar(Character c) {
            String s = c.toString().toUpperCase();
            return this.isCaps ? s.charAt(0) : s.toLowerCase().charAt(0);
        }
    }
    
    private static final HashMap<String, SpecialCaseWord> SPECIAL_CASE_WORDS;
    private static final SpecialCaseWord ENDING = new SpecialCaseWord(
        "OwO", "owo", "UwU", "uwu", ":3", ":3", "owu", "uwo"
    );

    static {
        HashMap<String, SpecialCaseWord> map = new HashMap<>();
        
        List<Pair<String, SpecialCaseWord>> list = List.of(
            specialCaseWord("hello", "hewwo", "heowo", "hiiya", "hewo"),
            specialCaseWord("the", "tee", "da"),
            specialCaseWord("two", "2", "twoo", "twoo", "toe"),
            specialCaseWord("too", "twoo", "twoo", "toe"),
            specialCaseWord("though", "tho", "toe"),
            specialCaseWord("cool", "cowol", "coool"),
            specialCaseWord("love", "wuv"),
            specialCaseWord("penis", "peeny", "penis"),
            specialCaseWord("you", "u", "u", "yoo")
        );
        
        for (Pair<String, SpecialCaseWord> w : list) {
            map.put(w.v0.toUpperCase(), w.v1);
        }
        
        SPECIAL_CASE_WORDS = map;
    }
    
    private static final Character[] END_OF_WORD = new Character[]{
        ' ', ',', '.', ';', '?', '!', ':', ')'
    };
    
    private static String handleWord(String word, Random random) {
        if (SPECIAL_CASE_WORDS.containsKey(word.toUpperCase())) {
            String newWord = SPECIAL_CASE_WORDS.get(word.toUpperCase()).get(random);
            boolean lc = new LetterChar(word.charAt(0)).isCaps();
            
            return lc ?
                newWord.toUpperCase().charAt(0) + newWord.substring(1)
                : newWord;
        }
        
        final int wordLength = word.length();
        word += "  ";
        String output = "";
        
        for (int i = 0; i < wordLength; i += 1) {
            output += handleChar(word, i);
            
        }
        
        return output;
    }
    
    private static boolean prevCharIsSimilarTo(String word, int i, Character... cha) {
        if (i <= 0) return false;
        return new LetterChar(word.charAt(i - 1)).isSimilarTo(cha);
    }
    
    private static boolean isNeighborsWith(Character cha, String word, int i) {
        LetterChar letterChar = new LetterChar(word.charAt(i));
        
        if (letterChar.isSimilarTo(word.charAt(i + 1))) {
            return true;
        }
        
        return prevCharIsSimilarTo(word, i, cha);
    }

    // public static void main(String[] args) {
    //     System.out.println(isAPartOf("ight", "Alright", 3));
    //     System.out.println(isAPartOf("ight", "Alright", 4));
    //     System.out.println(isAPartOf("ight", "Alright", 5));
    //     System.out.println(isAPartOf("ight", "Alright", 6));
    // }
    
    private static boolean isAPartOf(String letters, String word, int i) {
        final int length = letters.length();

        for (int j = 0; j < length; j += 1) {
            word += " ";
        }

        // if (i + length >= word.length()) return false;

        short flag = 0;

        for (int k = 0; k < length; k += 1) {
            if (i - k < 0) continue;
            String part = word.substring(i - k, i - k + length);

            for (int j = 0; j < length; j += 1) {
                part += " ";
            }

            for (int j = 0; j < length; j += 1) {
                // if (j - k < 0) continue;
                LetterChar lc = new LetterChar(part.charAt(j));

                // System.out.println(lc.get() + " " + letters.charAt(j));

                if (!lc.isSimilarTo(letters.charAt(j))) {
                    flag = 0;
                    break;
                }

                flag += 1;
            }

            // System.out.println("> One loop done!");

            if (flag == length) return true;
        }

        return flag == length;
    }
    
    private static String handleChar(String word, int i) {
        word = word + "     ";
        final LetterChar currentChar = new LetterChar(word.charAt(i));
        final LetterChar nextChar = new LetterChar(word.charAt(i + 1));
        final LetterChar nextNextChar = new LetterChar(word.charAt(i + 2));
        final LetterChar next3Char = new LetterChar(word.charAt(i + 3));
        
        if (currentChar.isSimilarTo('r')) {
            if (isNeighborsWith('w', word, i)) {
                return "";
            } else {
                return currentChar.returnChar('w').toString();
            }
        }
        if (currentChar.isSimilarTo('p')) {
            if (nextChar.isSimilarTo('h')) {
                return currentChar.returnChar('f').toString();
            }
        } 
        if (currentChar.isSimilarTo('t')) {
            if (nextChar.isSimilarTo('h')) {
                if (nextNextChar.isSimilarTo(END_OF_WORD)) {
                    return currentChar.get().toString();
                } else {
                    return currentChar.returnChar('t').toString();
                }
            }
        } 
        if (currentChar.isSimilarTo('o') && nextChar.isSimilarTo('o')) {
            String temp = "";
            temp += currentChar.get();
            temp += "w";
            return temp;
        }
        if (currentChar.isSimilarTo('u') && nextChar.isSimilarTo('u')) {
            String temp = "";
            temp += currentChar.get();
            temp += "w";
            return temp;
        }
        if (nextChar.isSimilarTo('g') 
            && nextNextChar.isSimilarTo('h')
            && next3Char.isSimilarTo('t')
        ) {
            return currentChar.get() + "te";
        }
        if (isAPartOf("ght", word, i)) {
            return "";
        }
        if (currentChar.isSimilarTo('t')
            && nextChar.isSimilarTo('i') 
            && nextNextChar.isSimilarTo('o')
            && next3Char.isSimilarTo('n')
        ) {
            return (currentChar.isCaps() ? 'S' : 's') + "hin";
        }
        if (isAPartOf("ion", word, i)) {
            int x;
            if (currentChar.isSimilarTo('i')) x = 0;
            else if (currentChar.isSimilarTo('o')) x = 1;
            else x = 2;

            if (prevCharIsSimilarTo(word, i - x, 't')) {
                return "";
            }
        }
        if (currentChar.isSimilarTo('l')) {
            if (nextChar.isSimilarTo('e', 'i')) {
                return currentChar.returnChar('w').toString();
            } else if (nextChar.isSimilarTo('l') && prevCharIsSimilarTo(word, i, 'a', 'e', 'i', 'y')) {
                return currentChar.returnChar('w').toString();
            }
        }
         
        return currentChar.get().toString();
    }
}


