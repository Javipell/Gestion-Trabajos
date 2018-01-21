package com.javi.pell.trabajos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.preference.SwitchPreference;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import Bases.Proveedores;
import Bases.SQLite_OpenHelper;

public class PreferenciasProv extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    SQLite_OpenHelper mHelper;
    SQLiteDatabase db;
    List<Proveedores> mProveedoresList = new ArrayList<>();

    Preference mDialogoNormal;
    SharedPreferences.Editor mEditorDialogoNormal;
    String mValorS;
    String fechaAlta;
    String fechaBaja;
    boolean nuevoRegistro = false;
    String nId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_mis_preferencias);
        addPreferencesFromResource(R.xml.datos_proveedor);
        Intent intento = getIntent();
        Bundle bundle = intento.getExtras();
        if (bundle!=null)
        {
            cambiarSummary(bundle.getString("00"));
            cambiarSummary(bundle.getString("01"));
            cambiarSummary(bundle.getString("02"));
            cambiarSummary(bundle.getString("03"));
            cambiarSummary(bundle.getString("04"));
            cambiarSummary(bundle.getString("05"));
            cambiarSummary(bundle.getString("06"));
            cambiarSummary(bundle.getString("07"));
            cambiarSummary(bundle.getString("08"));
            cambiarSummary(bundle.getString("09"));

            cambiarSummary(bundle.getString("11"));
            cambiarSummary(bundle.getString("12"));
            //cambiarSummary(bundle.getString("10"));
            System.out.println("msg valor "+ bundle.getString("10"));

        }

        final SwitchPreference estados = (SwitchPreference) findPreference("switch_estado");
        estados.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
            @Override
            public boolean onPreferenceChange(Preference preference, Object newValue) {
                if(!((Boolean) newValue)) {
                    System.out.println("msg NO ACTIVADO.");
                    estadosProveedor("false");
                } else {
                    System.out.println("msg ACTIVADO.");
                    estadosProveedor("true");
                }
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    public void estadosProveedor(String nuevEstado)
    {
        mDialogoNormal = findPreference("switch_estado");

        nId= mDialogoNormal.getSharedPreferences().getString("edit_text_id", "");
        cargarDatos();

        if (nuevEstado.contains("false")) {
            mProveedoresList.get(0).set_estado("Baja");
        }else{
            mProveedoresList.get(0).set_estado("Alta");
        }

        Calendar fechaActual = Calendar.getInstance();

        if (nuevEstado.contains("true"))
        {
            fechaAlta = ""+fechaActual.get(Calendar.DAY_OF_MONTH)+ "/" +
                    fechaActual.get(Calendar.MONTH)+1 + "/" +
                    fechaActual.get(Calendar.YEAR);
            mDialogoNormal = findPreference("edit_text_alta");
            onPreferenceChange(mDialogoNormal, fechaAlta);
            mProveedoresList.get(0).set_alta(
                    mDialogoNormal.getSharedPreferences().getString("edit_text_alta","") );
            fechaBaja ="";
            mDialogoNormal = findPreference("edit_text_baja");
            onPreferenceChange(mDialogoNormal, "");
            mProveedoresList.get(0).set_baja(
                    mDialogoNormal.getSharedPreferences().getString("edit_text_baja","") );
        }else{
            fechaBaja = ""+fechaActual.get(Calendar.DAY_OF_MONTH)+ "/" +
                    fechaActual.get(Calendar.MONTH)+1 + "/" +
                    fechaActual.get(Calendar.YEAR);
            mDialogoNormal = findPreference("edit_text_baja");
            onPreferenceChange(mDialogoNormal, fechaBaja);
            mProveedoresList.get(0).set_baja(
                    mDialogoNormal.getSharedPreferences().getString("edit_text_baja","") );
        }

        guardarCambios();
    }

    public void editarPreferencias() {
        // fuente : https://www.youtube.com/watch?v=AEBnm-bNqtE
        // crear un objeto SharedPreferences
        SharedPreferences proveedorPreferencias = PreferenceManager
                .getDefaultSharedPreferences(this);
        // hacer editable el objeto SharedPreferences
        SharedPreferences.Editor editor = proveedorPreferencias.edit();
        // cambia la informacion a SharedPreferences
        editor.putString("edit_text_nombre", mProveedoresList.get(0).get_nombre().toString());
        editor.putString("edit_text_logo", mProveedoresList.get(0).get_logo().toString());
        editor.putString("edit_text_domicilio", mProveedoresList.get(0).get_domicilio().toString());
        editor.putString("edit_text_poblacion", mProveedoresList.get(0).get_poblacion().toString());
        editor.putString("edit_text_codigo_postal", mProveedoresList.get(0).get_codigoPostal().toString());
        editor.putString("edit_text_telefono", mProveedoresList.get(0).get_telefono().toString());
        editor.putString("edit_text_fax", mProveedoresList.get(0).get_fax().toString());
        editor.putString("edit_text_correo", mProveedoresList.get(0).get_correo().toString());
        editor.putString("edit_text_contacto", mProveedoresList.get(0).get_contacto().toString());
        //editor.putString("switch_estado", mProveedoresList.get(0).get_estado().toString() );
        editor.putString("switch_estado", String.valueOf(false));
        editor.putString("edit_text_alta", mProveedoresList.get(0).get_alta().toString());
        editor.putString("edit_text_baja", mProveedoresList.get(0).get_baja().toString());

        // aplica los cambios
        editor.apply();
    }

    public void cambiarSummary(String campo)
    {
        mDialogoNormal = findPreference(campo);
        mDialogoNormal.setOnPreferenceChangeListener(this);
        mEditorDialogoNormal = mDialogoNormal.getEditor();
        if (campo !="switch_estado")
        {
            mValorS = mDialogoNormal.getSharedPreferences().getString(campo, "");
            fechaAlta = (campo == "edit_text_alta") ? mValorS : "";
            fechaBaja = (campo == "edit_text_baja") ? mValorS : "";
            mEditorDialogoNormal.putString(campo, mValorS);
            mEditorDialogoNormal.commit();
            mDialogoNormal.setSummary(mValorS);
            mEditorDialogoNormal.apply();
        }else{
            //mValorB = mPreference.getSharedPreferences().getBoolean(campo, false);
            System.out.println("msg valorB "+ campo);
        }
    }

    public void cambiarPreferencias(Preference preference, String newValue)
    {
        mValorS = newValue.toString();
        mDialogoNormal = findPreference(preference.getKey());
        mEditorDialogoNormal = mDialogoNormal.getEditor();
        mEditorDialogoNormal.putString(preference.getKey(), mValorS);
        mEditorDialogoNormal.commit();
        mDialogoNormal.setSummary(mValorS);
        mEditorDialogoNormal.apply();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue) {

        String [] camposFecha ;
        int dayOfMonth = 0;
        int month = 0;
        int year = 0;

        if (!preference.getKey().equals("switch_estado"))
        {
            cambiarPreferencias(preference, newValue.toString());

            mDialogoNormal = findPreference(preference.getKey());
            nId= mDialogoNormal.getSharedPreferences().getString("edit_text_id", "");

            cargarDatos();

            if (mProveedoresList.size()==0)
            {
                registroVacio();
                nuevoRegistro = true;
            }

            switch (preference.getKey())
            {
                case "edit_text_nombre":
                    mProveedoresList.get(0).set_nombre(
                            mDialogoNormal.getSharedPreferences().getString(preference.getKey(),"")
                    );
                    break;
                case "edit_text_logo":
                    mProveedoresList.get(0).set_logo(
                            mDialogoNormal.getSharedPreferences().getString(preference.getKey(),"")
                    );
                    break;
                case "edit_text_domicilio":
                    mProveedoresList.get(0).set_domicilio(
                            mDialogoNormal.getSharedPreferences().getString(preference.getKey(),"")
                    );
                    break;
                case "edit_text_poblacion":
                    mProveedoresList.get(0).set_poblacion(
                            mDialogoNormal.getSharedPreferences().getString(preference.getKey(),"")
                    );
                    break;
                case "edit_text_codigo_postal":
                    mProveedoresList.get(0).set_codigoPostal(
                            mDialogoNormal.getSharedPreferences().getString(preference.getKey(),"")
                    );
                    break;
                case "edit_text_telefono":
                    mProveedoresList.get(0).set_telefono(
                            mDialogoNormal.getSharedPreferences().getString(preference.getKey(),"")
                    );
                    break;
                case "edit_text_fax":
                    mProveedoresList.get(0).set_fax(
                            mDialogoNormal.getSharedPreferences().getString(preference.getKey(),"")
                    );
                    break;
                case "edit_text_correo":
                    mProveedoresList.get(0).set_correo(
                            mDialogoNormal.getSharedPreferences().getString(preference.getKey(),"")
                    );
                    break;
                case "edit_text_contacto":
                    mProveedoresList.get(0).set_contacto(
                            mDialogoNormal.getSharedPreferences().getString(preference.getKey(),"")
                    );
                    break;
                case "switch_estado":

                    break;
                case "edit_text_alta":
                    camposFecha = mDialogoNormal.getSharedPreferences().getString(preference.getKey(),"").split("/");

                    if (camposFecha.length == 3)
                    {
                        dayOfMonth = Integer.parseInt( camposFecha[0] );
                        month = Integer.parseInt( camposFecha[1] );
                        year = Integer.parseInt( camposFecha[2] );
                        Calendar calendar = Calendar.getInstance();
                        calendar.setLenient(false);

                        try {
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month - 1); // [0,...,11]
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            Date date = calendar.getTime();

                            mProveedoresList.get(0).set_alta(
                                    mDialogoNormal.getSharedPreferences().getString(preference.getKey(),"") );
                            fechaAlta = String.valueOf(date);
                            Toast.makeText(getApplicationContext(),"FECHA CORRECTA "+ fechaAlta, Toast.LENGTH_SHORT).show();
                            System.out.println("msg fecha alta correcta " + fechaAlta);
                        }catch (Exception ex)
                        {
                            Toast.makeText(getApplicationContext(),"FECHA INCORRECTA !!! ", Toast.LENGTH_SHORT).show();
                            cambiarPreferencias(preference, fechaAlta);
                        }
                    }

                    break;
                case "edit_text_baja":
                    camposFecha = mDialogoNormal.getSharedPreferences().getString(preference.getKey(),"").split("/");

                    if (camposFecha.length == 3)
                    {
                        dayOfMonth = Integer.parseInt( camposFecha[0] );
                        month = Integer.parseInt( camposFecha[1] );
                        year = Integer.parseInt( camposFecha[2] );
                        Calendar calendar = Calendar.getInstance();
                        calendar.setLenient(false);

                        try {
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, month - 1); // [0,...,11]
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                            Date date = calendar.getTime();

                            mProveedoresList.get(0).set_baja(
                                    mDialogoNormal.getSharedPreferences().getString(preference.getKey(),"") );
                            fechaBaja = String.valueOf(date);
                            Toast.makeText(getApplicationContext(),"FECHA CORRECTA "+ date, Toast.LENGTH_SHORT).show();
                            System.out.println("msg fecha baja correcta " + date);
                        }catch (Exception ex)
                        {
                            cambiarPreferencias(preference, fechaAlta);
                            Toast.makeText(getApplicationContext(),"FECHA INCORRECTA !!! ", Toast.LENGTH_SHORT).show();
                        }
                    }
                    break;
            }
            guardarCambios();
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

        //mEditor.putString("edit_text_domicilio", mValorS);
        //mEditor.commit();
    }

    public void guardarCambios()
    {
        mHelper = new SQLite_OpenHelper(this, null, 1);
        db = mHelper.getWritableDatabase();

        Proveedores p = new Proveedores();

        p.set_nombre(mProveedoresList.get(0).get_nombre()); //1
        p.set_domicilio(mProveedoresList.get(0).get_domicilio()); //2
        p.set_poblacion(mProveedoresList.get(0).get_poblacion()); //3
        p.set_codigoPostal(mProveedoresList.get(0).get_codigoPostal()); //4
        p.set_telefono(mProveedoresList.get(0).get_telefono()); //5
        p.set_fax(mProveedoresList.get(0).get_fax()); //6
        p.set_correo(mProveedoresList.get(0).get_correo()); //7
        p.set_contacto(mProveedoresList.get(0).get_contacto()); //8
        p.set_logo(mProveedoresList.get(0).get_logo()); //9
        //p.set_estado( mProveedoresList.get(0).get_estado());
        String estado = (!fechaBaja.isEmpty()) ? "Baja" : "Alta";
        estado = (fechaAlta.isEmpty()) ? "Baja" : "Alta";
        p.set_estado(estado); //10
        p.set_alta(mProveedoresList.get(0).get_alta()); //11
        p.set_baja(mProveedoresList.get(0).get_baja()); //12

        if (!nuevoRegistro) {
            p.set_Id(Integer.parseInt(nId));
            mHelper.actualizarProveedor(db, p);
        }else{
            mHelper.duplicado(db,p);
            nuevoRegistro = false;

            Cursor c = mHelper.leerRegistroCampo(db, "proveedores", "_nombre",mProveedoresList.get(0).get_nombre());
            if ( c.moveToFirst() )
            {
                p = new Proveedores();
                p.set_nombre( c.getString(1));
                p.set_domicilio( c.getString(2));
                p.set_poblacion( c.getString(3));
                p.set_codigoPostal( c.getString(4));
                p.set_telefono( c.getString(5));
                p.set_fax( c.getString(6));
                p.set_correo( c.getString(7));
                p.set_contacto( c.getString(8));
                p.set_logo( c.getString(9));
                p.set_estado( c.getString(10));
                p.set_alta( c.getString(11));
                p.set_baja( c.getString(12));
                mProveedoresList.clear();
                mProveedoresList.add(p);
            }
            c.close();

            mDialogoNormal = findPreference("edit_text_id");
            String valor = String.valueOf(mProveedoresList.get(0).get_Id());
            cambiarPreferencias(mDialogoNormal, valor);

        }
    }

    public void registroVacio()
    {
        Proveedores p = new Proveedores();
        mProveedoresList.add(p);
    }
    public void cargarDatos()
    {
        mHelper = new SQLite_OpenHelper(this, null, 1);
        db = mHelper.getReadableDatabase();
        //String sql = "SELECT * FROM proveedores WHERE _nombre='" + nombre + "'";
        //Cursor c = db.rawQuery(sql, null);
        Cursor c = mHelper.leerRegistroCampo(db, "proveedores", "_Id", nId);

        if ( c.moveToFirst() )
        {
            Proveedores p = new Proveedores();
            p.set_nombre( c.getString(1));
            p.set_domicilio( c.getString(2));
            p.set_poblacion( c.getString(3));
            p.set_codigoPostal( c.getString(4));
            p.set_telefono( c.getString(5));
            p.set_fax( c.getString(6));
            p.set_correo( c.getString(7));
            p.set_contacto( c.getString(8));
            p.set_logo( c.getString(9));
            p.set_estado( c.getString(10));
            p.set_alta( c.getString(11));
            p.set_baja( c.getString(12));

            mProveedoresList.add(p);
        }
        c.close();
    }

}
