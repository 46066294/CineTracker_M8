package marc.cinetracker_m8;

/**
 * Created by 46066294p on 24/02/16.
 */
public class PojoForNote {

    String titulo;
    String descripcion;
    Double longitud;
    Double latitud;



    public PojoForNote(){}

    public PojoForNote(String titulo, String descripcion, Double longitud, Double latitud) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.longitud = longitud;
        this.latitud = latitud;
    }


}
