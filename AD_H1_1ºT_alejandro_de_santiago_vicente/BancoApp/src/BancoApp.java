import java.io.*; // Importo las clases necesarias para manejar archivos
import java.util.Scanner; // Importo la clase Scanner para leer la entrada del usuario
import java.util.InputMismatchException; // Importo la excepción para manejar errores de entrada
import java.util.ArrayList; // Importo la clase ArrayList para manejar listas dinámicas

public class BancoApp {
    // Defino la ruta del archivo donde se guardará la cuenta
    private static final String FILE_PATH = "cuenta.dat";
    // Defino la ruta del archivo donde se guardarán los clientes
    private static final String CLIENTES_FILE_PATH = "clientes.dat";
    // Defino constantes para los colores ANSI que usaré en la consola
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    public static void main(String[] args) {
        // Cargo la lista de clientes desde el archivo o creo una nueva si no existe
        ArrayList<Cliente> clientes = cargarClientes();
        // Creo un objeto Scanner para leer la entrada del usuario
        Scanner scanner = new Scanner(System.in);
        
        // Solicito al usuario que seleccione un cliente o cree uno nuevo
        Cliente cliente = seleccionarCliente(clientes, scanner);
        
        // Cargo la cuenta del cliente seleccionado desde el archivo o creo una nueva si no existe
        Cuenta cuenta = cargarCuenta(cliente);
        
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
                    ingresarDinero(cuenta, scanner, cliente.getNombre()); // Ingreso dinero en la cuenta
                    break;
                case 2:
                    retirarDinero(cuenta, scanner, cliente.getNombre()); // Retiro dinero de la cuenta
                    break;
                case 3:
                    mostrarSaldo(cuenta, cliente.getNombre()); // Muestro el saldo de la cuenta
                    break;
                case 4:
                    mostrarMovimientos(cuenta, cliente.getNombre()); // Muestro los movimientos de la cuenta
                    break;
                case 5:
                    mostrarClientes(clientes); // Muestro la lista de clientes
                    break;
                case 6:
                    guardarCuenta(cuenta); // Guardo la cuenta en el archivo
                    guardarClientes(clientes); // Guardo la lista de clientes en el archivo
                    System.out.println(ANSI_GREEN + "¡Hasta luego, " + cliente.getNombre() + "!" + ANSI_RESET); // Despedida
                    break;
                default:
                    System.out.println(ANSI_RED + "Opción no válida." + ANSI_RESET); // Opción no válida
            }
        } while (opcion != 6); // El bucle se repite hasta que el usuario elija salir
        scanner.close(); // Cierro el Scanner
    }

    // Método para mostrar el menú de opciones
    private static void mostrarMenu() {
        System.out.println(ANSI_YELLOW + "\n1. Ingresar dinero");
        System.out.println("2. Retirar dinero");
        System.out.println("3. Mostrar saldo");
        System.out.println("4. Mostrar movimientos");
        System.out.println("5. Ver clientes");
        System.out.println("6. Salir");
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
    private static void ingresarDinero(Cuenta cuenta, Scanner scanner, String usuario) {
        System.out.print("Cantidad a ingresar: ");
        double ingreso = leerCantidad(scanner); // Leo la cantidad a ingresar
        if (ingreso > 0) {
            cuenta.ingresar(ingreso); // Ingreso la cantidad en la cuenta
            System.out.println(ANSI_GREEN + "Ingreso realizado con éxito, " + usuario + "." + ANSI_RESET);
        } else {
            System.out.println(ANSI_RED + "La cantidad a ingresar debe ser positiva, " + usuario + "." + ANSI_RESET);
        }
    }

    // Método para retirar dinero de la cuenta
    private static void retirarDinero(Cuenta cuenta, Scanner scanner, String usuario) {
        System.out.print("Cantidad a retirar: ");
        double retirada = leerCantidad(scanner); // Leo la cantidad a retirar
        if (retirada > 0) {
            if (cuenta.retirar(retirada)) { // Intento retirar la cantidad de la cuenta
                System.out.println(ANSI_GREEN + "Retiro realizado con éxito, " + usuario + "." + ANSI_RESET);
            } else {
                System.out.println(ANSI_RED + "Saldo insuficiente para realizar el retiro, " + usuario + "." + ANSI_RESET);
            }
        } else {
            System.out.println(ANSI_RED + "La cantidad a retirar ha de ser positiva, " + usuario + "." + ANSI_RESET);
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
    private static void mostrarSaldo(Cuenta cuenta, String usuario) {
        System.out.printf(ANSI_GREEN + "Saldo actual: %.2f€\n" + ANSI_RESET, cuenta.getSaldo());
    }

    // Método para mostrar los movimientos de la cuenta
    private static void mostrarMovimientos(Cuenta cuenta, String usuario) {
        System.out.println(ANSI_YELLOW + "Movimientos:" + ANSI_RESET);
        for (Movimiento m : cuenta.getMovimientos()) {
            System.out.printf("%s: %.2f€\n", m.getTipo(), m.getCantidad());
        }
    }

    // Método para mostrar la lista de clientes
    private static void mostrarClientes(ArrayList<Cliente> clientes) {
        System.out.println(ANSI_YELLOW + "Clientes:" + ANSI_RESET);
        for (Cliente c : clientes) {
            System.out.println(c);
        }
    }

    // Método para cargar la cuenta desde el archivo
    private static Cuenta cargarCuenta(Cliente cliente) {
        File file = new File(FILE_PATH); // Creo un objeto File con la ruta del archivo
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (Cuenta) ois.readObject(); // Intento leer la cuenta del archivo
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(ANSI_RED + "Error al cargar la cuenta. Creando una nueva." + ANSI_RESET);
            }
        }
        return new Cuenta(cliente); // Si no existe el archivo, creo una nueva cuenta
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

    // Método para cargar la lista de clientes desde el archivo
    private static ArrayList<Cliente> cargarClientes() {
        File file = new File(CLIENTES_FILE_PATH); // Creo un objeto File con la ruta del archivo
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                return (ArrayList<Cliente>) ois.readObject(); // Intento leer la lista de clientes del archivo
            } catch (IOException | ClassNotFoundException e) {
                System.out.println(ANSI_RED + "Error al cargar la lista de clientes. Creando una nueva." + ANSI_RESET);
            }
        }
        return new ArrayList<>(); // Si no existe el archivo, creo una nueva lista de clientes
    }

    // Método para guardar la lista de clientes en el archivo
    private static void guardarClientes(ArrayList<Cliente> clientes) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(CLIENTES_FILE_PATH))) {
            oos.writeObject(clientes); // Intento guardar la lista de clientes en el archivo
            System.out.println(ANSI_GREEN + "Lista de clientes guardada correctamente." + ANSI_RESET);
        } catch (IOException e) {
            System.out.println(ANSI_RED + "Error al guardar la lista de clientes." + ANSI_RESET);
            e.printStackTrace();
        }
    }

    // Método para seleccionar un cliente existente o crear uno nuevo
    private static Cliente seleccionarCliente(ArrayList<Cliente> clientes, Scanner scanner) {
        System.out.println(ANSI_YELLOW + "Seleccione un cliente existente o cree uno nuevo:" + ANSI_RESET);
        for (int i = 0; i < clientes.size(); i++) {
            System.out.println((i + 1) + ". " + clientes.get(i).getNombre());
        }
        System.out.println((clientes.size() + 1) + ". Crear nuevo cliente");
        int opcion = leerOpcion(scanner);
        if (opcion > 0 && opcion <= clientes.size()) {
            return clientes.get(opcion - 1); // Devuelvo el cliente seleccionado
        } else if (opcion == clientes.size() + 1) {
            Cliente nuevoCliente = crearNuevoCliente(scanner);
            clientes.add(nuevoCliente); // Agrego el nuevo cliente a la lista
            return nuevoCliente; // Devuelvo el nuevo cliente
        } else {
            System.out.println(ANSI_RED + "Opción no válida." + ANSI_RESET);
            return seleccionarCliente(clientes, scanner); // Vuelvo a solicitar la selección
        }
    }

    // Método para crear un nuevo cliente
    private static Cliente crearNuevoCliente(Scanner scanner) {
        System.out.print("Por favor, ingresa el nombre del cliente: ");
        String nombre = scanner.next();
        System.out.print("Ingresa el DNI del cliente: ");
        String dni = scanner.next();
        return new Cliente(nombre, dni); // Devuelvo el nuevo cliente
    }
}