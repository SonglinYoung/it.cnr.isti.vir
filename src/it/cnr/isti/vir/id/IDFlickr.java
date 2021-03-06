/*******************************************************************************
 * Copyright (c), Fabrizio Falchi (NeMIS Lab., ISTI-CNR, Italy)
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met: 
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer. 
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following disclaimer in the documentation and/or other materials provided with the distribution. 
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/

package it.cnr.isti.vir.id;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.nio.ByteBuffer;

public class IDFlickr extends AbstractID  {
		
		public final long id;
		public final long secret;
		public final int serverid;
		public final byte farmid;
		
		public final long hash1;
		public final long hash2;
		public final byte hashLength;
		
		private static boolean fullToString = true;
		
		public final static char MMC = '0';
		
		public IDFlickr(
				long id,
				byte farmid,
				int serverid,
				long secret,
				long hash1,
				long hash2,
				byte hashLength) {
			this.id = id;
			
			this.secret = secret;
			this.farmid = farmid;
			this.serverid = serverid;
			
			this.hash1 = hash1;
			this.hash2 = hash2;
			this.hashLength = hashLength;
		}
		
		public String getHash() {

			String tStr =
					String.format("%016x", hash1) + 
					String.format("%016x", hash2);
			return tStr.substring(tStr.length()-hashLength, tStr.length());
					
		}

		public static final IDFlickr[] readArray(DataInput in, int n) throws IOException {
			IDFlickr[] arr = new IDFlickr[n];
			
			for ( int i=0; i<arr.length; i++ ) {
				arr[i] = new IDFlickr(in);
			}
			return arr;
		}

		
		@Override
		public final void writeData(DataOutput out) throws IOException {
			out.writeLong(id);
			out.writeLong(secret);
			out.writeInt(serverid);
			out.write(farmid);		
			out.writeLong(hash1);
			out.writeLong(hash2);
			out.write(hashLength);
		}
		
		public IDFlickr(ByteBuffer in) throws IOException {
			id = in.getLong();
			secret = in.getLong();
			serverid = in.getInt();
			farmid = in.get();
			hash1 = in.getLong();
			hash2 = in.getLong();
			hashLength = in.get();
		}
		
		public IDFlickr(DataInput in) throws IOException {
			id = in.readLong();
			secret = in.readLong();
			serverid = in.readInt();
			farmid = in.readByte();
			hash1 = in.readLong();
			hash2 = in.readLong();
			hashLength = in.readByte();
		}
		
		public String toString() {
			//if ( fullToString ) return id + "_" + farmid + "_" + serverid + "_" + Long.toHexString(secret);
			if ( fullToString )
				return	id + "_" +
						farmid + "_" + serverid + "_" + Long.toHexString(secret) + "_" +
						this.getHash();
			else return Long.toString(id);
		}
		
		public String toString_tabbed() {
			return id + "\t" + farmid + "\t" + serverid + "\t" + Long.toHexString(secret);

		}
		

		/**
		 * @param opt
		 * 		s	small square 75x75
		 * 		q	large square 150x150
		 * 		t	thumbnail, 100 on longest side
		 * 		m	small, 240 on longest side
		 * 		n	small, 320 on longest sidee
		 * 		z	medium 640, 640 on longest side
		 * 		c	medium 800, 800 on longest side�
		 * 		b	large, 1024 on longest side*
		 * 		h	large 1600, 1600 on longest side�
		 * 		k	large 2048, 2048 on longest side�
		 *
		 * @return Flickr URL
		 */
		public String getFlickrURL(char opt) {
			return
		    		  "https://c2.staticflickr.com/" +
		    		  farmid + "/" +
		    		  serverid + "/" +
		    		  id + "_" +
		    		  String.format("%010x", secret) + //Long.toHexString(secret) + "_" + 
		    		  "_" + opt +".jpg";
		}
		
		public String getURL(char opt) {
			if ( opt==MMC ) return getURL_mmc();
			else return getFlickrURL(opt);
		}

		/**
		 * @return Flickr large square image
		 */
		public String getURL_q() {
			return getFlickrURL('q');
		}
		
		//https://s3-us-west-2.amazonaws.com/multimedia-commons/data/images/000/24a/00024a73d1a4c32fb29732d56a2.jpg
		public String getURL_mmc() {
			String hashString = getHash();
			return 
					"https://s3-us-west-2.amazonaws.com/multimedia-commons/data/images/" +
					hashString.substring(0, 3) + "/" + 
					hashString.substring(3, 6) + "/" +
					hashString + ".jpg";
					
		}
		
		
		private static final char[] code_string = "123456789abcdefghijkmnopqrstuvwxyzABCDEFGHJKLMNPQRSTUVWXYZ".toCharArray();
		public static final String getBase58(long value) {
			if ( value == 0 ) return "";
			
			StringBuilder tStr = new StringBuilder();
			
			long t = value;
			while ( t > 0 ) {
				int rem = (int) (t%58);
				tStr.append(code_string[rem]);
				t/=58L;
			}
			
			return tStr.reverse().toString();
		}
		
		public String getFlickrPage() {
			return "https://flic.kr/p/" + getBase58(id);
		}
		
		public boolean equals(Object obj) {
			return this.id == ((IDFlickr) obj).id;
		}
		
		
		public final int hashCode() {
			return  (int)( id ^ (id >>> 32) );	
		}

		@Override
		public final AbstractID getID() {
			return this;
		}
		
		@Override
		public int compareTo(AbstractID o) {
			// if ID classes differ the class id is used for comparing
			if ( !o.getClass().equals(IDFlickr.class) )
				return	IDClasses.getClassID(this.getClass()) - IDClasses.getClassID(o.getClass());
			
			long given = ((IDFlickr) o).id;
			if ( id == given ) return 0;
			if ( id > given ) return 1;
			return -1;
		}
		
		
}