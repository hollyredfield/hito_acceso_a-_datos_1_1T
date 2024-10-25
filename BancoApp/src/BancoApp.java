import java.io.*; // Importo las clases necesarias para manejar archivos
import java.util.Scanner; // Importo la clase Scanner para leer la entrada del usuario
import java.util.InputMismatchException; // Importo la excepción para manejar errores de entrada

public class BancoApp {
    // Defino la ruta del archivo donde se guardará la cuenta
    private static final String FILE_PATH = "cuenta.dat";
    // Defino constantes para los colores ANSI que usaré en la consola
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        // Cargo la cuenta desde el archivo o creo una nueva si no existe
        Cuenta cuenta = cargarCuenta();
        // Creo un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);
        int opcion;

        // Bucle principal del programa
        do {
            // Muestro el menú de opciones
            mostrarMenu();
            // Leo la opción seleccionada por el usuario
            opcion = leerOpcion(scanner);

            // Ejecuto la acción correspondiente a la opción seleccionada
            switch (opcion) {
                case 1:
                    ingresarDinero(cuenta, scanner); // Ingreso dinero en la cuenta
                    break;
                case 2:
                    retirarDinero(cuenta, scanner); // Retiro dinero de la cuenta
                    break;
                case 3:
                    mostrarSaldo(cuenta); // Muestro el saldo de la cuenta
                    break;
                case 4:
                    mostrarMovimientos(cuenta); // Muestro los movimientos de la cuenta
                    break;
                case 5:
                    guardarCuenta(cuenta); // Guardo la cuenta en el archivo
                    System.out.println(ANSI_GREEN + "¡Hasta luego!" + ANSI_RESET); // Despedida
                    break;
                default:
                    System.out.println(ANSI_RED + "Opción no válida." + ANSI_RESET); // Opción no válida
            }
        } while (opcion != 5); // El bucle se repite hasta que el usuario elija salir
        scanner.close(); // Cierro el Scanner
    }

    // Método para mostrar el menú de opciones
    private static void mostrarMenu() {
        System.out.println(ANSI_YELLOW + "\n1. Ingresar dinero");
        System.out.println("2. Retirar dinero");
        System.out.println("3. Mostrar saldo");
        System.out.println("4. Mostrar movimientos");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: " + ANSI_RESET);
    }

    // Método para leer la opción seleccionada por el usuario
    private static int leerOpcion(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextInt(); // Intento leer un entero
            } catch (InputMismatchException e) {
                System.out.print(ANSI_RED + "Por favor, ingrese un número válido: " + ANSI_RESET);
                scanner.next(); // Limpio el buffer del scanner
            }
        }
    }

    // Método para ingresar dinero en la cuenta
    private static void ingresarDinero(Cuenta cuenta, Scanner scanner) {
        System.out.print("Cantidad a ingresar: ");
        double ingreso = leerCantidad(scanner); // Leo la cantidad a ingresar
        if (ingreso > 0) {
            cuenta.ingresar(ingreso); // Ingreso la cantidad en la cuenta
            System.out.println(ANSI_GREEN + "Ingreso realizado con éxito." + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "La cantidad a ingresar debe ser positiva." + ANSI_RESET);
        }
    }

    // Método para retirar dinero de la cuenta
    private static void retirarDinero(Cuenta cuenta, Scanner scanner) {
        System.out.print("Cantidad a retirar: ");
        double retirada = leerCantidad(scanner); // Leo la cantidad a retirar
        if (retirada > 0) {
            if (cuenta.retirar(retirada)) { // Intento retirar la cantidad de la cuenta
                System.out.println(ANSI_GREEN + "Retiro realizado con éxito." + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Saldo insuficiente para realizar el retiro." + ANSI_RESET);
            }
        } else {
            System.out.println(ANSI_RED + "La cantidad a retirar ha de ser positiva." + ANSI_RESET);
        }
    }

    // Método para leer una cantidad de dinero
    private static double leerCantidad(Scanner scanner) {
        while (true) {
            try {
                return scanner.nextDouble(); // Intento leer un double
            } catch (InputMismatchException e) {
                System.out.print(ANSI_RED + "Por favor, ingresa una cantidad válida: " + ANSI_RESET);
                scanner.next(); // Limpio el buffer del scanner
            }
        }
    }

    // Método para mostrar el saldo de la cuenta
    private static void mostrarSaldo(Cuenta cuenta) {
        System.out.printf(ANSI_GREEN + "Saldo actual: %.2f€\n" + ANSI_RESET, cuenta.getSaldo());
    }

    // Método para mostrar los movimientos de la cuenta
    private static void mostrarMovimientos(Cuenta cuenta) {
        System.out.println(ANSI_YELLOW + "Movimientos:" + ANSI_RESET);
        for (Movimiento m : cuenta.getMovimientos()) {
            System.out.printf("%s: %.2f€\n", m.getTipo(), m.getCantidad());
        }
    }

    // Método para cargar la cuenta desde el archivo
    private static Cuenta cargarCuenta() {
        File file = new File(FILE_PATH); // Creo un objeto File con la ruta del archivo
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (Cuenta) ois.readObject(); // Intento leer la cuenta del archivo
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(ANSI_RED + "Error al cargar la cuenta. Creando una nueva." + ANSI_RESET);
            }
        }
        return crearNuevaCuenta(); // Si no existe el archivo, creo una nueva cuenta
    }

    // Método para crear una nueva cuenta
    private static Cuenta crearNuevaCuenta() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Por favor, ingresa el nombre del cliente: ");
        String nombre = scanner.nextLine(); // Leo el nombre del cliente
        System.out.print("Ingresa el DNI del cliente: ");
        String dni = scanner.nextLine(); // Leo el DNI del cliente
        return new Cuenta(new Cliente(nombre, dni)); // Creo una nueva cuenta con los datos del cliente
    }

    // Método para guardar la cuenta en el archivo
    private static void guardarCuenta(Cuenta cuenta) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(cuenta); // Intento guardar la cuenta en el archivo
            System.out.println(ANSI_GREEN + "Cuenta guardada correctamente." + ANSI_RESET);
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error al guardar la cuenta." + ANSI_RESET);
            e.printStackTrace();
        }
    }
}