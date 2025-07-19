import ClassesEObjetos.Conversor;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Scanner;
import com.google.gson.Gson;
import java.util.Map;
import java.util.HashMap;

public class Principal {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        HttpClient client = HttpClient.newHttpClient();
        Gson gson = new Gson();

        Map<Integer, String[]> opcoes = new HashMap<>();
        opcoes.put(1, new String[]{"USD", "ARS"});
        opcoes.put(2, new String[]{"ARS", "USD"});
        opcoes.put(3, new String[]{"USD", "BRL"});
        opcoes.put(4, new String[]{"BRL", "USD"});
        opcoes.put(5, new String[]{"USD", "COP"});
        opcoes.put(6, new String[]{"COP", "USD"});

        while(true){
            System.out.println("Bem-Vindo ao nosso conversor de moedas!");
            System.out.println("Informe o valor que deseja converter: ");
            double valor = scanner.nextDouble();
            
            System.out.println("Escolha o número correspondente à conversão:");
            System.out.println("1 - Dólar -> Peso Argentino");
            System.out.println("2 - Peso Argentino -> Dólar");
            System.out.println("3 - Dólar -> Real Brasileiro");
            System.out.println("4 - Real Brasileiro -> Dólar");
            System.out.println("5 - Dólar -> Peso Colombiano");
            System.out.println("6 - Peso Colombiano -> Dólar");
            System.out.println("7 - Sair");

            int escolha = scanner.nextInt();

            if (escolha == 7){
                System.out.println("Encerrando o programa. Até a próxima!! ;)");
                break; //encerra o programa
            }

            if (!opcoes.containsKey(escolha)) {
                System.out.println("Opção inválida. Tente novamente!\n");
                continue;
            }

            String moedaOrigem = opcoes.get(escolha)[0];
            String moedaDestino = opcoes.get(escolha)[1];

            //requisução
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://v6.exchangerate-api.com/v6/aaffae9562e48b26a08a0ab5/latest/" + moedaOrigem))
                .header("Accept", "application/json")
                .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            Conversor conversor = gson.fromJson(response.body(), Conversor.class);

            double taxa = conversor.getConversionRates().get(moedaDestino);
            double resultado = valor * taxa;

            System.out.printf("Valor convertido de %s para %s: %.2f%n", moedaOrigem, moedaDestino, resultado);
        }
    }
}
