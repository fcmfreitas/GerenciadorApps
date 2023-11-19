import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
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

    public void removeAssinatura(int codigo){
        assinaturas.removeIf(assinatura -> codigo == assinatura.getCodigo());
        saveToFile();
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
