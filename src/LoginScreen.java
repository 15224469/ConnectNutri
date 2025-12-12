package ConnectNutri;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Collections;
import java.util.List;

public class LoginScreen extends JFrame {

    private JTextField emailField;
    private JPasswordField senhaField;
    private boolean mostrarSenha = false; // Variável de estado para mostrar/ocultar senha

    public LoginScreen() {
        setTitle("ConnectNutri - Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Fundo com gradiente
        JPanel background = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(110, 186, 46),
                        getWidth(), getHeight(), new Color(199, 235, 180)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        background.setLayout(new GridBagLayout());
        add(background);

        // Card branco central
        JPanel card = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(Color.WHITE);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            }
        };
        card.setOpaque(false);
        card.setPreferredSize(new Dimension(450, 500));
        card.setLayout(null);

        background.add(card);

        // ------------------------
        // LOGO
        // ------------------------
        JLabel logo = new JLabel();
        URL logoUrl = getClass().getResource("ConnectNutri_Logo.png"); // Carrega a imagem do classpath
        if (logoUrl != null) {
            logo.setIcon(new ImageIcon(new ImageIcon(logoUrl).getImage().getScaledInstance(120, 70, Image.SCALE_SMOOTH)));
        }

        logo.setBounds(150, 20, 150, 90);
        card.add(logo);

        JLabel titulo = new JLabel("ConnectNutri");
        titulo.setFont(new Font("Serif", Font.ITALIC, 28));
        titulo.setForeground(new Color(40, 110, 40));
        titulo.setBounds(150, 100, 200, 40);
        card.add(titulo);

        // ------------------------
        // Campo Email
        // ------------------------
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        emailLabel.setBounds(60, 150, 200, 20);
        card.add(emailLabel);

        JSeparator emailLinha = new JSeparator();
        emailLinha.setBounds(60, 200, 330, 2);

        emailField = new JTextField();
        emailField.setBorder(null);
        emailField.setFont(new Font("Arial", Font.PLAIN, 16));
        emailField.setBounds(60, 170, 330, 30);

        card.add(emailField);
        card.add(emailLinha);

        // ------------------------
        // Campo Senha
        // ------------------------
        JLabel senhaLabel = new JLabel("Senha");
        senhaLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        senhaLabel.setBounds(60, 220, 200, 20);
        card.add(senhaLabel);

        senhaField = new JPasswordField();
        senhaField.setBorder(null);
        senhaField.setFont(new Font("Arial", Font.PLAIN, 16));
        senhaField.setBounds(60, 240, 300, 30);
        senhaField.setEchoChar('•');

        JButton verSenha = new JButton("\uD83D\uDC41");
        verSenha.setFocusPainted(false);
        verSenha.setBorder(null);
        verSenha.setBackground(Color.WHITE);
        verSenha.setFont(new Font("Arial", Font.PLAIN, 18));
        verSenha.setBounds(360, 240, 40, 30);

        verSenha.addActionListener(e -> {
            mostrarSenha = !mostrarSenha;
            senhaField.setEchoChar(mostrarSenha ? (char) 0 : '•');
        });

        JSeparator senhaLinha = new JSeparator();
        senhaLinha.setBounds(60, 275, 330, 2);

        card.add(senhaField);
        card.add(verSenha);
        card.add(senhaLinha);

        // ------------------------
        // Checkbox + esqueci senha
        // ------------------------
        JCheckBox lembrar = new JCheckBox("Relembre por 30 dias");
        lembrar.setBounds(60, 285, 200, 25);
        lembrar.setBackground(Color.WHITE);
        card.add(lembrar);

        JLabel esqueci = new JLabel("Esqueceu a senha?");
        esqueci.setFont(new Font("Arial", Font.PLAIN, 13));
        esqueci.setForeground(Color.GRAY);
        esqueci.setBounds(280, 285, 150, 25);
        card.add(esqueci);

        // ------------------------
        // Botão Login
        // ------------------------
        JButton loginBtn = new JButton("Login");
        estilizarBotao(loginBtn, new Color(40, 40, 40), Color.WHITE);
        loginBtn.setBounds(60, 330, 330, 45);
        loginBtn.addActionListener(e -> {
            String email = emailField.getText();
            String senha = new String(senhaField.getPassword());

            if (email.isEmpty() || senha.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, preencha todos os campos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (Storage.authenticate(email, senha)) {
                JOptionPane.showMessageDialog(this, "Login bem-sucedido!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
                // TODO: Abrir a próxima tela da aplicação (tela principal, dashboard, etc.)
                // new MainScreen().setVisible(true);
                // this.dispose();
            } else {
                JOptionPane.showMessageDialog(this, "Email ou senha incorretos.", "Erro de Login", JOptionPane.ERROR_MESSAGE);
            }
        });
        card.add(loginBtn);

        // Botão cadastrar
        JButton cadBtn = new JButton("Cadastre-se");
        estilizarBotao(cadBtn, new Color(40, 40, 40), Color.WHITE);
        cadBtn.setBounds(60, 380, 330, 45);
        cadBtn.addActionListener(e -> {
            // Abre a tela de Cadastro e fecha a de Login
            new CadastroScreen().setVisible(true);
            this.dispose();
        });
        card.add(cadBtn);

        // ------------------------
        // Botão Gmail
        // ------------------------
        JButton gmail = new JButton("Login com Google");
        estilizarBotao(gmail, new Color(240, 240, 240), Color.BLACK);

        gmail.setBounds(60, 440, 330, 45);
        gmail.setIcon(new ImageIcon("/mnt/data/gmail.png"));
        gmail.setHorizontalTextPosition(SwingConstants.RIGHT);

        card.add(gmail);
    }

    private void estilizarBotao(JButton btn, Color fundo, Color texto) {
        btn.setFocusPainted(false);
        btn.setBackground(fundo);
        btn.setForeground(texto);
        btn.setFont(new Font("Arial", Font.BOLD, 18));
        btn.setBorder(BorderFactory.createEmptyBorder());
        btn.setUI(new RoundedButtonUI());
    }

    // Classe interna para lidar com o armazenamento de usuários
    static class Storage {
        private static final Path USERS_FILE = Paths.get("users.txt");

        static boolean register(String username, String password) {
            try {
                if (!Files.exists(USERS_FILE)) {
                    Files.createFile(USERS_FILE);
                }

                List<String> lines = Files.readAllLines(USERS_FILE);

                for (String l : lines) {
                    String[] parts = l.split(";");
                    if (parts.length > 0 && parts[0].equalsIgnoreCase(username)) {
                        return false; // Usuário já existe
                    }
                }

                String line = username + ";" + password;
                Files.write(USERS_FILE, Collections.singletonList(line), StandardOpenOption.APPEND);
                return true;

            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao registrar usuário: " + e.getMessage());
                return false;
            }
        }

        static boolean authenticate(String username, String password) {
            try {
                if (!Files.exists(USERS_FILE)) return false;

                List<String> lines = Files.readAllLines(USERS_FILE);

                for (String l : lines) {
                    String[] parts = l.split(";");
                    if (parts.length == 2 && parts[0].equals(username) && parts[1].equals(password)) {
                        return true; // Autenticação bem-sucedida
                    }
                }
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Erro ao autenticar: " + e.getMessage());
            }
            return false; // Falha na autenticação
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LoginScreen().setVisible(true));
    }
}

