package com.javi.pell.trabajos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Bases.AdaptadorClientes;
import Bases.Clientes;
import Bases.SQLite_OpenHelper;
import Utilidades.FicheroCliente;

public class ListaClientes extends AppCompatActivity {

    SQLite_OpenHelper mHelper;
    SQLiteDatabase db;
    AdaptadorClientes mAdaptador;
    List<Clientes> mList = new ArrayList<>();

    TextView tv_nombre;
    ImageView imageButton3, imageButton4;
    ListView mListView;
    private String[] datos;
    int eleccion =0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_clientes);

        datos = getResources().getStringArray(R.array.datosNuevos);

        tv_nombre = (TextView) findViewById(R.id.tv_nombre);
        imageButton3 = (ImageView) findViewById(R.id.imageButton3);
        imageButton4 = (ImageView) findViewById(R.id.imageButton4);
        mListView = (ListView) findViewById(R.id.lv_clientes);

        cargarDatos();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Nuevo Registro", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                registroNuevo();
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // fuente http://www.sgoliver.net/blog/menus-en-android-i-conceptos-basicos/
        // opcion1 inflar el menu desde res/menu/main.xlm
        // getMenuInflater().inflate(R.menu.main, menu);
        // opcion 2 crear por codigo el menu
        menu.add(Menu.NONE, 1, Menu.NONE, "Añadir Nuevo Registro")
                .setIcon(android.R.drawable.ic_dialog_alert);
        menu.add(Menu.NONE, 2, Menu.NONE, "Importar Datos")
                .setIcon(android.R.drawable.ic_btn_speak_now);
        menu.add(Menu.NONE, 3, Menu.NONE, "Actualizar esta Pantalla")
                .setIcon(android.R.drawable.ic_dialog_info);
        //return super.onCreateOptionsMenu(menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch ( item.getItemId() )
        {
            case 1:
                registroNuevo();
                finish();
                return true;
            case 2:
                cuadroDialogo2();
                return true;
            case 3:
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void cuadroDialogo2()
    {
        // en onCreate añadir la siguiente linea
        // datos = getResources().getStringArray(R.array.datosNuevos);
        // en values/strings.xml  ponerlos valores
        /*
        <string-array name="datosNuevos">
            <item>Borrar datos existentes, y añadir nuevos</item>
            <item>Conservar datos existentes y añadir nuevos</item>
        </string-array>
        */
        // fuente https://www.youtube.com/watch?v=sBjVnB-ql-A
        // fuente https://www.youtube.com/watch?v=CxV-7lVikfo
        final AlertDialog.Builder builder = new AlertDialog.Builder(ListaClientes.this);
        // Get the layout inflater
        LayoutInflater inflater = this.getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialogo_personalizado2, null))
                // Add action buttons
                .setPositiveButton("aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        // sign in the user ...
                        //eleccion = id;
                    }
                })
                .setNegativeButton("cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //
                        //eleccion = id;
                    }
                });
        Dialog dialog = builder.create();
        dialog.show();
    }

    public void eventoClick(View view)
    {
        FicheroCliente ficheroCliente;

        switch (view.getId())
        {
            case R.id.radioButton1:
                eleccion = 0;
                ficheroCliente = new FicheroCliente(this, "clientes.txt", true);

                break;
            case R.id.radioButton2:
                eleccion = 1;
                ficheroCliente = new FicheroCliente(this, "clientes.txt", false);

                break;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        Intent intent = getIntent();
        finish();
    }

    public void cargarDatos()
    {
        mHelper = new SQLite_OpenHelper(this, null, 1);
        db = mHelper.getWritableDatabase();
        mList.clear();

        Cursor c = mHelper.leerRegistroCampo(db, "clientes", "", "");
        if ( c.moveToFirst() )
        {
            do{
                Clientes p = new Clientes();
                p.set_Id( c.getInt(0));
                p.set_nombre( c.getString(1));
                p.set_domicilio( c.getString(2));
                p.set_poblacion( c.getString(3));
                p.set_codigoPostal( c.getString(4));
                p.set_telefono( c.getString(5));
                p.set_contacto( c.getString(6));
                p.set_familiar( c.getString(7));
                p.set_identificador( c.getString(8));

                mList.add(p);
            }while (c.moveToNext());
        }
        c.close();
        mAdaptador = new AdaptadorClientes(this, mList);
        mListView.setAdapter(mAdaptador);
    }

    public void registroNuevo()
    {
        // crear un objeto SharedPreferences
        SharedPreferences clientePreferencias = PreferenceManager
                .getDefaultSharedPreferences(this);
        // hecer editable el objeto SharedPreferemces
        SharedPreferences.Editor editor = clientePreferencias.edit();
        // eliminar preferencias
        editor.clear();
        // aplicar los cambios
        editor.apply();

        Intent intento = new Intent(this, PreferenciasClientes.class);
        Bundle bundle = new Bundle();

        bundle.putString("00", "edit_text_id");
        bundle.putString("01", "edit_text_nombre");
        bundle.putString("02", "edit_text_domicilio");
        bundle.putString("03", "edit_text_poblacion");
        bundle.putString("04", "edit_text_codigo_postal");
        bundle.putString("05", "edit_text_telefono");
        bundle.putString("06", "edit_text_contacto");
        bundle.putString("07", "edit_text_familiar");
        bundle.putString("08", "edit_text_identificador");

        intento.putExtras(bundle);
        startActivity(intento);
        finish();
    }
}
