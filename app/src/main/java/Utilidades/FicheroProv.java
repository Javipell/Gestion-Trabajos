package Utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import Bases.Proveedores;
import Bases.SQLite_OpenHelper;

/**
 * Created by javier on 19/1/18.
 */

public class FicheroProv
{
    Proveedores datos;
    SQLite_OpenHelper mHelper;
    SQLiteDatabase db ;
    Context mContext;

    File _rutaArchivo = Environment.getExternalStorageDirectory();
    File _archivo;
    FileReader mFileReader = null;
    BufferedReader mBufferedReader;

    public FicheroProv(Context context, String archivo, boolean borrar)
    {
        mContext = context;
        mHelper = new SQLite_OpenHelper(mContext, null, 1);
        db = mHelper.getWritableDatabase();

        if (borrar)
        {
            mHelper.borrarTabla(db, "proveedores");
            mHelper.crearProveedores(db);
        }

        this._rutaArchivo = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        this._archivo = new File(_rutaArchivo + "/" + archivo);

        try {
            mFileReader = new FileReader(_archivo);
            mBufferedReader = new BufferedReader(mFileReader);

            String linea;

            try {
                while ((linea = mBufferedReader.readLine()) != null) {
                    String[] campos = linea.split(" -- ");
                    for (int pos=0; pos<campos.length; pos++)
                    {
                        campos[pos] = ( campos[pos].toLowerCase().contains("vacio") ) ? "" : campos[pos];
                    }
                    datos = new Proveedores(
                            campos[0], campos[1], campos[2],
                            campos[3], campos[4], campos[5],
                            campos[6], campos[7], campos[8],
                            campos[9], campos[10], campos[11]
                    );
                    mHelper.duplicado(db, datos);
                }
            } catch (Exception ex) {
                System.out.println("msg io: " + ex.getCause());
            }
        }catch (FileNotFoundException ex)
        {
            System.out.println("msg fichero no existe "+ _archivo + " " + ex.getCause());
        }finally {
            if ( null != mFileReader )
                try {
                    mFileReader.close();
                } catch (IOException ex)
                {
                    System.out.println("msg fichero cierre : " + ex.getCause());
                }
        }
    }
}
