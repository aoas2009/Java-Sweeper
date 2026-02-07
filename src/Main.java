//EMOJI LIST
//💣  ⬛  ⬜  🟥
// 0️⃣1️⃣2️⃣3️⃣4️⃣5️⃣6️⃣7️⃣8️⃣
// [0][1][2][3][4][5][6][7][8][9]
// [ ] --Clear

import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class Main {
    //REGRAS PARA INPUT:
    // O input de coordenadas segue a ordem 00A:
    /*

        0's: São para a coordenada.
            O primeiro numero é a dimensão X. Considerando que 0 seja o canto esquerdo.
            O segundo numero é a dimensão Y. Considerando que 0 seja o canto inferior.
        A: tipo de ação, pode ser...
            c: para "Limpar" o quadrado.
            f: para colocar uma bandeira.

     */

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("=====!!WELCOME TO JAVASWEEPER!!=====");
        System.out.println("Before starting, let's set the game!\nInsert...");
        System.out.print("Field Height: ");
        int altura = sc.nextInt();
        System.out.print("Field Width: ");
        int largura = sc.nextInt();
        System.out.print("Mines index (%): ");
        int bombas = Math.round( ( ( altura * largura ) * sc.nextInt() ) / 100);

        String[][] field = new String[altura][largura];

        insertBombs(field, bombas);

        System.out.println("Great! Game created. " + bombas + " mines on field, good luck!");

        boolean lost = false;

        String lowerLine = "    ";
        for(int i = 0; i<largura; i++){
            lowerLine += i+"  ";
        }

        while (!lost) {
            printField(field, lowerLine);
            System.out.print("Next play: ");
            String input = sc.next().toLowerCase();

            char type = ' ';
            int x = Integer.parseInt(input.charAt(0) + "");
            int y = Integer.parseInt(input.charAt(1) + "");
            if (input.length() >= 3) {
                type = input.charAt(2);
            }
            lost = !openSpace(field, x, y);
        }
        if(lost){
            System.out.println("BOOOMM!!! you've lost :( better luck next time!");
            printFieldLost(field);
        }


    }

    public static void printField(String[][] field, String lowerLine) {
        System.out.println("========================================\n========================================");

        String currentLine = "";

        for(int y = field.length-1; y>=0; y--){
            currentLine += y+"  ";
            for(int x = 0; x< field[0].length; x++) {
                if(field[x][y]==null){
                    currentLine += "[ ]";
                }
                else if(field[x][y]=="clear"){
                    currentLine += "[/]";
                }
                else if(field[x][y]=="bomb"){
                    currentLine += "[ ]";
                }
                else if(field[x][y]=="flag"){
                    currentLine += "[F]";
                }
                else{
                    currentLine += numberToEmote(field[x][y]);
                }
            }
            System.out.println(currentLine);
            currentLine = "";
        }
        System.out.println(lowerLine);
    }

    public static void printFieldLost(String[][] field) {
        String currentLine = "";
        for(int y = field.length-1; y>=0; y--){
            for(int x = 0; x< field[0].length; x++) {
                if(field[x][y]==null){
                    currentLine += "[ ]";
                }
                else if(field[x][y]=="clear"){
                    currentLine += "[/]";
                }
                else if(field[x][y]=="bomb"){
                    currentLine += "[!]";
                }
                else if(field[x][y]=="flag"){
                    currentLine += "[F]";
                }
                else if(field[x][y]=="flagged-bomb"){
                    currentLine += "[F]";
                }
                else{
                    currentLine += numberToEmote(field[x][y]);
                }
            }
            System.out.println(currentLine);
            currentLine = "";
        }
    }

    public static int checkBombsAround(String[][] field, int x, int y){
        int bombCount = 0;
        for(int xStep = -1; xStep<2; xStep++){
            for(int yStep = -1; yStep<2; yStep++){
                int X = x+xStep;
                int Y = y+yStep;

                if(X >= 0 && X <= field.length -1) {
                    if (Y >= 0 && Y <= field[0].length -1) {
                        if (field[x + xStep][y + yStep] == "bomb") {
                            bombCount++;
                        }
                    }
                }
            }
        }
        return bombCount;
    }

    public static boolean openSpace(String[][] field, int x, int y){
        if(field[x][y]!="flag" && field[x][y]!="flagged-bomb") {
            if (field[x][y] != "bomb") {
                int nearBombs = checkBombsAround(field, x, y);
                if (nearBombs == 0) {
                    field[x][y] = "clear";
                } else {
                    field[x][y] = "" + checkBombsAround(field, x, y);
                }
                return true;
            }
            return false;
        }
        return true;
    }

    public static void placeFlag(String[][] field, int x, int y){
        if(field[x][y]!="bomb"){
            int nearBombs = checkBombsAround(field, x, y);
            if(nearBombs==0){
                field[x][y] = "clear";
            }
            else{
                field[x][y] = ""+checkBombsAround(field, x, y);
            }
        }
    }

    public static void insertBombs(String[][] field, int bombAmount){
        Random rand = new Random();
        int curX;
        int curY;
        int i = bombAmount;
        while(i>0){
            curX = rand.nextInt(0, field.length);
            curY = rand.nextInt(0, field[0].length);
            if(field[curX][curY]!="bomb"){
                field[curX][curY] = "bomb";
                i--;
            }
        }
    }

    public static String numberToEmote(String number){
        if(number.equals("1")) {
            return "[1]";
        }
        else if(number.equals("2")) {
            return "[2]";
        }
        else if(number.equals("3")) {
            return "[3]";
        }
        else if(number.equals("4")) {
            return "[4]";
        }
        else if(number.equals("5")) {
            return "[5]";
        }
        else if(number.equals("6")) {
            return "[6]";
        }
        else if(number.equals("7")) {
            return "[7]";
        }
        else if(number.equals("0")){
            return "[0]";
        }
        else{
            return "[8]";
        }
    }
}