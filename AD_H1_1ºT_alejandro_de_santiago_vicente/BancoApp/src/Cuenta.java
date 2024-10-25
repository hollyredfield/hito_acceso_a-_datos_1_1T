import java.io.Serializable; // Importo la interfaz Serializable para que los objetos de esta clase se puedan guardar en archivos
import java.util.ArrayList; // Importo la clase ArrayList para manejar listas dinámicas

public class Cuenta implements Serializable {
    // Defino un identificador único para la serialización
    private static final long serialVersionUID = 1L;
    // Declaro una variable para almacenar el cliente asociado a la cuenta
    private Cliente cliente;
    // Declaro una variable para almacenar el saldo de la cuenta
    private double saldo;
    // Declaro una lista para almacenar los movimientos de la cuenta
    private ArrayList<Movimiento> movimientos;

    // Constructor de la clase Cuenta que recibe un cliente como parámetro
    public Cuenta(Cliente cliente) {
        this.cliente = cliente; // Asigno el cliente recibido al atributo cliente
        this.saldo = 0.0; // Inicializo el saldo en 0.0
        this.movimientos = new ArrayList<>(); // Inicializo la lista de movimientos
    }

    // Método para obtener el cliente asociado a la cuenta
    public Cliente getCliente() {
        return cliente; // Devuelvo el cliente asociado a la cuenta
    }

    // Método para obtener el saldo de la cuenta
    public double getSaldo() {
        return saldo; // Devuelvo el saldo de la cuenta
    }

    // Método para obtener la lista de movimientos de la cuenta
    public ArrayList<Movimiento> getMovimientos() {
        return movimientos; // Devuelvo la lista de movimientos de la cuenta
    }

    // Método para ingresar una cantidad de dinero en la cuenta
    public void ingresar(double cantidad) {
        if (cantidad > 0) { // Verifico que la cantidad a ingresar sea positiva
            saldo += cantidad; // Aumento el saldo de la cuenta
            movimientos.add(new Movimiento("Ingreso", cantidad)); // Agrego un nuevo movimiento de ingreso a la lista
        }
    }

    // Método para retirar una cantidad de dinero de la cuenta
    public boolean retirar(double cantidad) {
        if (cantidad > 0 && saldo >= cantidad) { // Verifico que la cantidad a retirar sea positiva y que haya suficiente saldo
            saldo -= cantidad; // Disminuyo el saldo de la cuenta
            movimientos.add(new Movimiento("Retirada", cantidad)); // Agrego un nuevo movimiento de retirada a la lista
            return true; // Devuelvo true para indicar que la retirada fue exitosa
        }
        return false; // Devuelvo false para indicar que la retirada no fue exitosa
    }

    // Sobrescribo el método toString para devolver una representación en cadena del objeto Cuenta
    @Override
    public String toString() {
        return "Cuenta{" +
                "cliente=" + cliente +
                ", saldo=" + saldo +
                ", movimientos=" + movimientos +
                '}'; // Devuelvo una cadena con el cliente, el saldo y los movimientos de la cuenta
    }
}