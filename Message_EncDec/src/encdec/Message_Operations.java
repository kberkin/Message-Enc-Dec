package encdec;

import java.io.*;
import java.util.Random;
import java.util.Scanner;

public class Message_Operations {

	public static void main(String[] args) {
		Scanner inp = new Scanner(System.in);
		
		int random;
		String message;
		
		System.out.println("Enter a message : ");
		message = inp.nextLine();
		
		try {
			FileWriter fwrite = new FileWriter("text.txt");
			fwrite.write(message);
			fwrite.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Random rnd = new Random();
		random = rnd.nextInt(7);
		
		System.out.println("To encrypt, random bit number chosen as : "+random);
		
		String[] m = new String[message.length()];
		String[] enc = new String[message.length()];
		
		try {
			FileReader fr = new FileReader("text.txt");
			
			int msg=0,count=0;
			
			char[] mm;
			
			while((msg=fr.read()) != -1) {
				m[count] = "0"+Integer.toBinaryString(msg);
				
				mm = m[count].toCharArray();
				
				if(mm[7-random] == '0') {
					mm[7-random] = '1';
				}else if(mm[7-random] == '1') {
					mm[7-random] = '0';
				}
				
				enc[count] = String.valueOf(mm);
				
				count = count + 1;
			}
			
			
			fr.close();
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		try {
			FileWriter fw = new FileWriter("enctext.txt");
			FileReader fr2 = new FileReader("enctext.txt");
			BufferedReader br = new BufferedReader(fr2);
			
			for(int i=0;i<message.length();i++) {
				fw.write(m[i]);
				fw.write(" ");
				fw.write(enc[i]);
				fw.write(" ");
			}
			
			fw.close();
			
			String s;
			int index = 0;
			
			String[] getEnc = null;
			
			while( (s = br.readLine()) != null) {
				System.out.println("Encrypted Message : "+s);
				getEnc = s.split(" ", 0);
			}
			
			for(int i=0;i<getEnc.length;i=i+2) {
				
				for(int j=1;j<getEnc.length;j=j+2) {
					
					for(int ind=0;ind<=7;ind++) {
						
						if(getEnc[i].charAt(ind) != getEnc[j].charAt(ind)) {
							index = 7-ind;
						}
					}
					
				}
			}
			
			System.out.println("Encrypted bit: "+index);
			
			br.close();
			
			String[] getDec = new String[message.length()];
			char[] temp;
			
			for(int k=0;k<enc.length;k++) {
				
				temp = enc[k].toCharArray();
				
				if(temp[7-index] == '1') {
					temp[7-index] = '0';
				}else if(temp[7-index] == '0') {
					temp[7-index] = '1';
				}
				
				getDec[k] = String.valueOf(temp);
			}
			
			double sum=0;
			int ssum;
			String decrypt = "";
			
			String[] alphabet = new String[] {"a","b","c","d","e","f","g","h","i","j","k","l","m",
					  "n","o","p","q","r","s","t","u","v","w","x","y","z"};

			String[] ascii = new String[] {"97", "98", "99", "100", "101", "102", "103", "104", "105",
				 "106", "107", "108", "109", "110", "111", "112", "113", "114",
				 "115", "116", "117", "118", "119", "120", "121", "122"};
			
			for(int z=0;z<getDec.length;z++) {
				
				for(int b=7;b>=0;b--) {
					if(getDec[z].charAt(b) == '1') {
						sum = sum + Math.pow(2, (7-b) );
					}
				}
				ssum = (int) sum;
				
				for(int asci=0;asci<ascii.length;asci++) {
					if(ascii[asci].equals(String.valueOf(ssum))) {
						decrypt += alphabet[asci];
					}
				}
				
				sum = 0;
			}
			
			System.out.println("Decrypted Message : "+decrypt);
			
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		
		
	}
}