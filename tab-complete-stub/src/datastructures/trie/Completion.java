package datastructures.trie;

/**
 * Represents a possible completion for a trie.
 * @author Wendy-Beth Minton
 */
 public class Completion implements Comparable<Completion>
 {
   private String word;
   private int rank;

   /**
    * Creates a completion with word {@code word} and rank {@code rank}.
    *
    * @param word the word the completion represents.
    * @param rank the rank of the word in the tab complete dictionary.
    */
   public Completion(String word, int rank)
   {
     this.word = word;
     this.rank = rank;
   }

   /**
    * Gets the word associated with this completion.
    *
    * @return the word associated with this completion.
    */
   public String getWord()
   {
     return this.word;
   }

   /**
    * Gets the rank associated with this completion.
    *
    * @return the rank of the word.
    */
   public int getRank()
   {
     return this.rank;
   }

   /**
    * Compares two completions.
    *
    * @param o the completion to compare against.
    * @return -1 if this completion is less than the completion, 0 if the
    * completions are equal, and 1 otherwise.
    */
   public int compareTo(Completion o)
   {
     return this.rank - o.rank;
   }

   /**
    * Determines if the two objects are equal.
    *
    * @param o the object to compare to.
    * @return true the this and the object are equal; otherwise false.
    */
    @Override
    public boolean equals(Object o)
    {
      // If they are the same memory address they are equal.
      if (this == o)
        return true;

      // If the object is not a completion they can't be equal.
      if (!(o instanceof Completion))
        return false;

      return (this.compareTo((Completion) o) == 0);
    }

    /**
     * Creates a string form of the completion.
     *
     * @return the completion as a string.
     */
     @Override
     public String toString()
     {
       return word + " (" + rank + ")";
     }

 }
