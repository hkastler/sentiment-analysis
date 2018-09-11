/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * 
 */
package com.hkstlr.sentiment.control;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;

import opennlp.tools.doccat.DoccatFactory;
import opennlp.tools.doccat.DoccatModel;
import opennlp.tools.doccat.DocumentCategorizerME;
import opennlp.tools.doccat.DocumentSample;
import opennlp.tools.doccat.DocumentSampleStream;
import opennlp.tools.util.InputStreamFactory;
import opennlp.tools.util.MarkableFileInputStreamFactory;
import opennlp.tools.util.ObjectStream;
import opennlp.tools.util.PlainTextByLineStream;
import opennlp.tools.util.TrainingParameters;

public class SentimentAnalyzer {

    private DoccatModel model;
    private DocumentCategorizerME doccat;
    Logger log = Logger.getLogger(this.getClass().getName());

    public SentimentAnalyzer() {
        super();
        init();
    }

    private void init() {
        trainModel();

    }

    public void trainModel() {

        InputStreamFactory tdata = null;

        try {

            tdata = new MarkableFileInputStreamFactory(new File("/etc/config/twitter_sentiment_training_data.train"));

        } catch (FileNotFoundException ne) {
            Path trainingPath = Paths.get("src", "main", "resources", "twitter_sentiment_training_data.train");
            try {
                tdata = new MarkableFileInputStreamFactory(trainingPath.toFile());
            } catch (FileNotFoundException e) {
                log.log(Level.SEVERE, null, e);
            }
        } catch (Exception e) {

            log.log(Level.SEVERE, null, e);
        }
        try {

            ObjectStream<String> lineStream = new PlainTextByLineStream(tdata, StandardCharsets.UTF_8);
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            TrainingParameters params = new TrainingParameters();
            params.put(TrainingParameters.ITERATIONS_PARAM, 100 + "");
            params.put(TrainingParameters.CUTOFF_PARAM, 0 + "");

            model = DocumentCategorizerME.train("en", sampleStream, params, new DoccatFactory());
            doccat = new DocumentCategorizerME(model);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int categorize(String str) throws IOException {

        double[] aProbs = doccat.categorize(str.split(" "));
        String category = doccat.getBestCategory(aProbs);

        if (category.equalsIgnoreCase("1")) {
            System.out.println(" POSITIVE " + Double.toString(aProbs[0]) + " " + Double.toString(aProbs[1]));
            System.out.print("TWEET:" + str + "\n");
            return 1;
        } else {
            System.out.println(" NEGATIVE " + Double.toString(aProbs[0]) + " " + Double.toString(aProbs[1]));
            System.out.print(" TWEET:" + str + "\n");
            return 0;
        }

    }

    public DoccatModel getModel() {
        return model;
    }

    public void setModel(DoccatModel model) {
        this.model = model;
    }

    public DocumentCategorizerME getDoccat() {
        return doccat;
    }

    public void setDoccat(DocumentCategorizerME myCategorizer) {
        this.doccat = myCategorizer;
    }

}
