package datastructures.trie;

import datastructures.list.LinkedList;
import datastructures.tree.BinarySearchTree;
import datastructures.tree.EmptyTreeException;
import datastructures.tree.TreeInterface;

/** A trie that stores words and their dictionary rankings. */
public class Trie implements TreeInterface<TrieNode>
{
  private TrieNode root;

  public Trie() { root = new TrieNode(); }

  public boolean contains(String word)
  {
    TrieNode node = findPrefixNode(word);
    return node != null && node.isLastLetter();
  }

  public void add(String word, int rank)
  {
    if (word == null) return;
    word = word.toLowerCase();
    TrieNode node = root;
    if (node == null)
    {
      root = new TrieNode();
      node = root;
    }

    for (int i = 0; i < word.length(); i++)
    {
      char letter = word.charAt(i);
      if (!isValidCharacter(letter)) return;
      TrieNode child = node.getChild(letter);
      if (child == null)
      {
        child = new TrieNode();
        node.setChild(letter, child);
      }
      node = child;
    }
    node.setLastLetter(true);
    node.setRanking(rank);
  }

  public LinkedList<Completion> findWordCompletions(String prefix)
  {
    LinkedList<Completion> result = new LinkedList<Completion>();
    TrieNode subtree = findPrefixNode(prefix);
    if (subtree == null) return result;

    BinarySearchTree<Completion> tree = new BinarySearchTree<Completion>();
    findCompletions(subtree, prefix.toLowerCase(), tree);
    return tree.sort();
  }

  public LinkedList<Completion> findTopCompletions(String prefix, int k)
  {
    LinkedList<Completion> result = findWordCompletions(prefix);
    result.truncate(Math.max(0, k));
    return result;
  }

  public TrieNode getRootData() throws EmptyTreeException
  {
    if (root == null) throw new EmptyTreeException();
    return root;
  }

  public int getHeight() { return root == null ? 0 : root.getHeight(); }
  public int getNumberOfNodes() { return root == null ? 0 : root.getNumberOfNodes(); }

  public boolean isEmpty()
  {
    return root == null || !hasWord(root);
  }

  public void clear() { root = null; }

  private void findCompletions(TrieNode subtree, String prefix,
      BinarySearchTree<Completion> tree)
  {
    if (subtree == null) return;
    if (subtree.isLastLetter())
      tree.add(new Completion(prefix, subtree.getRanking()));

    for (char letter = 'a'; letter <= 'z'; letter++)
    {
      TrieNode child = subtree.getChild(letter);
      if (child != null) findCompletions(child, prefix + letter, tree);
    }
    TrieNode apostropheChild = subtree.getChild('\'');
    if (apostropheChild != null)
      findCompletions(apostropheChild, prefix + '\'', tree);
  }

  private TrieNode findPrefixNode(String prefix)
  {
    if (prefix == null || root == null) return null;
    TrieNode node = root;
    String normalized = prefix.toLowerCase();
    for (int i = 0; i < normalized.length(); i++)
    {
      char letter = normalized.charAt(i);
      if (!isValidCharacter(letter)) return null;
      node = node.getChild(letter);
      if (node == null) return null;
    }
    return node;
  }

  private boolean hasWord(TrieNode node)
  {
    if (node.isLastLetter()) return true;
    for (char letter = 'a'; letter <= 'z'; letter++)
    {
      TrieNode child = node.getChild(letter);
      if (child != null && hasWord(child)) return true;
    }
    TrieNode apostropheChild = node.getChild('\'');
    return apostropheChild != null && hasWord(apostropheChild);
  }

  private boolean isValidCharacter(char letter)
  {
    return (letter >= 'a' && letter <= 'z') || letter == '\'';
  }
}
