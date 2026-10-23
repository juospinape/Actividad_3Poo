import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

abstract class FiguraGeometrica {
    protected double volumen;
    protected double superficie;

    public abstract void calcularVolumen();
    public abstract void calcularSuperficie();

    public double getVolumen() {
        return volumen;
    }

    public double getSuperficie() {
        return superficie;
    }
}

class Cilindro extends FiguraGeometrica {
    private double radio;
    private double altura;

    public Cilindro(double radio, double altura) {
        this.radio = radio;
        this.altura = altura;
    }

    @Override
    public void calcularVolumen() {
        volumen = Math.PI * Math.pow(radio, 2) * altura;
    }

    @Override
    public void calcularSuperficie() {
        superficie = (2 * Math.PI * radio * altura) + (2 * Math.PI * Math.pow(radio, 2));
    }
}

class Esfera extends FiguraGeometrica {
    private double radio;

    public Esfera(double radio) {
        this.radio = radio;
    }

    @Override
    public void calcularVolumen() {
        volumen = (4.0 / 3.0) * Math.PI * Math.pow(radio, 3);
    }

    @Override
    public void calcularSuperficie() {
        superficie = 4 * Math.PI * Math.pow(radio, 2);
    }
}

class Piramide extends FiguraGeometrica {
    private double base;
    private double altura;
    private double apotema;

    public Piramide(double base, double altura, double apotema) {
        this.base = base;
        this.altura = altura;
        this.apotema = apotema;
    }

    @Override
    public void calcularVolumen() {
        double areaBase = Math.pow(base, 2);
        volumen = (areaBase * altura) / 3.0;
    }

    @Override
    public void calcularSuperficie() {
        double areaBase = Math.pow(base, 2);
        double areaLado = 2 * base * apotema;
        superficie = areaBase + areaLado;
    }
}

public class Main extends JFrame implements ActionListener {
    private Container contenedor;
    private JComboBox<String> comboFiguras;
    private JLabel labelDato1, labelDato2, labelDato3;
    private JTextField campoDato1, campoDato2, campoDato3;
    private JButton botonCalcular, botonLimpiar;
    private JTextArea areaResultados;
    private JScrollPane scrollArea;

    public Main() {
        inicio();
        setTitle("Ejercicio 8.3 - Figuras Geometricas");
        setSize(440, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void inicio() {
        contenedor = getContentPane();
        contenedor.setLayout(null);

        JLabel labelFigura = new JLabel("Figura:");
        labelFigura.setBounds(30, 20, 100, 25);
        String[] figuras = {"Cilindro", "Esfera", "Pirámide"};
        comboFiguras = new JComboBox<>(figuras);
        comboFiguras.setBounds(140, 20, 240, 25);
        comboFiguras.addActionListener(this);

        labelDato1 = new JLabel("Radio (cm):");
        labelDato1.setBounds(30, 60, 100, 25);
        campoDato1 = new JTextField();
        campoDato1.setBounds(140, 60, 240, 25);

        labelDato2 = new JLabel("Altura (cm):");
        labelDato2.setBounds(30, 100, 100, 25);
        campoDato2 = new JTextField();
        campoDato2.setBounds(140, 100, 240, 25);

        labelDato3 = new JLabel("");
        labelDato3.setBounds(30, 140, 100, 25);
        campoDato3 = new JTextField();
        campoDato3.setBounds(140, 140, 240, 25);
        labelDato3.setVisible(false);
        campoDato3.setVisible(false);

        botonCalcular = new JButton("Calcular");
        botonCalcular.setBounds(80, 190, 120, 30);
        botonCalcular.addActionListener(this);

        botonLimpiar = new JButton("Limpiar");
        botonLimpiar.setBounds(220, 190, 120, 30);
        botonLimpiar.addActionListener(this);

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        scrollArea = new JScrollPane(areaResultados);
        scrollArea.setBounds(30, 240, 350, 210);

        contenedor.add(labelFigura);
        contenedor.add(comboFiguras);
        contenedor.add(labelDato1);
        contenedor.add(campoDato1);
        contenedor.add(labelDato2);
        contenedor.add(campoDato2);
        contenedor.add(labelDato3);
        contenedor.add(campoDato3);
        contenedor.add(botonCalcular);
        contenedor.add(botonLimpiar);
        contenedor.add(scrollArea);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == comboFiguras) {
            String seleccion = (String) comboFiguras.getSelectedItem();
            campoDato1.setText("");
            campoDato2.setText("");
            campoDato3.setText("");
            areaResultados.setText("");

            if (seleccion.equals("Cilindro")) {
                labelDato1.setText("Radio (cm):");
                labelDato2.setText("Altura (cm):");
                labelDato3.setVisible(false);
                campoDato3.setVisible(false);
                labelDato1.setVisible(true);
                campoDato1.setVisible(true);
                labelDato2.setVisible(true);
                campoDato2.setVisible(true);
            } else if (seleccion.equals("Esfera")) {
                labelDato1.setText("Radio (cm):");
                labelDato1.setVisible(true);
                campoDato1.setVisible(true);
                labelDato2.setVisible(false);
                campoDato2.setVisible(false);
                labelDato3.setVisible(false);
                campoDato3.setVisible(false);
            } else if (seleccion.equals("Pirámide")) {
                labelDato1.setText("Base (cm):");
                labelDato2.setText("Altura (cm):");
                labelDato3.setText("Apotema (cm):");
                labelDato1.setVisible(true);
                campoDato1.setVisible(true);
                labelDato2.setVisible(true);
                campoDato2.setVisible(true);
                labelDato3.setVisible(true);
                campoDato3.setVisible(true);
            }
        }

        if (evento.getSource() == botonCalcular) {
            try {
                String seleccion = (String) comboFiguras.getSelectedItem();
                FiguraGeometrica figura = null;

                if (seleccion.equals("Cilindro")) {
                    double radio = Double.parseDouble(campoDato1.getText().trim().replace(",", "."));
                    double altura = Double.parseDouble(campoDato2.getText().trim().replace(",", "."));
                    if (radio <= 0 || altura <= 0) throw new NumberFormatException();
                    figura = new Cilindro(radio, altura);

                } else if (seleccion.equals("Esfera")) {
                    double radio = Double.parseDouble(campoDato1.getText().trim().replace(",", "."));
                    if (radio <= 0) throw new NumberFormatException();
                    figura = new Esfera(radio);

                } else if (seleccion.equals("Pirámide")) {
                    double base = Double.parseDouble(campoDato1.getText().trim().replace(",", "."));
                    double altura = Double.parseDouble(campoDato2.getText().trim().replace(",", "."));
                    double apotema = Double.parseDouble(campoDato3.getText().trim().replace(",", "."));
                    if (base <= 0 || altura <= 0 || apotema <= 0) throw new NumberFormatException();
                    figura = new Piramide(base, altura, apotema);
                }

                if (figura != null) {
                    figura.calcularVolumen();
                    figura.calcularSuperficie();

                    areaResultados.setText("");
                    areaResultados.append("=== RESULTADOS (" + seleccion.toUpperCase() + ") ===\n");
                    areaResultados.append(String.format(" - Volumen: %.2f cm³\n", figura.getVolumen()));
                    areaResultados.append(String.format(" - Superficie: %.2f cm²\n", figura.getSuperficie()));
                }

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor ingrese valores numéricos válidos y mayores a cero.", 
                    "Error de Entrada", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        if (evento.getSource() == botonLimpiar) {
            campoDato1.setText("");
            campoDato2.setText("");
            campoDato3.setText("");
            areaResultados.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main ventana = new Main();
            ventana.setVisible(true);
        });
    }
}
