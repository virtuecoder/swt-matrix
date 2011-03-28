package pl.netanel.swt.matrix.painter;

import java.util.ArrayList;
import java.util.Iterator;

import org.eclipse.swt.graphics.Rectangle;

import pl.netanel.swt.matrix.Painter;
import pl.netanel.util.DelegatingList;


/**
 * Maintains a list of painters defining the order they paint.
 * <p>
 * The list can be customized by adding, removing, replacing and reordering 
 * individual painters. 
 *
 * @author Jacek
 * @created 15-10-2010
 */

class Painters extends DelegatingList<Painter> {
	protected Rectangle bounds;

	public Painters() {
		super(new ArrayList<Painter>());
	}

	
	/*------------------------------------------------------------------------
	 * Manage the collection of painters 
	 */
	
	/**
	 * Returns the first painter matching the given class or null if not found.
	 * 
	 * @param clazz
	 * @param painter
	 * @return 
	 */
	public <T extends Painter> T get(Class<T> clazz) {
		for (int i = 0; i < items.size(); i++) {
			Painter item = items.get(i);
			if (clazz.isInstance(item)) {
				return (T) item;
			}
		}
		return null;
	}
	


	public void removeByClass(Class<? extends Painter> clazz) {
		for (Iterator<Painter> it = items.iterator(); it.hasNext();) {
			Painter painter = it.next();
			if (clazz.isInstance(painter)) {
				it.remove();
			}
		}
	}

	public void replaceOrAdd(Class<? extends Painter> clazz, Painter painter) {
		for (int i = 0; i < items.size(); i++) {
			Painter item = items.get(i);
			if (clazz.isInstance(item)) {
				items.set(i, painter);
				return;
			}
		}
		items.add(painter);
	}

	public void replaceOrAdd(Class<? extends Painter> clazz, int index, Painter painter) {
		for (int i = 0; i < items.size(); i++) {
			Painter item = items.get(i);
			if (clazz.isInstance(item)) {
				items.set(i, painter);
				return;
			}
		}
		items.add(index, painter);
	}
}
