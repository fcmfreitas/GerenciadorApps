import java.awt.*;
import javax.swing.*;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;

public class App {
    private CatalogoAplicativos catApps;
    private CatalogoClientes catClientes;
    private CatalogoAssinaturas catAssinaturas;
    private CatalogoAplicativosViewModel catAppsVM;
    private CatalogoClientesViewModel catClientesVM;
    private CatalogoAssinaturasViewModel catAssinaturasVM;
    private JTextField tfCodigo;
    private JTextField tfCpf;
    private JTextField tfCodApp;
    private JTextField tfInicio;
    private JTextField tfFim;
    private JComboBox<Aplicativo.SO> cbSo;
    private JButton btAdd;
    private JPanel painelAtual;
    private JFrame frame;
    private JFrame frame2;
    private JLabel tituloMenu;
    private boolean temaEscuro = false;
    private DefaultMutableTreeNode root;
    private DefaultMutableTreeNode root2;
    private DefaultMutableTreeNode root3;
    private DefaultTreeModel treeModel;
    private JTree tree;

    public App(){
        
        catAssinaturas = new CatalogoAssinaturas();
        catApps = new CatalogoAplicativos();
        catClientes = new CatalogoClientes();
        
        catApps.loadFromFile();
        catClientes.loadFromFile();
        catAssinaturas.loadFromFile();

        this.frame2 = new JFrame("Listas");
        this.frame = new JFrame("Gestão de aplicativos");
        this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.frame.setLayout(new BorderLayout());
        this.frame.setSize(700, 800);
        this.painelAtual = painelMenu();
        this.frame.add(painelAtual, BorderLayout.CENTER);

        this.frame.setLocationRelativeTo(null);
        this.frame.setVisible(true);

        JMenuBar config = new JMenuBar();
        JMenu menu = new JMenu("Configurações");
        JMenuItem escuro = new JMenuItem("Tema Escuro");
        JMenuItem claro = new JMenuItem("Tema Claro");
        JMenuItem clAs = new JMenuItem("Clientes e Assinaturas");
        escuro.addActionListener(s -> darkMode("escuro"));
        claro.addActionListener(s -> darkMode(""));
        clAs.addActionListener(s -> {
        root = new DefaultMutableTreeNode("Listas");
        treeModel = new DefaultTreeModel(root);
        tree = new JTree(treeModel);
        root2 = new DefaultMutableTreeNode("Base de Clientes");
        root.add(root2);
        root3 = new DefaultMutableTreeNode("Base de Assinaturas");
        root.add(root3);
        root4 = new DefaultMutableTreeNode("Cobranças");
        root.add(root4);
        
        for(Cliente cliente : catClientes.getLista()){
            root2.add(new DefaultMutableTreeNode("CPF | " + cliente.getCpf()+":"));
            for(Assinatura assinatura : catAssinaturas.getLista()){
                if(assinatura.getCpfCliente().equals(cliente.getCpf())){
                    root2.add(new DefaultMutableTreeNode("app " + assinatura.getCodigo()));
                }
            }
        }
        for(Assinatura assinatura : catAssinaturas.getLista()){
            root3.add(new DefaultMutableTreeNode("CÓDIGO | " + assinatura.getCodigoApp()+":"));
            for(Cliente cliente : catClientes.getLista()){
                if(cliente.getCpf().equals(assinatura.getCpfCliente())){
                    root3.add(new DefaultMutableTreeNode("cpf " + cliente.getCpf()));
                }
            }
        }

        frame2.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        frame2.add(new JScrollPane(tree));
        frame2.setSize(300, 300);
        frame2.setVisible(true);
    
        });
        config.add(menu);
        menu.add(escuro);
        menu.add(claro);
        menu.add(clAs);
        frame.setJMenuBar(config);
    }

    public boolean darkMode(String modo) {
        if(modo.equals("escuro")){
            painelAtual.setBackground(Color.getHSBColor(0, 0, 0.2f));
            tituloMenu.setForeground(Color.WHITE);
            temaEscuro = true;
            return true;
        }
        painelAtual.setBackground(null);
        tituloMenu.setForeground(null);
        temaEscuro = false;
        return false;
    }

    public JPanel painelMenu(){
        
        JPanel painel = new JPanel();

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
        tituloMenu = new JLabel("<html><h1><strong><i>Menu</i></strong></h1><hr></html>");
        
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
        if(temaEscuro == true){
            darkMode("escuro");
        }
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
        tfCodigo = new JTextField(10);
        linha1.add(tfCodigo);
        linha1.add(new JLabel("Nome"));
        tfCpf = new JTextField(20);
        linha1.add(tfCpf);
        // linha2
        JPanel linha2 = new JPanel();
        linha2.add(new JLabel("Licença"));
        tfCodApp = new JTextField(10);
        linha2.add(tfCodApp);
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
        tfCodigo = new JTextField(10);
        linha1.add(tfCodigo);
        linha1.add(new JLabel("Nome"));
        tfCpf = new JTextField(15);
        linha1.add(tfCpf);
        // linha2
        JPanel linha2 = new JPanel();
        linha2.add(new JLabel("Email"));
        tfCodApp = new JTextField(20);
        linha2.add(tfCodApp);
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
        linha1.add(new JLabel("Novo Código"));
        tfCodigo = new JTextField(7);
        linha1.add(tfCodigo);
        linha1.add(new JLabel("CPF Cliente"));
        tfCpf = new JTextField(7);
        linha1.add(tfCpf);
        linha1.add(new JLabel("Código do App"));
        tfCodApp = new JTextField(7);
        linha1.add(tfCodApp);
        // linha2
        JPanel linha2 = new JPanel();
        linha2.add(new JLabel("Inicio da Assinatura (mês)"));
        tfInicio = new JTextField(6);
        linha2.add(tfInicio);
        btAdd = new JButton("Fim");
        tfFim = new JTextField(6);
        linha2.add(tfFim);
        btAdd = new JButton("Adicionar Assinatura");
        btAdd.addActionListener(e->adicionaAssinatura());
        linha2.add(btAdd);
        //linha3
        JPanel linha3 = new JPanel();
        JButton btRemover = new JButton("Cancelar Assinatura");
        JTextField codigoRemovido = new JTextField(7);
        linha3.add(new JLabel("Digite o código da assinatura para cancelar:"));
        linha3.add(codigoRemovido);
        linha3.add(btRemover);
        // linha4

        JButton voltar = new JButton("Voltar e Salvar");
        
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

        centraliza.gridy = 4;

        painel.add(linha2, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 5;

        painel.add(linha3, centraliza);

        centraliza.gridx = 0;
        centraliza.gridy = 6;

        painel.add(voltar, centraliza);
    
        return painel;
    }

    public JPanel criaPainelAssinaturas() {
        JPanel painel = new JPanel();

        return painel;
    }
    
    public void adicionaApp(){
        int codigo = Integer.parseInt(tfCodigo.getText());
        String nome = tfCpf.getText();
        double preco = Double.parseDouble(tfCodApp.getText());
        Aplicativo.SO so = (Aplicativo.SO)cbSo.getSelectedItem();
        Aplicativo novo = new Aplicativo(codigo, nome, preco, so);
        catApps.cadastra(novo);
        catAppsVM.fireTableDataChanged();
    }

    public void adicionaCliente() {
        String cpf = tfCodigo.getText();
        String email = tfCodApp.getText();
        String nome = tfCpf.getText();
        Cliente novo = new Cliente(cpf, email, nome);
        catClientes.cadastra(novo);
        catClientesVM.fireTableDataChanged();
    }

    public void adicionaAssinatura() {

        int codigo = Integer.parseInt(tfCodigo.getText());
        int codigoApp = Integer.parseInt(tfCodApp.getText());
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
