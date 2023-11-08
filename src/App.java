import java.awt.Container;
import java.awt.FlowLayout;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;


// Mais informações em: https://docs.oracle.com/javase/tutorial/uiswing/components/table.html#data

public class App {
    private CatalogoAplicativos catApps;
    private CatalogoAplicativosViewModel catAppsVM;
    private JTextField tfCodigo;
    private JTextField tfNome;
    private JTextField tfPreco;
    private JComboBox<Aplicativo.SO> cbSo;
    private JButton btAdd;

    public App(){
        catApps = new CatalogoAplicativos();
        catApps.loadFromFile();; 
    }

    public void criaJanela() throws Exception {
        catAppsVM = new CatalogoAplicativosViewModel(catApps);
        JTable tabela = new JTable(catAppsVM);
        tabela.setFillsViewportHeight(true);

        JFrame frame = new JFrame("Gestão de aplicativos");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(tabela,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        linha1.add(scrollPane);

        JPanel jpNovoApp = criaPainelNovoApp();

        JPanel linha3 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton btSave = new JButton("Salvar dados");
        btSave.addActionListener(e->catApps.saveToFile());
        linha3.add(btSave);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.PAGE_AXIS));
        contentPane.add(linha1);
        contentPane.add(jpNovoApp);
        contentPane.add(linha3);
    
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public JPanel criaPainelNovoApp(){
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel,BoxLayout.PAGE_AXIS));

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        linha1.add(new JLabel("Codigo"));
        tfCodigo = new JTextField(10);
        linha1.add(tfCodigo);
        linha1.add(new JLabel("Nome"));
        tfNome = new JTextField(20);
        linha1.add(tfNome);
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        linha2.add(new JLabel("Licença"));
        tfPreco = new JTextField(10);
        linha2.add(tfPreco);
        linha2.add(new JLabel("Sist. Oper."));
        cbSo = new JComboBox<>(Aplicativo.SO.values());
        linha2.add(cbSo);
        btAdd = new JButton("Novo App");
        btAdd.addActionListener(e->adicionaApp());
        linha2.add(btAdd);

        painel.add(linha1);
        painel.add(linha2);
        return painel;
    }

    public void adicionaApp(){
        int codigo = Integer.parseInt(tfCodigo.getText());
        String nome = tfNome.getText();
        double preco = Double.parseDouble(tfPreco.getText());
        Aplicativo.SO so = (Aplicativo.SO)cbSo.getSelectedItem();
        Aplicativo novo = new Aplicativo(codigo, nome, preco, so);
        catApps.cadastra(novo);
        catAppsVM.fireTableDataChanged();
    }

    public static void main(String[] args) throws Exception {
        App app = new App();
        app.criaJanela();
    }
}