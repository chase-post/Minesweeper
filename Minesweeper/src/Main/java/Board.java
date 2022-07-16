/* Name: Chase Post
 * Pennkey: cpost
 * Recitation: 205
 * Description: Creates Board object with attribute grid, a 2D array of Tile objects.
 *              Includes methods for placing random mines, assigning number 
 *              attributes to the board's tiles, and recursively revealing adjacent 
 *              blank tiles from a certain starting point.
 * Execution: N/A
 */
public class Board {
    
    //attribute declaration
    private Tile[][] grid;
    
    
    //constructor
    public Board(Tile[][] grid) {
        this.grid = grid;
    }
   
    
    /* Input: new 2D grid of Tiles
     * Output: None
     * Description: changes the grid attribute of the Board object to the newGrid
     */
    public void changeGrid(Tile[][] newGrid) {
        this.grid = newGrid;
    }
    
    
    /* Input: 2D array of tile objects
     * Output: none
     * Description: Modifies board's array by changing attribute of 10 random tiles
     *              to mines (char 'M')
     */
    public void randomMines(int numMines) {
        int currNumMines = 0;
        while (currNumMines < numMines) {
            int randomCol = (int) (9 * Math.random());
            int randomRow = (int) (9 * Math.random());
            //check that random mine is unrevealed and empty
            if (this.grid[randomRow][randomCol].getData() == 'E') {
                this.grid[randomRow][randomCol].changeData('M');
                currNumMines++;
            }
        }
    }
    
    
    /* Input: None
     * Output: none
     * Description: Modifies entire 2D array of board object to assign number 
     *              attribute to each tile depending on number of adjacent mines
     */
    public void assignNumbers() {
        int numRows = this.grid.length;
        int numColumns = this.grid[0].length;
        //iterate through 2D array
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                //initial number is 0 for all squares not mines
                if (this.grid[row][col].getData() != 'M') {
                    int displayedNum = 0;
                    //iterate through adjacent tiles
                    for (int x = col - 1; x <= col + 1; x++) {
                        for (int y = row - 1; y <= row + 1; y++) {
                            //if not out of bounds and a mine, add to displayed num
                            if (x >= 0 && y >= 0 && x < numColumns && y < numRows) {
                                if (this.grid[y][x].getData() == 'M') {
                                    displayedNum++;
                                }
                            }
                        }
                    }
                    //update current tile's data if displayedNum greater than 0
                    if (displayedNum > 0) {
                        char charNum = (char) (displayedNum + 48);
                        this.grid[row][col].changeData(charNum);
                    }
                }
            }
        }
    }
    
    
    /* Inputs: 2D array of tile objects, integer row, integer col
     * Output: none
     * Description: from starting position in array, reveals all adjacent blank tiles
     *              and repeats recursively for each revealed tile. 
     */
    public void adjacentBlanks(int row, int col) {
        int numRows = this.grid.length;
        int numColumns = this.grid[0].length;
        //iterate about current tile
        for (int x = col - 1; x <= col + 1; x++) {
            for (int y = row - 1; y <= row + 1; y++) {
                //current tile must be in bounds of array
                if (x >= 0 && y >= 0 && x < numColumns && y < numRows) {
                    //if current adjacent tile is empty, reveal it
                    if (this.grid[y][x].getData() == 'E') {
                        this.grid[y][x].changeData('B');
                        //recursive call to continue from the newly revealed tile
                        adjacentBlanks(y, x);
                        this.grid[y][x].draw();
                    //if current tile is number, draw and do not continue recursion
                    } else if (this.grid[y][x].getData() != 'M') {
                        this.grid[y][x].draw();
                    }
                }
            }
        }
    }
    
    
    /* Input: None
     * Output: None
     * Description: Draws entire board by iterating through array and drawing each 
     *              tile at its respective coordinates
     */
    public void draw() {
        //set up canvas
        PennDraw.setCanvasSize(600, 600);
        PennDraw.clear(255, 255, 191, 100);
        
        //define number of rows/columns and scale
        int numRows = this.grid.length;
        int numColumns = this.grid[0].length;
        PennDraw.setXscale(0, numColumns + 1);
        PennDraw.setYscale(0, numRows + 1);
        
        //draw Title
        PennDraw.setFontSize(38);
        PennDraw.setFontBold();
        PennDraw.text(4, 9.4, "Minesweeper");
        //insert Title Image
        PennDraw.picture(1, 9.5, "minesweep.png", 50, 50);
        
        //set font size for numbers
        PennDraw.setFontSize(18);
        //iterate through 2D array and draw each Tile
        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numColumns; col++) {
                grid[row][col].draw();
            }
        }
    }
}