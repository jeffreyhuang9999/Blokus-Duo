# Blokus-Duo
This code uses java. This is based on the board game Blokus Duo.
This is created from code you can find from https://github.com/patrickherrmann/Blokus

I created a menu with CardLayout. I tried and failed to create a bot.
I tried and somewhat succeeded in creating a coordinate system.
I tried and failed to create a system that saves games viewable as a txt file that users can modify and load based on a move system. 

I have hit a wall; this is as far as the code will get using just my ability.

Things to do:
-In theory, the program displays an error message if people place a piece off the starting point on the first move. I have no idea why this is the case. The error area takes the form of a large square around the bottom right starting point.

-Change the style of the program. The original code was done in kind of poor style that no one can understand. I added comments
based on what I thought the code did. 

If you change the style of the program please add comments so people will understand what you did.

-After changing the style, add a bot that moves based on a minimax algorithm.

-This is kinda separate from the program, but: Create a notation system that accounts for every orientation of the piece. For example, Y-piece, orientation 7, at (5,5). But condense that. Note that each piece has a pivot point.
Obviously the O-piece has no orientation. I5, I4, have only 2 orientations.

-Use the above notation to create exportable files. 

-Fix the janky-looking coordinate labels on the top and left of the board

-Make the pieces for each player appear to the sides of the board instead of as a scroll pane. 
