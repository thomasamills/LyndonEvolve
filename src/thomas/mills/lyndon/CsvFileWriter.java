package thomas.mills.lyndon;
 
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import thomas.mills.lyndon.datastructures.OrderingFitnessValue;

public class CsvFileWriter {
	
    private static final String DELIM = ",";
    private static final String LINE = "\n";
    private static final String RESULT_FILE_PATH = System.getProperty("user.dir") + "/results/";
    
 
    public static void writeCsvFile(String file_name, String string_id, ArrayList<ArrayList<OrderingFitnessValue>> data) {
        FileWriter file_writer = null;
        String filepath = RESULT_FILE_PATH  + file_name + ".txt";
        
        File file = new File(filepath);
        try {
        	file.createNewFile();

        	file_writer = new FileWriter(file.getAbsolutePath());
        
            //Write a generation to the CSV file
            for (ArrayList<OrderingFitnessValue> generation : data) {
            	
            	for(OrderingFitnessValue ofv: generation) {
            		file_writer.append(String.valueOf(ofv.getValue()));
 
            		file_writer.append(DELIM);
            	}
            	file_writer.append(LINE);
            }            
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                file_writer.flush();
                file_writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }          
        }
    }
}
