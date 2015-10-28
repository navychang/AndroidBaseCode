/*
 * Copyright (C) 2006 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.lixh.basecode.util;

import android.text.GetChars;
import android.text.TextUtils;

public class PasswordTransformationMethod {
	public CharSequence getTransformation(CharSequence source, int start,
			int end) {
		return new PasswordCharSequence(source, start, end);
	}

	public static PasswordTransformationMethod sInstance;

	public static PasswordTransformationMethod getInstance() {
		if (sInstance != null)
			return sInstance;

		sInstance = new PasswordTransformationMethod();
		return sInstance;
	}

	private static class PasswordCharSequence implements CharSequence, GetChars {
		private int str_start;
		private int str_end;
		private static char DOT = '*';
		private CharSequence mSource;

		public PasswordCharSequence(CharSequence source, int start, int end) {
			this.str_start = start;
			this.str_end = end;
			mSource = source;
		}

		public int length() {
			return mSource.length();
		}

		public char charAt(int i) {
			for (int a = 0; a < mSource.length(); a++) {

				if (i >= str_start && i < str_end) {
					return mSource.charAt(i);
				}
			}
			return DOT;
		}

		public CharSequence subSequence(int start, int end) {
			char[] buf = new char[end - start];

			getChars(start, end, buf, 0);
			return new String(buf);
		}

		public String toString() {
			return subSequence(0, length()).toString();
		}

		public void getChars(int start, int end, char[] dest, int off) {
			TextUtils.getChars(mSource, start, end, dest, off);
			for (int i = str_start; i <= str_end; i++) {
				dest[i] = DOT;
			}

		}
	}

}
