public class DeckOfCards {



}

class Card{
    int faceValue;
    Suit suit;

    Card(int faceValue, Suit suit){
        this.faceValue = faceValue;
        this.suit = suit;
    }
}

enum Suit{
    SPADE,
    CLOVER,
    DIAMOND,
    HEART
}

