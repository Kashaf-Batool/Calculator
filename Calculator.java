import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
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
    Calculator(){
        ImageIcon image = new ImageIcon("image.png");
        setIconImage(image.getImage());
       //JFrame
        JPanel panel = new JPanel(new GridLayout(5, 4, 5, 5));
        setTitle("Calculator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(Color.BLACK);
        setSize(300,500);
        setResizable(false);
        setBackground(Color.BLACK);
       

//Buttons
        String[] labels = {
            "√" ,"x²","x³" ,"⌫",
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", "C", "=", "+"
        };
        for(String label : labels){
           panel.add(createButton(label));
            
        }
        setLayout(new BorderLayout());

         display = new JTextField();
        display.setPreferredSize(new Dimension(0, 160));
        display.setFont(new Font("Arial",Font.BOLD,35));
        display.setHorizontalAlignment(JTextField.RIGHT);

        add(display, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
         setVisible(true);
        
    }
    
        public JButton createButton(String text){
           JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 30));
            btn.setBackground(Color.black);
            btn.setBorder(BorderFactory.createLineBorder(Color.orange));
            btn.setForeground(Color.orange);
            btn.setFocusable(false);
           
            btn.addActionListener(e -> buttonClicked(text));
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
            result = applyAdvOperator(operator, num1);
       } else{
        num2 = Double.parseDouble(display.getText());
        result = applyOperator(operator, num1, num2);
       }
     display.setText(String.valueOf(result));
     num1 = result;
     operator = "";
    }
    else if(value.equals("C")){
        display.setText("");
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
                AdvResult = Math.sqrt(a);
                break;
    }
    return AdvResult;
}
}