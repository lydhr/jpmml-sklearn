/*
 * Copyright (c) 2015 Villu Ruusmann
 *
 * This file is part of JPMML-SkLearn
 *
 * JPMML-SkLearn is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * JPMML-SkLearn is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with JPMML-SkLearn.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.jpmml.sklearn;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import joblib.NDArrayWrapper;
import net.razorvine.pickle.objects.ClassDict;
import numpy.core.NDArray;
import numpy.core.NDArrayUtil;
import numpy.core.Scalar;

public class ClassDictUtil {

	private ClassDictUtil(){
	}

	static
	public List<?> getArray(ClassDict dict, String name){
		Object object = dict.get(name);

		if(object instanceof NDArray){
			NDArray array = (NDArray)object;

			return NDArrayUtil.getContent(array);
		} else

		if(object instanceof NDArrayWrapper){
			NDArrayWrapper arrayWrapper = (NDArrayWrapper)object;

			NDArray array = arrayWrapper.getContent();

			return NDArrayUtil.getContent(array);
		} else

		if(object instanceof Scalar){
			Scalar scalar = (Scalar)object;

			return scalar.getContent();
		}

		throw new IllegalArgumentException();
	}

	static
	public List<?> getArray(ClassDict dict, String name, String key){
		Object object = dict.get(name);

		if(object instanceof NDArray){
			NDArray array = (NDArray)object;

			return NDArrayUtil.getContent(array, key);
		} else

		if(object instanceof NDArrayWrapper){
			NDArrayWrapper arrayWrapper = (NDArrayWrapper)object;

			NDArray array = arrayWrapper.getContent();

			return NDArrayUtil.getContent(array, key);
		}

		throw new IllegalArgumentException();
	}

	static
	public int[] getShape(ClassDict dict, String name){
		Object object = dict.get(name);

		if(object instanceof NDArray){
			NDArray array = (NDArray)object;

			return NDArrayUtil.getShape(array);
		} else

		if(object instanceof NDArrayWrapper){
			NDArrayWrapper arrayWrapper = (NDArrayWrapper)object;

			NDArray array = arrayWrapper.getContent();

			return NDArrayUtil.getShape(array);
		}

		throw new IllegalArgumentException();
	}

	static
	public String toString(ClassDict dict){
		StringBuffer sb = new StringBuffer();

		sb.append("\n{\n");

		String sep = "";

		List<? extends Map.Entry<String, ?>> entries = new ArrayList<>(dict.entrySet());

		Comparator<Map.Entry<String, ?>> comparator = new Comparator<Map.Entry<String, ?>>(){

			@Override
			public int compare(Map.Entry<String, ?> left, Map.Entry<String, ?> right){
				return (left.getKey()).compareToIgnoreCase(right.getKey());
			}
		};
		Collections.sort(entries, comparator);

		for(Map.Entry<String, ?> entry : entries){
			sb.append(sep);

			sep = "\n";

			String key = entry.getKey();
			Object value = entry.getValue();

			sb.append("\t" + key + "=" + value + (" /*" + (value != null ? value.getClass() : "N/A") + "*/"));
		}

		sb.append("\n}\n");

		return sb.toString();
	}
}