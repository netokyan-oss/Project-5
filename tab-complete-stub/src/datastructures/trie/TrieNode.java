package datastructures.trie;

/** A node in a trie. Children represent a-z and apostrophe. */
public class TrieNode
{
  private TrieNode[] children;
  private int ranking;
  private boolean lastLetter;

  public TrieNode() { this(false); }

  public TrieNode(boolean lastLetter)
  {
    children = new TrieNode[27];
    this.lastLetter = lastLetter;
    ranking = 0;
  }

  public void setChild(char letter, TrieNode child)
  {
    int index = getIndex(letter);
    if (index >= 0) children[index] = child;
  }

  public TrieNode getChild(char letter)
  {
    int index = getIndex(letter);
    return index < 0 ? null : children[index];
  }

  public boolean isLastLetter() { return lastLetter; }

  public void setLastLetter(boolean lastLetter)
  {
    this.lastLetter = lastLetter;
    if (!lastLetter) ranking = 0;
  }

  public void setRanking(int rank)
  {
    if (lastLetter) ranking = rank;
  }

  public int getRanking() { return ranking; }

  public int getNumberOfNodes()
  {
    int count = 1;
    for (TrieNode child : children)
      if (child != null) count += child.getNumberOfNodes();
    return count;
  }

  public int getHeight()
  {
    int height = 0;
    for (TrieNode child : children)
      if (child != null) height = Math.max(height, child.getHeight());
    return height + 1;
  }

  private int getIndex(char letter)
  {
    if (letter >= 'a' && letter <= 'z') return letter - 'a';
    if (letter == '\'') return 26;
    return -1;
  }
}
