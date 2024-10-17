import java.io.Serializable;

public class Movimiento implements Serializable {
    private String tipo; // Aquí guardo el tipo de movimiento (por ejemplo, "Ingreso" o "Retirada")
    private double cantidad; // Aquí guardo la cantidad del movimiento

    // Constructor para inicializar el tipo y la cantidad del movimiento
    public Movimiento(String tipo, double cantidad) {
        this.tipo = tipo;
        this.cantidad = cantidad;
    }

    // Método para obtener el tipo de movimiento
    public String getTipo() {
        return tipo;
    }

    // Método para obtener la cantidad del movimiento
    public double getCantidad() {
        return cantidad;
    }

    // Sobrescribo el método toString para mostrar la información del movimiento
    @Override
    public String toString() {
        return "Movimiento{" +
                "tipo='" + tipo + '\'' +
                ", cantidad=" + cantidad +
                '}';
    }
}