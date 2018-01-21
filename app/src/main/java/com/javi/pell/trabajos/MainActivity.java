package com.javi.pell.trabajos;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import Bases.SQLite_OpenHelper;
import Utilidades.FicheroCliente;
import Utilidades.FicheroProv;
import Utilidades.FicheroZona;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SQLite_OpenHelper mHelper;
    SQLiteDatabase db;
    String[] datos;
    int eleccion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        datos = getResources().getStringArray(R.array.datosNuevos);

        cargarDatos();

        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
                drawer.openDrawer(Gravity.START);
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_proveedor) {

            Intent intento = new Intent(MainActivity.this, ListaProveedores.class);
            startActivity(intento);

        } else if (id == R.id.nav_zona) {

            Intent intento = new Intent(MainActivity.this, ListaZonas.class);
            startActivity(intento);

        } else if (id == R.id.nav_cliente) {

            Intent intento = new Intent( MainActivity.this, ListaClientes.class);
            startActivity(intento);

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void cuadroDialogo()
    {
        // en onCreate añadir la siguiente linea
        // datos = getResources().getStringArray(R.array.datosNuevos);
        // fuente https://www.youtube.com/watch?v=sBjVnB-ql-A
        // fuente https://www.youtube.com/watch?v=CxV-7lVikfo
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this);
        dialogo.setTitle("¿Qué desea hacer con los datos existentes?")
                .setItems(R.array.datosNuevos, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        //Toast.makeText(MainActivity.this, datos[i], Toast.LENGTH_SHORT).show();
                        eleccion = i;
                    }
                });

        Dialog dialog = dialogo.create();
        dialog.show();

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
        final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
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
        switch (view.getId())
        {
            case R.id.radioButton1:
                eleccion = 0;
                break;
            case R.id.radioButton2:
                eleccion = 1;
                break;
        }
    }

    public void cargarDatos()
    {
        mHelper = new SQLite_OpenHelper(this, null, 1);
        db = mHelper.getReadableDatabase();
        mHelper.datosIniciales(db);
    }
}
