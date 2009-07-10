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

package org.apache.mahout.cf.taste.impl.recommender;

import org.apache.mahout.cf.taste.common.Refreshable;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.model.User;

import java.util.Collection;

/**
 * <p>Returns the "similarity" between two clusters of users, according to some definition of similarity. Subclassses
 * define different notions of similarity.</p>
 *
 * @see TreeClusteringRecommender
 */
public interface ClusterSimilarity extends Refreshable {

  /**
   * @param cluster1 first cluster of {@link User}s
   * @param cluster2 second cluste rof {@link User}s
   * @return "distance" between clusters; a positiv
   * @throws TasteException           if an error occurs while computing similarity, such as errors accessing an
   *                                  underlying {@link org.apache.mahout.cf.taste.model.DataModel}
   * @throws IllegalArgumentException if either argument is null or empty
   */
  double getSimilarity(Collection<User> cluster1, Collection<User> cluster2) throws TasteException;

}
