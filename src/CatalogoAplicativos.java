import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CatalogoAplicativos {
    private List<Aplicativo> aplicativos;

    public CatalogoAplicativos() {
        aplicativos = new ArrayList<>();
    }

    public void cadastra(Aplicativo p) {
        aplicativos.add(p);
    }

    public Aplicativo getProdutoNaLinha(int linha) {
        if (linha >= aplicativos.size()) {
            return null;
        }
        return aplicativos.get(linha);
    }

    public int getQtdade() {
        return aplicativos.size();
    }

    public Stream<Aplicativo> getStream() {
        return aplicativos.stream();
    }

    public void loadFromFile() {
        Path appsFilePath = Path.of("apps.dat");
        try (Stream<String> appsStream = Files.lines(appsFilePath)) {
            appsStream.forEach(str -> aplicativos.add(Aplicativo.fromLineFile(str)));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveToFile() {
        Path appsFilePath = Path.of("apps.dat");
        try (PrintWriter writer = new PrintWriter(Files.newBufferedWriter(appsFilePath, StandardCharsets.UTF_8))) {
            for (Aplicativo app : aplicativos) {
                writer.println(app.toLineFile());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
