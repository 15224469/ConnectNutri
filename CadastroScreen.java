import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class CadastroScreen extends JFrame {

    // --- Componente Customizado para o Fundo Gradiente ---
    private class CustomGradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g.create();
            int w = getWidth();
            int h = getHeight();

            // Cores aproximadas do gradiente:
            // Verde Claro Central (similar ao do meio da imagem)
            Color centerColor = new Color(220, 255, 220, 255);
            // Verde Mais Escuro/Borda (similar ao das laterais)
            Color edgeColor = new Color(180, 230, 180, 255); 

            // Para simular o efeito de luz central, usamos um Gradiente Radial (ou Duplo Gradiente)
            // Em Swing, vamos simplificar com um gradiente que suaviza o centro.
            
            // Cria um gradiente que vai de um verde pálido nas extremidades
            // para um verde mais claro no centro (simulando a iluminação).
            GradientPaint gp = new GradientPaint(
                0, 0, edgeColor,
                w, h, centerColor.brighter() // Ponto mais claro no canto inferior
            );
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, w, h);

            g2d.dispose();
        }
    }
    
    // --- Campo de Texto Customizado para replicar a linha de separação ---
    private class CustomTextField extends JTextField {
        public CustomTextField(int columns) {
            super(columns);
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); // Linha preta inferior
            setFont(new Font("Arial", Font.PLAIN, 16));
            setOpaque(false); // Fundo transparente
        }
    }

    // --- Campo de Senha Customizado ---
    private class CustomPasswordField extends JPasswordField {
        public CustomPasswordField(int columns) {
            super(columns);
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK)); // Linha preta inferior
            setFont(new Font("Arial", Font.PLAIN, 16));
            setOpaque(false); // Fundo transparente
        }
    }

    public CadastroScreen() {
        // --- Configuração Básica da Janela ---
        setTitle("Cadastro do Paciente - ConnectNutri");
        setSize(900, 700); // Ajustando o tamanho para o layout
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // --- Painel Principal com Gradiente ---
        CustomGradientPanel mainPanel = new CustomGradientPanel();
        mainPanel.setLayout(new GridBagLayout());
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30); // Padding para os componentes
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int textFieldWidth = 30; // Largura preferencial dos campos

        // --- 1. Logo (Topo Centralizado) ---
        JLabel logoLabel = new JLabel("ConnectNutri", SwingConstants.CENTER);
        URL imageURL = getClass().getResource("ConnectNutri_Logo.png");
        if (imageURL != null) {
            logoLabel.setIcon(new ImageIcon(
                new ImageIcon(imageURL).getImage().getScaledInstance(100, 50, Image.SCALE_SMOOTH)
            ));
        }
        logoLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        logoLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        logoLabel.setFont(new Font("Arial", Font.ITALIC, 20));
        
        if (imageURL == null) {
             logoLabel.setText("ConnectNutri 📅🌿");
        }

        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(50, 0, 50, 0); // Espaço para o logo
        gbc.anchor = GridBagConstraints.CENTER;
        mainPanel.add(logoLabel, gbc);
        
        // Resetando gbc
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 30, 5, 30);
        
        Font labelFont = new Font("Arial", Font.PLAIN, 16);

        // --- 2. Coluna Esquerda ---
        
        // NOME COMPLETO 
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel nomeLabel = new JLabel("Nome completo"); nomeLabel.setFont(labelFont); mainPanel.add(nomeLabel, gbc);
        gbc.gridy = 2;
        JTextField nomeField = new CustomTextField(textFieldWidth); mainPanel.add(nomeField, gbc);

        // CEP
        gbc.gridy = 3;
        JLabel cepLabel = new JLabel("CEP"); cepLabel.setFont(labelFont); mainPanel.add(cepLabel, gbc);
        gbc.gridy = 4;
        JTextField cepField = new CustomTextField(textFieldWidth); mainPanel.add(cepField, gbc);

        // ENDEREÇO e N° (Composto)
        // Linha do Label (Endereço e N°)
        gbc.gridy = 5;
        JPanel labelPanelEnd = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        labelPanelEnd.setOpaque(false);
        JLabel enderecoLabel = new JLabel("Endereço"); enderecoLabel.setFont(labelFont);
        labelPanelEnd.add(enderecoLabel);
        labelPanelEnd.setBorder(new EmptyBorder(0, 0, 0, 80)); // Espaço para o "N°"
        mainPanel.add(labelPanelEnd, gbc);
        
        // Linha dos Fields (Endereço e N°)
        gbc.gridy = 6; 
        JPanel endNumPanel = new JPanel(new GridBagLayout());
        endNumPanel.setOpaque(false);
        GridBagConstraints enGbc = new GridBagConstraints();
        enGbc.insets = new Insets(0, 0, 0, 10);
        enGbc.fill = GridBagConstraints.HORIZONTAL;
        
        JTextField endField = new CustomTextField(25);
        JTextField numField = new CustomTextField(5);
        
        JLabel numLabel = new JLabel("N°"); numLabel.setFont(labelFont);
        
        enGbc.gridx = 0; enGbc.weightx = 0.8; endNumPanel.add(endField, enGbc);
        enGbc.gridx = 1; enGbc.weightx = 0.1; endNumPanel.add(numLabel, enGbc);
        enGbc.gridx = 2; enGbc.weightx = 0.2; endNumPanel.add(numField, enGbc);

        mainPanel.add(endNumPanel, gbc);

        // TELEFONE
        gbc.gridy = 7;
        JLabel telLabel = new JLabel("Telefone"); telLabel.setFont(labelFont); mainPanel.add(telLabel, gbc);
        gbc.gridy = 8;
        JTextField telefoneField = new CustomTextField(textFieldWidth); 
        telefoneField.setText("(81) 9xxxx-xxxx"); // Placeholder
        mainPanel.add(telefoneField, gbc);
        
        
        // --- 3. Coluna Direita ---
        
        // E-MAIL
        gbc.gridx = 1; gbc.gridy = 1;
        JLabel emailLabel = new JLabel("E-mail"); emailLabel.setFont(labelFont); mainPanel.add(emailLabel, gbc);
        gbc.gridy = 2;
        JTextField emailField = new CustomTextField(textFieldWidth); mainPanel.add(emailField, gbc);

        // SENHA
        gbc.gridy = 3;
        JLabel senhaLabel = new JLabel("Senha"); senhaLabel.setFont(labelFont); mainPanel.add(senhaLabel, gbc);
        gbc.gridy = 4;
        JPasswordField senhaField = new CustomPasswordField(textFieldWidth); mainPanel.add(senhaField, gbc);

        // CONFIRMAR SENHA
        gbc.gridy = 5;
        JLabel confSenhaLabel = new JLabel("Confirmar Senha"); confSenhaLabel.setFont(labelFont); mainPanel.add(confSenhaLabel, gbc);
        gbc.gridy = 6;
        JPasswordField confirmarSenhaField = new CustomPasswordField(textFieldWidth); mainPanel.add(confirmarSenhaField, gbc);

        // CHECKBOX TERMOS DE USO
        gbc.gridy = 7; gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(30, 30, 5, 30); // Mais espaço acima
        
        JCheckBox termosCheckbox = new JCheckBox("Eu li e concordo com os termos de uso");
        termosCheckbox.setOpaque(false);
        termosCheckbox.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(termosCheckbox, gbc);
        
        // --- 4. Botão Finalizar Cadastro (No centro abaixo) ---
        
        JButton finalizarButton = new JButton("Finalizar cadastro");
        finalizarButton.setPreferredSize(new Dimension(300, 50));
        finalizarButton.setBackground(Color.DARK_GRAY);
        finalizarButton.setForeground(Color.WHITE);
        finalizarButton.setFocusPainted(false);
        finalizarButton.setBorderPainted(false);
        finalizarButton.setFont(new Font("Arial", Font.BOLD, 18));
        
        // Estilo para simular borda arredondada (recurso de UI customizada no Swing)
        finalizarButton.setBorder(new EmptyBorder(10, 20, 10, 20));
        // Para bordas arredondadas reais, você precisaria criar uma classe JButton customizada (fora do escopo deste exemplo).

        // Posicionamento do botão centralizado, ocupando duas colunas
        gbc.gridx = 0;
        gbc.gridy = 9; // Abaixo de todos os campos
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(50, 0, 50, 0);

        mainPanel.add(finalizarButton, gbc);


        // --- Ação de Integração com LoginScreen --- 
        finalizarButton.addActionListener(e -> {
            // Fecha a tela de cadastro
            this.dispose(); 
            // Abre a tela de Login
            new LoginScreen().setVisible(true);
        });

        // Adiciona o painel principal à janela
        add(mainPanel);
    }

    // Método main para teste independente
    public static void main(String[] args) {
        try {
            // Tenta usar o LookAndFeel do sistema para um visual mais moderno
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            new CadastroScreen().setVisible(true);
        });
    }
}