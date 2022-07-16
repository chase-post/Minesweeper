/* Name: Chase Post
 * Pennkey: cpost
 * Recitation: 205
 * Description: Creates Tile object with coordinate attributes and a char attribute
 *              called data representing the status of the Tile, its adjacency to
 *              mines, and its contents.
 * Execution: N/A
 */
public class Tile {
    
    //attribute declarations
    private double x;
    private double y;
    private char data;
    
    
    //Constructor for Tile object
    public Tile(double x, double y, char data) {
        this.x = x;
        this.y = y;
        this.data = data;
    }
    
    
    /* Input: char, new data for Tile
     * Output: None
     * Description: changes the data of the Tile object to the inputted char
     */
    public void changeData(char newData) {
        data = newData;
    }
    
    
    /* Input: None
     * Output: char, data of Tile
     * Description: Returns the char data of the Tile object
     */
    public char getData() {
        return this.data;
    }
    
    
    /* Input: None
     * Output: double, x-coordinate of tile
     * Description: Returns the double x-coordinate of the Tile object
     */
    public double getX() {
        return this.x;
    }
    
    
    /* Input: None
     * Output: double, y-coordinate of tile
     * Description: Returns the double y-coordinate of the Tile object
     */
    public double getY() {
        return this.y;
    }
    
    
    /* Input: None
     * Output: None
     * Description: Draws the tile at its coordinates with a halflength of 0.5. The
     *              appearance of the tile changes depending on its data attribute.
     */
    public void draw() {
        double halfLength = 0.5;
        double xCenter = this.x;
        double yCenter = this.y;
        char curr = this.data;
        
        //if unrevealed tile, draw 3D looking box
        if (curr == 'E' || curr == 'M') {
                PennDraw.setPenColor(PennDraw.BLACK);
                PennDraw.square(xCenter, yCenter, halfLength);
                PennDraw.setPenColor(PennDraw.GRAY);
                PennDraw.filledSquare(xCenter, yCenter, halfLength - 0.01);
                PennDraw.setPenColor(PennDraw.LIGHT_GRAY);
                PennDraw.filledSquare(xCenter, yCenter, halfLength - 0.08);
                PennDraw.setPenColor(PennDraw.WHITE);
                PennDraw.filledRectangle(xCenter - 0.46, yCenter, 
                                             0.025, halfLength - 0.02);
                PennDraw.filledRectangle(xCenter, yCenter + 0.46, 
                                             halfLength - 0.02, 0.025); 
        
        //Just draw plain gray square if revealed blank (char 'B')
        } else if (curr == 'B') {
            PennDraw.setPenColor(PennDraw.GRAY);
            PennDraw.filledSquare(xCenter, yCenter, halfLength - 0.02);
            
        /* if number, add printed number on top of gray square. change data to D 
         * after drawing for win calculation purposes.
         */
        } else if (curr == '1') {
            PennDraw.setPenColor(PennDraw.GRAY);
            PennDraw.filledSquare(xCenter, yCenter, halfLength - 0.02);
            PennDraw.setPenColor(PennDraw.BLUE);
            PennDraw.setFontBold();
            this.changeData('D');
            PennDraw.text(xCenter, yCenter, "1");
        } else if (curr == '2') {
            PennDraw.setPenColor(PennDraw.GRAY);
            PennDraw.filledSquare(xCenter, yCenter, halfLength - 0.02);
            PennDraw.setPenColor(PennDraw.GREEN);
            PennDraw.setFontBold();
            this.changeData('D');
            PennDraw.text(xCenter, yCenter, "2");
        } else if (curr == '3') {
            PennDraw.setPenColor(PennDraw.GRAY);
            PennDraw.filledSquare(xCenter, yCenter, halfLength - 0.02);
            PennDraw.setPenColor(PennDraw.RED);
            PennDraw.setFontBold();
            this.changeData('D');
            PennDraw.text(xCenter, yCenter, "3");
        } else if (curr == '4') {
            PennDraw.setPenColor(PennDraw.GRAY);
            PennDraw.filledSquare(xCenter, yCenter, halfLength - 0.02);
            PennDraw.setPenColor(PennDraw.BLUE);
            PennDraw.setFontBold();
            this.changeData('D');
            PennDraw.text(xCenter, yCenter, "4");
        } else if (curr == '5') {
            PennDraw.setPenColor(PennDraw.GRAY);
            PennDraw.filledSquare(xCenter, yCenter, halfLength - 0.02);
            PennDraw.setPenColor(PennDraw.BOOK_RED);
            PennDraw.setFontBold();
            this.changeData('D');
            PennDraw.text(xCenter, yCenter, "5");
        } else if (curr == '6') {
            PennDraw.setPenColor(PennDraw.GRAY);
            PennDraw.filledSquare(xCenter, yCenter, halfLength - 0.02);
            PennDraw.setPenColor(PennDraw.BOOK_RED);
            PennDraw.setFontBold();
            this.changeData('D');
            PennDraw.text(xCenter, yCenter, "6");
        } else if (curr == '7') {
            PennDraw.setPenColor(PennDraw.BOOK_RED);
            PennDraw.filledSquare(xCenter, yCenter, halfLength - 0.02);
            PennDraw.setPenColor(PennDraw.BOOK_RED);
            PennDraw.setFontBold();
            this.changeData('D');
            PennDraw.text(xCenter, yCenter, "7");
        } else if (curr == '8') {
            PennDraw.setPenColor(PennDraw.GRAY);
            PennDraw.filledSquare(xCenter, yCenter, halfLength - 0.02);
            PennDraw.setPenColor(PennDraw.BOOK_RED);
            PennDraw.setFontBold();
            this.changeData('D');
            PennDraw.text(xCenter, yCenter, "8");
        //if the tile is a revealed bomb, draw the bomb 
        } else if (curr == 'X') {
            PennDraw.setPenColor(PennDraw.GRAY);
            PennDraw.filledSquare(xCenter, yCenter, halfLength - 0.02);
            PennDraw.picture(xCenter, yCenter, "bomb.png", 50, 50);
        //if the tile is the flag button, draw flag button
        } else if (curr == 'F') {
            PennDraw.setPenColor(PennDraw.LIGHT_GRAY);
            PennDraw.filledSquare(xCenter, yCenter, halfLength - 0.02);
            PennDraw.picture(xCenter, yCenter, "flag.png", 50, 50);
            PennDraw.setPenColor(PennDraw.BLACK);
            PennDraw.setFontBold();
            PennDraw.square(xCenter, yCenter, halfLength - 0.02);
            PennDraw.text(xCenter - 1.5, yCenter + 0.3, "Click to");
            PennDraw.text(xCenter - 1.5, yCenter - 0.02, "Toggle");
            PennDraw.text(xCenter - 1.5, yCenter - 0.34, "Flag Mode:");
        }     
    }   
}