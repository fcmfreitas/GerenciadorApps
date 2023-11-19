public class ArquivoAssinaturas {
    public static void main(String args[]){
        CatalogoAssinaturas ca = new CatalogoAssinaturas();
        ca.loadFromFile();
        ca.getStream()
        .forEach(c->System.out.println(c.toLineFile()));
        ca.cadastra(new Assinatura(020, 66, "028.065.740-48", "11/23" , "00/00"));
        ca.saveToFile();
    }
}
