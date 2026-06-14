/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.vehicleservice.utils;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author clemente
 */
public class HashBCrypt {
    public static String hashed(String password) {
        String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt(10));
        return hashedPassword;
    }

    public static Boolean compare(String password, String hashedPassword) {
        boolean check = BCrypt.checkpw(password, hashedPassword);
        return check;
    }
}
