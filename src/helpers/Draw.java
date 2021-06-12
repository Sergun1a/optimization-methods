package helpers;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

class LinesComponent extends JComponent {

    private static class Line {
        final int x1;
        final int y1;
        final int x2;
        final int y2;
        final Color color;

        public Line(int x1, int y1, int x2, int y2, Color color) {
            this.x1 = x1;
            this.y1 = y1;
            this.x2 = x2;
            this.y2 = y2;
            this.color = color;
        }
    }

    private final LinkedList<Line> lines = new LinkedList<Line>();

    private final LinkedList<Line> rays = new LinkedList<Line>();

    public void addLine(int x1, int x2, int x3, int x4) {
        addLine(x1, x2, x3, x4, Color.black);
    }

    public void addLine(int x1, int x2, int x3, int x4, Color color) {
        lines.add(new Line(x1, x2, x3, x4, color));
        repaint();
    }

    public void addRay(int x1, int x2, int x3, int x4, Color color) {
        rays.add(new Line(x1, x2, x3, x4, color));
        repaint();
    }

    public void clearLines() {
        lines.clear();
        repaint();
    }

    public void clearRays() {
        rays.clear();
        repaint();
    }

    // получаю функцию по двум точкам и возвращаю значение в точке x
    private int f_value(int x1, int y1, int x2, int y2, int x) {
        double y = ((double) (x - x1) / (double) (x2 - x1)) * (y2 - y1) - y1;
        return (int) Math.round(y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        int widthCenter = Math.round((int) ((double) this.size().width / (double) 2));
        int heightCenter = Math.round((int) ((double) this.size().height / (double) 2));
        int width = (int) this.size().width;
        int height = (int) this.size().height;

        super.paintComponent(g);
        for (Line line : lines) {
            g.setColor(line.color);
            if ((line.x1 < line.x2) || (line.x1 == line.x2 && (line.y1 <= line.y2))) {
                g.drawLine(widthCenter + line.x1, heightCenter - line.y1, widthCenter + line.x2, heightCenter - line.y2);
            } else if ((line.x1 > line.x2) || (line.x1 == line.x2 && (line.y1 > line.y2))) {
                g.drawLine(widthCenter + line.x2, heightCenter - line.y2, widthCenter + line.x1, heightCenter - line.y1);

            }
        }
        for (Line line : rays) {
            g.setColor(line.color);
            if ((line.x1 < line.x2) || (line.x1 == line.x2 && (line.y1 <= line.y2))) {
                g.drawLine(widthCenter + line.x1, heightCenter - line.y1, widthCenter + width, heightCenter - f_value(line.x1, line.y1, line.x2, line.y2, width));
            } else if ((line.x1 > line.x2) || (line.x1 == line.x2 && (line.y1 > line.y2))) {
                g.drawLine(widthCenter + line.x2, heightCenter - line.y2, widthCenter + width, heightCenter - f_value(line.x2, line.y2, line.x1, line.y1, width));
            }

        }
    }
}

public class Draw {
    public final LinesComponent comp = new LinesComponent();
    JFrame testFrame;

    public Draw() {
        testFrame = new JFrame();
        testFrame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        testFrame.setLocationRelativeTo(null);
        comp.setPreferredSize(Toolkit.getDefaultToolkit().getScreenSize());
        testFrame.getContentPane().add(comp, BorderLayout.CENTER);
        // добавляю оси координат
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        this.addLine((int) Math.round(height), 0, (int) Math.round(-height), 0, Color.BLACK);
        this.addLine(0, (int) Math.round(width), 0, (int) Math.round(-width), Color.BLACK);

    }

    public void addLine(int x1, int y1, int x2, int y2, Color color) {
        Color randomColor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
        if (x1 < x2) {
            comp.addLine(x1, y1, x2, y2, color);
        }
        if (x1 > x2) {
            comp.addLine(x2, y2, x1, y1, color);
        }
        if (x1 == x2) {
            if (y1 <= y2) {
                comp.addLine(x1, y1, x2, y2, color);
            } else {
                comp.addLine(x2, y2, x1, y1, color);
            }
        }

    }

    public void addRay(int x1, int y1, int x2, int y2, Color color) {
        Color randomColor = new Color((float) Math.random(), (float) Math.random(), (float) Math.random());
        if (x1 < x2) {
            comp.addRay(x1, y1, x2, y2, color);
        }
        if (x1 > x2) {
            comp.addRay(x2, y2, x1, y1, color);
        }
        if (x1 == x2) {
            if (y1 <= y2) {
                comp.addRay(x1, y1, x2, y2, color);
            } else {
                comp.addRay(x2, y2, x1, y1, color);
            }
        }
    }

    public void clearLines() {
        comp.clearLines();
    }

    public void show() {
        testFrame.pack();
        testFrame.setLocationRelativeTo(null);
        testFrame.setVisible(true);
    }

}


