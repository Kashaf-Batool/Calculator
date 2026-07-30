import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class Calculator extends JFrame implements ActionListener{
    double num1 = 0;
    double num2 = 0;
    double result = 0;
    String operator = "";
    JTextField display; 
    JTextField history;
    // --- Added: extra color palette (new fields, nothing existing touched) ---
    private static final Color APP_BG = new Color(18, 18, 20);
    private static final Color PANEL_BG = new Color(24, 24, 27);
    private static final Color DISPLAY_BG = new Color(28, 28, 32);
    private static final Color ACCENT = new Color(255, 170, 40);
    private static final Color ACCENT_HOVER = new Color(255, 190, 90);
    private static final Color BTN_BG = new Color(40, 40, 44);
    private static final Color BTN_BG_HOVER = new Color(55, 55, 60);
    private static final Color OP_BTN_BG = new Color(50, 45, 40);
    Calculator(){
        ImageIcon image = new ImageIcon("image.png");
        setIconImage(image.getImage());
       //JFrame
       
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        // Added: softer overall background layered on top of the black base
        getContentPane().setBackground(APP_BG);
        setSize(300,500);
        setResizable(false);
        setLayout(null);
        setLocationRelativeTo(null);
        
       
 JPanel panel = new JPanel(new GridLayout(5, 4, 5, 5));
 //panel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
 // Added: give the button grid some breathing room and a matching dark background
 panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
 panel.setBackground(PANEL_BG);
 panel.setOpaque(true);
//Buttons
        String[] labels = {
            "√" ,"x²","x³" ,"x",
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };
        for(String label : labels){
           panel.add(createButton(label));
            
        }
        setLayout(new BorderLayout());
//Top Panel//
        display = new JTextField();
        display.setFont(new Font("Arial",Font.BOLD,35));
        display.setHorizontalAlignment(JTextField.RIGHT);
        //display.setBorder(BorderFactory.createLineBorder(Color.green,1));
        display.setBorder(null);
        // Added: nicer display styling layered on top of the defaults above
        display.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 20));
        display.setBackground(DISPLAY_BG);
        display.setForeground(Color.WHITE);
        display.setCaretColor(Color.WHITE);
        display.setFont(new Font("Consolas", Font.BOLD, 38));

        history = new JTextField();
        history.setEditable(false);
        history.getCaret().setVisible(false);
        history.setFont(new Font("Arial",Font.PLAIN,15));
        history.setHorizontalAlignment(JTextField.RIGHT);
        history.setForeground(Color.gray);
        history.setBorder(null);
        history.setBackground(Color.white);
        // Added: match history strip to the new dark palette
        history.setBorder(BorderFactory.createEmptyBorder(8, 10, 0, 20));
        history.setBackground(DISPLAY_BG);
        history.setForeground(new Color(150, 150, 155));
        history.setFont(new Font("Consolas", Font.PLAIN, 16));
   
        JPanel topPanel = new JPanel(new GridLayout(2,1));
           topPanel.setPreferredSize(new Dimension(0, 150));
        topPanel.add(history);
        topPanel.add(display);
        // Added: frame the display area to match the dark theme
        topPanel.setBackground(DISPLAY_BG);
        topPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, ACCENT));
        

        add(topPanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
         setVisible(true);
        
    }
    
        public JButton createButton(String text){
           JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 30));
          btn.setBackground(new Color(245,245,245));
                btn.setForeground(Color.BLACK);
            btn.setBorder(BorderFactory.createLineBorder(Color.orange));
            btn.setFocusable(false);
            if(text.equals("=")){
                    btn.setBackground(new Color(255,170,40));
                    btn.setForeground(Color.WHITE);
}
           
            btn.addActionListener(e -> buttonClicked(text));
            // Added: rounded, padded border + dark palette + hover glow, layered after original setup
            boolean isEquals = text.equals("=");
            boolean isOperator = "/*-+".contains(text) && text.length() == 1;
            Color baseBg = isEquals ? ACCENT : (isOperator ? OP_BTN_BG : BTN_BG);
            btn.setBackground(baseBg);
            btn.setForeground(Color.WHITE);
            btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(isEquals ? ACCENT : new Color(70, 70, 75), 1, true),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)
            ));
            btn.addMouseListener(new java.awt.event.MouseAdapter(){
                public void mouseEntered(java.awt.event.MouseEvent evt){
                    btn.setBackground(isEquals ? ACCENT_HOVER : BTN_BG_HOVER);
                }
                public void mouseExited(java.awt.event.MouseEvent evt){
                    btn.setBackground(baseBg);
                }
            });
            return btn;
        }
     @Override
    public void actionPerformed(ActionEvent e){

    }
    //Button Clicked
    public void buttonClicked(String value){
    if(value.matches("[0-9]")){
        display.setText(display.getText() + value);
    }
    else if(value.equals("+") || value.equals("-")
    || value.equals("*") || value.equals("/") ||
        (value.equals("x²") || value.equals("x³") || value.equals("√"))){
        if(operator.isEmpty()){
            num1 = Double.parseDouble(display.getText());
        } else {
            num1 = applyOperator(operator, num1, Double.parseDouble(display.getText()));
        }
        history.setText(num1 + " " + value);
        operator = value;
        display.setText("");
    }

    else if(value.equals("=")){
       if(operator.isEmpty()) return;
       //Checks for Uniary Operator
       if(isUnary(operator)){
            if(operator.equals("√") && num1<0){
                display.setText("Error!");
                operator = "";
                return;
            }
            history.setText(value + "(" + num1 + ")");
            result = applyAdvOperator(operator, num1);
       } else{
        num2 = Double.parseDouble(display.getText());
         history.setText(num1 + " " + operator + " " + num2 + " =");
        result = applyOperator(operator, num1, num2);
       }
    
     display.setText(String.valueOf(result));
     num1 = result;
     operator = "";
    }
    else if(value.equals("C")){
        display.setText("");
          history.setText("");
        num1 = 0;
        num2 = 0;
        operator = "";
        result = 0;
    }
    //Remove last Digit
    else if(value.equals("x")){
        String text = display.getText();

        if(!text.isEmpty()){
            display.setText(text.substring(0,text.length()-1));
        }
    }

}


private boolean isUnary(String operator){
    return operator.equals("x²") || operator.equals("x³") || operator.equals("√");
}
private double applyOperator(String op, double a, double b){
    switch(op){
        case "+": return a + b;
        case "-": return a - b;
        case "*": return a * b;
        case "/":
            if(b != 0) return a / b;
            display.setText("Error");
            operator = "";
            return a;
        default: return b;
    }
}
private double applyAdvOperator(String op, double a){
    double AdvResult = 0;
    switch(op){
           case "x²": AdvResult= a * a; break;
            case "x³": AdvResult = a * a * a; break;
            case "√":
                AdvResult = Math.round(Math.sqrt(a) * 1_000_000.0) / 1_000_000.0;
                break;
    }
    return AdvResult;
}
}