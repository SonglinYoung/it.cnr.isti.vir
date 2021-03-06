/*******************************************************************************
 * Copyright (c) 2013, Fabrizio Falchi and Lucia Vadicamo (NeMIS Lab., ISTI-CNR, Italy)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package it.cnr.isti.vir.distance;

import it.cnr.isti.vir.features.IByteValues;
import it.cnr.isti.vir.features.IDoubleValues;
import it.cnr.isti.vir.features.IFloatByteValues;
import it.cnr.isti.vir.features.IFloatValues;
import it.cnr.isti.vir.features.IIntValues;
import it.cnr.isti.vir.features.IUByteValues;

public class L2 {
	
	public static final int getSquared(byte[] v1, byte[] v2) {
		int dist = 0;	    
		int dif = 0;		
	    for (int i = 0; i < v1.length; i++) {
	    	dif = v1[i] - v2[i];
	    	dist += dif * dif;
	    }		
		return dist;
	}

	
	public static final int getSquared(byte[] v1, byte[] v2, int maxDist) {
		int dist = 0;	    
		int dif = 0;
	    for (int i = 0; i < v1.length; i++) {
	    	dif = v1[i] - v2[i];
	    	dist += dif * dif;
	    	if ( dist > maxDist ) return -dist;
	    }		
		return dist;
	}
	
	public static final double getSquared(double[]f1, double[]f2) {
		double acc = 0;
		for ( int j=0; j<f1.length; j++) {
			double diff = f1[j] - f2[j];
			acc += diff * diff;
		}
		return acc; 
	}
	
	public static final double getSquared(double[]f1, double[]f2, double max) {
		double acc = 0;
		for ( int j=0; j<f1.length; j++) {
			double diff = f1[j] - f2[j];
			acc += diff * diff;
			if ( acc > max ) return -acc;
		}
		return acc; 
	}
	
	public static final double get(double[]f1, double[]f2, double max) {
		double temp = getSquared(f1,f2, max*max);
		
		if (temp <0) {
			return -Math.sqrt(-temp);
		} else {
			return Math.sqrt(temp);
		}
	}
	
	public static final int getSquared(int[]f1, int[]f2) {
		int acc = 0;
		for ( int j=0; j<f1.length; j++) {
			int diff = f1[j] - f2[j];
			acc += diff * diff;
		}
		return acc; 
	}	
	
	public static final double getSquared(float[]f1, float[]f2) {
		double acc = 0;
		for ( int j=0; j<f1.length; j++) {
			double diff = f1[j] - f2[j];
			acc += diff * diff;
		}
		return acc; 
	}
	
	public static final double getSquared(float[]f1, float[]f2, double max) {
		double acc = 0;	    
	    for (int i = 0; i < f1.length; i++) {
			double diff = f1[i] - f2[i];
			acc += diff * diff;
	    	if ( acc > max ) return -acc;
	    }		
		return acc;
	}
	
	public static final double get(float[]f1, float[]f2, double max) {
		double temp = getSquared(f1,f2, max*max);
		
		if (temp <0) {
			return -Math.sqrt(-temp);
		} else {
			return Math.sqrt(temp);
		}
	}

	
	
	public static final double get(int[]f1, int[]f2) {
		return  Math.sqrt(getSquared(f1,f2));
	}
	
	public static final double get(byte[]f1, byte[]f2) {
		return  Math.sqrt(getSquared(f1,f2));
	}
	
	public static double get_fromCompSparseBytes(byte[] v1, byte[] v2) {
		return Math.sqrt(getSquared_fromCompSparseBytes(v1, v2));
	}
	
	public static double get_fromCompSparseBytes(byte[] v1, byte[] v2, double max) {
		return Math.sqrt(getSquared_fromCompSparseBytes(v1, v2, (int) Math.ceil(max*max)));
	}
	
	public static final double get(float[]f1, float[]f2) {
		return  Math.sqrt(getSquared(f1,f2));
	}

	public static final double get(double[]f1, double[]f2) {
		return  Math.sqrt(getSquared(f1,f2));
	}

	public static final double get(byte[]f1, byte[]f2, int max) {
		int temp = getSquared(f1,f2, max*max);
		
		if (temp <0) {
			return -Math.sqrt(-temp);
		} else {
			return Math.sqrt(temp);
		}
	}
	
	public static final double get(byte[]f1, byte[]f2, double max) {
		int temp = getSquared(f1,f2, (int) Math.ceil(max*max));
		
		if (temp <0) {
			return -Math.sqrt(-temp);
		} else {
			return Math.sqrt(temp);
		}
	}
	
	public static final double get(IByteValues v1, IByteValues v2 ) {
		return  Math.sqrt(getSquared(v1,v2));
	}
	
	public static final double get(IUByteValues v1, IUByteValues v2 ) {
		return  Math.sqrt(getSquared(v1,v2));
	}
	
	public static final double get(IFloatByteValues v1, IFloatByteValues v2 ) {
		return  Math.sqrt(getSquared(v1,v2));
	}
	
	public static final double get(IFloatValues v1, IFloatValues v2 ) {
		return  Math.sqrt(getSquared(v1,v2));
	}
	
	public static final double get(IDoubleValues v1, IDoubleValues v2 ) {
		return  Math.sqrt(getSquared(v1,v2));
	}
	
	public static final double get(IIntValues v1, IIntValues v2 ) {
		return  Math.sqrt(getSquared(v1,v2));
	}
	
	
	public static final int getSquared(IByteValues v1, IByteValues v2 ) {
		return getSquared(v1.getValues(), v2.getValues());
	}
	
	public static final int getSquared(IUByteValues v1, IUByteValues v2 ) {
		return getSquared(v1.getValues(), v2.getValues());
	}
	
	public static final double getSquared(IFloatByteValues v1, IFloatByteValues v2 ) {
		return getSquared(v1.getValues(), v2.getValues()) / (255.0f*Math.sqrt(v1.getLength()));
	}
	
	public static final double getSquared(IFloatValues v1, IFloatValues v2 ) {
		return getSquared(v1.getValues(), v2.getValues());
	}
	
	public static final double getSquared(IDoubleValues v1, IDoubleValues v2 ) {
		return getSquared(v1.getValues(), v2.getValues());
	}
	
	public static final double getSquared(IIntValues v1, IIntValues v2 ) {
		return getSquared(v1.getValues(), v2.getValues()) ;
	}
	
	

	public static final int getSquared_fromCompSparseBytes(byte[] v1, byte[] v2, int max) {
		
		int i1 = 0;
		int i2 = 0;
		
		int n1 = 0;
		int n2 = 0;
		
		int c1;
		int c2;
		
		int dist = 0;
		while ( i1 < v1.length || i2 < v2.length) {

			if ( n1 < n2 ) {
				c1 = v1[i1++] +128;
				
				if ( c1 == 0 ) {
					n1 += v1[i1++] + 129;
					continue;
				}
				n1++;
				dist += c1*c1;
				
			} else if ( n2 < n1 ) {
				c2 = v2[i2++] +128;
				if ( c2 == 0 ) {
					n2 += v2[i2++] + 129;
					continue;
				}
				n2++;
				dist += c2*c2;
				
			} else {
				c1 = v1[i1++] ;
				c2 = v2[i2++] ;
				n1++;
				n2++;
				if ( c1 == -128 ) n1 += v1[i1++] + 128;
				if ( c2 == -128 ) n2 += v2[i2++] + 128;
				int dif = c1 - c2;
				dist += dif*dif;
			}			
	 		
			if ( dist > max ) return -dist;
		}
		
		return dist;
	}
	
	public static final int getSquared_fromCompSparseBytes(byte[] v1, byte[] v2) {
		
		int i1 = 0;
		int i2 = 0;
		
		int n1 = 0;
		int n2 = 0;
		
		int c1;
		int c2;
		
		int dist = 0;
		while ( i1 < v1.length || i2 < v2.length) {

			if ( n1 < n2 ) {
				c1 = v1[i1++] +128;
				
				if ( c1 == 0 ) {
					n1 += v1[i1++] + 129;
					continue;
				}
				n1++;
				dist += c1*c1;
				
			} else if ( n2 < n1 ) {
				c2 = v2[i2++] +128;
				if ( c2 == 0 ) {
					n2 += v2[i2++] + 129;
					continue;
				}
				n2++;
				dist += c2*c2;
				
			} else {
				c1 = v1[i1++] ;
				c2 = v2[i2++] ;
				n1++;
				n2++;
				if ( c1 == -128 ) n1 += v1[i1++] + 128;
				if ( c2 == -128 ) n2 += v2[i2++] + 128;
				int dif = c1 - c2;
				dist += dif*dif;
			}			
	 		
		}
		
		return dist;
	}

}
