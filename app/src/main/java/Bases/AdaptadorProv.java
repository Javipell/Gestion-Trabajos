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

import com.javi.pell.trabajos.PreferenciasProv;
import com.javi.pell.trabajos.R;

import java.util.List;

/**
 * Created by javier on 16/1/18.
 */

public class AdaptadorProv extends BaseAdapter
{

    Context mContext;
    List<Proveedores> ListaProv;

    public AdaptadorProv(Context context, List<Proveedores> listaProv)
    {
        mContext = context;

        ListaProv = listaProv;
    }

    @Override
    public int getCount() {
        return ListaProv.size();
    }

    @Override
    public Object getItem(int posicion)
    {
        return ListaProv.get(posicion);
    }

    @Override
    public long getItemId(int posicion) {
        return ListaProv.get(posicion).get_Id();
    }

    @Override
    public View getView(final int posicion, View view, ViewGroup viewGroup) {
        View vista = view;
        LayoutInflater inflate = LayoutInflater.from(mContext);
        vista = inflate.inflate(R.layout.listado, null);
        final TextView tv_nombre = vista.findViewById(R.id.tv_nombre);
        ImageView imageButton3 = vista.findViewById(R.id.imageButton3);
        ImageView imageButton4 = vista.findViewById(R.id.imageButton4);

        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Selecciono Modificar: "+ tv_nombre.getText() ,Toast.LENGTH_SHORT).show();

                // fuente : https://www.youtube.com/watch?v=AEBnm-bNqtE
                // crear un objeto SharedPreferences
                SharedPreferences proveedorPreferencias = PreferenceManager
                        .getDefaultSharedPreferences(mContext);
                // hacer editable el objeto SharedPreferences
                SharedPreferences.Editor editor = proveedorPreferencias.edit();
                // cambia la informacion a SharedPreferences
                editor.putString("edit_text_id", String.valueOf(ListaProv.get(posicion).get_Id()));
                editor.putString("edit_text_nombre", ListaProv.get(posicion).get_nombre().toString() );
                editor.putString("edit_text_logo", ListaProv.get(posicion).get_logo().toString() );
                editor.putString("edit_text_domicilio", ListaProv.get(posicion).get_domicilio().toString() );
                editor.putString("edit_text_poblacion", ListaProv.get(posicion).get_poblacion().toString() );
                editor.putString("edit_text_codigo_postal", ListaProv.get(posicion).get_codigoPostal().toString() );
                editor.putString("edit_text_telefono", ListaProv.get(posicion).get_telefono().toString() );
                editor.putString("edit_text_fax", ListaProv.get(posicion).get_fax().toString() );
                editor.putString("edit_text_correo", ListaProv.get(posicion).get_correo().toString() );
                editor.putString("edit_text_contacto", ListaProv.get(posicion).get_contacto().toString() );

                String estado = ListaProv.get(posicion).get_estado().toString();
                System.out.println("msg valor " + estado);
                if (estado.toLowerCase().contains("alta") )
                {
                    editor.putBoolean("switch_estado", true );
                }else{
                    editor.putBoolean("switch_estado", false );
                }

                estado = ListaProv.get(posicion).get_alta().toString();
                System.out.println("msg valor " + estado);
                estado = ListaProv.get(posicion).get_baja().toString();
                System.out.println("msg valor " + estado);
                editor.putString("edit_text_alta", ListaProv.get(posicion).get_alta().toString() );
                editor.putString("edit_text_baja", ListaProv.get(posicion).get_baja().toString() );

                // aplica los cambios
                editor.apply();

                // abre la actividad misPreferencias donde cambia el texto Summary por el valor
                //view.getContext().startActivity((new Intent(view.getContext(), MisPreferencias.class)));
                Intent intento = new Intent(view.getContext(), PreferenciasProv.class);
                Bundle bundle = new Bundle();

                bundle.putString("00", "edit_text_id");
                bundle.putString("01", "edit_text_nombre");
                bundle.putString("02", "edit_text_logo");
                bundle.putString("03", "edit_text_domicilio");
                bundle.putString("04", "edit_text_poblacion");
                bundle.putString("05", "edit_text_codigo_postal");
                bundle.putString("06", "edit_text_telefono");
                bundle.putString("07", "edit_text_fax");
                bundle.putString("08", "edit_text_correo");
                bundle.putString("09", "edit_text_contacto");
                bundle.putString("10", "switch_estado");
                bundle.putString("11", "edit_text_alta");
                bundle.putString("12", "edit_text_baja");

                intento.putExtras(bundle);
                view.getContext().startActivity(intento);

            }
        });

        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"SE BORRO EL REGISTRO DE : "+ tv_nombre.getText() + "\nVUELVA A LA PANTALLA PRINCIPAL PARA ACTUALIAR CAMBIOS",Toast.LENGTH_SHORT).show();
                SQLite_OpenHelper mHelper;
                SQLiteDatabase db;
                mHelper = new SQLite_OpenHelper(mContext,null, 1);
                db = mHelper.getWritableDatabase();
                mHelper.borrarRegistro(db, "proveedores", ListaProv.get(posicion).get_Id());
            }
        });


        tv_nombre.setText(ListaProv.get(posicion).get_nombre());
        System.out.println("msg le nombre "+ tv_nombre.getText());

        return vista;
    }
}
