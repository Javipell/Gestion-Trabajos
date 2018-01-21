package com.javi.pell.trabajos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;

import java.util.ArrayList;
import java.util.List;

import Bases.Clientes;
import Bases.SQLite_OpenHelper;

/**
 * Created by javier on 20/1/18.
 */

public class PreferenciasClientes extends PreferenceActivity
        implements Preference.OnPreferenceChangeListener
{

    SQLite_OpenHelper mHelper;
    SQLiteDatabase db;
    List<Clientes> mList = new ArrayList<>();
    Preference mPreference;
    SharedPreferences.Editor mEditor;
    String mValorS;
    boolean nuevoRegistro = false;
    String nId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.datos_cliente);
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
        }
    }

    public void cambiarSummary(String campo)
    {
        mPreference = findPreference(campo);
        mPreference.setOnPreferenceChangeListener(this);
        mEditor = mPreference.getEditor();
        mValorS = mPreference.getSharedPreferences().getString(campo, " ");
        mEditor.putString(campo, mValorS);
        mEditor.commit();
        mPreference.setSummary(mValorS);
        mEditor.apply();
    }

    public void cambiarPreferencias(Preference preference, String newValue)
    {
        mValorS = newValue;
        mPreference = findPreference(preference.getKey());
        mEditor = mPreference.getEditor();
        mEditor.putString(preference.getKey(), mValorS);
        mEditor.commit();
        mPreference.setSummary(mValorS);
        mEditor.apply();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue)
    {
        cambiarPreferencias(preference, newValue.toString());
        mPreference = findPreference(preference.getKey());
        nId = mPreference.getSharedPreferences().getString("edit_text_id", " ");

        cargarDatos();

        if (mList.size()==0)
        {
            registroVacio();
            nuevoRegistro = true;
        }

        switch (preference.getKey())
        {
            case "edit_text_nombre":
                mList.get(0).set_nombre(
                        mPreference.getSharedPreferences().getString(preference.getKey()," ")
                );
                break;
            case "edit_text_familiar":
                mList.get(0).set_familiar(
                        mPreference.getSharedPreferences().getString(preference.getKey(),"")
                );
                break;
            case "edit_text_domicilio":
                mList.get(0).set_domicilio(
                        mPreference.getSharedPreferences().getString(preference.getKey(),"")
                );
                break;
            case "edit_text_poblacion":
                mList.get(0).set_poblacion(
                        mPreference.getSharedPreferences().getString(preference.getKey(),"")
                );
                break;
            case "edit_text_codigo_postal":
                mList.get(0).set_codigoPostal(
                        mPreference.getSharedPreferences().getString(preference.getKey(),"")
                );
                break;
            case "edit_text_telefono":
                mList.get(0).set_telefono(
                        mPreference.getSharedPreferences().getString(preference.getKey(),"")
                );
                break;
            case "edit_text_contacto":
                mList.get(0).set_contacto(
                        mPreference.getSharedPreferences().getString(preference.getKey(),"")
                );
                break;
            case "edit_text_identificador":
                mList.get(0).set_identificador(
                        mPreference.getSharedPreferences().getString(preference.getKey(), " ")
                );
                break;
        }

        guardarCambios();

        return true;
    }

    public void guardarCambios()
    {
        mHelper = new SQLite_OpenHelper(this, null, 1);
        db = mHelper.getWritableDatabase();
        Clientes p = new Clientes();

        p.set_nombre(mList.get(0).get_nombre());
        p.set_domicilio(mList.get(0).get_domicilio());
        p.set_codigoPostal(mList.get(0).get_codigoPostal());
        p.set_poblacion(mList.get(0).get_poblacion());
        p.set_telefono(mList.get(0).get_telefono());
        p.set_contacto(mList.get(0).get_contacto());
        p.set_familiar(mList.get(0).get_familiar());
        p.set_identificador(mList.get(0).get_identificador());

        if (!nuevoRegistro)
        {
            p.set_Id(Integer.parseInt(nId));
            mHelper.actualizarCliente(db, p);
        }else {
            mHelper.duplicadoClientes(db, p);
            nuevoRegistro = false;

            Cursor c = mHelper.leerRegistroCampo(db, "clientes", "_nombre", mList.get(0).get_nombre());
            if ( c.moveToFirst() )
            {
                p = new Clientes();
                p.set_nombre( c.getString(1));
                p.set_domicilio( c.getString(2));
                p.set_poblacion( c.getString(3));
                p.set_codigoPostal( c.getString(4));
                p.set_telefono( c.getString(5));
                p.set_contacto( c.getString(6));
                p.set_familiar( c.getString( 7));
                p.set_identificador( c.getString(8));

                mList.clear();
                mList.add(p);
            }
            c.close();

            mPreference = findPreference("edit_text_id");
            String valor = String.valueOf(mList.get(0).get_Id());
            cambiarPreferencias(mPreference, valor);
        }
    }

    public void registroVacio()
    {
        Clientes p = new Clientes();
        mList.add(p);
    }

    public void cargarDatos()
    {
        mHelper = new SQLite_OpenHelper(this, null, 1);
        db = mHelper.getWritableDatabase();
        Cursor c = mHelper.leerRegistroCampo(db, "clientes", "_Id", nId);

        if ( c.moveToFirst() )
        {
            Clientes p = new Clientes();
            p.set_nombre( c.getString(1));
            p.set_domicilio( c.getString(2));
            p.set_poblacion( c.getString(3));
            p.set_codigoPostal( c.getString(4));
            p.set_telefono( c.getString(5));
            p.set_contacto( c.getString(6));
            p.set_familiar( c.getString( 7));
            p.set_identificador( c.getString(8));

            mList.clear();
            mList.add(p);
        }
        c.close();
    }
}
