package Bases;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


/**
 * Created by javier on 16/1/18.
 */

public class SQLite_OpenHelper extends SQLiteOpenHelper
{

    public static final String BASE_DATOS = "base_datos";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLA_PROVEEDORES = "proveedores";
    public static final String COL_ID = "_Id";
    public static final String COL_NOMBRE = "_nombre";
    public static final String COL_DOMICILIO = "_domicilio";
    public static final String COL_POBLACION = "_poblacion";
    public static final String COL_CODPOSTAL = "_codigoPostal";
    public static final String COL_TELEFONO = "_telefono";
    public static final String COL_FAX = "_fax";
    public static final String COL_CORREO = "_correo";
    public static final String COL_CONTACTO = "_contacto";
    public static final String COL_LOGO = "_logo";
    public static final String COL_ESTADO = "_estado";
    public static final String COL_ALTA = "_alta";
    public static final String COL_BAJA = "_baja";

    public static final String TABLA_ZONAS = "zonas";

    public static final String TABLA_CLIENTES = "clientes";
    public static final String COL_FAMILIAR = "_familiar";
    public static final String COL_IDENTIFICADOR = "_identificador";


    String CADENA_PROVEEDORES = "CREATE TABLE if not exists " + TABLA_PROVEEDORES + " ( " +
    COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
    COL_NOMBRE + " TEXT, " +
    COL_DOMICILIO + " TEXT, " +
    COL_POBLACION + " TEXT, " +
    COL_CODPOSTAL + " TEXT, " +
    COL_TELEFONO + " TEXT, " +
    COL_FAX + " TEXT, " +
    COL_CORREO + " TEXT, " +
    COL_CONTACTO + " TEXT, " +
    COL_LOGO + " TEXT, " +
    COL_ESTADO + " TEXT, " +
    COL_ALTA + " TEXT, " +
    COL_BAJA + " TEXT " +
        " );";

    String  CADENA_ZONAS = "CREATE TABLE if not exists " + TABLA_ZONAS + " ( " +
    COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
    COL_NOMBRE + " TEXT, " +
    COL_DOMICILIO + " TEXT " +
        " );";

    String CADENA_CLIENTES = "CREATE TABLE if not exists " + TABLA_CLIENTES + " ( " +
            COL_ID + " INTEGER PRIMARY KEY autoincrement, " +
            COL_NOMBRE + " TEXT, " +
            COL_DOMICILIO + " TEXT, " +
            COL_POBLACION + " TEXT, " +
            COL_CODPOSTAL + " TEXT, " +
            COL_TELEFONO + " TEXT, " +
            COL_CONTACTO + " TEXT, " +
            COL_FAMILIAR + " TEXT, " +
            COL_IDENTIFICADOR + " TEXT " +
            " );";

    public SQLite_OpenHelper(Context context, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, BASE_DATOS, factory, version);
    }

    /**
     * CADENAS DE CREACION DE TABLAS
     * CREACION DE BASES DE DATOS
     * @param db
     */
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // CREAR BASES DE DATOS
        db.execSQL(CADENA_PROVEEDORES);
        db.execSQL(CADENA_ZONAS);
        db.execSQL(CADENA_CLIENTES);

    }

    public void crearProveedores(SQLiteDatabase db)
    {
        db.execSQL(CADENA_PROVEEDORES);
    }

    public void crearZonas(SQLiteDatabase db)
    {
        db.execSQL(CADENA_ZONAS);
    }

    public void crearClientes(SQLiteDatabase db)
    {
        db.execSQL(CADENA_CLIENTES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    /**
     * ABRIR BASE DE DATOS
     */
    public void abrir()
    {
        this.getReadableDatabase();
    }

    /**
     * CERRAR BASE DE DATOS
     */
    public void cerrar()
    {
        this.close();
    }

    public void borrarTabla(SQLiteDatabase db, String tabla)
    {
        db.execSQL("drop table if exists "+ tabla);
    }
    /**
     * Leer un registro por el ID en una tabla
     * @param db
     * @param tabla
     * @param id
     * @return
     */
    public Cursor leerRegistroId(SQLiteDatabase db, String tabla, int id)
    {
        String sql = "SELECT * FROM " + tabla + " WHERE _id = " + id;
        Cursor c = db.rawQuery(sql, null);
        if ( !c.moveToFirst() )
        {
            System.out.println("msg leerRegistroID Fallo la busqueda en la tabla " + tabla + " del registro id " + id);
            System.out.println("msg sql: " + sql);
        }
        return c;
    }

    public Cursor leerRegistroCampo(SQLiteDatabase db, String tabla, String campo, String valor)
    {
        String sql;
        if (campo !="" ) {
            sql = "SELECT * FROM " + tabla + " WHERE " + campo + "='" + valor + "'";
        }else{
            sql = "SELECT * FROM " + tabla ;
        }
        System.out.println("msg sql: " + sql);
        Cursor c = db.rawQuery(sql, null);
        if ( !c.moveToFirst() )
        {
            System.out.println("msg leerRegistroCampo Fallo la busqueda en la tabla " + tabla + " en el campo " + campo + " el valor "+ valor);
            System.out.println("msg sql: " + sql);
        }
        return c;
    }

    /**
     * INSERTAR PROVEEDOR NUEVO
     * @param nombre
     * @param domicilio
     * @param poblacion
     * @param codigoPostal
     * @param telefono
     * @param fax
     * @param correo
     * @param contacto
     * @param logo
     * @param estado
     * @param alta
     * @param baja
     * @return
     */
    public boolean insertarProveedor( String nombre, String domicilio, String poblacion, String codigoPostal,
                                   String telefono, String fax, String correo, String contacto,
                                   String logo, String estado, String alta, String baja )
    {
        boolean error = false;
        ContentValues values = new ContentValues();
        values.put("_nombre", nombre);
        values.put("_domicilio", domicilio);
        values.put("_poblacion", poblacion);
        values.put("_codigoPostal", codigoPostal);
        values.put("_telefono", telefono);
        values.put("_fax", fax);
        values.put("_correo", correo);
        values.put("_contacto", contacto);
        values.put("_logo", logo);
        values.put("_estado", estado);
        values.put("_alta", alta);
        values.put("_baja", baja);
        try {
            this.getWritableDatabase().insert(TABLA_PROVEEDORES, null, values);
        }catch (Exception ex){
            error = true;
            System.out.println("msg no se ha insertado el registro en " + TABLA_PROVEEDORES );
            System.out.println("msg error " + ex.getMessage());
        }
        return error;
    }


    /**
     * BORRAR REGISTRO POR SU ID EN UNA TABLA
     * @param db
     * @param tabla
     * @param id
     * @return
     */
    public boolean borrarRegistro(SQLiteDatabase db, String tabla, int id)
    {
        boolean error = false;
        String condicion = "_Id=" + id;
        try{
            db.delete(tabla, condicion, null);
            System.out.println("msg se ha BORRADO el registro ("+ id +") en " + tabla );

        }catch (Exception ex)
        {
            error = true;
            System.out.println("msg no se ha BORRADO el registro en " + tabla );
            System.out.println("msg error " + ex.getMessage());
        }
        return error;
    }

    /**
     * ACTUALIZAR UN REGISTRO DE LA TABLA PROVEEDORES POR SU ID
     * @param db
     * @param p
     * @return
     */
    public boolean actualizarProveedor(SQLiteDatabase db, Proveedores p)
    {
        boolean error = false;
        ContentValues values = new ContentValues();
        values.put("_nombre", p.get_nombre()); // 1
        values.put("_domicilio", p.get_domicilio()); // 2
        values.put("_poblacion", p.get_poblacion()); // 3
        values.put("_codigoPostal", p.get_codigoPostal()); //4
        values.put("_telefono", p.get_telefono()); // 5
        values.put("_fax", p.get_fax()); // 6
        values.put("_correo", p.get_correo()); // 7
        values.put("_contacto", p.get_contacto()); // 8
        values.put("_logo", p.get_logo()); // 9
        values.put("_estado", p.get_estado()); //10
        values.put("_alta", p.get_alta()); //11
        values.put("_baja", p.get_baja()); //12
        try{
            String codicion = "_Id=" + p.get_Id();
            int cant = db.update(TABLA_PROVEEDORES, values, codicion, null);
            if (cant==1)
            {
                System.out.println("msg registro actualizado " + values);

            }
        }catch (Exception ex)
        {
            error = true;
            System.out.println("msg error al actualizar el registro " + p.get_Id() + " en la tabla " + TABLA_PROVEEDORES);
            System.out.println("msg error " + ex.getCause());
        }
    return error;
    }

    public void duplicado(SQLiteDatabase db, Proveedores p)
    {
        boolean error = false;
        String sql = "SELECT * FROM " + TABLA_PROVEEDORES + " WHERE _nombre='" + p.get_nombre() + "'";
        System.out.println("msg sql " + sql);
        Cursor c = db.rawQuery(sql, null);
        if ( c.moveToFirst() )
        {
            error = true;
            System.out.println("msg error ya existe el registro " + p.get_Id() + " en la tabla " + TABLA_PROVEEDORES);
        }else{
            error = insertarProveedor(
                    p.get_nombre(), p.get_domicilio(), p.get_poblacion(),
                    p.get_codigoPostal(), p.get_telefono(), p.get_fax(),
                    p.get_correo(), p.get_contacto(), p.get_logo(),
                    p.get_estado(), p.get_alta(), p.get_baja()
            );
            System.out.println("msg registro añadido (" + p.get_Id() + ") "+ p.get_nombre() +" en la tabla " + TABLA_PROVEEDORES);

        }
    }

    public void duplicadoZonas(SQLiteDatabase db, Zonas p)
    {
        boolean error = false;
        String sql = "SELECT * FROM " + TABLA_ZONAS + " WHERE _nombre='" + p.get_nombre() + "'";
        Cursor c = db.rawQuery(sql, null);
        if ( c.moveToFirst() )
        {
            error = true;
            System.out.println("msg error ya existe el registro " + p.get_Id() + " en la tabla " + TABLA_ZONAS);
        }else{
            error = insertarZona(
                    p.get_nombre(), p.get_domicilio()
            );
            System.out.println("msg registro añadido (" + p.get_Id() + ") "+ p.get_nombre() +" en la tabla " + TABLA_ZONAS);

        }
    }

    public void duplicadoClientes(SQLiteDatabase db, Clientes p)
    {
        boolean error = false;
        String sql = "SELECT * FROM " + TABLA_CLIENTES + " WHERE _nombre='" + p.get_nombre() + "'";
        System.out.println("msg sql " + sql);
        Cursor c = db.rawQuery(sql, null);
        if ( c.moveToFirst() )
        {
            error = true;
            System.out.println("msg error ya existe el registro " + p.get_Id() + " en la tabla " + TABLA_CLIENTES);
        }else{
            error = insertarCliente(
                    p.get_nombre(), p.get_domicilio(), p.get_poblacion(),
                    p.get_codigoPostal(), p.get_telefono(), p.get_contacto(),
                    p.get_familiar(), p.get_identificador()
            );
            System.out.println("msg registro añadido (" + p.get_Id() + ") "+ p.get_nombre() +" en la tabla " + TABLA_PROVEEDORES);

        }
    }

    /**
     * INSERTAR zona NUEVO
     * @param nombre
     * @param domicilio

     * @return
     */
    public boolean insertarZona( String nombre, String domicilio )
    {
        boolean error = false;
        ContentValues values = new ContentValues();
        values.put("_nombre", nombre);
        values.put("_domicilio", domicilio);

        try {
            this.getWritableDatabase().insert(TABLA_ZONAS, null, values);
        }catch (Exception ex){
            error = true;
            System.out.println("msg no se ha insertado el registro en " + TABLA_ZONAS );
            System.out.println("msg error " + ex.getCause());
        }
        return error;
    }

    /**
     * INSERTAR PROVEEDOR NUEVO
     * @param nombre
     * @param domicilio
     * @param poblacion
     * @param codigoPostal
     * @param telefono
     * @param contacto
     * @param familiar
     * @return
     */
    public boolean insertarCliente( String nombre, String domicilio, String poblacion, String codigoPostal,
                                      String telefono, String contacto, String familiar, String identificador)
    {
        boolean error = false;
        ContentValues values = new ContentValues();
        values.put("_nombre", nombre);
        values.put("_domicilio", domicilio);
        values.put("_poblacion", poblacion);
        values.put("_codigoPostal", codigoPostal);
        values.put("_telefono", telefono);
        values.put("_contacto", contacto);
        values.put("_familiar", familiar);
        values.put("_identificador", identificador);

        try {
            this.getWritableDatabase().insert(TABLA_CLIENTES, null, values);
        }catch (Exception ex){
            error = true;
            System.out.println("msg no se ha insertado el registro en " + TABLA_CLIENTES );
            System.out.println("msg error " + ex.getMessage());
        }
        return error;
    }

    /**
     * ACTUALIZAR UN REGISTRO DE LA TABLA PROVEEDORES POR SU ID
     * @param db
     * @param p
     * @return
     */
    public boolean actualizarZona(SQLiteDatabase db, Zonas p)
    {
        boolean error = false;
        ContentValues values = new ContentValues();
        values.put("_nombre", p.get_nombre()); // 1
        values.put("_domicilio", p.get_domicilio()); // 2

        try{
            String codicion = "_Id=" + p.get_Id();
            int cant = db.update(TABLA_ZONAS, values, codicion, null);
            if (cant==1)
            {
                System.out.println("msg registro actualizado " + values);

            }
        }catch (Exception ex)
        {
            error = true;
            System.out.println("msg error al actualizar el registro " + p.get_Id() + " en la tabla " + TABLA_ZONAS);
            System.out.println("msg error " + ex.getCause());
        }
        return error;
    }

    /**
     * ACTUALIZAR UN REGISTRO DE LA TABLA PROVEEDORES POR SU ID
     * @param db
     * @param p
     * @return
     */
    public boolean actualizarCliente(SQLiteDatabase db, Clientes p)
    {
        boolean error = false;
        ContentValues values = new ContentValues();
        values.put("_nombre", p.get_nombre()); // 1
        values.put("_domicilio", p.get_domicilio()); // 2
        values.put("_poblacion", p.get_poblacion()); // 3
        values.put("_codigoPostal", p.get_codigoPostal()); //4
        values.put("_telefono", p.get_telefono()); // 5
        values.put("_contacto", p.get_contacto()); // 6
        values.put("_familiar", p.get_familiar()); // 7
        values.put("_identificador", p.get_identificador()); //8
        try{
            String codicion = "_Id=" + p.get_Id();
            int cant = db.update(TABLA_CLIENTES, values, codicion, null);
            if (cant==1)
            {
                System.out.println("msg registro actualizado " + values);

            }
        }catch (Exception ex)
        {
            error = true;
            System.out.println("msg error al actualizar el registro " + p.get_Id() + " en la tabla " + TABLA_CLIENTES);
            System.out.println("msg error " + ex.getCause());
        }
        return error;
    }

    /**
     * INSERTAR DATOS INICIALES
     * @param db
     */
    public void datosIniciales(SQLiteDatabase db)
    {
        duplicado(db, new Proveedores("Memora", "San Juan Bosco, 58",
                "Zaragoza","50008",
                "976467273","976","info@memora.es",
                "Director","logotipo",
                "Alta", "01/12/2017", "31/12/2017" ) );
        duplicado(db, new Proveedores("La Paz", "San Juan Bosco, 58",
                "Zaragoza","50008",
                "976467273","976","info@memora.es",
                "Director","logo",
                "Baja", "01/12/2017", "31/12/2017" ) );
        duplicado(db, new Proveedores("Servisa", "San Juan Bosco, 58",
                "Zaragoza","50008",
                "976467273","976","info@memora.es",
                "Director","logo",
                "Alta", "01/12/2017", "31/12/2017" ) );

        duplicadoZonas(db, new Zonas("Capilla 1", "Edificio B"));
        duplicadoZonas(db, new Zonas("Capilla 2", "Edificio B"));
        duplicadoZonas(db, new Zonas("Capilla 3", "Edificio A"));

        duplicadoClientes(db, new Clientes( "NOMBRE CLIENTE 1", "DOMICILIO 1", "POBLACION 1",
                "CODIGO POSTAL 1", "TELEFONO 1" , "COL_CONTACTO 1", "COL_FAMILIAR 1","identificador1" ));
        duplicadoClientes(db, new Clientes( "NOMBRE CLIENTE 2", "DOMICILIO 2", "POBLACION 2",
                "CODIGO POSTAL 2", "TELEFONO 2" , "COL_CONTACTO 2", "COL_FAMILIAR 2", "identficador 2" ));
        duplicadoClientes(db, new Clientes( "NOMBRE CLIENTE 3", "DOMICILIO 3", "POBLACION 3",
                "CODIGO POSTAL 3", "TELEFONO 3" , "COL_CONTACTO 3", "COL_FAMILIAR 3", "identificaddor 3" ));

    }
}
