/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package puzzle;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.text.NumberFormatter;
import java.util.Random;


/**
 *
 * @author Tmehyg
 */
public class Autre {
  private ImageIcon[][] partIcons; 
   private JLabel firstClickedLabel;
   private int nombreChangements = 0;
   private JPanel cadrePanel;
   private JLabel[][] partLabels;
   private ImageIcon[][] initialState;
   
   
   
  
      public void Test() {
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
                partLabels = new JLabel[entierSaisi][valeur2];
                ImageIcon icone = createImageIcon("../image/img.jpg", 400, 300);

                
                if (icone != null) {
                    nombreChangements = 0;
                    Image image = icone.getImage();
                    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
                    Graphics2D g2 = bufferedImage.createGraphics();
                    g2.drawImage(image, 0, 0, null);
                    g2.dispose();
                    

                    partIcons = new ImageIcon[entierSaisi][valeur2];
                     int rows = entierSaisi;
                    int cols = valeur2;
                    cadrePanel = new JPanel(new GridLayout(rows, cols, 5, 5));
                        MouseListener mouseListener = new MouseAdapter() {
                            @Override
                            public void mouseClicked(MouseEvent e) {
                                if (e.getSource() instanceof JLabel) {
                                    JLabel clickedLabel = (JLabel) e.getSource();

                                    if (firstClickedLabel == null) {
                                        firstClickedLabel = clickedLabel;
                                    } else {
                                        Icon tempIcon = firstClickedLabel.getIcon();
                                        firstClickedLabel.setIcon(clickedLabel.getIcon());
                                        clickedLabel.setIcon(tempIcon);
                                        nombreChangements++;
                                        afficherMatrice(entierSaisi, valeur2);

                                        if (estPuzzleResolu(entierSaisi, valeur2)) {
                                            JOptionPane.showMessageDialog(fenetrePrincipale, "Puzzle résolu ! ET Nombre de mouvement " +nombreChangements);
                                        }

                                        firstClickedLabel = null;
                                    }
                                }
                            }
                        };
                   
                        for (int j = 0; j < valeur2; j++) {
                            for (int i = 0; i < entierSaisi; i++) {
                            int startX = i * (bufferedImage.getWidth() / entierSaisi);
                            int startY = j * (bufferedImage.getHeight() / valeur2);

                            int partWidth = bufferedImage.getWidth() / entierSaisi;
                            int partHeight = bufferedImage.getHeight() / valeur2;

                            BufferedImage partBufferedImage = bufferedImage.getSubimage(startX, startY, partWidth, partHeight);
                            Image partImage = partBufferedImage.getScaledInstance(partWidth, partHeight, Image.SCALE_SMOOTH);
                            partIcons[i][j] = new ImageIcon(partImage);

                            JLabel partLabel = new JLabel(partIcons[i][j]);
                            cadrePanel.add(partLabel);
                            partLabels[i][j] = partLabel;
                            

                           
                            partLabel.addMouseListener(mouseListener);
                        }
                    }
                        

                        
                            Random random = new Random();
                            JButton boutonMelanger = new JButton("Mélanger");
                            JButton boutonGauche = new JButton("Gauche");
                            
                            boutonGauche.addActionListener(eve -> {
                                if (partLabels != null) {
                                    JLabel[][] tempPartLabels = new JLabel[entierSaisi][valeur2];
                                    for (int j = 0; j < valeur2; j++) {
                                        for (int i = 0; i < entierSaisi; i++) {
                                            tempPartLabels[i][j] = partLabels[j][i];
                                        }
                                    }

                                    partLabels = tempPartLabels;
                                    cadrePanel.removeAll();
                                    for (int i = 0; i < entierSaisi; i++) {
                                        for (int j = 0; j < valeur2; j++) {
                                            cadrePanel.add(partLabels[i][j]);
                                        }
                                    }
                                    afficherMatrice(entierSaisi, valeur2);
                                    cadrePanel.revalidate();
                                    cadrePanel.repaint();
                                    cadrePanel.add(partLabels[0][0], 0);
                                    cadrePanel.add(partLabels[entierSaisi - 1][valeur2 - 1]);
                                } else {
                                    
                                    JOptionPane.showMessageDialog(fenetrePrincipale, "Veuillez d'abord valider pour créer les parties.");
                                }
                            });
                            JButton boutonRotationDroite = new JButton("Droit");
                            boutonRotationDroite.addActionListener(eve -> {
  
                             if (partLabels != null) {
                                 JLabel[][] tempPartLabels = new JLabel[valeur2][entierSaisi];

                                 for (int j = 0; j < valeur2; j++) {
                                    for (int i = 0; i < entierSaisi; i++) {
                                         tempPartLabels[j][entierSaisi - 1 - i] = partLabels[i][j];
                                         
                                     }
                                 }

                                 partLabels = tempPartLabels;
                                 cadrePanel.removeAll();

                                 for (int i = 0; i < valeur2; i++) {
                                     for (int j = 0; j < entierSaisi; j++) {
                                         cadrePanel.add(partLabels[i][j]);
                                     }
                                 }

                                 afficherMatrice(entierSaisi, valeur2);
                                 cadrePanel.revalidate();
                                 cadrePanel.repaint();
                                    
                                } else {
                                    JOptionPane.showMessageDialog(fenetrePrincipale, "Veuillez d'abord valider pour créer les parties.");
                                }
                            });

                            
                            boutonMelanger.addActionListener(ev -> {
                            initialState = new ImageIcon[entierSaisi][valeur2];
                            for (int j = 0; j < valeur2; j++) {
                                    for (int i = 0; i < entierSaisi; i++) {
                                        initialState[i][j] = partIcons[i][j];
                                    }
                                }
                                
                                if (partLabels != null) {
                                 melangerImages(entierSaisi,valeur2);
                                } else {
                                    JOptionPane.showMessageDialog(fenetrePrincipale, "Veuillez d'abord valider pour créer les parties.");
                                }
                            });

                    panelPrincipal.add(boutonMelanger);
                    panelPrincipal.add(boutonGauche);
                    panelPrincipal.add(boutonRotationDroite);
                    JLabel cadre = new JLabel();
                    cadre.setLayout(new BorderLayout());
                    cadre.setIcon(icone);
                    cadre.add(cadrePanel, BorderLayout.CENTER);

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
      
      
    private void melangerImages(int entierSaisi, int valeur2) {
        Random random = new Random();

        for (int j = 0; j < valeur2; j++) {
            for (int i = 0; i < entierSaisi; i++) {
                int randomI = random.nextInt(entierSaisi);
                int randomJ = random.nextInt(valeur2);

                Icon tempIcon = partLabels[i][j].getIcon();
                partLabels[i][j].setIcon(partLabels[randomI][randomJ].getIcon());
                partLabels[randomI][randomJ].setIcon(tempIcon);
            }
        }

        afficherMatrice(entierSaisi, valeur2);
    }
    
        private boolean estPuzzleResolu(int entierSaisi, int valeur2) {
        if (initialState == null || partLabels == null) {
            return false;
        }

        for (int j = 0; j < valeur2; j++) {
            for (int i = 0; i < entierSaisi; i++) {
                if (partLabels[i][j].getIcon() != initialState[i][j]) {
                    return false; 
                }
            }
        }

        return true; 
    }
        
        
        
       /* private void melangerDroite(int entierSaisi, int valeur2) {
JLabel[][] tempPartLabels = new JLabel[valeur2][entierSaisi];

     for (int i = 0; i < entierSaisi; i++) {
        for (int j = 0; j < valeur2; j++) {
          Icon currentIcon = partLabels[i][j].getIcon();

            if (currentIcon instanceof ImageIcon) {
                                                ImageIcon imageIcon = (ImageIcon) currentIcon;

                                                int imageWidth = imageIcon.getIconWidth();
                                                int imageHeight = imageIcon.getIconHeight();
                                                if (imageWidth > 0 && imageHeight > 0) {
                                                    BufferedImage image = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
                                                    Graphics2D g2 = image.createGraphics();
                                                    g2.rotate(Math.toRadians(90), imageWidth / 2.0, imageHeight / 2.0);
                                                    g2.drawImage(imageIcon.getImage(), 0, 0, null);
                                                    g2.dispose();

                                                    ImageIcon rotatedIcon = new ImageIcon(image);
                                                    tempPartLabels[j][entierSaisi - 1 - i] = new JLabel(rotatedIcon);
                                                }
                                            }
                                        }
                                    }

                                    partLabels = tempPartLabels;
                                    cadrePanel.removeAll();

                                    for (int i = 0; i < valeur2; i++) {
                                        for (int j = 0; j < entierSaisi; j++) {
                                            cadrePanel.add(partLabels[i][j]);
                                        }
                                    }

                                    afficherMatrice(entierSaisi, valeur2);
                                    cadrePanel.revalidate();
                                    cadrePanel.repaint();

    afficherMatrice(entierSaisi, valeur2);
}*/



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

    private ImageIcon createImageIcon(String path, int width, int height) {
        URL imgURL = getClass().getResource(path);
        if (imgURL != null) {
            ImageIcon icon = new ImageIcon(imgURL);
            Image image = icon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
            return new ImageIcon(image);
        } else {
            System.err.println("Impossible de trouver le fichier d'image : " + path);
            return null;
        }
    }
    
private void afficherMatrice(int entierSaisi, int valeur2) {
    System.out.println("Matrice après " + nombreChangements + " changements :");

    for (int i = 0; i < entierSaisi; i++) {
        for (int j = 0; j < valeur2; j++) {
            String imageName = "Vide";
            System.out.print("Partie [" + i + "][" + j + "] ");
            
            if (partIcons[i][j] != null) {
                String[] pathSegments = partIcons[i][j].toString().split("/");
                imageName = pathSegments[pathSegments.length - 1].split("@")[0];
            }

            System.out.print(imageName + " ");
        }
        System.out.println();
    }
}
   
/*public void Test() {
    JFrame fenetrePrincipale = new JFrame("Fenêtre 2");
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
                        JLabel cadreInitial = new JLabel();
                        cadreInitial.setIcon(icone);
                        panelPrincipal.add(cadreInitial);

                        diviserDansCadreInitial(cadreInitial, entierSaisi, valeur2);

                        JPanel cadrePanel = new JPanel();
                        JButton boutonRandom = new JButton("Melange");

                        boutonRandom.addActionListener(ev -> {
                            if (cadreInitial.getComponentCount() == 0) {
                                System.out.println("Pas de composants à mélanger.");
                                return;
                            }

                            Component[] labels = cadreInitial.getComponents();
                            List<Component> labelList = Arrays.asList(labels);
                            Collections.shuffle(labelList);

                            cadreInitial.removeAll();
                            cadreInitial.setLayout(new FlowLayout());

                            for (Component label : labelList) {
                                cadreInitial.add(label);
                                System.out.println("Composant ajouté : " + label);
                            }

                            // Ajoutez cadreInitial à panelPrincipal après le mélange si nécessaire
                            panelPrincipal.add(cadreInitial);

                            fenetrePrincipale.revalidate();
                            fenetrePrincipale.repaint();
                        });

                        // Ajoutez cadreInitial à panelPrincipal lors de la création
                        panelPrincipal.add(cadreInitial);
                        panelPrincipal.add(boutonRandom);
                    

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
}*/
 
private void ajouterGestionnaireBoutonRandom(JButton boutonRandom, JLabel cadreInitial) {
    boutonRandom.addActionListener(e -> {
        if (cadreInitial.getIcon() instanceof ImageIcon) {
            return;
        }

        JPanel cadrePanel = (JPanel) cadreInitial.getIcon();
        Component[] labels = cadrePanel.getComponents();
        List<Component> labelList = Arrays.asList(labels);
        Collections.shuffle(labelList);

        cadrePanel.removeAll();
        cadrePanel.setLayout(new FlowLayout()); 

        for (Component label : labelList) {
            cadrePanel.add(label);
        }

        cadreInitial.revalidate();
        cadreInitial.repaint();
    });
}





private void diviserDansCadreInitial(JLabel cadreInitial, int entierSaisi, int valeur2) {
    ImageIcon icone = (ImageIcon) cadreInitial.getIcon();
    Image image = icone.getImage();
    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_ARGB);
    Graphics2D g2 = bufferedImage.createGraphics();
    g2.drawImage(image, 0, 0, null);
    g2.dispose();

    int partWidth = bufferedImage.getWidth() / entierSaisi;
    int partHeight = bufferedImage.getHeight() / valeur2;
    
    System.out.println("Image Width: " + bufferedImage.getWidth());
    System.out.println("Image Height: " + bufferedImage.getHeight());
    System.out.println("Part Width: " + partWidth);
    System.out.println("Part Height: " + partHeight);

    int espaceEntreCases = 1; 

    JPanel cadrePanel = new JPanel(new GridLayout(entierSaisi, valeur2, espaceEntreCases, espaceEntreCases));
    for (int j = 0; j < valeur2; j++) {
        for (int i = 0; i < entierSaisi; i++) {
            int startX = i * (partWidth + espaceEntreCases);
            int startY = j * (partHeight + espaceEntreCases);

            int subImageWidth = Math.min(partWidth, bufferedImage.getWidth() - startX);
            int subImageHeight = Math.min(partHeight, bufferedImage.getHeight() - startY);

            BufferedImage partBufferedImage = bufferedImage.getSubimage(startX, startY, subImageWidth, subImageHeight);
            Image partImage = partBufferedImage.getScaledInstance(subImageWidth, subImageHeight, Image.SCALE_SMOOTH);
            ImageIcon partIcon = new ImageIcon(partImage);

            JLabel partLabel = new JLabel(partIcon);
            cadrePanel.add(partLabel);
        }
    }


    //cadreInitial.setIcon(null); 
    cadreInitial.setLayout(new BorderLayout());
    cadreInitial.add(cadrePanel, BorderLayout.CENTER);
    cadreInitial.revalidate();
    cadreInitial.repaint();
}



    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new Autre().Test();
        });
    }

    
}
