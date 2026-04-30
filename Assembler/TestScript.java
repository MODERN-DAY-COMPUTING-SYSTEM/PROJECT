import java.util.Scanner;

import java.nio.file.Paths;

public class TestScript {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // your code here
        boolean MATCHING = true;
        int LOCATION = 1; //FIRST_LINE

        try(Scanner fileScanner = new Scanner(Paths.get("output.hack"))){
            
            boolean Matching = true;

            try(Scanner fileScanner1 = new Scanner(Paths.get("HackAssemblerOutput.hack"))){

                String Binary = "";  // Personal generated binary
                String Binary1 = ""; // Machine  generated binary
                boolean matching = true;
               
                while(fileScanner.hasNextLine() && fileScanner1.hasNextLine()){
                    
                    Binary = fileScanner.nextLine();
                    Binary1 = fileScanner1.nextLine();
               
                    if(Binary.equals(Binary1) == false){
                    
                        matching = false;
                        break;
                    
                    }

                    else{

                        matching = true;
                        LOCATION++;
                        continue;

                    }

                }

                Matching = matching;

            }

            catch(Exception E){
               
                System.out.println("There seems to be some sort of problem in opening machine file");
               
                System.out.println("Error: "+ E);
            
            }

            MATCHING = Matching;
        
        }

        catch(Exception e){
            
            System.out.println("There seems to be sort of problem with personal file.");
            System.out.println("Error: "+ e);
        
        }

        System.out.println("The programs are matching : "+ MATCHING);
        
        if(MATCHING == true){
        
            System.out.println("SUCCESS!");
        
        }

        else{

            System.out.println("FAILURE!");
            System.out.println("Repeat failure at line number : "+ LOCATION);

        }
        
        scanner.close();
   
    }

}