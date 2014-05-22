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
package uk.co.q3c.v7.base.navigate.sitemap;

import java.util.Map;
import java.util.Map.Entry;

import com.google.inject.Inject;

/**
 * If a Map<String, DirectSitemapEntry> binding has been created (using Guice modules sub-classed from
 * {@link DirectSitemapModule}), then {@link #pageMap} will be non-null. If so, its contents are transferred to the
 * {@link MasterSitemap}. Also loads the standard pages.
 *
 * @author David Sowerby
 *
 */
public class DefaultDirectSitemapLoader extends SitemapLoaderBase implements DirectSitemapLoader {

	private Map<String, DirectSitemapEntry> pageMap;
	private final MasterSitemap sitemap;
	private Map<String, RedirectEntry> redirects;

	@Inject
	protected DefaultDirectSitemapLoader(MasterSitemap sitemap) {
		this.sitemap = sitemap;
	}

	@Override
	public boolean load() {
		if (pageMap != null) {
			for (Entry<String, DirectSitemapEntry> entry : pageMap.entrySet()) {
				MasterSitemapNode node = sitemap.append(entry.getKey());
				DirectSitemapEntry value = entry.getValue();
				node.setLabelKey(value.getLabelKey());
				node.setPageAccessControl(value.getPageAccessControl());
				node.setViewClass(value.getViewClass());
				if (node.getLabelKey() instanceof StandardPageKey) {
					sitemap.addStandardPage((StandardPageKey) entry.getValue().getLabelKey(), node);
				}
			}
			processRedirects();
			return true;
		}
		processRedirects();
		return false;
	}

	/**
	 * Transfers directly defined URI redirects to the {@link MasterSitemap}
	 */
	protected void processRedirects() {
		if (redirects != null) {
			for (Entry<String, RedirectEntry> entry : redirects.entrySet()) {
				sitemap.addRedirect(entry.getKey(), entry.getValue().getRedirectTarget());
			}
		}
	}

	/**
	 * Uses Method injection to enable use of optional parameter
	 *
	 * @param map
	 */
	@Inject(optional = true)
	protected void setMap(Map<String, DirectSitemapEntry> map) {
		this.pageMap = map;
	}

	/**
	 * Uses Method injection to enable use of optional parameter
	 *
	 * @param map
	 */
	@Inject(optional = true)
	protected void setRedirects(Map<String, RedirectEntry> redirects) {
		this.redirects = redirects;
	}

}
