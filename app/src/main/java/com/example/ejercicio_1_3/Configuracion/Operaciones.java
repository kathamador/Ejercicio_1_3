package com.example.ejercicio_1_3.Configuracion;
//REALIZADO POR:
//Mirian Fatima Ordoñez Amador
//Katherin Nicole Amador Maradiaga
public class Operaciones {
    public static final String NameDatabase = "PM01DB";
    public static final String tablapersonas = "personas";
    public static final String id = "id";
    public static final String nombres = "nombres";
    public static final String apellidos = "apellidos";
    public static final String edad = "edad";
    public static final String correo = "correo";
    public static final String direccion = "direccion";

    public static final String createTablepersonas = "CREATE TABLE " + tablapersonas +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT," +
            "nombres TEXT,apellidos TEXT,edad INTEGER, correo TEXT, direccion TEXT)";

    public static final String dropTablepersonas = "DROP TABLE IF EXIST" + tablapersonas;

    public static final String Empty= "";
}