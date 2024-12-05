import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    public static ArrayList<int[]> rules = new ArrayList<>();
    public static ArrayList<int[]> updates = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        firstPart();
        secondPart3();
    }

    public static void processFile(List<String> input) {
        int counter = 0;
        for (int i = 0; i < input.size(); i++) {
            counter++;
            if (input.get(i).equals("")) {
                break;
            }
        }
        for (int i = 0; i < counter - 1; i++) {
            int[] rule = {Integer.parseInt(input.get(i).substring(0, 2)), Integer.parseInt(input.get(i).substring(3, 5))};
            rules.add(rule);
        }

        for (int i = counter; i < input.size(); i++) {
            // Pattern to match numbers
            String oneLine = input.get(i);
            Pattern pattern = Pattern.compile("\\d+");
            Matcher matcher = pattern.matcher(oneLine);
            ArrayList<Integer> numbersList = new ArrayList<>();

            while (matcher.find()) {
                numbersList.add(Integer.parseInt(matcher.group()));
            }
            int[] numbersArray = new int[numbersList.size()];
            for (int j = 0; j < numbersList.size(); j++) {
                numbersArray[j] = numbersList.get(j);
            }
            updates.add(numbersArray);
        }
    }

    public static void firstPart(){
        int result = 0;

        for (int i = 0; i < updates.size(); i++) {
            int[] current = updates.get(i);
            int counter = 0;

            for (int j = 0; j < rules.size(); j++) {
                boolean isOK = true;
                int index1 = - 1;
                int index2 = - 1;
                for (int k = 0; k < current.length; k++) {
                    if(current[k] == rules.get(j)[0]){
                        index1 = k;
                    }
                    if(current[k] == rules.get(j)[1]){
                        index2 = k;
                    }
                }
                if(index1 != -1 && index2 != -1){
                    if(index1 > index2){
                        isOK = false;
                    }
                }
                if(isOK){
                    counter ++;
                }
            }

            if(counter == rules.size()){
                result = result + current[current.length / 2];
            }
        }
        System.out.println(result);

    }
    public static void secondPart3(){
        //get good solutions:
        Set<Integer> goodSolutions = new HashSet<>();
        for (int i = 0; i < updates.size(); i++) {
            int[] current = updates.get(i);
            int counter = 0;

            for (int j = 0; j < rules.size(); j++) {
                boolean isOK = true;
                int index1 = - 1;
                int index2 = - 1;
                for (int k = 0; k < current.length; k++) {
                    if(current[k] == rules.get(j)[0]){
                        index1 = k;
                    }
                    if(current[k] == rules.get(j)[1]){
                        index2 = k;
                    }
                }
                if(index1 != -1 && index2 != -1){
                    if(index1 > index2){
                        isOK = false;
                    }
                }
                if(isOK){
                    counter ++;
                }
            }

            if(counter == rules.size()){
                goodSolutions.add(i);
            }
        }

        int result = 0;
        // only good solutions:
        for (int i = 0; i < updates.size(); i++) {

            //get the only applicable rules for good solutions:
            if(goodSolutions.contains(i)){
                continue;
            }

            ArrayList<int[]> onlyApplicableRules = new ArrayList<>();
            for (int j = 0; j < rules.size(); j++) {
                if(containsBoth(rules.get(j), updates.get(i))){
                    onlyApplicableRules.add(rules.get(j));
                }
            }

            ArrayList<Integer> numbers = new ArrayList<>();
            for(int j = 0; j < onlyApplicableRules.size(); j ++){
                if(!numbers.contains(onlyApplicableRules.get(j)[0])) {
                    numbers.add(onlyApplicableRules.get(j)[0]);
                }
                if(!numbers.contains(onlyApplicableRules.get(j)[1])) {
                    numbers.add(onlyApplicableRules.get(j)[1]);
                }
            }

            // make numbers based on applicable rules only
            Queue<Integer> pq = new PriorityQueue<>();
            ArrayList<int[]> copiedRules = new ArrayList<>();
            for (int j = 0; j < onlyApplicableRules.size(); j++) {
                copiedRules.add(onlyApplicableRules.get(j).clone());
            }

            int[][] helperVariable = new int[numbers.size()][2];
            for (int l = 0; l < numbers.size(); l++) {
                pq.add(numbers.get(l));
                int counter = 0;
                while(!pq.isEmpty()){
                    boolean found = false;
                    for(int j = 0; j < onlyApplicableRules.size(); j ++){
                        if(onlyApplicableRules.get(j)[1] == pq.peek()){
                            pq.add(onlyApplicableRules.get(j)[0]);
                            onlyApplicableRules.remove(j);
                            j --;
                            counter ++;
                            found = true;
                        }
                    }
                    if (!found){
                        pq.remove();
                    }
                }

                onlyApplicableRules = new ArrayList<>();
                for (int j = 0; j < copiedRules.size(); j++) {
                    onlyApplicableRules.add(copiedRules.get(j).clone());
                }
                helperVariable[l][0] = numbers.get(l);
                helperVariable[l][1] = counter;
            }

            Arrays.sort(helperVariable, (a, b) -> Integer.compare(a[1], b[1]));

            ArrayList<Integer> helperList = new ArrayList<>();
            for (int j = 0; j < helperVariable.length; j++) {
                for (int k = 0; k < updates.get(i).length; k++) {
                    if(updates.get(i)[k] == helperVariable[j][0]){
                        helperList.add(updates.get(i)[k]);
                    }
                }
            }
            result = result + helperList.get(updates.get(i).length / 2);

            //end of i
        }
        System.out.println(result);
    }
    public static boolean containsBoth(int[] rulesArray1, int[] updatesArray){
        boolean contains1 = false;
        boolean contains2 = false;

        for (int i = 0; i < updatesArray.length; i++) {
            if(updatesArray[i] == rulesArray1[0]){
                contains1 = true;
            } else if (updatesArray[i] == rulesArray1[1]){
                contains2 = true;
            }
        }
        if(contains2 && contains1){
            return true;
        } else return false;
    }
    public static void secondPart2(){
        //sort the numbers from the rules into one integer array:
        ArrayList<Integer> numbers = new ArrayList<>();
        for(int i = 0; i < rules.size(); i ++){
            if(!numbers.contains(rules.get(i)[0])) {
                numbers.add(rules.get(i)[0]);
            }
            if(!numbers.contains(rules.get(i)[1])) {
                numbers.add(rules.get(i)[1]);
            }
        }

        int[] numbersArray = new int[numbers.size()];
        Queue<Integer> pq = new PriorityQueue<>();

        ArrayList<int[]> copiedRules = new ArrayList<>();
        for (int j = 0; j < rules.size(); j++) {
            copiedRules.add(rules.get(j).clone());
        }

        int[][] helperVariable = new int[numbers.size()][2];
        for (int i = 0; i < numbers.size(); i++) {
            pq.add(numbers.get(i));
            int counter = 0;
            while(!pq.isEmpty()){
                boolean found = false;
                for(int j = 0; j < rules.size(); j ++){
                    if(rules.get(j)[1] == pq.peek()){
                        pq.add(rules.get(j)[0]);
                        rules.remove(j);
                        j --;
                        counter ++;
                        found = true;
                    }
                }
                if (!found){
                    pq.remove();
                }
            }

            rules = new ArrayList<>();
            for (int j = 0; j < copiedRules.size(); j++) {
                rules.add(copiedRules.get(j).clone());
            }
            helperVariable[i][0] = numbers.get(i);
            helperVariable[i][1] = counter;
        }

        Arrays.sort(helperVariable, (a, b) -> Integer.compare(a[1], b[1]));

        //now solve again as part 1, to get the already good solutions:
        Set<Integer> goodSolutions = new HashSet<>();

        for (int i = 0; i < updates.size(); i++) {
            int[] current = updates.get(i);
            int counter = 0;

            for (int j = 0; j < rules.size(); j++) {
                boolean isOK = true;
                int index1 = - 1;
                int index2 = - 1;
                for (int k = 0; k < current.length; k++) {
                    if(current[k] == rules.get(j)[0]){
                        index1 = k;
                    }
                    if(current[k] == rules.get(j)[1]){
                        index2 = k;
                    }
                }
                if(index1 != -1 && index2 != -1){
                    if(index1 > index2){
                        isOK = false;
                    }
                }
                if(isOK){
                    counter ++;
                }
            }

            if(counter == rules.size()){
                goodSolutions.add(i);
            }
        }
        int result = 0;

        // solve again with shuffling, ignoring the already good solutions:
        for (int i = 0; i < updates.size(); i++) {
            if(goodSolutions.contains(i)){
                continue;
            }

            ArrayList<Integer> helperList = new ArrayList<>();
            for (int j = 0; j < helperVariable.length; j++) {
                for (int k = 0; k < updates.get(i).length; k++) {
                    if(updates.get(i)[k] == helperVariable[j][0]){
                        helperList.add(updates.get(i)[k]);
                    }
                }
            }
            result = result + helperList.get(updates.get(i).length / 2);
        }
        System.out.println(result);
    }

    //this works only on test input sample where array is max 5 numbers
    //the method just shuffles the arrays until its good for all rules
    public static void secondPart(){
        Set<Integer> goodSolutions = new HashSet<>();

        //get good solutions:
        for (int i = 0; i < updates.size(); i++) {
            int[] current = updates.get(i);

            int counter = 0;

            for (int j = 0; j < rules.size(); j++) {
                boolean isOK = true;
                int index1 = - 1;
                int index2 = - 1;
                for (int k = 0; k < current.length; k++) {
                    if(current[k] == rules.get(j)[0]){
                        index1 = k;
                    }
                    if(current[k] == rules.get(j)[1]){
                        index2 = k;
                    }
                }
                if(index1 != -1 && index2 != -1){
                    if(index1 > index2){
                        isOK = false;
                    }
                }
                if(isOK){
                    counter ++;
                }
            }

            if(counter == rules.size()){
                goodSolutions.add(i);
            }
        }

        //same but shuffle bad solutions only until its good:
        int result = 0;

        for (int i = 0; i < updates.size(); i++) {
            int[] current = updates.get(i);

            if(goodSolutions.contains(i)){
                continue;
            }
            current = shuffleArray(current);

            int counter = 0;

            for (int j = 0; j < rules.size(); j++) {
                boolean isOK = true;
                int index1 = - 1;
                int index2 = - 1;
                for (int k = 0; k < current.length; k++) {
                    if(current[k] == rules.get(j)[0]){
                        index1 = k;
                    }
                    if(current[k] == rules.get(j)[1]){
                        index2 = k;
                    }
                }
                if(index1 != -1 && index2 != -1){
                    if(index1 > index2){
                        isOK = false;
                    }
                }
                if(isOK){
                    counter ++;
                }
            }

            if(counter == rules.size()){
                result = result + current[current.length / 2];
            } else {
                i--;
            }
        }
        System.out.println(result);
    }

    public static int[] shuffleArray(int[] array) {
        Random random = new Random();
        for (int i = array.length - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);

            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
        return array;
    }

    public static List<String> readFile(String file){
        Path filePath = Paths.get(file);
        try{
            List<String> fileLines = Files.readAllLines(filePath);
            return fileLines;
        }
        catch (IOException e){
            System.err.println("beep beep error");
            return new ArrayList<>();
        }
    }
}