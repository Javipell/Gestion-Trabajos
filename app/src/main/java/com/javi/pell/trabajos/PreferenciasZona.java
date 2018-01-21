package com.javi.pell.trabajos;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.PreferenceActivity;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

import Bases.SQLite_OpenHelper;
import Bases.Zonas;

/**
 * Created by javier on 19/1/18.
 */

public class PreferenciasZona extends PreferenceActivity implements Preference.OnPreferenceChangeListener {

    SQLite_OpenHelper mHelper;
    SQLiteDatabase db;
    List<Zonas> mZonasList = new ArrayList<>();
    Preference mDialogoNormal;
    SharedPreferences.Editor mEditorDialogoNormal;
    String mValorS;
    boolean nuevoRegistro = false;
    String nId;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.datos_zona);
        Intent intento = getIntent();
        Bundle bundle = intento.getExtras();
        if (bundle!=null)
        {
            cambiarSummary(bundle.getString("00"));
            cambiarSummary(bundle.getString("01"));
            cambiarSummary(bundle.getString("02"));
        }
    }

    public void cambiarSummary(String campo)
    {
        mDialogoNormal = findPreference(campo);
        mDialogoNormal.setOnPreferenceChangeListener(this);
        mEditorDialogoNormal = mDialogoNormal.getEditor();

        mValorS = mDialogoNormal.getSharedPreferences().getString(campo, " ");
        mEditorDialogoNormal.putString(campo, mValorS);
        mEditorDialogoNormal.commit();
        mDialogoNormal.setSummary(mValorS);
        mEditorDialogoNormal.apply();
    }

    public void cambiarPreferencias( Preference preference, String newValue)
    {
        mValorS = newValue;
        mDialogoNormal = findPreference(preference.getKey());
        mEditorDialogoNormal = mDialogoNormal.getEditor();
        mEditorDialogoNormal.putString(preference.getKey(), mValorS);
        mEditorDialogoNormal.commit();
        mDialogoNormal.setSummary(mValorS);
        mEditorDialogoNormal.apply();
    }

    @Override
    public boolean onPreferenceChange(Preference preference, Object newValue)
    {

        cambiarPreferencias(preference, newValue.toString());

        mDialogoNormal = findPreference(preference.getKey());
        nId = mDialogoNormal.getSharedPreferences().getString("edit_text_id_zona", " ");

        cargarDatos();

        if (mZonasList.size()==0)
        {
            registroVacio();
            nuevoRegistro = true;
        }

        switch (preference.getKey())
        {
            case "edit_text_nombre_zona":
                mZonasList.get(0).set_nombre(
                        mDialogoNormal.getSharedPreferences().getString(preference.getKey(), " ")
                );
                break;
            case "edit_text_domicilio_zona":
                mZonasList.get(0).set_domicilio(
                        mDialogoNormal.getSharedPreferences().getString(preference.getKey(), " ")
                );
                break;
        }

        guardarCambios();

        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    public void guardarCambios()
    {
        mHelper = new SQLite_OpenHelper(this, null, 1);
        db = mHelper.getWritableDatabase();

        Zonas p =  new Zonas();
        p.set_nombre(mZonasList.get(0).get_nombre());
        p.set_domicilio(mZonasList.get(0).get_domicilio());

        if (!nuevoRegistro)
        {
            p.set_Id(Integer.parseInt(nId));
            mHelper.actualizarZona(db, p);
        }else{
            mHelper.duplicadoZonas(db, p);
            nuevoRegistro = false;

            Cursor c = mHelper.leerRegistroCampo(db, "zonas", "_nombre", mZonasList.get(0).get_nombre());
            if ( c.moveToFirst() )
            {
                p = new Zonas();
                p.set_nombre( c.getString(1));
                p.set_domicilio( c.getString( 2));
                mZonasList.clear();
                mZonasList.add(p);
            }
            c.close();

            mDialogoNormal = findPreference("edit_text_id_zona");
            String valor = String.valueOf(mZonasList.get(0).get_Id());
            cambiarPreferencias(mDialogoNormal, valor);
        }
    }

    public void registroVacio()
    {
        Zonas p = new Zonas();
        mZonasList.add(p);
    }

    public void cargarDatos()
    {
        mHelper = new SQLite_OpenHelper(this, null, 1);
        db = mHelper.getReadableDatabase();
        Cursor c = mHelper.leerRegistroCampo(db, "zonas", "_Id", nId);

        if ( c.moveToFirst() )
        {
            Zonas p = new Zonas();
            p.set_nombre( c.getString(1));
            p.set_domicilio( c.getString(2));

            mZonasList.add(p);
        }
        c.close();
    }
}
