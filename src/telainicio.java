package ConnectNutri;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.net.URL;

public class telainicio extends JFrame {

    // --- Componente Customizado para o Fundo Gradiente Principal (Mantido) ---
    private class CustomGradientPanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;

            Color edgeColor = new Color(110, 186, 46);
            Color centerColor = new Color(199, 235, 180);

            GradientPaint gp = new GradientPaint(
                    0, 0, edgeColor,
                    getWidth(), getHeight(), centerColor
            );
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }

    // --- Painel Branco Arredondado para o Conteúdo Principal (Mantido) ---
    private class RoundedContentPanel extends JPanel {
        public RoundedContentPanel() {
            setOpaque(false);
            setLayout(new FlowLayout(FlowLayout.CENTER, 60, 60));
            setBorder(new EmptyBorder(60, 60, 60, 60));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 40, 40);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // --- Card Interno Arredondado para Ação (Mantido) ---
    private class ActionCardPanel extends JPanel {
        private final int radius = 20;

        public ActionCardPanel() {
            setOpaque(false);
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setPreferredSize(new Dimension(380, 250));
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(Color.WHITE);
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), radius, radius);
            g2.dispose();
            super.paintComponent(g);
        }
    }

    // --- Cria o Painel de Ação (Cards internos - Sem Imagem) (Mantido) ---
    private JPanel createActionCard(String title, String buttonText, Color buttonColor) {
        ActionCardPanel card = new ActionCardPanel();

        // --- Título ---
        JLabel titleLabel = new JLabel(title, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setBorder(new EmptyBorder(50, 10, 50, 10));
        card.add(titleLabel);

        // --- Espaçador ---
        card.add(Box.createVerticalGlue());

        // --- Botão ---
        JButton actionButton = new JButton(buttonText);
        actionButton.setBackground(buttonColor);
        actionButton.setForeground(Color.WHITE);
        actionButton.setFont(new Font("Arial", Font.BOLD, 16));
        actionButton.setBorderPainted(false);
        actionButton.setFocusPainted(false);

        JPanel buttonWrapper = new JPanel();
        buttonWrapper.setLayout(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.setOpaque(false);
        buttonWrapper.setBorder(new EmptyBorder(10, 0, 30, 0));
        actionButton.setPreferredSize(new Dimension(250, 50));
        buttonWrapper.add(actionButton);

        card.add(buttonWrapper);

        return card;
    }

    // --- MÉTODO AUXILIAR REAJUSTADO: Cria labels de navegação SOMENTE COM TEXTO ---
    private JLabel createNavLabel(String text, Font font, Color color) {
        // Retorna apenas um JLabel com o texto, eliminando a estrutura vertical e os ícones.
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(font);
        label.setForeground(color);
        label.setBorder(new EmptyBorder(5, 10, 5, 10));
        return label;
    }

    public telainicio() {
        // --- Configuração Básica da Janela ---
        setTitle("Início - ConnectNutri");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // --- 1. Painel Principal com Gradiente (Fundo) ---
        CustomGradientPanel mainPanel = new CustomGradientPanel();
        mainPanel.setLayout(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        // --- 2. Cabeçalho (Barra de Navegação Superior) ---
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(199, 235, 180));
        headerPanel.setBorder(new EmptyBorder(15, 30, 15, 30));

        // Logo (Canto Esquerdo)
        JLabel logoHeader = new JLabel("ConnectNutri");
        // Tentativa de carregar a Logo (sem fallback de símbolo)
        URL imageURL = getClass().getResource("ConnectNutri_Logo.png");
        if (imageURL != null) {
            logoHeader.setIcon(new ImageIcon(
                    new ImageIcon(imageURL).getImage().getScaledInstance(140, 60, Image.SCALE_SMOOTH)
            ));
        } else {
            // Apenas o texto ConnectNutri
            logoHeader.setText("ConnectNutri");
        }
        logoHeader.setFont(new Font("Serif", Font.BOLD, 18));
        logoHeader.setForeground(new Color(40, 110, 40));
        headerPanel.add(logoHeader, BorderLayout.WEST);

        // Opções de Navegação (Centro)
        // Volto para FlowLayout, pois não precisamos mais da estrutura vertical complexa
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 0));
        navPanel.setOpaque(false);

        Font navFont = new Font("Arial", Font.BOLD, 14);
        Color navColor = new Color(50, 50, 50);

        // Usando o método que cria apenas o texto
        navPanel.add(createNavLabel("INÍCIO", navFont, navColor));
        navPanel.add(createNavLabel("SUPORTE", navFont, navColor));
        navPanel.add(createNavLabel("CONSULTAS", navFont, navColor));

        headerPanel.add(navPanel, BorderLayout.CENTER);

        // Perfil (Canto Direito)
        JPanel profilePanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 0));
        profilePanel.setOpaque(false);

        // Apenas a palavra "Perfil"
        JLabel profileNameLabel = new JLabel("Perfil");
        profileNameLabel.setFont(navFont);
        profilePanel.add(profileNameLabel);

        // Seta de Dropdown (Mantida, pois não é um placeholder de ícone)
        JLabel dropdownArrow = new JLabel("▼");
        dropdownArrow.setFont(new Font("Arial", Font.BOLD, 10));
        profilePanel.add(dropdownArrow);

        headerPanel.add(profilePanel, BorderLayout.EAST);

        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // --- 3. Conteúdo Principal (Card Grande Arredondado) ---
        RoundedContentPanel contentWrapper = new RoundedContentPanel();
        mainPanel.add(contentWrapper, BorderLayout.CENTER);

        // --- Layout Interno do Conteúdo (Ações) ---

        // --- Card 1: Marcar Consulta (Sem Imagem) ---
        JPanel card1 = createActionCard(
                "Marque aqui a sua consulta",
                "Agendar Consulta",
                new Color(15, 76, 129)
        );

        // --- Card 2: Fale com a equipe (Sem Imagem) ---
        JPanel card2 = createActionCard(
                "Fale com a nossa equipe",
                "Atendimento",
                new Color(15, 76, 129)
        );

        contentWrapper.add(card1);
        contentWrapper.add(card2);
    }

    // Método main para inicializar a tela
    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        SwingUtilities.invokeLater(() -> {
            new telainicio().setVisible(true);
        });
    }
}