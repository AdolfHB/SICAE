/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fei.vehicleservice.utils;

import java.io.InputStream;
import java.util.Properties;

/**
 *
 * @author clemente
 */
public class AppProperties {
    private static final Properties properties = new Properties();

    static {
        try {
            InputStream input = AppProperties.class
                    .getClassLoader()
                    .getResourceAsStream("application.properties");
            if (input == null) {
                System.out.println("ERROR: No se encontró application.properties");
            }
            properties.load(input);
            System.out.println("application.properties cargado correctamente");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String get(String key) {
        return properties.getProperty(key);
    }
}
