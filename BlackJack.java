// Name: Kaillah Selvaretnam
// CSE 145 1736
// Professor: Jeremiah Ramsey
// Lab 4
// This program allows the user to play the popular game "BlackJack".


import java.util.*;
import java.io.*;

public class BlackJack {
   //fields
   private final String[] suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
   private final String[] faces = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
   private final int[] weight = {11, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
   
   private Stack<String> deck;
   private Queue<String> dealer;
   private Queue<String> player;
   private Map<String, Integer> faceToWeight;

   
   //constructor
   public BlackJack() {
      deck = new Stack<>();
      dealer = new LinkedList<>();
      player = new LinkedList<>();
      faceToWeight = new HashMap<>();
      
      int count = 0;
      for (String face: faces) {
         faceToWeight.put(face, weight[count]);
         count++;
      }  
   }
   
   //This method creates an ordered standard 52 card deck. There are no parameters. 
   private void createDeck() {
      for (String suit : suits) {
         for (String face: faces) {
            deck.push(face + " of " + suit);
         }
      }
   }
   
   //This method shuffles the card deck. There are no parameters. 
   private void shuffleDeck() {
      Random rand = new Random();
      List<String> tempDeck = new LinkedList<>();
      
      while (!deck.isEmpty()) {
         tempDeck.add(deck.pop());
      }
      
      for (int i = 0; i < 52; i++) {
         int randIndex = rand.nextInt(52-i);
         String card = tempDeck.get(randIndex);
         tempDeck.remove(randIndex);
         deck.push(card);
      }
   }
   
   //This method deals a card to the given player and and keeps track of the players total points.
   //The parameters are a Queue<String> representing the players current hand, and integer
   //representing the players current points, and a String representing which player is playing. 
   //The return of this method is an integer representing the players current number of points. 
   private int deal(Queue<String> hand, int points, String name) { 
      String newCard = deck.pop();
      hand.add(newCard);
      
      Scanner scanner = new Scanner(newCard);
      String key = scanner.next();
      
      points += faceToWeight.get(key);
      printHand(hand, points, name);
      
      return points;      
   }
   
   //This method prints the hand and the current points of the given player. The parameters
   //are a Queue<String> representing the players current hand, and integer
   //representing the players current points, and a String representing which player is playing.
   private void printHand(Queue<String> hand, int points, String name) {
      
      System.out.println(name + " hand:");
      Queue<String> aux = new LinkedList<>();
      while(!hand.isEmpty()) {
         String card = hand.remove();
         aux.add(card);
         System.out.println("   " + card);
         
      }
      
      while(!aux.isEmpty()) {
         hand.add(aux.remove());
      }
      System.out.println();
      System.out.println();
      System.out.println("   current points: " + points);
      System.out.println();
      System.out.println();

   } 
   
   //This method allows the player to play the BlackJack game. There are no paramters. 
   public void play() {
      //introduce the game
      System.out.println("Lets play BlackJack!\nEnter 'Hit' or 'Stand' each round with out " + 
      "exeeding 21.\nIn this version, Aces are worth 11 points.");
      System.out.println();
      
      int dealerPoints = 0;
      int playerPoints = 0;
      createDeck();
      shuffleDeck();
      Scanner scanner = new Scanner(System.in);
      
      //first dealt cards 
      playerPoints = deal(this.player, playerPoints,"player");
      dealerPoints = deal(this.dealer, dealerPoints, "dealer");
      playerPoints = deal(this.player, playerPoints, "player");
      
      //game play
      while (true) {
         System.out.print("Hit or Stand? ");
         String action = scanner.next();
         
         if (action.equalsIgnoreCase("Hit") && playerPoints < 21){
            playerPoints = deal(this.player, playerPoints, "player");
            
            if (playerPoints > 21) {
               System.out.println("Bust! You Loose.");
               break;
            }
         } else if (action.equalsIgnoreCase("Stand")) {
            while (dealerPoints <= 17) {
               dealerPoints = deal(this.dealer, dealerPoints, "dealer");
            }
            
            if (dealerPoints > 21 || (21 - dealerPoints) > (21 - playerPoints)) {

               System.out.println("You Win!");
               break;
            }
            
            if ((21 - dealerPoints) < (21 - playerPoints)) {
               System.out.println("You Loose.");
               break;
            }
            
            if (dealerPoints == playerPoints) {
               System.out.println("It's a tie!");
               break;
            }    
         }
      }    
   }
   
   //This method implements the BlackJack game. 
   public static void main(String[] args) {
      BlackJack game = new BlackJack();
      game.play();
   }
 
}