import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
public class CatalogoClientes {
    private List<Cliente> clientes;

    public List<Cliente> getLista(){
        return clientes;
    }

    public CatalogoClientes() {
        clientes = new ArrayList<>();
    }

    public void cadastra(Cliente c) {
        clientes.add(c);
    }

    public void remove(Cliente c) {
        clientes.remove(c);
    }
    /*
    public ArrayList<String> gerarLista(Cliente cl){
        LocalDate dataAtual = LocalDate.now();
        String mes = String.format("%02d", dataAtual.getMonthValue());
        return clientes.stream()
        .filter(c -> c.getCpf().equals(cl.getCpf())
            .collect(Collectors.toList()));    
    }
    */

    public void removeCliente(String cpf){
        clientes.removeIf(cliente -> cpf.equals(cliente.getCpf()));
        saveToFile();
    }

    public Cliente getClientesNaLinha(int linha) {
        if (linha >= clientes.size()) {
            return null;
        }
        return clientes.get(linha);
    }
    public int getQtdade() {
        return clientes.size();
    }

    public Stream<Cliente> getStream() {
        return clientes.stream();
    }

    public void loadFromFile() {
        Path appsFilePath = Path.of("clientes.dat");
        try (Stream<String> appsStream = Files.lines(appsFilePath)) {
            appsStream.forEach(str -> clientes.add(Cliente.fromLineFile(str)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile() {
        Path appsFilePath = Path.of("clientes.dat");
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(appsFilePath, StandardCharsets.UTF_8))) {
            for (Cliente c : clientes) {
                writer.println(c.toLineFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
}
