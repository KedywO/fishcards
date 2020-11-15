import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BaseMultiResolutionImage;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Fiszki extends JFrame {
    static Fiszki frame;
    static Fishki frame1;
    private JPanel insertPanel;
    private JLabel labelES;
    private JTextField insertES;
    private JTextField insertPL;
    private JLabel labelPL;
    private JButton zapiszButton;
    private JButton cofnijButton;
    private JButton zamknijButton;
    JLabel background;
    static Connection con;
    static List<Words> words = new ArrayList<>();

    public Fiszki(){
        super("Fiszki");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setContentPane(insertPanel);
        pack();
        setResizable(false);
        createConnection();
        ImageIcon img = new ImageIcon(getClass().getResource("images/espana.png"));
        background = new JLabel();
        background.setIcon(img);
        zapiszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    createConnection();
                    String es = insertES.getText();
                    String pl = insertPL.getText();
                    Statement statement = con.createStatement();
                    if(es.length()!=0 && pl.length()!=0) {
                        statement.execute("insert into words(word_ES, word_PL) values('" + es + "','" + pl + "');");
                        statement.close();
                        insertES.setText("");
                        insertPL.setText("");
                    }else
                        System.out.println("Wprowadz słowa, aby zapisać");
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }

            }
        });
        cofnijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.setVisible(false);
                frame1.setVisible(true);
            }
        });
        zamknijButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                frame.dispose();
                frame1.dispose();
            }
        });
        insertPL.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                super.keyPressed(e);
                if(e.getKeyCode()==KeyEvent.VK_ENTER){
                    try {
                        createConnection();
                        String es = insertES.getText();
                        String pl = insertPL.getText();
                        Statement statement = con.createStatement();
                        if(es.length()!=0 && pl.length()!=0) {
                            statement.execute("insert into words(word_ES, word_PL) values('" + es + "','" + pl + "');");
                            statement.close();
                            insertES.setText("");
                            insertPL.setText("");

                        }else
                            System.out.println("Wprowadz słowa, aby zapisać");
                    } catch (SQLException throwables) {
                        throwables.printStackTrace();
                    }

                }
            }
        });
    }


    public static void main(String[] args) throws SQLException {


        createConnection();
        Statement stm = con.createStatement();
        ResultSet rs = stm.executeQuery("select * from words");
        while (rs.next()){
            words.add(new Words(rs.getString(1),rs.getString(2)));
        }
        frame = new Fiszki();
        frame1 = new Fishki();
        frame1.background.setBounds(0,0,frame1.getMainPanel().getWidth(),frame1.getMainPanel().getHeight());
        frame1.add(frame1.background);
        frame1.setVisible(true);
        frame.background.setBounds(0,0,frame.insertPanel.getWidth(),frame.insertPanel.getHeight());
        frame.setLayout(null);
        frame.add(frame.background);
        Words timeOne = new Words();
        timeOne=words.get(1);
        timeOne.getEs();







    }
    static void createConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/fiszki_db","root","root");
        }catch (ClassNotFoundException | SQLException ex){
            Logger.getLogger(Fiszki.class.getName()).log(Level.SEVERE,null,ex);
        }
    }


}
