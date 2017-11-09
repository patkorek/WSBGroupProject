package com.WSBGroupProject.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import com.WSBGroupProject.model.Account;

/**
 *
 * @author Piotr Czapiewski
 */
@Service
public class StringService {
    
    private static final String LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String UPPER = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    private static final String DIGITS = "0123456789";
    private static final String PUNCTUATION = "!@#$%&*()_+-=[]|,./?><";

    public String getNewPassword() {
        int length = 8;
        StringBuilder password = new StringBuilder(length);
        Random random = new Random(System.nanoTime());
        
        List<String> charCategories = new ArrayList<>(4);
        charCategories.add(LOWER);
        charCategories.add(UPPER);
        charCategories.add(DIGITS);
        charCategories.add(PUNCTUATION);
            
        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            password.append(charCategory.charAt(position));
        }
        return new String(password);
    }

    public String getLinkHash() {
        int length = 16;
        StringBuilder hash = new StringBuilder(length);
        Random random = new Random(System.nanoTime());
        
        List<String> charCategories = new ArrayList<>(4);
        charCategories.add(LOWER);
        charCategories.add(DIGITS);
            
        for (int i = 0; i < length; i++) {
            String charCategory = charCategories.get(random.nextInt(charCategories.size()));
            int position = random.nextInt(charCategory.length());
            hash.append(charCategory.charAt(position));
        }
        return new String(hash);
    }
	
	public boolean isNIPValid(String NIPNumber) {		
		byte NIP[] = new byte[10];
		
		for (int i = 0; i < 10; i++){
			NIP[i] = Byte.parseByte(NIPNumber.substring(i, i+1));
		}
		int sum = 6 * NIP[0] + 5 * NIP[1] + 7 * NIP[2] + 2 * NIP[3] + 3 * NIP[4] +
				4 * NIP[5] + 5 * NIP[6] + 6 * NIP[7] + 7 * NIP[8];
		sum %= 11;
		 
		return NIP[9] == sum;
	}
    
    public String hashPassword(String plainTextPassword){
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }
    
    public boolean checkPass(String plainPassword, String hashedPassword) {
    	return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
