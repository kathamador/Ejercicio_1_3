package com.example.ejercicio_1_3;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.ejercicio_1_3.Configuracion.Operaciones;
import com.example.ejercicio_1_3.Configuracion.SQLiteConexion;
import com.example.ejercicio_1_3.transacciones.Personas;

import java.util.ArrayList;

public class ActivityListView extends AppCompatActivity {

    SQLiteConexion conexion;
    ListView lista;
    ArrayList<Personas> listaPersonas;
    ArrayList <String> ArregloPersonas;

    Button btnAtras,btnactualizarC, btnEliminar;
    Intent intent;
    Personas personas;
    int PosicionAnterior = 10;
    int count=0;
    long previousMil=0;
    final Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_view);

        lista = (ListView) findViewById(R.id.LisViewPersonas);

        btnAtras = (Button) findViewById(R.id.btnAtras);
        btnactualizarC = (Button) findViewById(R.id.btnActualizarContacto);
        btnEliminar = (Button) findViewById(R.id.btnEliminar);

        intent = new Intent(getApplicationContext(), ActivityActualizar.class);

        conexion = new SQLiteConexion(this, Operaciones.NameDatabase,null,1);

        obtenerPersonas();

        ArrayAdapter adp = new ArrayAdapter(this, android.R.layout.simple_list_item_checked,ArregloPersonas);
        lista.setAdapter(adp);

        lista.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PosicionAnterior=i;
                count=1;
                previousMil=System.currentTimeMillis();
                personas = listaPersonas.get(i);
                setPersonaSeleccionada();
            }
        });

        btnAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });

        btnactualizarC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(intent);
            }
        });

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

                alertDialogBuilder.setTitle("Eliminar Persona");

                alertDialogBuilder
                        .setMessage("¿Está seguro de eliminar a esta persona?")
                        .setCancelable(false)
                        .setPositiveButton("SI",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                eliminar();
                            }
                        })
                        .setNegativeButton("No",new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

                // crear alerta
                AlertDialog alertDialog = alertDialogBuilder.create();
                // mostrarla
                alertDialog.show();
            }
        });

    }
    private void eliminar() {
        SQLiteConexion conexion = new SQLiteConexion(this, Operaciones.NameDatabase, null, 1);
        SQLiteDatabase db = conexion.getWritableDatabase();
        int obtenerCodigo = personas.getId();

        db.delete(Operaciones.tablapersonas,Operaciones.id +" = "+ obtenerCodigo, null);

        Toast.makeText(getApplicationContext(), "Registro eliminado con exito, Codigo " + obtenerCodigo
                ,Toast.LENGTH_LONG).show();
        db.close();

        Intent intent = new Intent(this, ActivityListView.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private void setPersonaSeleccionada() {
        intent.putExtra("id", personas.getId()+"");
        intent.putExtra("nombres", personas.getNombres());
        intent.putExtra("apellidos", personas.getApellidos()+"");
        intent.putExtra("edad", personas.getEdad()+"");
        intent.putExtra("correo", personas.getCorreo());
        intent.putExtra("direccion", personas.getDireccion());
    }

    private void obtenerPersonas() {
        SQLiteDatabase db = conexion.getReadableDatabase();

        Personas list_contact = null;

        listaPersonas = new ArrayList<Personas>();

        Cursor cursor = db.rawQuery("SELECT * FROM "+ Operaciones.tablapersonas, null);

        while (cursor.moveToNext())
        {
            list_contact = new Personas();
            list_contact.setId(cursor.getInt(0));
            list_contact.setNombres(cursor.getString(1));
            list_contact.setApellidos(cursor.getString(2));
            list_contact.setEdad(cursor.getInt(3));
            list_contact.setCorreo(cursor.getString(4));
            list_contact.setDireccion(cursor.getString(5));
            listaPersonas.add(list_contact);
        }
        cursor.close();

        llenarlista();
    }
    private void llenarlista()
    {
        ArregloPersonas = new ArrayList<String>();

        for (int i=0; i<listaPersonas.size();i++)
        {
            ArregloPersonas.add(listaPersonas.get(i).getNombres()+" "+
                    listaPersonas.get(i).getApellidos()+" | "+
                    listaPersonas.get(i).getEdad()+" años");
        }
    }

}