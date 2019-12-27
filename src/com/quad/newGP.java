package com.quad;

import com.quad.ClientData.GP;
import com.quad.ClientData.MedCentre;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class newGP extends JFrame{
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JPanel GPPanel;
    private JButton OKButton;
    private JTextField textField4;
    private JTextField textField5;
    private JTextField textField6;
    private JTextField photoAddress;
    private JButton button1;
    private JLabel image;

    newGP(){
        setContentPane(GPPanel);
        getRootPane().setDefaultButton(OKButton);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveInfo();
                goBack();
            }
        });

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                JFileChooser chooser = new JFileChooser();
                chooser.showOpenDialog(null);
                File f = chooser.getSelectedFile();
                String filename = f.getAbsolutePath();
                textField1.setText(filename);
                try {
                    ImageIcon ii=new ImageIcon(scaleImage(ImageIO.read(new File(f.getAbsolutePath()))));//get the image from file chooser and scale it to match JLabel size
                    image.setIcon(ii); //SHOW THE IMAGE

                } catch (Exception ex) {
                    ex.printStackTrace();
                }

                BufferedImage theImage = null; //HENRY LOOK INTO SAVING THIS TO SQL
                try {
                    theImage = ImageIO.read(f);
                    ImageIO.write(theImage, "jpg", new File("C://Users/scott/Desktop/image.png")); //THIS SAVES IT LOCALLY SOMEWHERE IDK
                } catch (IOException e) {
                    System.out.println("Exception occured :" + e.getMessage());
                }
                System.out.println("Images were written succesfully.");
            }
        });


    }

    private void saveInfo(){
        String name = textField1.getText(); //Name
        String email = textField2.getText(); //Email
        String pagerNum = textField3.getText(); //Pager Number
        String username = textField5.getText(); //Username
        String Password = textField6.getText(); //Password
        MedCentre medC = new MedCentre(textField4.getText(), "", 0); //Med Centre
        medC = medC.search();
        if (medC.getID()==0){
            JOptionPane.showMessageDialog(null, "Sorry, no medical centre with that name could be found!");
        }
        else{
            GP newGP = new GP(name, email, medC, 0, pagerNum, username, Password);
            newGP.save();
            //only saves the gp if valid medical centre entered
        }

    }

    private static BufferedImage scaleImage(BufferedImage img) throws Exception {
        BufferedImage bi;
        bi = new BufferedImage(60, 100, BufferedImage.TRANSLUCENT);
        Graphics2D g2d = (Graphics2D) bi.createGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.addRenderingHints(new RenderingHints(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY));
        g2d.drawImage(img, 0, 0, 120, 120, null);
        g2d.dispose();
        return bi;
    }

    private void goBack(){
        dispose();
        adminOptions frame = new adminOptions();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setSize(700,400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        newGP frame5 = new newGP();
        frame5.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame5.pack();
        frame5.setSize(700,400);
        frame5.setLocationRelativeTo(null);
        frame5.setVisible(true);
    }
}
