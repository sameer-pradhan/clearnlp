/**
 * Copyright 2014, Emory University
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package edu.emory.clir.clearnlp.nlp.trainer;

import java.io.InputStream;
import java.io.ObjectInputStream;

import edu.emory.clir.clearnlp.classification.model.StringModel;
import edu.emory.clir.clearnlp.component.AbstractStatisticalComponent;
import edu.emory.clir.clearnlp.component.evaluation.TagEval;
import edu.emory.clir.clearnlp.component.mode.pos.DefaultPOSTagger;
import edu.emory.clir.clearnlp.component.mode.pos.POSFeatureExtractor;
import edu.emory.clir.clearnlp.nlp.configuration.AbstractTrainConfiguration;
import edu.emory.clir.clearnlp.nlp.configuration.POSTrainConfiguration;

/**
 * @since 3.0.0
 * @author Jinho D. Choi ({@code jinho.choi@emory.edu})
 */
public class POSTrainer extends AbstractNLPTrainer
{
	protected POSFeatureExtractor[] f_extractors;
	
	public POSTrainer(InputStream configuration, InputStream[] features)
	{
		super(configuration);
		f_extractors = new POSFeatureExtractor[]{new POSFeatureExtractor(features[0])};
	}
	
	@Override
	protected AbstractTrainConfiguration createConfiguration(InputStream configuration)
	{
		return new POSTrainConfiguration(configuration);
	}
	
	@Override
	protected AbstractStatisticalComponent<?,?,?,?> createComponentForCollect()
	{
		return new DefaultPOSTagger((POSTrainConfiguration)t_configuration);
	}
	
	@Override
	protected AbstractStatisticalComponent<?,?,?,?> createComponentForTrain(Object[] lexicons)
	{
		return new DefaultPOSTagger(f_extractors, lexicons);
	}
	
	@Override
	protected AbstractStatisticalComponent<?,?,?,?> createComponentForBootstrap(Object[] lexicons, StringModel[] models)
	{
		return new DefaultPOSTagger(f_extractors, lexicons, models);
	}
	
	@Override
	protected AbstractStatisticalComponent<?,?,?,?> createComponentForEvaluate(Object[] lexicons, StringModel[] models)
	{
		return new DefaultPOSTagger(f_extractors, lexicons, models, new TagEval());
	}
	
	@Override
	protected AbstractStatisticalComponent<?,?,?,?> createComponentForDecode(ObjectInputStream in)
	{
		return new DefaultPOSTagger(in);
	}
}