import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main extends JFrame implements ActionListener {
    private Container contenedor;
    private JLabel[] etiquetasNotas;
    private JTextField[] camposNotas;
    private JButton botonCalcular, botonLimpiar;
    private JTextArea areaResultados;
    private JScrollPane scrollArea;

    public Main() {
        inicio();
        setTitle("Calculo de notas");
        setSize(420, 560);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
    }

    private void inicio() {
        contenedor = getContentPane();
        contenedor.setLayout(null);

        etiquetasNotas = new JLabel[5];
        camposNotas = new JTextField[5];

        int y = 20;
        for (int i = 0; i < 5; i++) {
            etiquetasNotas[i] = new JLabel("Nota " + (i + 1) + ":");
            etiquetasNotas[i].setBounds(30, y, 100, 25);
            contenedor.add(etiquetasNotas[i]);

            camposNotas[i] = new JTextField();
            camposNotas[i].setBounds(140, y, 220, 25);
            contenedor.add(camposNotas[i]);

            y += 40;
        }

        botonCalcular = new JButton("Calcular");
        botonCalcular.setBounds(70, y, 120, 30);
        botonCalcular.addActionListener(this);
        contenedor.add(botonCalcular);

        botonLimpiar = new JButton("Limpiar");
        botonLimpiar.setBounds(210, y, 120, 30);
        botonLimpiar.addActionListener(this);
        contenedor.add(botonLimpiar);

        y += 50;

        areaResultados = new JTextArea();
        areaResultados.setEditable(false);
        scrollArea = new JScrollPane(areaResultados);
        scrollArea.setBounds(30, y, 330, 150);
        contenedor.add(scrollArea);
    }

    @Override
    public void actionPerformed(ActionEvent evento) {
        if (evento.getSource() == botonCalcular) {
            try {
                double[] notas = new double[5];
                double suma = 0;

                for (int i = 0; i < 5; i++) {
                    String texto = camposNotas[i].getText().trim().replace(",", ".");
                    if (texto.isEmpty()) {
                        throw new NumberFormatException();
                    }
                    notas[i] = Double.parseDouble(texto);
                    suma += notas[i];
                }

                double promedio = suma / 5.0;

                double mayor = notas[0];
                double menor = notas[0];
                for (int i = 1; i < 5; i++) {
                    if (notas[i] > mayor) mayor = notas[i];
                    if (notas[i] < menor) menor = notas[i];
                }

                double sumaDiferenciasCuadradas = 0;
                for (int i = 0; i < 5; i++) {
                    sumaDiferenciasCuadradas += Math.pow(notas[i] - promedio, 2);
                }
                double desviacionEstandar = Math.sqrt(sumaDiferenciasCuadradas / 4.0);

                areaResultados.setText("");
                areaResultados.append("=== RESULTADOS DEL ESTUDIANTE ===\n");
                areaResultados.append(String.format(" - Promedio: %.2f\n", promedio));
                areaResultados.append(String.format(" - Desviación Estándar: %.2f\n", desviacionEstandar));
                areaResultados.append(String.format(" - Nota Mayor: %.2f\n", mayor));
                areaResultados.append(String.format(" - Nota Menor: %.2f\n", menor));

            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, 
                    "Por favor ingrese valores numéricos válidos en todas las notas.", 
                    "Error de Entrada", 
                    JOptionPane.ERROR_MESSAGE);
            }
        }

        if (evento.getSource() == botonLimpiar) {
            for (int i = 0; i < 5; i++) {
                camposNotas[i].setText("");
            }
            areaResultados.setText("");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(()-> {
            Main ventana = new Main();
            ventana.setVisible(true);
        });
    }
}
