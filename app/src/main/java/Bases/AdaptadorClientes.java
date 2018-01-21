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

import com.javi.pell.trabajos.PreferenciasClientes;
import com.javi.pell.trabajos.R;

import java.util.List;

/**
 * Created by javier on 20/1/18.
 */

public class AdaptadorClientes  extends BaseAdapter
{
    Context mContext;
    List<Clientes> mList;

    public AdaptadorClientes(Context context, List<Clientes> mList)
    {
        mContext = context;
        this.mList = mList;
    }

    @Override
    public int getCount()
    {
        return mList.size();
    }

    @Override
    public Object getItem(int posicion)
    {
        return mList.get(posicion);
    }

    @Override
    public long getItemId(int posicion)
    {
        return mList.get(posicion).get_Id();
    }

    @Override
    public View getView(final int posicion, View view, ViewGroup viewGroup) {
        View vista = view;
        LayoutInflater inflate = LayoutInflater.from(mContext);
        vista = inflate.inflate(R.layout.listado, null);
        final TextView tv_nombre = vista.findViewById(R.id.tv_nombre);
        ImageView imageButton3 = vista.findViewById(R.id.imageButton3);
        ImageView imageButton4 = vista.findViewById(R.id.imageButton4);

        // boton modificar registro
        imageButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Selecciono modificar: " + tv_nombre.getText(),
                        Toast.LENGTH_LONG).show();
                // crear un objeto SharedPreferences
                SharedPreferences clientesPreferencias = PreferenceManager
                        .getDefaultSharedPreferences(mContext);
                // hacer editable el objeto SharedPreferences
                SharedPreferences.Editor editor = clientesPreferencias.edit();
                // cambia la informacion a SharedPreferences
                editor.putString("edit_text_id", String.valueOf(mList.get(posicion).get_Id()));
                editor.putString("edit_text_nombre", mList.get(posicion).get_nombre().toString() );
                editor.putString("edit_text_familiar", mList.get(posicion).get_familiar().toString() );
                editor.putString("edit_text_domicilio", mList.get(posicion).get_domicilio().toString() );
                editor.putString("edit_text_poblacion", mList.get(posicion).get_poblacion().toString() );
                editor.putString("edit_text_codigo_postal", mList.get(posicion).get_codigoPostal().toString() );
                editor.putString("edit_text_telefono", mList.get(posicion).get_telefono().toString() );
                editor.putString("edit_text_contacto", mList.get(posicion).get_contacto().toString() );
                editor.putString("edit_text_identificador", mList.get(posicion).get_identificador().toString());
                // aplicar cambios
                editor.apply();

                Intent intento = new Intent(view.getContext(), PreferenciasClientes.class);
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
                view.getContext().startActivity(intento);
            }
        });

        // boton eliminar registro
        imageButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "SE BORRRO EL REGISTRO DE: "+ tv_nombre.getText() +
                "\nVUELVA A LA PANTALLA PRINCIPAL PARA ACTUALIZAR CAMBIOS",
                        Toast.LENGTH_SHORT).show();
                SQLite_OpenHelper mHelper;
                SQLiteDatabase db;
                mHelper = new SQLite_OpenHelper(mContext, null, 1);
                db = mHelper.getWritableDatabase();
                mHelper.borrarRegistro(db, "clientes", mList.get(posicion).get_Id());
            }
        });

        // guarda el nombre del cliente en la lista
        tv_nombre.setText(mList.get(posicion).get_nombre());
        System.out.println("msg el nombre "+ tv_nombre.getText());

        return vista;
    }
}
