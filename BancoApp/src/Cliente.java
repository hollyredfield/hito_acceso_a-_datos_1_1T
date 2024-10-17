import java.io.Serializable;

public class Cliente implements Serializable {
    private String nombre; // Aquí guardo el nombre del cliente
    private String dni; // Aquí guardo el DNI del cliente

    // Constructor para inicializar el nombre y el DNI
    public Cliente(String nombre, String dni) {
        this.nombre = nombre;
        this.dni = dni;
    }

    // Método para obtener el nombre del cliente
    public String getNombre() {
        return nombre;
    }

    // Método para obtener el DNI del cliente
    public String getDni() {
        return dni;
    }

    // Sobrescribo el método toString para mostrar la información del cliente
    @Override
    public String toString() {
        return "Cliente{" +
                "nombre='" + nombre + '\'' +
                ", dni='" + dni + '\'' +
                '}';
    }
}