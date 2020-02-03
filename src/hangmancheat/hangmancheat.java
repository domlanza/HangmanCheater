package hangmancheat;



import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class hangmancheat {

    // fencepost for length of the word
    public static final int RAND_MIN = 2;
    public static final int RAND_MAX = 10;

    // generates length of word to be guessed
    public static int wordLength() {
        int temp = (int) (Math.random() * RAND_MAX) + RAND_MIN;
        System.out.println("The word has a length of " + temp + " you get " + temp + " guesses.");
        return temp;
    }

    // generates initial arraylist when passed in a text document
    public static ArrayList<String> dictionArrayList(String S, int n) throws FileNotFoundException {
        ArrayList<String> dictionary = new ArrayList<String>();
        Scanner input = new Scanner(new File(S));
        while (input.hasNext()) {
            String temp = input.next();
            if (temp.length() == n) {
                dictionary.add(temp);
            }
        }
        input.close();
        return dictionary;
    }

    // takes in user input
    public static String userGuess() {
        System.out.println("Please enter a character or word: ");
        Scanner input = new Scanner(System.in);
        return input.next();
    }

    // checks if user guess (char) is in word
    public static boolean charInWord(String s, char c) {
        for (int i = 0; i < s.length(); i++) {
            if (c == s.charAt(i)) {
                return true;
            }
        }
        return false;
    }

    //checks if userinput causes there to be no entries left, if it's empty then we can't cheat for that turn
    //checks if userinput is a single character or a word
    public static boolean cannotCheat(ArrayList<String> S, String userinput) {
        if (userinput.length() > 1) {
            S.remove(userinput);
        } else {
            for (int i = 0; i < S.size(); i++) {
                if (charInWord(S.get(i), userinput.charAt(0))) {
                    S.remove(i);
                    i--;
                }
            }
        }return (0==S.size());
    }

    //if user guessed correctly no changes to array
    public static void correctGuess(String S){
        if (S.length()>1){
            System.out.println("You guessed " + S + " correctly!");
        }System.out.println(S + " is in the word.");
    }
    
    //chooses from arraylist after no more guesses
    public static void finalword(ArrayList<String> S){
        int index = (int)(Math.random()*S.size());
        System.out.println("The word was " + S.get(index));
    }

    //let's user to know to guess the word
    public static void guessTheWord(int G){
        System.out.println("Last chance, time to guess the word!");
    }

    // main game logic, pass in word list and number of guesses
    // if game cannot cheat then we let user know that the letter exists and do not remove elements from the array
    // after user has no guesses we select a random word remaining in our arraylist
    public static void mainLogic(ArrayList<String> S, int G) {
        int guesses = 0;
        ArrayList<String> temp = (ArrayList<String>) S.clone();
        while (guesses < G) {
        	System.out.println(temp);
            String guess = userGuess();
            if (!cannotCheat(S, guess)){
                temp = (ArrayList<String>) S.clone();
                System.out.println("Sorry, " + guess + " is not in the word.");
            }else if(cannotCheat(S, guess))
                correctGuess(guess);
                S = (ArrayList<String>) temp.clone();
                if (guess.length()>1){
                    break;
                }
            guesses++;
            }if (guesses == G){
            finalword(temp);
        }
    }

    public static void main(String[] args) throws FileNotFoundException {
        int randomLength = wordLength();
        ArrayList<String> dictionary = dictionArrayList("dictionary.txt", randomLength);
        mainLogic(dictionary, randomLength);
    }
}