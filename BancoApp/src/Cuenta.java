import java.io.Serializable;
import java.util.ArrayList;

public class Cuenta implements Serializable {
    private Cliente cliente; // Aquí guardo la información del cliente
    private double saldo; // Aquí guardo el saldo de la cuenta
    private ArrayList<Movimiento> movimientos; // Aquí guardo la lista de movimientos

    // Constructor para inicializar la cuenta con un cliente
    public Cuenta(Cliente cliente) {
        this.cliente = cliente;
        this.saldo = 0.0; // El saldo inicial es 0.0
        this.movimientos = new ArrayList<>(); // Inicializo la lista de movimientos
    }

    // Método para obtener el cliente
    public Cliente getCliente() {
        return cliente;
    }

    // Método para obtener el saldo
    public double getSaldo() {
        return saldo;
    }

    // Método para obtener la lista de movimientos
    public ArrayList<Movimiento> getMovimientos() {
        return movimientos;
    }

    // Método para ingresar dinero en la cuenta
    public void ingresar(double cantidad) {
        if (cantidad > 0) {
            saldo += cantidad; // Aumento el saldo
            movimientos.add(new Movimiento("Ingreso", cantidad)); // Registro el movimiento
        }
    }

    // Método para retirar dinero de la cuenta
    public void retirar(double cantidad) {
        if (cantidad > 0 && saldo >= cantidad) {
            saldo -= cantidad; // Disminuyo el saldo
            movimientos.add(new Movimiento("Retirada", cantidad)); // Registro el movimiento
        }
    }

    // Sobrescribo el método toString para mostrar la información de la cuenta
    @Override
    public String toString() {
        return "Cuenta{" +
                "cliente=" + cliente +
                ", saldo=" + saldo +
                ", movimientos=" + movimientos +
                '}';
    }
}