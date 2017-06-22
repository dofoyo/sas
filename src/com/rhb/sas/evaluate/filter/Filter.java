package com.rhb.sas.evaluate.filter;

import java.util.List;
import java.util.Map;

import com.rhb.sas.firm.Firm;

public interface Filter {

	public Map<Integer,List<Firm>> result();
}
