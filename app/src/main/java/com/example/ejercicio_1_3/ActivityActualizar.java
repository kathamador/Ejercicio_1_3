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

public class ActivityActualizar extends AppCompatActivity {
    SQLiteConexion conexion = new SQLiteConexion(this, Operaciones.NameDatabase,null,1);

    Button btnatras,btnEditar;
    EditText codigo, nombrecompleto, apellido, edad,correo,direccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actualizar);
        btnatras = (Button) findViewById(R.id.btnatras);
        btnEditar = (Button) findViewById(R.id.btnEditar);

        codigo = (EditText)findViewById(R.id.txtActCodigo);
        nombrecompleto = (EditText) findViewById(R.id.txtActNombre);
        apellido = (EditText) findViewById(R.id.txtActApellido);
        edad = (EditText) findViewById(R.id.txtActEdad);
        correo = (EditText) findViewById(R.id.txtActCorreo);
        direccion = (EditText) findViewById(R.id.txtActdireccion);


        codigo.setText(getIntent().getStringExtra("id"));
        nombrecompleto.setText(getIntent().getStringExtra("nombres"));
        apellido.setText(getIntent().getStringExtra("apellidos"));
        edad.setText(getIntent().getStringExtra("edad"));
        correo.setText(getIntent().getStringExtra("correo"));
        direccion.setText(getIntent().getStringExtra("direccion"));

        btnatras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),ActivityListView.class);
                startActivity(intent);
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditarContacto();
            }
        });
    }
    private void EditarContacto() {

        SQLiteDatabase db = conexion.getWritableDatabase();

        String ObjCodigo = codigo.getText().toString();

        ContentValues valores = new ContentValues();

        valores.put(Operaciones.nombres, nombrecompleto.getText().toString());
        valores.put(Operaciones.apellidos, apellido.getText().toString());
        valores.put(Operaciones.edad, edad.getText().toString());
        valores.put(Operaciones.correo, correo.getText().toString());
        valores.put(Operaciones.direccion, direccion.getText().toString());

        try {
            db.update(Operaciones.tablapersonas,valores, Operaciones.id +" = "+ ObjCodigo, null);
            db.close();
            Toast.makeText(getApplicationContext(),"Los datos se han actualizado correctamente", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(this, ActivityListView.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            finish();


        }catch (Exception e)
        {
            Toast.makeText(getApplicationContext(),"No se actualizaron los datos", Toast.LENGTH_SHORT).show();
        }
    }
}