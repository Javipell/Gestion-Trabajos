package Bases;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.javi.pell.trabajos.PreferenciasZona;
import com.javi.pell.trabajos.R;

import java.util.List;

/**
 * Created by javier on 19/1/18.
 */

public class AdaptadorZonas extends BaseAdapter
{

    Context mContext;
    List<Zonas> ListaZonas;

    public AdaptadorZonas(Context context, List<Zonas> listaZonas)
    {
        mContext = context;
        ListaZonas = listaZonas;
    }

    @Override
    public int getCount() {
        return ListaZonas.size();
    }

    @Override
    public Object getItem(int posicion)
    {
        return ListaZonas.get(posicion);
    }

    @Override
    public long getItemId(int posicion)
    {
        return ListaZonas.get(posicion).get_Id();
    }

    @Override
    public View getView(final int posicion, View view, ViewGroup viewGroup)
    {
        View vista = view;
        LayoutInflater inflate = LayoutInflater.from(mContext);
        vista = inflate.inflate(R.layout.listado, null);
        final TextView tv_nombre = vista.findViewById(R.id.tv_nombre);
        ImageView imageButton3 = vista.findViewById(R.id.imageButton3);
        ImageView imageButton4 = vista.findViewById(R.id.imageButton4);

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Selecciono Modificar:  "+ tv_nombre.getText(),
                        Toast.LENGTH_SHORT).show();
                // crear un objeto SharedPreferences
                SharedPreferences zonaPreferencias = PreferenceManager.
                        getDefaultSharedPreferences(mContext);
                // hacer editable el objeto SharedPreferences
                SharedPreferences.Editor editor = zonaPreferencias.edit();
                // cambia la informacion en SharedPreferences
                editor.putString("edit_text_id_zona", String.valueOf(ListaZonas.get(posicion).get_Id()));
                editor.putString("edit_text_nombre_zona", ListaZonas.get(posicion).get_nombre());
                editor.putString("edit_text_domicilio_zona", ListaZonas.get(posicion).get_domicilio());
                // aplica cambios
                editor.apply();
                // abre la actividad misPreferencias zonas donde cambia el texto Summary por el valor
                Intent intento = new Intent(view.getContext(), PreferenciasZona.class);
                Bundle bundle = new Bundle();
                bundle.putString("00", "edit_text_id_zona");
                bundle.putString("01", "edit_text_nombre_zona");
                bundle.putString("02", "edit_text_domicilio_zona");
                intento.putExtras(bundle);
                view.getContext().startActivity(intento);
            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "SE BORRO EL REGISTRO DE: " + tv_nombre.getText()
                        + "\nVUELVA A LA PANTALLA PRINCIAL PARA ACTUALIZAR CAMBIOS", Toast.LENGTH_SHORT).show();
                SQLite_OpenHelper mHelper;
                SQLiteDatabase db;
                mHelper = new SQLite_OpenHelper(mContext, null, 1);
                db = mHelper.getWritableDatabase();
                mHelper.borrarRegistro(db, "zonas", ListaZonas.get(posicion).get_Id());
            }
        });

        tv_nombre.setText(ListaZonas.get(posicion).get_nombre());

        return vista;
    }
}
