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
package edu.ucdenver.ccp.nlp.ae;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Collection;
import java.util.Set;
import java.util.TreeSet;

import org.apache.uima.UimaContext;
import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngine;
import org.apache.uima.analysis_engine.AnalysisEngineDescription;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.metadata.TypeSystemDescription;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.jcas.JCas;
import org.apache.uima.jcas.JFSIndexRepository;
import org.apache.uima.jcas.cas.FSArray;
import org.apache.uima.jcas.cas.StringArray;
import org.apache.uima.jcas.tcas.Annotation;
import org.apache.uima.cas.text.AnnotationIndex;
import org.apache.uima.examples.SourceDocumentInformation;

import edu.ucdenver.ccp.nlp.ts.IdDictTerm;
import edu.ucdenver.ccp.nlp.ts.Protein;
import edu.ucdenver.ccp.nlp.ts.DocumentInformation;

import org.uimafit.factory.AnalysisEngineFactory;
import org.uimafit.descriptor.SofaCapability;


import edu.ucdenver.ccp.nlp.ts.SentenceAnnotation;
import edu.ucdenver.ccp.nlp.ts.TextAnnotation;
import org.apache.uima.conceptMapper.support.tokenizer.TokenAnnotation;
import edu.ucdenver.ccp.nlp.ts.Annotator;
import edu.ucdenver.ccp.nlp.ts.ClassMention;
import edu.ucdenver.ccp.nlp.tsx.ClassMentionX;
import edu.ucdenver.ccp.nlp.ts.IntegerSlotMention;
import edu.ucdenver.ccp.nlp.ts.StringSlotMention;
import edu.ucdenver.ccp.nlp.ts.PrimitiveSlotMention;
import edu.ucdenver.ccp.nlp.ts.ComplexSlotMention;


@SofaCapability
public class Debug_AE extends JCasAnnotator_ImplBase {
    private final boolean DEBUG = false;
	private String[] classMentionNames = {
						"hates", "loves", "cell_type", "normalized_gene",
						"protein-list", "expression", "c-expression", "c-protein",
						"protein", "protein-list", "g-protein-list", "cell",
						"c-cell-type", "cell-list"
	};
	private Set<String> classMentionNameSet = new TreeSet<String>();


    @Override
	public void initialize(UimaContext context) throws ResourceInitializationException {
		super.initialize(context);
		classMentionNameSet.addAll(Arrays.asList(classMentionNames));
    }


    @Override
	public void process(JCas jcas) throws AnalysisEngineProcessException {

        FSIterator<Annotation> annotIter = jcas.getJFSIndexRepository().getAnnotationIndex().iterator();
        while (annotIter.hasNext()) {

	        Annotation annot = (Annotation) annotIter.next();
			if (DEBUG && annot instanceof TokenAnnotation) {
				System.out.println("SEMANTIC token: " + annot.getBegin() + ", " + annot.getEnd() + " \"" + annot.getCoveredText() + "\"");
			}
			if (annot instanceof DocumentInformation) {
				DocumentInformation di = (DocumentInformation) annot;
				System.out.println("Source doc. \"" + di.getUri() + "\" id:" + di.getDocumentId());
			}
			if (annot instanceof SourceDocumentInformation) {
				SourceDocumentInformation sdi = (SourceDocumentInformation) annot;
				System.out.println("Source doc. \"" + sdi.getUri() + "\"");
			}
			if (DEBUG && annot instanceof IdDictTerm) {
				IdDictTerm dt = (IdDictTerm) annot;
				System.out.println("SEMANTIC dictTerm (ConceptMapper):\"" 
					+ " canon:" + dt.getDictCanon() + "\"" 
					+ " (" + annot.getBegin() + ", " + annot.getEnd() + ")"
					+ " covered:  \"" + annot.getCoveredText() + "\""
					+ " matched: \"" + dt.getMatchedText() + "\"" 
					+ " id: \"" + dt.getId() + "\"");
			}
			else if (annot instanceof Protein) {
				Protein proteinAnnot = (Protein) annot;
				System.out.println("PROTEIN annotation:" + proteinAnnot.getPrefix() + ", " + proteinAnnot.getSuffix());
			}
			else if (annot instanceof TextAnnotation) {
	
	        	TextAnnotation textAnnot = (TextAnnotation) annot;
	
				if (textAnnot instanceof TextAnnotation) {
					Annotator annotator = textAnnot.getAnnotator();
					if ( DEBUG && annotator != null) {
						System.out.println("Annotator:"
						+ " First Name: " + annotator.getFirstName()
						+ " Last Name: " + annotator.getLastName()
						+ " Affiliation: " + annotator.getAffiliation());
					}
					else {
						if (DEBUG)  { System.out.println(" NO ANNOTATOR " ); }
					}
				}
	
				// SENTENCE
				if (DEBUG && textAnnot instanceof SentenceAnnotation) {
					System.out.println("SEMANTIC sentence: \"" + textAnnot.getCoveredText() + "\"");
				}
				else if (annot instanceof TextAnnotation) {
					TextAnnotation ta =  (TextAnnotation) annot;
					ClassMention cm = ta.getClassMention();
					if (cm == null) {
						try {
							System.out.println("NULL ClassMention for TA class:" + ta.getClass().getName() + " with covered=" + ta.getCoveredText());
						}
						catch (Exception x)  {
							System.out.println("This TA is really hosed");
						}
					}
				
	
					// TOKEN   ??? un-"normalized"??? WTF from CM tokenizer?
					if (DEBUG && cm != null && cm.getMentionName().equals("token")) {
						IntegerSlotMention numberSlot = (IntegerSlotMention) ClassMentionX.getSlotMentionByName(cm,"tokenNumber");
						System.out.println("class mention token:" + ta.getCoveredText() + " number: " + numberSlot.getSlotValues(0));
					}	
	
	
					// ** SEMANTIC slots, DMAP stuff **
					else if (cm != null && classMentionNameSet.contains(cm.getMentionName().toLowerCase())) {
						System.out.println("SEMANTIC: name:\"" + cm.getMentionName() + "\" covered: \"" +  ta.getCoveredText() + "\"" );
	
						Collection<String> slotNames;
						slotNames =  ClassMentionX.getComplexSlotMentionNames(cm);
						if (slotNames != null ) {
							for (String slotName : slotNames) {
								System.out.println("  complex slot name:" + slotName);
								ComplexSlotMention complexSlot = ClassMentionX.getComplexSlotMentionByName(cm, slotName);

								ClassMention referent = (ClassMention) complexSlot.getClassMentions(0);
								System.out.print("  Complex Slot:" + slotName + " refers to " + referent.getMentionName());

								//PrimitiveSlotMention pSlot =(PrimitiveSlotMention) ClassMentionX.getSlotMentionByName(referent, slotName);
								PrimitiveSlotMention pSlot =(PrimitiveSlotMention) ClassMentionX.getSlotMentionByName(referent, "ID");

								if (pSlot != null && pSlot instanceof StringSlotMention ) {
									System.out.println(" has value: " + ((StringSlotMention) pSlot).getSlotValues().get(0) );						
								}
								else { System.out.println(" no value "); }
								
							}
						}
	
						Collection<String> primitiveSlotNames =  ClassMentionX.getPrimitiveSlotMentionNames(cm);
						if (primitiveSlotNames != null && primitiveSlotNames.size() > 0) {
							for (String slotName : primitiveSlotNames) {
								System.out.print("  primitive slot:" + slotName + " "); 
								PrimitiveSlotMention pSlot = ClassMentionX.getPrimitiveSlotMentionByName(cm, slotName);
								if (pSlot instanceof StringSlotMention) {
									System.out.println("  has value: " + ((StringSlotMention) pSlot).getSlotValues().get(0) );						
								}
								else { System.out.println(""); }
							}
						}
					}
	
	
					// OTHER
					else if ( DEBUG && cm != null) { //DEBUG) {
						System.out.println("unknown mention: " + cm.getMentionName());
						Collection<String> slotNames =  ClassMentionX.getPrimitiveSlotMentionNames(cm);
						for (String slotName : slotNames) {
							System.out.print("    slot:" + slotName);
							////// + " value:" + ClassMentionX.getStringSlotMentionValue(cm, slotName) );
						}
	
						slotNames =  ClassMentionX.getComplexSlotMentionNames(cm);
						for (String slotName : slotNames) {
							System.out.print("    complex slot:" + slotName + " "); 
						}
					}
	
				}
			}
		}

    }

    @Override
	public void collectionProcessComplete() throws AnalysisEngineProcessException {
        super.collectionProcessComplete();
    }
    
    
	public static AnalysisEngine createAnalysisEngine(TypeSystemDescription tsd)
	throws ResourceInitializationException {
		return AnalysisEngineFactory.createPrimitive(
				Debug_AE.class, tsd
		);
	}
	public static AnalysisEngineDescription createAnalysisEngineDescription(TypeSystemDescription tsd)
	throws ResourceInitializationException {
		return AnalysisEngineFactory.createPrimitiveDescription(
				Debug_AE.class, tsd
		);
	}
	
	

}
