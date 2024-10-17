import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import com.google.gson.Gson;
import java.util.Map;
import java.util.stream.Collectors;

public class APICliente {
    private static final String API_KEY = "2754d54da64211f4b5ce39ee"; // Tu API key
    private static final String API_URL = "https://v6.exchangerate-api.com/v6/" + API_KEY + "/latest/USD"; // URL de la API
    private final Gson gson = new Gson(); // Crear una instancia de Gson

    // obtiene la tasa de cambio de una moneda dada
    public double obtenerTasaDeCambio(String monedaObjetivo) throws Exception {
        HttpClient client = HttpClient.newHttpClient(); // Crear cliente HttpClient

        // Crear solicitud HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Accept", "application/json")
                .header("User-Agent", "Java HttpClient")
                .GET()
                .build();

        //Enviar la solicitud y recibir respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verificar el código de estado
        int statusCode = response.statusCode();
        if (statusCode == 200) {
            // Imprimir el cuerpo de la respuesta para depuración
            System.out.println("Cuerpo de la respuesta: " + response.body());

            // Parsear la respuesta JSON usando Gson
            TasaCambioResponse tasaCambioResponse = gson.fromJson(response.body(), TasaCambioResponse.class);

            // Verificar si la respuesta fue exitosa
            if (!"success".equals(tasaCambioResponse.getResult())) {
                throw new RuntimeException("Error en la API: " + tasaCambioResponse.getDocumentation());
            }

            // Asegurar de que conversion_rates no sea null
            if (tasaCambioResponse.getConversion_rates() == null) {
                throw new RuntimeException("No se recibieron tasas de cambio.");
            }

            return tasaCambioResponse.getConversion_rates().get(monedaObjetivo); // Extraer la tasa de cambio
        } else {
            throw new RuntimeException("Error en la solicitud: " + statusCode);
        }
    }

    //obtener todas las tasas de cambio
    public Map<String, Double> obtenerTodasLasTasas() throws Exception {
        HttpClient client = HttpClient.newHttpClient(); // Crear cliente HttpClient

        //Crear solicitud HttpRequest
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Accept", "application/json")
                .header("User-Agent", "Java HttpClient")
                .GET()
                .build();

        //Enviar la solicitud y recibir respuesta
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        // Verificar el código de estado
        int statusCode = response.statusCode();
        if (statusCode == 200) {
            // Imprimir el cuerpo de la respuesta para depuración
            System.out.println("Cuerpo de la respuesta: " + response.body());

            //Parsear la respuesta JSON usando Gson
            TasaCambioResponse tasaCambioResponse = gson.fromJson(response.body(), TasaCambioResponse.class);

            //Verificar si la respuesta fue exitosa
            if (!"success".equals(tasaCambioResponse.getResult())) {
                throw new RuntimeException("Error en la API: " + tasaCambioResponse.getDocumentation());
            }

            //Asegurar de que conversion_rates no sea null
            if (tasaCambioResponse.getConversion_rates() == null) {
                throw new RuntimeException("No se recibieron tasas de cambio.");
            }

            return tasaCambioResponse.getConversion_rates(); // Retornar todas las tasas
        } else {
            throw new RuntimeException("Error en la solicitud: " + statusCode);
        }
    }
}
