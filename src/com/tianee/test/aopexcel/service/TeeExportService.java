package com.tianee.test.aopexcel.service;

import java.util.ArrayList;

import org.springframework.stereotype.Service;

import com.tianee.webframe.data.TeeDataRecord;
@Service
public class TeeExportService {
  
  public ArrayList<TeeDataRecord> getDbRecord(){
    ArrayList<TeeDataRecord > dbL = new ArrayList<TeeDataRecord>();
    for (int i = 0; i < 10; i++) {
      TeeDataRecord dbrec = new TeeDataRecord();
      dbrec.addField("好", "1" + i);
      dbrec.addField("好1", "1");
      dbrec.addField("好2", "1");
      dbrec.addField("好43", "1");
      dbrec.addField("好4", "1");
      dbrec.addField("好5", "1");
      dbrec.addField("好6", "1");
      dbrec.addField("好7", "1");
      dbL.add(dbrec);
    }
    return dbL;
  }
}
