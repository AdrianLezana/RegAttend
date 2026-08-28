package com.example.regattend;

import com.example.regattend.config.DatabaseConnection;

public class Launcher {
    public static void main(String[] args) {
        System.out.println("Creando/Conectando a la base de datos...");
        
        DatabaseConnection.getConnection();
        
        System.out.println("¡Listo! Ya puedes ver el archivo regattend.db en tu carpeta.");
    }
}
