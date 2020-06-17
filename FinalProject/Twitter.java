//package FinalProject_Template;

import java.util.ArrayList;

public class Twitter {
	
	// make hashtable of tweets
	private MyHashTable<String, Tweet> tweetTable; 
	
	// make arraylist of stop words
	private MyHashTable<String, Integer> stopWordsTable;
	
	// hashtable
	private MyHashTable<String, Tweet> authorLatest;
	
	// for tweetsbydate
	private MyHashTable<String, ArrayList<Tweet>> dateTable;
	
	// O(n+m) where n is the number of tweets, and m the number of stopWords
	public Twitter(ArrayList<Tweet> tweets, ArrayList<String> stopWords) {

		// number of buckets
		int numBuckets = 7;
		
		// initialize hashtables
		this.tweetTable = new MyHashTable<String, Tweet>(numBuckets);
		this.authorLatest = new MyHashTable<String, Tweet>(numBuckets);
		this.dateTable = new MyHashTable<String, ArrayList<Tweet>>(numBuckets);
		this.stopWordsTable = new MyHashTable<String, Integer>(numBuckets);
		
		// initialize tweets inside and initialize authorLatest
		for (Tweet t: tweets) {
			this.tweetTable.put(t.getDateAndTime(), t);
			if (authorLatest.get(t.getAuthor()) == null) {
				this.authorLatest.put(t.getAuthor(), t);
			} else {
				if ((t.getDateAndTime().compareTo(this.authorLatest.get(t.getAuthor()).getDateAndTime())) > 0) {
					Tweet removed = this.authorLatest.remove(t.getAuthor());
					this.authorLatest.put(t.getAuthor(), t);
				}
			}
			
			// take care of datetable
			if (this.dateTable.get(t.getDateAndTime().split(" ")[0]) == null) {
				ArrayList<Tweet> twe = new ArrayList<>();
				twe.add(t);
				this.dateTable.put(t.getDateAndTime().split(" ")[0], twe);
			} else {
				this.dateTable.get(t.getDateAndTime().split(" ")[0]).add(t);
			}
		}
		
		// initialize the stopwords
		int count = 0;
		for (String s: stopWords) {
			this.stopWordsTable.put(s,count);
			count++;
		}
	}
	
	
    /**
     * Add Tweet t to this Twitter
     * O(1)
     */
	public void addTweet(Tweet t) {

		// add tweet t to arraylist of tweets (this twitter)
		if (authorLatest.get(t.getAuthor()) == null) {
			this.authorLatest.put(t.getAuthor(), t);
		} else {
			if ((t.getDateAndTime().compareTo(this.authorLatest.get(t.getAuthor()).getDateAndTime())) > 0) {
				Tweet removed = this.authorLatest.remove(t.getAuthor());
				this.authorLatest.put(t.getAuthor(), t);
			}
		}
	}
	

    /**
     * Search this Twitter for the latest Tweet of a given author.
     * If there are no tweets from the given author, then the 
     * method returns null. 
     * O(1)  
     */
    public Tweet latestTweetByAuthor(String author) {
    	
    	return this.authorLatest.get(author);
    }


    /**
     * Search this Twitter for Tweets by `date' and return an 
     * ArrayList of all such Tweets. If there are no tweets on 
     * the given date, then the method returns null.
     * O(1)
     */
    public ArrayList<Tweet> tweetsByDate(String date) {
    	
    	return this.dateTable.get(date);
    }
    
	/**
	 * Returns an ArrayList of words (that are not stop words!) that
	 * appear in the tweets. The words should be ordered from most 
	 * frequent to least frequent by counting in how many tweet messages
	 * the words appear. Note that if a word appears more than once
	 * in the same tweet, it should be counted only once. 
	 */
    public ArrayList<String> trendingTopics() {
    	
    	MyHashTable<String, Integer> tweetMessage = new MyHashTable<String, Integer>(7);
    	
    	for (HashPair<String,Tweet> pair: this.tweetTable) {
    		String message = pair.getValue().getMessage();
    		System.out.println(message);
    		
    		// using the modified getWords helper method
    		ArrayList<String> words = getWords(message);
    		
    		int count = 0;
    		for (String word: words) {
    			
    			try {
    				//If the word exists already, the put() method will overwrite the key 
    				//and update the value
    				tweetMessage.put(word, tweetMessage.get(word)+1);
    				
    			} catch (Exception e) {
    				//If it does not exist, add it with value 1.
    				tweetMessage.put(word, 1);
    			}
    			
    			//If we try the get() on the stopWords Hash Table and it yields a number, it 
    			//means that this word is a stop word
    			//so set its count to 0
    			if (!this.stopWordsTable.isEmpty()) {
    				if (this.stopWordsTable.get(word)!=null) {
    					tweetMessage.put(word,0);
    				}
    			}
    			
    			for (int j = count-1; j>=0; j--){
    				if (word.equalsIgnoreCase(words.get(j))){
    					tweetMessage.put(word, tweetMessage.get(word)-1);
    				}
    			}
    			count++;
    		}
    	}
    	
    	ArrayList<String> sorted = MyHashTable.fastSort(tweetMessage);
  
      	return sorted;     	
    }
    
    
    /**
     * A helper method you can use to obtain an ArrayList of words from a 
     * String, separating them based on apostrophes and space characters. 
     * All character that are not letters from the English alphabet are ignored. 
     */
    private static ArrayList<String> getWords(String msg) {
    	msg = msg.replace('\'', ' ');
    	String[] words = msg.split(" ");
    	ArrayList<String> wordsList = new ArrayList<String>(words.length);
    	for (int i=0; i<words.length; i++) {
    		String w = "";
    		for (int j=0; j< words[i].length(); j++) {
    			char c = words[i].charAt(j);
    			if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
    				w += c;
    			
    		}
    		wordsList.add(w.toLowerCase());
    	}
    	return wordsList;
    }

    

}
