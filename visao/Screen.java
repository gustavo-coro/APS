package visao;

import javax.swing.*;

import controle.Controle;
import controle.InventarioBehavior;
import controle.ItemBehavior;
import controle.MonstroBehavior;
import controle.PersonagemBehavior;
import controle.UsuarioBehavior;
import modelo.Inventario;
import modelo.Item;
import modelo.Monstro;
import modelo.Personagem;
import modelo.Usuario;
import modelo.except.CapacidadeException;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class Screen extends JFrame implements ActionListener {

    // usuario login
    private Usuario usuarioLogin;

    // objetos controle
    private Controle controle;

    // modelos de tabela
    private ModeloTabelaUsuario modeloTabelaUsuario;
    private ModeloTabelaItem modeloTabelaItem;
    private ModeloTabelaMonstro modeloTabelaMonstro;
    private ModeloTabelaInventario modeloTabelaInventario;
    private ModeloTabelaPersonagem modeloTabelaPersonagem;

    private CardLayout cardLayout;
    private JPanel cardPanel;

    // componentes disponiveis apenas ao adm
    private JButton btnMenuUsuarios;
    private JButton btnAdicionarMonstro;
    private JButton btnRemoverMonstro;
    private JButton btnAdicionarItem;
    private JButton btnRemoverItem;
    private JButton btnAdicionarInventario;;
    private JButton btnRemoverInventario;

    public Screen() {
        // inicializando o usuario logado como null
        usuarioLogin = null;

        // inicializando as variaveis de controle
        try {
            controle = new Controle(UsuarioBehavior.getInstancia());
            
            // iniciando os modelos de tabela
            modeloTabelaUsuario = new ModeloTabelaUsuario();
            modeloTabelaItem = new ModeloTabelaItem();
            modeloTabelaMonstro = new ModeloTabelaMonstro();
            modeloTabelaInventario = new ModeloTabelaInventario();
            modeloTabelaPersonagem = new ModeloTabelaPersonagem();

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
        }

        // Configurações da janela
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Cria um painel principal
        cardPanel = new JPanel();
        cardLayout = new CardLayout();
        cardPanel.setLayout(cardLayout);

        // Cria os menus
        JPanel menuInicial = criarMenuInicial();
        JPanel menuCadastro = criarMenuCadastro();
        JPanel menuPrincipal = criarMenuPrincipal();
        JPanel menuItens = criarMenuItens();
        JPanel menuInventario = criarMenuInventario();
        JPanel menuPersonagens = criarMenuPersonagens();
        JPanel menuMonstros = criarMenuMonstros();
        JPanel menuUsuarios = criarMenuUsuarios();

        // Adiciona os menus ao painel principal
        cardPanel.add(menuInicial, "MENU_INICIAL");
        cardPanel.add(menuCadastro, "MENU_CADASTRO");
        cardPanel.add(menuPrincipal, "MENU_PRINCIPAL");
        cardPanel.add(menuItens, "MENU_ITENS");
        cardPanel.add(menuInventario, "MENU_INVENTARIO");
        cardPanel.add(menuPersonagens, "MENU_PERSONAGENS");
        cardPanel.add(menuMonstros, "MENU_MONSTROS");
        cardPanel.add(menuUsuarios, "MENU_USUARIOS");

        // Adiciona o painel principal à janela
        add(cardPanel);

        // Define o menu principal como visível inicialmente
        cardLayout.show(cardPanel, "MENU_INICIAL");
    }

    public JPanel criarMenuInicial() {
        setTitle("Menu Inicial");
        JPanel janelaInicial = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Usuário:");
        gbc.gridy = 0; // Linha 0
        janelaInicial.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 25));
        gbc.gridy = 1; // Linha 1
        janelaInicial.add(usernameField, gbc);

        JLabel passwordLabel = new JLabel("Senha:");
        gbc.gridy = 2; // Linha 2
        janelaInicial.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 25));
        gbc.gridy = 3; // Linha 3
        janelaInicial.add(passwordField, gbc);

        JButton loginButton = new JButton("Entrar");
        loginButton.setPreferredSize(new Dimension(200, 25));
        gbc.gridy = 4; // Linha 4
        janelaInicial.add(loginButton, gbc);

        JLabel newAccountLabel = new JLabel("Não tem uma conta ?");
        gbc.gridy = 5; // Linha 5
        janelaInicial.add(newAccountLabel, gbc);

        JButton newAccountButton = new JButton("Criar conta");
        newAccountButton.setPreferredSize(new Dimension(200, 25));
        gbc.gridy = 6; // Linha 6
        janelaInicial.add(newAccountButton, gbc);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);

                // Aqui você pode adicionar a lógica de autenticação
                // Verifique as credenciais e tome a ação apropriada

                try {
                    controle.setEntidadeBehavior(UsuarioBehavior.getInstancia());
                    usuarioLogin = controle.getEntidadeBehavior().login_usuario(username, password);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (UnsupportedOperationException ex) {
                    JOptionPane.showMessageDialog(null, "Erro na implementação do método!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }

                if (usuarioLogin != null) {
                    btnMenuUsuarios.setVisible(usuarioLogin.getIsAdmin());
                    btnAdicionarMonstro.setVisible(usuarioLogin.getIsAdmin());
                    btnRemoverMonstro.setVisible(usuarioLogin.getIsAdmin());
                    btnAdicionarItem.setVisible(usuarioLogin.getIsAdmin());
                    btnRemoverItem.setVisible(usuarioLogin.getIsAdmin());
                    btnAdicionarInventario.setVisible(usuarioLogin.getIsAdmin());
                    btnRemoverInventario.setVisible(usuarioLogin.getIsAdmin());

                    modeloTabelaPersonagem.setRowData(usuarioLogin);

                    JOptionPane.showMessageDialog(janelaInicial, "Login successful!");
                    setTitle("Menu Principal");
                    cardLayout.show(cardPanel, "MENU_PRINCIPAL");
                } else {
                    JOptionPane.showMessageDialog(janelaInicial, "Erro no login! Tente novamente.");
                }
            }
        });

        newAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarMenuCadastro();
            }
        });

        cardLayout.show(cardPanel, "MENU_INICIAL");
        return janelaInicial;
    }

    public JPanel criarMenuCadastro() {
        setTitle("Menu Cadastro");
        JPanel janelaCadastro = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel usernameLabel = new JLabel("Nome:");
        gbc.gridy = 0; // Linha 0
        janelaCadastro.add(usernameLabel, gbc);

        JTextField usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 25));
        gbc.gridy = 1; // Linha 1
        janelaCadastro.add(usernameField, gbc);

        JLabel loginLabel = new JLabel("Usuário:");
        gbc.gridy = 2; // Linha 2
        janelaCadastro.add(loginLabel, gbc);

        JTextField loginField = new JTextField();
        loginField.setPreferredSize(new Dimension(200, 25));
        gbc.gridy = 3; // Linha 3
        janelaCadastro.add(loginField, gbc);

        JLabel passwordLabel = new JLabel("Senha:");
        gbc.gridy = 4; // Linha 4
        janelaCadastro.add(passwordLabel, gbc);

        JPasswordField passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 25));
        gbc.gridy = 5; // Linha 5
        janelaCadastro.add(passwordField, gbc);

        JCheckBox admCheckBox = new JCheckBox("Administrador");
        gbc.gridy = 6; // Linha 6
        janelaCadastro.add(admCheckBox, gbc);

        JLabel admPasswordLabel = new JLabel("Senha de Administrador:");
        admPasswordLabel.setVisible(admCheckBox.isSelected());
        gbc.gridy = 7; // Linha 7
        janelaCadastro.add(admPasswordLabel, gbc);

        JPasswordField admPasswordField = new JPasswordField();
        admPasswordField.setPreferredSize(new Dimension(200, 25));
        admPasswordField.setVisible(admCheckBox.isSelected());
        gbc.gridy = 8; // Linha 8
        janelaCadastro.add(admPasswordField, gbc);

        JButton createButton = new JButton("Criar");
        createButton.setPreferredSize(new Dimension(200, 25));
        gbc.gridy = 9; // Linha 9
        janelaCadastro.add(createButton, gbc);

        JLabel existAccountLabel = new JLabel("Já tem uma conta ?");
        gbc.gridy = 10; // Linha 10
        janelaCadastro.add(existAccountLabel, gbc);

        JButton existAccountButton = new JButton("Faça seu login");
        existAccountButton.setPreferredSize(new Dimension(200, 25));
        gbc.gridy = 11; // Linha 11
        janelaCadastro.add(existAccountButton, gbc);

        admCheckBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                admPasswordLabel.setVisible(admCheckBox.isSelected());
                admPasswordField.setVisible(admCheckBox.isSelected());
            }
        });

        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String login = loginField.getText();
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                char[] admPasswordChars = admPasswordField.getPassword();
                String admPassword = new String(admPasswordChars);
                boolean isAdmin = admCheckBox.isSelected();

                // Aqui está a lógica de autenticação
                // Verifica as credenciais e tome a ação apropriada
                if ((isAdmin == true) && (!admPassword.equals("adm"))) {
                    JOptionPane.showMessageDialog(janelaCadastro, "Senha de administrador errada!", "Erro!",
                            JOptionPane.ERROR_MESSAGE, null);
                    return;
                }
                try {
                    Usuario usuario = new Usuario(username, login, password, isAdmin, new ArrayList<Personagem>());
                    if (controle.adicionar(usuario)) {
                        usuarioLogin = usuario;
                        JOptionPane.showMessageDialog(janelaCadastro, "Conta criada com sucesso!");
                        btnMenuUsuarios.setVisible(usuarioLogin.getIsAdmin());
                        btnAdicionarMonstro.setVisible(usuarioLogin.getIsAdmin());
                        btnRemoverMonstro.setVisible(usuarioLogin.getIsAdmin());
                        btnAdicionarItem.setVisible(usuarioLogin.getIsAdmin());
                        btnRemoverItem.setVisible(usuarioLogin.getIsAdmin());
                        btnAdicionarInventario.setVisible(usuarioLogin.getIsAdmin());
                        btnRemoverInventario.setVisible(usuarioLogin.getIsAdmin());

                        modeloTabelaPersonagem.setRowData(usuarioLogin);
                        modeloTabelaUsuario.setRowData();
                        modeloTabelaUsuario.fireTableDataChanged();

                        setTitle("Menu Principal");
                        cardLayout.show(cardPanel, "MENU_PRINCIPAL");
                        modeloTabelaUsuario.fireTableDataChanged();
                    } else {
                        JOptionPane.showMessageDialog(janelaCadastro, "Usuário já existe!", "Erro!",
                                JOptionPane.ERROR_MESSAGE, null);
                        return;
                    }
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        existAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                criarMenuInicial();
            }
        });

        cardLayout.show(cardPanel, "MENU_CADASTRO");
        return janelaCadastro;
    }

    private JPanel criarMenuPrincipal() {
        setTitle("Menu Principal");
        JPanel menu = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel label = new JLabel("Clique na opção que deseja");
        JButton btnMenuItens = new JButton("Menu de Itens");
        JButton btnMenuInventario = new JButton("Menu de Inventário");
        JButton btnMenuPersonagens = new JButton("Menu de Personagens");
        JButton btnMenuMonstros = new JButton("Menu de Monstros");
        btnMenuUsuarios = new JButton("Menu de Usuários");

        gbc.gridy = 0; // Linha 0
        menu.add(label, gbc);

        gbc.gridy = 1; // Linha 1
        btnMenuItens.setPreferredSize(new Dimension(200, 25));
        menu.add(btnMenuItens, gbc);

        gbc.gridy = 2; // Linha 2
        btnMenuInventario.setPreferredSize(new Dimension(200, 25));
        menu.add(btnMenuInventario, gbc);

        gbc.gridy = 3; // Linha 3
        btnMenuPersonagens.setPreferredSize(new Dimension(200, 25));
        menu.add(btnMenuPersonagens, gbc);

        gbc.gridy = 4; // Linha 4
        btnMenuMonstros.setPreferredSize(new Dimension(200, 25));
        menu.add(btnMenuMonstros, gbc);

        gbc.gridy = 5; // Linha 5
        btnMenuUsuarios.setPreferredSize(new Dimension(200, 25));
        menu.add(btnMenuUsuarios, gbc);
        if (usuarioLogin != null) {
            btnMenuInventario.setVisible(usuarioLogin.getIsAdmin());
        }

        btnMenuItens.addActionListener(this);
        btnMenuInventario.addActionListener(this);
        btnMenuPersonagens.addActionListener(this);
        btnMenuMonstros.addActionListener(this);
        btnMenuUsuarios.addActionListener(this);

        return menu;
    }

    private JPanel criarMenuItens() {
        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());

        // Tabela no menu de itens
        JTable tabela = new JTable(modeloTabelaItem);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menu.add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Botões adicionais no menu de itens
        btnAdicionarItem = new JButton("Adicionar");
        btnRemoverItem = new JButton("Remover");
        JButton btnVoltar = new JButton("Voltar");

        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnAdicionarItem);
        panelBotoes.add(btnRemoverItem);
        panelBotoes.add(btnVoltar);
        menu.add(panelBotoes, BorderLayout.SOUTH);

        // Adicionar ouvinte de ação para o botão "Adicionar"
        btnAdicionarItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    renderizarJanelaAdicaoItem();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Remover"
        btnRemoverItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    modeloTabelaItem.removeItem(tabela.getSelectedRow());
                    modeloTabelaItem.setRowData();
                    modeloTabelaItem.fireTableDataChanged();
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Selecione um item para remover!",
                            "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Voltar"
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Menu Principal");
                cardLayout.show(cardPanel, "MENU_PRINCIPAL");
            }
        });

        return menu;
    }

    private JPanel criarMenuInventario() {
        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());

        // Tabela no menu de inventário
        JTable tabela = new JTable(modeloTabelaInventario);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menu.add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Botões adicionais no menu de inventário
        btnAdicionarInventario = new JButton("Adicionar");
        btnRemoverInventario = new JButton("Remover");
        JButton btnVerItens = new JButton("Ver Itens");
        JButton btnVoltar = new JButton("Voltar");

        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnAdicionarInventario);
        panelBotoes.add(btnRemoverInventario);
        panelBotoes.add(btnVerItens);
        panelBotoes.add(btnVoltar);
        menu.add(panelBotoes, BorderLayout.SOUTH);

        // Adicionar ouvinte de ação para o botão "Adicionar"
        btnAdicionarInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    renderizarJanelaAdicaoInventario();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Remover"
        btnRemoverInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    modeloTabelaInventario.removeInventario(tabela.getSelectedRow());
                    modeloTabelaInventario.setRowData();
                    modeloTabelaInventario.fireTableDataChanged();
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Selecione um inventário para remover!",
                            "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Ver Itens"
        btnVerItens.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    renderizarJanelaVizualizarItensInventario(modeloTabelaInventario.getIdAt(tabela.getSelectedRow()));
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Selecione um inventário para ver seus itens!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Voltar"
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Menu Principal");
                cardLayout.show(cardPanel, "MENU_PRINCIPAL");
            }
        });

        return menu;
    }

    private JPanel criarMenuPersonagens() {
        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());

        // Tabela no menu de personagens
        JTable tabela = new JTable(modeloTabelaPersonagem);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menu.add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Botões adicionais no menu de personagens
        JButton btnAdicionar = new JButton("Adicionar");
        JButton btnRemover = new JButton("Remover");
        JButton btnVerInventario = new JButton("Ver Inventário");
        JButton btnVoltar = new JButton("Voltar");

        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnAdicionar);
        panelBotoes.add(btnRemover);
        panelBotoes.add(btnVerInventario);
        panelBotoes.add(btnVoltar);
        menu.add(panelBotoes, BorderLayout.SOUTH);

        // Adicionar ouvinte de ação para o botão "Adicionar"
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    renderizarJanelaAdicaoPersonagem();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Remover"
        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    modeloTabelaPersonagem.removePersonagem(tabela.getSelectedRow(), usuarioLogin.getId());
                    modeloTabelaPersonagem.setRowData(usuarioLogin);
                    modeloTabelaPersonagem.fireTableDataChanged();
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Selecione um personagem para remover!",
                            "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Ver Inventário"
        btnVerInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    renderizarJanelaVizualizarInventarioPersonagem(
                            modeloTabelaPersonagem.getIdAt(tabela.getSelectedRow()));
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Selecione um personagem para ver seu inventário!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Voltar"
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Menu Principal");
                cardLayout.show(cardPanel, "MENU_PRINCIPAL");
            }
        });

        return menu;
    }

    private JPanel criarMenuMonstros() {
        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());

        // Tabela no menu de monstro
        JTable tabela = new JTable(modeloTabelaMonstro);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menu.add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Botões adicionais no menu de monstro
        btnAdicionarMonstro = new JButton("Adicionar");
        btnRemoverMonstro = new JButton("Remover");
        JButton btnVoltar = new JButton("Voltar");

        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnAdicionarMonstro);
        panelBotoes.add(btnRemoverMonstro);
        panelBotoes.add(btnVoltar);
        menu.add(panelBotoes, BorderLayout.SOUTH);

        // Adicionar ouvinte de ação para o botão "Adicionar"
        btnAdicionarMonstro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    renderizarJanelaAdicaoMonstro();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Remover"
        btnRemoverMonstro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    modeloTabelaMonstro.removeMonstro(tabela.getSelectedRow());
                    modeloTabelaMonstro.setRowData();
                    modeloTabelaMonstro.fireTableDataChanged();
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Selecione um monstro para remover!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Voltar"
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Menu Principal");
                cardLayout.show(cardPanel, "MENU_PRINCIPAL");
            }
        });

        return menu;
    }

    private JPanel criarMenuUsuarios() {
        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());

        // Tabela no menu de usuario
        JTable tabela = new JTable(modeloTabelaUsuario);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menu.add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Botões adicionais no menu de usuario
        JButton btnRemover = new JButton("Remover");
        JButton btnVoltar = new JButton("Voltar");

        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnRemover);
        panelBotoes.add(btnVoltar);
        menu.add(panelBotoes, BorderLayout.SOUTH);

        // Adicionar ouvinte de ação para o botão "Remover"
        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int idRemovido = modeloTabelaUsuario.removeUsuario(tabela.getSelectedRow());
                    modeloTabelaUsuario.setRowData();
                    modeloTabelaUsuario.fireTableDataChanged();
                    if (usuarioLogin.getId() == idRemovido) {
                        usuarioLogin = null;
                        setTitle("Menu Inicial");
                        cardLayout.show(cardPanel, "MENU_INICIAL");
                    }
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Selecione um usuário para remover!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Voltar"
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setTitle("Menu Principal");
                cardLayout.show(cardPanel, "MENU_PRINCIPAL");
            }
        });

        return menu;
    }

    private void renderizarJanelaAdicaoItem() throws IOException {
        controle.setEntidadeBehavior(ItemBehavior.getInstancia());
        
        JPanel janelaAdicaoItens = new JPanel();
        janelaAdicaoItens.setLayout(new BorderLayout());

        // Campos de texto para adição
        JTextField inputNomeItem = new JTextField();
        JTextField inputNivelItem = new JTextField();
        JTextField inputForcaItem = new JTextField();
        JTextField inputDurabilidadeItem = new JTextField();

        // Botões de salvar e cancelar
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        // Painel para campos de texto
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridy = 0; // Linha 0
        panelCampos.add(new JLabel("Digite o nome do item:"), gbc);

        gbc.gridy = 1; // Linha 1
        inputNomeItem.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(inputNomeItem, gbc);

        gbc.gridy = 2; // Linha 2
        panelCampos.add(new JLabel("Digite o nível do item:"), gbc);

        gbc.gridy = 3; // Linha 3
        inputNivelItem.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(inputNivelItem, gbc);

        gbc.gridy = 4; // Linha 4
        panelCampos.add(new JLabel("Digite a força do item:"), gbc);

        gbc.gridy = 5; // Linha 5
        inputForcaItem.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(inputForcaItem, gbc);

        gbc.gridy = 4; // Linha 4
        panelCampos.add(new JLabel("Digite a durabilidade do item:"), gbc);

        gbc.gridy = 5; // Linha 5
        inputDurabilidadeItem.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(inputDurabilidadeItem, gbc);

        // Painel para botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnCancelar);

        janelaAdicaoItens.add(panelCampos, BorderLayout.CENTER);
        janelaAdicaoItens.add(panelBotoes, BorderLayout.SOUTH);

        // Adicionar ouvinte de ação para o botão "Salvar"
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obter os valores dos campos de texto
                String strNomeItem = inputNomeItem.getText();
                String strNivelItem = inputNivelItem.getText();
                String strForcaItem = inputForcaItem.getText();
                String strDurabilidadeItem = inputDurabilidadeItem.getText();
                try {
                    int valorNivelItem = Integer.parseInt(strNivelItem);
                    int valorForcaItem = Integer.parseInt(strForcaItem);
                    int valorDurabilidadeItem = Integer.parseInt(strDurabilidadeItem);
                    // Método para salvar no banco de dados
                    Item item = new Item(strNomeItem, valorNivelItem, valorForcaItem, valorDurabilidadeItem);
                    controle.adicionar(item);
                    modeloTabelaItem.setRowData();
                    modeloTabelaItem.fireTableDataChanged();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Valor incorreto no campo de números", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Voltar à janela anterior (menu de itens)
                cardLayout.show(cardPanel, "MENU_ITENS");
            }
        });

        // Adicionar ouvinte de ação para o botão "Cancelar"
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Voltar à janela anterior (menu de itens)
                cardLayout.show(cardPanel, "MENU_ITENS");
            }
        });

        // Renderizar a janela de adição de itens
        cardPanel.add(janelaAdicaoItens, "JANELA_ADICAO_ITENS");
        cardLayout.show(cardPanel, "JANELA_ADICAO_ITENS");
    }

    private void renderizarJanelaAdicaoInventario() throws IOException {
        controle.setEntidadeBehavior(InventarioBehavior.getInstancia());
        
        JPanel janelaAdicaoInventario = new JPanel();
        janelaAdicaoInventario.setLayout(new BorderLayout());

        // Campos de texto para adição
        JTextField inputNomeInventario = new JTextField();
        JTextField inputCapacidadeInventario = new JTextField();
        JTextField inputDurabilidadeInventario = new JTextField();

        // Botões de salvar e cancelar
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        // Painel para campos de texto
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridy = 0; // Linha 0
        panelCampos.add(new JLabel("Digite o nome do inventário:"), gbc);

        gbc.gridy = 1; // Linha 1
        inputNomeInventario.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(inputNomeInventario, gbc);

        gbc.gridy = 2; // Linha 2
        panelCampos.add(new JLabel("Digite a capacidade do inventário:"), gbc);

        gbc.gridy = 3; // Linha 3
        inputCapacidadeInventario.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(inputCapacidadeInventario, gbc);

        gbc.gridy = 4; // Linha 4
        panelCampos.add(new JLabel("Digite a durabilidade do inventário:"), gbc);

        gbc.gridy = 5; // Linha 5
        inputDurabilidadeInventario.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(inputDurabilidadeInventario, gbc);

        // Painel para botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnCancelar);

        janelaAdicaoInventario.add(panelCampos, BorderLayout.CENTER);
        janelaAdicaoInventario.add(panelBotoes, BorderLayout.SOUTH);

        // Adicionar ouvinte de ação para o botão "Salvar"
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obter os valores dos campos de texto
                String strNomeInventario = inputNomeInventario.getText();
                String strCapacidadeInventario = inputCapacidadeInventario.getText();
                String strDurabilidadeInventario = inputDurabilidadeInventario.getText();
                try {
                    int valorCapacidadeInventario = Integer.parseInt(strCapacidadeInventario);
                    int valorDurabilidadeInventario = Integer.parseInt(strDurabilidadeInventario);
                    // Método para salvar no banco de dados
                    Inventario inventario = new Inventario(strNomeInventario, valorCapacidadeInventario,
                            valorDurabilidadeInventario);
                    controle.adicionar(inventario);
                    modeloTabelaInventario.setRowData();
                    modeloTabelaInventario.fireTableDataChanged();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Valor incorreto no campo de números", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Voltar à janela anterior (menu de inventarios)
                cardLayout.show(cardPanel, "MENU_INVENTARIO");
            }
        });

        // Adicionar ouvinte de ação para o botão "Cancelar"
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Voltar à janela anterior (menu de inventário)
                cardLayout.show(cardPanel, "MENU_INVENTARIO");
            }
        });

        // Renderizar a janela de adição
        cardPanel.add(janelaAdicaoInventario, "JANELA_ADICAO_INVENTARIO");
        cardLayout.show(cardPanel, "JANELA_ADICAO_INVENTARIO");
    }

    private void renderizarJanelaAdicaoPersonagem() throws IOException {
        controle.setEntidadeBehavior(PersonagemBehavior.getInstancia());
        
        JPanel janelaAdicaoPersonagem = new JPanel();
        janelaAdicaoPersonagem.setLayout(new BorderLayout());

        // Campos de texto para adição
        JTextField inputNomePersonagem = new JTextField();
        JTextField inputForcaPersonagem = new JTextField();
        JTextField inputNivelPersonagem = new JTextField();

        // Botões de salvar e cancelar
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        // Painel para campos de texto
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridy = 0; // Linha 0
        panelCampos.add(new JLabel("Digite o nome do personagem:"), gbc);

        gbc.gridy = 1; // Linha 1
        inputNomePersonagem.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(inputNomePersonagem, gbc);

        gbc.gridy = 2; // Linha 2
        panelCampos.add(new JLabel("Digite o nível do personagem:"), gbc);

        gbc.gridy = 3; // Linha 3
        inputForcaPersonagem.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(inputForcaPersonagem, gbc);

        gbc.gridy = 4; // Linha 4
        panelCampos.add(new JLabel("Digite a força do personagem:"), gbc);

        gbc.gridy = 5; // Linha 5
        inputNivelPersonagem.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(inputNivelPersonagem, gbc);

        // Painel para botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnCancelar);

        janelaAdicaoPersonagem.add(panelCampos, BorderLayout.CENTER);
        janelaAdicaoPersonagem.add(panelBotoes, BorderLayout.SOUTH);

        // Adicionar ouvinte de ação para o botão "Salvar"
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obter os valores dos campos de texto
                String strNomePersonagem = inputNomePersonagem.getText();
                String strForcaPersonagem = inputForcaPersonagem.getText();
                String strNivelPersonagem = inputNivelPersonagem.getText();

                try {
                    int valorForcaPersonagem = Integer.parseInt(strForcaPersonagem);
                    int valorNivelPersonagem = Integer.parseInt(strNivelPersonagem);
                    // Método para salvar no banco de dados
                    Personagem personagem = new Personagem(strNomePersonagem, valorForcaPersonagem, valorNivelPersonagem);
                    if (controle.adicionar(personagem)) {
                        controle.setEntidadeBehavior(UsuarioBehavior.getInstancia());
                        controle.getEntidadeBehavior().add_personagem(usuarioLogin.getId(), personagem);
                        modeloTabelaPersonagem.fireTableDataChanged();
                        controle.setEntidadeBehavior(PersonagemBehavior.getInstancia());
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Valor incorreto no campo de números", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (UnsupportedOperationException ex) {
                    JOptionPane.showMessageDialog(null, "Erro na implementação do método!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Voltar à janela anterior (menu de perosnagem)
                cardLayout.show(cardPanel, "MENU_PERSONAGENS");
            }
        });

        // Adicionar ouvinte de ação para o botão "Cancelar"
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Voltar à janela anterior (menu de itens)
                cardLayout.show(cardPanel, "MENU_PERSONAGENS");
            }
        });

        // Renderizar a janela de adição
        cardPanel.add(janelaAdicaoPersonagem, "JANELA_ADICAO_PERSONAGEM");
        cardLayout.show(cardPanel, "JANELA_ADICAO_PERSONAGEM");
    }

    private void renderizarJanelaAdicaoMonstro() throws IOException {
        controle.setEntidadeBehavior(MonstroBehavior.getInstancia());
        
        JPanel janelaAdicaoMonstros = new JPanel();
        janelaAdicaoMonstros.setLayout(new BorderLayout());

        // Campos de texto para adição
        JTextField inputNomeMonstro = new JTextField();
        JTextField inputForcaMonstro = new JTextField();
        JTextField inputNivelMonstro = new JTextField();
        JTextField inputVidaMonstro = new JTextField();

        // Botões de salvar e cancelar
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");

        // Painel para campos de texto
        JPanel panelCampos = new JPanel();
        panelCampos.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridy = 0; // Linha 0
        panelCampos.add(new JLabel("Digite o nome do monstro:"), gbc);

        gbc.gridy = 1; // Linha 1
        inputNomeMonstro.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(inputNomeMonstro, gbc);

        gbc.gridy = 2; // Linha 2
        panelCampos.add(new JLabel("Digite a força do monstro:"), gbc);

        gbc.gridy = 3; // Linha 3
        inputForcaMonstro.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(inputForcaMonstro, gbc);

        gbc.gridy = 4; // Linha 4
        panelCampos.add(new JLabel("Digite o nível do monstro:"), gbc);

        gbc.gridy = 5; // Linha 5
        inputNivelMonstro.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(inputNivelMonstro, gbc);

        gbc.gridy = 4; // Linha 4
        panelCampos.add(new JLabel("Digite a vida do monstro:"), gbc);

        gbc.gridy = 5; // Linha 5
        inputVidaMonstro.setPreferredSize(new Dimension(200, 25));
        panelCampos.add(inputVidaMonstro, gbc);

        // Painel para botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnSalvar);
        panelBotoes.add(btnCancelar);

        janelaAdicaoMonstros.add(panelCampos, BorderLayout.CENTER);
        janelaAdicaoMonstros.add(panelBotoes, BorderLayout.SOUTH);

        // Adicionar ouvinte de ação para o botão "Salvar"
        btnSalvar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Obter os valores dos campos de texto
                String strNomeMonstro = inputNomeMonstro.getText();
                String strForcaMonstro = inputForcaMonstro.getText();
                String strNivelMonstro = inputNivelMonstro.getText();
                String strVidaMonstro = inputVidaMonstro.getText();
                try {
                    int valorForcaMonstro = Integer.parseInt(strForcaMonstro);
                    int valorNivelMonstro = Integer.parseInt(strNivelMonstro);
                    int valorVidaMonstro = Integer.parseInt(strVidaMonstro);
                    // Método para salvar no banco de dados
                    Monstro monstro = new Monstro(strNomeMonstro, valorNivelMonstro, valorForcaMonstro, valorVidaMonstro);
                    controle.adicionar(monstro);
                    modeloTabelaMonstro.setRowData();
                    modeloTabelaMonstro.fireTableDataChanged();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Valor incorreto no campo de números", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }

                // Voltar à janela anterior (menu de monstros)
                cardLayout.show(cardPanel, "MENU_MONSTROS");
            }
        });

        // Adicionar ouvinte de ação para o botão "Cancelar"
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Voltar à janela anterior (menu de monstros)
                cardLayout.show(cardPanel, "MENU_MONSTROS");
            }
        });

        // Renderizar a janela de adição de monstros
        cardPanel.add(janelaAdicaoMonstros, "JANELA_ADICAO_MONSTROS");
        cardLayout.show(cardPanel, "JANELA_ADICAO_MONSTROS");
    }

    private void renderizarJanelaVizualizarItensInventario(int id) throws IOException {
        controle.setEntidadeBehavior(InventarioBehavior.getInstancia());
        
        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());

        // Tabela no menu de itens
        ModeloTabelaItem modeloTabelaItemInventario = new ModeloTabelaItem(id);
        JTable tabela = new JTable(modeloTabelaItemInventario);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menu.add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Painel para título
        Inventario i = (Inventario) controle.retorna(id);
        JPanel panelTitulo = new JPanel();
        panelTitulo.add(new JLabel("Inventário: " + i.getNome() +
                " | Capacidade: " + i.getCapacidade()));

        // Botões adicionais no menu de itens
        JButton btnAdicionarItemInventario = new JButton("Adicionar");
        JButton btnRemoverItemInventario = new JButton("Remover");
        JButton btnVoltar = new JButton("Voltar");

        btnAdicionarItemInventario.setVisible(usuarioLogin.getIsAdmin());
        btnRemoverItemInventario.setVisible(usuarioLogin.getIsAdmin());

        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnAdicionarItemInventario);
        panelBotoes.add(btnRemoverItemInventario);
        panelBotoes.add(btnVoltar);
        menu.add(panelBotoes, BorderLayout.SOUTH);

        // Adicionar ouvinte de ação para o botão "Adicionar"
        btnAdicionarItemInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    renderizarJanelaAdicaoItensInventario(id);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Remover"
        btnRemoverItemInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    modeloTabelaItemInventario.removeItemInventario(tabela.getSelectedRow(), id);
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Selecione um item para remover!",
                            "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Voltar"
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "MENU_INVENTARIO");
            }
        });

        // Adicionar componentes à janela de vizualizar itens
        menu.add(panelTitulo, BorderLayout.NORTH);
        menu.add(panelBotoes, BorderLayout.SOUTH);

        // Renderizar a janela de vizualizar itens
        cardPanel.add(menu, "JANELA_VIZUALIZAR_ITENS_INVENTARIO");
        cardLayout.show(cardPanel, "JANELA_VIZUALIZAR_ITENS_INVENTARIO");
    }

    private void renderizarJanelaVizualizarInventarioPersonagem(int id) throws IOException{
        controle.setEntidadeBehavior(PersonagemBehavior.getInstancia());
        JPanel menu = new JPanel();
        menu.setLayout(new BorderLayout());

        // Tabela no menu de itens
        Personagem p = (Personagem) controle.retorna(id);
        ModeloTabelaItem modeloTabelaItemInventario = new ModeloTabelaItem(p.getInventario().getId());
        JTable tabela = new JTable(modeloTabelaItemInventario);
        tabela.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        menu.add(new JScrollPane(tabela), BorderLayout.CENTER);

        // Painel para título
        JPanel panelTitulo = new JPanel();
        panelTitulo.add(new JLabel("Personagem: " + p.getNome() +
                " | Inventário: " + p.getInventario().getNome() +
                " | Capacidade: " + p.getInventario().getCapacidade()));

        // Botões adicionais no menu de itens
        JButton btnAdicionarInventario = new JButton("Trocar de Inventário");
        JButton btnRemoverInventario = new JButton("Remover");
        JButton btnVoltar = new JButton("Voltar");

        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnAdicionarInventario);
        panelBotoes.add(btnRemoverInventario);
        panelBotoes.add(btnVoltar);
        menu.add(panelBotoes, BorderLayout.SOUTH);

        // Adicionar ouvinte de ação para o botão "Adicionar"
        btnAdicionarInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    renderizarJanelaAdicaoInventarioPersonagem(id);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Remover"
        btnRemoverInventario.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controle.setEntidadeBehavior(PersonagemBehavior.getInstancia());
                    controle.getEntidadeBehavior().remove_inventario(id);
                    controle.getEntidadeBehavior().add_inventario(id, new Inventario());
                    renderizarJanelaVizualizarInventarioPersonagem(id);
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Selecione um item para remover!",
                            "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro no arquivo de salvamento!",
                            "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (UnsupportedOperationException ex) {
                    JOptionPane.showMessageDialog(null, "Erro na implementação do método!",
                            "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Voltar"
        btnVoltar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cardLayout.show(cardPanel, "MENU_PERSONAGENS");
            }
        });

        // Adicionar componentes à janela de vizualizar itens
        menu.add(panelTitulo, BorderLayout.NORTH);
        menu.add(panelBotoes, BorderLayout.SOUTH);

        // Renderizar a janela de vizualizar itens
        cardPanel.add(menu, "JANELA_VIZUALIZAR_INVENTARIO_PERSONAGEM");
        cardLayout.show(cardPanel, "JANELA_VIZUALIZAR_INVENTARIO_PERSONAGEM");
    }

    private void renderizarJanelaAdicaoItensInventario(int id) throws IOException{
        controle.setEntidadeBehavior(InventarioBehavior.getInstancia());
        JPanel janelaAdicaoItens = new JPanel();
        janelaAdicaoItens.setLayout(new BorderLayout());

        JTable tabelaTodosItens = new JTable(modeloTabelaItem);
        tabelaTodosItens.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        janelaAdicaoItens.add(new JScrollPane(tabelaTodosItens), BorderLayout.CENTER);

        // Botões de adicionar e cancelar
        JButton btnAdd = new JButton("Adicionar");
        JButton btnCancelar = new JButton("Cancelar");

        // Painel para botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnAdd);
        panelBotoes.add(btnCancelar);

        janelaAdicaoItens.add(panelBotoes, BorderLayout.SOUTH);

        // Adicionar ouvinte de ação para o botão "Salvar"
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controle.setEntidadeBehavior(InventarioBehavior.getInstancia());
                    controle.getEntidadeBehavior().add_item(id, modeloTabelaItem.getItemAt(tabelaTodosItens.getSelectedRow()));
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Valor incorreto no campo de números", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Selecionae um item para adicionar", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (CapacidadeException ex) {
                    JOptionPane.showMessageDialog(null, "Capacidade do inventário atingida!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (UnsupportedOperationException ex) {
                    JOptionPane.showMessageDialog(null, "Erro na implementação do método!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
                // Voltar à janela anterior
                try {
                    renderizarJanelaVizualizarItensInventario(id);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Cancelar"
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Voltar à janela anterior
                cardLayout.show(cardPanel, "JANELA_VIZUALIZAR_ITENS_INVENTARIO");
            }
        });

        // Renderizar a janela de adição de itens
        cardPanel.add(janelaAdicaoItens, "JANELA_ADICAO_ITENS_INVENTARIO");
        cardLayout.show(cardPanel, "JANELA_ADICAO_ITENS_INVENTARIO");
    }

    private void renderizarJanelaAdicaoInventarioPersonagem(int id) throws IOException {
        controle.setEntidadeBehavior(PersonagemBehavior.getInstancia());
        JPanel janelaAdicaoInventario = new JPanel();
        janelaAdicaoInventario.setLayout(new BorderLayout());

        JTable tabelaTodosInventarios = new JTable(modeloTabelaInventario);
        tabelaTodosInventarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        janelaAdicaoInventario.add(new JScrollPane(tabelaTodosInventarios), BorderLayout.CENTER);

        // Botões de adicionar e cancelar
        JButton btnAdd = new JButton("Adicionar");
        JButton btnCancelar = new JButton("Cancelar");

        // Painel para botões
        JPanel panelBotoes = new JPanel();
        panelBotoes.add(btnAdd);
        panelBotoes.add(btnCancelar);

        janelaAdicaoInventario.add(panelBotoes, BorderLayout.SOUTH);

        // Adicionar ouvinte de ação para o botão "Salvar"
        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controle.setEntidadeBehavior(PersonagemBehavior.getInstancia());
                    controle.getEntidadeBehavior().add_inventario(id,
                            modeloTabelaInventario.getInventarioAt(tabelaTodosInventarios.getSelectedRow()));
                } catch (IndexOutOfBoundsException ex) {
                    JOptionPane.showMessageDialog(null, "Selecionae um inventario para adicionar", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } catch (UnsupportedOperationException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com a implementação do método!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                } 
                // Voltar à janela anterior
                try {
                    renderizarJanelaVizualizarInventarioPersonagem(id);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Adicionar ouvinte de ação para o botão "Cancelar"
        btnCancelar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Voltar à janela anterior
                try {
                    renderizarJanelaVizualizarInventarioPersonagem(id);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "Erro com o arquivo de salvamento!", "ERRO",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Renderizar a janela de adição de inventarios
        cardPanel.add(janelaAdicaoInventario, "JANELA_ADICAO_INVENTARIO_ITENS");
        cardLayout.show(cardPanel, "JANELA_ADICAO_INVENTARIO_ITENS");
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String comando = e.getActionCommand();

        switch (comando) {
            case "Menu de Itens":
                setTitle("Menu de Itens");
                cardLayout.show(cardPanel, "MENU_ITENS");
                break;
            case "Menu de Inventário":
                setTitle("Menu de Inventários");
                cardLayout.show(cardPanel, "MENU_INVENTARIO");
                break;
            case "Menu de Personagens":
                setTitle("Menu de Personagens");
                cardLayout.show(cardPanel, "MENU_PERSONAGENS");
                break;
            case "Menu de Monstros":
                setTitle("Menu de Monstros");
                cardLayout.show(cardPanel, "MENU_MONSTROS");
                break;
            case "Menu de Usuários":
                setTitle("Menu de Usuários");
                cardLayout.show(cardPanel, "MENU_USUARIOS");
                break;
            default:
                break;
        }
    }

}