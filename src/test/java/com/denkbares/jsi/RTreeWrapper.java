//   RTreeWrapper.java
//   Java Spatial Index Library
//   Copyright (C) 2002-2005 Infomatiq Limited.
//  
//  This library is free software; you can redistribute it and/or
//  modify it under the terms of the GNU Lesser General Public
//  License as published by the Free Software Foundation; either
//  version 2.1 of the License, or (at your option) any later version.
//  
//  This library is distributed in the hope that it will be useful,
//  but WITHOUT ANY WARRANTY; without even the implied warranty of
//  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
//  Lesser General Public License for more details.
//  
//  You should have received a copy of the GNU Lesser General Public
//  License along with this library; if not, write to the Free Software
//  Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307 USA

package com.denkbares.jsi;

import java.util.Properties;

import gnu.trove.procedure.TIntProcedure;

import com.denkbares.jsi.rtree.RTree;

/**
 * A completely useless wrapper class for the RTree class.
 * <p>
 * Actually the point to introduce the same overhead as
 * the SILWrapper class, so that performance comparisons
 * can be made.
 */
public class RTreeWrapper implements SpatialIndex {
	private RTree tree;

	/**
	 * @see SpatialIndex#init(Properties)
	 */
	public void init(Properties props) {
		// create a memory-based storage manager

		tree = new RTree();
		tree.init(props);
	}

	/**
	 * @see SpatialIndex#nearest(Point, gnu.trove.TIntProcedure, float)
	 */
	public void nearest(Point p, TIntProcedure v, float furthestDistance) {
		tree.nearest(new Point(p.x, p.y),
				new IntProcedure2(v),
				Float.POSITIVE_INFINITY);
	}

	/**
	 * @see SpatialIndex#nearestN(Point, gnu.trove.TIntProcedure, int, float)
	 */
	public void nearestN(Point p, TIntProcedure v, int n, float furthestDistance) {
		tree.nearestN(new Point(p.x, p.y),
				new IntProcedure2(v),
				n,
				furthestDistance);
	}

	/**
	 * @see SpatialIndex#nearestNUnsorted(Point, gnu.trove.TIntProcedure, int, float)
	 */
	public void nearestNUnsorted(Point p, TIntProcedure v, int n, float furthestDistance) {
		tree.nearestNUnsorted(new Point(p.x, p.y),
				new IntProcedure2(v),
				n,
				furthestDistance);
	}

	/**
	 * @see SpatialIndex#intersects(Rectangle, gnu.trove.TIntProcedure)
	 */
	public void intersects(Rectangle r, TIntProcedure ip) {
		Rectangle r2 = new Rectangle(r.minX, r.minY, r.maxX, r.maxY);
		tree.intersects(r2, new IntProcedure2(ip));
	}

	/**
	 * @see SpatialIndex#contains(Rectangle, gnu.trove.TIntProcedure)
	 */
	public void contains(Rectangle r, TIntProcedure ip) {
		Rectangle r2 = new Rectangle(r.minX, r.minY, r.maxX, r.maxY);
		tree.contains(r2, new IntProcedure2(ip));
	}

	/**
	 * @see SpatialIndex#add(Rectangle, int)
	 */
	public void add(Rectangle r, int id) {
		Rectangle r2 = new Rectangle(r.minX, r.minY, r.maxX, r.maxY);
		tree.add(r2, id);
	}

	/**
	 * @see SpatialIndex#delete(Rectangle, int)
	 */
	public boolean delete(Rectangle r, int id) {
		Rectangle r2 = new Rectangle(r.minX, r.minY, r.maxX, r.maxY);
		return tree.delete(r2, id);
	}

	/**
	 * @see SpatialIndex#size()
	 */
	public int size() {
		return tree.size();
	}

	/**
	 * @see SpatialIndex#getBounds()
	 */
	public Rectangle getBounds() {
		return tree.getBounds();
	}

	/**
	 * @see SpatialIndex#getVersion()
	 */
	public String getVersion() {
		return "RTreeWrapper-" + BuildProperties.getVersion();
	}

	class IntProcedure2 implements TIntProcedure {
		private TIntProcedure m_intProcedure = null;

		public IntProcedure2(TIntProcedure ip) {
			m_intProcedure = ip;
		}

		public boolean execute(int i) {
			return m_intProcedure.execute(i);
		}
	}

}
