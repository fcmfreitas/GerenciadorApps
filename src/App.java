import java.awt.*;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class App {
    private CatalogoAplicativos catApps;
    private CatalogoClientes catClientes;
    private CatalogoAssinaturas catAssinaturas;
    private CatalogoAplicativosViewModel catAppsVM;
    private CatalogoClientesViewModel catClientesVM;
    private CatalogoAssinaturasViewModel catAssinaturasVM;
    private JTextField tfCodigo;
    private JTextField tfNome;
    private JTextField tfPreco;
    private JComboBox<Aplicativo.SO> cbSo;
    private JButton btAdd;
    private JPanel painelAtual;
    private JFrame frame;

    public App(){
        
        catAssinaturas = new CatalogoAssinaturas();
        catApps = new CatalogoAplicativos();
        catClientes = new CatalogoClientes();
        
        catApps.loadFromFile();
        catClientes.loadFromFile();
        catAssinaturas.loadFromFile();

        this.frame = new JFrame("Gestão de aplicativos");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        this.frame.setSize(700, 800);
        this.painelAtual = painelMenu();
        this.frame.add(painelAtual, BorderLayout.CENTER);

        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

    }

    public JPanel painelMenu(){
        
        JPanel painel01 = new JPanel();
    
        //painel01.setBackground(Color.magenta);

        painel01.setPreferredSize(new Dimension(100,100));
        
        //cria o botão
        JButton btApps = new JButton("Gerenciar Apps");
        JButton btClientes = new JButton("Gerenciar Clientes");
        JButton btAssinaturas = new JButton("Gerenciar Assinaturas");
        btApps.setPreferredSize(new Dimension(200, 70));
        btClientes.setPreferredSize(new Dimension(200, 70));
        btAssinaturas.setPreferredSize(new Dimension(200, 70));
        btApps.addActionListener(b -> trocarPainel(painelApps()));
        btClientes.addActionListener(b -> trocarPainel(painelClientes()));
        btAssinaturas.addActionListener(b -> trocarPainel(painelAssinaturas()));
        
        //titulo
        JLabel menu = new JLabel("<html><h1><strong><i>Menu</i></strong></h1><hr></html>");
        

        //sistema de grade e centralização dos elementos
        painel01.setLayout(new GridBagLayout());
        GridBagConstraints centraliza = new GridBagConstraints();
        
        centraliza.insets = new Insets(15, 15, 15, 15);

        centraliza.gridx = 0;
        centraliza.gridy = 0;
        painel01.add(menu, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 1;
        painel01.add(btClientes, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 2;
        painel01.add(btApps, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 3;
        painel01.add(btAssinaturas, centraliza);

        return painel01;
    }

    public void trocarPainel(JPanel novoPainel) {
        this.frame.getContentPane().removeAll();
        this.frame.getContentPane().add(novoPainel, BorderLayout.CENTER);
        this.frame.revalidate();
        this.frame.repaint();
        this.painelAtual = novoPainel;
    }
    
    public JPanel painelApps() {
        
        catAppsVM = new CatalogoAplicativosViewModel(catApps);
        JTable tabela = new JTable(catAppsVM);
        tabela.setFillsViewportHeight(true);

        JPanel painel = new JPanel();

        JPanel linha1 = new JPanel();
        JPanel linha2 = new JPanel();
        JScrollPane scrollPane = new JScrollPane(tabela,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        linha1.add(scrollPane);

        JButton btRemover = new JButton("Remover");
        JTextField codigoRemover = new JTextField(10);
        JButton voltar = new JButton("Voltar e Salvar");
        linha2.add(new JLabel("Digite o código para remover:"));
        linha2.add(codigoRemover);
        linha2.add(btRemover);

        btRemover.addActionListener(e -> {
            catApps.removeApp(Integer.parseInt(codigoRemover.getText()));
            trocarPainel(painelApps());
        });
        voltar.addActionListener(e -> {
            trocarPainel(painelMenu());
            catApps.saveToFile();
        });

        JPanel nApp = criaPainelNovoApp();

        painel.setLayout(new GridBagLayout());
        GridBagConstraints centraliza = new GridBagConstraints();
        centraliza.insets = new Insets(5, 5, 5, 5);

        centraliza.gridx = 0;
        centraliza.gridy = 0;
        painel.add(linha1, centraliza);
      
        centraliza.gridx = 0;
        centraliza.gridy = 3;
        painel.add(nApp, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 4;

        painel.add(linha2, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 5;

        painel.add(voltar, centraliza);
    

        return painel;
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

    public JPanel painelClientes() {
        catClientesVM = new CatalogoClientesViewModel(catClientes);
        JTable tabela = new JTable(catClientesVM);

        JPanel painel = new JPanel();

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(tabela,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        linha1.add(scrollPane);


        JButton btSave = new JButton("Salvar dados");
        btSave.addActionListener(e->catClientes.saveToFile());

        JPanel clientes = criaPainelClientes();

        painel.setLayout(new GridBagLayout());
        GridBagConstraints centraliza = new GridBagConstraints();
        centraliza.insets = new Insets(5, 5, 5, 5);

        centraliza.gridx = 0;
        centraliza.gridy = 0;
        painel.add(tabela, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 1;
        painel.add(linha1, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 3;
        painel.add(clientes, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 4;
        painel.add(btSave, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 5;
        JButton voltar = new JButton("Voltar");
        painel.add(voltar, centraliza);
        voltar.addActionListener(e -> trocarPainel(painelMenu()));

        return painel;

    }

    public JPanel criaPainelClientes(){
        JPanel painel = new JPanel();
        painel.setLayout(new BoxLayout(painel,BoxLayout.PAGE_AXIS));

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        linha1.add(new JLabel("CPF"));
        tfCodigo = new JTextField(10);
        linha1.add(tfCodigo);
        linha1.add(new JLabel("Email"));
        tfNome = new JTextField(20);
        linha1.add(tfNome);
        JPanel linha2 = new JPanel(new FlowLayout(FlowLayout.LEADING));
        linha2.add(new JLabel("Nome"));
        tfPreco = new JTextField(10);
        linha2.add(tfPreco);
        btAdd = new JButton("Novo Cliente");
        btAdd.addActionListener(e->adicionaCliente());
        linha2.add(btAdd);

        painel.add(linha1);
        painel.add(linha2);
        return painel;
    }
    
    public JPanel painelAssinaturas() {
        catAssinaturasVM = new CatalogoAssinaturasViewModel(catAssinaturas);
        JTable tabela = new JTable(catAssinaturasVM);

        JPanel painel = new JPanel();

        JPanel linha1 = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JScrollPane scrollPane = new JScrollPane(tabela,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        linha1.add(scrollPane);


        JButton btSave = new JButton("Salvar dados");
        btSave.addActionListener(e->catAssinaturas.saveToFile());

        JPanel assinaturas = criaPainelAssinaturas();

        painel.setLayout(new GridBagLayout());
        GridBagConstraints centraliza = new GridBagConstraints();
        centraliza.insets = new Insets(5, 5, 5, 5);

        centraliza.gridx = 0;
        centraliza.gridy = 0;
        painel.add(tabela, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 1;
        painel.add(linha1, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 3;
        painel.add(assinaturas, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 4;
        painel.add(btSave, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 5;
        JButton voltar = new JButton("Voltar");
        painel.add(voltar, centraliza);
        voltar.addActionListener(e -> trocarPainel(painelMenu()));

        return painel;

    }

    public JPanel criaPainelAssinaturas() {
        JPanel painel = new JPanel();
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

    public void adicionaCliente() {
        String cpf = tfNome.getText();
        String email = tfNome.getText();
        String nome = tfNome.getText();
        Cliente novo = new Cliente(cpf, email, nome);
        catClientes.cadastra(novo);
        catClientesVM.fireTableDataChanged();
    }

    public void adicionaAssinatura() {
        int codigo = Integer.parseInt(tfCodigo.getText());
        int codigoApp = Integer.parseInt(tfCodigo.getText());
        String cpfCliente = tfNome.getText();
        String inicio = tfNome.getText();
        String fim = tfNome.getText();
        Assinatura nova = new Assinatura(codigo, codigoApp, cpfCliente, inicio, fim);
        catAssinaturas.cadastra(nova);
        catAssinaturasVM.fireTableDataChanged();
    }
    public static void main(String[] args) throws Exception {
        App app = new App();
    }
}
