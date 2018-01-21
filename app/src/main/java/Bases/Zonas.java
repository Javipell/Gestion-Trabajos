package Bases;

/**
 * Created by javier on 19/1/18.
 */

public class Zonas
{


    private int _Id;
    private String _nombre;
    private String _domicilio;

    public Zonas()
    {
        this._nombre = "";
        this._domicilio = "";
    }

    public Zonas( String _nombre, String _domicilio)
    {
        this._nombre = _nombre;
        this._domicilio = _domicilio;
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
}
