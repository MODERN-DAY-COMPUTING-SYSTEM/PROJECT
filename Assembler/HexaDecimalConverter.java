import java.util.Scanner;
import java.util.ArrayList;
import java.nio.file.Paths;
import java.io.FileWriter;

public class HexaDecimalConverter {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<String> hexadecimalConverted = new ArrayList<String>();
        // Lets convert the binary file code into hexadecimal format so that it makes it easier for
        // the Logisim software to fetch because its making mistakes decoding
        // And just let hope that the ALU functions        
        try(FileWriter writer = new FileWriter("hex.txt", false)){ // false = overwrite
        writer.write("v2.O raw\n");    
        try(Scanner fileScanner = new Scanner(Paths.get("output.hack"))){
            while(fileScanner.hasNextLine()){
                String binaryRow = fileScanner.nextLine();
                String hexadecimalRow = converterFunction(binaryRow);
                hexadecimalConverted.add(hexadecimalRow);
            }
        } 
        catch(Exception e){
            System.out.println("There was an error accessing the file");
        }
        for(String row : hexadecimalConverted){
            System.out.println(row);
            writer.write(row + "\n");
        }
        }
        catch(Exception e){
            System.out.println("ERROR!");
        }
        scanner.close();
    }
    public static String converterFunction(String binaryRow){
        String hexadecimalRow = ""; // this is a string remember
        int placeholder0 = 1;
        int placeholder1 = 2;
        int placeholder2 = 4;
        int placeholder3 = 8;
        String[] hexadecimalFormat = {"10;a", "11;b", "12;c", "13;d", "14;e", "15;f"};
        int counter = 0;
        int value3 = 0, value2 = 0, value1 = 0, value0 = 0;
        int value = 0;
        while(counter <= 3){
            for(int i = counter*4; i <= counter*4 + 3; i++){
                if(i == counter*4){
                    value3 = placeholder3*(Integer.valueOf(String.valueOf(binaryRow.charAt(i))));
                }
                else if(i == counter*4 +1){
                    value2 = placeholder2*(Integer.valueOf(String.valueOf(binaryRow.charAt(i))));
                } 
                else if(i == counter*4 +2){
                    value1 = placeholder1*(Integer.valueOf(String.valueOf(binaryRow.charAt(i))));
                }
                else if(i == counter*4 +3){
                    value0 = placeholder0*(Integer.valueOf(String.valueOf(binaryRow.charAt(i))));
                }
            }
            value = value3 + value2 + value1 + value0;
            if(value == 10){
                hexadecimalRow = hexadecimalRow + "a";
            }
            else if(value == 11){
                hexadecimalRow = hexadecimalRow + "b";
            }
            else if(value == 12){
                hexadecimalRow = hexadecimalRow + "c";
            }
            else if(value == 13){
                hexadecimalRow = hexadecimalRow + "d";
            }
            else if(value == 14){
                hexadecimalRow = hexadecimalRow + "e";
            }
            else if(value == 15){
                hexadecimalRow = hexadecimalRow + "f";
            }
            else{
                hexadecimalRow = hexadecimalRow + String.valueOf(value);
            }
            counter++;
        }
        return hexadecimalRow;
    }
}