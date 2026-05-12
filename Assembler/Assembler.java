import java.util.Scanner;
import java.util.ArrayList;
import java.lang.Math;
import java.nio.file.Paths;
import java.util.regex.Pattern;
//.function(Pattern.quote("+"))

public class Assembler {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // ASSEMBLER PROJECT
        // The file name is Code.asm
        ArrayList<String> untranslatedCode = new ArrayList<String>();
        ArrayList<String> translatedCode = new ArrayList<String>();
        ArrayList<ArrayList<String>> translatedTable = new ArrayList<ArrayList<String>>();
        try(Scanner fileScanner = new Scanner(Paths.get("Code.asm"))){
            while(fileScanner.hasNextLine()){
                String untranslatedRow = fileScanner.nextLine();
                untranslatedCode.add(untranslatedRow);
            }
        }    
        catch(Exception e){
            System.out.println("the file couldnt be accessed");
            System.out.println("Error is "+ e);
        }
        ArrayList<String> refinedCode = new ArrayList<String>();
        // we will now call the refining function.
        // to ignore the comments.
        refinedCode = refiningFunction(untranslatedCode);    
        ArrayList<String> untranslatedCode_new = refinedCode;
        // printingFunction(untranslatedCode_new);
        // Now that we have a untranslated code stored in our data structure we have to
        // make a translation function
        // We have to handle the Label/Loop variables beforehand;  
        // find some way to transmit the data to the tranlationFunction
        // Lets send a ArrayList<String>
        // find some way to transmit the data to the tranlationFunction
        // Lets send a ArrayList<String>
        ArrayList<String> labelVariables = new ArrayList<String>(); 
        labelVariables  = labelGenerationFunction(untranslatedCode_new);
        translatedTable = translationFunction(untranslatedCode_new, labelVariables);
        translatedCode = translatedTable.get(0); // CODE --> 0
        //We need to display our whole variable table AND we also need its value stored in main memory here
        ArrayList<String> variableTable = translatedTable.get(1);
        
        printingFunction(translatedCode);
        // displayFunction(variableTable);
        
        /* 
        We have ran into the spacing issue
        We have to make sure that there are no extra spaces before pressing enter
        Lets write the code for that character != ' '
        i.e. if(character == whitespace){break;}
        i.e. I got a string and i gotta pass that String through a refining function
        */

        scanner.close();
    }
    public static ArrayList<String> labelGenerationFunction(ArrayList<String> untranslatedCode){
        ArrayList<String> loopVariables = new ArrayList<String>();
        int i = 0;
        int counter = 0; //This is the label counter
        // Used to keep track of the right lines at which label variables point to
        while(i <= untranslatedCode.size() -1){
            char fistCharacter = untranslatedCode.get(i).charAt(0);
            if(fistCharacter == '('){ 
                String labelName = insideBracketFunction(untranslatedCode.get(i));
                int location = i;
                location = location - counter;
                String variableValue = String.valueOf(location); 
                String variableName = labelName + ";" + variableValue;
                loopVariables.add(variableName);
                counter++;
            }
            i++;
        }    
        return loopVariables;
    }
    public static void printingFunction(ArrayList<String> toPrint){
        for(String row : toPrint){
            System.out.println(row);
        }
    }
    public static void displayFunction(ArrayList<String> variable){
        System.out.println("__________________________________________________________________________________");
        System.out.println("| VARIABLE NAME            |" + " VALUE       |" + " BINARY-VALUE                          |");
        System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
        String s1 = "VARIABLE NAME            ";
        int size1 = s1.length();
        String s2 = "VALUE       ";
        int size2 = s2.length();
        String s3 = "BINARY-VALUE                          ";
        int size3 = s3.length();
        for(int i = 0; i <= variable.size() -1; i++){
            String[] variableData = variable.get(i).split(";");
            String variableName = variableData[0];
            int length1 = size1 - variableName.length();
            String whiteSpaces1 = whiteSpaceFunction(length1);
            // variableName = variableName + whiteSpaces1;
            String variableValue = variableData[1];
            int value = Integer.valueOf(variableValue);
            String binaryValue = binaryFunction(value);
            System.out.print("| "+ variableName + whiteSpaces1 + "| ");
            int length2 = size2 - variableValue.length();
            String whiteSpaces2 = whiteSpaceFunction(length2); 
            System.out.print(variableValue + whiteSpaces2 + "| ");
            int length3 = size3 - binaryValue.length();
            String whiteSpaces3 = whiteSpaceFunction(length3);
            System.out.print(binaryValue + whiteSpaces3 + "|\n");
            System.out.println("‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾‾");
        }    
    }
    public static String whiteSpaceFunction(int length1){
        String whiteSpaces = "";
        for(int i = 1; i <= length1; i++){
            whiteSpaces = whiteSpaces + " ";
        }
        return whiteSpaces;
    }
    public static ArrayList<ArrayList<String>> translationFunction(ArrayList<String> untranslatedCode, ArrayList<String> loopVariables){
        ArrayList<String> translatedCode = new ArrayList<String>();
        ArrayList<String> variables = new ArrayList<String>();
        ArrayList<ArrayList<String>> translatedTable = new ArrayList<ArrayList<String>>();
        int i = 0;
        int variableCounter = 16;
        variables = predefinedAddition();
        for(int j = 0; j <= loopVariables.size() -1; j++){
            variables.add(loopVariables.get(j));
        }
        while(i <= untranslatedCode.size() -1){
            char fistCharacter = untranslatedCode.get(i).charAt(0);
            char secondCharacter = untranslatedCode.get(i).charAt(1);
            if(fistCharacter == '('){ //Lets deal with loops first 2 if's
                /*
                String labelName = insideBracketFunction(untranslatedCode.get(i));
                int location = i;
                String variableValue = String.valueOf(location); 
                String variableName = labelName + ";" + variableValue;
                variables.add(variableName);
                */
                i = i + 1;
                continue;
            }

            if(fistCharacter == '@' && (secondCharacter == '0' || secondCharacter == '1' || secondCharacter == '2' || 
            secondCharacter == '3' || secondCharacter == '4' || secondCharacter == '5' || secondCharacter == '6' || 
            secondCharacter == '7' || secondCharacter == '8' || secondCharacter == '9')){              
                //checking if it is a number or a variable at second place.
                String translatedRow = binaryFunction(untranslatedCode.get(i));
                translatedCode.add(translatedRow);
            }
            else if(fistCharacter == '@' && (secondCharacter != '0' || secondCharacter != '1' || 
            secondCharacter != '2' ||secondCharacter != '3' || secondCharacter != '4' || 
            secondCharacter != '5' || secondCharacter != '6' || secondCharacter != '7' || 
            secondCharacter != '8' || secondCharacter != '9')){
                String[] temporary = untranslatedCode.get(i).split("@");
                String translatedRow;
                String variableName = temporary[1];

                if(variableCheckFunction(variables, variableName) == false){
                    variableName = variableName + ";" + String.valueOf(variableCounter); //CSV
                    // CSV --> Comma Separated Values
                    variables.add(variableName);
                    translatedRow = variableTranslationFunction(variables.get(variables.size()-1));
                    variableCounter++; // we start from sixteen
                }

                else{
                  translatedRow = alreadyExistingFunction(variables, variableName);    
                }
                translatedCode.add(translatedRow);
            }              
            else{
                Assembler assembler = new Assembler();
                Assembler.ControlBits controls = assembler.new ControlBits(untranslatedCode.get(i));
                String translatedRow = controls.Command;
                translatedCode.add(translatedRow);
            } 
            i++;
        }
        translatedTable.add(translatedCode); // 0 --> CODE
        translatedTable.add(variables);      // 1 --> TABLE
        return translatedTable;
    }
    public static String insideBracketFunction(String label){
        int i = 0;
        char characterValue = label.charAt(i);
        String name = "";
        while(true){
            i++;
            characterValue = label.charAt(i);          
            if(characterValue == ')'){
                break;
            }
            name = name + label.charAt(i);
        }
        return name;
    }
    public static boolean variableCheckFunction(ArrayList<String> variables, String varibleName){
        boolean checker = false;
        for(int i  = 0; i <= variables.size() -1; i++){
            String temporary = variables.get(i);
            String[] temporary1 = temporary.split(";");
            String temporary2 = temporary1[0];
            if(temporary2.equals(varibleName)){
                checker = true;
            }
        }    
        return checker;
    }
    public static String alreadyExistingFunction(ArrayList<String> variables, String variableName){
        String translatedRow;
        int value = 0;
        for(int i = 0; i <= variables.size() -1; i++){
            String[] temporary = variables.get(i).split(";");
            String temporary1 = temporary[0];
            String value1 = temporary[1];
            if(temporary1.equals(variableName)){
                value = Integer.valueOf(value1);
                break;
            }
        }
        translatedRow = binaryFunction(value);
        return translatedRow;
    }

    public static String variableTranslationFunction(String untranslatedRow){
        String translatedRow;
        String[] temporary = untranslatedRow.split(";");
        translatedRow = temporary[1];
        String translatedBinaryRow = binaryFunction(Integer.valueOf(translatedRow));
        return translatedBinaryRow;
    }

    public static ArrayList<String> refiningFunction(ArrayList<String> untranslatedCode){
        ArrayList<String> refinedCode = new ArrayList<String>();
        int i = 0;
        while(i <= untranslatedCode.size() -1){
            if(untranslatedCode.get(i).equals("") != true){ // now we can ignore blankLines
                if(untranslatedCode.get(i).charAt(0) != '/'){
                    // we are using this function to ignore the comments in the code
                    String row = RefineryFunction(untranslatedCode.get(i));
/*                     if(row.contains("A") || row.contains("D") || row.contains("M") ||
                       row.contains("@") || row.contains("(") || row.contains("=") ||
                       row.contains("!") || row.contains("&") || row.contains("|") ||
                       row.contains("1") || row.contains("0") || row.contains("-1")|| 
                       row.contains("J") || row.contains("N") || row.contains("-") ||
                       row.contains(Pattern.quote("+"))){      */   
                        refinedCode.add(row);
                    // REVEALATION - Empty Lines are handled Automaticallly}    
                }
            }
            i++;
        }
        return refinedCode;
    }
    public static String RefineryFunction(String original){
        String updated = "";//NULL is unpredictable
        int i = 0;        
        while(i <= original.length() -1){
            // If the Code was using ; it would have been Easy.
            // This also means no white-space use in the code allowed
            // f.y.i. empty lines are also not allowed .contains(''|''|''|'')
            // Revealation: Empty lines are handled automatically , idk man really idk it works!
            // Alright Let me also allow empty lines
            // Update : This doesn't work 2 weeks later
            // Be careful y'all
            if(original.charAt(i) == ' '){
                break;
            }
            updated = updated + original.charAt(i);
            i++;
        }
        return updated;
    }
    public static String binaryFunction(String untranslatedRow){
        
        String translatedRow = "";
        String[] temporarySplitter = untranslatedRow.split("@");
        String valueStr = temporarySplitter[1];
        int value = Integer.valueOf(valueStr);
        int quotient = (value / 2);
        int remainder = (value % 2);
        int counter = 1;
        value = quotient;
        //int placeCounter = 0; //first we are operating at the base of 10
        //int placeValue = (int)Math.pow(10, placeCounter); //typeCasting double --> integer
        //int binaryValue = remainder*placeValue;
        translatedRow = String.valueOf(remainder) + translatedRow;
        while(quotient != 0){
            quotient = value/2;
            remainder = value%2;
            value = quotient;
            translatedRow = String.valueOf(remainder) + translatedRow;
            counter++;
        }
        String zeros = "";
        for(int i = 1; i <= 16-(counter+1); i++){
            zeros = zeros + "0";
        }
        translatedRow = "0" + zeros + translatedRow;
        return translatedRow;
    }
    
    public static String binaryFunction(int value){
        String binaryTranslation = "";
        int quotient = (value / 2);
        int remainder = (value % 2);
        int counter = 1;
        value = quotient;
        binaryTranslation = String.valueOf(remainder) + binaryTranslation;
        while(quotient != 0){
            quotient = value/2;
            remainder = value%2;
            value = quotient;
            binaryTranslation = String.valueOf(remainder) + binaryTranslation;
            counter++;
        }
        String zeros = "";
        for(int i = 1; i <= 16-(counter+1); i++){
            zeros = zeros + "0";
        }
        binaryTranslation = "0" + zeros + binaryTranslation;
        return binaryTranslation;
    }
    public static ArrayList<String> predefinedAddition(){
        ArrayList<String> keyWords = new ArrayList<String>();
        String[] keywords = {"R0;0", "R1;1", "R2;2", "R3;3", "R4;4", "R5;5", "R6;6", "R7;7",
                             "R8;8", "R9;9", "R10;10", "R11;11", "R12;12", "R13;13", "R14;14",
                             "R15;15", "SCREEN;16384", "KBD;20481", "SP;0", "LCL;1", "ARG;2",
                             "THIS;3", "THAT;4" };
        
        for(int i = 0; i <= keywords.length -1; i++){
            keyWords.add(keywords[i]);
        }                     
        return keyWords;
    }

    public class ControlBits{
        private String[] opCode = {"0", "1"}; //a
        private String[] computation = {"0;101010", "1;111111", "-1;111010", "D;001100", "A;110000",
                                        "!D;001101", "!A;110001", "-D;001111", "-A;110011",
                                         "D+1;011111", "A+1;110111", "D-1;001110", "A-1;110010",
                                         "D+A;000010", "D-A;010011", "A-D;000111", "D&A;000000",
                                         "D|A;010101" }; //cccccc
        private String[] destination = {"NULL;000", "M;001", "D;010", "DM;011", "A;100", "AM;101",
                                        "AD;110", "ADM;111"}; //ddd
        private String[] jumper =      {"NULL;000", "JGT;001", "JEQ;010", "JGE;011", "JLT;100", 
                                        "JNE;101", "JLE;110", "JMP;111"}; //jjj
        public String Command; 
        public ControlBits(String Command){
            this.Command = commandFunction(Command);
        }
        public String commandFunction(String Command){
            String finalCommand = "";
            boolean checkSemicolon = Command.contains(";");
            boolean checkM;
            // boolean checkM = Command.contains("M"); misleading{just here so I can learn from my errors}
            boolean checkEqual = Command.contains("=");
            String Destination;
            String Jumper;
            String Computation;
            String opCode;
            if(checkSemicolon && checkEqual){
                String[] initial = Command.split(";");
                String jumper = initial[1];
                Jumper = jumperFunction(jumper);
                String other = initial[0];
                String[] information = other.split("=");
                String destination = information[0];
                Destination = destinationFunction(destination);
                String computation = information[1];
                checkM = computation.contains("M"); 
                Computation = computationFunction(computation, checkM);
                opCode = "0"; //default 
                if(checkM){
                    opCode = "1";
                }
                finalCommand = "111" + opCode + Computation + Destination + Jumper; // 111accccccdddjjj                
            }
            else if(checkSemicolon == true && checkEqual == false){
                String[] information = Command.split(";");
                String computation = information[0];
                String jumper = information[1];
                Destination = "000"; //NULL
                checkM = computation.contains("M"); 
                Computation = computationFunction(computation, checkM);
                Jumper = jumperFunction(jumper);
                opCode = "0";
                if(checkM){
                    opCode = "1";
                }
                finalCommand = "111" + opCode + Computation + Destination + Jumper; // 111accccccdddjjj                
            }
            else if(checkSemicolon == false && checkEqual == true){
                String[] information = Command.split("=");
                String destination = information[0];
                String computation = information[1];
                checkM = computation.contains("M"); 
                Jumper = "000"; //i.e. NULL
                Destination = destinationFunction(destination);
                Computation = computationFunction(computation, checkM);
                opCode = "0";
                if(checkM){
                    opCode = "1";
                }
                finalCommand = "111" + opCode + Computation + Destination + Jumper; // 111accccccdddjjj                
            }
        return finalCommand;
        }
        public String jumperFunction(String jumper){
            String binaryTranslation="";
            for(int i = 0; i <= this.jumper.length -1; i++){
                String[] information = this.jumper[i].split(";");
                String instruction = information[0];
                String value = information[1];
                if(jumper.equals(instruction)){
                    binaryTranslation = value;
                    break;
                } 
            } 
        return binaryTranslation;
        }
        public String destinationFunction(String destination){
            String binaryTranslation="";
            for(int i = 0; i <= this.destination.length -1; i++){
                String[] information = this.destination[i].split(";");
                String instruction = information[0];
                String value = information[1];
                if(destination.equals(instruction)){
                    binaryTranslation = value;
                    break;
                } 
            } 
        return binaryTranslation;
        }    
        public String computationFunction(String computation, boolean checkM){
            String computation_new;
            String binaryTranslation = "";
            if(checkM){
                computation_new  = replaceFunction(computation);
            }
            else{
                computation_new = computation;
            }
            String inverseString = "";
            if(computation_new.contains("+") || computation_new.contains("-") || 
               computation_new.contains("&") || computation_new.contains("|")){
                inverseString = inverseFunction(computation_new);
            }
            for(int i  = 0; i <= this.computation.length -1; i++){
                String[] information = this.computation[i].split(";");
                String instruction = information[0];
                String value = information[1];
                if(instruction.contains("-")){
                    if(instruction.equals(computation_new)){
                        binaryTranslation = value;
                        break;
                    }
                }
                else{    
                    if(instruction.equals(computation_new) || instruction.equals(inverseString)){
                        binaryTranslation = value;
                        break;
                    }
                }
            }
        return binaryTranslation;
        }
        public String inverseFunction(String computation_new){
            String inverseString; // A+D --> D+A; +,|,&,A-D --> D-A
            String middle = "";
            if(computation_new.contains("+")){
                middle = "+";
            }
            else if(computation_new.contains("-")){
                middle = "-";
            }
            else if(computation_new.contains("&")){
                middle = "&";
            }
            else if(computation_new.contains("|")){
                middle = "|";
            }
            // middle = "\\" +middle; Regex
            String[] twoSides = computation_new.split(Pattern.quote(middle));
            String side1 = twoSides[0];
            String side2 = twoSides[1];
            inverseString = side2 + middle + side1;
            return inverseString;
        }
        public String replaceFunction(String computation){
            String replacement = "";
            int mIndicator = 77;
            String[] storage = new String[2];
            storage[0] = ""; // Be very very careful with NULL values
            storage[1] = "";
            int m = 0; // storage parts
            for(int i = 0; i <= computation.length() -1; i++){
                if(computation.charAt(i) == 'M'){
                    // storage[m] = storage[m] + String.valueOf(computation.charAt(i)); 
                    mIndicator = 777;
                    m++;
                    continue;
                }
                else{
                    storage[m] = storage[m] + String.valueOf(computation.charAt(i));
                }
            }
            if(mIndicator == 777){
                replacement = storage[0] + "A" + storage[1];
            }
            else{
                replacement = computation;
            }
        return replacement;
        }
    }
}

