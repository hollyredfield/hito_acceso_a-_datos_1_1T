import java.io.*;
import java.util.Scanner;

public class BancoApp {
    private static final String FILE_PATH = "cuenta.dat";

    public static void main(String[] args) {
        // Cargo la cuenta desde el archivo
        Cuenta cuenta = cargarCuenta();

        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            // Muestro el menú de opciones
            System.out.println("1. Ingresar dinero");
            System.out.println("2. Retirar dinero");
            System.out.println("3. Mostrar saldo");
            System.out.println("4. Mostrar movimientos");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();

            switch (opcion) {
                case 1:
                    // Ingreso de dinero
                    System.out.print("Cantidad a ingresar: ");
                    double ingreso = scanner.nextDouble();
                    cuenta.ingresar(ingreso);
                    break;
                case 2:
                    // Retiro de dinero
                    System.out.print("Cantidad a retirar: ");
                    double retirada = scanner.nextDouble();
                    cuenta.retirar(retirada);
                    break;
                case 3:
                    // Muestro el saldo actual
                    System.out.println("Saldo actual: " + cuenta.getSaldo());
                    break;
                case 4:
                    // Muestro los movimientos
                    System.out.println("Movimientos: " + cuenta.getMovimientos());
                    break;
                case 5:
                    // Guardo la cuenta y salgo
                    guardarCuenta(cuenta);
                    System.out.println("¡Hasta luego!");
                    break;
                default:
                    // Opción no válida
                    System.out.println("Opción no válida.");
            }
        } while (opcion != 5);
    }

    private static Cuenta cargarCuenta() {
        File file = new File(FILE_PATH);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                // Leo la cuenta desde el archivo
                return (Cuenta) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        // Si no existe el archivo, creo una cuenta nueva
        return new Cuenta(new Cliente("Juan Perez", "12345678A"));
    }

    private static void guardarCuenta(Cuenta cuenta) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            // Guardo la cuenta en el archivo
            oos.writeObject(cuenta);
            System.out.println("Cuenta guardada correctamente.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}