/* Copyright (C) 2007-2010 Center for Computational Pharmacology, University of Colorado School of Medicine
 * 
 * This file is part of the CCP NLP library.
 * The CCP NLP library is free software: you can redistribute it and/or
 * modify it under the terms of the GNU General Public License
 * as published by the Free Software Foundation; either version 2
 * of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.
 */

package edu.ucdenver.ccp.nlp.ae.opendmap;

public class OpenDMAPRuntimeException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = -5266925776965430594L;

   
    public OpenDMAPRuntimeException(String message) {
        super(message);
    }
    
    public OpenDMAPRuntimeException(String message, Throwable e) {
        super(message, e);
    }
}
