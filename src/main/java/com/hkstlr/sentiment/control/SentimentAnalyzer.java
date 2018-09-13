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
 * see https://github.com/technobium/opennlp-categorizer
 * 
 */
package com.hkstlr.sentiment.control;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.Locale;
import java.util.Optional;
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
    private String trainingDataFile;
    static final Logger LOG = Logger.getLogger(SentimentAnalyzer.class.getName());

    public SentimentAnalyzer() {
        super();
        init();
    }

    public SentimentAnalyzer(String trainingDataFile) {
        this.trainingDataFile = trainingDataFile;
        init();
    }

    private void init() {
        trainModel();

    }

    public InputStreamFactory getTrainingData() {

        InputStreamFactory tdata = null;
        Optional<String> tdataCustomFile = Optional.ofNullable(trainingDataFile);
        if (tdataCustomFile.isPresent() && !tdataCustomFile.get().isEmpty()) {
            try {
                tdata = new MarkableFileInputStreamFactory(Paths.get(tdataCustomFile.get()).toFile());

            } catch (FileNotFoundException e) {
                LOG.log(Level.SEVERE, null, e);
            }
            return tdata;
        }

        try {

            tdata = new MarkableFileInputStreamFactory(
                    Paths.get("/etc/config/twitter_sentiment_training_data.train").toFile());

        } catch (FileNotFoundException ne) {

            try {

                tdata = new MarkableFileInputStreamFactory(
                        Paths.get("src", "main", "resources", "twitter_sentiment_training_data.train").toFile());

            } catch (FileNotFoundException e) {
                LOG.log(Level.SEVERE, null, e);
            }
        } catch (Exception e) {

            LOG.log(Level.SEVERE, null, e);
        }
        return tdata;
    }

    public void trainModel() {

        try {

            ObjectStream<String> lineStream = new PlainTextByLineStream(getTrainingData(), StandardCharsets.UTF_8);
            ObjectStream<DocumentSample> sampleStream = new DocumentSampleStream(lineStream);

            TrainingParameters params = new TrainingParameters();
            params.put(TrainingParameters.ITERATIONS_PARAM, 100 + "");
            params.put(TrainingParameters.CUTOFF_PARAM, 0 + "");

            model = DocumentCategorizerME.train(Locale.ENGLISH.getLanguage(), sampleStream, params,
                    new DoccatFactory());
            doccat = new DocumentCategorizerME(model);

        } catch (IOException e) {
            LOG.log(Level.SEVERE, null, e);
        }
    }

    public double[] getCategorize(String str) {

        return doccat.categorize(opennlp.tools.tokenize.WhitespaceTokenizer.INSTANCE.tokenize(str));
    }

    public String getBestCategory(String str) {

        return doccat.getBestCategory(getCategorize(str));
    }

    public Object[] getCategorizeAndBestCategory(String str) {
        Object[] returnObj = new Object[2];

        returnObj[0] = getCategorize(str);
        returnObj[1] = doccat.getBestCategory((double[]) returnObj[0]);

        return returnObj;
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

    public void setDoccat(DocumentCategorizerME doccat) {
        this.doccat = doccat;
    }

    /**
     * @return the trainingDataFile
     */
    public String getTrainingDataFile() {
        return trainingDataFile;
    }

    /**
     * @param trainingDataFile the trainingDataFile to set
     */
    public void setTrainingDataFile(String trainingDataFile) {
        this.trainingDataFile = trainingDataFile;
    }

}
