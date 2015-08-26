/*
 * CSCI 143 - Summer 2015 
 * Assignment 6 - Assassins Game
 * AssassinManager.java
 * August 12 2015
 * Alex Terikov (teraliv@gmail.com)
 */

package game;

import java.util.List;

/**
 * Assassin Game class.
 * 
 * @author Alex Terikov
 * @version 1.0
 */
public class AssassinManager {
	
	/** The head of the kill ring linked list. */
	private AssassinNode assassinsHead;
	
	/** The head of the grave yard linked list. */
	private AssassinNode graveHead;
	
	
	/**
	 * Constructs a circular linked list with the assassins names.
	 * 
	 * @param names The list of assassins names.
	 */
	public AssassinManager(List<String> names) {
		
		// if the list is empty throw an exeption
		if (names == null || names.isEmpty()) {
			throw new IllegalArgumentException("There are no assassins!");
		
		// otherwise make a circular linked list
		} else {
			
			// assign head to a first node
			assassinsHead = new AssassinNode(names.get(0));
			
			AssassinNode assassinCurrent = assassinsHead;
			
			// iterate over list of names and assign all other nodes
			for (int i = 1; i < names.size(); i++) {
				assassinCurrent.next = new AssassinNode(names.get(i));
				assassinCurrent = assassinCurrent.next;
			}
			
			// point the last node to the head, which makes it circular
			assassinCurrent.next = assassinsHead;
		}
		
	}
	
	
	/**
	 * Print the names of the people in the kill 
	 * ring and what they are stalking.
	 */
	public void printKillRing() {
		
		AssassinNode current = assassinsHead;
		
		// print out current kill ring only if the game is not yet over
		// if the game is over there is only one assassin, the winner
		if (!isGameOver()) {
			
			// loop while the current node does not point to head
			while (current.next != assassinsHead) {
				
				// print current name and who he/she is stalking
				System.out.println(current.name + " is stalking " + current.next.name);
				current = current.next;
			}
			
			// print out the last name in the linked list
			System.out.println(current.name + " is stalking " + current.next.name);
		
		}
	}
	
	/**
	 * Print the names of the people in the grave 
	 * yard (that have been killed), and who kill them.
	 */
	public void printGraveyard() {
		
		// only print grave yard if there are killed assassins in there
		if (graveHead != null) {
			
			AssassinNode current = graveHead;
			
			// loop while the current node does not point to null
			while(current.next != null) {
				
				// print current killed name and the its killer
				System.out.println(current.name + " was killed by " + current.killer);
				current = current.next;
			}
			
			// print the last name in grave yard
			System.out.println(current.name + " was killed by " + current.killer);	
		}
		
	}
 	
	/**
	 * This method check if the assassin's name in the kill ring.	
	 * 
	 * @param theName The name of assassin.
	 * @return Returns true if the name is in the kill ring.
	 */
	public boolean killRingContains(String theName) {
		
		AssassinNode current = assassinsHead;
		
		Boolean contains = false;
		
		// loop while the current node does not point to head
		while (current.next != assassinsHead) {
			
			if (current.name.equalsIgnoreCase(theName) || 
				current.next.name.equalsIgnoreCase(theName)) {
				
				contains = true;
			} 
			
			current = current.next;
		}
		
		return contains;
	}
	
	/**
	 * This method checks if the assassin's name is in 
	 * grave yard ring.
	 * 
	 * @param theName The name of assassin.
	 * @return Returns true if the name is in the grave yard ring.
	 */
	public boolean graveyardContains(String theName) {
		
		AssassinNode current = graveHead;
		
		Boolean contains = false;
		
		// check only if the grave yard is not empty
		if (graveHead != null) {
			
			// loop while the current node does not point to null
			while (current.next != null) {
				
				if (current.name.equalsIgnoreCase(theName) || 
					current.next.name.equalsIgnoreCase(theName)) {
					
					contains = true;
				} 
				
				current = current.next;
			}
		}
		
		return contains;
	}
	
	/**
	 * This method kills the person with specified name, by removing 
	 * them from the kill ring, and placing them in the grave yard.
	 *  
	 * @param theName The name of assassin.
	 */
	public void kill(String theName) {
		
		AssassinNode current = assassinsHead;
		AssassinNode temp;
		
		// if the killed assassin is the head, remove them
		// and reassign the head to the next position.
		if (current.name.equalsIgnoreCase(theName)) {
			
			temp = assassinsHead.next;
			
			// loop until we find the last node which "points" to the head
			while (temp.next != assassinsHead) {
				
				temp = temp.next;
			}
			
			// place killed assassin to the grave yard
			addKilledAssassinToGraveYard(assassinsHead);
			
			// point head to the next position
			temp.next = temp.next.next;
			assassinsHead = current.next;
		
			
		// check if the killed assassin is not the head
		} else {
			
			while (!current.next.name.equalsIgnoreCase(theName)) {
				
				current = current.next;
			}
			
			// place killed assassin to the grave yard
			addKilledAssassinToGraveYard(current.next);
			
			current.next = current.next.next;
		}
		
	}
	
	/**
	 * This method check if the game is over. 
	 * If there is only one assassin in the kill ring 
	 * then the game is over.
	 * 
	 * @return Returns true if there’s only one person left.
	 */
	public boolean isGameOver() {
		
		Boolean gameOver= false;
		
		if (assassinsHead.next == assassinsHead) {
			gameOver = true;
		
		} else {
			gameOver = false;
		}
		
		return gameOver;
	}
	
	/**
	 * This method returns the name of the winner.
	 * 
	 * @return Returns the name of the winner if the game is over and null otherwise.
	 */
	public String winner() {
		
		String winner = null;
		
		if (isGameOver()) {
			winner = assassinsHead.name;
		}
		
		return winner;
	}
	
	/**
	 * Private helper method to add assassin to the grave yard.
	 * 
	 * @param theAssassin The killed assassin.
	 */
	private void addKilledAssassinToGraveYard(AssassinNode theAssassin) {
		
		// if the grave yard is empty place assassin to the
		// head of grave yard ring
		if (graveHead == null) {
			
			graveHead = new AssassinNode(theAssassin.name);
			graveHead.killer = findPreviousNode(theAssassin);
			graveHead.next = null;
		
			
		// otherwise place assassin to the next position
		} else {
			
			AssassinNode current = graveHead;
			
			while (current.next != null) {
				current = current.next;
			}
			
			current.next = new AssassinNode(theAssassin.name);
			current.next.killer = findPreviousNode(theAssassin);
			current.next.next = null;

		}
		
	}
	
	/**
	 * Helper method to find a assassin's killer.
	 * 
	 * @param theAssassin The killer of an assassin.
	 * @return The killer's name.
	 */
	private String findPreviousNode(AssassinNode theAssassin) {
		
		AssassinNode current = assassinsHead;
		
		while (current.next != theAssassin) {
			current = current.next;
		}
		
		return current.name;
	}
	
	
	
	// ASSASSIN NODE INNER CLASS
	private static class AssassinNode {
		
		/** The name of assassin */
		private String name;
		
		/** The person who killed this assassin. */
		private String killer;
		
		/** The next node(assassin) in the linked list.*/
		private AssassinNode next;
		
		/**
		 * Constructs a new assassin node.
		 * 
		 * @param name The name of assassin.
		 */
		public AssassinNode(String name) {
			
			this.name = name;
		}
		
		
		/**
		 * This method returns the assassin's name.
		 * 
		 * @return Return the assassin's name.
		 */
		public String getName() {
			return name;
		}
		
		/**
		 * This method sets the assassin's name.
		 * 
		 * @param name The name of the assassin.
		 */
		public void setName(String name) {
			this.name = name; 
		}
		
		/**
		 * This method return the next assassin.
		 * 
		 * @return Returns the next assassin.
		 */
		public AssassinNode getNext() {
			return next;
		}
		
		/**
		 * This method sets the next assassin.
		 * 
		 * @param next The next assassin.
		 */
		public void setNext(AssassinNode next) {
			this.next = next;
		}

		@Override
		public String toString() {
			return "AssassinNode [name=" + name + ", killer=" + killer + ", next=" + next + "]";
		}
		
	}
}
