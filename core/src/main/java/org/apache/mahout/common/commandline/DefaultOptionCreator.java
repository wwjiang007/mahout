/**
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.mahout.common.commandline;

import org.apache.commons.cli2.Option;
import org.apache.commons.cli2.builder.ArgumentBuilder;
import org.apache.commons.cli2.builder.DefaultOptionBuilder;
import org.apache.mahout.common.distance.SquaredEuclideanDistanceMeasure;

public final class DefaultOptionCreator {

  // private option keywords
  private static final String THRESHOLD_OPTION = "threshold";

  private static final String EMIT_MOST_LIKELY_OPTION = "emitMostLikely";

  private static final String CLUSTERING_OPTION = "clustering";

  private static final String MAX_REDUCERS_OPTION = "maxRed";

  private static final String CONVERGENCE_DELTA_OPTION = "convergenceDelta";

  private static final String NUM_CLUSTERS_OPTION = "numClusters";

  private static final String MAX_ITERATIONS_OPTION = "maxIter";

  private static final String T2_OPTION = "t2";

  private static final String T1_OPTION = "t1";

  private static final String DISTANCE_MEASURE_OPTION = "distanceMeasure";

  private static final String OVERWRITE_OPTION = "overwrite";

  private static final String OUTPUT_OPTION = "output";

  private static final String CLUSTERS_IN_OPTION = "clusters";

  private static final String INPUT_OPTION = "input";

  // public keys used to access parsed arguments map
  public static final String T2_OPTION_KEY = "--" + T2_OPTION;

  public static final String T1_OPTION_KEY = "--" + T1_OPTION;

  public static final String DISTANCE_MEASURE_OPTION_KEY = "--" + DISTANCE_MEASURE_OPTION;

  public static final String OVERWRITE_OPTION_KEY = "--" + OVERWRITE_OPTION;

  public static final String CLUSTERING_OPTION_KEY = "--" + CLUSTERING_OPTION;

  public static final String NUM_CLUSTERS_OPTION_KEY = "--" + NUM_CLUSTERS_OPTION;

  public static final String MAX_REDUCERS_OPTION_KEY = "--" + MAX_REDUCERS_OPTION;

  public static final String MAX_ITERATIONS_OPTION_KEY = "--" + MAX_ITERATIONS_OPTION;

  public static final String EMIT_MOST_LIKELY_OPTION_KEY = "--" + EMIT_MOST_LIKELY_OPTION;

  public static final String THRESHOLD_OPTION_KEY = "--" + THRESHOLD_OPTION;

  public static final String CLUSTERS_IN_OPTION_KEY = "--" + CLUSTERS_IN_OPTION;

  public static final String CONVERGENCE_DELTA_OPTION_KEY = "--" + CONVERGENCE_DELTA_OPTION;

  public static final String INPUT_OPTION_KEY = "--" + INPUT_OPTION;

  public static final String OUTPUT_OPTION_KEY = "--" + OUTPUT_OPTION;

  private DefaultOptionCreator() {
  }

  /**
   * Returns a default command line option for help. Used by all clustering jobs and many others
   * */
  public static Option helpOption() {
    return new DefaultOptionBuilder().withLongName("help").withDescription("Print out help").withShortName("h").create();
  }

  /**
   * Returns a default command line option for input directory specification. Used by all clustering jobs plus others
   */
  public static DefaultOptionBuilder inputOption() {
    return new DefaultOptionBuilder().withLongName(INPUT_OPTION).withRequired(false).withShortName("i")
        .withArgument(new ArgumentBuilder().withName(INPUT_OPTION).withMinimum(1).withMaximum(1).create())
        .withDescription("Path to job input directory.");
  }

  /**
   * Returns a default command line option for clusters input directory specification. Used by FuzzyKmeans, Kmeans
   */
  public static DefaultOptionBuilder clustersInOption() {
    return new DefaultOptionBuilder().withLongName(CLUSTERS_IN_OPTION).withRequired(true).withArgument(new ArgumentBuilder()
        .withName(CLUSTERS_IN_OPTION).withMinimum(1).withMaximum(1).create())
        .withDescription("The path to the initial clusters directory. Must be a SequenceFile of some type of Cluster")
        .withShortName("c");
  }

  /**
   * Returns a default command line option for output directory specification. Used by all clustering jobs plus others
   */
  public static DefaultOptionBuilder outputOption() {
    return new DefaultOptionBuilder().withLongName(OUTPUT_OPTION).withRequired(false).withShortName("o")
        .withArgument(new ArgumentBuilder().withName(OUTPUT_OPTION).withMinimum(1).withMaximum(1).create())
        .withDescription("The directory pathname for output.");
  }

  /**
   * Returns a default command line option for output directory overwriting. Used by all clustering jobs
   */
  public static DefaultOptionBuilder overwriteOption() {
    return new DefaultOptionBuilder().withLongName(OVERWRITE_OPTION).withRequired(false)
        .withDescription("If present, overwrite the output directory before running job").withShortName("ow");
  }

  /**
   * Returns a default command line option for specification of distance measure class to use.
   * Used by Canopy, FuzzyKmeans, Kmeans, MeanShift
   */
  public static DefaultOptionBuilder distanceMeasureOption() {
    return new DefaultOptionBuilder().withLongName(DISTANCE_MEASURE_OPTION).withRequired(false).withShortName("dm")
        .withArgument(new ArgumentBuilder().withName(DISTANCE_MEASURE_OPTION).withDefault(SquaredEuclideanDistanceMeasure.class
            .getName()).withMinimum(1).withMaximum(1).create())
        .withDescription("The classname of the DistanceMeasure. Default is SquaredEuclidean");
  }

  /**
   * Returns a default command line option for specification of T1. Used by Canopy, MeanShift
   */
  public static DefaultOptionBuilder t1Option() {
    return new DefaultOptionBuilder().withLongName(T1_OPTION).withRequired(true).withArgument(new ArgumentBuilder()
        .withName(T1_OPTION).withMinimum(1).withMaximum(1).create()).withDescription("T1 threshold value").withShortName(T1_OPTION);
  }

  /**
   * Returns a default command line option for specification of T2. Used by Canopy, MeanShift
   */
  public static DefaultOptionBuilder t2Option() {
    return new DefaultOptionBuilder().withLongName(T2_OPTION).withRequired(true).withArgument(new ArgumentBuilder()
        .withName(T2_OPTION).withMinimum(1).withMaximum(1).create()).withDescription("T2 threshold value").withShortName(T2_OPTION);
  }

  /**
   * Returns a default command line option for specification of max number of iterations.
   * Used by Dirichlet, FuzzyKmeans, Kmeans, LDA
   */
  public static DefaultOptionBuilder maxIterationsOption() {
    // default value used by LDA which overrides withRequired(false)
    return new DefaultOptionBuilder().withLongName(MAX_ITERATIONS_OPTION).withRequired(true).withShortName("x")
        .withArgument(new ArgumentBuilder().withName(MAX_ITERATIONS_OPTION).withDefault("-1").withMinimum(1).withMaximum(1)
            .create()).withDescription("The maximum number of iterations.");
  }

  /**
   * Returns a default command line option for specification of numbers of clusters to create.
   * Used by Dirichlet, FuzzyKmeans, Kmeans
   */
  public static DefaultOptionBuilder numClustersOption() {
    return new DefaultOptionBuilder().withLongName(NUM_CLUSTERS_OPTION).withRequired(false).withArgument(new ArgumentBuilder()
        .withName("k").withMinimum(1).withMaximum(1).create()).withDescription("The number of clusters to create")
        .withShortName("k");
  }

  /**
   * Returns a default command line option for convergence delta specification.
   * Used by FuzzyKmeans, Kmeans, MeanShift
   */
  public static DefaultOptionBuilder convergenceOption() {
    return new DefaultOptionBuilder().withLongName(CONVERGENCE_DELTA_OPTION).withRequired(false).withShortName("cd")
        .withArgument(new ArgumentBuilder().withName(CONVERGENCE_DELTA_OPTION).withDefault("0.5").withMinimum(1).withMaximum(1)
            .create()).withDescription("The convergence delta value. Default is 0.5");
  }

  /**
   * Returns a default command line option for specifying the max number of reducers.
   * Used by Dirichlet, FuzzyKmeans, Kmeans and LDA
   */
  public static DefaultOptionBuilder numReducersOption() {
    return new DefaultOptionBuilder().withLongName(MAX_REDUCERS_OPTION).withRequired(false).withShortName("r")
        .withArgument(new ArgumentBuilder().withName(MAX_REDUCERS_OPTION).withDefault("2").withMinimum(1).withMaximum(1).create())
        .withDescription("The number of reduce tasks. Defaults to 2");
  }

  /**
   * Returns a default command line option for clustering specification. Used by all clustering except LDA
   */
  public static DefaultOptionBuilder clusteringOption() {
    return new DefaultOptionBuilder().withLongName(CLUSTERING_OPTION).withRequired(false)
        .withDescription("If present, run clustering after the iterations have taken place").withShortName("cl");
  }

  /**
   * Returns a default command line option for specifying the emitMostLikely flag. Used by Dirichlet and FuzzyKmeans
   */
  public static DefaultOptionBuilder emitMostLikelyOption() {
    return new DefaultOptionBuilder().withLongName(EMIT_MOST_LIKELY_OPTION).withRequired(false).withShortName("e")
        .withArgument(new ArgumentBuilder().withName(EMIT_MOST_LIKELY_OPTION).withDefault("true").withMinimum(1).withMaximum(1)
            .create()).withDescription("True if clustering should emit the most likely point only, "
            + "false for threshold clustering. Default is true");
  }

  /**
   * Returns a default command line option for specifying the clustering threshold value.
   * Used by Dirichlet and FuzzyKmeans
   */
  public static DefaultOptionBuilder thresholdOption() {
    return new DefaultOptionBuilder().withLongName(THRESHOLD_OPTION).withRequired(false).withShortName("t")
        .withArgument(new ArgumentBuilder().withName(THRESHOLD_OPTION).withDefault("0").withMinimum(1).withMaximum(1).create())
        .withDescription("The pdf threshold used for cluster determination. Default is 0");
  }

}
