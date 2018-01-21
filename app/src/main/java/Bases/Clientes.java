package Bases;

/**
 * Created by javier on 20/1/18.
 */

public class Clientes
{
    private int _Id;
    private String _nombre;
    private String _domicilio;
    private String _poblacion;
    private String _codigoPostal;
    private String _telefono;
    private String _contacto;
    private String _familiar;
    private String _identificador;

    public Clientes()
    {

    }

    public Clientes(String _nombre, String _domicilio, String _poblacion, String _codigoPostal,
                    String _telefono, String _contacto, String _familiar, String _identificador)
    {
        this._nombre = _nombre;
        this._domicilio = _domicilio;
        this._poblacion = _poblacion;
        this._codigoPostal = _codigoPostal;
        this._telefono = _telefono;
        this._contacto = _contacto;
        this._familiar = _familiar;
        this._identificador = _identificador;
    }

    public int get_Id() {
        return _Id;
    }

    public void set_Id(int _Id) {
        this._Id = _Id;
    }

    public String get_nombre() {
        return _nombre;
    }

    public void set_nombre(String _nombre) {
        this._nombre = _nombre;
    }

    public String get_domicilio() {
        return _domicilio;
    }

    public void set_domicilio(String _domicilio) {
        this._domicilio = _domicilio;
    }

    public String get_poblacion() {
        return _poblacion;
    }

    public void set_poblacion(String _poblacion) {
        this._poblacion = _poblacion;
    }

    public String get_codigoPostal() {
        return _codigoPostal;
    }

    public void set_codigoPostal(String _codigoPostal) {
        this._codigoPostal = _codigoPostal;
    }

    public String get_telefono() {
        return _telefono;
    }

    public void set_telefono(String _telefono) {
        this._telefono = _telefono;
    }

    public String get_contacto() {
        return _contacto;
    }

    public void set_contacto(String _contacto) {
        this._contacto = _contacto;
    }

    public String get_familiar() {
        return _familiar;
    }

    public void set_familiar(String _familiar) {
        this._familiar = _familiar;
    }

    public String get_identificador() {
        return _identificador;
    }

    public void set_identificador(String _identificador) {
        this._identificador = _identificador;
    }
}
