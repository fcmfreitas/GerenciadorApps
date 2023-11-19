import java.awt.*;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;

public class App {
    private CatalogoAplicativos catApps;
    private CatalogoClientes catClientes;
    private CatalogoAssinaturas catAssinaturas;
    private CatalogoAplicativosViewModel catAppsVM;
    private CatalogoClientesViewModel catClientesVM;
    private CatalogoAssinaturasViewModel catAssinaturasVM;
    private JTextField tfCpf;
    private JTextField tfNome;
    private JTextField tfEmail;
    private JTextField tfInicio;
    private JTextField tfFim;
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
        
        JPanel painel = new JPanel();
    
        //painel01.setBackground(Color.magenta);

        painel.setPreferredSize(new Dimension(100,100));
        
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
        JLabel tituloMenu = new JLabel("<html><h1><strong><i>Menu</i></strong></h1><hr></html>");
        

        //sistema de grade e centralização dos elementos
        painel.setLayout(new GridBagLayout());
        GridBagConstraints centraliza = new GridBagConstraints();
        
        centraliza.insets = new Insets(15, 15, 15, 15);

        centraliza.gridx = 0;
        centraliza.gridy = 0;
        painel.add(tituloMenu, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 1;
        painel.add(btClientes, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 2;
        painel.add(btApps, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 3;
        painel.add(btAssinaturas, centraliza);

        return painel;
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
        // linha0
        JPanel linha0 = new JPanel();
        JScrollPane scrollPane = new JScrollPane(tabela,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        linha0.add(scrollPane);
        // linha1
        JPanel linha1 = new JPanel(); 
        linha1.add(new JLabel("Codigo"));
        tfCpf = new JTextField(10);
        linha1.add(tfCpf);
        linha1.add(new JLabel("Nome"));
        tfNome = new JTextField(20);
        linha1.add(tfNome);
        // linha2
        JPanel linha2 = new JPanel();
        linha2.add(new JLabel("Licença"));
        tfEmail = new JTextField(10);
        linha2.add(tfEmail);
        linha2.add(new JLabel("Sist. Oper."));
        cbSo = new JComboBox<>(Aplicativo.SO.values());
        linha2.add(cbSo);
        btAdd = new JButton("Novo App");
        linha2.add(btAdd);
        // linha3
        JPanel linha3 = new JPanel();
        JButton btRemover = new JButton("Remover");
        JTextField codigoRemover = new JTextField(10);
        JButton voltar = new JButton("Voltar e Salvar");
        linha3.add(new JLabel("Digite o código para remover:"));
        linha3.add(codigoRemover);
        linha3.add(btRemover);
        JButton btLista = new JButton("Lista de Assinantes");

        btRemover.addActionListener(e -> {
            catApps.removeApp(Integer.parseInt(codigoRemover.getText()));
            trocarPainel(painelApps());
        });
        voltar.addActionListener(e -> {
            trocarPainel(painelMenu());
            catApps.saveToFile();
        });
        
        //configura painel em grade
        painel.setLayout(new GridBagLayout());
        GridBagConstraints centraliza = new GridBagConstraints();
        centraliza.insets = new Insets(5, 5, 5, 5);

        centraliza.gridx = 0;
        centraliza.gridy = 0;
        painel.add(linha0, centraliza);
      
        centraliza.gridx = 0;
        centraliza.gridy = 2;
        painel.add(linha1, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 3;
        painel.add(linha2, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 4;

        painel.add(linha3, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 5;

        painel.add(voltar, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 6;
        
        painel.add(btLista, centraliza);
    
        return painel;
    }

    public JPanel painelClientes() {
        
        catClientesVM = new CatalogoClientesViewModel(catClientes);
        JTable tabela = new JTable(catClientesVM);
        tabela.setFillsViewportHeight(true);

        JPanel painel = new JPanel();
        // linha0
        JPanel linha0 = new JPanel();
        JScrollPane scrollPane = new JScrollPane(tabela,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        linha0.add(scrollPane);
        // linha1
        JPanel linha1 = new JPanel(); 
        linha1.add(new JLabel("CPF"));
        tfCpf = new JTextField(11);
        linha1.add(tfCpf);
        linha1.add(new JLabel("Nome"));
        tfNome = new JTextField(15);
        linha1.add(tfNome);
        // linha2
        JPanel linha2 = new JPanel();
        linha2.add(new JLabel("Email"));
        tfEmail = new JTextField(20);
        linha2.add(tfEmail);
        btAdd = new JButton("Novo Cliente");
        btAdd.addActionListener(e->adicionaCliente());
        linha2.add(btAdd);
        // linha3
        JPanel linha3 = new JPanel();
        JButton btRemover = new JButton("Remover");
        JTextField cpfRemover = new JTextField(10);
        JButton voltar = new JButton("Voltar e Salvar");
        linha3.add(new JLabel("Digite o CPF para remover:"));
        linha3.add(cpfRemover);
        linha3.add(btRemover);

        btRemover.addActionListener(e -> {
            catClientes.removeCliente(cpfRemover.getText());
            trocarPainel(painelClientes());
        });
        voltar.addActionListener(e -> {
            trocarPainel(painelMenu());
            catClientes.saveToFile();
        });
        
        //configura painel em grade
        painel.setLayout(new GridBagLayout());
        GridBagConstraints centraliza = new GridBagConstraints();
        centraliza.insets = new Insets(5, 5, 5, 5);

        centraliza.gridx = 0;
        centraliza.gridy = 0;
        painel.add(linha0, centraliza);
      
        centraliza.gridx = 0;
        centraliza.gridy = 2;
        painel.add(linha1, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 3;
        painel.add(linha2, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 4;

        painel.add(linha3, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 5;

        painel.add(voltar, centraliza);
    
        return painel;
    }
    
    public JPanel painelAssinaturas() {
        catAssinaturasVM = new CatalogoAssinaturasViewModel(catAssinaturas);
        JTable tabela = new JTable(catAssinaturasVM);
        tabela.setFillsViewportHeight(true);

        JPanel painel = new JPanel();
        // linha0
        JPanel linha0 = new JPanel();
        JScrollPane scrollPane = new JScrollPane(tabela,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,
        JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        linha0.add(scrollPane);
        // linha1
        JPanel linha1 = new JPanel(); 
        linha1.add(new JLabel("Codigo"));
        tfEmail = new JTextField(10);
        linha1.add(tfEmail);
        linha1.add(new JLabel("Codigo do App"));
        tfNome = new JTextField(10);
        linha1.add(tfNome);
        // linha2
        JPanel linha2 = new JPanel();
        linha2.add(new JLabel("CPF do cliente"));
        tfCpf = new JTextField(11);
        linha2.add(tfCpf);
        // linha 3
        JPanel linha3 = new JPanel();
        linha3.add(new JLabel("Inicio da vigencia:"));
        tfInicio = new JTextField(5);
        linha3.add(tfInicio);
        linha3.add(new JLabel("Fim da vigencia"));
        tfFim = new JTextField(5);
        linha3.add(tfFim);
        

        btAdd = new JButton("Nova Assinatura");
        btAdd.addActionListener(e->adicionaAssinatura());
        linha3.add(btAdd);
        //linha4
        JPanel linha4 = new JPanel();
        JButton btRemover = new JButton("Cancelar Assinatura");
        JTextField codigoRemovido = new JTextField(11);
        JButton voltar = new JButton("Voltar e Salvar");
        linha4.add(new JLabel("Digite o codigo da assinatura para cancelar:"));
        linha4.add(codigoRemovido);
        linha4.add(btRemover);

        btRemover.addActionListener(e -> {
            catAssinaturas.removeAssinatura(Integer.parseInt(codigoRemovido.getText()));
            trocarPainel(painelAssinaturas());
        });
        voltar.addActionListener(e -> {
            trocarPainel(painelMenu());
            catAssinaturas.saveToFile();
        });
        
        //configura painel em grade
        painel.setLayout(new GridBagLayout());
        GridBagConstraints centraliza = new GridBagConstraints();
        centraliza.insets = new Insets(5, 5, 5, 5);

        centraliza.gridx = 0;
        centraliza.gridy = 0;
        painel.add(linha0, centraliza);
      
        centraliza.gridx = 0;
        centraliza.gridy = 2;
        painel.add(linha1, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 3;
        painel.add(linha2, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 4;

        painel.add(linha3, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 5;

        painel.add(linha4, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 6;

        painel.add(voltar, centraliza);
    
        return painel;
    }
    
    public void adicionaApp(){
        int codigo = Integer.parseInt(tfCpf.getText());
        String nome = tfNome.getText();
        double preco = Double.parseDouble(tfEmail.getText());
        Aplicativo.SO so = (Aplicativo.SO)cbSo.getSelectedItem();
        Aplicativo novo = new Aplicativo(codigo, nome, preco, so);
        catApps.cadastra(novo);
        catAppsVM.fireTableDataChanged();
    }

    public void adicionaCliente() {
        String cpf = tfCpf.getText();
        String email = tfEmail.getText();
        String nome = tfNome.getText();
        Cliente novo = new Cliente(cpf, email, nome);
        catClientes.cadastra(novo);
        catClientesVM.fireTableDataChanged();
    }

    public void adicionaAssinatura() {
        int codigo = Integer.parseInt(tfEmail.getText());
        int codigoApp = Integer.parseInt(tfNome.getText());
        String cpfCliente = tfCpf.getText();
        String inicio = tfInicio.getText();
        String fim = tfFim.getText();
        Assinatura nova = new Assinatura(codigo, codigoApp, cpfCliente, inicio, fim);
        catAssinaturas.cadastra(nova);
        catAssinaturasVM.fireTableDataChanged();
    }
    public static void main(String[] args) throws Exception {
        App app = new App();
    }
}
