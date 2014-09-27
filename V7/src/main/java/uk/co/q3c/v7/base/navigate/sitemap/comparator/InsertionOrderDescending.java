/*
 * Copyright (C) 2013 David Sowerby
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with
 * the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on
 * an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations under the License.
 */
package uk.co.q3c.v7.base.navigate.sitemap.comparator;

import java.util.Comparator;

import uk.co.q3c.v7.base.navigate.sitemap.MasterSitemapNode;
import uk.co.q3c.v7.base.navigate.sitemap.UserSitemapNode;

/**
 * Comparator which can be used to sort SitemapNode by insertion order, based on {@link MasterSitemapNode#getId()}
 */
public class InsertionOrderDescending implements Comparator<UserSitemapNode> {
	/**
	 *
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(UserSitemapNode o1, UserSitemapNode o2) {
		return o2.getId() - o1.getId();
	}

}