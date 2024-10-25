import java.io.Serializable; // Importo la interfaz Serializable para que los objetos de esta clase se puedan guardar en archivos

public class Movimiento implements Serializable {
    // Defino un identificador único para la serialización
    private static final long serialVersionUID = 1L;
    // Declaro una variable para almacenar el tipo de movimiento (Ingreso o Retirada)
    private String tipo;
    // Declaro una variable para almacenar la cantidad del movimiento
    private double cantidad;

    // Constructor de la clase Movimiento que recibe el tipo y la cantidad como parámetros
    public Movimiento(String tipo, double cantidad) {
        this.tipo = tipo; // Asigno el tipo recibido al atributo tipo
        this.cantidad = cantidad; // Asigno la cantidad recibida al atributo cantidad
    }

    // Método para obtener el tipo de movimiento
    public String getTipo() {
        return tipo; // Devuelvo el tipo de movimiento
    }

    // Método para obtener la cantidad del movimiento
    public double getCantidad() {
        return cantidad; // Devuelvo la cantidad del movimiento
    }

    // Sobrescribo el método toString para devolver una representación en cadena del objeto Movimiento
    @Override
    public String toString() {
        return "Movimiento{" +
                "tipo='" + tipo + '\'' +
                ", cantidad=" + cantidad +
                '}'; // Devuelvo una cadena con el tipo y la cantidad del movimiento
    }
}