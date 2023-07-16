module Demo {
  //UsedALotSharedIceObject
  interface UALSIO {
    int addGet();
  };
  //DefaultUsedALotSharedIceObject
  interface DUALSIO {
    long subtract(long a, long b);
  };
  //UsedRarelyDedicatedIceObject
  interface URDIO {
    void updateDescription(string description);
  };

};