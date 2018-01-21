package Bases;

import java.util.Date;

/**
 * Created by javier on 16/1/18.
 */

public class Proveedores
{
    private int _Id;
    private String _nombre;
    private String _domicilio;
    private String _poblacion;
    private String _codigoPostal;
    private String _telefono;
    private String _fax;
    private String _correo;
    private String _contacto;
    private String _logo;
    private String _estado;
    private String _alta;
    private String _baja;

    public Proveedores()
    {
        this._nombre = "";
        this._domicilio = "";
        this._poblacion = "";
        this._codigoPostal = "";
        this._telefono = "";
        this._fax = "";
        this._correo = "";
        this._contacto = "";
        this._logo = "";
        this._estado = "";
        this._alta = "";
        this._baja = "";
    }

    public Proveedores(String _nombre, String _domicilio, String _poblacion, String _codigoPostal,
                       String _telefono, String _fax, String _correo, String _contacto,
                       String _logo, String _estado, String _alta, String _baja)
    {

        this._nombre = _nombre;
        this._domicilio = _domicilio;
        this._poblacion = _poblacion;
        this._codigoPostal = _codigoPostal;
        this._telefono = _telefono;
        this._fax = _fax;
        this._correo = _correo;
        this._contacto = _contacto;
        this._logo = _logo;
        this._estado = _estado;
        this._alta = _alta;
        this._baja = _baja;
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

    public String get_fax() {
        return _fax;
    }

    public void set_fax(String _fax) {
        this._fax = _fax;
    }

    public String get_correo() {
        return _correo;
    }

    public void set_correo(String _correo) {
        this._correo = _correo;
    }

    public String get_contacto() {
        return _contacto;
    }

    public void set_contacto(String _contacto) {
        this._contacto = _contacto;
    }

    public String get_logo() {
        return _logo;
    }

    public void set_logo(String _logo) {
        this._logo = _logo;
    }

    public String get_estado() {
        return _estado;
    }

    public void set_estado(String _estado) {
        this._estado = _estado;
    }

    public String get_alta() {
        return _alta;
    }

    public void set_alta(String _alta) {
        this._alta = _alta;
    }

    public String get_baja() {
        return _baja;
    }

    public void set_baja(String  _baja) {
        this._baja = _baja;
    }
}
