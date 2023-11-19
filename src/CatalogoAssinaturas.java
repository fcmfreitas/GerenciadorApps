import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CatalogoAssinaturas {
    private List<Assinatura> assinaturas;

    public CatalogoAssinaturas() {
        assinaturas = new ArrayList<>();
    }

    public void cadastra(Assinatura s) {
        assinaturas.add(s);
    }

    public void remove(Assinatura s) {
        assinaturas.remove(s);
    }

    public void gerenciarAssinatura(Assinatura s) {
        if (assinaturaAtiva(s)) {
            cancelaAssinatura(s);
        } else {
            cadastra(s);
        }
    }

    private boolean assinaturaAtiva(Assinatura s) {
        List<Assinatura> sAtiva = assinaturas.stream()
            .filter(a -> a.getCpfCliente().equals(s.getCpfCliente()) &&
                    a.getCodigoApp() == s.getCodigoApp() &&
                    a.getFim().equals("00"))
            .collect(Collectors.toList());  
        return !sAtiva.isEmpty();
    }

    private void cancelaAssinatura(Assinatura s) {
        for (Assinatura a : assinaturas) {
            if (a.getCpfCliente().equals(s.getCpfCliente()) &&
                a.getCodigoApp() == s.getCodigoApp() &&
                a.getFim().equals("00")) {
                    a.setFim(getData());
                    break;
                }
        }
    }

    public void removeAssinatura(int codigo){
        assinaturas.removeIf(assinatura -> codigo == assinatura.getCodigo());
        saveToFile();
    }


    private String getData() {
        LocalDate dataAtual = LocalDate.now();
        return String.format("%02d", dataAtual.getMonthValue());
    }

    public Assinatura getAssinaturaNaLinha(int linha) {
        if (linha >= assinaturas.size()) {
            return null;
        }
        return assinaturas.get(linha);
    }

    public int getQtdade() {
        return assinaturas.size();
    }

    public Stream<Assinatura> getStream() {
        return assinaturas.stream();
    }

    public void loadFromFile() {
        Path assinaturasFilePath = Path.of("assinaturas.dat");
        try (Stream<String> appsStream = Files.lines(assinaturasFilePath)) {
            appsStream.forEach(str -> assinaturas.add(Assinatura.fromLineFile(str)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile() {
        Path assinaturasFilePath = Path.of("assinaturas.dat");
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(assinaturasFilePath, StandardCharsets.UTF_8))) {
            for (Assinatura s : assinaturas) {
                writer.println(s.toLineFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
