package mikera.engine;

import mikera.util.*;

/**
 * Multi-dimension vector class
 * 
 * @author Mike
 *
 */
public final class Vector implements Cloneable {
	public float[] data;
	
	public Vector() {
	}
	
	public Vector(int size) {
		data=new float[size];
	}
	
	public Vector(float x, float y) {
		this(2);
		data[0]=x;
		data[1]=y;
	}
	
	public Vector(int x, int y, int z) {
		this(3);
		data[0]=x;
		data[1]=y;
		data[2]=z;
	}
	
	public Vector(Vector a) {
		this(a.data);
	}
	
	public float x() {
		return data[0];
	}
	
	public float y() {
		return data[1];
	}
	
	public float z() {
		return data[2];
	}
	
	public Vector(float[] adata) {
		int size=adata.length;
		data=new float[size];
		System.arraycopy(adata, 0, data, 0, size);
	}
	
	public Vector(float[] adata, int offset, int size) {
		data=new float[size];
		System.arraycopy(adata, offset, data, 0, size);
	}
	
	public Vector construct(float[] dataToEmbed) {
		Vector v=new Vector();
		v.data=dataToEmbed;
		return v;
	}
	
	public void resize(int size) {
		if (size!=data.length) {
			float[] newData=new float[size];
			System.arraycopy(data,0,newData,0,Math.min(size,data.length));
			data=newData;
		}
	}
	
	public int size() {
		return data.length;
	}
	
	public String toString() {
		String result="{";
		int max=data.length-1;
		for (int i=0; i<max; i++) {
			result+=Float.toString(data[i]);
			result+=", ";
		}
		result+=Float.toString(data[max]);
		return result+"}";
	}
	
	public Vector(float x, float y, float z) {
		this(3);
		data[0]=x;
		data[1]=y;
		data[2]=y;
	}
	
	public void add(Vector v) {
		for (int i=0; i<data.length; i++) {
			data[i]+=v.data[i];
		}
	}

	public void addMultiple(Vector v, float factor) {
		for (int i=0; i<data.length; i++) {
			data[i]+=v.data[i]*factor;
		}
	}
	
	public void set(int index, float value) {
		data[index]=value;
	}
	
	public void scale(float f) {
		for (int i=0; i<data.length; i++) {
			data[i]*=f;
		}
	}
	
	public float dot(Vector v) {
		float result=0;
		for (int i=0; i<data.length; i++) {
			result+=data[i]*v.data[i];
		}
		return result;
	}
	
	public float lengthSquared() {
		float result=0;
		for (int i=0; i<data.length; i++) {
			result+=data[i]*data[i];
		}
		return result;		
	}
	
	public float length() {
		return Maths.sqrt(lengthSquared());
	}
	
	public void cross(Vector v) {
		float x=data[1]*v.data[2]-data[2]*v.data[1];
		float y=data[2]*v.data[0]-data[0]*v.data[2];
		float z=data[0]*v.data[1]-data[1]*v.data[0];
		data[0]=x;
		data[1]=y;
		data[2]=z;
	}
	
	
	public Vector cross(Vector a, Vector b) {
		Vector target=new Vector(3);
		Vector.cross(a.data,0,b.data,0,target.data,0);
		return target;
	}
	
	public static void cross(float[] adata, int ai, float[]  bdata, int bi, float[] tdata, int ti) {
		float x=adata[ai+1]*bdata[bi+2]-adata[ai+2]*bdata[bi+1];
		float y=adata[ai+2]*bdata[bi+0]-adata[ai+0]*bdata[bi+2];
		float z=adata[ai+0]*bdata[bi+1]-adata[ai+1]*bdata[bi+0];	
		tdata[ti]=x;
		tdata[ti+1]=y;
		tdata[ti+2]=z;
	}
	
	public static float lengthSquared(float[] data, int di, int n) {
		float result=0;
		for (int i=0; i<n; i++) {
			float f=data[di+i];
			result+=f*f;
		}
		return result;			
	}	
	
	public float normalise() {
		return Vector.normalise(data,0,data.length);
	}
	
	public Vector clone() {
		return new Vector(this);
	}
	
	public static float normalise(float[] data, int di, int n) {
		float f=lengthSquared(data, di, n);
		float factor;
		if (f!=0.0) {
			f=(float)Math.sqrt(f);
			factor=1.0f/f;
		} else {
			return 0.0f;
		}
		for (int i=0; i<n; i++) {
			data[di+i]*=factor;
		}
		return f;
	}
}
