public class DemoImp {
    public static void main(String args[]){
        CatalogoAplicativos  ca = new CatalogoAplicativos();
        ca.loadFromFile();
        ca.getStream()
        .forEach(app->System.out.println(app.toLineFile()));
        ca.cadastra(new Aplicativo(400,"Desenho Magico", 9.50, Aplicativo.SO.Android));
        ca.saveToFile();
    }
}
