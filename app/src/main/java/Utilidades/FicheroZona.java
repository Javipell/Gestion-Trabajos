package Utilidades;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import Bases.SQLite_OpenHelper;
import Bases.Zonas;

/**
 * Created by javier on 19/1/18.
 */

public class FicheroZona
{
    Zonas datos;
    SQLite_OpenHelper mHelper;
    SQLiteDatabase db ;
    Context mContext;

    File _rutaArchivo = Environment.getExternalStorageDirectory();
    File _archivo;
    FileReader mFileReader = null;
    BufferedReader mBufferedReader;

    public FicheroZona(Context context, String archivo, boolean borrar)
    {
        mContext = context;
        mHelper = new SQLite_OpenHelper(mContext, null, 1);
        db = mHelper.getWritableDatabase();

        if (borrar)
        {
            mHelper.borrarTabla(db, "zonas");
            mHelper.crearZonas(db);
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
                    datos = new Zonas( campos[0], campos[1] );
                    mHelper.duplicadoZonas(db, datos);
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
