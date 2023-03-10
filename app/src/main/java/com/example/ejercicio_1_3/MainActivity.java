package com.example.ejercicio_1_3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ejercicio_1_3.Configuracion.Operaciones;
import com.example.ejercicio_1_3.Configuracion.SQLiteConexion;

public class MainActivity extends AppCompatActivity {
    EditText nombres, apellidos, correo,edad,direccion;
    Button btnagregar,btnlista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        nombres = (EditText) findViewById(R.id.txtnombre);
        apellidos = (EditText) findViewById(R.id.txtapellido);
        correo = (EditText) findViewById(R.id.txtcorreo);
        edad = (EditText) findViewById(R.id.txtedad);
        direccion = (EditText) findViewById(R.id.txtdireccion);

        btnagregar = (Button) findViewById(R.id.btnsalvar);
        btnlista = (Button) findViewById(R.id.btnlista);

        btnagregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombres.getText().toString().isEmpty() || apellidos.getText().toString().isEmpty() || correo.getText().toString().isEmpty() || edad.getText().toString().isEmpty() || direccion.getText().toString().isEmpty()){
                    validaciones();
                }
                else{
                    AgregarPersona();
                }
            }
        });
        btnlista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityListView.class);
                startActivity(intent);
            }
        });
    }
    private void AgregarPersona()
    {
        try {
            SQLiteConexion conexion = new SQLiteConexion(this, Operaciones.NameDatabase,null,1);
            SQLiteDatabase db = conexion.getWritableDatabase();

            ContentValues valores = new ContentValues();
            valores.put("nombres",nombres.getText().toString());
            valores.put("apellidos",apellidos.getText().toString());
            valores.put("edad",edad.getText().toString());
            valores.put("correo",correo.getText().toString());
            valores.put("direccion",direccion.getText().toString());

            Long Resultado = db.insert(Operaciones.tablapersonas,"id",valores);
            Toast.makeText(this,"Dato ingresado correctamente, ID: "+Resultado.toString(), Toast.LENGTH_SHORT).show();

            ClearScreen();
        }
        catch (Exception ex)
        {
            Toast.makeText(this,"No se pudo insertar el dato", Toast.LENGTH_LONG).show();
        }

    }
    private void ClearScreen()
    {
        nombres.setText(Operaciones.Empty);
        apellidos.setText(Operaciones.Empty);
        correo.setText(Operaciones.Empty);
        edad.setText(Operaciones.Empty);
        direccion.setText(Operaciones.Empty);
    }

    private void validaciones() {
        if(nombres.getText().toString().equals("") && apellidos.getText().toString().equals("") && correo.getText().toString().equals("") && edad.getText().toString().equals("") && direccion.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Alerta! Todos los campos estan vacios, llenelos." ,Toast.LENGTH_LONG).show();
        } else if (nombres.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), " Alerta! Debe de escribir un nombre." ,Toast.LENGTH_LONG).show();
        }else if (apellidos.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), " Alerta! Debe de escribir un apellido." ,Toast.LENGTH_LONG).show();
        }else if (correo.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Alerta! Debe de escribir un correo." ,Toast.LENGTH_LONG).show();
        } else if (edad.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Alerta! Debe de escribir una edad." ,Toast.LENGTH_LONG).show();
        } else if (direccion.getText().toString().equals("")){
            Toast.makeText(getApplicationContext(), "Alerta! Debe de escribir una direccion." ,Toast.LENGTH_LONG).show();
        }
    }
}