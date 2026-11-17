import datastructures.trie.Trie;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * This implements a basic tab complete program.
 * @author Wendy-Beth Minton
 */
 public class TabComplete
 {

   /**
    * Loads a dictionary from a file. The format of the dictionary is
    * one word per line.
    *
    * @param fileName the name of the dictionary file.
    * @throws FileNotFoundException when the file is not found.
    */
    public static Trie loadDictionary(String fileName) throws FileNotFoundException
    {
      Scanner in = new Scanner(new File(fileName));
      int rank = 1;
      String word;

      Trie trie = new Trie();

      while(in.hasNextLine())
      {
          word = in.nextLine();
          trie.add(word.toLowerCase(), rank++);
      }

      return trie;
    }

   /**
    * Runs the very simple Read, Evaluate, Print, Loop (REPL). This
    * continually prompts the user for a string and lists the possible
    * completions.
    *
    * @param trie the trie representing the dictionary.
    */
   public static void doREPL(Trie trie)
   {
     doREPL(trie, new Scanner(System.in));
   }

   private static void doREPL(Trie trie, Scanner scan)
   {
     String word = "";
     int k = 5;

     // Run the standard read, parse, evaluate loop.
     while (!word.equals(".quit"))
     {
       System.out.print("> ");
       word = scan.nextLine();

       // If the user didn't ask to quit, try to process the word.
       if (!word.equals("") && !word.equals(".quit") && !word.equals(".set-k"))
          System.out.println(trie.findTopCompletions(word, k));
       if(word.equals(".set-k"))
       {
         do {
           System.out.print("Enter a positive value for k: ");
           k = Integer.valueOf(scan.nextLine());
         } while (k <= 0);
       }
     }
   }

   public static void main(String[] args)
   {
     Scanner scan = new Scanner(System.in);
     String dictionaryFile;
     Trie trie = null;

     // Prompt the user for the dictionary file to open.
     System.out.print(
         "Please enter the name of the file holding the dictionary: ");
     dictionaryFile = scan.nextLine();

     try
     {
       System.out.print("Loading . . . ");
       trie = loadDictionary(dictionaryFile);
       System.out.println("[ DONE ]");
     }
     catch (FileNotFoundException ex)
     {
       System.out.println("[ FAIL ]");
       System.err.println("Could not load dictionary. Exiting.");
       System.exit(1);
     }

     System.out.println("To quit interaction, type .quit at the prompt.");
     System.out.println("To set the value of k use .set-k at the prompt.");
     doREPL(trie, scan);
   }
 }
