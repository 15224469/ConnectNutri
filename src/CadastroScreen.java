package ConnectNutri;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class CadastroScreen extends JFrame {

    // --- Componente Customizado para o Fundo Gradiente (Melhorado) ---
    private class CustomGradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            // Cores mais próximas ao fundo da tela de Login (Verde Mais Escuro -> Verde Claro)
            Color edgeColor = new Color(110, 186, 46); // Mais escuro (verde da borda do login)
            Color centerColor = new Color(199, 235, 180); // Mais claro (verde do centro do login)

            GradientPaint gp = new GradientPaint(
                    0, 0, edgeColor,
                    getWidth(), getHeight(), centerColor
            );
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // --- Campo de Texto Customizado para replicar a linha de separação ---
    private class CustomTextField extends JTextField {
        public CustomTextField(int columns) {
            super(columns);
            // Borda inferior preta (semelhante ao login)
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            setFont(new Font("Arial", Font.PLAIN, 16));
            setOpaque(false); // Fundo transparente
        }
    }

    // --- Campo de Senha Customizado ---
    private class CustomPasswordField extends JPasswordField {
        public CustomPasswordField(int columns) {
            super(columns);
            // Borda inferior preta (semelhante ao login)
            setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
            setFont(new Font("Arial", Font.PLAIN, 16));
            setOpaque(false); // Fundo transparente
        }
    }

    // --- Card Branco Arredondado para o Formulário ---
    private class CustomCardPanel extends JPanel {
        public CustomCardPanel() {
            setOpaque(false); // Garante que o fundo do Card seja transparente para desenharmos.
            setLayout(new GridBagLayout());
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            // Desenha um retângulo branco com cantos arredondados (raio 40)
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    public CadastroScreen() {
        // --- Configuração Básica da Janela ---
        setTitle("Cadastro do Paciente - ConnectNutri");
        setSize(950, 750); // Ajustando o tamanho para melhor visualização do card
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- 1. Painel Principal com Gradiente ---
        CustomGradientPanel mainPanel = new CustomGradientPanel();
        mainPanel.setLayout(new GridBagLayout()); // Usaremos um GridBagLayout para centralizar o card
        add(mainPanel, BorderLayout.CENTER);

        // --- 2. Card Branco Centralizado ---
        CustomCardPanel cardPanel = new CustomCardPanel();
        cardPanel.setPreferredSize(new Dimension(850, 650)); // Tamanho fixo para o card

        // Adiciona o card ao painel principal, que o centraliza
        mainPanel.add(cardPanel);

        // --- 3. Layout dos Componentes (dentro do Card) ---
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 30, 10, 30); // Padding para os componentes
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        int textFieldWidth = 30; // Largura preferencial dos campos
        Font labelFont = new Font("Arial", Font.PLAIN, 16);

        // --- LOGO (Topo Centralizado) ---
        JLabel logoLabel = new JLabel("ConnectNutri");
        URL imageURL = getClass().getResource("ConnectNutri_Logo.png");
        // Tenta usar a imagem, se não, usa a string com ícones
        if (imageURL != null) {
            logoLabel.setIcon(new ImageIcon(
                    new ImageIcon(imageURL).getImage().getScaledInstance(120, 70, Image.SCALE_SMOOTH)
            ));
        } else {
            logoLabel.setText("ConnectNutri");
        }
        logoLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        logoLabel.setVerticalTextPosition(SwingConstants.BOTTOM);
        // Estilo de fonte similar ao do LoginScreen
        logoLabel.setFont(new Font("Serif",Font.PLAIN, 38));
        logoLabel.setForeground(new Color(40, 110, 40));

        gbc.gridwidth = 2; // Ocupa duas colunas
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(30, 240, 40, 0); // Espaço para o logo
        gbc.anchor = GridBagConstraints.CENTER;
        cardPanel.add(logoLabel, gbc);

        // Resetando gbc para o formulário
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(10, 30, 5, 30);

        // ------------------------------------
        // --- Formulário em Duas Colunas ---
        // ------------------------------------

        // --- Coluna Esquerda (NOME COMPLETO, CEP, ENDEREÇO, TELEFONE) ---

        // NOME COMPLETO
        gbc.gridx = 0; gbc.gridy = 1;
        JLabel nomeLabel = new JLabel("Nome completo"); nomeLabel.setFont(labelFont); cardPanel.add(nomeLabel, gbc);
        gbc.gridy = 2;
        JTextField nomeField = new CustomTextField(textFieldWidth); cardPanel.add(nomeField, gbc);

        // CEP
        gbc.gridy = 3;
        JLabel cepLabel = new JLabel("CEP"); cepLabel.setFont(labelFont); cardPanel.add(cepLabel, gbc);
        gbc.gridy = 4;
        JTextField cepField = new CustomTextField(textFieldWidth); cardPanel.add(cepField, gbc);

        // ENDEREÇO e N° (Composto)
        // Linha do Label (Endereço e N°)
        gbc.gridy = 5;
        // Ajuste para replicar o Endereço e N° da imagem (usando um FlowLayout para posicionar os labels)
        JPanel labelPanelEnd = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        labelPanelEnd.setOpaque(false);
        JLabel enderecoLabel = new JLabel("Endereço"); enderecoLabel.setFont(labelFont);
        labelPanelEnd.add(enderecoLabel);
        // Adiciona um espaçamento (separador) e o label N° no final, como na imagem.
        labelPanelEnd.add(Box.createHorizontalStrut(200));
        JLabel numLabelInHeader = new JLabel("N°"); numLabelInHeader.setFont(labelFont);
        labelPanelEnd.add(numLabelInHeader);
        cardPanel.add(labelPanelEnd, gbc);

        // Linha dos Fields (Endereço e N°)
        gbc.gridy = 6;
        JPanel endNumPanel = new JPanel(new GridBagLayout());
        endNumPanel.setOpaque(false);
        GridBagConstraints enGbc = new GridBagConstraints();
        enGbc.insets = new Insets(0, 0, 0, 10);
        enGbc.fill = GridBagConstraints.HORIZONTAL;

        // Endereço ocupa a maior parte
        JTextField endField = new CustomTextField(25);
        // Número ocupa uma parte menor
        JTextField numField = new CustomTextField(5);

        // Aqui, removemos o label "N°" pois ele já está na linha de cima (como na imagem original)
        enGbc.gridx = 0; enGbc.weightx = 0.7; endNumPanel.add(endField, enGbc);
        enGbc.gridx = 1; enGbc.weightx = 0.3; endNumPanel.add(numField, enGbc);

        cardPanel.add(endNumPanel, gbc);

        // TELEFONE
        gbc.gridy = 7;
        JLabel telLabel = new JLabel("Telefone"); telLabel.setFont(labelFont); cardPanel.add(telLabel, gbc);
        gbc.gridy = 8;
        JTextField telefoneField = new CustomTextField(textFieldWidth);
        telefoneField.setText("(81) 9xxxx-xxxx"); // Placeholder
        cardPanel.add(telefoneField, gbc);


        // --- Coluna Direita (E-MAIL, SENHA, CONFIRMAR SENHA, TERMOS DE USO) ---

        // E-MAIL
        gbc.gridx = 1; gbc.gridy = 1;
        JLabel emailLabel = new JLabel("E-mail"); emailLabel.setFont(labelFont); cardPanel.add(emailLabel, gbc);
        gbc.gridy = 2;
        JTextField emailField = new CustomTextField(textFieldWidth); cardPanel.add(emailField, gbc);

        // SENHA
        gbc.gridy = 3;
        JLabel senhaLabel = new JLabel("Senha"); senhaLabel.setFont(labelFont); cardPanel.add(senhaLabel, gbc);
        gbc.gridy = 4;
        JPasswordField senhaField = new CustomPasswordField(textFieldWidth); cardPanel.add(senhaField, gbc);

        // CONFIRMAR SENHA
        gbc.gridy = 5;
        JLabel confSenhaLabel = new JLabel("Confirmar Senha"); confSenhaLabel.setFont(labelFont); cardPanel.add(confSenhaLabel, gbc);
        gbc.gridy = 6;
        JPasswordField confirmarSenhaField = new CustomPasswordField(textFieldWidth); cardPanel.add(confirmarSenhaField, gbc);

        // CHECKBOX TERMOS DE USO
        gbc.gridy = 7; gbc.anchor = GridBagConstraints.SOUTHWEST;
        gbc.insets = new Insets(30, 30, 5, 30); // Mais espaço acima

        JCheckBox termosCheckbox = new JCheckBox("Eu li e concordo com os termos de uso");
        termosCheckbox.setOpaque(false);
        termosCheckbox.setFont(new Font("Arial", Font.PLAIN, 14));
        cardPanel.add(termosCheckbox, gbc);

        // --- Botão Finalizar Cadastro (No centro abaixo) ---

        JButton finalizarButton = new JButton("Finalizar cadastro");
        finalizarButton.setPreferredSize(new Dimension(330, 100)); // Aumenta o tamanho para ficar como os botões de login

        // Estilização do botão
        finalizarButton.setBackground(new Color(40, 40, 40));
        finalizarButton.setForeground(Color.WHITE);
        finalizarButton.setFont(new Font("Arial", Font.BOLD, 22));
        finalizarButton.setFocusPainted(false); // Remove a borda de foco
        finalizarButton.setBorderPainted(false); // Garante que a borda seja simples ou não exista
        // finalizarButton.setUI(new RoundedButtonUI()); // COMENTADO para ter cantos retos

        // Posicionamento do botão centralizado, ocupando duas colunas
        gbc.gridx = 0;
        gbc.gridy = 9; // Abaixo de todos os campos
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(50, 0, 50, 0);

        cardPanel.add(finalizarButton, gbc);

        // --- Ação de Integração com LoginScreen ---
        finalizarButton.addActionListener(e -> {
            // Fecha a tela de cadastro
            this.dispose();
            // Abre a tela de Login
            new LoginScreen().setVisible(true);
        });
    }

    // Método main para teste independente
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            new CadastroScreen().setVisible(true);
        });
    }
}