/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.miage.millan.feuxcarrefourihm;

import fr.miage.millan.feuxcarrefourihm.objetsGraphiques.BlocFeux;
import java.util.ArrayList;
import javax.swing.Timer;

/**
 *La classe principale de l'application, elle représente un carrefour avec 4 feux de signalisation syncrhonisés 2 à 2
 * 
 * @author aympa
 */
public class IntersectionFeux extends javax.swing.JFrame {

    //DEFINITION DES ETATS
    /**
     * Les différents états possibles 
     * H pour horizontaux et V pour verticaux (les feux)
     */
    enum Etat {
        eEteint,
        eHRouge,
        eHVert,
        eHOrange,
        eVRouge,
        eVVert,
        eVOrange,
        ePanneEteint,
        ePanneOrange,
    }
    
    /**
     * L'état courant du système 
     */
    private static Etat etat;
    
    /**
     * Méthode permettant de changer d'état en gérant les activations/désactivations de composants générant des évènements
     * @param etat Le nouvel état vers lequel on veut changer
     */
    private void changerEtat(Etat etat){
        IntersectionFeux.etat = etat;
        
        switch(IntersectionFeux.etat){
            case eEteint :
                jButtonMarche.setEnabled(true);
                jButtonArret.setEnabled(false);
                jButtonPanne.setEnabled(false);
                timerHOrange.stop();
                timerHRouge.stop();
                timerHVert.stop();
                timerVOrange.stop();
                timerVRouge.stop();
                timerVVert.stop();
                timerPanneEteint.stop();
                timerPanneOrange.stop();
                break;
                
            case eHRouge :
                jButtonMarche.setEnabled(false);
                jButtonArret.setEnabled(true);
                jButtonPanne.setEnabled(true);
                timerHOrange.stop();
                timerHRouge.start();
                timerHVert.stop();
                timerVOrange.stop();
                timerVRouge.stop();
                timerVVert.stop();
                timerPanneEteint.stop();
                timerPanneOrange.stop();
                break;
                
            case eHVert :
                jButtonMarche.setEnabled(false);
                jButtonArret.setEnabled(true);
                jButtonPanne.setEnabled(true);
                timerHOrange.stop();
                timerHRouge.stop();
                timerHVert.start();
                timerVOrange.stop();
                timerVRouge.stop();
                timerVVert.stop();
                timerPanneEteint.stop();
                timerPanneOrange.stop();
                break;
                
            case eHOrange :
                jButtonMarche.setEnabled(false);
                jButtonArret.setEnabled(true);
                jButtonPanne.setEnabled(true);
                timerHOrange.start();
                timerHRouge.stop();
                timerHVert.stop();
                timerVOrange.stop();
                timerVRouge.stop();
                timerVVert.stop();
                timerPanneEteint.stop();
                timerPanneOrange.stop();
                break;
                
            case eVRouge :
                jButtonMarche.setEnabled(false);
                jButtonArret.setEnabled(true);
                jButtonPanne.setEnabled(true);
                timerHOrange.stop();
                timerHRouge.stop();
                timerHVert.stop();
                timerVOrange.stop();
                timerVRouge.start();
                timerVVert.stop();
                timerPanneEteint.stop();
                timerPanneOrange.stop();
                break;
                
            case eVVert :
                jButtonMarche.setEnabled(false);
                jButtonArret.setEnabled(true);
                jButtonPanne.setEnabled(true);
                timerHOrange.stop();
                timerHRouge.stop();
                timerHVert.stop();
                timerVOrange.stop();
                timerVRouge.stop();
                timerVVert.start();
                timerPanneEteint.stop();
                timerPanneOrange.stop();
                break;
                
            case eVOrange :
                jButtonMarche.setEnabled(false);
                jButtonArret.setEnabled(true);
                jButtonPanne.setEnabled(true);
                timerHOrange.stop();
                timerHRouge.stop();
                timerHVert.stop();
                timerVOrange.start();
                timerVRouge.stop();
                timerVVert.stop();
                timerPanneEteint.stop();
                timerPanneOrange.stop();
                break;
                
            case ePanneEteint :
                jButtonMarche.setEnabled(true);
                jButtonArret.setEnabled(true);
                jButtonPanne.setEnabled(false);
                timerHOrange.stop();
                timerHRouge.stop();
                timerHVert.stop();
                timerVOrange.stop();
                timerVRouge.stop();
                timerVVert.stop();
                timerPanneEteint.start();
                timerPanneOrange.stop();
                break;
                
            case ePanneOrange :
                jButtonMarche.setEnabled(true);
                jButtonArret.setEnabled(true);
                jButtonPanne.setEnabled(false);
                timerHOrange.stop();
                timerHRouge.stop();
                timerHVert.stop();
                timerVOrange.stop();
                timerVRouge.stop();
                timerVVert.stop();
                timerPanneEteint.stop();
                timerPanneOrange.start();
                break;                
        }
    }
    
    
    
    //INITIALISATION
    
    /**
     * Constructeur, appelé lors de l'ouverture de la page
     */
    public IntersectionFeux() {
        initComponents();
        
        //Initialisation des timers
        initialiserTimers();    
        //Intitialisation des ArrayList de feux
        initialiserListesFeux();
        
        //On commence par l'état eTeint
        changerEtat(Etat.eEteint);
        
        //On commence avec tous les feux éteints
        eteindreTout();
    }

    /**
     * Méthode regroupant l'initialisation de tous les timers 
     * C'est ici qu'on choisira les durée des ticks
     */
    private void initialiserTimers(){
        timerHRouge  = new Timer(3000, timerHRougeActionPerformed -> timerHRougeActionPerformed(timerHRougeActionPerformed));
        timerHOrange = new Timer(2000, timerHOrangeActionPerformed -> timerHOrangeActionPerformed(timerHOrangeActionPerformed));
        timerHVert   = new Timer(4000, timerHVertActionPerformed -> timerHVertActionPerformed(timerHVertActionPerformed));
        
        timerVRouge  = new Timer(2000, timerVRougeActionPerformed -> timerVRougeActionPerformed(timerVRougeActionPerformed));
        timerVOrange = new Timer(2000, timerVOrangeActionPerformed -> timerVOrangeActionPerformed(timerVOrangeActionPerformed));
        timerVVert   = new Timer(3000, timerVVertActionPerformed -> timerVVertActionPerformed(timerVVertActionPerformed));
        
        timerPanneEteint       = new Timer(500, timerPanneEteintActionPerformed -> timerPanneEteintActionPerformed(timerPanneEteintActionPerformed));
        timerPanneOrange       = new Timer(500, timerPanneOrangeOrangeActionPerformed -> timerPanneOrangeActionPerformed(timerPanneOrangeOrangeActionPerformed));
    }
    
    /**
     * Initialise les deux ArrayList feuxV pour les feux verticaux (haut et bas) et feuxH pour lse feux horizontaux (gauche et droite)
     */
    private void initialiserListesFeux(){
        //Feux verticaux (haut et bas)
        feuxV = new ArrayList<>();
        feuxV.add(blocFeuxHaut);
        feuxV.add(blocFeuxBas);
        
        //Feux horizontaux (droite et gauche)
        feuxH = new ArrayList<>();
        feuxH.add(blocFeuxDroite);
        feuxH.add(blocFeuxGauche);
    }
    
    //ACTIONS
    /**
     * Eteint toutes les lumières de tous les feux
     */
    private void eteindreTout(){
        blocFeuxHaut.eteindTout();
        blocFeuxBas.eteindTout();
        blocFeuxDroite.eteindTout();
        blocFeuxGauche.eteindTout();
    }
    
    /**
     * Permet d'allumer seulement les ampoules VERTES d'une liste de BlocFeux 
     * @param feux Les feux qu'on veut allumer (on y passe feuxV ou feuxH)
     */
    private void allumerSeulementVert(ArrayList<BlocFeux> feux){
        feux.forEach((feu) -> {
            feu.allumeSeulementVert();
        });
    }
    
    /**
     * Permet d'allumer seulement les ampoules ROUGES d'une liste de BlocFeux 
     * @param feux Les feux qu'on veut allumer (on y passe feuxV ou feuxH)
     */
    private void allumerSeulementRouge(ArrayList<BlocFeux> feux){
        feux.forEach((feu) -> {
            feu.allumeSeulementRouge();
        });
    }
    
    /**
     * Permet d'allumer les ampoules ROUGES de tous les feux
     */
    private void allumerSeulementRouge(){
        allumerSeulementRouge(feuxH);
        allumerSeulementRouge(feuxV);
    }
    
    /**
     * Permet d'allumer seulement les ampoules ORANGES d'une liste de BlocFeux 
     * @param feux Les feux qu'on veut allumer (on y passe feuxV ou feuxH)
     */
    private void allumerSeulementOrange(ArrayList<BlocFeux> feux){
        feux.forEach((feu) -> {
            feu.allumeSeulementOrange();
        });
    }
    
    /**
     * Permet d'allumer les ampoules ORANGES de tous les feux
     */
    private void allumerSeulementOrange(){
        allumerSeulementOrange(feuxH);
        allumerSeulementOrange(feuxV);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        blocFeuxGauche = new fr.miage.millan.feuxcarrefourihm.objetsGraphiques.BlocFeux();
        blocFeuxHaut = new fr.miage.millan.feuxcarrefourihm.objetsGraphiques.BlocFeux();
        blocFeuxBas = new fr.miage.millan.feuxcarrefourihm.objetsGraphiques.BlocFeux();
        blocFeuxDroite = new fr.miage.millan.feuxcarrefourihm.objetsGraphiques.BlocFeux();
        jGridPanelBoutons = new javax.swing.JPanel();
        jButtonMarche = new javax.swing.JButton();
        jButtonArret = new javax.swing.JButton();
        jButtonPanne = new javax.swing.JButton();
        pictureBackground = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMaximumSize(new java.awt.Dimension(600, 600));
        setMinimumSize(new java.awt.Dimension(600, 600));
        setName("framePrincipale"); // NOI18N
        setPreferredSize(new java.awt.Dimension(600, 600));
        setResizable(false);
        getContentPane().setLayout(null);
        getContentPane().add(blocFeuxGauche);
        blocFeuxGauche.setBounds(180, 250, 40, 120);
        getContentPane().add(blocFeuxHaut);
        blocFeuxHaut.setBounds(230, 90, 40, 120);
        getContentPane().add(blocFeuxBas);
        blocFeuxBas.setBounds(340, 360, 40, 120);
        getContentPane().add(blocFeuxDroite);
        blocFeuxDroite.setBounds(380, 220, 40, 120);

        jGridPanelBoutons.setBackground(new java.awt.Color(34, 177, 76));
        jGridPanelBoutons.setLayout(new java.awt.GridLayout(3, 1, 15, 15));

        jButtonMarche.setBackground(new java.awt.Color(105, 87, 52));
        jButtonMarche.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imageButtMarche.png"))); // NOI18N
        jButtonMarche.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonMarche.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonMarcheActionPerformed(evt);
            }
        });
        jGridPanelBoutons.add(jButtonMarche);

        jButtonArret.setBackground(new java.awt.Color(105, 87, 52));
        jButtonArret.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imageButtArret.png"))); // NOI18N
        jButtonArret.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonArret.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonArretActionPerformed(evt);
            }
        });
        jGridPanelBoutons.add(jButtonArret);

        jButtonPanne.setBackground(new java.awt.Color(105, 87, 52));
        jButtonPanne.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imageButtPanne.png"))); // NOI18N
        jButtonPanne.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButtonPanne.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPanneActionPerformed(evt);
            }
        });
        jGridPanelBoutons.add(jButtonPanne);

        getContentPane().add(jGridPanelBoutons);
        jGridPanelBoutons.setBounds(20, 10, 120, 190);

        pictureBackground.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imageBackground.png"))); // NOI18N
        getContentPane().add(pictureBackground);
        pictureBackground.setBounds(0, -10, 610, 610);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    //EVENEMENTS
    
    private void jButtonMarcheActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonMarcheActionPerformed
        switch (IntersectionFeux.etat) {
            case eEteint:
                changerEtat(Etat.eHRouge);
                allumerSeulementRouge();
                break;
                
            case eHRouge:
                //Impossible
                throw new IllegalStateException();
                
            case eHVert:
                //Impossible
                throw new IllegalStateException();
                
            case eHOrange:
                //Impossible
                throw new IllegalStateException();
                
            case eVRouge:
                //Impossible
                throw new IllegalStateException();
                
            case eVVert:
                //Impossible
                throw new IllegalStateException();
                
            case eVOrange:
                //Impossible
                throw new IllegalStateException();
                
            case ePanneEteint:
                changerEtat(Etat.eHRouge);
                allumerSeulementRouge();
                break;
                
            case ePanneOrange:
                changerEtat(Etat.eHRouge);
                allumerSeulementRouge();
                break;              
        }
    }//GEN-LAST:event_jButtonMarcheActionPerformed

    private void jButtonArretActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonArretActionPerformed
        switch(IntersectionFeux.etat){
            case eEteint :
                //Impossible
                throw new IllegalStateException();
                
            case eHRouge :
                changerEtat(Etat.eEteint);
                eteindreTout();
                break;
                
            case eHVert :
                changerEtat(Etat.eEteint);
                eteindreTout();
                break;
                
            case eHOrange :
                changerEtat(Etat.eEteint);
                eteindreTout();
                break;
                
            case eVRouge :
                changerEtat(Etat.eEteint);
                eteindreTout();
                break;
                
            case eVVert :
                changerEtat(Etat.eEteint);
                eteindreTout();
                break;
                
            case eVOrange :
                changerEtat(Etat.eEteint);
                eteindreTout();
                break;
                
            case ePanneEteint :
                changerEtat(Etat.eEteint);
                eteindreTout();
                break;
                
            case ePanneOrange:
                changerEtat(Etat.eEteint);
                eteindreTout();
                break;
        }
    }//GEN-LAST:event_jButtonArretActionPerformed

    private void jButtonPanneActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPanneActionPerformed
        switch (IntersectionFeux.etat) {
            case eEteint:
                //Impossible
                throw new IllegalStateException();

            case eHRouge:
                changerEtat(Etat.ePanneEteint);
                eteindreTout();
                break;

            case eHVert:
                changerEtat(Etat.ePanneEteint);
                eteindreTout();
                break;

            case eHOrange:
                changerEtat(Etat.ePanneEteint);
                eteindreTout();
                break;

            case eVRouge:
                changerEtat(Etat.ePanneEteint);
                eteindreTout();
                break;

            case eVVert:
                changerEtat(Etat.ePanneEteint);
                eteindreTout();
                break;

            case eVOrange:
                changerEtat(Etat.ePanneEteint);
                eteindreTout();
                break;

            case ePanneEteint:
                //Impossible
                throw new IllegalStateException();

            case ePanneOrange:
                //Impossible
                throw new IllegalStateException();
        }
    }//GEN-LAST:event_jButtonPanneActionPerformed

    private void timerHRougeActionPerformed(java.awt.event.ActionEvent evt) {
        switch (IntersectionFeux.etat) {
            case eEteint:
                //Impossible
                throw new IllegalStateException();

            case eHRouge:
                changerEtat(Etat.eHVert);
                allumerSeulementVert(feuxH);
                allumerSeulementRouge(feuxV);
                break;

            case eHVert:
                //Impossible
                throw new IllegalStateException();

            case eHOrange:
                //Impossible
                throw new IllegalStateException();

            case eVRouge:
                //Impossible
                throw new IllegalStateException();

            case eVVert:
                //Impossible
                throw new IllegalStateException();

            case eVOrange:
                //Impossible
                throw new IllegalStateException();

            case ePanneEteint:
                //Impossible
                throw new IllegalStateException();

            case ePanneOrange:
                //Impossible
                throw new IllegalStateException();

        }
    }

    private void timerHVertActionPerformed(java.awt.event.ActionEvent evt) {
        switch (IntersectionFeux.etat) {
            case eEteint:
                //Impossible
                throw new IllegalStateException();

            case eHRouge:
                //Impossible
                throw new IllegalStateException();

            case eHVert:
                changerEtat(Etat.eHOrange);
                allumerSeulementOrange(feuxH);
                allumerSeulementRouge(feuxV);
                break;

            case eHOrange:
                //Impossible
                throw new IllegalStateException();

            case eVRouge:
                //Impossible
                throw new IllegalStateException();

            case eVVert:
                //Impossible
                throw new IllegalStateException();

            case eVOrange:
                //Impossible
                throw new IllegalStateException();

            case ePanneEteint:
                //Impossible
                throw new IllegalStateException();

            case ePanneOrange:
                //Impossible
                throw new IllegalStateException();

        }
    }

    private void timerHOrangeActionPerformed(java.awt.event.ActionEvent evt) {
        switch (IntersectionFeux.etat) {
            case eEteint:
                //Impossible
                throw new IllegalStateException();

            case eHRouge:
                //Impossible
                throw new IllegalStateException();

            case eHVert:
                //Impossible
                throw new IllegalStateException();

            case eHOrange:
                changerEtat(Etat.eVRouge);
                allumerSeulementRouge();
                break;

            case eVRouge:
                //Impossible
                throw new IllegalStateException();

            case eVVert:
                //Impossible
                throw new IllegalStateException();

            case eVOrange:
                //Impossible
                throw new IllegalStateException();

            case ePanneEteint:
                //Impossible
                throw new IllegalStateException();

            case ePanneOrange:
                //Impossible
                throw new IllegalStateException();

        }
    }

    private void timerVRougeActionPerformed(java.awt.event.ActionEvent evt) {
        switch (IntersectionFeux.etat) {
            case eEteint:
                //Impossible
                throw new IllegalStateException();

            case eHRouge:
                //Impossible
                throw new IllegalStateException();

            case eHVert:
                //Impossible
                throw new IllegalStateException();

            case eHOrange:
                //Impossible
                throw new IllegalStateException();

            case eVRouge:
                changerEtat(Etat.eVVert);
                allumerSeulementRouge(feuxH);
                allumerSeulementVert(feuxV);
                break;

            case eVVert:
                //Impossible
                throw new IllegalStateException();

            case eVOrange:
                //Impossible
                throw new IllegalStateException();

            case ePanneEteint:
                //Impossible
                throw new IllegalStateException();

            case ePanneOrange:
                //Impossible
                throw new IllegalStateException();

        }
    }

    private void timerVVertActionPerformed(java.awt.event.ActionEvent evt) {
        switch (IntersectionFeux.etat) {
            case eEteint:
                //Impossible
                throw new IllegalStateException();

            case eHRouge:
                //Impossible
                throw new IllegalStateException();

            case eHVert:
                //Impossible
                throw new IllegalStateException();

            case eHOrange:
                //Impossible
                throw new IllegalStateException();

            case eVRouge:
                //Impossible
                throw new IllegalStateException();

            case eVVert:
                changerEtat(Etat.eVOrange);
                allumerSeulementRouge(feuxH);
                allumerSeulementOrange(feuxV);
                break;

            case eVOrange:
                //Impossible
                throw new IllegalStateException();

            case ePanneEteint:
                //Impossible
                throw new IllegalStateException();

            case ePanneOrange:
                //Impossible
                throw new IllegalStateException();

        }
    }

    private void timerVOrangeActionPerformed(java.awt.event.ActionEvent evt) {
        switch (IntersectionFeux.etat) {
            case eEteint:
                //Impossible
                throw new IllegalStateException();

            case eHRouge:
                //Impossible
                throw new IllegalStateException();

            case eHVert:
                //Impossible
                throw new IllegalStateException();

            case eHOrange:
                //Impossible
                throw new IllegalStateException();

            case eVRouge:
                //Impossible
                throw new IllegalStateException();

            case eVVert:
                //Impossible
                throw new IllegalStateException();

            case eVOrange:
                changerEtat(Etat.eHRouge);
                allumerSeulementRouge();
                break;

            case ePanneEteint:
                //Impossible
                throw new IllegalStateException();

            case ePanneOrange:
                //Impossible
                throw new IllegalStateException();

        }
    }

    private void timerPanneEteintActionPerformed(java.awt.event.ActionEvent evt) {
        switch (IntersectionFeux.etat) {
            case eEteint:
                //Impossible
                throw new IllegalStateException();

            case eHRouge:
                //Impossible
                throw new IllegalStateException();

            case eHVert:
                //Impossible
                throw new IllegalStateException();

            case eHOrange:
                //Impossible
                throw new IllegalStateException();

            case eVRouge:
                //Impossible
                throw new IllegalStateException();

            case eVVert:
                //Impossible
                throw new IllegalStateException();

            case eVOrange:
                //Impossible
                throw new IllegalStateException();

            case ePanneEteint:
                changerEtat(Etat.ePanneOrange);
                allumerSeulementOrange();
                break;

            case ePanneOrange:
                //Impossible
                throw new IllegalStateException();

        }
    }

    private void timerPanneOrangeActionPerformed(java.awt.event.ActionEvent evt) {
        switch (IntersectionFeux.etat) {
            case eEteint:
                //Impossible
                throw new IllegalStateException();

            case eHRouge:
                //Impossible
                throw new IllegalStateException();

            case eHVert:
                //Impossible
                throw new IllegalStateException();

            case eHOrange:
                //Impossible
                throw new IllegalStateException();

            case eVRouge:
                //Impossible
                throw new IllegalStateException();

            case eVVert:
                //Impossible
                throw new IllegalStateException();

            case eVOrange:
                //Impossible
                throw new IllegalStateException();

            case ePanneEteint:
                //Impossible
                throw new IllegalStateException();

            case ePanneOrange:
                changerEtat(Etat.ePanneEteint);
                eteindreTout();
                break;
        }
    }

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
            java.util.logging.Logger.getLogger(IntersectionFeux.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(IntersectionFeux.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(IntersectionFeux.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(IntersectionFeux.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new IntersectionFeux().setVisible(true);
            }
        });
    }
    
    
    /**
     * Une liste contenant les feux horizontaux pour les appels de méthode
     */
    private ArrayList<BlocFeux> feuxH;
    
    /**
     * Une liste contenant les feux verticaux pour les appels de méthode
     */
    private ArrayList<BlocFeux> feuxV;
    
    
    
    /**
     * Le timer définissant le temps d'allumage de la lumière ROUGE pour les feux HORIZONTAUX
     */
    private Timer timerHRouge;
    /**
     * Le timer définissant le temps d'allumage de la lumière VERTE pour les feux HORIZONTAUX
     */
    private Timer timerHVert;
    /**
     * Le timer définissant le temps d'allumage de la lumière ORANGE pour les feux HORIZONTAUX
     */
    private Timer timerHOrange;    
    
    
    /**
     * Le timer définissant le temps d'allumage de la lumière ROUGE pour les feux VERTICAUX
     */
    private Timer timerVRouge;
    /**
     * Le timer définissant le temps d'allumage de la lumière VERTE pour les feux VERTICAUX
     */
    private Timer timerVVert;
    /**
     * Le timer définissant le temps d'allumage de la lumière ORANGE pour les feux VERTICAUX
     */
    private Timer timerVOrange;
        
    
    /**
     * Le timer définissant la durée pendant laquelle il n'y a pas de lumière pour la panne
     */
    private Timer timerPanneEteint;
    /**
     * Le timer définissant la durée d'affichage de la lumière orange lors d'une panne
     */
    private Timer timerPanneOrange;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private fr.miage.millan.feuxcarrefourihm.objetsGraphiques.BlocFeux blocFeuxBas;
    private fr.miage.millan.feuxcarrefourihm.objetsGraphiques.BlocFeux blocFeuxDroite;
    private fr.miage.millan.feuxcarrefourihm.objetsGraphiques.BlocFeux blocFeuxGauche;
    private fr.miage.millan.feuxcarrefourihm.objetsGraphiques.BlocFeux blocFeuxHaut;
    private javax.swing.JButton jButtonArret;
    private javax.swing.JButton jButtonMarche;
    private javax.swing.JButton jButtonPanne;
    private javax.swing.JPanel jGridPanelBoutons;
    private javax.swing.JLabel pictureBackground;
    // End of variables declaration//GEN-END:variables
}
