/*******************************************************************************
 * Copyright (c) 2013, Fabrizio Falchi (NeMIS Lab., ISTI-CNR, Italy)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package it.cnr.isti.vir.features.localfeatures;

import it.cnr.isti.vir.distance.L2;
import it.cnr.isti.vir.features.IByteValues;
import it.cnr.isti.vir.features.IFloatValues;
import it.cnr.isti.vir.util.bytes.FloatByteArrayUtil;

import java.io.DataInput;
import java.io.IOException;
import java.nio.ByteBuffer;

public class SIFTPCAFloat extends ALocalFeature<SIFTPCAFloatGroup> implements IFloatValues{
	

	final float[] values; 
	
	@Override
	public final int getLength() {
		return values.length;
	}
	
	public final double getMmaxSQRDistValue() {
		return 2.0;
	}

	@Override
    public float[] getValues() {
        return values;
    }
	
	public SIFTPCAFloat(DataInput str ) throws IOException {
            super(str);	
		int dim = str.readByte();
                if ( dim < 0 ) dim &= 0xff;
		values = new float[dim];
		for ( int i=0; i<values.length; i++ ) {
			values[i] = str.readFloat();
		}
	}
	
	public SIFTPCAFloat(ByteBuffer src ) throws IOException {
                super(src);	
		int dim = src.get();
                if ( dim < 0 ) dim &= 0xff;
		values = new float[dim];
		for ( int i=0; i<values.length; i++ ) {
			values[i] = src.getFloat();
		}
	}
        
    public SIFTPCAFloat(KeyPoint kp, float[] values) {
		this(kp, values, null);
	}
	
	public SIFTPCAFloat(KeyPoint kp, float[] values, SIFTPCAFloatGroup group) {
		this.kp = kp;
		this.values = values;
		this.linkedGroup = group;
	}
	
	public SIFTPCAFloat(float[] values) {
		super((KeyPoint) null, null);
		this.values = values;
	}

	public int getDataByteSize() {
		return values.length*4 +1;
	}
	
	public int putDescriptor(byte[] bArr, int bArrI) {
		bArr[bArrI++]  = (byte) values.length;
		return FloatByteArrayUtil.convToBytes(values, bArr, bArrI);
	}

	@Override
	public int compareTo(ALocalFeature<SIFTPCAFloatGroup> given) {
		if ( this == given ) return 0;
		if ( this.kp != given.kp ) {
			if ( kp == null ) return -1;
			if ( given.kp == null ) return 1;
			int tComp = this.kp.compareTo( given.kp);	
			if ( tComp != 0 ) return tComp;
		}
		for ( int i=0; i<values.length; i++ ) {
			int tComp = Float.compare(values[i], ((SIFTPCAFloat)given).values[i]);
			if ( tComp != 0 ) return tComp;
		}
		return 0;
	}

	@Override
	public Class<SIFTPCAFloatGroup> getGroupClass() {
		return SIFTPCAFloatGroup.class;
	}
	
	public static final double getL2SquaredDistance(SIFTPCAFloat s1, SIFTPCAFloat s2) {
		return L2.getSquared(s1.values, s2.values);
	}
	

	public static final double getL2SquaredDistance(SIFTPCAFloat s1, SIFTPCAFloat s2, double maxDist ) {
		return L2.getSquared(s1.values, s2.values, maxDist);
	}
	
	public static final double getDistance_Norm(SIFTPCAFloat s1, SIFTPCAFloat s2 ) {
		return Math.sqrt(getL2SquaredDistance(s1,s2)/s1.getMmaxSQRDistValue());
	}

	

	
	
}
