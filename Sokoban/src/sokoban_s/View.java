package sokoban_s;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;
import sokoban_s.LevelLoader.Level;


public class View extends javax.swing.JFrame {

    private Model model;
    private BufferedImage tiles[] = new BufferedImage[6];
    private BufferedImage bg;

    private double screenScale;
    /**
     * Creates new form View
     */
    public View() {
        initComponents();
        setTitle("Sokoban");
        setLocationRelativeTo(null);
        
        String levelString = JOptionPane.showInputDialog("Enter level (1-5):");
        
        Level level = LevelLoader.get(levelString);
        model = new Model(level);
        
        double screenScaleX = (double) (64 * level.width + 0) / getWidth();
        double screenScaleY = (double) (64 * level.height + 25) / getHeight();
        screenScale = screenScaleX > screenScaleY ? screenScaleX : screenScaleY;
        
        
        try {
            tiles[0] = ImageIO.read(getClass().getResource("character.png"));
            tiles[1] = ImageIO.read(getClass().getResource("wall.png"));
            tiles[2] = ImageIO.read(getClass().getResource("ground.png"));
            tiles[3] = ImageIO.read(getClass().getResource("box.png"));
            tiles[4] = ImageIO.read(getClass().getResource("box_blue.png"));
            tiles[5] = ImageIO.read(getClass().getResource("dest.png"));
        } catch (IOException ex) {
            
            ex.printStackTrace();
            System.exit(-1);
        }
        bg = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 654, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 493, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new View().setVisible(true);
            }
        });
    }
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

    @Override
    public void paint(Graphics g) {
        //super.paint(g);
        draw(bg.createGraphics());
        g.drawImage(bg, 0, 0, null);
    }
    
    public void draw(Graphics2D g) {
        g.setBackground(Color.GRAY);
        g.clearRect(0, 0, getWidth(), getHeight());
        g.translate(0, 25);
        
        g.scale(1 / screenScale, 1 / screenScale);
        
        
        int tileSize = 64;
        char[][] map = model.getMap();
        for (int y = 0; y < map[0].length; y++) {
            for (int x = 0; x < map.length; x++) {
                   
    
                g.drawImage(tiles[2], x*tileSize, y*tileSize, null);
                if (map[x][y] == 'H') {
                    g.drawImage(tiles[1], x*tileSize, y*tileSize, null);
                }
                if (map[x][y] == 'B') {
                    g.drawImage(tiles[5], x*tileSize+tileSize/4, y*tileSize+tileSize/4, null);
                }
                if (map[x][y] == 'F') {
                    g.drawImage(tiles[4], x*tileSize, y*tileSize, null);
                }
                if (map[x][y] == 'D') {
                    g.drawImage(tiles[3], x*tileSize, y*tileSize, null);
                }
            }
        }        
        g.drawImage(tiles[0], model.getP().x*tileSize+tileSize/4, model.getP().y*tileSize, this);
    }

    @Override
    protected void processKeyEvent(KeyEvent ke) {
        if (ke.getID() != KeyEvent.KEY_PRESSED || model.isCompleted()) {
            return;
        }
        model.move(ke.getKeyCode());
        repaint();
        if (model.isCompleted()) {
            JOptionPane.showMessageDialog(this, "Congratulations ! Level cleared !");
            //levelString
            //String levelString = JOptionPane.showInputDialog("Enter level (1-5):");
            
        }
    }
    
}
