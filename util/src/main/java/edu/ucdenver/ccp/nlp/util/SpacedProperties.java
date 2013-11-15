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
package com.croeder.util;

import java.util.Properties;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.log4j.Logger;

/**
 * This class encapsulates java.util.Properties ,
 * reading the file and loading it, 
 * and some namespace trickery
 */
public class SpacedProperties {

	private Logger logger = Logger.getLogger(SpacedProperties.class);
	private Properties props;
	String namespace;	

	public SpacedProperties(File propertiesFile, String namespace) {
		props = new Properties();
		this.namespace = namespace;
		readProperties(propertiesFile);
		logger.info("read properties file: " + propertiesFile + " with namespace: " + namespace);
		//dumpProperties();
	}

	public String get(String name) {
		String x = namespace + "." + name;
		return (String)  props.get(x);
	}
	
	private void readProperties(File propertiesFile) {
        try {
            // TODO: do stream from classpath
            InputStream propsStream = new FileInputStream(propertiesFile);
            props = new Properties();
            props.load(propsStream);
			//dumpProperties();
        }
        catch (IOException e) {
            logger.error(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

	public void dumpProperties() {
		logger.info("-- property values --");
		for (Object key : props.keySet()) {
			logger.info("\"" + key + "\", \"" + props.get(key) + "\"");
			logger.info(key.getClass().getName());
		}
	}
		
}


