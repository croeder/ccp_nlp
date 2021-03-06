/*
 Copyright (c) 2013, Regents of the University of Colorado
 All rights reserved.

 Redistribution and use in source and binary forms, with or without modification,
 are permitted provided that the following conditions are met:

 * Redistributions of source code must retain the above copyright notice, this
    list of conditions and the following disclaimer.

 * Redistributions in binary form must reproduce the above copyright notice,
    this list of conditions and the following disclaimer in the documentation
    and/or other materials provided with the distribution.

 * Neither the name of the University of Colorado nor the names of its
    contributors may be used to endorse or promote products derived from this
    software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR
 ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package edu.ucdenver.ccp.nlp.cr;


import static java.lang.System.out;
import static  org.junit.Assert.assertEquals;
import static  org.junit.Assert.assertTrue;
import static  org.junit.Assert.assertNotNull;

import java.util.Collection;

import org.junit.Test;

import org.apache.uima.UIMAException;
import org.apache.uima.collection.CollectionReader;
import org.apache.uima.collection.CollectionReaderDescription;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;

import org.uimafit.factory.CollectionReaderFactory;
import org.uimafit.pipeline.JCasIterable;
import org.uimafit.util.JCasUtil;
import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.factory.CollectionReaderFactory;
import org.uimafit.factory.TypeSystemDescriptionFactory;

import edu.ucdenver.ccp.nlp.doc.DocumentProviderType;
import edu.ucdenver.ccp.nlp.ts.TextAnnotation;
import edu.ucdenver.ccp.nlp.ts.ClassMention;
import edu.ucdenver.ccp.nlp.ts.SlotMention;
import edu.ucdenver.ccp.nlp.ts.StringSlotMention;

public class MedlineDbCollectionReaderTest {


	@Test
	public void testCollectionReaderMedline() throws Exception {
		String[] typeSystemDescriptions = { "edu.ucdenver.ccp.nlp.ts.TypeSystem" };
		TypeSystemDescription tsd 
			= TypeSystemDescriptionFactory.createTypeSystemDescription(typeSystemDescriptions); 
		CollectionReader cr =  MedlineDbCollectionReader.createCollectionReader(tsd, 1);


		JCasIterable iter = new JCasIterable(cr);
		assertTrue(iter.hasNext());
		JCas jcas = iter.next();
		assertEquals(
			"In order to throw light on the problems related to the magnitude and the possibility of maintaining pressor response in the case of bilateral carotid occlusion (BCO), acute experiments were carried out on heparinized cats in chloralose-urethane narcosis and spontaneous respiration. The perfusion pressure in a hind leg autoperfused with a roller pump with a constant flow and the arterial blood pressure were recorded electromanometrically. A study was made of the changes taking place under the effect of BCO in the normal animal, in animals in a haemorrhagic state, after pharmacological alpha-adrenergic blockade, haemorrhage after alpha-adrenergic blockade, retransfusion of blood + alpha-adrenergic blocking agent and after local application of 0.01 papaverine. It was established that some of the factors determining the haemodynamic state of the organism, such as: blood volume, arterial pressure, vascular resistance, cardiac output, etc., are of great significance for the realization of the pressor response to BCO, but the haemodynamic state of the animal before the occlusion and the interactions between the abovementioned factors are decisive for the form, magnitude and maintenance of the pressor response in BCO.",
			jcas.getDocumentText());

	}

}
