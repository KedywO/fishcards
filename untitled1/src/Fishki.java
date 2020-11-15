import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.security.SecureRandom;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Fishki extends JFrame {
    private JPanel mainPanel;
    private JLabel labelESFiszki;
    private JButton usuńFiszkeButton;
    private JButton zamknijButton;
    private JButton dodajNowąFiszkeButton;
    private JLabel langID;
    int oldRandom=0;
    boolean whichLang=true;
    JLabel background;
    Words tempWord = new Words();


    public JPanel getMainPanel() {
        return mainPanel;
    }

    public Fishki(){
        super("fishki");
        pack();

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(mainPanel);
        setSize(600,400);
        setLayout(null);
        background = new JLabel();
        background.setIcon(new ImageIcon(getClass().getResource("/images/espanaFishki.png")));
        setResizable(false);
        labelESFiszki.setText("Klinij aby zacząć!");
        labelESFiszki.setVisible(true);
        langID.setVisible(false);
        zamknijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Fiszki.frame1.dispose();
                Fiszki.frame.dispose();
            }
        });
        labelESFiszki.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);



                    int randomBound = Fiszki.words.size();

                    SecureRandom secureRandom = new SecureRandom();
                    String wordEs = new String();
                    int random = secureRandom.nextInt(randomBound+1);
                    if(whichLang) {
                        oldRandom=random;
                        langID.setText("ES:");
                        langID.setVisible(true);
                        tempWord = Fiszki.words.get(random);
                        wordEs = tempWord.getEs();
                        wordEs.toUpperCase();
                        labelESFiszki.setText(wordEs);
                        whichLang=false;

                    }else
                    {
                        langID.setText("PL:");
                        langID.setVisible(true);

                            tempWord=Fiszki.words.get(oldRandom);
                            wordEs = tempWord.getPl();
                            wordEs.toUpperCase();
                            labelESFiszki.setText(wordEs);
                            whichLang=true;
                        }

                    }


            });

        dodajNowąFiszkeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Fiszki.frame1.setVisible(false);
                Fiszki.frame.setVisible(true);
            }
        });
       usuńFiszkeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    //Class.forName("com.mysql.cj.jdbc.Driver");
                    //Connection conFishki = DriverManager.getConnection("jdbc:mysql://localhost:3306/fiszki_db","root","root");
                    Statement statement= Fiszki.con.createStatement();
                    statement.execute("delete from words where word_ID="+oldRandom);
                    statement.close();


                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
            }
        });
    }




}
