/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzzle;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.NumberFormat;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.text.NumberFormatter;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author Tmehyg
 */
public class Fond{
    
public void MaFenetre() {
        JFrame fenetrePrincipale = new JFrame("Fenêtre");
        fenetrePrincipale.setSize(800, 600);
        fenetrePrincipale.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panelPrincipal = new JPanel(new FlowLayout(FlowLayout.LEFT));
        fenetrePrincipale.add(panelPrincipal);

        JFormattedTextField zoneDeTexte = createFormattedTextField();
        panelPrincipal.add(zoneDeTexte);

        JFormattedTextField zoneTexte2 = createFormattedTextField();
        panelPrincipal.add(zoneTexte2);

        JButton bouton = new JButton("Valider");
        bouton.addActionListener(e -> {
            Object value = zoneDeTexte.getValue();
            Object value2 = zoneTexte2.getValue();
            if (value instanceof Integer && value2 instanceof Integer) {
                int entierSaisi = (int) value;
                int valeur2 = (int) value2;

                ImageIcon icone = createImageIcon("../image/img.jpg", 400, 300);

                if (icone != null) {
                    Image image = icone.getImage();
                    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = bufferedImage.createGraphics();
                    g2.drawImage(image, 0, 0, null);
                    g2.dispose();
                    
                    System.out.println("heig "+ bufferedImage.getHeight());
                    System.out.println("weid " + bufferedImage.getWidth());
                    int partWidth = bufferedImage.getWidth()/ entierSaisi;
                    int partHeight = bufferedImage.getHeight() / valeur2;
                     
                    System.out.println("partWidth " + partWidth);
                     System.out.println("partHeight " + partHeight);
                     
                    int rows = entierSaisi;
                    int cols = valeur2;
                    JPanel cadrePanel = new JPanel(new GridLayout(rows, cols, 5, 5));

                    for (int i = 0; i < entierSaisi; i++) {
                        for (int j = 0; j < valeur2; j++) {
                            int startX = i * partWidth;
                            int startY = j * partHeight;

                            //System.out.println("startX " +startX + " startY " + startY);
                            //System.out.println("matric "+ " i " +i + " y "+ j);
                            //System.out.print("Partie [" + i + "][" + j + "] ");
                           BufferedImage partBufferedImage = bufferedImage.getSubimage(startX, startY, partWidth, partHeight);
                            Image partImage = partBufferedImage.getScaledInstance(partWidth, partHeight, Image.SCALE_SMOOTH);
                            ImageIcon partIcon = new ImageIcon(partImage);

                            JLabel partLabel = new JLabel(partIcon);
                            cadrePanel.add(partLabel);
                            
                        }
                    }

                    // Ajouter le cadrePanel au cadre
                    JLabel cadre = new JLabel();
                    cadre.setLayout(new BorderLayout());
                    cadre.setIcon(icone);
                    cadre.add(cadrePanel, BorderLayout.CENTER);

                    // Ajouter le cadre à la fenêtre
                    panelPrincipal.add(cadre);

                    fenetrePrincipale.revalidate();
                    fenetrePrincipale.repaint();
                } else {
                    JOptionPane.showMessageDialog(fenetrePrincipale, "Impossible de charger l'image.");
                }
            } else {
                JOptionPane.showMessageDialog(fenetrePrincipale, "Veuillez saisir des nombres entiers valides.");
            }
        });

        panelPrincipal.add(bouton);

        fenetrePrincipale.setLayout(new FlowLayout());
        fenetrePrincipale.setVisible(true);
    }
        
    protected JPanel createImagePanel(ImageIcon image, String legende) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel imageLabel = new JLabel(image);
        panel.add(imageLabel, BorderLayout.CENTER);

        TitledBorder titledBorder = BorderFactory.createTitledBorder(legende);
        panel.setBorder(titledBorder);

        return panel;
    }
     
   /* private JFormattedTextField createFormattedTextField() {
        return new JFormattedTextField();
    }*/
    
        protected ImageIcon createImageIcon(String path, int width, int height) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon originalIcon = new ImageIcon(imgURL);
            Image image = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } else {
            System.err.println("Impossible de trouver le fichier d'image : " + path);
            return null;
        }
}
    private JFormattedTextField createFormattedTextField() {
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(0);
        formatter.setMaximum(Integer.MAX_VALUE);

        JFormattedTextField formattedTextField = new JFormattedTextField(formatter);
        formattedTextField.setColumns(5);
        return formattedTextField;
    }
    
        private JFormattedTextField createFormattedTextField2() {
        NumberFormat format = NumberFormat.getInstance();
        NumberFormatter formatter = new NumberFormatter(format);
        formatter.setValueClass(Integer.class);
        formatter.setMinimum(1);
        formatter.setMaximum(Integer.MAX_VALUE);

        JFormattedTextField formattedTextField = new JFormattedTextField(formatter);
        formattedTextField.setColumns(5);
        return formattedTextField;
    }
}
