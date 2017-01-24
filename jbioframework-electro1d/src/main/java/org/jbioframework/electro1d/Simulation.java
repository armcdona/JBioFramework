package org.jbioframework.electro1d;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DecimalFormat;
import javax.swing.JPanel;

/**
 * @author Bader AlHarbi
 * Simulation class to initiate the simulation panel GUI
 */
public class Simulation extends JPanel implements Runnable {
    private final int numOfStds = 7;
    Thread runner;
    Electrophoresis parent;
    int pause;
    float animationModifier;
    float modifier;
    boolean addInfo;
    Protein stdSamples[];
    Protein sample;
    Protein dye1;
    Protein dye2;
    Sample stdSample;
    Sample sampSample;
    Pipette pipette;
    protected int border;
    protected int baseX;
    protected int baseY;
    protected int baseHeight;
    protected int baseWidth;
    protected int topX;
    protected int topY;
    protected int topHeight;
    protected int topWidth;
    protected int bottomEdge;
    protected int rightEdge;
    protected int leftEdge;
    protected int plateX;
    protected int plateY;
    protected int plateHeight;
    protected int plateWidth;
    protected int plateBottom;
    protected int plateRtEdge;
    protected int topOpeningX;
    protected int topOpeningY;
    protected int topOpeningHeight;
    protected int topOpeningWidth;
    protected int wellOpening1X;
    protected int wellOpening1Y;
    protected int wellOpening1Height;
    protected int wellOpening1Width;
    protected int wellOpening2X;
    protected int wellOpening2Y;
    protected int wellOpening2Height;
    protected int wellOpening2Width;
    protected int wellBottom;
    protected int halfWellWidth;
    protected int totalElutionDist;
    protected float scaleFactor;
    int ratioModifier;
    int divStart;
    int divXLine;
    int divLabelX;
    int charHalfHeight;
    int charHeight;
    int scaleDivs[];
    int scaleHalfDivs[];
    String Jlabels[];
    FontMetrics fm;
    Font font;
    int gelLabelX;
    int gelLabelY;
    String gelLabel;
    int cellLabelY;
    int posProbeX;
    int posProbeY;
    int probeWidth;
    int probeHeight;
    int negProbeX;
    int negProbeY;
    int posProbeCenterX;
    int negProbeCenterX;
    int polarityPlusHorizontalX1;
    int polarityPlusHorizontalX2;
    int polarityNegHorizontalY;
    int polarityPlusVerticalY1;
    int polarityPlusVerticalY2;
    int polarityNegHorizontalX1;
    int polarityNegHorizontalX2;
    int polarityPlusY;
    int endPosX;
    int endPosY;
    int endNegX;
    int endNegY;
    int endWidth;
    int endHeight;
    int posLineX;
    int powerLineWidth;
    int negLineX;
    int posLineHeight;
    String proteinName;
    String proteinMW;
    String proteinDist;
    String relMigration;
    private Image offScreenImage;
    private Graphics offScreenGraphics;
    boolean addSampleFlag;
    boolean runSampleFlag;
    boolean imageCreated;
    boolean stopAnimation;
    boolean notAtBottom;
    boolean runExperiment;
    int notLoaded;
    int loading;
    int loaded;
    int sampLoadState;
    int stdLoadState;
    boolean noLoadError;
    protected DecimalFormat twoDigits;

    /**
     * constructor that take instant of the Electro1D parent class
     *
     * @param electrophoresis
     */
    Simulation(Electrophoresis electrophoresis) {
        this.setPreferredSize(new Dimension(450, 450));
        animationModifier = 1.0F;
        modifier = 1.0F;
        stdSamples = new Protein[7];
        sample = new Protein();
        dye1 = new Protein();
        dye2 = new Protein();
        stdSample = new Sample();
        sampSample = new Sample();
        pipette = new Pipette();
        ratioModifier = 10;
        scaleDivs = new int[7];
        scaleHalfDivs = new int[13];
        Jlabels = new String[7];
        gelLabel = "notSet";
        powerLineWidth = 3;
        proteinName = "notSet";
        proteinMW = "0.0";
        proteinDist = "0 mm";
        relMigration = "0.0";
        runExperiment = true;
        loading = 1;
        loaded = 2;
        sampLoadState = notLoaded;
        stdLoadState = notLoaded;
        parent = electrophoresis;
        Jlabels[0] = "0";
        Jlabels[1] = "1";
        Jlabels[2] = "2";
        Jlabels[3] = "3";
        Jlabels[4] = "4";
        Jlabels[5] = "5";
        Jlabels[6] = "6";
        twoDigits = new DecimalFormat("0.00");

        MouseClickListener msl = new MouseClickListener();
        this.addMouseListener(msl);
    }

    /**
     * start thread
     */
    public void start() {
        if (runner == null) {
            runner = new Thread(this);
            runner.start();
        }
    }

    /**
     * interrupt the running thread
     */
    public void stop() {
        if (runner != null) {
            runner.interrupt();
            runner = null;
        }
    }

    /**
     * addStandard()method that's control the addition of the standard proteins,
     * invoked by the Add standard button
     */
    public void addStandard() {
        if (sampLoadState == loading) {
            return;
        }
        stopRun();
        stdSample.reset();
        stdSample.fill();
        stdSample.setRatio(wellOpening1Height / ratioModifier);
        stdSample.setXPosition(wellOpening1X);
        stdSample.setWidth(wellOpening1Width);
        stdSample.setYPosition(wellBottom);
        stdSample.setMaxY(wellBottom);
        pipette.setSample(stdSample);
        pipette.setStartXPosition(wellOpening1X + halfWellWidth);
        pipette.setMaxYPosition(wellBottom);
        pipette.setSampleDepth(wellOpening1Height * 2);
        ResetFlags();
        addSampleFlag = true;
        stdLoadState = loading;
        setPause("fill");
        start();
    }

    /**
     * paintData(Graphics g)
     * draw protein info on the simulation panel
     *
     * @param g
     */
    private void paintData(Graphics g) {
        g.setColor(Color.black);
        int i = charHeight - 3;
        int charSpacing = (int) (0.9 * charHeight);
        if (noLoadError) {
            i += charHeight * 2;
            g.drawString("Add Standard  & Sample", plateX, i);
            noLoadError = false;
        } else {
            g.drawString(proteinName, plateX, i);
            i += charSpacing;
            g.drawString(proteinMW, plateX, i);
            i += charSpacing;
            g.drawString(proteinDist, plateX, i);
            i += charSpacing;
            g.drawString(relMigration, plateX, i);
        }
        addInfo = false;
    }

    /**
     * start the simulation on the gel panel , the protein bands moves after invoking this method
     *
     * @param aprotein is an array of standard proteins (known)
     * @param protein1 is a unknown protein (sample)
     * @param protein2 is a dye
     * @param protein3 is a dye
     */
    public void startRun(Protein aprotein[], Protein protein1,
                         Protein protein2, Protein protein3) {
        stopRun();
        if (stdLoadState == notLoaded || sampLoadState == notLoaded) {
            addInfo = true;
            noLoadError = true;
            repaint();
            return;
        }
        stdSamples = aprotein;
        sample = protein1;
        dye1 = protein2;
        dye2 = protein3;
        int i = 0;
        do {
            if (stdSamples[i].selected) {
                stdSamples[i].setWidth(wellOpening1Width);
                stdSamples[i].setStartPosition(wellOpening1X, wellBottom);
                stdSamples[i].setMaxPosition(plateBottom);
                stdSamples[i].SetHostScaleFactor(scaleFactor);
            }
        } while (++i < 7);
        sample.setWidth(wellOpening2Width);
        sample.setStartPosition(wellOpening2X, wellBottom);
        sample.setMaxPosition(plateBottom);
        sample.SetHostScaleFactor(scaleFactor);
        dye1.setWidth(wellOpening1Width);
        dye1.setStartPosition(wellOpening1X, wellBottom);
        dye1.setMaxPosition(plateBottom);
        dye1.SetHostScaleFactor(scaleFactor);
        dye2.setWidth(wellOpening2Width);
        dye2.setStartPosition(wellOpening2X, wellBottom);
        dye2.setMaxPosition(plateBottom);
        dye2.SetHostScaleFactor(scaleFactor);
        stdSample.setRatio(0);
        sampSample.setRatio(0);
        stdSample.drawSwitch(true);
        sampSample.drawSwitch(true);
        stdSample.empty();
        sampSample.empty();
        ResetFlags();
        runSampleFlag = true;
        stdLoadState = notLoaded;
        sampLoadState = notLoaded;
        setPause("elute");
        start();
    }

    /**
     * Stop the simulation
     */
    public void stopRun() {
        stopAnimation = true;
        stop();
    }

    /**
     * calculate the dimension of the simulation panel, the location of the
     * proteins bands relative to other panel edges
     */
    private void calcDimensions() {
        int i1 = 0;
        int j1 = 0;
        int k1 = 0;
        int i2 = 0;
        int j2 = 0;
        int k2 = 0;
        int i3 = 0;
        int j3 = 0;
        int k3 = 0;
        int i4 = 0;
        int j4 = 0;
        int k4 = 0;
        byte b1 = 2;
        byte b2 = 4;
        float f = 60.0F;
        bottomEdge = getSize().height - 2;
        rightEdge = getSize().width - 2;
        i1 = rightEdge / 2;
        border = getSize().width / 16;
        j3 = border / 2;
        k3 = border + border;
        j1 = bottomEdge / 8;
        k1 = bottomEdge / 10;
        i2 = rightEdge / 6;
        j2 = rightEdge / 18;
        baseX = leftEdge + border;
        baseY = bottomEdge - border - j1;
        baseHeight = j1;
        baseWidth = rightEdge - k3;
        topX = leftEdge + border;
        topY = border + j1;
        topHeight = j1;
        topWidth = rightEdge - k3;
        i3 = baseHeight / 4 * 3 + baseY;
        k2 = topY + topHeight / 4;
        plateX = i1 - i2 - i2;
        plateY = k2;
        plateHeight = i3 - plateY;
        plateWidth = i2 * 4;
        plateBottom = plateY + plateHeight;
        plateRtEdge = plateX + plateWidth;
        topOpeningX = plateX + j3;
        topOpeningY = plateY;
        topOpeningHeight = j3;
        topOpeningWidth = plateWidth - border;
        wellOpening1X = topOpeningX + border;
        wellOpening1Y = topOpeningY + topOpeningHeight;
        wellOpening1Height = k3;
        wellOpening1Width = i2;
        wellOpening2X = i1 + (i1 - (wellOpening1X + wellOpening1Width));
        wellOpening2Y = topOpeningY + topOpeningHeight;
        wellOpening2Height = wellOpening1Height;
        wellOpening2Width = wellOpening1Width;
        wellBottom = wellOpening1Y + wellOpening1Height;
        halfWellWidth = wellOpening1Width / 2;
        totalElutionDist = plateBottom - wellBottom;
        i4 = plateBottom - wellBottom;
        j4 = i4 / 6;
        k4 = i4 / 12;
        int i5 = wellBottom;
        int j5 = wellBottom;
        int k5 = 0;
        do {
            scaleDivs[k5] = i5;
            i5 += j4;
        } while (++k5 < 7);
        k5 = 0;
        do {
            scaleHalfDivs[k5] = j5;
            j5 += k4;
        } while (++k5 < 13);
        divStart = plateRtEdge - b1;
        divXLine = divStart + b2;
        divLabelX = divStart + fm.charWidth('0');
        charHalfHeight = fm.getAscent() / 8; // DPH2;
        charHeight = fm.getHeight();
        scaleFactor = f / totalElutionDist;
        gelLabelX = baseX + b1;
        gelLabelY = baseY + baseHeight - b1;
        cellLabelY = baseY + baseHeight + charHeight;
        negProbeX = topX;
        negProbeY = topY - k1;
        probeWidth = j2;
        posProbeX = baseX + baseWidth - probeWidth;
        posProbeY = baseY - k1;
        probeHeight = topY - negProbeY - b1 * 2;
        negProbeCenterX = negProbeX + probeWidth / 2;
        posProbeCenterX = posProbeX + probeWidth / 2;
        negLineX = negProbeCenterX - 1;
        posLineX = posProbeCenterX - 1;
        posLineHeight = posProbeY;
        polarityNegHorizontalX1 = negProbeCenterX - 2;
        polarityNegHorizontalX2 = negProbeCenterX + 2;
        polarityPlusHorizontalX1 = posProbeCenterX - 2;
        polarityPlusHorizontalX2 = posProbeCenterX + 2;
        polarityNegHorizontalY = negProbeY + probeHeight / 2;
        polarityPlusVerticalY1 = posProbeY + probeHeight / 2 - 2;
        polarityPlusVerticalY2 = posProbeY + probeHeight / 2 + 2;
        polarityPlusY = posProbeY + probeHeight / 2;
        endWidth = probeWidth / 3;
        endPosX = negProbeCenterX - endWidth / 2;
        endPosY = negProbeY + probeHeight - 2;
        endNegX = posProbeCenterX - endWidth / 2;
        endNegY = posProbeY + probeHeight - 2;
        endHeight = probeHeight / 2;
    }

    /**
     * pause the simulation
     *
     * @param string determines the animation modifier
     */
    public void setPause(String string) {
        byte b = 100;
        float f1 = 2.0F;
        float f2 = 4.0F;
        float f3 = 1.8F;
        float f4 = 2.5F;
        float f5 = 3.5F;
        if (string == "elute")
            modifier = f1;
        else if (string == "fill")
            modifier = f2;
        else if (string.compareTo("Slow") == 0)
            animationModifier = f3;
        else if (string.compareTo("Moderate") == 0)
            animationModifier = f4;
        else if (string.compareTo("Fast") == 0)
            animationModifier = f5;
        pause = (int) ((float) b / modifier / animationModifier);
    }

    /**
     * draw protein band
     *
     * @param g
     */
    private void drawCell(Graphics g) {
        g.setColor(Color.black);
        g.drawRect(0, 0, rightEdge, bottomEdge);
        g.setColor(Color.cyan);
        g.fillRect(baseX, baseY, baseWidth, baseHeight);
        g.setColor(Color.black);
        g.drawRect(baseX, baseY, baseWidth, baseHeight);
        g.setColor(Color.red);
        g.fillRect(posLineX, 0, powerLineWidth, posLineHeight);
        g.setColor(Color.cyan);
        g.fillRect(topX, topY, topWidth, topHeight);
        g.setColor(Color.black);
        g.drawRect(topX, topY, topWidth, topHeight);
        g.setColor(Color.lightGray);
        g.fillRect(plateX, plateY, plateWidth, plateHeight);
        g.setColor(Color.darkGray);
        g.drawLine(plateX, plateY, plateX, plateBottom);
        g.drawLine(plateRtEdge, plateY, plateRtEdge, plateBottom);
        g.setColor(Color.white);
        g.fillRect(topOpeningX, topOpeningY, topOpeningWidth, topOpeningHeight);
        g.fillRect(wellOpening1X, wellOpening1Y, wellOpening1Width,
                wellOpening1Height);
        g.fillRect(wellOpening2X, wellOpening2Y, wellOpening2Width,
                wellOpening2Height);
        g.setColor(Color.black);
        int i = 0;
        do
            g.drawString(Jlabels[i], divLabelX, scaleDivs[i] + charHalfHeight);
        while (++i < 7);
        i = 0;
        do
            g.drawLine(divStart, scaleHalfDivs[i], divXLine, scaleHalfDivs[i]);
        while (++i < 13);
        g.drawString(gelLabel, gelLabelX, gelLabelY);
        g.setColor(Color.gray);
        g.fillRect(endPosX, endPosY, endWidth, endHeight);
        g.fillRect(endNegX, endNegY, endWidth, endHeight);
        g.setColor(Color.black);
        g.drawRoundRect(negProbeX, negProbeY, probeWidth, probeHeight,
                probeWidth, probeWidth);
        g.fillRoundRect(negProbeX, negProbeY, probeWidth, probeHeight,
                probeWidth, probeWidth);
        g.fillRect(negLineX, 0, powerLineWidth, negProbeY);
        g.setColor(Color.white);
        g.drawLine(polarityNegHorizontalX1, polarityNegHorizontalY,
                polarityNegHorizontalX2, polarityNegHorizontalY);
        g.setColor(Color.red);
        g.drawRoundRect(posProbeX, posProbeY, probeWidth, probeHeight,
                probeWidth, probeWidth);
        g.fillRoundRect(posProbeX, posProbeY, probeWidth, probeHeight,
                probeWidth, probeWidth);
        g.setColor(Color.black);
        g.drawLine(posProbeCenterX, polarityPlusVerticalY1, posProbeCenterX,
                polarityPlusVerticalY2);
        g.drawLine(polarityPlusHorizontalX1, polarityPlusY,
                polarityPlusHorizontalX2, polarityPlusY);
        g.setColor(Color.red);
        g.drawString(" ELECTROPHORESIS CELL", plateX, cellLabelY);
    }

    /**
     * add sample
     */
    public void addSample() {
        if (stdLoadState == loading) {
            return;
        }
        stopRun();
        sampSample.reset();
        sampSample.fill();
        sampSample.setRatio(wellOpening2Height / ratioModifier);
        sampSample.setXPosition(wellOpening2X);
        sampSample.setWidth(wellOpening2Width);
        sampSample.setYPosition(wellBottom);
        sampSample.setMaxY(wellBottom);
        pipette.setSample(sampSample);
        pipette.setStartXPosition(wellOpening2X + halfWellWidth);
        pipette.setMaxYPosition(wellBottom);
        pipette.setSampleDepth(wellOpening2Height * 2);
        ResetFlags();
        addSampleFlag = true;
        sampLoadState = loading;
        setPause("fill");
        start();
    }

    /**
     * set the acrylamide gel properties
     *
     * @param acrylamide acrylamide reference
     */
    public void setAcrylamide(Acrylamide acrylamide) {
        gelLabel = acrylamide.percentGel + " Acrylamide";
        repaint();
    }

    /**
     * draw graph
     *
     * @param g
     */
    private void drawGraph(Graphics g) {
        g.setColor(Color.black);
        int i = 0;
        do
            g.drawString(Jlabels[i], border, scaleDivs[i] + charHalfHeight);
        while (++i < 7);
    }

    /**
     * paint the protein sample band
     *
     * @param g
     */
    private void paintSample(Graphics g) {
        int i = 0;
        notAtBottom = false;
        dye1.drawProtein(g);
        dye2.drawProtein(g);
        i = 0;
        do
            if (stdSamples[i].selected && stdSamples[i].drawProtein(g))
                notAtBottom = true;
        while (++i < 7);
        if (sample.drawProtein(g))
            notAtBottom = true;
        if (!notAtBottom)
            stopRun();
    }

    /**
     * run the thread
     */
    public void run() {
        Thread.currentThread().setPriority(1);
        while (!stopAnimation) {
            try {
                Thread.sleep((long) pause);
            } catch (InterruptedException e) {
            }
            repaint();
        }
    }

    /**
     * change the standardized proteins loading status (per each simulation )
     *
     * @param g
     */
    private void paintAddition(Graphics g) {
        if (!pipette.fillWell(g)) {
            if (stdLoadState == loading)
                stdLoadState = loaded;
            else if (sampLoadState == loading)
                sampLoadState = loaded;
            stopRun();
        }
    }

    /**
     * reset the animation flags
     */
    private void ResetFlags() {
        runSampleFlag = false;
        addSampleFlag = false;
        stopAnimation = false;
        notAtBottom = false;
    }

    /**
     * update the graphics (the simulation panel)
     *
     * @param g graphics reference
     */
    public void update(Graphics g) {
        paint(g);
    }

    /**
     * the paint method
     *
     * @param g graphics reference
     */
    public void paint(Graphics g) {
        if (!imageCreated) {
            offScreenImage = createImage(getSize().width, getSize().height);
            font = getFont();
            fm = getFontMetrics(font);
            calcDimensions();
            imageCreated = true;
        }
        offScreenGraphics = offScreenImage.getGraphics();
        if (addInfo) {
            offScreenGraphics.setColor(Color.white);
            offScreenGraphics.fillRect(plateX, 1, plateWidth, topY - 5);
            paintData(offScreenGraphics);
            g.drawImage(offScreenImage, 0, 0, this);
            return;
        }
        offScreenGraphics.setColor(Color.white);
        offScreenGraphics.fillRect(0, 0, getSize().width, getSize().height);
        offScreenGraphics.setColor(g.getColor());
        if (runExperiment) {
            drawCell(offScreenGraphics);
            if (runSampleFlag)
                paintSample(offScreenGraphics);
            else if (addSampleFlag)
                paintAddition(offScreenGraphics);
            stdSample.drawSample(offScreenGraphics);
            sampSample.drawSample(offScreenGraphics);
        } else
            drawGraph(offScreenGraphics);
        g.drawImage(offScreenImage, 0, 0, this);
    }

    /**
     * inner class handle mouse events when user clicks the protein bands
     */
    class MouseClickListener implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            // System.out.println(e + ", i: " + e.getX() + ", j: " + e.getY());
            if (stopAnimation && notAtBottom) {
                for (int k = 0; k < numOfStds; k++) {
                    if (stdSamples[k].matchPosition(e.getX(), e.getY())) {

                        stdSamples[k].relativeMigration = stdSamples[k]
                                .GetDistance() / dye1.GetDistance();
                        proteinName = stdSamples[k].fullName;
                        proteinMW = "MW = "
                                + String.valueOf((int) stdSamples[k].mw);
                        proteinDist = "mm travel = "
                                + twoDigits.format(stdSamples[k].GetDistance());
                        relMigration = "RM = "
                                + twoDigits
                                .format(stdSamples[k].relativeMigration);
                        addInfo = true;
                        repaint();
                        break;
                    }
                }
                if (sample.matchPosition(e.getX(), e.getY())) {
                    proteinName = sample.name;
                    proteinMW = "MW = tbd";
                    proteinDist = "mm travel = "
                            + twoDigits.format(sample.GetDistance());
                    relMigration = "RM = "
                            + twoDigits.format(sample.GetDistance()
                            / dye1.GetDistance());
                    addInfo = true;
                    repaint();
                } else if (dye1.matchPosition(e.getX(), e.getY())
                        || dye2.matchPosition(e.getX(), e.getY())) {
                    proteinName = dye1.name;
                    proteinMW = "";
                    proteinDist = "mm Travel = "
                            + twoDigits.format(dye1.GetDistance());
                    relMigration = "";
                    addInfo = true;
                    repaint();
                }
            }

        }

        @Override
        public void mouseEntered(MouseEvent arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseExited(MouseEvent arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mousePressed(MouseEvent arg0) {
            // TODO Auto-generated method stub

        }

        @Override
        public void mouseReleased(MouseEvent arg0) {
            // TODO Auto-generated method stub

        }

    }
}
