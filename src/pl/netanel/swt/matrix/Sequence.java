/*******************************************************************************
 * Copyright (c) 2011 netanel.pl.
 * All rights reserved. This source code and the accompanying materials
 * are made available under the terms of the EULA v1.0
 * which accompanies this distribution, and is available at
 * http://www.netanel.pl/swt-matrix/EULA.html
 ******************************************************************************/
package pl.netanel.swt.matrix;

import java.util.Iterator;

/**
 * <p>Indicates iteration capability. It has more general semantics 
 * then traversing a collection with concrete objects in it.</p>
 * <p>Sequence has the following advanages over an {@link Iterator}:</p><ul> 
 * <li>It doesn't have to return one value like <code>Iterator.next()</code>. 
 * 		In case of a Cartesian product of two sequences for example. 
 * 		Accessing the value of each sequence individually performes better then 
 * 		creating on each iteration step a seperate object containg them both 
 * 		only to be able return it from a single method of <code>Iterator.next()</code></li>
 * <li>It doesn't need to implement <code>Iterator.remove()</code> </li>
 * <li>It is reusable thanks to the <code>init()</code> method, 
 * 		which provides a better performance the creating whole object over and over again. </li>
 * </ul>
 * Usage example: <pre>
 * Sequence seq = new XYSequence(); // usually defined somewhere outside of the loop
 * for (seq = init(); seq.next();) {
 *   System.out.println(seq.x, seq.y);
 * }
 * </pre>
 * 
 * @author jacek.p.kolodziejczyk@gmail.com
 * @created 03-08-2010
 */
interface Sequence {
	/**
	 * Initiates the iteration. Can be used to restart the iteration 
	 * and thus to reuse the instance of Sequence without creating a new one.
	 */
	public void init();

	/**
	 * Performs next iteration step and returns true if that's been possible, or false otherwise. 
	 * Another words false return value indicates the end of iteration.
	 * 
	 * @return success state of last iteration
	 */
	public boolean next();
}
