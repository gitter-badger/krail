/*
 * Copyright (C) 2014 David Sowerby
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
package uk.co.q3c.util;

/**
 * A default implementation for {@link NodeCreator} which assumes that the source and target nodes are the same. This
 * would be used by {@link TreeCopier} where the nodes in the target tree are just references to nodes in the source
 * tree.
 *
 * @author David Sowerby
 * @date 27 May 2014
 * @param <S>
 */
public class DefaultNodeCreator<S> implements NodeCreator<S, S> {

	@Override
	public S create(S source) {
		return source;
	}

	@Override
	public S sourceNodeFor(S target) {
		return target;
	}

}
