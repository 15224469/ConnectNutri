import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URI;

public class AbrirLink {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Abrir Formulário");
        frame.setSize(300, 150);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JButton botao = new JButton("ww.formulario.com");

        botao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Desktop.getDesktop().browse(new URI("https://forms.gle/L2Q2rNjR73m5mbHD7"));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        frame.add(botao);
        frame.setVisible(true);
    }
}
