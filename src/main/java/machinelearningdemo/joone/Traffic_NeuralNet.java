package machinelearningdemo.joone;

import org.joone.engine.*;
import org.joone.engine.learning.TeachingSynapse;
import org.joone.io.MemoryInputSynapse;
import org.joone.io.MemoryOutputSynapse;
import org.joone.net.NeuralNet;

import java.util.List;

/**
 * Created by cdz on 2017/6/19.
 */
public class Traffic_NeuralNet implements NeuralNetListener {

    private NeuralNet nnet = null;
    private MemoryInputSynapse inputSynapse, desiredOutputSynapse;
    private LinearLayer input;
    private SigmoidLayer output;
    private SigmoidLayer hidden;
    private List<SigmoidLayer> hiddenLayerList;
    private boolean singleThreadMode = true;
    private double[][] desiredOutputArray;
    private double[][] inputArray;
    private double[][] testArray;
    private double learningRate;
    private double momentum;
    private int totCicles;
    private int hiddenLayerlength;
    public void train(){
        inputSynapse.setInputArray(inputArray);
        inputSynapse.setAdvancedColumnSelector(" 1,2,3 ");
        // set the desired outputs
        desiredOutputSynapse.setInputArray(desiredOutputArray);
        desiredOutputSynapse.setAdvancedColumnSelector(" 1 ");

        // get the monitor object to train or feed forward
        Monitor monitor = nnet.getMonitor();

        // set the monitor parameters

        monitor.setLearningRate(learningRate==0?0.8:learningRate);
        monitor.setMomentum(momentum==0?0.3:momentum);
        monitor.setTrainingPatterns(inputArray.length);
        monitor.setTotCicles(totCicles==0?5000:totCicles);
        monitor.setLearning(true);

        long initms = System.currentTimeMillis();
        // Run the network in single-thread, synchronized mode
        nnet.getMonitor().setSingleThreadMode(singleThreadMode);
        nnet.go(true);
        System.out.println(" Total time=  "
                + (System.currentTimeMillis() - initms) + "  ms ");
    }

    private void interrogate()
    {
//
//        double[][] inputArray = new double[][]
//                {
//                        { 1.0, 1.0 } };
        // set the inputs
        inputSynapse.setInputArray(testArray);
        inputSynapse.setAdvancedColumnSelector(" 1,2,3 ");
        Monitor monitor = nnet.getMonitor();
        monitor.setTrainingPatterns(testArray.length);
        monitor.setTotCicles(1);
        monitor.setLearning(false);
        MemoryOutputSynapse memOut = new MemoryOutputSynapse();
        // set the output synapse to write the output of the net

        if (nnet != null)
        {
            nnet.addOutputSynapse(memOut);
            System.out.println(nnet.check());
            nnet.getMonitor().setSingleThreadMode(singleThreadMode);
            nnet.go();

            for (int i = 0; i < testArray.length; i++)
            {
                double[] pattern = memOut.getNextPattern();
                System.out.println(" Output pattern # " + (i + 1) + " = "
                        + pattern[0]);
            }
            System.out.println(" Interrogating Finished ");
        }
    }

    protected void initNeuralNet()
    {

        // First create the three layers
        input = new LinearLayer();
        hidden = new SigmoidLayer();
        output = new SigmoidLayer();

        // set the dimensions of the layers
        input.setRows(48);
        hidden.setRows(50);
        output.setRows(24);

        input.setLayerName(" L.input ");
        hidden.setLayerName(" L.hidden ");
        output.setLayerName(" L.output ");

        // Now create the two Synapses
        FullSynapse synapse_IH = new FullSynapse(); /* input -> hidden conn. */
        FullSynapse synapse_HO = new FullSynapse(); /* hidden -> output conn. */

        // Connect the input layer whit the hidden layer
        input.addOutputSynapse(synapse_IH);
        hidden.addInputSynapse(synapse_IH);

        // Connect the hidden layer whit the output layer
        hidden.addOutputSynapse(synapse_HO);
        output.addInputSynapse(synapse_HO);

        // the input to the neural net
        inputSynapse = new MemoryInputSynapse();

        input.addInputSynapse(inputSynapse);

        // The Trainer and its desired output
        desiredOutputSynapse = new MemoryInputSynapse();

        TeachingSynapse trainer = new TeachingSynapse();

        trainer.setDesired(desiredOutputSynapse);

        // Now we add this structure to a NeuralNet object
        nnet = new NeuralNet();

        nnet.addLayer(input, NeuralNet.INPUT_LAYER);
        nnet.addLayer(hidden, NeuralNet.HIDDEN_LAYER);
        nnet.addLayer(output, NeuralNet.OUTPUT_LAYER);
        nnet.setTeacher(trainer);
        output.addOutputSynapse(trainer);
        nnet.addNeuralNetListener(this);
    }

    @Override
    public void netStarted(NeuralNetEvent neuralNetEvent) {
        Monitor mon = (Monitor) neuralNetEvent.getSource();
        System.out.print(" Network started for  ");
        if (mon.isLearning())
            System.out.println(" training. ");
        else
            System.out.println(" interrogation. ");
    }

    @Override
    public void cicleTerminated(NeuralNetEvent neuralNetEvent) {

    }

    @Override
    public void netStopped(NeuralNetEvent neuralNetEvent) {
        Monitor mon = (Monitor) neuralNetEvent.getSource();
        System.out.println(" Network stopped. Last RMSE= "
                + mon.getGlobalError());
    }

    @Override
    public void errorChanged(NeuralNetEvent neuralNetEvent) {
        Monitor mon = (Monitor) neuralNetEvent.getSource();
        if (mon.getCurrentCicle() % 100 == 0)
            System.out.println(" Epoch:  "
                    + (mon.getTotCicles() - mon.getCurrentCicle()) + "  RMSE: "
                    + mon.getGlobalError());
    }

    @Override
    public void netStoppedError(NeuralNetEvent neuralNetEvent, String s) {
        System.out.println(" Network stopped due the following error:  "
                + s);
    }
}
