//  Name: Mockingbird     date: 12/7/11
//  This program takes a text file, creates an index (by line numbers)
//  for all the words in the file and writes the index
//  into the output file.  The program prompts the user for the file names.

   import java.util.*; //Modified to include all classes including ArrayList
   import java.io.*;

    public class IndexMaker
   {
       public static void main(String[] args) throws IOException
      {
         Scanner keyboard = new Scanner(System.in);
         System.out.print("\nEnter input file name: ");
         String fileName = keyboard.nextLine().trim();
         Scanner inputFile = new Scanner(new File(fileName));
         System.out.print("\nEnter output file name: ");
         fileName = keyboard.nextLine().trim();
         PrintWriter outputFile = new PrintWriter(new FileWriter(fileName));
         DocumentIndex index = new DocumentIndex();   
         String line = null;
         int lineNum = 0;
         while(inputFile.hasNextLine())
         {
            lineNum++;
            index.addAllWords(inputFile.nextLine(), lineNum);
         }
         for(IndexEntry entry : index)
            outputFile.println(entry);
         inputFile.close(); 						
         outputFile.close();
         System.out.println("Done.");
      }
   }
    class DocumentIndex extends ArrayList<IndexEntry>
   {
       public DocumentIndex()
      {
         super(); //Calls default constructor of ArrayList
      }
   	
       public DocumentIndex(int size)
      {
         super(size); //Calls default constructor of ArrayList with argument size
      }
   	
       public void addWord(String str, int num)
      {
         boolean found = false; //Set a boolean to false
         IndexEntry ie = new IndexEntry(str); //Instantiate a new IndexEntry object holding String str
         ie.add(num); //Add a number to the IndexEntry
         for(IndexEntry i : this) //For all the IndexEntries in this current object
            if(str.toUpperCase().equals(i.getWord())) //If the word is equal to the IndexEntry word
            {
               i.add(num); //Add the number to the IndexEntry
               found = true; //Set the boolean to true
            }
         if(found == false && size() > 0) //Ignore if the word was found
         {
            if(get(size()-1).getWord().compareTo(str.toUpperCase()) < 0) //Compares str to the last entry
            {
               add(ie); //DocumentIndex adds the IndexEntry
               return; //Ends the method
            } 
            for(IndexEntry i : this) //For all IndexEntries in this DocumentIndex
            {
               if(i.getWord().compareTo(str.toUpperCase()) > 0) //Selectively sorts the list
               {
                  add(indexOf(i), ie); //Adds the IndexEntry to the object in the alphabetic position
                  return; //Quit the method to lessen runtime if possible
               }
            }
         }
         else if(found == false) //If and only if boolean found is false
            add(ie); //Add the IndexEntry to the current DocumentIndex
      }
   	
       public void addAllWords(String str, int num)
      {
         String[] strArray = str.split("[., \"!?]"); //Splits the array except for blank lines
         for(String s : strArray) //For every String sorted
            if(!s.equals("")) //If it is not an empty line
               addWord(s, num); //Add this to the num position
      }
   	
       private int foundOrInserted(String word)
      {
         IndexEntry temp = new IndexEntry(word); //Instantiates IndexEntry with word
         for(IndexEntry element : this) //For every IndexEntry in this DocumentIndex
         {
            if(element == get(indexOf(element))) //If it is equal to the DocumentIndexes word
               return 0; //Return the position 0 which means nothing in this case
         }
      	
         for(IndexEntry element : this) //For every IndexEntry in the same DocumentIndex again
         {
            if(word.compareTo(element.getWord()) > 0) //If the word is of higher ACSII value
            {
               add(indexOf(element) - 1, temp); //Add the String to that position in DocumentIndex
               return indexOf(element) - 1; //Return that position
            }
         }
         return -1; //This generally should not reach this line
      }
   }
    class IndexEntry implements Comparable<IndexEntry>
   {
      private String word;
      private ArrayList<Integer> numsList;
   	
       public IndexEntry(String s)
      {
         word = s.toUpperCase(); //Makes the word capital in the constructor itself
         numsList = new ArrayList<Integer>(); //Default ArrayList of size 10
      }
       public void add(int num)
      {
         if(!numsList.contains(num)) //If the numsList has not the number
            numsList.add(num); //Add it
      }
   	
       public String getWord()
      {
         return word; //Simply returns the word in the private data field
      }
   	
       public String toString()
      {
         String s = word + " "; //Instantiates return String with the current word
         for(Integer i : numsList) //For every integer in the number ArrayList
            s += i + ", "; //Add it and add a ,\s
         s = s.substring(0, s.length()-2); //Remove the extraneous ,\s
         return s; //Return the String representation of the Index
      }
   	
       public int compareTo(IndexEntry entry)
      {
         return word.compareTo(entry.toString()); //Compares the String with the String of the IndexEntry
      }
   }

