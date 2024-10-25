import java.io.Serializable; // Importo la interfaz Serializable para que los objetos de esta clase se puedan guardar en archivos

public class Cliente implements Serializable {
    // Defino un identificador único para la serialización
    private static final long serialVersionUID = 1L;
    // Declaro las variables para almacenar el nombre y el DNI del cliente
    private String nombre;
    private String dni;

    // Constructor de la clase Cliente que recibe el nombre y el DNI como parámetros
    public Cliente(String nombre, String dni) {
        this.nombre = nombre; // Asigno el nombre recibido al atributo nombre
        this.dni = dni; // Asigno el DNI recibido al atributo dni
    }

    // Método para obtener el nombre del cliente
    public String getNombre() {
        return nombre; // Devuelvo el nombre del cliente
    }

    // Método para obtener el DNI del cliente
    public String getDni() {
        return dni; // Devuelvo el DNI del cliente
    }

    // Sobrescribo el método toString para devolver una representación en cadena del objeto Cliente
    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                '}'; // Devuelvo una cadena con el nombre y el DNI del cliente
    }
}