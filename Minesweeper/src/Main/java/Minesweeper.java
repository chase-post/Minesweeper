package Main.java;

/* Name: Chase Post
 * Pennkey: cpost
 * Recitation: 205
 * Description: Runs the main sequence of the Minesweeper game, including defining 
 *              a separate method for flag mode.
 * Execution: java Minesweeper
 */
public class Minesweeper {
    
    /* Input: 2D array of tile objects
     * Output: None
     * Description: defines what happens while in flag mode.
     */
    public static void flagMode(Tile[][] grid) {
        int numRows = grid.length;
        int numColumns = grid[0].length;
        boolean flagMode = true;
        //partially transparent black for the pressed flag Button
        PennDraw.setPenColor(0, 0, 0, 150);
        PennDraw.filledSquare(9.5, 9.5, 0.5);
        while (flagMode == true) {
            if (PennDraw.mousePressed()) {
                int flagCol = (int) PennDraw.mouseX();
                int flagRow = numRows - (int) (PennDraw.mouseY()) - 1;
                //if one of the tiles pressed
                if (flagRow < 9 && flagRow >= 0 && flagCol < 9 && flagCol >= 0) {
                    Tile flagSelected = grid[flagRow][flagCol];
                    PennDraw.picture(flagSelected.getX(), flagSelected.getY(), 
                                                         "flag.png",  50, 50);
                //if flag button pressed again
                } else if (flagRow < 0 && flagCol >= 9) {
                    flagMode = false;
                }
            }
        }
    }
    
    
    //main method & main game sequence
    public static void main(String[] args) {
        //generate initial board
        Tile[][] grid = new Tile[9][9];
        int numRows = grid.length;
        int numColumns = grid[0].length;
        
        //main game boolean
        boolean inGame = true;
        
        //infinite loop to enable restarting game
        while (true) {
            //loop to allow restarting with press of space bar
            while (inGame == false) {
                if (PennDraw.hasNextKeyTyped() && PennDraw.nextKeyTyped() == ' ') {
                        inGame = true;
                        break;
                }
            }

            //fill board with unrevealed blank Tiles ('E')
            for (int row = 0; row < numRows; row++) {
                for (int col = 0; col < numColumns; col++) {
                    /* define x and y coordinates of each Tile as avg of current and 
                     * next column/row. Also invert y-coordinates b/c PennDraw canvas
                     * and 2D array have vertically inverted origins.
                     */
                    double xCenter = (double) (col + (col + 1)) / 2;
                    int drawRow = numRows - row;
                    double yCenter = (double) (drawRow + (drawRow - 1)) / 2;
                    grid[row][col] = new Tile(xCenter, yCenter, 'E');
                }
            }
        
            //draw initial board
            Board b = new Board(grid);
            b.draw(); 
            //create and draw flag button
            Tile flagButton = new Tile(9.5, 9.5, 'F');
            flagButton.draw();

            //after first click, set initial revealed blanks and hidden mines
            boolean clicked = false;
            while (clicked == false) {
                if (PennDraw.mousePressed()) {
                    int col = (int) (PennDraw.mouseX());
                    int row = numRows - (int) (PennDraw.mouseY()) - 1;

                    //first click must be one of the game tiles
                    if (col < 9 && col >= 0 && row < 9 && row >= 0) {
                        //change clicked type to B: revealed blank
                        grid[row][col].changeData('B');
                        //redraw relevant tile
                        grid[row][col].draw(); 

                        //assign mines randomly
                        b.randomMines(10);
                        //assign numbered squares
                        b.assignNumbers();
                        //reveal all recursively adjacent empty tiles
                        b.adjacentBlanks(row, col); 

                        //update board
                        b.changeGrid(grid);
                        clicked = true;
                    }
                }
            }
            
            while (inGame) {
                //create boolean useful for flagMode button
                boolean wasItPressed = false;
                //find tile at row, col of click
                if (PennDraw.mousePressed()) {
                    int col = (int) PennDraw.mouseX();
                    int row = numRows - (int) (PennDraw.mouseY()) - 1;
                    Tile selected = null;
                    //if field of flag button, activate flag mode
                    wasItPressed = true;
                    if (col >= 9 && row < 0) {
                        if (!PennDraw.mousePressed() && wasItPressed) {
                             wasItPressed = false;
                            flagMode(grid);
                            flagButton.draw();
                        }
                        continue;
                    } else if (col < 9 && col >= 0 && row < 9 && row >= 0) {
                        selected = grid[row][col];
                    } else {
                        continue;
                    }

                    //if hidden mine, change data to be for revealed mine
                    if (selected.getData() == 'M') {
                        selected.changeData('X');

                    //if totally empty, change data to be for revealed empty
                    } else if (selected.getData() == 'E') {
                        selected.changeData('B');
                        //reveal any recursively adjacent blank squares
                        b.adjacentBlanks(row, col);
                    }

                    //if selected is any of above or numbered, draw it
                    selected.draw();

                    //lose message if click a bomb
                    if (selected.getData() == 'X') {
                        PennDraw.setPenColor(PennDraw.RED);
                        PennDraw.setFontBold();
                        PennDraw.setFontSize(100);
                        PennDraw.text(5, 5, "You Lose!");
                        PennDraw.setPenColor(PennDraw.WHITE);
                        PennDraw.filledRectangle(5, 4, 3.8, 0.35);
                        PennDraw.setPenColor(PennDraw.BLACK);
                        PennDraw.setFontSize(30);
                        PennDraw.text(5, 4, "Press space to Restart");
                        inGame = false;

                    }

                    /* win message if all tiles have type 'D' (which denotes drawn #)
                     * or type 'M' (hidden mine) or type 'B' (revealed blank tile)
                     */
                    boolean hasWon = true;
                    for (int y = 0; y < numRows; y++) {
                        for (int x = 0; x < numColumns; x++) {
                            char data = grid[y][x].getData();
                            if (data != 'D' && data != 'M' && data != 'B') {
                                hasWon = false;
                            } 
                        }
                    }

                    //if has won variable true, print win message and end game
                    if (hasWon == true) {
                        PennDraw.setPenColor(PennDraw.GREEN);
                        PennDraw.setFontBold();
                        PennDraw.setFontSize(100);
                        PennDraw.text(5, 5, "You Win!");
                        PennDraw.setPenColor(PennDraw.WHITE);
                        PennDraw.filledRectangle(5, 4, 3.8, 0.35);
                        PennDraw.setPenColor(PennDraw.BLACK);
                        PennDraw.setFontSize(30);
                        PennDraw.text(5, 4, "Press space to Restart");
                        inGame = false;
                    }
                }
            }
        }
    }
}