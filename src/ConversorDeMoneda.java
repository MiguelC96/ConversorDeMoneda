import java.util.Map;
import java.util.Scanner;

public class ConversorDeMoneda {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            //Crear instancia de la clase APICliente
            APICliente apiClient = new APICliente();
            Map<String, Double> tasasDeCambio = apiClient.obtenerTodasLasTasas(); // Obtener todas las tasas de cambio

            if (tasasDeCambio == null || tasasDeCambio.isEmpty()) {
                System.out.println("No se pudieron obtener las tasas de cambio.");
                return;
            }

            boolean continuar = true;

            while (continuar) {
                // Mostrar el menú de opciones
                System.out.println("\n--- Menú de Conversión de Monedas ---");
                System.out.println("1. Convertir moneda");
                System.out.println("2. Ver lista de monedas disponibles");
                System.out.println("3. Salir");
                System.out.print("Seleccione una opción: ");
                int opcion = scanner.nextInt();

                switch (opcion) {
                    case 1:
                        //Convertir moneda
                        realizarConversion(scanner, tasasDeCambio);
                        break;
                    case 2:
                        // Listar monedas disponibles
                        System.out.println("Monedas disponibles: " + tasasDeCambio.keySet());
                        break;
                    case 3:
                        // Salir del programa
                        continuar = false;
                        System.out.println("Gracias por usar el conversor de monedas.");
                        break;
                    default:
                        System.out.println("Opción no válida. Inténtelo de nuevo.");
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Ocurrió un error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    //Realizar la conversión de moneda
    private static void realizarConversion(Scanner scanner, Map<String, Double> tasasDeCambio) {
        // Solicitar la moneda de origen y cantidad
        System.out.print("Ingrese la moneda de origen (por ejemplo, USD): ");
        String monedaOrigen = scanner.next().toUpperCase();
        System.out.print("Ingrese la cantidad a convertir: ");
        double cantidad = scanner.nextDouble();

        // Solicitar la moneda de destino
        System.out.print("Ingrese la moneda de destino (por ejemplo, EUR): ");
        String monedaDestino = scanner.next().toUpperCase();

        // Realizar la conversión si ambas monedas son válidas
        if (tasasDeCambio.containsKey(monedaOrigen) && tasasDeCambio.containsKey(monedaDestino)) {
            double cantidadConvertida = convertirMoneda(cantidad, monedaOrigen, monedaDestino, tasasDeCambio);
            System.out.printf("%.2f %s son %.2f %s%n", cantidad, monedaOrigen, cantidadConvertida, monedaDestino);
        } else {
            System.out.println("Moneda no válida. Por favor, intente nuevamente.");
        }
    }

    //forma de convertir la moneda
    private static double convertirMoneda(double cantidad, String monedaOrigen, String monedaDestino, Map<String, Double> tasasDeCambio) {
        double tasaOrigen = tasasDeCambio.get(monedaOrigen);
        double tasaDestino = tasasDeCambio.get(monedaDestino);
        return (cantidad / tasaOrigen) * tasaDestino;
    }
}