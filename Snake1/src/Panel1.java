 import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
 
    public class Panel1 extends JPanel implements ActionListener{
        static final int SCREEN_WIDTH = 300;
        static final int SCREEN_HEIGHT = 500;
        private boolean check = false; 
                
        public Panel1(){
            this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
//            this.setBackground(Color.black);
            
            JTextField jTextField = new JTextField();
            
            JButton startButton = new JButton("Start");
            JButton guideButton = new JButton("Guide");
            JButton exitButton = new JButton("Exit");
            
            this.setLayout(new GridLayout(5,5));     
            
            this.add(startButton);
            startButton.setActionCommand("Start");
            startButton.addActionListener(this);
            startButton.setMnemonic(KeyEvent.VK_S);
            
            this.add(guideButton);
            guideButton.setActionCommand("Guide");
            guideButton.addActionListener(this);
            guideButton.setMnemonic(KeyEvent.VK_G);
            
            this.add(exitButton);
            exitButton.setActionCommand("Exit");
            exitButton.addActionListener(this);
            exitButton.setMnemonic(KeyEvent.VK_E);
            
    }
        

    @Override
    public void actionPerformed(ActionEvent e) {
        if("Start".equals(e.getActionCommand()))
        {
            
        }
        if("Guide".equals(e.getActionCommand()))
            JOptionPane.showConfirmDialog(this, "Bạn muốn đọc qua hướng dẫn chứ?");
        
        if("Exit".equals(e.getActionCommand()))
            System.exit(0);
    }
}
