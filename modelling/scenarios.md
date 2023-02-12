# Scenarios UNO

## scenario 1: playing a card

**start:** 
> Ash is playing UNO against three bots. <br>
> Every player has seven cards in their hand. <br>
> On top of the discard pile is a "blue 7" card. <br>
> Ash is on turn, the current playing direction is clockwise. <br>

**action:**
> Ash plays a "blue 2" card. <br>

**result:**
> Ash now has six cards in their hand, the bots still have seven cards. <br>
> There is now a "blue 2" card on top of the discard pile. <br>
> The bot on the left of Ash is in turn now. <br>

___
## scenario 2: playing a "draw two" card

**start:**
> Ash is playing UNO against three bots. <br>
> Every player has 5 cards on their hand. <br>
> On top of the discard pile is a "red 5" card. <br>
> Ash is on turn, the current playing direction is clockwise. <br>


**action:**
> Ash plays a "red draw two" card.

**result:**
> Ash now has four cards on their hand. <br>
> The "red draw two" card is on top of the discard pile now. <br>
> The bot on the left of Ash has to draw two cards from the draw pile and now has seven cards on their hand. <br>
> After drawing two cards the turn is skipped and the bot on the left is on turn.

___
## scenario 3: winning a game

**start:**
> Ash is playing UNO against three bots. <br>
> Ash has only one card on their hand, it is a "yellow 0" card. <br>
> On top of the discard pile is a "green 0" card. <br>
> Ash is on turn, the current playing direction is clockwise. <br>

**action:**
> Ash plays the "yellow 0" card. <br>

**result:**
> Ash has no other card on their hand. <br>
> Ash won the game. <br>