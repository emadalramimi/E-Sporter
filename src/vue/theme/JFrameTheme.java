package vue.theme;

import javax.swing.*;

import vue.TitleBar;

import java.awt.*;

public class JFrameTheme extends JFrame {

    private TitleBar titleBar;

    public JFrameTheme() {
        titleBar = new TitleBar();
        setUndecorated(true);
        setLayout(new BorderLayout());
        add(titleBar, BorderLayout.NORTH);
    }

    public TitleBar getTitleBar() {
        return titleBar;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrameTheme frame = new JFrameTheme();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(800, 600);

            JPanel contentPanel = new JPanel();
            contentPanel.setBackground(Color.WHITE);
            frame.add(contentPanel, BorderLayout.CENTER);

            frame.setVisible(true);
        });
    }
}
