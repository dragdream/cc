package com.tianee.lucene;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.NumericRangeQuery;
import org.apache.lucene.util.Version;

public class MyQueryParser extends MultiFieldQueryParser {  
    public MyQueryParser(Version matchVersion, String[] b, Analyzer a) {  
        super(matchVersion,b,a);
    }  
    
    @Override  
    protected org.apache.lucene.search.Query getRangeQuery(String field,  
            String part1, String part2, boolean inclusive,boolean arg)  
            throws ParseException {  
    	if(field.contains("|")){
    		String sp [] = field.split("\\|");
    		field = sp[0];
    		if(sp[1].equals("int")){
    			return NumericRangeQuery.newIntRange(field, Integer.parseInt(part1),
    					Integer.parseInt(part2), inclusive, inclusive);
    		}else if(sp[1].equals("long")){
    			return NumericRangeQuery.newLongRange(field, Long.parseLong(part1),
                        Long.parseLong(part2), inclusive, inclusive);
    		}else if(sp[1].equals("double")){
    			return NumericRangeQuery.newDoubleRange(field, Double.parseDouble(part1),
    					Double.parseDouble(part2), inclusive, inclusive);
    		}else if(sp[1].equals("float")){
    			return NumericRangeQuery.newFloatRange(field, Float.parseFloat(part1),
    					Float.parseFloat(part2), inclusive, inclusive);
    		}
    	}
        return super.newRangeQuery(field, part1, part2, inclusive,arg);  
    }  
}  
