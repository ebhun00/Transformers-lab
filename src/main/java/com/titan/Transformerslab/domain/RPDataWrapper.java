package com.titan.Transformerslab.domain;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class RPDataWrapper {

	private ResourceKey resourceKey;
	Map<String, RPOrderDomain> rpOrderInfoMap = null;
}
