package thomas.mills.lyndon.proteintest;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/** This file is used to read in a protein based FASTA file into a series of strings ready to be processed 
 * by the evolutionary algorithm
 * @author Thomas Andrew Mills
 *
 */
public class ReadFastaToString {
	
	public Map<String, String> read(String filename) {
		//to store sequences
		Map<String, String> sequences = new HashMap<String, String>();
		InputStream input_stream;
		try {//attempts to open a buffered reader to open the file
			input_stream = new FileInputStream(filename);
			InputStreamReader stream_reader = new InputStreamReader(input_stream);
	        BufferedReader buffered_reader = new BufferedReader(stream_reader);
	        StringBuilder sb = new StringBuilder();
	        String id = "";
        	for (String s = buffered_reader.readLine(); s != null; s = buffered_reader.readLine()) {
        		   if(s.startsWith(">")) { // splits upon '>' characters (where a new protein sequence starts)
        			   String arr[] = s.split(" ",2);
        			   id = arr[0];   //the
        			   id = id.substring(1);
        			  if(sb.toString().length() !=0) {
        			       sequences.put(id,sb.toString());
        			       sb = new StringBuilder();
        			  }
        		   }
        		   else {
        			   sb.append(s);
        		   }
        	}
        	sequences.put(id,sb.toString());
        	buffered_reader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return sequences;
		
	}

}
