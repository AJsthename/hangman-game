import java.util.Arrays;
import java.util.Scanner;

public class Hangman {

    public static String[] words = {"ant", "baboon", "badger", "bat", "bear", "beaver", "camel",
    "cat", "clam", "cobra", "cougar", "coyote", "crow", "deer",
    "dog", "donkey", "duck", "eagle", "ferret", "fox", "frog", "goat",
    "goose", "hawk", "lion", "lizard", "llama", "mole", "monkey", "moose",
    "mouse", "mule", "newt", "otter", "owl", "panda", "parrot", "pigeon", 
    "python", "rabbit", "ram", "rat", "raven","rhino", "salmon", "seal",
    "shark", "sheep", "skunk", "sloth", "snake", "spider", "stork", "swan",
    "tiger", "toad", "trout", "turkey", "turtle", "weasel", "whale", "wolf",
    "wombat", "zebra"};

    public static String[] gallows = {"+---+\n" +
    "|   |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    "+---+\n" +
    "|   |\n" +
    "O   |\n" +
    "    |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    "+---+\n" +
    "|   |\n" +
    "O   |\n" +
    "|   |\n" +
    "    |\n" +
    "    |\n" +
    "=========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|   |\n" +
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + //if you were wondering, the only way to print '\' is with a trailing escape character, which also happens to be '\'
    "     |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" +
    "/    |\n" +
    "     |\n" +
    " =========\n",

    " +---+\n" +
    " |   |\n" +
    " O   |\n" +
    "/|\\  |\n" + 
    "/ \\  |\n" +
    "     |\n" +
    " =========\n"};

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        String secretWord = randomWord(words);
        char[] placeHolder = new char[secretWord.length()];

        int correctCount = 0;
        int missCount = 0;
        char[] missedGuesses = new char[gallows.length-1];

        char user_guess = '\u0000'; // Initialize user_guess with null character or non-printable character

        while(true){

            printGallows(gallows, missCount);
            printWordPlaceHolders(placeHolder,secretWord, user_guess);
            printMissedGuesses(missedGuesses);
            
            user_guess = getGuess(scan);
            boolean isInString = isInString(user_guess, secretWord);
    
            if(!isInString){
                missCount++;
                char[] tempMissedGuesses = updateMissedGuessesArray(missedGuesses, user_guess, missCount);
                missedGuesses = Arrays.copyOf(tempMissedGuesses, tempMissedGuesses.length);
 
                if(missCount == missedGuesses.length){
                    printGallows(gallows, missCount);
                    System.out.println("YOU'LL GET IT NEXT TIME");
                    break;
                }
            } else{
                if(correctCount >= 0 && correctCount < secretWord.length()){
                    correctCount++;
                }
                int index = findIndex(user_guess, secretWord);
                char[] tempPlaceHolder = updateWordPlaceHolders(placeHolder, index, user_guess);
                placeHolder = Arrays.copyOf(tempPlaceHolder, tempPlaceHolder.length);
                if(correctCount == secretWord.length()){
                    printGallows(gallows, missCount);
                    printWordPlaceHolders(tempPlaceHolder, secretWord, user_guess);
                    System.out.println("GREAT JOB!");
                    break;
                }
            }
        }
        scan.close();
    }

    /**
     * Function name -- randomWord
     * @param words (String[])
     * @return (String)
     * 
     *  Description:
     *  - Selects a random word from the String array of words and returns it
     */
    public static String randomWord(String[] words){
        double randomNumber = Math.random() * words.length;
        int element = (int)randomNumber;
        return words[element];
    }

    /**
     * Function name -- printGallows
     * @param gallows (String[])
     * @param missCount (int)
     * 
     * Description:
     *  - Prints the gallows according to the number of missed attempts
     */
    public static void printGallows(String[] gallows, int missCount){
        switch(missCount){
            case 0: System.out.println(gallows[0]); break;
            case 1: System.out.println(gallows[1]); break;
            case 2: System.out.println(gallows[2]); break;
            case 3: System.out.println(gallows[3]); break;
            case 4: System.out.println(gallows[4]); break;
            case 5: System.out.println(gallows[5]); break;
            case 6: System.out.println(gallows[6]); break;
        }
    }

    /**
     * Function name -- getGuess
     * @param scan (Scanner)
     * @return (char)
     * 
     * Description:
     *  - Prompts the user to insert a guess of what the hidden word could be
     *  - The program accepts only a string input of a single letter.
     *  - Turns input into a single, lower case character
     *  - Returns user's guess (input) as a single character
     */
    public static char getGuess(Scanner scan){
        while (true) {
            System.out.print("Guess: ");
            String input = scan.nextLine().trim().toLowerCase(); // 'A ' => 'A' => 'a'
            if (input.length() == 1 && input.charAt(0) >= 'a' && input.charAt(0) <= 'z'){
                return input.charAt(0);
            }
             System.out.println("Error: Enter a single letter (a-z) only.");
        }
    }

    /**
     * Function name -- isInString
     * @param guess (char)
     * @param word (String)
     * @return (boolean)
     * 
     * Description:
     *  - Checks if the user's guess is within the hidden word
     */
    public static boolean isInString(char guess, String word){
        boolean bool = false;

        for (int i = 0; i < word.length(); i++){
            if (guess == word.charAt(i)) bool = true;
        }
        return bool;
    }

    /**
     * Function name -- findIndex 
     * @param guess (char)
     * @param word (String)
     * @return (int)
     * 
     * Description:
     *  - Finds exactly at what index the user's guess is located within the hidden word
     */
    public static int findIndex(char guess, String word){
        for(int i = 0; i < word.length(); i++){
            if(guess != '\u0000' && guess == word.charAt(i)) return i;
        }
        return -1;
    }
    
    /**
     * Function name -- updateWordPlaceHolders
     * @param placeHolder (char[])
     * @param index (int)
     * @param guess (char)
     * @return (char[])
     * 
     * Description:
     *  - Update the placeholder array with the user's (correct) guess
     */
    public static char[] updateWordPlaceHolders(char[] placeHolder, int index, char guess){
        if(placeHolder[index] == '_' && guess != '\u0000'){
            placeHolder[index] = guess;
        }
        return placeHolder;
    }

    /**
     * Function name -- printWordPlaceHolders
     * @param placeHolder (char[])
     * @param word (String)
     * @param guess (char)
     * 
     * Description:
     *  - 
     */
    public static void printWordPlaceHolders(char[] placeHolder, String word, char guess){
        System.out.print("Word: ");
        for(int i = 0; i < word.length(); i++){
            if(placeHolder[i] == '\u0000'){
                placeHolder[i] = '_';
            }
            System.out.print(placeHolder[i] + " ");
        }
        System.out.print("\n\n");
    }

    public static char[] updateMissedGuessesArray(char[] missedGuesses, char guess, int missCount){
        if(missCount > 0 && missCount <= 6){
                missedGuesses[missCount-1] = guess;
        }
        return missedGuesses;
    }
    
    public static void printMissedGuesses(char[] missedGuesses){ 
        System.out.print("Misses: ");
        for(int i = 0; i < missedGuesses.length; i++){
            if(missedGuesses[i] == '\u0000'){
                missedGuesses[i] = '_';
            }
            System.out.print(missedGuesses[i]);
        }
        System.out.print("\n\n");
    }
}


