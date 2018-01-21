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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import Bases.AdaptadorZonas;
import Bases.SQLite_OpenHelper;
import Bases.Zonas;
import Utilidades.FicheroCliente;
import Utilidades.FicheroZona;

public class ListaZonas extends AppCompatActivity {

    SQLite_OpenHelper mHelper;
    SQLiteDatabase db;
    AdaptadorZonas mAdaptadorZonas;
    List<Zonas> mZonasList = new ArrayList<>();

    TextView tv_nombre;
    ImageView imageButton3, imageButton4;
    ListView lv_zonas;
    private String[] datos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_zonas);

        datos = getResources().getStringArray(R.array.datosNuevos);


        tv_nombre = (TextView) findViewById(R.id.tv_nombre);
        imageButton3 = (ImageView) findViewById(R.id.imageButton3);
        imageButton4 = (ImageView) findViewById(R.id.imageButton4);
        lv_zonas = (ListView) findViewById(R.id.lv_zonas);

        cargarDatos();

        lv_zonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(),
                        "Seleccion: " + mZonasList.get(i).get_nombre(),
                        Toast.LENGTH_SHORT).show();
            }
        });

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
        final AlertDialog.Builder builder = new AlertDialog.Builder(ListaZonas.this);
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
        FicheroZona fichero;

        switch (view.getId())
        {
            case R.id.radioButton1:
                fichero = new FicheroZona(this, "zonas.txt", true);

                break;
            case R.id.radioButton2:
                fichero = new FicheroZona(this, "zonas.txt", false);

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
        db = mHelper.getReadableDatabase();
        mZonasList.clear();
        Cursor c = mHelper.leerRegistroCampo(db, "zonas", "", "");
        if (c.moveToFirst())
        {
            do{
                Zonas p = new Zonas();
                p.set_Id( c.getInt(0));
                p.set_nombre( c.getString(1));
                p.set_domicilio( c.getString(2));

                mZonasList.add(p);
            }while (c.moveToNext());
        }
        c.close();

        mAdaptadorZonas = new AdaptadorZonas(this, mZonasList);
        lv_zonas.setAdapter(mAdaptadorZonas);
    }

    public void registroNuevo()
    {
        SharedPreferences zonaPreferencias = PreferenceManager
                .getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = zonaPreferencias.edit();
        editor.clear();
        editor.apply();

        Intent intento = new Intent(this, PreferenciasZona.class);
        Bundle bundle = new Bundle();

        bundle.putString("00", "edit_text_id_zona");
        bundle.putString("01", "edit_text_nombre_zona");
        bundle.putString("02", "edit_text_domicilio_zona");

        intento.putExtras(bundle);
        startActivity(intento);
        finish();
    }
}
