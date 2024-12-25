import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static ArrayList<Lock> locks = new ArrayList<>();
    static ArrayList<Key> keys = new ArrayList<>();

    public static void main(String[] args) {
        List<String> input = readFile("src/input1.txt");
        processFile(input);
        part1();
    }
    public static void part1(){
        int result = 0;
        for (int i = 0; i < locks.size(); i++) {
            for (int j = 0; j < keys.size(); j++) {
                if(doesKeyFitIntoLock(locks.get(i), keys.get(j))){
                    result ++;
                }
            }
        }
        System.out.println(result);
    }

    public static boolean doesKeyFitIntoLock(Lock lock, Key key){
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 5; j++) {
                if(lock.field[i][j] == 1 && key.field[i][j] == 1){
                    return false;
                }
            }
        }
        return true;
    }

    public static void processFile(List<String> input){
        int counter = 0;
        while(counter < input.size()){
            if(input.get(counter).equals("#####")){
                Lock lock = new Lock();
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 5; j++) {
                        if(input.get(counter + i).charAt(j) == '#'){
                            lock.field[i][j] = 1;
                        } else {
                            lock.field[i][j] = 0;
                        }
                    }
                }
                locks.add(lock);
            } else {
                Key key = new Key();
                for (int i = 0; i < 7; i++) {
                    for (int j = 0; j < 5; j++) {
                        if(input.get(counter + i).charAt(j) == '#'){
                            key.field[i][j] = 1;
                        } else {
                            key.field[i][j] = 0;
                        }
                    }
                }
                keys.add(key);
            }
            counter = counter + 8;
        }
    }

    public static List<String> readFile(String file) {
        Path filePath = Paths.get(file);
        try {
            return Files.readAllLines(filePath);
        } catch (IOException e) {
            System.err.println("Error reading file");
            return new ArrayList<>();
        }
    }
}