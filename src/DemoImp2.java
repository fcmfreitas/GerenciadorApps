public class DemoImp2 {
    public static void main(String args[]){
        CatalogoClientes cc = new CatalogoClientes();
        cc.loadFromFile();
        cc.getStream()
        .forEach(c->System.out.println(c.toLineFile()));
        cc.cadastra(new Cliente("028.065.740-48", "tonycoelho04@gmail.com", "Antônio Coelho"));
        cc.saveToFile();
    }
}
